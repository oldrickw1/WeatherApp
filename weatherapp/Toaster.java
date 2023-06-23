package com.example.weatherapp;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    static public void toast(Context c, String m) {
        Toast.makeText(c, m, Toast.LENGTH_SHORT).show();
    }
}
