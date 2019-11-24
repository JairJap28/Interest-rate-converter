package com.example.economyapp.FeeCalculation;


import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
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
import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.FeeCalculation.mvp.FeeCalculationPresenter;
import com.example.economyapp.FeeCalculation.mvp.contracts.FeeCalculationMVP;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;
import com.example.economyapp.Utiles.Messages;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

import java.lang.ref.WeakReference;
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
    @BindView(R.id.input_rate_fee_calculation)
    EditText interestRate;
    @BindView(R.id.input_number_dues_fee_calculation)
    EditText inputNumberDues;
    @BindView(R.id.input_amout_fee_calculation)
    EditText inputFinalAmount;
    @BindView(R.id.btn_add_recycler_fee_calculation)
    Button addPaymentsToView;
    @BindView(R.id.btnCalculateRate_fee_calculation)
    Button calculate;

    private EntityRateConvert rateEntity;
    private ArrayList<EntityPayment> payments;
    private EntityPayment initialData;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private WeakReference<Context> contextWeakReference;

    private FeeCalculationPresenter presenter;
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
        initialData = new EntityPayment();
        contextWeakReference = new WeakReference<>(getContext());
        presenter = new FeeCalculationPresenter();
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
        calculate.setOnClickListener(view -> calculate());
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
        Pair<Integer, Messages> validationNPayments = validateAndGetNumberPayments(nPayments);
        if (validationNPayments.second != null) {
            validationNPayments.second.showAlert(contextWeakReference);
        }
        nPayments = validationNPayments.first;

        List<EntityPayment> payments = new ArrayList<>();
        for (int i = 0; i < nPayments; i++) {
            payments.add(new EntityPayment());
        }
        mAdapter = new PaymentsAdapter(payments);
        recyclerPayments.setAdapter(mAdapter);

        showPaymentsInput(false);
    }

    private Pair<Integer, Messages> validateAndGetNumberPayments(int nPayments) {
        if (inputNumberDues.getText().toString().isEmpty()) {
            return new Pair<>(0, new Messages("Alerta", "Para ingresar pagos extraordinarios porfavor primero indique el número de cuotas"));
        } else if (Integer.parseInt(inputNumberDues.getText().toString()) < nPayments) {
            return new Pair<>(Integer.parseInt(inputNumberDues.getText().toString()),
                    new Messages("Información", "El número de pagos ingresado es superior al número de cuotas ingresadas, se permitirá ingresar como maximo el número de cuotas"));
        }
        return new Pair<>(nPayments, null);
    }

    private void clearRecycler() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerPayments.setLayoutManager(layoutManager);
        mAdapter = new PaymentsAdapter(new ArrayList<>());
        recyclerPayments.setAdapter(mAdapter);
    }

    private void getPayments() {
        payments = new ArrayList<>();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            PaymentsAdapter.ViewHolderPayment viewHolder = (PaymentsAdapter.ViewHolderPayment) recyclerPayments.findViewHolderForAdapterPosition(i);
            EntityPayment payment = new EntityPayment();
            if (viewHolder != null && viewHolder.amount.getEditText() != null && viewHolder.numberPeriod.getEditText() != null) {
                payment.setAmount(Float.parseFloat(viewHolder.amount.getEditText().getText().toString()));
                payment.setPeriodo(Integer.parseInt(viewHolder.numberPeriod.getEditText().getText().toString()));
                payments.add(payment);
            }
        }
    }

    private void calculate() {
        getPayments();
        rateEntity.setRate(Float.parseFloat(interestRate.getText().toString().isEmpty() ? "0" : interestRate.getText().toString()));
        initialData.setPeriodo(Integer.parseInt(inputNumberDues.getText().toString().isEmpty() ? "0" : inputNumberDues.getText().toString()));
        initialData.setAmount(Float.parseFloat(inputFinalAmount.getText().toString().isEmpty() ? "0" : inputFinalAmount.getText().toString()));
        presenter.setInitialData(initialData);
        presenter.setInterest(rateEntity);
        presenter.setPayments(payments);
        Pair<Boolean, Messages> validationImportantData = validateImportantData();
        if (validationImportantData.first) {
            if (rateEntity.getRate() == 0.0) {
                Messages message = new Messages("Alerta tasa interes", "Desea continuar con una tasa de interes del 0%");
                message.showAletWithAction(contextWeakReference, new Runnable() {
                    @Override
                    public void run() {
                        message.showAletWithAction(contextWeakReference, () -> {
                            validationImportantData.second.showLoading(contextWeakReference);
                            presenter.calculateButtonClicked();
                            validationImportantData.second.hideLoading();
                        });
                    }
                });
            } else {
                validationImportantData.second.showLoading(contextWeakReference);
                presenter.setTypeCalculation(ConventionsFee.DueRedcution);
                presenter.calculateButtonClicked();
                validationImportantData.second.hideLoading();
            }
        } else {
            validationImportantData.second.showAlert(contextWeakReference);
        }
    }

    private Pair<Boolean, Messages> validateImportantData() {
        if (initialData.getPeriodo() == 0 || initialData.getAmount() == 0) {
            Messages messages = new Messages("Datos iniciales", "Es importante que ingrese la cantidad de periodos y la cantidad de dinero");
            return new Pair<>(false, messages);
        }
        return new Pair<>(true, new Messages("Cargando", "Espere un momento por favor..."));
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
