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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.FeeCalculation.mvp.FeeCalculationPresenter;
import com.example.economyapp.FeeCalculation.mvp.contracts.FeeCalculationMVP;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;
import com.example.economyapp.Tests.DynamicTable;
import com.example.economyapp.Utiles.Messages;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.card_view_result_fee_calculation)
    CardView cardViewResult;
    @BindView(R.id.layout_result_rate_feeC)
    LinearLayout layoutResult;
    @BindView(R.id.scroll_view_fee_calculation)
    ScrollView scrollView;
    @BindView(R.id.radio_group_type_calculation)
    RadioGroup groupTypeCalculation;
    @BindView(R.id.text_view_final_interest_payment_feeC)
    TextView finalInteresValue;
    @BindView(R.id.text_view_final_payment_feeC)
    TextView finalPayment;

    private EntityRateConvert rateEntity;
    private ArrayList<EntityPayment> payments;
    private EntityPayment initialData;
    private PaymentsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private WeakReference<Context> contextWeakReference;

    private FeeCalculationPresenter presenter;
    private int selectedCalculation;
    private boolean flagShowOptions;
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
        selectedCalculation = R.id.radio_button_reduction_due;
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
        groupTypeCalculation.setOnCheckedChangeListener((radioGroup, id) -> {
            selectedCalculation = id;
            if (id == R.id.radio_button_reduction_mix) {
                updateAdapter(true);
                flagShowOptions = true;
            } else {
                updateAdapter(false);
                flagShowOptions = false;
            }
        });
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
    private void updateAdapter(boolean showOptions) {
        mAdapter.setShowOptions(showOptions);
        mAdapter.notifyDataSetChanged();
    }

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
        mAdapter.setShowOptions(flagShowOptions);
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

    private boolean getPayments() {
        payments = new ArrayList<EntityPayment>();
        try {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                PaymentsAdapter.ViewHolderPayment viewHolder = (PaymentsAdapter.ViewHolderPayment) recyclerPayments.findViewHolderForAdapterPosition(i);
                EntityPayment payment = new EntityPayment();
                if (viewHolder != null && viewHolder.amount.getEditText() != null && viewHolder.numberPeriod.getEditText() != null) {
                    payment.setAmount(Float.parseFloat(viewHolder.amount.getEditText().getText().toString()));
                    payment.setPeriodo(Integer.parseInt(viewHolder.numberPeriod.getEditText().getText().toString()));

                    int selectedID = viewHolder.radioGroup.getCheckedRadioButtonId();
                    if (selectedID != -1) {
                        if (selectedID == R.id.radio_buttom_cuota) {
                            payment.setTypeCuota(true);
                        } else if (selectedID == R.id.radio_buttom_tiempo) {
                            payment.setTypeTiempo(true);
                        }
                    }
                    payments.add(payment);
                }
            }
            Collections.sort(payments, (entityPayment, t1) -> entityPayment.getPeriodo() - t1.getPeriodo());
        } catch (Exception e) {
            Messages messages = new Messages("Alerta", "Verifique que los campos de los pagos extraordinarios no esten vacios");
            messages.showAlert(contextWeakReference);
            return false;
        }
        return true;
    }

    private void calculate() {
        boolean flag = getPayments();
        if (!flag) return;
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
                            if (selectedCalculation == R.id.radio_button_reduction_due)
                                presenter.setTypeCalculation(ConventionsFee.DueRedcution);
                            else if (selectedCalculation == R.id.radio_button_reduction_time)
                                presenter.setTypeCalculation(ConventionsFee.TimeReduction);
                            else presenter.setTypeCalculation(ConventionsFee.MixReduction);
                            presenter.calculateButtonClicked();
                            showResult(presenter.getResult());
                        });
                    }
                });
            } else {
                if (selectedCalculation == R.id.radio_button_reduction_due)
                    presenter.setTypeCalculation(ConventionsFee.DueRedcution);
                else if (selectedCalculation == R.id.radio_button_reduction_time)
                    presenter.setTypeCalculation(ConventionsFee.TimeReduction);
                else presenter.setTypeCalculation(ConventionsFee.MixReduction);
                presenter.calculateButtonClicked();
                showResult(presenter.getResult());
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

    private void moveScrollDown() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
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
    public void showResult(EntityProcessFeeCalculation result) {
        Messages messages = new Messages("Cargando", "Espere un momento por favor...");
        messages.showLoading(contextWeakReference);
        if (layoutResult.getChildAt(1) != null) layoutResult.removeViewAt(1);
        cardViewResult.setVisibility(View.VISIBLE);
        DynamicTable tableResult = new DynamicTable(getContext());
        tableResult.setResult(result);
        tableResult.initComponents();
        layoutResult.addView(tableResult);

        Locale locale = new Locale("es", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        finalPayment.setText(currencyFormatter.format(result.getFinalAmountToPay()));
        finalInteresValue.setText(currencyFormatter.format(result.getValueInterests()));
        moveScrollDown();
        messages.hideLoading();
    }

    @Override
    public void restoreFields() {

    }
    //endregion
}
