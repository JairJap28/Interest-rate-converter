package com.example.economyapp.FeeCalculation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economyapp.FeeCalculation.entities.EntityPayment;
import com.example.economyapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolderPayment> {
    //region Properties
    private List<EntityPayment> payments;
    private boolean showOptions;

    public void setShowOptions(boolean showOptions) {
        this.showOptions = showOptions;
    }
    //endregion

    PaymentsAdapter(List<EntityPayment> payments) {
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolderPayment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_payments, parent, false);
        return new ViewHolderPayment(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPayment holder, int position) {
        holder.deleteButton.setOnClickListener(view -> holder.deleteItem(position));
        holder.radioGroup.setVisibility(showOptions ? View.VISIBLE : View.GONE);
        if (!showOptions) holder.radioButtonCuota.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    class ViewHolderPayment extends RecyclerView.ViewHolder {
        ImageButton deleteButton;
        TextInputLayout numberPeriod;
        TextInputLayout amount;
        RadioGroup radioGroup;
        RadioButton radioButtonCuota;

        ViewHolderPayment(@NonNull View itemView) {
            super(itemView);
            deleteButton = itemView.findViewById(R.id.image_btn_delete_payment_item_fee_calculation);
            numberPeriod = itemView.findViewById(R.id.text_input_number_period_fee_calculation);
            amount = itemView.findViewById(R.id.text_input_amount_fee_calculation);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioButtonCuota = itemView.findViewById(R.id.radio_buttom_cuota);
        }

        void deleteItem(int pos) {
            try {
                payments.remove(pos);
                notifyItemRemoved(pos);
            } catch (Exception ignored) {
            }
        }
    }
}
