package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;

import com.example.defaultdemotoken.Model.HomeModel.BestSelling;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.util.List;

    public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.MyViewHolder> {
        Context context;
        private List<BestSelling> bestSellingList;

        public BestSellingAdapter(Context context,List<BestSelling> bestSellingList) {
            this.context = context;
            this.bestSellingList = bestSellingList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.best_selling_row, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final BestSelling bestSelling = bestSellingList.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder) holder;

            holder.tv_product_name_bestselling.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_product_price_bestselling.setTypeface(SplashActivity.montserrat_medium);

            myViewHolder.tv_product_name_bestselling.setText(bestSellingList.get(position).getName());
            myViewHolder.tv_product_price_bestselling.setText(bestSellingList.get(position).getPrice());

            Log.e("best_selling_name",""+bestSellingList.get(position).getName());
            Log.e("best_selling_rats",""+bestSellingList.get(position).getRating());

            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image);
            requestOptions.error(R.drawable.image);

            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(bestSellingList.get(position).getImage()).into(myViewHolder.iv_product_img_bestselling);

           myViewHolder.ratingbar_bestselling.setRating(Float.parseFloat(bestSellingList.get(position).getRating()));

        }

        /*public void add(HomebannerModel r) {
            HomebannerModelList.add(r);
            notifyItemInserted(HomebannerModelList.size() - 1);
        }

        public void addAll(List<HomebannerModel> moveResults) {

            for (HomebannerModel result : moveResults) {
                Log.e("debug_127adapter",""+result);
                add(result);
            }
        }*/

        @Override
        public int getItemCount() {
            return bestSellingList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            RoundRectCornerImageView iv_product_img_bestselling;
            TextView tv_product_name_bestselling,tv_product_price_bestselling;
            RatingBar ratingbar_bestselling;

            public MyViewHolder(@NonNull View view) {
                super(view);

                iv_product_img_bestselling = view.findViewById(R.id.iv_product_img_bestselling);
                tv_product_name_bestselling = view.findViewById(R.id.tv_product_name_bestselling);
                tv_product_price_bestselling = view.findViewById(R.id.tv_product_price_bestselling);
                ratingbar_bestselling = view.findViewById(R.id.ratingbar_bestselling);

            }
        }
    }



