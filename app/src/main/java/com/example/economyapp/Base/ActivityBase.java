package com.example.economyapp.Base;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class ActivityBase extends AppCompatActivity {
    private int menuId;

    public void setMenu(int menu) {
        this.menuId = menu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuId, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initialize(Activity activity){
        ButterKnife.bind(activity);
    }

    public void customizeToolBar() {
    }

    public abstract void startActivity();

    public abstract void startUI();

}
