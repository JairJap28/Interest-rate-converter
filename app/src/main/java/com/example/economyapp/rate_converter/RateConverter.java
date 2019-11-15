package com.example.economyapp.rate_converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.economyapp.Base.FragmentBase;
import com.example.economyapp.MainActivity;
import com.example.economyapp.R;

import butterknife.BindView;

public class RateConverter extends FragmentBase implements RateConverterMVP.View, AdapterView.OnItemSelectedListener {
    //region Properties
    @BindView(R.id.initial_period)
    Spinner spinnerInitialPeriod;

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
    @BindView(R.id.input_rate)
    EditText inputRate;
    @BindView(R.id.spinner_final_period)
    Spinner spinnerFinalPeriod;
    @BindView(R.id.spinner_initial_nominal_efectiva)
    Spinner spinnerNominalEfectiva;
    @BindView(R.id.spinner_initial_vencida_anticipada)
    Spinner spinnerVencidaAnticipada;
    @BindView(R.id.spinner_final_nominal_efectiva)
    Spinner spinnerFinalNominalEfectiva;
    @BindView(R.id.spinner_final_vencida_anticipada)
    Spinner spinnerFinalVencidaAnticipada;
    @BindView(R.id.textView_text_result)
    TextView textViewResult;
    @BindView(R.id.text_view_value_result)
    TextView valueViewResult;

    private RateConverterPresenter presenter;
    private EntityRateConvert initialRateEntity;
    private EntityRateConvert finalRateEntity;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialRateEntity = new EntityRateConvert();
        finalRateEntity = new EntityRateConvert();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        switch (parent.getId()) {
            case R.id.initial_period:
                initialRateEntity.setPeriod(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_initial_nominal_efectiva:
                initialRateEntity.setNominalEfectiva(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_initial_vencida_anticipada:
                initialRateEntity.setVencidaAnticipada(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_final_period:
                finalRateEntity.setPeriod(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_final_nominal_efectiva:
                finalRateEntity.setNominalEfectiva(parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinner_final_vencida_anticipada:
                finalRateEntity.setVencidaAnticipada(parent.getItemAtPosition(pos).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //endregion

    //region Override Base Methods
    @Override
    public void initializeUI() {
        super.initilize();
        presenter = new RateConverterPresenter();
        initializeSpinners();
        imgShowCardInitialRate.setOnClickListener(view -> showCardsRate());
        imgShowCardFinalRate.setOnClickListener(view -> showCardsRate());
        calculateRate.setOnClickListener(view -> calculateAndHideCards());
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

    @Override
    public void initializeSpinners() {
        spinnerInitialPeriod.setAdapter(getPeriods());
        spinnerInitialPeriod.setOnItemSelectedListener(this);
        spinnerNominalEfectiva.setAdapter(getNominalEfectiva());
        spinnerNominalEfectiva.setOnItemSelectedListener(this);
        spinnerVencidaAnticipada.setAdapter(getVencidaAnticipada());
        spinnerVencidaAnticipada.setOnItemSelectedListener(this);
        spinnerFinalPeriod.setAdapter(getPeriods());
        spinnerFinalPeriod.setOnItemSelectedListener(this);
        spinnerFinalNominalEfectiva.setAdapter(getNominalEfectiva());
        spinnerFinalNominalEfectiva.setOnItemSelectedListener(this);
        spinnerFinalVencidaAnticipada.setAdapter(getVencidaAnticipada());
        spinnerFinalVencidaAnticipada.setOnItemSelectedListener(this);
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

    private void calculateAndHideCards(){
        hideCardsRate();
        presenter.setInitialRate(getInitialRate());
        presenter.setFinalRate(getFinalRate());
        showResult(presenter.calculateButtonClicked() * 100);
    }
    //endregion

    //region Contract RateConverterMVP
    @Override
    public ArrayAdapter<CharSequence> getPeriods() {
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
    public EntityRateConvert getInitialRate() {
        initialRateEntity.setRate(Float.parseFloat(inputRate.getText().toString()));
        return initialRateEntity;
    }

    @Override
    public EntityRateConvert getFinalRate() {
        finalRateEntity.setRate(Float.parseFloat(inputRate.getText().toString()));
        return finalRateEntity;
    }

    @Override
    public void showResult(float value) {
        textViewResult.setText(String.format(getString(R.string.textResult),
                Float.parseFloat(inputRate.getText().toString()) * 100 + " %",
                initialRateEntity.getNominalEfectiva(), initialRateEntity.getVencidaAnticipada(),
                finalRateEntity.getNominalEfectiva(), finalRateEntity.getVencidaAnticipada()));
        valueViewResult.setText((value + " %"));
    }

    @Override
    public void restoreFields() {
        inputRate.setText("0.0");
        initializeSpinners();
        showCardsRate();
    }
    //endregion
}
