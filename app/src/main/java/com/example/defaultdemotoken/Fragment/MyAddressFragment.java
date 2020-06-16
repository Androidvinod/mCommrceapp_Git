package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;

import java.io.Serializable;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressFragment extends Fragment implements View.OnClickListener {
    View v;

    Toolbar toolbar_account_info;
    LinearLayout lv_edit_user_info,lv_edit_address;

    TextView tv_username,tv_email_main,tv_titleinfo,tv_full,tv_fullname,tv_emailll,tv_email,tv_phone,tv_number,tv_myadressess,tv_kwaitaddree,tv_address;

    public MyAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_my_address, container, false);
        AllocateMemory();

        setHasOptionsMenu(true);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_account_info);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        lv_edit_user_info.setOnClickListener(this);
        lv_edit_address.setOnClickListener(this);

        tv_username.setText(Login_preference.getfirstname(getActivity()) +" "+ Login_preference.getlastname(getActivity()));
        tv_fullname.setText(Login_preference.getfirstname(getActivity()) +" "+ Login_preference.getlastname(getActivity()));
        tv_email_main.setText(Login_preference.getemail(getActivity()));
        tv_email.setText(Login_preference.getemail(getActivity()));

        return v;
    }

    private void AllocateMemory() {
        toolbar_account_info=v.findViewById(R.id.toolbar_account_info);
        lv_edit_user_info=v.findViewById(R.id.lv_edit_user_info);
        lv_edit_address=v.findViewById(R.id.lv_edit_address);
        tv_username=v.findViewById(R.id.tv_username);
        tv_email_main=v.findViewById(R.id.tv_email_main);
        tv_titleinfo=v.findViewById(R.id.tv_titleinfo);
        tv_full=v.findViewById(R.id.tv_full);
        tv_fullname=v.findViewById(R.id.tv_fullname);
        tv_emailll=v.findViewById(R.id.tv_emailll);
        tv_email=v.findViewById(R.id.tv_email);
        tv_phone=v.findViewById(R.id.tv_phone);
        tv_number=v.findViewById(R.id.tv_number);
        tv_myadressess=v.findViewById(R.id.tv_myadressess);
        tv_kwaitaddree=v.findViewById(R.id.tv_kwaitaddree);
        tv_address=v.findViewById(R.id.tv_address);


        tv_username.setTypeface(SplashActivity.montserrat_regular);
        tv_email_main.setTypeface(SplashActivity.montserrat_regular);
        tv_titleinfo.setTypeface(SplashActivity.montserrat_semibold);
        tv_address.setTypeface(SplashActivity.montserrat_regular);
        tv_kwaitaddree.setTypeface(SplashActivity.montserrat_medium);
        tv_myadressess.setTypeface(SplashActivity.montserrat_semibold);
        tv_number.setTypeface(SplashActivity.montserrat_regular);
        tv_phone.setTypeface(SplashActivity.montserrat_regular);
        tv_email.setTypeface(SplashActivity.montserrat_regular);
        tv_emailll.setTypeface(SplashActivity.montserrat_regular);
        tv_fullname.setTypeface(SplashActivity.montserrat_regular);
        tv_full.setTypeface(SplashActivity.montserrat_regular);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //    inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.framlayout, fragment);
                ft.addToBackStack(add_to_backstack);

                ft.commit();
            }
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
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

    @Override
    public void onClick(View v) {
        if(v==lv_edit_user_info)
        {
            Bundle b=new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen","edit detail");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        }else if(v==lv_edit_address)
        {
            Bundle b=new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen","edit address");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();
        }
    }
}
