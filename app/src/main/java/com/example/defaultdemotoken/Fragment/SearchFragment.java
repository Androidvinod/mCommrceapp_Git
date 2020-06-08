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
public class SearchFragment extends Fragment {

    View v;
    Toolbar toolbar_Search;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_search, container, false);
        bottom_navigation.getMenu().getItem(0).setChecked(true);

        toolbar_Search=v.findViewById(R.id.toolbar_Search);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_Search);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_Search.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        return v;
    }
}
