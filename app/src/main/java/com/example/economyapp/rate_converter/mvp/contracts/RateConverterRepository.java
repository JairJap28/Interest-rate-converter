package com.example.economyapp.rate_converter.mvp.contracts;

import com.example.economyapp.rate_converter.entities.EntityRateConvert;

public interface RateConverterRepository {
    float calculateRate(EntityRateConvert initialRate, EntityRateConvert finalRate);
}
