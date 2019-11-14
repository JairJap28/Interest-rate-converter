package com.example.economyapp.rate_converter;

import android.widget.ArrayAdapter;

public interface RateConverterMVP {
    interface View {
        ArrayAdapter<CharSequence> getPeriods();

        ArrayAdapter<CharSequence> getNominalEfectiva();

        ArrayAdapter<CharSequence> getVencidaAnticipada();

        EntityRateConvert getInitialRate();

        EntityRateConvert getFinalRate();

        void showResult(float value);

        void restoreFields();
    }

    interface Presenter {
        void setView(View view);

        float calculateButtonClicked();
        float getResult();
    }

    interface Model {
        float convertRate(EntityRateConvert initialRate, EntityRateConvert finalRate);
    }
}
