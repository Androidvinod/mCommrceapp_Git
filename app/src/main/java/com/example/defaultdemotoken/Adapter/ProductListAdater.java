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
import com.example.defaultdemotoken.Fragment.CartListFragment;
import com.example.defaultdemotoken.Fragment.LoginFragment;
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
import org.json.JSONObject;

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

            holder.lv_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1"))
                    {
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            callAddtoCartApi(item.getSku(),holder.lv_product_main, holder.lv_pb_prod);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        LoginFragment myFragment = new LoginFragment();
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                    }
                }
            });
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
                    if (Login_preference.getLogin_flag(context).equalsIgnoreCase("1"))
                    {
                        if (CheckNetwork.isNetworkAvailable(context)) {
                            CallAddtoWishlistApi_list(holder, prod_id, position);
                        } else {
                            Toast.makeText(context, context.getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        LoginFragment myFragment = new LoginFragment();
                        activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
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

    private void callAddtoCartApi(String sku, final LinearLayout lv_product_main, final LinearLayout lv_pb_prod) {
        lv_product_main.setVisibility(View.GONE);
        lv_pb_prod.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("product_sku_pass", "" + sku);
        String url="http://dkbraende.demoproject.info/rest/V1/carts/mine/items?cartItem[quoteId]="+ Login_preference.getquote_id(context)+"&cartItem[qty]=1"+"&cartItem[sku]="+sku;

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
                } else if (response.code() == Integer.parseInt("400")) {
                    lv_product_main.setVisibility(View.VISIBLE);
                    lv_pb_prod.setVisibility(View.GONE);
                    Toast.makeText(context, "Bad Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
 // lv_product_progress.setVisibility(View.GONE);
// coordinator_product_main.setVisibility(View.VISIBLE);
                Toast.makeText(context, context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
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

                        if (count.equalsIgnoreCase("null") || count.equals("") || count.equals("0")) {
                            tv_wishlist_count.setVisibility(View.GONE);
                        } else {
                            tv_wishlist_count.setVisibility(View.VISIBLE);
                            tv_wishlist_count.setText(count);
                            Login_preference.set_wishlist_count(context,count);
                            Log.e("wishcount",""+count);
                        }


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
            LinearLayout lv_product_clickk,lv_addtocart,lv_pb_prod,lv_product_main;
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





