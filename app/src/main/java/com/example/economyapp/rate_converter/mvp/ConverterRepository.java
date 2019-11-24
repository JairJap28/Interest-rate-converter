package com.example.economyapp.rate_converter.mvp;

import com.example.economyapp.rate_converter.Converter;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;
import com.example.economyapp.rate_converter.mvp.contracts.RateConverterRepository;

public class ConverterRepository implements RateConverterRepository {
    @Override
    public float calculateRate(EntityRateConvert initialRate, EntityRateConvert finalRate) {
        return new Converter(initialRate, finalRate).getResult();
    }
}
