package com.example.economyapp.rate_converter;

public interface RateConverterRepository {
    float calculateRate(EntityRateConvert initialRate, EntityRateConvert finalRate);
}
