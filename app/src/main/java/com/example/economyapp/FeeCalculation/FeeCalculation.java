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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;
import com.example.economyapp.rate_converter.EntityRateConvert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeeCalculation extends FragmentBase implements FeeCalculationMVP.View, AdapterView.OnItemSelectedListener {

    //region Properties
    @BindView(R.id.initial_period_fee_calculation)
    Spinner spinnerPeriods;
    @BindView(R.id.spinner_initial_nominal_efectiva_fee_calculation)
    Spinner spinnerNominalEfectiva;
    @BindView(R.id.spinner_initial_vencida_anticipada_fee_calculation)
    Spinner spinnerVencidaAnticipada;
    @BindView(R.id.layout_add_payments_fee_calculation)
    LinearLayout layoutPayments;
    @BindView(R.id.switch_add_payments_fee_calculation)
    Switch switchPayments;
    @BindView(R.id.recycler_view_payments_fee_calculation)
    RecyclerView recyclerPayments;
    @BindView(R.id.input_number_payments_fee_calculation)
    EditText numberPayments;
    @BindView(R.id.btn_add_recycler_fee_calculation)
    Button addPaymentsToView;

    private EntityRateConvert rateEntity;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
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
        initializeRecycler();
        switchPayments.setOnCheckedChangeListener((compoundButton, state) -> showPaymentsInput(state));
        addPaymentsToView.setOnClickListener(view -> initializeRecycler());
    }

    @Override
    public void customizeToolbar() {
        Toolbar toolbar;
        if (getActivity() != null) {
            toolbar = ((MainActivity) getActivity()).toolbar;
            toolbar.setTitle(R.string.calculo_de_cuota);

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
    private void showPaymentsInput(boolean state) {
        if (state) {
            layoutPayments.setVisibility(View.VISIBLE);
        } else {
            layoutPayments.setVisibility(View.GONE);
        }
    }

    private void initializeRecycler() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerPayments.setLayoutManager(layoutManager);
        int nPayments = numberPayments.getText().toString().isEmpty() ? 0 : Integer.parseInt(numberPayments.getText().toString());
        List<EntityPayment> payments = new ArrayList<>();
        for (int i = 0; i < nPayments; i++) {
            payments.add(new EntityPayment());
        }
        mAdapter = new PaymentsAdapter(payments);
        recyclerPayments.setAdapter(mAdapter);

        showPaymentsInput(false);
    }

    private void clearRecycler() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerPayments.setLayoutManager(layoutManager);
        mAdapter = new PaymentsAdapter(new ArrayList<>());
        recyclerPayments.setAdapter(mAdapter);
    }
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
