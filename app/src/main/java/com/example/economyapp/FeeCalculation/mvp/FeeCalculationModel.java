package com.example.economyapp.FeeCalculation.mvp;

import com.example.economyapp.FeeCalculation.ReduceDue;
import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.FeeCalculation.mvp.contracts.FeeCalculationMVP;
import com.example.economyapp.rate_converter.Converter;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

import java.util.ArrayList;

public class FeeCalculationModel implements FeeCalculationMVP.Model {
    //region Properties
    private float nPeriods;
    private float amount;
    private EntityRateConvert interest;
    private ArrayList<EntityPayment> listPayments;

    public void setnPeriods(float nPeriods) {
        this.nPeriods = nPeriods;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setInterest(EntityRateConvert interest) {
        this.interest = interest;
    }

    public void setListPayments(ArrayList<EntityPayment> listPayments) {
        this.listPayments = listPayments;
    }

    //endregion

    @Override
    public EntityProcessFeeCalculation reduceDue(EntityRateConvert interest) {
        ReduceDue reduceDue = new ReduceDue(nPeriods, convertInterest(interest), listPayments);
        reduceDue.setAmount(amount);
        return reduceDue.processReduction();
    }

    @Override
    public EntityProcessFeeCalculation reduceTime(EntityRateConvert interest) {
        ReduceDue reduceDue = new ReduceDue(nPeriods, convertInterest(interest), listPayments);
        reduceDue.setAmount(amount);
        return reduceDue.processTimeReduction();
    }

    @Override
    public EntityProcessFeeCalculation reduceBoth(EntityRateConvert interest) {
        ReduceDue reduceDue = new ReduceDue(nPeriods, convertInterest(interest), listPayments);
        reduceDue.setAmount(amount);
        return reduceDue.processMixReduction();
    }

    //region Class Methods
    private EntityRateConvert convertInterest(EntityRateConvert interest) {
        EntityRateConvert convertedInterest = new EntityRateConvert();
        convertedInterest.setPeriod("Mensual");
        if (interest.getNominalEfectiva() != null) convertedInterest.setNominalEfectiva("Efectiva");
        if (interest.getVencidaAnticipada() != null)
            convertedInterest.setVencidaAnticipada(interest.getVencidaAnticipada());

        Converter converter = new Converter(interest, convertedInterest);
        convertedInterest.setRate(converter.getResult());
        return convertedInterest;
    }
    //endregion
}
