package com.example.economyapp.rate_converter;

public class EntityRateConvert {
    //region Properties
    private float rate;
    private String period;
    private String nominalEfectiva;
    private String vencidaAnticipada;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNominalEfectiva() {
        return nominalEfectiva;
    }

    public void setNominalEfectiva(String nominalVencida) {
        this.nominalEfectiva = nominalVencida;
    }

    public String getVencidaAnticipada() {
        return vencidaAnticipada;
    }

    public void setVencidaAnticipada(String vencidaAnticipada) {
        this.vencidaAnticipada = vencidaAnticipada;
    }

    //endregion
}
