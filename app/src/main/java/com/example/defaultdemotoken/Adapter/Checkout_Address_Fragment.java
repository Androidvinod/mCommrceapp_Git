package com.example.defaultdemotoken.Adapter;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.defaultdemotoken.Fragment.DeliveryFragment;
import com.example.defaultdemotoken.Model.Payments;
import com.example.defaultdemotoken.R;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_address;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_delivery;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.rad_payments;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_first_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_first_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_address_last_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_del_grey;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_payments_green;
import static com.example.defaultdemotoken.Fragment.CheckoutFragment.view_payments_grey;

public class Checkout_Address_Fragment extends Fragment implements View.OnClickListener {

    View v;
    LinearLayout lv_address_back,lv_address_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_checkout__address, container, false);

        AllocateMemory();

        rad_delivery.setChecked(false);
        rad_address.setChecked(true);
        rad_payments.setChecked(false);

        view_del_grey.setVisibility(View.GONE);
        view_del_green.setVisibility(View.VISIBLE);

        view_address_first_grey.setVisibility(View.GONE);
        view_address_first_green.setVisibility(View.VISIBLE);

        view_address_last_grey.setVisibility(View.VISIBLE);
        view_address_last_green.setVisibility(View.GONE);

        view_payments_grey.setVisibility(View.VISIBLE);
        view_payments_green.setVisibility(View.GONE);

        lv_address_back.setOnClickListener(this);
        lv_address_next.setOnClickListener(this);

        return v;
    }

    private void AllocateMemory() {
        lv_address_back = v.findViewById(R.id.lv_address_back);
        lv_address_next = v.findViewById(R.id.lv_address_next);
    }

    @Override
    public void onClick(View v) {
        if (v==lv_address_back){
            pushFragment(new DeliveryFragment(),"delivery");
        }
        if (v==lv_address_next){
            pushFragment(new Payments(),"payment");
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