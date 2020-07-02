package com.example.defaultdemotoken.Model;

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
import com.example.defaultdemotoken.Adapter.PaymentAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.DeliveryModel;
import com.example.defaultdemotoken.Model.PaymentModel;
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
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_address;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_delivery;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_payments;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_payments_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_payments_grey;

public class Payments extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView payment_recyclerview;
    private List<PaymentModel> paymentModels = new ArrayList<PaymentModel>();
    private PaymentAdapter paymentAdapter;
    LinearLayout lv_payment_main,lv_payment_progress,lv_payment_back;
    public static ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_payments, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        AllocateMemory();
        AttachRecyclerview();

        rad_delivery.setChecked(false);
        rad_address.setChecked(false);
        rad_payments.setChecked(true);

        view_address_last_grey.setVisibility(View.GONE);
        view_address_last_green.setVisibility(View.VISIBLE);

        view_payments_grey.setVisibility(View.GONE);
        view_payments_green.setVisibility(View.VISIBLE);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CallPaymentApi();
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }


        lv_payment_back.setOnClickListener(this);

        return v;
    }

    private void CallPaymentApi() {
        paymentModels.clear();
        lv_payment_progress.setVisibility(View.VISIBLE);
        lv_payment_main.setVisibility(View.GONE);
        callPaymentMethodsApi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                lv_payment_progress.setVisibility(View.GONE);
                lv_payment_main.setVisibility(View.VISIBLE);
                Log.e("payment_method_response", "" + response);

                if (response.isSuccessful() || response.code() == 200) {
                    JSONArray itemArray = null;
                    try {
                        itemArray = new JSONArray(response.body().string());
                        Log.e("payment_method__response_array", "" + itemArray);

                        for (int i = 0; i < itemArray.length(); i++) {
                            try {
                                JSONObject vals = itemArray.getJSONObject(i);

                                String payment_code = vals.getString("code");
                                Log.e("payment_code", "" + payment_code);

                                paymentModels.add(new PaymentModel(vals.getString("code"), vals.getString("title")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {

                                paymentAdapter.notifyItemChanged(i);

                            }
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lv_payment_progress.setVisibility(View.GONE);
                lv_payment_main.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "" + t.getMessage());
            }
        });
    }

    public Call<ResponseBody> callPaymentMethodsApi() {
        return apiInterface.getPaymentMethod("Bearer " + Login_preference.gettoken(getActivity()), "http://dkbraende.demoproject.info/rest//V1/carts/"+"192001"+"/payment-methods");
    }

    private void AllocateMemory() {
        lv_payment_back = v.findViewById(R.id.lv_payment_back);
        payment_recyclerview = v.findViewById(R.id.payment_recyclerview);
        lv_payment_main = v.findViewById(R.id.lv_payment_main);
        lv_payment_progress = v.findViewById(R.id.lv_payment_progress);
    }

    @Override
    public void onClick(View v) {
        if (v==lv_payment_back){
            pushFragment(new Checkout_Address_Fragment(),"checkout_address");
        }
    }

    private void AttachRecyclerview() {
        paymentAdapter = new PaymentAdapter(getActivity(), paymentModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        payment_recyclerview.setLayoutManager(layoutManager);
        payment_recyclerview.setAdapter(paymentAdapter);
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