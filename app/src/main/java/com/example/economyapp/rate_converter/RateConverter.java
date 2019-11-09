package com.example.economyapp.rate_converter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.economyapp.Base.ActivityBase;
import com.example.economyapp.R;

public class RateConverter extends ActivityBase {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter_rate);
    }

    @Override
    public void start() {
        super.initActivity(this);
    }
}
