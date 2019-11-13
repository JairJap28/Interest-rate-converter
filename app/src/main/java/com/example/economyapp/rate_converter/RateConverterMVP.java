package com.example.economyapp.rate_converter;

import java.util.List;

public interface RateConverterMVP {
    interface View {
        List<String> getPeriods();

        List<String> getNominalEfectiva();

        List<String> getVencidaAnticipada();

        EntityRateConvert getInitialRate();

        EntityRateConvert getFinalRate();

        void showResult(float value);
    }

    interface Presenter {
        void setView(View view);
        void calculateButtonClicked();
        float getResult();
    }

    interface Model {
        float convertRate(EntityRateConvert initialRate, EntityRateConvert finalRate);
    }
}
