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
    float auxOriginal;

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
        auxOriginal = amount;
    }
    //endregion

    //region Class Methods
    public EntityProcessFeeCalculation processReduction() {
        duesList = new ArrayList<>();
        previousPeriods = nPeriods;
        float auxPreviousPeriod = 0;
        for (int i = 0; i <= listPayments.size(); i++) {
            EntityDueDetail due = new EntityDueDetail();
            due.setPresentValue(amount);
            due.setValueDue(processDueBasedOnInterest(interest, amount, previousPeriods));

            if (i < listPayments.size()) {
                EntityPayment pay = listPayments.get(i);
                due.setnPeriod(pay.getPeriodo());
                due.setOriginalPayment((float) pay.getAmount());
                amount = processPresentValue(interest, due.getValueDue(), (nPeriods - pay.getPeriodo() + 1));
                pay.setAmount(ajustExtraordinaryPayment(pay.getAmount(), getInterestToNextDue(amount, interest.getRate())));
                amount = amount - (float) pay.getAmount();
                previousPeriods = nPeriods - pay.getPeriodo();
            } else {
                due.setnPeriod(nPeriods);
            }
            duesList.add(due);
        }
        return fetchData();
    }

    public EntityProcessFeeCalculation processTimeReduction() {
        duesList = new ArrayList<>();
        previousPeriods = nPeriods;
        float valueDue = 0f;
        float auxPreviousPeriod = 0;
        for (int i = 0; i <= listPayments.size(); i++) {
            EntityDueDetail due = new EntityDueDetail();
            due.setPresentValue(amount);
            if (i == 0) {
                valueDue = processDueBasedOnInterest(interest, amount, previousPeriods);
            }
            due.setValueDue(valueDue);

            if (i < listPayments.size()) {
                EntityPayment pay = listPayments.get(i);
                due.setnPeriod(pay.getPeriodo());
                due.setOriginalPayment((float) pay.getAmount());
                amount = processPresentValue(interest, valueDue, (previousPeriods - pay.getPeriodo() + 1));
                pay.setAmount(ajustExtraordinaryPayment(pay.getAmount(), getInterestToNextDue(amount, interest.getRate())));
                amount = amount - (float) pay.getAmount();
                previousPeriods = getNewPeriod(interest, amount, valueDue) + pay.getPeriodo();
            } else {
                due.setnPeriod(previousPeriods);
            }
            duesList.add(due);
        }
        return fetchData();
    }

    private EntityProcessFeeCalculation calculateInterests(EntityProcessFeeCalculation result) {
        float finalValue = 0;
        float starterPeriod = 0;
        finalValue += result.getDueDetails().get(0).getValueDue() * 2;
        for (int i = 0; i < result.getDueDetails().size(); i++) {
            EntityDueDetail item = result.getDueDetails().get(i);
            if (i + 1 >= result.getDueDetails().size()) {
                starterPeriod = Math.abs(item.getnPeriod() + 1) - starterPeriod;
            } else {
                starterPeriod = Math.abs(item.getnPeriod() - 1) - starterPeriod;
            }
            finalValue += item.getValueDue() * (starterPeriod);
            finalValue += item.getOriginalPayment();
            starterPeriod = item.getnPeriod() + 1;
        }
        result.setValueInterests(finalValue - auxOriginal);
        result.setFinalAmountToPay(finalValue);
        return result;
    }

    public EntityProcessFeeCalculation processMixReduction() {
        duesList = new ArrayList<>();
        previousPeriods = nPeriods;
        float valueDue = 0f;
        float auxPreviousPerids = 0;
        for (int i = 0; i <= listPayments.size(); i++) {
            EntityDueDetail due = new EntityDueDetail();
            due.setnPeriod(previousPeriods);
            due.setPresentValue(amount);
            if (i == 0) {
                valueDue = processDueBasedOnInterest(interest, amount, previousPeriods);
                due.setValueDue(valueDue);
            } else due.setValueDue(valueDue);

            if (i < listPayments.size()) {
                EntityPayment pay = listPayments.get(i);
                due.setOriginalPayment((float) pay.getAmount());
                amount = processPresentValue(interest, valueDue, (previousPeriods - pay.getPeriodo() + 1));
                pay.setAmount(ajustExtraordinaryPayment(pay.getAmount(), getInterestToNextDue(amount, interest.getRate())));
                amount = amount - (float) pay.getAmount();
                auxPreviousPerids = previousPeriods;
                previousPeriods = getNewPeriod(interest, amount, valueDue) + pay.getPeriodo();
                due.setnPeriod(pay.getPeriodo());

                try {
                    if (listPayments.get(i).isTypeCuota()) {
                        valueDue = processDueBasedOnInterest(interest, amount, auxPreviousPerids - pay.getPeriodo());
                    }
                } catch (Exception ignored) {

                }
            } else {
                due.setnPeriod(auxPreviousPerids);
            }
            duesList.add(due);
        }
        return fetchData();
    }

    private EntityProcessFeeCalculation fetchData() {
        EntityProcessFeeCalculation entityProcessFeeCalculation = new EntityProcessFeeCalculation();
        entityProcessFeeCalculation.setFinalPeriod(nPeriods);
        entityProcessFeeCalculation.setDueDetails(duesList);
        entityProcessFeeCalculation.setFinalAmountToPay(amount);

        EntityProcessFeeCalculation aux = calculateInterests(entityProcessFeeCalculation);

        entityProcessFeeCalculation.setFinalAmountToPay(aux.getFinalAmountToPay());
        entityProcessFeeCalculation.setValueInterests(aux.getValueInterests());
        return entityProcessFeeCalculation;
    }

    private float getInterestToNextDue(double presentValue, float interest) {
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

    private float getNewPeriod(EntityRateConvert interest, float amout, float due) {
        float auxUp = (float) Math.log(-(((amout * interest.getRate()) / (due)) - 1));
        float auxDown = (-1) * (float) Math.log(1 + interest.getRate());
        return auxUp / auxDown;
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
