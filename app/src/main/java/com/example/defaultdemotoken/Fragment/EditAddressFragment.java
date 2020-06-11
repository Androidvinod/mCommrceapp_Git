package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditAddressFragment extends Fragment {

    View v;
    Toolbar toolbar_edit;
    TextView tv_mydetails,tv_save;
    LinearLayout lv_edit_detail,lv_main_edit;

    TextInputLayout layout_details_email,layout_details_address,layout_details_city,layout_details_country,layout_details_postalcode,layout_details_phoneno,layout_detail_fullname,layout_detail_lastname;

    EditText et_deails_email,et_deails_postal,et_deails_country,et_deails_city,et_deails_address,et_details_fullname,et_details_lastname;

    Bundle b;
    String screen;

    public EditAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_edit_address, container, false);
        AllocateMemory(v);
        setupUI(lv_main_edit);

        setHasOptionsMenu(true);


        b=this.getArguments();
        if(b!=null)
        {
            screen=b.getString("screen");
            Log.e("debug_33","="+screen);
        }


        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_edit);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);



        if(screen.equalsIgnoreCase("edit address"))
        { tv_mydetails.setText(getActivity().getResources().getString(R.string.editadd));
            layout_details_address.setVisibility(View.VISIBLE);
            layout_details_city.setVisibility(View.VISIBLE);
            layout_details_country.setVisibility(View.VISIBLE);
            layout_details_postalcode.setVisibility(View.VISIBLE);

        }else if(screen.equalsIgnoreCase("edit detail"))
        {
            tv_mydetails.setText(getActivity().getResources().getString(R.string.editdetails));
            layout_details_address.setVisibility(View.GONE);
            layout_details_city.setVisibility(View.GONE);
            layout_details_country.setVisibility(View.GONE);
            layout_details_postalcode.setVisibility(View.GONE);
        }

        return v;
    }

    private void AllocateMemory(View v) {

        lv_main_edit=v.findViewById(R.id.lv_main_edit);
        et_deails_email=v.findViewById(R.id.et_deails_email);
        layout_details_email=v.findViewById(R.id.layout_details_email);
        et_details_lastname=v.findViewById(R.id.et_details_lastname);
        layout_detail_lastname=v.findViewById(R.id.layout_detail_lastname);
        et_details_fullname=v.findViewById(R.id.et_details_fullname);
        layout_detail_fullname=v.findViewById(R.id.layout_detail_fullname);
        layout_details_phoneno=v.findViewById(R.id.layout_details_phoneno);
        et_deails_city=v.findViewById(R.id.et_deails_city);
        layout_details_postalcode=v.findViewById(R.id.layout_details_postalcode);
        toolbar_edit=v.findViewById(R.id.toolbar_edit);
        tv_mydetails=v.findViewById(R.id.tv_mydetails);
        lv_edit_detail=v.findViewById(R.id.lv_edit_detail);
        tv_save=v.findViewById(R.id.tv_save);
        layout_details_address=v.findViewById(R.id.layout_details_address);
        layout_details_city=v.findViewById(R.id.layout_details_city);
        layout_details_country=v.findViewById(R.id.layout_details_country);
        et_deails_postal=v.findViewById(R.id.et_deails_postal);
        et_deails_country=v.findViewById(R.id.et_deails_country);
        layout_details_country=v.findViewById(R.id.layout_details_country);
        et_deails_address=v.findViewById(R.id.et_deails_address);



        tv_save.setTypeface(SplashActivity.montserrat_semibold);
        tv_mydetails.setTypeface(SplashActivity.montserrat_semibold);
        et_deails_address.setTypeface(SplashActivity.montserrat_medium);
        et_deails_country.setTypeface(SplashActivity.montserrat_medium);
        et_deails_postal.setTypeface(SplashActivity.montserrat_medium);

        et_deails_city.setTypeface(SplashActivity.montserrat_medium);
        et_details_fullname.setTypeface(SplashActivity.montserrat_medium);
        et_details_lastname.setTypeface(SplashActivity.montserrat_medium);
        et_deails_email.setTypeface(SplashActivity.montserrat_medium);
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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


}
