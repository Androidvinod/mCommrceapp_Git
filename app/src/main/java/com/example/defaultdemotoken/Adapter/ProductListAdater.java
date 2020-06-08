package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.SubCategoryFragment;
import com.example.defaultdemotoken.Model.ProductModel.Item;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;


public class ProductListAdater extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.ProductListAdater.MyViewHolder> {

        Context context;
        private List<Item> ItemList;

        public ProductListAdater(Context context) {
            this.context = context;
            ItemList = new ArrayList<>();
        }

        @NonNull
        @Override
        public com.example.defaultdemotoken.Adapter.ProductListAdater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.product_row, viewGroup, false);
            return new com.example.defaultdemotoken.Adapter.ProductListAdater.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final com.example.defaultdemotoken.Adapter.ProductListAdater.MyViewHolder holder, int position) {
            final Item item = ItemList.get(position);
            holder.tv_product_name.setText(item.getName());
            holder.tv_product_price.setText(""+item.getPrice());

            holder.tv_product_new.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_product_name.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_product_price.setTypeface(SplashActivity.montserrat_semibold);

            Log.e("debug_48","fg"+item.getMediaGalleryEntries().get(0).getFile());
            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image);
            requestOptions.error(R.drawable.image);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://dkbraende.demoproject.info/pub/media/catalog/product"+item.getMediaGalleryEntries().get(0).getFile()).into(holder.iv_product_img);


            holder.lv_product_clickk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("debug_55","gg"+item.getId());
                    Bundle b=new Bundle();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                  /*  b.putString("categoryid",String.valueOf(ChildDataList.get(position).getId()));
                    b.putString("categoryname",ChildDataList.get(position).getName());
                    b.putSerializable("subCatarraylist", (Serializable) ChildDataList.get(position).getChildrenData());

                    SubCategoryFragment myFragment = new SubCategoryFragment();
                    myFragment.setArguments(b);
                    activity.getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null).replace(R.id.framlayout, myFragment)
                            .commit();*/
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });

        }

        public void addAll(List<Item> categories) {
            for (Item result : categories) {
                add(result);
            }
        }

        public void add(Item r) {
            ItemList.add(r);
            notifyItemInserted(ItemList.size() - 1);
        }

        @Override
        public int getItemCount() {
            return ItemList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_product_price,tv_product_name,tv_product_new;
            ImageView iv_wishlist,iv_pluss;
            RoundRectCornerImageView iv_product_img;
            LinearLayout lv_product_clickk;
            RatingBar ratingbar_gourment;
            public MyViewHolder(@NonNull View view) {
                super(view);
                ratingbar_gourment = view.findViewById(R.id.ratingbar_gourment);
                iv_wishlist = view.findViewById(R.id.iv_wishlist);
                tv_product_name = view.findViewById(R.id.tv_product_name);
                tv_product_price = view.findViewById(R.id.tv_product_price);
                tv_product_new = view.findViewById(R.id.tv_product_new);
                lv_product_clickk = view.findViewById(R.id.lv_product_clickk);
                iv_pluss = view.findViewById(R.id.iv_pluss);
                iv_product_img = view.findViewById(R.id.iv_product_img);
            }
        }
    }





