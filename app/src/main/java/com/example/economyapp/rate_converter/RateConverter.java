package com.example.economyapp.rate_converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;

import butterknife.BindView;

public class RateConverter extends FragmentBase {
    //region Properties
    @BindView(R.id.layout_initial_rate)
    LinearLayout layoutInitialRate;
    @BindView(R.id.layout_final_rate)
    LinearLayout layoutFinalRate;
    @BindView(R.id.layout_result_rate)
    LinearLayout layoutResultRate;
    @BindView(R.id.imgButton_show_CardView_initial_rate)
    ImageButton imgShowCardInitialRate;
    @BindView(R.id.imgButton_show_CardView_final_rate)
    ImageButton imgShowCardFinalRate;
    @BindView(R.id.btnCalculateRate)
    Button calculateRate;
    @BindView(R.id.btnCleanRate)
    Button cleanRate;
    //endregion

    //region Override Fragment Methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setLayout(R.layout.fragment_converter_rate);
        super.setMenu(R.menu.menu_fragment);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    //endregion

    //region Override Base Methods
    @Override
    public void initializeUI() {
        super.initilize();
        imgShowCardInitialRate.setOnClickListener(view -> showCardsRate());
        imgShowCardFinalRate.setOnClickListener(view -> showCardsRate());
        calculateRate.setOnClickListener(view -> hideCardsRate());
        cleanRate.setOnClickListener(view -> restoreFields());
    }

    @Override
    public void customizeToolbar() {
        Toolbar toolbar;
        if (getActivity() != null) {
            toolbar = ((MainActivity) getActivity()).toolbar;
            toolbar.setTitle(R.string.conversor_de_tasas_de_interes);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    //endregion

    //region Class Methods
    private void showCardsRate(){
        layoutInitialRate.setVisibility(View.VISIBLE);
        layoutFinalRate.setVisibility(View.VISIBLE);
        layoutResultRate.setVisibility(View.GONE);
        imgShowCardInitialRate.setVisibility(View.GONE);
        imgShowCardFinalRate.setVisibility(View.GONE);
    }

    private void hideCardsRate(){
        layoutInitialRate.setVisibility(View.GONE);
        layoutFinalRate.setVisibility(View.GONE);
        layoutResultRate.setVisibility(View.VISIBLE);
        imgShowCardInitialRate.setVisibility(View.VISIBLE);
        imgShowCardFinalRate.setVisibility(View.VISIBLE);
    }

    private void restoreFields(){
        Toast.makeText(getActivity(), "Restoring Fields", Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region Butterkinfe
    //endregion
}
