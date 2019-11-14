package com.example.economyapp.FeeCalculation;

import android.widget.ArrayAdapter;

public interface FeeCalculationMVP {
    interface View {
        ArrayAdapter<CharSequence> getSpinnerPeriods();

        ArrayAdapter<CharSequence> getNominalEfectiva();

        ArrayAdapter<CharSequence> getVencidaAnticipada();

        void showResult(float value);

        void restoreFields();
    }
}
