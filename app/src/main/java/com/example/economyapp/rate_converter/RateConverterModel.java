package com.example.economyapp.rate_converter;

public class RateConverterModel implements RateConverterMVP.Model {

    private RateConverterRepository repository;

    public RateConverterModel(RateConverterRepository repository) {
        this.repository = repository;
    }

    @Override
    public float convertRate(EntityRateConvert initialRate, EntityRateConvert finalRate) {
        return repository.calculateRate(initialRate, finalRate);
    }
}
