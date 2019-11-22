package com.example.economyapp.FeeCalculation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economyapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolderPayment> {
    private List<EntityPayment> payments;

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
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    class ViewHolderPayment extends RecyclerView.ViewHolder {
        ImageButton deleteButton;
        TextInputLayout numberPeriod;
        TextInputLayout amount;

        ViewHolderPayment(@NonNull View itemView) {
            super(itemView);
            deleteButton = itemView.findViewById(R.id.image_btn_delete_payment_item_fee_calculation);
            numberPeriod = itemView.findViewById(R.id.text_input_number_period_fee_calculation);
            amount = itemView.findViewById(R.id.text_input_amount_fee_calculation);
        }

        void deleteItem(int pos) {
            if (payments.size() > 0) {
                payments.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }
}
