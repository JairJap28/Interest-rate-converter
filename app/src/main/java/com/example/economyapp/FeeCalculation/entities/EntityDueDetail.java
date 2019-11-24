package com.example.economyapp.FeeCalculation.entities;

public class EntityDueDetail {
    private float valueDue;
    private float presentValue;
    private float nPeriod;

    public float getValueDue() {
        return valueDue;
    }

    public void setValueDue(float valueDue) {
        this.valueDue = valueDue;
    }

    public float getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(float presentValue) {
        this.presentValue = presentValue;
    }

    public float getnPeriod() {
        return nPeriod;
    }

    public void setnPeriod(float nPeriod) {
        this.nPeriod = nPeriod;
    }
}
