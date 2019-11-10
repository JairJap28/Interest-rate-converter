package com.example.economyapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.economyapp.Base.ActivityBase;

public class MainActivity extends ActivityBase {

    //region Properties
    public Toolbar toolbar;
    //endregion

    //region Override Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity();
        startUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.setMenu(R.menu.menu_main);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Override Base Methods
    @Override
    public void startActivity() {
        super.initialize(this);
    }

    @Override
    public void startUI() {
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);

        customizeToolBar();
        setSupportActionBar(toolbar);
    }

    @Override
    public void customizeToolBar() {
        super.customizeToolBar();
        toolbar.setTitle(R.string.economia);
    }
    //endregion

    //region Class Methods
    //endregion
}
