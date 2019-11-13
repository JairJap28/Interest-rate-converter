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
        result = process();
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
        Pair<Conventions, Conventions> initialTypes = new Pair<>(getTypeNominalEfectiva(initialRate), getTypeVencidaAnticipada(initialRate));
        Pair<Conventions, Conventions> finalTypes = new Pair<>(getTypeNominalEfectiva(finalRate), getTypeVencidaAnticipada(finalRate));
        return processDependingOnTypes(initialTypes, finalTypes);
    }

    private float processDependingOnTypes(Pair<Conventions, Conventions> typesInitial, Pair<Conventions, Conventions> typesFinal) {
        if (typesInitial.first.equals(Conventions.NOMINAL) && typesInitial.second.equals(Conventions.VENCIDA)) {
            if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.VENCIDA)) {
                return changeTime();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                return fromNominalVencidaToEfectivaVencida();
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromNominalVencidaToEfectivaVencida();
                initialRate.setRate(auxInteres);
                auxInteres = changeTime();
                initialRate.setRate(auxInteres);
                auxInteres = fromEfectivaVencidaToEfectivaAnticipada();
                initialRate.setRate(auxInteres);
                return fromEfectivaAnticipadaToNominalAnticipada();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromNominalVencidaToEfectivaVencida();
                initialRate.setRate(auxInteres);
                auxInteres = changeTime();
                initialRate.setRate(auxInteres);
                return fromEfectivaVencidaToEfectivaAnticipada();
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
        return initialRate.getRate() * Integer.parseInt(finalRate.getPeriod());
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
        double inisedeSqrt = Math.pow((1 + initialRate.getRate()), Double.parseDouble(initialRate.getPeriod()));
        return (float) Math.pow(inisedeSqrt, Double.parseDouble(finalRate.getPeriod()));
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
