package com.example.economyapp.Base;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class ActivityBase extends AppCompatActivity implements BaseContract {
    //region Properties
    private int menuId;
    public void setMenu(int menu) {
        this.menuId = menu;
    }
    //endregion

    //overrida AppCompatActivity Methodds
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuId, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Override Contract Methods
    @Override
    public void initilize() {
        ButterKnife.bind(this);
    }

    //endregion

    //region Class Methods
    public void customizeToolBar() {
    }
    //endregion

    //region Abstract Methods
    public abstract void startActivity();
    public abstract void startUI();
    //region
}
