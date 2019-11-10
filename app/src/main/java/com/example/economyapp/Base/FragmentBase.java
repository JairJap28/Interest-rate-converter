package com.example.economyapp.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public abstract class FragmentBase extends Fragment implements BaseContract {

    private int layout;
    private View view;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    protected void setLayout(int layout) {
        this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout, container, false);
        customizeToolbar();
        return view;
    }

    @Override
    public void initilize() {
        ButterKnife.bind(this, view);
    }

    public abstract void initializeUI();

    public abstract void customizeToolbar();
}
