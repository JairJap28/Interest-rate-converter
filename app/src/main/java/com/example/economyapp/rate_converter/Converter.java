package com.example.economyapp.rate_converter;

import android.util.Pair;

public class Converter {
    //region Properties
    private EntityRateConvert initialRate;
    private EntityRateConvert finalRate;
    private float result;

    //region Constructor
    public Converter(EntityRateConvert initialRate, EntityRateConvert finalRate) {
        this.initialRate = initialRate;
        this.finalRate = finalRate;
    }

    public EntityRateConvert getInitialRate() {
        return initialRate;
    }

    public void setInitialRate(EntityRateConvert initialRate) {
        this.initialRate = initialRate;
    }

    public EntityRateConvert getFinalRate() {
        return finalRate;
    }

    public void setFinalRate(EntityRateConvert finalRate) {
        this.finalRate = finalRate;
    }

    public float getResult() {
        return result;
    }
    //endregion

    public void setResult(float result) {
        this.result = result;
    }
    //endregion

    //region Class Methods
    private float process() {
        return 0;
    }

    private float processDependingOnTypes(Pair<Conventions, Conventions> typesInitial, Pair<Conventions, Conventions> typesFinal) {
        if (typesInitial.first.equals(Conventions.NOMINAL) && typesInitial.second.equals(Conventions.VENCIDA)) {
            if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.VENCIDA)) {
                return initialRate.getRate();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                return fromNominalVencidaToEfectivaVencida();
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromNominalVencidaToEfectivaVencida();
                auxInteres = changeTime(); //TODO create equation
                auxInteres = fromEfectivaVencidaToEfectivaAnticipada();
                return fromEfectivaAnticipadaToNominalAnticipada();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {

            }
        } else if (typesInitial.first.equals(Conventions.EFECTIVA) && typesInitial.second.equals(Conventions.VENCIDA)) {

        } else if (typesInitial.first.equals(Conventions.NOMINAL) && typesInitial.second.equals(Conventions.ANTICIPADA)) {

        } else if (typesInitial.first.equals(Conventions.EFECTIVA) && typesInitial.second.equals(Conventions.ANTICIPADA)) {

        }
        return 0;
    }

    private float fromNominalAnticipadaToEfectivaAnticipada() {
        return 0;
    }

    private float fromEfectivaActicipadaToEfectivaVencida() {
        return 0;
    }

    private float fromEfectivaVencidaToEfectivaAnticipada() {
        return (result) / (1 + result);
    }

    private float fromEfectivaAnticipadaToNominalAnticipada() {
        return 0;
    }

    private float fromEfectivaVencidatoNominalVencida() {
        return 0;
    }

    private float fromNominalVencidaToEfectivaVencida() {
        float auxInteres = initialRate.getRate() / Integer.parseInt(initialRate.getPeriod());
        auxInteres = validteTime() ? auxInteres : changeTime();
        return auxInteres;
    }

    private float changeTime() {
        return 0;
    }

    private boolean validteTime() {
        return initialRate.getPeriod().equals(finalRate.getPeriod());
    }

    private Conventions getTypeNominalEfectiva(EntityRateConvert rate) {
        if ("EFECTIVA".equalsIgnoreCase(rate.getNominalEfectiva())) {
            return Conventions.EFECTIVA;
        } else {
            return Conventions.NOMINAL;
        }
    }

    private Conventions getTypeVencidaAnticipada(EntityRateConvert rate) {
        if ("ANTICIPADA".equalsIgnoreCase(rate.getVencidaAnticipada())) {
            return Conventions.ANTICIPADA;
        } else {
            return Conventions.VENCIDA;
        }
    }
    //endregion
}
