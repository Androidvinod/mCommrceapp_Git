package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.R;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {
    public  Toolbar toolbar_cart;

    public CartListFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_cart_list, container, false);
        bottom_navigation.getMenu().getItem(3).setChecked(true);
        AllocateMemory(v);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_cart);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_cart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });
        return v;

    }

    private void AllocateMemory(View v) {
        toolbar_cart = v.findViewById(R.id.toolbar_cart);
    }
}
