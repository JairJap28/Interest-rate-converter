package com.example.economyapp.Base;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class ActivityBase extends AppCompatActivity {
    protected void initActivity(Activity activity){
        ButterKnife.bind(activity);
    }

    public abstract void start();
}
