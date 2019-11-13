package com.example.economyapp.rate_converter;

public class RateConverterPresenter implements RateConverterMVP.Presenter {
    //region Properties
    private RateConverterModel rateConverterModel;
    private EntityRateConvert initialRate;
    private EntityRateConvert finalRate;

    RateConverterPresenter() {
        rateConverterModel = new RateConverterModel();
    }

    void setInitialRate(EntityRateConvert initialRate) {
        this.initialRate = initialRate;
    }

    void setFinalRate(EntityRateConvert finalRate) {
        this.finalRate = finalRate;
    }
    //endregion

    @Override
    public void setView(RateConverterMVP.View view) {

    }

    @Override
    public void calculateButtonClicked() {
        getResult();
    }

    @Override
    public float getResult() {
        return rateConverterModel.convertRate(initialRate, finalRate);
    }
}
