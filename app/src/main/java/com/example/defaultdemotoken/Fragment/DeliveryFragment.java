package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.defaultdemotoken.Adapter.Checkout_Address_Fragment;
import com.example.defaultdemotoken.Adapter.DeliveryAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.DeliveryModel;
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
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;
import static com.example.defaultdemotoken.Adapter.DeliveryAdapter.MyViewHolder.rad_delivery_description;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_address;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_delivery;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_payments;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_grey;


public class DeliveryFragment extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView delivery_recyclerview ;

    private List<DeliveryModel> deliveryModels = new ArrayList<DeliveryModel>();
    private DeliveryAdapter deliveryAdapter;

    LinearLayout lv_delivery_progress,lv_delivery_main,lv_delivery_next;

    public static ApiInterface apiInterface;
    public  static String deliveryaddres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_delivery, container, false);
        deliveryaddres="";
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        rad_delivery.setChecked(true);
        rad_address.setChecked(false);
        rad_payments.setChecked(false);

        view_del_grey.setVisibility(View.VISIBLE);
        view_del_green.setVisibility(View.GONE);

        view_address_last_grey.setVisibility(View.VISIBLE);
        view_address_last_green.setVisibility(View.GONE);

        AllocateMemory();
        AttachRecyclerview();

        if (CheckNetwork.isNetworkAvailable(getActivity())) {

            CallDeliveryApi();

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

        lv_delivery_next.setOnClickListener(this);

        return v;

    }

    private void CallDeliveryApi() {
        lv_delivery_progress.setVisibility(View.VISIBLE);
        lv_delivery_main.setVisibility(View.GONE);
        deliveryModels.clear();
        callshippingmethodsApi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_delivery_progress.setVisibility(View.GONE);
                lv_delivery_main.setVisibility(View.VISIBLE);
                Log.e("deliveryresponse", "" + response);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray itemArray = null;
                    try {
                        itemArray = new JSONArray(response.body().string());
                        Log.e("delivery_response_array", "" + itemArray);

                        for (int i = 0; i < itemArray.length(); i++) {
                            try {
                                JSONObject vals = itemArray.getJSONObject(i);

                                String carrier_code = vals.getString("carrier_code");
                                Log.e("delivery_carrier_code", "" + carrier_code);

                                deliveryModels.add(new DeliveryModel(vals.getString("carrier_code"), vals.getString("method_code"), vals.getString("carrier_title"),
                                        vals.getString("method_title"), vals.getString("amount"),vals.getString("base_amount"),vals.getString("available"),
                                        vals.getString("error_message"),vals.getString("price_excl_tax"), vals.getString("price_incl_tax")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                deliveryAdapter.notifyItemChanged(i);
                            }
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_delivery_progress.setVisibility(View.GONE);
                lv_delivery_main.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "" + t.getMessage());
            }
        });
    }

    public Call<ResponseBody> callshippingmethodsApi() {
        return apiInterface.getShippingMethod("Bearer " + Login_preference.gettoken(getActivity()), "http://dkbraende.demoproject.info/rest//V1/carts/"+"192001"+"/shipping-methods");
    }

    private void AllocateMemory() {
        delivery_recyclerview = v.findViewById(R.id.delivery_recyclerview);
        lv_delivery_main = v.findViewById(R.id.lv_delivery_main);
        lv_delivery_progress = v.findViewById(R.id.lv_delivery_progress);
        lv_delivery_next = v.findViewById(R.id.lv_delivery_next);
    }

    private void AttachRecyclerview() {
        deliveryAdapter = new DeliveryAdapter(getActivity(), deliveryModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        delivery_recyclerview.setLayoutManager(layoutManager);
        delivery_recyclerview.setAdapter(deliveryAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == lv_delivery_next) {

            if (rad_delivery_description.isChecked()==true){

                Bundle b = new Bundle();
                b.putString("delivery_address", deliveryaddres);
                Checkout_Address_Fragment myFragment = new Checkout_Address_Fragment();
                myFragment.setArguments(b);
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out)
                        .setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out)
                        .add(R.id.frameLayout_checkout, myFragment)
                        .addToBackStack("checkout_address").commit();

            }else {
                Toast.makeText(getActivity(), "Please select anyone shipping method...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.frameLayout_checkout, fragment);
                ft.addToBackStack(add_to_backstack);
                ft.commit();
            }
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}