package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    View v;
    Toolbar toolbar_changepsw;
    TextView tv_change;
    TextInputLayout layout_current_password,layout_new_password,layout_confirm_password;
    TextInputEditText et_current_password,et_new_password,et_confirm_password;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_change_password, container, false);

        allocateMemory();
        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_changepsw);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);


        return v;
    }

    private void allocateMemory() {
        toolbar_changepsw=v.findViewById(R.id.toolbar_changepsw);
        layout_confirm_password=v.findViewById(R.id.layout_confirm_password);
        layout_current_password=v.findViewById(R.id.layout_current_password);
        et_current_password=v.findViewById(R.id.et_current_password);
        layout_new_password=v.findViewById(R.id.layout_new_password);
        et_new_password=v.findViewById(R.id.et_new_password);
        et_confirm_password=v.findViewById(R.id.et_confirm_password);
        tv_change=v.findViewById(R.id.tv_change);



        tv_change.setTypeface(SplashActivity.montserrat_semibold);
        et_confirm_password.setTypeface(SplashActivity.montserrat_medium);
        et_current_password.setTypeface(SplashActivity.montserrat_regular);
        et_new_password.setTypeface(SplashActivity.montserrat_regular);
        layout_confirm_password.setTypeface(SplashActivity.montserrat_regular);
        layout_current_password.setTypeface(SplashActivity.montserrat_regular);
        layout_new_password.setTypeface(SplashActivity.montserrat_regular);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}










