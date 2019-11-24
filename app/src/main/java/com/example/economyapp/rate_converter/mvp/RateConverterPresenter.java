package com.example.economyapp.rate_converter.mvp;

import com.example.economyapp.rate_converter.entities.EntityRateConvert;
import com.example.economyapp.rate_converter.mvp.contracts.RateConverterMVP;

public class RateConverterPresenter implements RateConverterMVP.Presenter {
    //region Properties
    private RateConverterModel rateConverterModel;
    private EntityRateConvert initialRate;
    private EntityRateConvert finalRate;

    public RateConverterPresenter() {
        rateConverterModel = new RateConverterModel();
    }

    public void setInitialRate(EntityRateConvert initialRate) {
        this.initialRate = initialRate;
    }

    public void setFinalRate(EntityRateConvert finalRate) {
        this.finalRate = finalRate;
    }
    //endregion

    @Override
    public void setView(RateConverterMVP.View view) {

    }

    @Override
    public float calculateButtonClicked() {
        return getResult();
    }

    @Override
    public float getResult() {
        return rateConverterModel.convertRate(initialRate, finalRate);
    }
}
