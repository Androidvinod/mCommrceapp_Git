package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;

import com.example.defaultdemotoken.Model.HomeModel.NewArrrivalsModel;

import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.util.List;

public class NewArrivalsAdapter extends RecyclerView.Adapter<NewArrivalsAdapter.MyViewHolder> {
    Context context;
    private List<NewArrrivalsModel> newArrrivalsModels;

    public NewArrivalsAdapter(Context context,List<NewArrrivalsModel> newArrrivalsModels) {
        this.context = context;
        this.newArrrivalsModels = newArrrivalsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_arrivals_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NewArrrivalsModel newArrrivalsModel = newArrrivalsModels.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        holder.tv_product_name_newarrivals.setTypeface(SplashActivity.montserrat_medium);
        holder.tv_product_price_new_arrivals.setTypeface(SplashActivity.montserrat_medium);

        myViewHolder.tv_product_name_newarrivals.setText(newArrrivalsModels.get(position).getName());
        myViewHolder.tv_product_price_new_arrivals.setText(newArrrivalsModels.get(position).getPrice());

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image);
        requestOptions.error(R.drawable.image);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(newArrrivalsModels.get(position).getImage()).into(myViewHolder.iv_product_img_newarrivals);

        myViewHolder.ratingbar_newarrivals.setRating(Float.parseFloat(newArrrivalsModels.get(position).getRating()));

    }


    @Override
    public int getItemCount() {
        return newArrrivalsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundRectCornerImageView iv_product_img_newarrivals;
        TextView tv_product_name_newarrivals,tv_product_price_new_arrivals;
        RatingBar ratingbar_newarrivals;

        public MyViewHolder(@NonNull View view) {
            super(view);

            iv_product_img_newarrivals = view.findViewById(R.id.iv_product_img_newarrivals);
            tv_product_name_newarrivals = view.findViewById(R.id.tv_product_name_newarrivals);
            tv_product_price_new_arrivals = view.findViewById(R.id.tv_product_price_new_arrivals);
            ratingbar_newarrivals = view.findViewById(R.id.ratingbar_newarrivals);

        }
    }
}