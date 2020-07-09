package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.List;

public class Current_Order_Adapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.Current_Order_Adapter.MyViewHolder> {
    Context context;
    private List<HomebannerModel> HomebannerModelList;

    public Current_Order_Adapter(Context context, List<HomebannerModel> HomebannerModelList) {
        this.context = context;
        this.HomebannerModelList = HomebannerModelList;
    }

    @NonNull
    @Override
    public com.example.defaultdemotoken.Adapter.Current_Order_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orders_row, viewGroup, false);
        return new com.example.defaultdemotoken.Adapter.Current_Order_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.defaultdemotoken.Adapter.Current_Order_Adapter.MyViewHolder holder, int position) {
        final HomebannerModel HomebannerModel = HomebannerModelList.get(position);


       holder.lv__track.setVisibility(View.VISIBLE);
         holder.lv_reorder.setVisibility(View.GONE);

        holder.tv_orders_product_name.setTypeface(SplashActivity.montserrat_bold);
        holder.tv_order_itemid.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_orders_itemid_value.setTypeface(SplashActivity.montserrat_regular);
        holder.tv_orders_product_price.setTypeface(SplashActivity.montserrat_regular);
        holder.tv_order_status.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_orders_status_value.setTypeface(SplashActivity.montserrat_semibold);
        //            myViewHolder.lv_HomebannerModel_click.setEnabled(true);
         /*   myViewHolder.tvHomebannerModelName.setText(Html.fromHtml(HomebannerModel.getName()));
            holder.lv_HomebannerModel_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //  myViewHolder.lv_HomebannerModel_click.setEnabled(false);
                            Bundle b=new Bundle();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            b.putString("categoryid", String.valueOf(HomebannerModel.getId()));
                            b.putString("categoryname",HomebannerModel.getName());
                            b.putString("screen","HomebannerModel");

                            ProductListFragment myFragment = new ProductListFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, myFragment)
                                    .addToBackStack(null).commit();

                        }
                    }, 100);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });*/


    }

    public void add(HomebannerModel r) {
        HomebannerModelList.add(r);
        notifyItemInserted(HomebannerModelList.size() - 1);
    }

    public void addAll(List<HomebannerModel> moveResults) {

        for (HomebannerModel result : moveResults) {
            Log.e("debug_127adapter", "" + result);
            add(result);
        }
    }


    @Override
    public int getItemCount() {
        return HomebannerModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orders_product_name, tv_order_itemid, tv_orders_itemid_value, tv_orders_product_price, tv_order_status, tv_orders_status_value;
        LinearLayout lv_main_order, lv_order_status_value, lv__track, lv_reorder;
        View viewsubcat;

        public MyViewHolder(@NonNull View view) {
            super(view);


            lv_reorder = view.findViewById(R.id.lv_reorder);
            lv__track = view.findViewById(R.id.lv__track);
            tv_orders_status_value = view.findViewById(R.id.tv_orders_status_value);
            tv_order_status = view.findViewById(R.id.tv_order_status);
            tv_orders_product_price = view.findViewById(R.id.tv_orders_product_price);
            tv_orders_itemid_value = view.findViewById(R.id.tv_orders_itemid_value);
            tv_order_itemid = view.findViewById(R.id.tv_order_itemid);
            tv_orders_product_name = view.findViewById(R.id.tv_orders_product_name);
            lv_main_order = view.findViewById(R.id.lv_main_order);
            lv_order_status_value = view.findViewById(R.id.lv_order_status_value);


        }
    }
}

