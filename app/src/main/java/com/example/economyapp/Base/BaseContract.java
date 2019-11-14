package com.example.economyapp.Base;

import android.widget.ArrayAdapter;

public interface BaseContract<T> {
    void initilize();

    ArrayAdapter<CharSequence> getAdapter(int data);
}
