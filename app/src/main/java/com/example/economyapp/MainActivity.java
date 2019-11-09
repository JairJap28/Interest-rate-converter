package com.example.economyapp;

import android.os.Bundle;

import com.example.economyapp.Base.ActivityBase;

public class MainActivity extends ActivityBase {

    //region Properties
    //endregion

    //region Override Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //endregion

    //region Override Base Methods
    @Override
    public void start() {
        super.initActivity(this);
    }
    //endregion

    //region Class Methods
    //endregion
}
