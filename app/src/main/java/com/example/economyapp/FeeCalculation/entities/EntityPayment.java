package com.example.economyapp.FeeCalculation.entities;

public class EntityPayment {
    private int periodo;
    private double amount;
    private boolean typeCuota;
    private boolean typeTiempo;

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

    public boolean isTypeCuota() {
        return typeCuota;
    }

    public void setTypeCuota(boolean typeCuota) {
        this.typeCuota = typeCuota;
    }

    public boolean isTypeTiempo() {
        return typeTiempo;
    }

    public void setTypeTiempo(boolean typeTiempo) {
        this.typeTiempo = typeTiempo;
    }
}
