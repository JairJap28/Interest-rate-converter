package com.example.economyapp.rate_converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.List;

import butterknife.BindView;

public class RateConverter extends FragmentBase implements RateConverterMVP.View {
    //region Properties
    RateConverterPresenter presenter;

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
        presenter = new RateConverterPresenter();
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
        presenter.calculateButtonClicked();
    }

    private void restoreFields(){
        Toast.makeText(getActivity(), "Restoring Fields", Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region Contract RateConverterMVP
    @Override
    public List<String> getPeriods() {
        return null;
    }

    @Override
    public List<String> getNominalEfectiva() {
        return null;
    }

    @Override
    public List<String> getVencidaAnticipada() {
        return null;
    }

    @Override
    public EntityRateConvert getInitialRate() {
        EntityRateConvert entityRateConvert = new EntityRateConvert();
        entityRateConvert.setRate(Float.parseFloat(inputRate.getText().toString()));
        return entityRateConvert;
    }

    @Override
    public EntityRateConvert getFinalRate() {
        EntityRateConvert entityRateConvert = new EntityRateConvert();
        entityRateConvert.setRate(Float.parseFloat(inputRate.getText().toString()));
        return entityRateConvert;
    }

    @Override
    public void showResult(float value) {

    }
    //endregion

    //region Butterkinfe
    //endregion
}
