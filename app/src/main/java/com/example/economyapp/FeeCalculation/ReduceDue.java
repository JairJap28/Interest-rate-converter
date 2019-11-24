package com.example.economyapp.FeeCalculation;

import com.example.economyapp.FeeCalculation.entities.EntityDueDetail;
import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.rate_converter.Converter;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

import java.util.ArrayList;

public class ReduceDue {
    //region Properties
    private float nPeriods;
    private float previousPeriods;
    private float amount;
    private EntityRateConvert interest;
    private ArrayList<EntityPayment> listPayments;

    private ArrayList<EntityDueDetail> duesList;
    private Converter converter;

    //region Constructor
    public ReduceDue(float nPeriods, EntityRateConvert interest, ArrayList<EntityPayment> listPayments) {
        this.nPeriods = nPeriods;
        this.interest = interest;
        this.listPayments = listPayments;
        converter = new Converter();
    }

    public float getnPeriods() {
        return nPeriods;
    }

    public void setnPeriods(float nPeriods) {
        this.nPeriods = nPeriods;
    }

    public EntityRateConvert getInterest() {
        return interest;
    }

    public void setInterest(EntityRateConvert interest) {
        this.interest = interest;
    }

    public ArrayList<EntityPayment> getListPayments() {
        return listPayments;
    }

    public void setListPayments(ArrayList<EntityPayment> listPayments) {
        this.listPayments = listPayments;
    }

    public float getAmount() {
        return amount;
    }
    //endregion

    public void setAmount(float amount) {
        this.amount = amount;
    }
    //endregion

    //region Class Methods
    public EntityProcessFeeCalculation processReduction() {
        duesList = new ArrayList<>();
        previousPeriods = nPeriods;
        for (int i = 0; i <= listPayments.size(); i++) {
            EntityDueDetail due = new EntityDueDetail();
            due.setnPeriod(previousPeriods);
            due.setPresentValue(amount);
            due.setValueDue(processDueBasedOnInterest(interest, amount, previousPeriods));
            duesList.add(due);
            if (i < listPayments.size()) {
                EntityPayment pay = listPayments.get(i);
                amount = processPresentValue(interest, due.getValueDue(), (nPeriods - pay.getPeriodo() + 1));
                pay.setAmount(ajustExtraordinaryPayment(pay.getAmount(), getInterestToNextDue(amount, interest.getRate())));
                amount = amount - (float) pay.getAmount();
                previousPeriods = nPeriods - pay.getPeriodo();
            }
        }
        return fetchData();
    }

    private EntityProcessFeeCalculation fetchData() {
        EntityProcessFeeCalculation entityProcessFeeCalculation = new EntityProcessFeeCalculation();
        entityProcessFeeCalculation.setFinalPeriod(nPeriods);
        entityProcessFeeCalculation.setDueDetails(duesList);
        entityProcessFeeCalculation.setFinalAmountToPay(amount);
        return entityProcessFeeCalculation;
    }

    public float getInterestToNextDue(double presentValue, float interest) {
        return (float) presentValue * interest;
    }

    private float ajustExtraordinaryPayment(double originalAmout, double discount) {
        return (float) originalAmout - (float) discount;
    }

    private float processPresentValue(EntityRateConvert interest, float dueValue, float nPeriods) {
        switch (converter.getTypeVencidaAnticipada(interest)) {
            case ANTICIPADA:
                return getPresentValueTAnticipada(interest.getRate(), nPeriods, dueValue);
            case VENCIDA:
                return getPresentValueTVencida(interest.getRate(), nPeriods, dueValue);
        }
        return 0;
    }

    private float processDueBasedOnInterest(EntityRateConvert interest, float amount, float nPeriods) {
        return getDuePresentValue(interest.getRate(), nPeriods, amount);
    }

    private float getPresentValueTAnticipada(float interes, float nPeriods, float dueValue) {
        float aux = (1 - (float) Math.pow((1 + interes), nPeriods)) / (interes);
        aux = dueValue * aux;
        return (float) Math.pow((1 + interes), (-1) * nPeriods) * aux;
    }

    public float getFutureValueTAnticipada(float interes, float nPeriods, float dueValue) {
        float aux = ((float) Math.pow((1 + interes), nPeriods) - 1) / (interes);
        aux = dueValue * aux;
        return (float) Math.pow((1 + interes), (-1) * nPeriods) * aux;
    }

    private float getPresentValueTVencida(float interes, float nPeriods, float dueValue) {
        float aux = (1 - (float) Math.pow((1 + interes), (-1) * nPeriods)) / (interes);
        return dueValue * aux;
    }

    public float getFutureValueTVencida(float interes, float nPeriods, float dueValue) {
        float aux = ((float) Math.pow((1 + interes), nPeriods) - 1) / (interes);
        return dueValue * aux;
    }

    private float getDuePresentValue(float interes, float nPeriods, float presentValue) {
        float aux = (1 - (float) Math.pow((1 + interes), (-1) * nPeriods)) / (interes);
        return presentValue / aux;
    }

    public float getDueFutureValue(float interes, float nPeriods, float presentValue) {
        float aux = ((float) Math.pow((1 + interes), nPeriods) - 1) / (interes);
        return presentValue / aux;
    }
    //endregion
}
