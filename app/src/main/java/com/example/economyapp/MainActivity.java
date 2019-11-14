package com.example.economyapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_info:
                showDialogAbout();
                break;
            case R.id.action_save:
                saveDataToHistory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Override Base Methods
    @Override
    public void startActivity() {
        super.initilize();
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

    @Override
    public ArrayAdapter<CharSequence> getAdapter(int data) {
        return null;
    }
    //endregion

    //region Class Methods
    public void showDialogAbout(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_about);

        ImageButton close = dialog.findViewById(R.id.imgButton_close_dialog_about);
        close.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    public void saveDataToHistory(){
        Toast.makeText(this, R.string.not_availaibe_yet, Toast.LENGTH_LONG).show();
    }
    //endregion
}
