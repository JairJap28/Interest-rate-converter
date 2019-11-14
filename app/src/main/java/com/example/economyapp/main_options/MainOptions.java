package com.example.economyapp.main_options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;

import butterknife.BindView;

public class MainOptions extends FragmentBase {
    //region Properties
    @BindView(R.id.card_view_option_rate)
    CardView optionRate;
    @BindView(R.id.card_view_option_fee_calculation)
    CardView optionFeeCalculation;
    //endregion

    //region Override Fragment Methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setLayout(R.layout.fragment_main);
        super.setMenu(R.menu.menu_main);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //endregion

    //region Override Base Methods
    @Override
    public void initializeUI() {
        super.initilize();
        if(super.getView() != null){
            optionRate.setOnClickListener(viewClick -> Navigation.findNavController(super.getView()).navigate(R.id.action_mainOptions_to_rateConverter));
            optionFeeCalculation.setOnClickListener(viewClick -> Navigation.findNavController(super.getView()).navigate(R.id.action_mainOptions_to_feeCalculation));
        }
    }

    @Override
    public void customizeToolbar() {
        Toolbar toolbar;
        if (getActivity() != null) {
            toolbar = ((MainActivity) getActivity()).toolbar;
            toolbar.setTitle(R.string.economia);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void initializeSpinners() {

    }

    @Override
    public ArrayAdapter<CharSequence> getAdapter(int data) {
        return null;
    }
    //endregion

    //region Class Methods
    //endregion

    //region ButterKnife Methods
    //endregion
}
