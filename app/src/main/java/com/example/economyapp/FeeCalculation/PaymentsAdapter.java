package com.example.economyapp.FeeCalculation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economyapp.R;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolderPayment> {
    private List<EntityPayment> payments;

    public PaymentsAdapter(List<EntityPayment> payments) {
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

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolderPayment extends RecyclerView.ViewHolder {

        public ViewHolderPayment(@NonNull View itemView) {
            super(itemView);
        }
    }
}
