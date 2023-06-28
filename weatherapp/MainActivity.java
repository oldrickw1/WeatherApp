package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {


    Button getCityLocationBtn, useCityLocationButton, useCityNameButton;
    EditText cityInputET;
    EditText latitudeET;
    EditText longitudeET;
    RecyclerView weatherRV;
    private String latitude;
    private String longitude;
    private String uv_index_max;

    RequestQueueSingleton queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCityLocationBtn = findViewById(R.id.get_city_geolocation_btn);
        useCityLocationButton = findViewById(R.id.use_location_btn);
        useCityNameButton = findViewById(R.id.use_city_name_btn);
        cityInputET = findViewById(R.id.city_input_et);
        latitudeET = findViewById(R.id.latitude_et);
        longitudeET = findViewById(R.id.longitude_et);
        weatherRV = findViewById(R.id.weatherRV);

        longitude = "";
        latitude = "";


        queue = RequestQueueSingleton.getInstance(this);

        //TODO: THERE IS A THREAD ISSUE, 100% sure. DEBUG THIS.

        getCityLocationBtn.setOnClickListener(getLocationFromCityName);
        useCityLocationButton.setOnClickListener(getWeatherFromLocation);
        useCityNameButton.setOnClickListener(getWeatherFromCityName);
    }




    private View.OnClickListener getWeatherFromLocation = (v) -> {
        if (latitude.equals("")) {
            latitude = latitudeET.getText().toString();
        }
        if (longitude.equals("")) {
            longitude = longitudeET.getText().toString();
        }
        latitudeET.setText("");
        latitudeET.setHint("Latitude: ");
        longitudeET.setText("");
        longitudeET.setHint("Longitude: ");

        // Some variables are hard coded, but that's ok for purpose of this program
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=uv_index_max&start_date=2023-06-23&end_date=2023-06-30&timezone=Europe%2FBerlin";
        Logger.log("This one >>> " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONObject daily = null;
                    try {
                        daily = response.getJSONObject("daily");
                        float uv_index_max = (float) daily.getJSONArray("uv_index_max").getDouble(0);
                        Logger.log("UV_INDEX: " + uv_index_max);
                        Toaster.toast(this, "UV_INDEX MAX: " + uv_index_max);
                        this.uv_index_max = Float.toString(uv_index_max);
                    }
                    catch (Exception e) {
                        Logger.log(e.toString());
                    }
                },
                error -> {
                    Logger.log(error.toString());
                }
        );
        queue.requestQueue.add(jsonObjectRequest);
    };


    private View.OnClickListener getLocationFromCityName = (v) -> {
        String city = cityInputET.getText().toString();
        cityInputET.setText("");
        String url = "https://api.api-ninjas.com/v1/city?name=" + city;
        Logger.log("URL:    " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(0);
                        latitude = Double.toString(jsonObject.getDouble("latitude"));
                        longitude = Double.toString(jsonObject.getDouble("longitude"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                    latitudeET.setHint(latitude);
                    longitudeET.setHint(longitude);

                },
                error -> {
                    Toaster.toast(MainActivity.this, error.toString());
                    Logger.log(error.toString());
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", API.apiKey);
                return headers;
            }
        };
        queue.requestQueue.add(jsonArrayRequest);
    };

    private View.OnClickListener getWeatherFromCityName = (v) -> {
        Logger.log("Starting execution of getWeatherFromCityName");
        getLocationFromCityName.onClick(null);
        Logger.log("Just executed getLocationFromCityName");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Logger.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            e.printStackTrace();
        }
        Logger.log("About to execute getLocationFromCityName");
        getWeatherFromLocation.onClick(null);
    };


}