package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Button getCityLocationBtn, useCityLocationButton, useCityNameButton;
    EditText cityInputET;
    EditText latitudeET;
    EditText longitudeET;
    RecyclerView weatherRV;

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

        RequestQueue queue = Volley.newRequestQueue(this);

        //Todo: Both VolleyRequests are of type String, whereas they should be of type JSON. Change this.
        //Todo: Extract the right data from that JSON and display accordingly.
        //Todo: Add the last onClickListener for the useCityNameButton.


        getCityLocationBtn.setOnClickListener((v) -> {
            String city = cityInputET.getText().toString();
            String url = "https://api.api-ninjas.com/v1/city?name=" + city;
            Logger.log("URL:    " + url);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    response -> {
                        Toaster.toast(MainActivity.this, response);
                        Logger.log(response);
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
            queue.add(stringRequest);
        });



        useCityLocationButton.setOnClickListener((v) -> {
            String latitude = latitudeET.getText().toString();
            String longitude = longitudeET.getText().toString();
            latitudeET.setText("");
            longitudeET.setText("");

            // Some variables are hard coded, but that's ok for purpose of this program
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&daily=uv_index_max&start_date=2023-06-23&end_date=2023-06-30&timezone=Europe%2FBerlin";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    response -> {
                        Toaster.toast(MainActivity.this, response);
                        Logger.log(response);
                    },
                    error -> {
                        Toaster.toast(MainActivity.this, error.toString());
                        Logger.log(error.toString());
                    }
            );
            queue.add(stringRequest);
        });

    }
}