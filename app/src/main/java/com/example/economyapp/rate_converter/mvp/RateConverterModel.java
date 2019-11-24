package com.example.economyapp.rate_converter.mvp;

import com.example.economyapp.rate_converter.entities.EntityRateConvert;
import com.example.economyapp.rate_converter.mvp.contracts.RateConverterMVP;
import com.example.economyapp.rate_converter.mvp.contracts.RateConverterRepository;

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
