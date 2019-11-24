package com.example.economyapp.FeeCalculation.mvp.contracts;

import android.widget.ArrayAdapter;

import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

public interface FeeCalculationMVP {
    interface View {
        ArrayAdapter<CharSequence> getSpinnerPeriods();

        ArrayAdapter<CharSequence> getNominalEfectiva();

        ArrayAdapter<CharSequence> getVencidaAnticipada();

        void showResult(float value);

        void restoreFields();
    }

    interface Presenter {
        float calculateButtonClicked();

        float getResult();
    }

    interface Model {
        EntityProcessFeeCalculation reduceDue(EntityRateConvert interest);

        EntityProcessFeeCalculation reduceTime();
    }
}
