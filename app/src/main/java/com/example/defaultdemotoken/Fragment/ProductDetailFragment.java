package com.example.defaultdemotoken.Fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.ColorAdapter;
import com.example.defaultdemotoken.Adapter.ProductDetailsImageSliderAdapter;
import com.example.defaultdemotoken.Adapter.ProductSizeGuideAdapter;
import com.example.defaultdemotoken.Adapter.Relative_Product_Adapter;
import com.example.defaultdemotoken.Adapter.ReviewAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.HomeModel.BestSelling;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.Model.ProductDetailModel.Item;
import com.example.defaultdemotoken.Model.ProductDetailModel.MediaGalleryEntry;
import com.example.defaultdemotoken.Model.ProductDetailModel.ProductDetailsM;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator2;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements View.OnClickListener {
    Relative_Product_Adapter relative_product_adapter;
    ReviewAdapter reviewAdapter;
    ProductDetailsImageSliderAdapter productDetailsImageSliderAdapter;
    View v;
    RecyclerView recv_product_image_slider,recv_similar_product,recv_review;
    Toolbar toolbar_product_detail;
    CollapsingToolbarLayout collapse_toolbar_pdetail;
    AppBarLayout appbar_product_detail;
    CircleIndicator2 indicatorr;
   // private List<MediaGalleryEntry> mediaGalleryEntries = new ArrayList<MediaGalleryEntry>();
    ApiInterface apiInterface;

    String product_id,product_name;
    private List<BestSelling> similarProductList=new ArrayList<>();

    TextView tv_pdetail_off,tv_pdetail_product_name,tv_pdetail_sku,tv_pdetail_sku_value,tv_pdetail_price,tv_special_price,tv_price,tv_rating_pdetail,tv_instock
            ,tv_pdettail_addtocart,tv_pdetail_size,tv_pdetail_color,tv_pdetail_detail,tv_show_half_desc,tv_show_full_desc,tv_read_more_description,
            tv_product_feature_pdetail,tv_feature_value1,tv_feature_value2,tv_feature_value3,tv_readmore_feature,tv_pdetail_similarproduct,tv_pdetail_paymentopt,
            tv_cashondelevery,tv_banking,tv_reviewsa,tv_write_your_review,tv_full_rating,tv_rating_pdetail_review,tv_view_all_review,tv_havedouts,tv_postquestion,tv_submit;
    NestedScrollView nested_scroll_product_detail;
    LinearLayout lv_pdetail_wishlist,lv_ad_cart_pdetail,lv_pdetail_size,lv_pdetail_color,lv_submit,lv_progress_productdetail,lv_app_off_button;
   public static ImageView iv_color_green;
    RatingBar ratingbar_pdetail;

    public static TextView tv_pdetail_xl;
    EditText edit_query;
    private List<HomebannerModel> HomebannerModelList=new ArrayList<>();
    public static BottomSheetDialog dialog,colourDialoge;
    ProductSizeGuideAdapter productSizeGuideAdapter;
    ColorAdapter colorAdapter;
    List<MediaGalleryEntry> medialist=new ArrayList<>();
    String sku;
    CoordinatorLayout cordinator_productdetail;
    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_product_detail, container, false);
        AllocateMemory(v);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setHasOptionsMenu(true);
        AttachRecyclerView();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            product_id = bundle.getString("product_id");
            product_name = bundle.getString("product_name");
            Log.e("prod_id_andar",""+product_id);
            Log.e("prod_id_andar",""+product_name);
        }

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_product_detail);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);


        toolbar_product_detail.setTitleTextColor(getActivity().getResources().getColor(R.color.black));

        collapse_toolbar_pdetail.setCollapsedTitleTextColor(getActivity().getResources().getColor(R.color.black));
        collapse_toolbar_pdetail.setExpandedTitleColor(getActivity().getResources().getColor(R.color.black));


        appbar_product_detail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0)  {
                    collapse_toolbar_pdetail.setTitle(product_name);
                    collapse_toolbar_pdetail.setCollapsedTitleTextColor(getActivity().getResources().getColor(R.color.black));
                    collapse_toolbar_pdetail.setExpandedTitleColor(getActivity().getResources().getColor(R.color.black));
                    collapse_toolbar_pdetail.setCollapsedTitleTypeface(SplashActivity.montserrat_semibold);
                    lv_app_off_button.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
                    lv_app_off_button.setVisibility(View.VISIBLE);

                    collapse_toolbar_pdetail.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
           // ProductdetailsapiMSimpleType(product_id);
            Productdetailsapiiiii(product_id);
        } else {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }


        tv_read_more_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lv_pdetail_size.setOnClickListener(this);
        lv_pdetail_color.setOnClickListener(this);
        lv_ad_cart_pdetail.setOnClickListener(this);
        return v;
    }

    private void AttachRecyclerView() {
        productDetailsImageSliderAdapter = new ProductDetailsImageSliderAdapter(getActivity(), medialist);
        recv_product_image_slider.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recv_product_image_slider.setAdapter(productDetailsImageSliderAdapter);
        recv_product_image_slider.setOnFlingListener(null);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recv_product_image_slider);
       //    recv_product_image_slider.setOnFlingListener(null);
        indicatorr.attachToRecyclerView(recv_product_image_slider,pagerSnapHelper);
        productDetailsImageSliderAdapter.registerAdapterDataObserver(indicatorr.getAdapterDataObserver());

        similarProductList.clear();
        for(int i=0;i<3;i++)
        {
            similarProductList.add(new BestSelling("","","","","",""));
        }


        //similar product list
        relative_product_adapter = new Relative_Product_Adapter(getActivity(), similarProductList);
        recv_similar_product.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recv_similar_product.setAdapter(relative_product_adapter);

        //review list
        reviewAdapter = new ReviewAdapter(getActivity(), similarProductList);
        recv_review.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_review.setAdapter(reviewAdapter);

    }



    private void Productdetailsapiiiii(String product_id) {
        lv_progress_productdetail.setVisibility(View.VISIBLE);
        cordinator_productdetail.setVisibility(View.GONE);
        callproductdetailsgetapi(product_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response",""+response.body());
                Log.e("response",""+response.body().toString());
                Log.e("response",""+response);

                if(response.isSuccessful() || response.code()==200)
                {
                    // Log.e("status",""+response.body().getItems());
                    lv_progress_productdetail.setVisibility(View.GONE);
                    cordinator_productdetail.setVisibility(View.VISIBLE);
                    try {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONArray itemarray=jsonObject.getJSONArray("items");

                        JSONObject itemarrayJSONObject = itemarray.getJSONObject(0);
                        //  tv_daily_special_product_name.setText(itemarray.get(0).getName());
                        tv_pdetail_product_name.setText(itemarrayJSONObject.getString("name"));
                        tv_price.setText(itemarrayJSONObject.getString("price") + " Kr");
                        tv_special_price.setText(" ");
                        sku=itemarrayJSONObject.getString("sku");
                        tv_pdetail_sku_value.setText(sku);
                        JSONArray attributearry = itemarrayJSONObject.getJSONArray("custom_attributes");
                        for(int i=0;i<attributearry.length();i++)
                        {
                            JSONObject attributeobject = attributearry.getJSONObject(i);
                            if(attributeobject.getString("attribute_code").equals("description"))
                            {
                                String value= String.valueOf(Html.fromHtml(String.valueOf(attributeobject.getString("value"))));
                                tv_show_full_desc.setText(value);

                            }
                            if(attributeobject.getString("attribute_code").equals("short_description")){
                                String value= String.valueOf(Html.fromHtml(String.valueOf(attributeobject.getString("value"))));
                                tv_show_half_desc.setText(value);
                            }
                            if(attributeobject.getString("attribute_code").equals("special_price")){
                                String value= String.valueOf(Html.fromHtml(String.valueOf(attributeobject.getString("value")+"  Kr")));
                                tv_special_price.setText(value);
                                tv_special_price.setVisibility(View.VISIBLE);
                                tv_special_price.setVisibility(View.VISIBLE);
                            }else {
                                // tv_rifles_Specialprice.setVisibility(View.GONE);
                                // tv_rifles_Specialprice_title.setVisibility(View.GONE);
                            }
                        }

                        if(tv_special_price.getText().equals(" "))
                        {

                        }else {
                            tv_price.setPaintFlags(tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                        //gallary
                        JSONArray mediaarry=itemarrayJSONObject.getJSONArray("media_gallery_entries");
                        for (int i=0;i<mediaarry.length();i++)
                        {
                            try {
                                JSONObject object = mediaarry.getJSONObject(i);
                                medialist.add(new MediaGalleryEntry(object.getString("file")));
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                productDetailsImageSliderAdapter.notifyItemChanged(i);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    cordinator_productdetail.setVisibility(View.VISIBLE);
                    lv_progress_productdetail.setVisibility(View.GONE);
                }


            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                lv_progress_productdetail.setVisibility(View.GONE);
                Log.e("emptyyyyfdfgfdg","eeeeeeeeeee"+t.getMessage());
            }
        });
    }



    private Call<ResponseBody> callproductdetailsgetapi(String product_id) {
        Log.e("debug_11","=="+product_id);
        Log.e("debug_11token","=="+Login_preference.gettoken(getActivity()));
        return apiInterface.productsdetail("Bearer "+Login_preference.gettoken(getActivity()),"entity_id","eq",product_id);
    }
    
    
    private void AllocateMemory(View v) {
        lv_app_off_button=v.findViewById(R.id.lv_app_off_button);
        cordinator_productdetail=v.findViewById(R.id.cordinator_productdetail);
        lv_progress_productdetail=v.findViewById(R.id.lv_progress_productdetail);
        tv_havedouts=v.findViewById(R.id.tv_havedouts);
        tv_feature_value2=v.findViewById(R.id.tv_feature_value2);
        tv_product_feature_pdetail=v.findViewById(R.id.tv_product_feature_pdetail);
        tv_pdetail_detail=v.findViewById(R.id.tv_pdetail_detail);
        iv_color_green=v.findViewById(R.id.iv_color_green);
        lv_pdetail_color=v.findViewById(R.id.lv_pdetail_color);
        lv_pdetail_size=v.findViewById(R.id.lv_pdetail_size);
        lv_ad_cart_pdetail=v.findViewById(R.id.lv_ad_cart_pdetail);
        tv_rating_pdetail=v.findViewById(R.id.tv_rating_pdetail);
        lv_pdetail_wishlist=v.findViewById(R.id.lv_pdetail_wishlist);
        recv_review=v.findViewById(R.id.recv_review);
        recv_similar_product=v.findViewById(R.id.recv_similar_product);
        recv_product_image_slider=v.findViewById(R.id.recv_product_image_slider);
        toolbar_product_detail=v.findViewById(R.id.toolbar_product_detail);
        collapse_toolbar_pdetail=v.findViewById(R.id.collapse_toolbar_pdetail);
        appbar_product_detail=v.findViewById(R.id.appbar_product_detail);
        indicatorr=v.findViewById(R.id.indicatorr);
        tv_pdetail_off=v.findViewById(R.id.tv_pdetail_off);
        nested_scroll_product_detail=v.findViewById(R.id.nested_scroll_product_detail);
        tv_pdetail_product_name=v.findViewById(R.id.tv_pdetail_product_name);
        tv_pdetail_sku=v.findViewById(R.id.tv_pdetail_sku);
        tv_pdetail_sku_value=v.findViewById(R.id.tv_pdetail_sku_value);
        tv_pdetail_price=v.findViewById(R.id.tv_pdetail_price);
        tv_special_price=v.findViewById(R.id.tv_special_price);
        tv_price=v.findViewById(R.id.tv_price);
        ratingbar_pdetail=v.findViewById(R.id.ratingbar_pdetail);
        tv_instock=v.findViewById(R.id.tv_instock);
        tv_pdettail_addtocart=v.findViewById(R.id.tv_pdettail_addtocart);
        tv_pdetail_size=v.findViewById(R.id.tv_pdetail_size);
        tv_pdetail_xl=v.findViewById(R.id.tv_pdetail_xl);
        tv_pdetail_color=v.findViewById(R.id.tv_pdetail_color);
        tv_show_half_desc=v.findViewById(R.id.tv_show_half_desc);
        tv_show_full_desc=v.findViewById(R.id.tv_show_full_desc);
        tv_read_more_description=v.findViewById(R.id.tv_read_more_description);
        tv_feature_value1=v.findViewById(R.id.tv_feature_value1);
        tv_feature_value3=v.findViewById(R.id.tv_feature_value3);
        tv_readmore_feature=v.findViewById(R.id.tv_readmore_feature);
        tv_pdetail_similarproduct=v.findViewById(R.id.tv_pdetail_similarproduct);
        tv_pdetail_paymentopt=v.findViewById(R.id.tv_pdetail_paymentopt);
        tv_cashondelevery=v.findViewById(R.id.tv_cashondelevery);
        tv_banking=v.findViewById(R.id.tv_banking);
        tv_reviewsa=v.findViewById(R.id.tv_reviewsa);
        tv_write_your_review=v.findViewById(R.id.tv_write_your_review);
        tv_full_rating=v.findViewById(R.id.tv_full_rating);
        tv_rating_pdetail_review=v.findViewById(R.id.tv_rating_pdetail_review);
        tv_view_all_review=v.findViewById(R.id.tv_view_all_review);
        tv_postquestion=v.findViewById(R.id.tv_postquestion);
        edit_query=v.findViewById(R.id.edit_query);
        tv_submit=v.findViewById(R.id.tv_submit);
        lv_submit=v.findViewById(R.id.lv_submit);



        tv_feature_value3.setTypeface(SplashActivity.montserrat_regular);
        tv_feature_value2.setTypeface(SplashActivity.montserrat_regular);
        tv_feature_value1.setTypeface(SplashActivity.montserrat_regular);
        tv_show_full_desc.setTypeface(SplashActivity.montserrat_regular);
        tv_show_half_desc.setTypeface(SplashActivity.montserrat_regular);
        tv_pdetail_color.setTypeface(SplashActivity.montserrat_regular);
        tv_pdetail_size.setTypeface(SplashActivity.montserrat_regular);
        tv_pdetail_sku_value.setTypeface(SplashActivity.montserrat_regular);
        tv_rating_pdetail.setTypeface(SplashActivity.montserrat_extralight);

        tv_pdettail_addtocart.setTypeface(SplashActivity.montserrat_semibold);
        tv_submit.setTypeface(SplashActivity.montserrat_semibold);

        tv_read_more_description.setTypeface(SplashActivity.montserrat_medium);
        tv_readmore_feature.setTypeface(SplashActivity.montserrat_medium);
        tv_cashondelevery.setTypeface(SplashActivity.montserrat_medium);
        tv_banking.setTypeface(SplashActivity.montserrat_medium);
        tv_write_your_review.setTypeface(SplashActivity.montserrat_medium);
        tv_rating_pdetail_review.setTypeface(SplashActivity.montserrat_medium);
        tv_view_all_review.setTypeface(SplashActivity.montserrat_medium);
        tv_postquestion.setTypeface(SplashActivity.montserrat_medium);
        edit_query.setTypeface(SplashActivity.montserrat_medium);

        tv_havedouts.setTypeface(SplashActivity.montserrat_bold);
        tv_full_rating.setTypeface(SplashActivity.montserrat_bold);
        tv_reviewsa.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_paymentopt.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_similarproduct.setTypeface(SplashActivity.montserrat_bold);
        tv_instock.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_off.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_product_name.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_sku.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_price.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_detail.setTypeface(SplashActivity.montserrat_bold);
        tv_pdetail_xl.setTypeface(SplashActivity.montserrat_bold);
        tv_product_feature_pdetail.setTypeface(SplashActivity.montserrat_bold);

        tv_price.setTypeface(SplashActivity.montserrat_bold);
        tv_special_price.setTypeface(SplashActivity.montserrat_bold);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
       // inflater.inflate(R.menu.cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==lv_pdetail_size)
        {
            openProductSizeDialogue();
        }else if(v==lv_pdetail_color)
        {
            openColorDialogue();
        }else if(v==lv_ad_cart_pdetail)
        {
            if (Login_preference.getLogin_flag(getActivity()).equalsIgnoreCase("1")) {
                if (CheckNetwork.isNetworkAvailable(getActivity())) {
                    callAddtoCartApi(sku);
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }
            } else {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                LoginFragment myFragment = new LoginFragment();
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).replace(R.id.framlayout, myFragment).addToBackStack(null).commit();
            }
        }
    }


    private void callAddtoCartApi(final String sku) {
        cordinator_productdetail.setVisibility(View.GONE);
        lv_progress_productdetail.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Log.e("product_sku_pass", "" + sku);
        String url = "http://dkbraende.demoproject.info/rest/V1/carts/mine/items?cartItem[quoteId]=" + Login_preference.getquote_id(getActivity()) + "&cartItem[qty]=1" + "&cartItem[sku]=" + sku;

        final Call<ResponseBody> productDetails = api.addtocart("Bearer " + Login_preference.getCustomertoken(getActivity()), url);
        productDetails.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                cordinator_productdetail.setVisibility(View.VISIBLE);
                lv_progress_productdetail.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Add to cart SuccessFully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == Integer.parseInt("401")) {
                    cordinator_productdetail.setVisibility(View.VISIBLE);
                    lv_progress_productdetail.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Response NULL", Toast.LENGTH_SHORT).show();
                    NavigationActivity.get_Customer_tokenapi();

                    callAddtoCartApi(sku);

                } else if (response.code() == Integer.parseInt("400")) {
                    cordinator_productdetail.setVisibility(View.VISIBLE);
                    lv_progress_productdetail.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Bad Response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // lv_product_progress.setVisibility(View.GONE);
// coordinator_product_main.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openColorDialogue() {

        colourDialoge = new BottomSheetDialog(getActivity());
        colourDialoge.setContentView(R.layout.product_size_guide_row);
        colourDialoge.show();
        RecyclerView recv_size = colourDialoge.findViewById(R.id.recv_size);
        TextView tv_selectsize = colourDialoge.findViewById(R.id.tv_selectsize);
        TextView tv_viewsize_guide = colourDialoge.findViewById(R.id.tv_viewsize_guide);
        LinearLayout iv_close_size = colourDialoge.findViewById(R.id.iv_close_size);
        HomebannerModelList.clear();

        tv_selectsize.setTypeface(SplashActivity.montserrat_medium);
        tv_selectsize.setText("Select Colour");
//        tv_viewsize_guide.setTypeface(SplashActivity.montserrat_medium);
        for (int i=0;i <4;i++)
        {
            HomebannerModelList.add(new HomebannerModel(""));
        }

        colorAdapter = new ColorAdapter(getActivity(), HomebannerModelList);
        recv_size.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_size.setAdapter(colorAdapter);



        iv_close_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void openProductSizeDialogue() {
        dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.product_size_guide_row);
        dialog.show();
        RecyclerView recv_size = dialog.findViewById(R.id.recv_size);
        TextView tv_selectsize = dialog.findViewById(R.id.tv_selectsize);
        TextView tv_viewsize_guide = dialog.findViewById(R.id.tv_viewsize_guide);
        LinearLayout iv_close_size = dialog.findViewById(R.id.iv_close_size);
        HomebannerModelList.clear();

        tv_selectsize.setTypeface(SplashActivity.montserrat_medium);
//        tv_viewsize_guide.setTypeface(SplashActivity.montserrat_medium);
        for (int i=0;i <4;i++)
        {
            HomebannerModelList.add(new HomebannerModel(""));
        }

        productSizeGuideAdapter = new ProductSizeGuideAdapter(getActivity(), HomebannerModelList);
        recv_size.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_size.setAdapter(productSizeGuideAdapter);



        iv_close_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
