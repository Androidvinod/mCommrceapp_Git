package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Fragment.CartListFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CartListModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Fragment.CartListFragment.cordinator_cart;
import static com.example.defaultdemotoken.Fragment.CartListFragment.lv_cartlist_progress;


public class CartlistAdapter_new extends RecyclerView.Adapter<CartlistAdapter_new.MyViewHolder> {
    private Context context;
    private List<CartListModel> cartList;
    ApiInterface api;

    public CartlistAdapter_new(Context context, List<CartListModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlist_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        api = ApiClient.getClient().create(ApiInterface.class);
        final CartListModel cart_model = cartList.get(position);
        Log.e("ada_cart_pdname",""+cart_model.getName());
        Log.e("ada_cart_pdprice",""+cart_model.getPrice());
        Log.e("ada_cart_pdquote",""+cart_model.getQuoteId());
        Log.e("ada_cart_pdimagee",""+cart_model.getImage());

        //Log.e("ada_cart_pdimage",""+cart_model.getExtensionAttributes());

        holder.tv_cartlist_product_name.setTypeface(SplashActivity.montserrat_semibold);
        holder.tv_cart_sku.setTypeface(SplashActivity.montserrat_medium);
        holder.et_cart_qty.setTypeface(SplashActivity.montserrat_medium);
        holder.tv_cartlist_price.setTypeface(SplashActivity.montserrat_semibold);

        holder.tv_cartlist_product_name.setText(cart_model.getName());
        holder.tv_cartlist_price.setText(String.valueOf(cart_model.getPrice()));
        holder.tv_cart_sku.setText(cart_model.getSku());
        holder.et_cart_qty.setText(String.valueOf(cart_model.getQty()));

        final RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(R.drawable.acc);
        requestOptions.error(R.drawable.acc);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cart_model.getImage()).into(holder.iv_cartlist_product);

        holder.lv_remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removefromcart(cart_model.getItemId());
            }
        });

    }

    private void removefromcart(final String itemId) {
        cordinator_cart.setVisibility(View.GONE);
        lv_cartlist_progress.setVisibility(View.VISIBLE);
        callremoveFromCart(itemId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                cordinator_cart.setVisibility(View.VISIBLE);
                lv_cartlist_progress.setVisibility(View.GONE);
                String result = response.body();
                Log.e("rc_res",""+result);
                if (result.equals("true")){
                    CartListFragment.CallCartlistApi();
                    Toast.makeText(context, "Product has been successfully removed from your cart", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(Integer.parseInt(itemId));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("failllllll","");
            }
        });
    }
    private Call<String> callremoveFromCart(String itemId) {
        return api.removeFromCart("Bearer " + Login_preference.getCustomertoken(context),"http://dkbraende.demoproject.info/rest//V1/carts/mine/items/"+itemId);
    }

    public void addAll(List<CartListModel> media1) {
        for (CartListModel cartListModel : media1) {
            add(cartListModel);
        }
    }

    private  void add(CartListModel r) {
        cartList.add(r);
        notifyItemInserted(cartList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cartlist_product_name,tv_cartlist_price,tv_cart_sku;
        ImageView iv_cartlist_product;
        /*RoundRectCornerImageView iv_cartlist_product;*/
        EditText et_cart_qty;
        LinearLayout lv_remove_from_cart;
        public MyViewHolder(View view) {
            super(view);

            tv_cartlist_product_name =  view.findViewById(R.id.tv_cartlist_product_name);
            tv_cartlist_price =  view.findViewById(R.id.tv_cartlist_price);
            tv_cart_sku =  view.findViewById(R.id.tv_cart_sku);
            iv_cartlist_product =  view.findViewById(R.id.iv_cartlist_product);
            et_cart_qty =  view.findViewById(R.id.et_cart_qty);
            lv_remove_from_cart =  view.findViewById(R.id.lv_remove_from_cart);

        }
    }
}