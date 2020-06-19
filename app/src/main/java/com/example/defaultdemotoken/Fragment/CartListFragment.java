package com.example.defaultdemotoken.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.CartlistAdapter_new;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;

import com.example.defaultdemotoken.Model.CartListModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {

    View v;
    public Toolbar toolbar_cart;
    public static RecyclerView recv_cart;
    public static ApiInterface apiInterface;
    public static CartlistAdapter_new cartlistAdapter;//CartlistAdapter
    private Paint p = new Paint();
    String url;
    public static Call<ResponseBody> cartlistt = null;
    public static List<CartListModel> cart_models = new ArrayList<CartListModel>();
    NavigationActivity parent;
    public static LinearLayout lv_cartlist_progress,lv_cart_checkout;
    public static CoordinatorLayout cordinator_cart;
    public static Context context;
    static LinearLayout lv_nodata_cart;

    public CartListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cart_list, container, false);
        parent = (NavigationActivity) getActivity();
        bottom_navigation.getMenu().getItem(3).setChecked(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        context = getActivity();
        AllocateMemory(v);
        cart_models.clear();

        url = "http://dkbraende.demoproject.info/rest/V1/carts/mine/items";
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_cart);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        //demo
        toolbar_cart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        AttachRecyclerView();
        //initSwipe();

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallCartlistApi();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }



        lv_cart_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == lv_cart_checkout) {
                    Log.e("check_flag",""+CartlistAdapter_new.flag);
                    if (CartlistAdapter_new.flag == false) {
                        Toast.makeText(context, context.getString(R.string.quantity_messge), Toast.LENGTH_SHORT).show();
                    } else {
                       // pushFragment(new CheckoutFragment(), "Checkout");
                    }

                }
            }
        });
        return v;

    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    Log.e("debug_224", "wishlist");
                    //cartlistAdapter.addToWishlistItem(position);
                    cartlistAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                } else {
                    Log.e("debug_227", "reove");
                    //mdeletecartitem(viewHolder, direction);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#06466c"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(parent.getResources(), R.drawable.acoountt);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(parent.getResources(), R.drawable.acc);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recv_cart);
    }

    private void mdeletecartitem(final RecyclerView.ViewHolder viewHolder, final int direction) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getActivity());
        SpannableString spannableString = new SpannableString(getActivity().getResources().getString(R.string.accinfo));
        builder.setTitle(spannableString);
        builder.setCancelable(false);

        builder.setPositiveButton(getActivity().getResources().getString(R.string.accinfo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    //cartlistAdapter.removeItem(position);
                } else {
                    //     removeView();
                    cartlistAdapter.notifyDataSetChanged();
                }
            }
        });

        // Set the Negative button with No name
        builder.setNegativeButton(getResources().getString(R.string.accinfo), new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartlistAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                //CallGetWishlistApi();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void CallCartlistApi() {


        cart_models.clear();
        cordinator_cart.setVisibility(View.GONE);
        lv_cartlist_progress.setVisibility(View.VISIBLE);

        callcartlistapi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                cordinator_cart.setVisibility(View.VISIBLE);
                lv_cartlist_progress.setVisibility(View.GONE);
                Log.e("cartlist_response", "" + response.body());

                JSONArray itemArray = null;
                try {
                    itemArray = new JSONArray(response.body().string());
                    Log.e("cartresponseeeee", "" + itemArray);

                    if (itemArray.equals("[]")||itemArray.length()==0){
                        lv_nodata_cart.setVisibility(View.VISIBLE);
                        cordinator_cart.setVisibility(View.GONE);
                        lv_cartlist_progress.setVisibility(View.GONE);
                    }else {
                        lv_nodata_cart.setVisibility(View.GONE);
                        cordinator_cart.setVisibility(View.VISIBLE);
                        lv_cartlist_progress.setVisibility(View.GONE);
                        for (int i = 0; i < itemArray.length(); i++) {
                            try {
                                JSONObject vals = itemArray.getJSONObject(i);
                                Log.e("cartarrvals", "" + vals);
                                String valsmain = vals.getString("name");
                                Log.e("nameeeecartprod", "" + valsmain);

                                String extension_attributes = vals.getString("extension_attributes");

                                JSONObject image = new JSONObject(extension_attributes);

                                String imageurl = image.getString("image_url");

                                Log.e("cart_image_url", "" + imageurl);

                                cart_models.add(new CartListModel(vals.getString("item_id"), vals.getString("sku"), vals.getString("qty"), vals.getString("name"),
                                        vals.getInt("price"), vals.getString("product_type"), vals.getString("quote_id"), imageurl));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                cartlistAdapter.notifyItemChanged(i);
                            }

                        }
                    }

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "" + context.getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);
            }
        });
    }

    /*private List<CartListModel> fetchResults(Response<CartListModel> response) {
        return (List<CartListModel>) response.body();
    }*/

    public static Call<ResponseBody> callcartlistapi() {
        Log.e("debugcustomertoen","="+Login_preference.getCustomertoken(context));
        return apiInterface.getcartlist("Bearer " + Login_preference.getCustomertoken(context));
    }

    /*private List<ExtensionAttributes> fetchResults(Response<CartListModel> response) {
        CartListModel cartlist = response.body();
        ///tv_cart_subtotal.setText(cartlist.getGrandTotal());
        return (List<ExtensionAttributes>) cartlist.getExtensionAttributes();
    }*/


    private void AttachRecyclerView() {

        /*cartlistAdapter = new CartlistAdapter_new(getActivity());//CartlistAdapter
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), WrapContentLinearLayoutManager.VERTICAL, false);
        recv_cart.setLayoutManager(layoutManager);
        recv_cart.setAdapter(cartlistAdapter);*/

        cartlistAdapter = new CartlistAdapter_new(getActivity(), cart_models);
        recv_cart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_cart.setAdapter(cartlistAdapter);

    }

    private void AllocateMemory(View v) {
        lv_cart_checkout = v.findViewById(R.id.lv_cart_checkout);
        toolbar_cart = v.findViewById(R.id.toolbar_cart);
        recv_cart = v.findViewById(R.id.recv_cart);
        lv_cartlist_progress = v.findViewById(R.id.lv_cartlist_progress);
        cordinator_cart = v.findViewById(R.id.cordinator_cart);
        lv_nodata_cart = v.findViewById(R.id.lv_nodata_cart);
      //  lv_cart_Main = v.findViewById(R.id.lv_cart_Main);
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

}
