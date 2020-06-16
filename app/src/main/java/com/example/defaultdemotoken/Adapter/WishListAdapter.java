package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Fragment.WishlistFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.WishlistModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.tv_wishlist_count;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.lv_progress_wishist;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.lvnodata_wishlistlist;
import static com.example.defaultdemotoken.Fragment.WishlistFragment.recv_wishlist;


public class WishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        private List<WishlistModel> productList;
        ApiInterface getwish;
        static String prod_id;
        LayoutInflater inflater;
        ApiInterface apiInterface;

        public WishListAdapter(Context context, List<WishlistModel> model) {
            this.context = context;
            this.productList = model;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.wishlist_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


            getwish = ApiClient.getClient().create(ApiInterface.class);
            final WishlistModel product = productList.get(position);

            Log.e("debug822","="+product.getSpecial_price());
            Log.e("debug822","="+product.getPrice());
            Log.e("debug822","="+product.getName());
            prod_id = product.getProduct_id();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            MyViewHolder myViewHolder = (MyViewHolder) holder;



            NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_product_name, product.getName());
            //  myViewHolder.tv_wishlist_product_name.setText(product.getProductName());
            //   myViewHolder.tv_wishlist_msrp.setText(product.getProductPrice());

            if ( product.getSpecial_price()==null || product.getSpecial_price()=="null" ||
                    product.getSpecial_price().equalsIgnoreCase("null")
                    || product.getSpecial_price().equalsIgnoreCase("") == true ) {
                myViewHolder.lv_special.setVisibility(View.GONE);
                NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_msrp, product.getPrice());

            } else {
                myViewHolder.tv_wishlist_msrp.setPaintFlags(myViewHolder.tv_wishlist_msrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                if(product.getSpecial_price()==null)
                {
                    myViewHolder.lv_special.setVisibility(View.GONE);
                }else {
                    myViewHolder.lv_special.setVisibility(View.VISIBLE);
                    NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_msrp, product.getPrice());
                    NavigationActivity.Check_String_NULL_Value(myViewHolder.tv_wishlist_special_price, product.getSpecial_price());
                }

            }


            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.dr);
            requestOptions.error(R.drawable.dr);
            Log.e("debug_fav_image",""+product.getThumbnail());
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://dkbraende.demoproject.info/pub/media/catalog/product"+product.getThumbnail()).into(myViewHolder.iv_wishlist_product);

            myViewHolder.lv_productwish_click.setEnabled(true);
           /* myViewHolder.lv_productwish_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //  myViewHolder.lv_productwish_click.setEnabled(false);
                            Bundle b = new Bundle();
                            b.putString("product_id", product.getWishlist_item_id());
                            b.putString("wishlist", product.getWishlist_item_id());

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            ProductDetailFragment myFragment = new ProductDetailFragment();
                            myFragment.setArguments(b);
                            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                                    0, 0, R.anim.fade_out).addToBackStack(null).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
                            if (drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }
                    }, 100);
                }
            });*/


            ((MyViewHolder) holder).lv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  itemid= String.valueOf(productList.get(position).getWishlist_item_id());
                    Log.e("debg","="+itemid);
                    callRemoveFromCartApi(itemid,position,v);
                }
            });

        }

        @Override
        public int getItemCount() {
            return productList == null ? 0 : productList.size();
        }





        private void callRemoveFromCartApi(String itemid, int position, final View v) {

            lvnodata_wishlistlist.setVisibility(View.GONE);

            lv_progress_wishist.setVisibility(View.VISIBLE);
            recv_wishlist.setVisibility(View.VISIBLE);

            callRemovecartapi(itemid).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean paymentMehtodModel = response.body();

                    Log.e("resaaaremove wishlist", "=" + response.code());
                    Log.e("resaaacccc", "=" + response);
                    lv_progress_wishist.setVisibility(View.GONE);
                    lvnodata_wishlistlist.setVisibility(View.GONE);
                    recv_wishlist.setVisibility(View.VISIBLE);

//                    Log.e("res_payment", "aaa" + response.body().toString());
                    //progressBar_review.setVisibility(View.GONE);

                    //  lv_cartlist_progress.setVisibility(View.GONE);
                    // lv_cart_Main.setVisibility(View.VISIBLE);


                    if(response.code()==200)
                    {
                        //    CallCartlistApi();
                        // cartlistdata.remove(position);
                        // cartlistAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "Product Remove from Wishlist successfully", Toast.LENGTH_SHORT).show();


                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        WishlistFragment myFragment = new WishlistFragment();

                        activity.getSupportFragmentManager()
                                .beginTransaction().addToBackStack(null).
                                replace(R.id.framlayout, myFragment).commit();
                        callWishlistCountApi();

                    }else {

                    }






                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    // String error=  t.printStackTrace();
                    Toast.makeText(context, "" + context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                    Log.e("debug_175125", "pages: " + t);
                }
            });
        }



        private Call<Boolean> callRemovecartapi(String itemid) {

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Log.e("debug_11","=="+itemid);
            ///http://dkbraende.demoproject.info/rest//V1/carts/mine/items/162920


            String url=ApiClient.MAIN_URLL+"wishlist/delete/"+itemid;
            Log.e("url11","=="+url);
            return api.removeitemfromWishlistt("Bearer "+ Login_preference.getCustomertoken(context),url);
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
                        Log.e("wishcount","="+count);
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


        public boolean isEmpty() {
            return getItemCount() == 0;
        }


        public WishlistModel getItem(int position) {
            Log.e("pos_galada", "" + position);
            return productList.get(position);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_wishlist_product;
            TextView tv_wishlist_product_name, tv_wishlist_msrp_title, tv_wishlist_msrp, tv_wishlist_special_price;
            LinearLayout lv_special, lv_productwish_click,lv_delete;

            public MyViewHolder(@NonNull View view) {
                super(view);

                lv_delete = view.findViewById(R.id.lv_delete);
                lv_productwish_click = view.findViewById(R.id.lv_productwish_click);
                lv_special = view.findViewById(R.id.lv_special);
                iv_wishlist_product = view.findViewById(R.id.iv_wishlist_product);
                tv_wishlist_product_name = view.findViewById(R.id.tv_wishlist_product_name);
                tv_wishlist_msrp_title = view.findViewById(R.id.tv_wishlist_msrp_title);
                tv_wishlist_msrp = view.findViewById(R.id.tv_wishlist_msrp);
                tv_wishlist_special_price = view.findViewById(R.id.tv_wishlist_special_price);

            }
        }


    }


