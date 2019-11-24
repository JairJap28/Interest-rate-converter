package com.example.economyapp.FeeCalculation.mvp;

import com.example.economyapp.FeeCalculation.ConventionsFee;
import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.FeeCalculation.mvp.contracts.FeeCalculationMVP;
import com.example.economyapp.rate_converter.entities.EntityRateConvert;

import java.util.ArrayList;

public class FeeCalculationPresenter implements FeeCalculationMVP.Presenter {
    //region Properties
    private FeeCalculationModel feeCalculationModel;
    private ConventionsFee typeCalculation;
    private EntityRateConvert interest;
    private EntityPayment initialData;
    private ArrayList<EntityPayment> payments;

    //region Constructor
    public FeeCalculationPresenter() {
        feeCalculationModel = new FeeCalculationModel();
    }

    public void setInterest(EntityRateConvert interest) {
        this.interest = interest;
    }

    public void setInitialData(EntityPayment initialData) {
        this.initialData = initialData;
    }

    public void setPayments(ArrayList<EntityPayment> payments) {
        this.payments = payments;
    }
    //endregion

    public void setTypeCalculation(ConventionsFee typeCalculation) {
        this.typeCalculation = typeCalculation;
    }
    //endregion

    @Override
    public float calculateButtonClicked() {
        if (typeCalculation == ConventionsFee.DueRedcution) {
            feeCalculationModel.setAmount((float) initialData.getAmount());
            feeCalculationModel.setnPeriods(initialData.getPeriodo());
            feeCalculationModel.setListPayments(payments);
            EntityProcessFeeCalculation out = feeCalculationModel.reduceDue(interest);
        }
        return 0;
    }

    @Override
    public float getResult() {
        return 0;
    }
}
