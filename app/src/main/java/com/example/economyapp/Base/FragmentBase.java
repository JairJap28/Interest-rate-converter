package com.example.economyapp.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public abstract class FragmentBase extends Fragment implements BaseContract {
    //region Properties
    private int layout;
    private int menuId;
    private View view;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    protected void setLayout(int layout) {
        this.layout = layout;
    }

    public void setMenu(int menu) {
        this.menuId = menu;
    }
    //endregion

    //region Override Fragment Methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout, container, false);
        setHasOptionsMenu(true);
        customizeToolbar();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initializeUI();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(menuId, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    //endregion

    //region Override Contract Methods
    @Override
    public void initilize() {
        ButterKnife.bind(this, view);
    }
    //endregion

    //region Abstract Methods
    public abstract void initializeUI();
    public abstract void customizeToolbar();
    //endregion
}
