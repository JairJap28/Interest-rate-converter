package com.example.economyapp.rate_converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.R;

public class RateConverter extends FragmentBase {
    //region Properties
    //endregion

    //region Override Fragment Methods

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setLayout(R.layout.fragment_converter_rate);
        return inflater.inflate(R.layout.fragment_converter_rate, container, false);
    }

    //endregion

    //region Override Base Methods
    //endregion

    //region Class Methods
    //endregion
}
