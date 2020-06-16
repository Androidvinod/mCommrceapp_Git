package com.example.defaultdemotoken.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.R;


public class OrderSuccessful extends Fragment {

    View v;
    LinearLayout lv_back_to_home;
    TextView tv_your_order,tv_successful,tv_desc,tv_back_to_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_successful, container, false);

        AllocateMemeory(v);

        tv_your_order.setTypeface(SplashActivity.montserrat_semibold);
        tv_successful.setTypeface(SplashActivity.montserrat_semibold);
        tv_desc.setTypeface(SplashActivity.montserrat_medium);
        tv_back_to_home.setTypeface(SplashActivity.montserrat_semibold);

        lv_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void AllocateMemeory(View v) {
        lv_back_to_home = v.findViewById(R.id.lv_back_to_home);
        tv_your_order = v.findViewById(R.id.tv_your_order);
        tv_successful = v.findViewById(R.id.tv_successful);
        tv_desc = v.findViewById(R.id.tv_desc);
        tv_back_to_home = v.findViewById(R.id.tv_back_to_home);
    }
}