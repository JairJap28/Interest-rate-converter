package com.example.economyapp.FeeCalculation.entities;

public class EntityPayment {
    private int periodo;
    private double amount;

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
