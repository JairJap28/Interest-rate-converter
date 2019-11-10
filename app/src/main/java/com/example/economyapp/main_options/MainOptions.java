package com.example.economyapp.main_options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.R;

public class MainOptions extends FragmentBase {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setLayout(R.layout.fragment_converter_rate);
        return inflater.inflate(R.layout.fragment_converter_rate, container, false);
    }
}
