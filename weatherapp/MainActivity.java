package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {


    Button getCityLocationBtn, useCityLocationButton, useCityNameButton;
    EditText inputET;
    RecyclerView weatherRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCityLocationBtn = findViewById(R.id.get_city_geolocation_btn);
        useCityLocationButton = findViewById(R.id.use_location_btn);
        useCityNameButton = findViewById(R.id.use_city_name_btn);
        inputET = findViewById(R.id.input_et);
        weatherRV = findViewById(R.id.weatherRV);



        useCityLocationButton.setOnClickListener((v) -> {
            String input = inputET.getText().toString();
            inputET.setText("");
            String[] inputArray;
            try {
                inputArray = input.split(" ");
            } catch (Exception e) {
                Toaster.toast(this, "Enter in form: 'latitude longitude'");
                return;
            }
            String latitude = inputArray[0];
            String longitude = inputArray[1];

            RequestQueue queue = Volley.newRequestQueue(this);
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