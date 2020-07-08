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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.LoginFragment;
import com.example.defaultdemotoken.Fragment.ProductDetailFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.SearchModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;


public class SearchProductListAdapter extends RecyclerView.Adapter<SearchProductListAdapter.MyViewHolder> {
        Context context;
        private List<SearchModel> SearchModelList;

        public SearchProductListAdapter(Context context,List<SearchModel> SearchModelList) {
            this.context = context;
            this.SearchModelList = SearchModelList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.product_row, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            final SearchModel searchModel = SearchModelList.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            holder.tv_product_name.setText(searchModel.getName());
            holder.tv_product_price.setText("" + searchModel.getPrice());

            holder.tv_product_new.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_product_name.setTypeface(SplashActivity.montserrat_medium);
            holder.tv_product_price.setTypeface(SplashActivity.montserrat_semibold);


            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image);
            requestOptions.error(R.drawable.image);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://dkbraende.demoproject.info/pub/media/catalog/product" + searchModel.getImage()).into(holder.iv_product_img);


            holder.lv_product_clickk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("debug_55", "gg" + searchModel.getId());
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Bundle b = new Bundle();
                    b.putString("product_id", String.valueOf(SearchModelList.get(position).getId()));
                    b.putString("product_name", String.valueOf(SearchModelList.get(position).getName()));
                    ProductDetailFragment myFragment = new ProductDetailFragment();
                    myFragment.setArguments(b);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out)
                            .setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out)
                            .add(R.id.framlayout, myFragment)
                            .addToBackStack(null).commit();
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
            });


            // add ti cart
            holder.lv_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1")) {
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            callAddtoCartApi(SearchModelList.get(position).getSku(), holder.lv_product_main, holder.lv_pb_prod);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        LoginFragment myFragment = new LoginFragment();
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                    }
                }
            });

        }


    private void callAddtoCartApi(final String sku, final LinearLayout lv_product_main, final LinearLayout lv_pb_prod) {
        lv_product_main.setVisibility(View.GONE);
        lv_pb_prod.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("product_sku_pass", "=" + sku);
        Log.e("Customertoken_139", "=" +  Login_preference.getCustomertoken(context));
        String url = "http://dkbraende.demoproject.info/rest/V1/carts/mine/items?cartItem[quoteId]=" + Login_preference.getquote_id(context) + "&cartItem[qty]=1" + "&cartItem[sku]=" + sku;

        final Call<ResponseBody> productDetails = api.addtocart("Bearer " + Login_preference.getCustomertoken(context), url);
        productDetails.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_product_main.setVisibility(View.VISIBLE);
                lv_pb_prod.setVisibility(View.GONE);
                /*AddToCartListModel model = response.body();*/
                /*Log.e("response_add_to_cart",""+model);*/

                Log.e("response_add_to_cartt", "" + response);

                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        String name = jsonObject.getString("name");
                        Log.e("cart_prod_name", "" + name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == Integer.parseInt("200")) {
                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(response.body().string());

                        String name = jsonObject.getString("name");
                        Log.e("cart_prod_name", "" + name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == Integer.parseInt("401")) {
                    lv_product_main.setVisibility(View.VISIBLE);
                    lv_pb_prod.setVisibility(View.GONE);
                    Toast.makeText(context, "Response NULL", Toast.LENGTH_SHORT).show();
                    NavigationActivity.get_Customer_tokenapi();

                    callAddtoCartApi(sku, lv_product_main, lv_pb_prod);

                } else if (response.code() == Integer.parseInt("400")) {
                    lv_product_main.setVisibility(View.VISIBLE);
                    lv_pb_prod.setVisibility(View.GONE);
                    Toast.makeText(context, "Bad Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_product_main.setVisibility(View.VISIBLE);
                lv_pb_prod.setVisibility(View.GONE);
                Log.e("error", "=" + t.getMessage());

                // lv_product_progress.setVisibility(View.GONE);
// coordinator_product_main.setVisibility(View.VISIBLE);
                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void add(SearchModel r) {
            SearchModelList.add(r);
            notifyItemInserted(SearchModelList.size() - 1);
        }

        public void addAll(List<SearchModel> moveResults) {

            for (SearchModel result : moveResults) {
                Log.e("debug_127adapter",""+result);
                add(result);
            }
        }

        @Override
        public int getItemCount() {
            return SearchModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_product_price, tv_product_name, tv_product_new;
            ImageView iv_wishlist, iv_pluss, iv_wishlist_remove;
            RoundRectCornerImageView iv_product_img;
            LinearLayout lv_product_clickk, lv_addtocart, lv_pb_prod, lv_product_main;
            RatingBar ratingbar_gourment;

            public MyViewHolder(@NonNull View view) {
                super(view);

                iv_wishlist_remove = view.findViewById(R.id.iv_wishlist_remove);
                ratingbar_gourment = view.findViewById(R.id.ratingbar_gourment);
                iv_wishlist = view.findViewById(R.id.iv_wishlist);
                tv_product_name = view.findViewById(R.id.tv_product_name);
                tv_product_price = view.findViewById(R.id.tv_product_price);
                tv_product_new = view.findViewById(R.id.tv_product_new);
                lv_product_clickk = view.findViewById(R.id.lv_product_clickk);
                iv_pluss = view.findViewById(R.id.iv_pluss);

                iv_product_img = view.findViewById(R.id.iv_product_img);
                lv_addtocart = view.findViewById(R.id.lv_addtocart);
                lv_pb_prod = view.findViewById(R.id.lv_pb_prod);
                lv_product_main = view.findViewById(R.id.lv_product_main);

            }
        }
    }



