package com.example.economyapp.FeeCalculation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;
import com.example.economyapp.rate_converter.EntityRateConvert;

import butterknife.BindView;

public class FeeCalculation extends FragmentBase implements FeeCalculationMVP.View, AdapterView.OnItemSelectedListener {

    //region Properties
    @BindView(R.id.initial_period_fee_calculation)
    Spinner spinnerPeriods;
    @BindView(R.id.spinner_initial_nominal_efectiva_fee_calculation)
    Spinner spinnerNominalEfectiva;
    @BindView(R.id.spinner_initial_vencida_anticipada_fee_calculation)
    Spinner spinnerVencidaAnticipada;

    private EntityRateConvert rateEntity;
    //endregion

    //region Override Fragment Methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setLayout(R.layout.fragment_fee_calculation);
        super.setMenu(R.menu.menu_empty);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rateEntity = new EntityRateConvert();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        switch (parent.getId()) {
            case R.id.initial_period_fee_calculation:
                rateEntity.setPeriod(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_initial_nominal_efectiva_fee_calculation:
                rateEntity.setNominalEfectiva(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_initial_vencida_anticipada_fee_calculation:
                rateEntity.setVencidaAnticipada(parent.getItemAtPosition(pos).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //enderegion

    //region Override Base Methods
    @Override
    public void initializeUI() {
        super.initilize();
        initializeSpinners();
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

    @Override
    public void initializeSpinners() {
        spinnerPeriods.setAdapter(getSpinnerPeriods());
        spinnerPeriods.setOnItemSelectedListener(this);
        spinnerNominalEfectiva.setAdapter(getNominalEfectiva());
        spinnerNominalEfectiva.setOnItemSelectedListener(this);
        spinnerVencidaAnticipada.setAdapter(getVencidaAnticipada());
        spinnerVencidaAnticipada.setOnItemSelectedListener(this);
    }

    @Override
    public ArrayAdapter<CharSequence> getAdapter(int data) {
        if (getContext() != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    data, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return adapter;
        }
        return null;
    }
    //endregion

    //region Class Methods
    //endregion

    //Contract FeeCalculationMVP
    @Override
    public ArrayAdapter<CharSequence> getSpinnerPeriods() {
        return getAdapter(R.array.periciocidad);
    }

    @Override
    public ArrayAdapter<CharSequence> getNominalEfectiva() {
        return getAdapter(R.array.efectiva_nominal);
    }

    @Override
    public ArrayAdapter<CharSequence> getVencidaAnticipada() {
        return getAdapter(R.array.vencida_anticipada);
    }

    @Override
    public void showResult(float value) {

    }

    @Override
    public void restoreFields() {

    }
    //endregion
}
