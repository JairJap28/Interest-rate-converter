package com.example.economyapp.rate_converter;

public class RateConverterModel implements RateConverterMVP.Model {

    private RateConverterRepository repository;

    RateConverterModel() {
        this.repository = new ConverterRepository();
    }

    @Override
    public float convertRate(EntityRateConvert initialRate, EntityRateConvert finalRate) {
        return repository.calculateRate(initialRate, finalRate);
    }
}
