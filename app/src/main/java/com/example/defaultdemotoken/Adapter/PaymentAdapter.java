package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.Model.PaymentModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private Context context;
    private List<PaymentModel> paymentModels;

    public PaymentAdapter(Context context, List<PaymentModel> paymentModels) {
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PaymentModel paymentModel = paymentModels.get(position);

        holder.tv_payment_method_name.setTypeface(SplashActivity.montserrat_bold);

        holder.tv_payment_method_name.setText(paymentModels.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_payment_method_name;

        public MyViewHolder(View view) {
            super(view);

            tv_payment_method_name = view.findViewById(R.id.tv_payment_method_name);

        }
    }
}