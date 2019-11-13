package com.example.economyapp.rate_converter;

public class ConverterRepository implements RateConverterRepository {
    @Override
    public float calculateRate(EntityRateConvert initialRate, EntityRateConvert finalRate) {
        return new Converter(initialRate, finalRate).getResult();
    }
}
