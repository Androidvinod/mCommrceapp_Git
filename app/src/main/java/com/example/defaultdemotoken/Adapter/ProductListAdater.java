package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.MyBounceInterpolator;
import com.example.defaultdemotoken.Fragment.SubCategoryFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.ProductModel.Item;

import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.example.defaultdemotoken.RoundRectCornerImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;
import static com.example.defaultdemotoken.Activity.NavigationActivity.tv_wishlist_count;


public class ProductListAdater extends RecyclerView.Adapter<ProductListAdater.MyViewHolder> {

        Context context;
        private List<Item> ItemList;

        public ProductListAdater(Context context) {
            this.context = context;
            ItemList = new ArrayList<>();
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


            holder.iv_wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String prod_id = String.valueOf(ItemList.get(position).getId());
                    ///  Log.e("prod_idddd_wish",""+prod_id);

                    /*if (Login_preference.getLogin_flag(context).matches("1")) {*/
                    if (CheckNetwork.isNetworkAvailable(context)) {
                        CallAddtoWishlistApi_list(holder, prod_id, position);
                    } else {
                        Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
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

    private void callWishlistCountApi() {
        Log.e("response201tokenff",""+Login_preference.getCustomertoken(context));
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> customertoken = api.defaultWishlistCount("Bearer "+Login_preference.getCustomertoken(context));
        customertoken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response200gffgdf",""+response.toString());
                Log.e("response201fgd",""+response.body());
                if(response.code()==200 || response.isSuccessful())
                {
                    try {
                        JSONArray jsonObject = new JSONArray(response.body().string());

                        String count= jsonObject.getJSONObject(0).getString("total_items");
                        tv_wishlist_count.setText(count);
                        Login_preference.set_wishlist_count(context,count);
                        Log.e("wishcount",""+count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private Call<Boolean> calladdtowishnew(String itemid) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_11111","=="+itemid);
        Log.e("debug_11token","=="+ Login_preference.getCustomertoken(context));
        ///http://dkbraende.demoproject.info/rest//V1/carts/mine/items/162920


        String url=ApiClient.MAIN_URLL+"wishlist/add/"+itemid;
        Log.e("url1111","=="+url);
        return api.defaultaddtowishlist("Bearer "+Login_preference.getCustomertoken(context),url);
    }

    private void CallAddtoWishlistApi_list(final MyViewHolder listHolder, String prod_id, final int position) {

        calladdtowishnew(prod_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("debug_166",""+response);
                Log.e("debug_167",""+response.body());
                Boolean addToWishlist = response.body();
                //  Log.e("response_168",""+addToWishlist);
                //   Log.e("status_wish",""+addToWishlist.getStatus());
                Log.e("status_wish","ok");
                if(response.isSuccessful() || response.code()==200)
                {
                    listHolder.iv_wishlist.setVisibility(View.GONE);
                    listHolder.iv_wishlist_remove.setVisibility(View.VISIBLE);
                    final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    listHolder.iv_wishlist_remove.startAnimation(myAnim);

                    callWishlistCountApi();
                }else {
                    Log.e("debug_error","="+response);
                    Log.e("error","="+response.body());

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Log.e("error_wish",""+t);
                Log.e("debug_remivr", ""+t.getMessage());
                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
        public int getItemCount() {
            return ItemList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_product_price,tv_product_name,tv_product_new;
            ImageView iv_wishlist,iv_pluss,iv_wishlist_remove;
            RoundRectCornerImageView iv_product_img;
            LinearLayout lv_product_clickk;
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
            }
        }
    }





