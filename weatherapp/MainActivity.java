package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    Button getCityIDBtn, useCityIDButton, useCityNameButton;
    EditText inputET;
    RecyclerView weatherRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCityIDBtn = findViewById(R.id.get_city_id_btn);
        useCityIDButton = findViewById(R.id.use_city_id_btn);
        useCityNameButton = findViewById(R.id.use_city_name_btn);
        inputET = findViewById(R.id.input_et);
        weatherRV = findViewById(R.id.weatherRV);

        View.OnClickListener handleInput = (v) -> {

            if (v.getId() == R.id.get_city_id_btn) {

                return;
            }
            if (v.getId() == R.id.use_city_id_btn) {

                return;
            }
            if (v.getId() == R.id.use_city_name_btn) {

                return;
            }
        };

        getCityIDBtn.setOnClickListener(handleInput);
        useCityNameButton.setOnClickListener(handleInput);
        useCityNameButton.setOnClickListener(handleInput);

    }
}