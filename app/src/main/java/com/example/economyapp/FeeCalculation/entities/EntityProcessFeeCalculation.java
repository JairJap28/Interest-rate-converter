package com.example.economyapp.FeeCalculation.entities;

import java.util.List;

public class EntityProcessFeeCalculation {
    private float finalAmountToPay;
    private float valueInterests;
    private float finalPeriod;
    private List<EntityDueDetail> dueDetails;

    public float getFinalAmountToPay() {
        return finalAmountToPay;
    }

    public void setFinalAmountToPay(float finalAmountToPay) {
        this.finalAmountToPay = finalAmountToPay;
    }

    public float getValueInterests() {
        return valueInterests;
    }

    public void setValueInterests(float valueInterests) {
        this.valueInterests = valueInterests;
    }

    public float getFinalPeriod() {
        return finalPeriod;
    }

    public void setFinalPeriod(float finalPeriod) {
        this.finalPeriod = finalPeriod;
    }

    public List<EntityDueDetail> getDueDetails() {
        return dueDetails;
    }

    public void setDueDetails(List<EntityDueDetail> dueDetails) {
        this.dueDetails = dueDetails;
    }
}
