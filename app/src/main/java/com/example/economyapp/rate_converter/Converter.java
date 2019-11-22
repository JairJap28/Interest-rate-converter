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
                float auxRate = fromNominalVencidaToEfectivaVencida(false);
                initialRate.setRate(auxRate);
                auxRate = changeTime(auxRate);
                initialRate.setRate(auxRate);
                return fromEfectivaVencidatoNominalVencida();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = fromNominalVencidaToEfectivaVencida(false);
                initialRate.setRate(auxRate);
                auxRate = changeTime(initialRate.getRate());
                initialRate.setRate(auxRate);
                return changeTime(initialRate.getRate());
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromNominalVencidaToEfectivaVencida(false);
                initialRate.setRate(auxInteres);
                auxInteres = changeTime(initialRate.getRate());
                initialRate.setRate(auxInteres);
                auxInteres = fromEfectivaVencidaToEfectivaAnticipada();
                initialRate.setRate(auxInteres);
                return fromEfectivaAnticipadaToNominalAnticipada();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromNominalVencidaToEfectivaVencida(false);
                initialRate.setRate(auxInteres);
                auxInteres = changeTime(initialRate.getRate());
                initialRate.setRate(auxInteres);
                return fromEfectivaVencidaToEfectivaAnticipada();
            }
        } else if (typesInitial.first.equals(Conventions.EFECTIVA) && typesInitial.second.equals(Conventions.VENCIDA)) {
            if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = changeTime(initialRate.getRate());
                initialRate.setRate(auxRate);
                return fromEfectivaVencidatoNominalVencida();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                return changeTime(initialRate.getRate());
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = changeTime(initialRate.getRate());
                initialRate.setRate(auxInteres);
                auxInteres = fromEfectivaVencidaToEfectivaAnticipada();
                initialRate.setRate(auxInteres);
                return fromEfectivaAnticipadaToNominalAnticipada();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = changeTime(initialRate.getRate());
                initialRate.setRate(auxInteres);
                return fromEfectivaVencidaToEfectivaAnticipada();
            }
        } else if (typesInitial.first.equals(Conventions.NOMINAL) && typesInitial.second.equals(Conventions.ANTICIPADA)) {
            if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = fromNominalAnticipadaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                auxRate = changeTime(auxRate);
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaVencidaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                return fromEfectivaAnticipadaToNominalAnticipada();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = fromNominalAnticipadaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                return changeTime(auxRate);
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxRate = fromNominalAnticipadaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                auxRate = changeTime(auxRate);
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaVencidaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                return fromEfectivaVencidatoNominalVencida();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxRate = fromNominalAnticipadaToEfectivaAnticipada();
                initialRate.setRate(auxRate);
                auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                auxRate = changeTime(auxRate);
                initialRate.setRate(auxRate);
                return fromEfectivaVencidaToEfectivaAnticipada();
            }
        } else if (typesInitial.first.equals(Conventions.EFECTIVA) && typesInitial.second.equals(Conventions.ANTICIPADA)) {
            if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                auxRate = changeTime(auxRate);
                initialRate.setRate(auxRate);
                return fromEfectivaVencidatoNominalVencida();
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.VENCIDA)) {
                float auxRate = fromEfectivaActicipadaToEfectivaVencida();
                initialRate.setRate(auxRate);
                return changeTime(auxRate);
            } else if (typesFinal.first.equals(Conventions.NOMINAL) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                float auxInteres = fromEfectivaAnticipadaToNominalAnticipada();
                initialRate.setRate(auxInteres);
                return changeTime(auxInteres);
            } else if (typesFinal.first.equals(Conventions.EFECTIVA) && typesFinal.second.equals(Conventions.ANTICIPADA)) {
                return changeTime(initialRate.getRate());
            }
        }
        return 0;
    }

    private float fromNominalAnticipadaToEfectivaAnticipada() {
        return initialRate.getRate() / getNumericalPeriods(initialRate.getPeriod());
    }

    private float fromEfectivaActicipadaToEfectivaVencida() {
        return initialRate.getRate() / (1 - initialRate.getRate());
    }

    private float fromEfectivaVencidaToEfectivaAnticipada() {
        return initialRate.getRate() / (1 + initialRate.getRate());
    }

    private float fromEfectivaAnticipadaToNominalAnticipada() {
        return initialRate.getRate() * getNumericalPeriods(finalRate.getPeriod());
    }

    private float fromEfectivaVencidatoNominalVencida() {
        return initialRate.getRate() * getNumericalPeriods(finalRate.getPeriod());
    }

    private float fromNominalVencidaToEfectivaVencida(boolean changeTime) {
        return initialRate.getRate() / getNumericalPeriods(initialRate.getPeriod());
    }

    private float changeTime(float newRate) {
        double inisedeSqrt = Math.pow((1 + newRate), getNumericalPeriods(initialRate.getPeriod()));
        return ((float) Math.pow(inisedeSqrt, 1.0 / getNumericalPeriods(finalRate.getPeriod()))) - 1;
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

    private int getNumericalPeriods(String period) {
        if (period.equalsIgnoreCase("Mensual")) {
            return 12;
        } else if (period.equalsIgnoreCase("Bimestral")) {
            return 6;
        } else if (period.equalsIgnoreCase("Trimestral")) {
            return 4;
        } else if (period.equalsIgnoreCase("Semestral")) {
            return 2;
        } else if (period.equalsIgnoreCase("Anual")) {
            return 1;
        } else return Integer.parseInt(period);
    }
    //endregion
}
