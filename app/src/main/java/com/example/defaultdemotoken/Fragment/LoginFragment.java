package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    View v;
    Toolbar toolbar_login;
    TextView tv_welcome,tv_continue,tv_forgetpsw,tv_sign_in_login,tv_or,tv_fb,tv_google,tv_login;
    TextInputLayout layout_login_email,layout_login_password;
    TextInputEditText et_login_email,et_login_password;
    LinearLayout lv_sign_in,lv_fb_login,lv_google_login,lv_login_main,lv_progress_login;
    NestedScrollView nestedscrol_login;
    ApiInterface api,customeapi;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_login, container, false);
        AllocateMemory(v);
        setHasOptionsMenu(true);
        setupUI(lv_login_main);
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_login);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        customeapi = ApiClient.getClient().create(ApiInterface.class);
        lv_sign_in.setOnClickListener(this);
        return v;
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

    private void AllocateMemory(View v) {
        nestedscrol_login=v.findViewById(R.id.nestedscrol_login);
        lv_progress_login=v.findViewById(R.id.lv_progress_login);
        lv_login_main=v.findViewById(R.id.lv_login_main);
        tv_login=v.findViewById(R.id.tv_login);
        tv_google=v.findViewById(R.id.tv_google);
        lv_fb_login=v.findViewById(R.id.lv_fb_login);
        toolbar_login=v.findViewById(R.id.toolbar_login);
        tv_welcome=v.findViewById(R.id.tv_welcome);
        tv_continue=v.findViewById(R.id.tv_continue);
        layout_login_email=v.findViewById(R.id.layout_login_email);
        et_login_email=v.findViewById(R.id.et_login_email);
        layout_login_password=v.findViewById(R.id.layout_login_password);
        et_login_password=v.findViewById(R.id.et_login_password);
        tv_forgetpsw=v.findViewById(R.id.tv_forgetpsw);
        lv_sign_in=v.findViewById(R.id.lv_sign_in);
        tv_sign_in_login=v.findViewById(R.id.tv_sign_in_login);
        tv_or=v.findViewById(R.id.tv_or);
        tv_fb=v.findViewById(R.id.tv_fb);
        lv_google_login=v.findViewById(R.id.lv_google_login);


        tv_welcome.setTypeface(SplashActivity.montserrat_extrabold);
        tv_continue.setTypeface(SplashActivity.montserrat_regular);
        layout_login_email.setTypeface(SplashActivity.montserrat_regular);
        layout_login_password.setTypeface(SplashActivity.montserrat_regular);
        et_login_email.setTypeface(SplashActivity.montserrat_regular);
        et_login_password.setTypeface(SplashActivity.montserrat_regular);
        tv_forgetpsw.setTypeface(SplashActivity.montserrat_regular);
        tv_or.setTypeface(SplashActivity.montserrat_medium);
        tv_google.setTypeface(SplashActivity.montserrat_medium);
        tv_fb.setTypeface(SplashActivity.montserrat_medium);
        tv_login.setTypeface(SplashActivity.montserrat_bold);
        tv_sign_in_login.setTypeface(SplashActivity.montserrat_semibold);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
       // inflater.inflate(R.menu.menu_cart, menu);
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

    @Override
    public void onClick(View v) {
        if(v==lv_sign_in)
        {
            validateData();
        }
    }

    private void validateData() {
     String   email = et_login_email.getText().toString();
       String password = et_login_password.getText().toString();

        if (et_login_email.getText().length() == 0) {

            et_login_email.setError(getActivity().getResources().getString(R.string.enteremailaddress));
            et_login_email.requestFocus();

        }else if(isValidEmailAddress(et_login_email.getText().toString())==false)
        {
            et_login_email.setError(getActivity().getResources().getString(R.string.validemail));
            et_login_email.requestFocus();
        } else if (et_login_password.getText().length() == 0) {

            et_login_password.setError(getActivity().getResources().getString(R.string.enterpw));
            et_login_password.requestFocus();

        } else if (isValidPassword(et_login_password.getText().toString()) == false) {
            et_login_password.requestFocus();

            Toast.makeText(getContext(), "" + getResources().getString(R.string.validpassword_validate), Toast.LENGTH_SHORT).show();

        }else {
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                CallLoginApi(email,password);
                //Loginapi(email, password);
            } else {
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void CallLoginApi(String email, String password) {
        get_Customer_tokenapi(email,password);
    }

    private void get_Customer_tokenapi(String email, String password) {
        nestedscrol_login.setVisibility(View.GONE);
        lv_progress_login.setVisibility(View.VISIBLE);
        Log.e("response201tokenff",""+ Login_preference.gettoken(getActivity()));
        Log.e("email",""+email);
        Log.e("password",""+password);
        String url="http://dkbraende.demoproject.info/rest/V1/integration/customer/token?username="+email+"&password="+password;
        Call<String> customertoken = customeapi.getcustomerToken(url);
        customertoken.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("response20066",""+response.toString());
                Log.e("response20166",""+response.body());
                Login_preference.setCustomertoken(getActivity(),response.body());
                CallCustomerData();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                nestedscrol_login.setVisibility(View.GONE);
                lv_progress_login.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void CallCustomerData() {
        Call<ResponseBody> customertoken = customeapi.loginn("Bearer "+Login_preference.getCustomertoken(getActivity()));

        customertoken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("res_login",""+response.toString());
                Log.e("res_login",""+response.body());
                if(response.code()==200)
                {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        Log.e("jsonObject","fff"+jsonObject);

                        String email=jsonObject.getString("email");
                        String firstname=jsonObject.getString("firstname");
                        String lastname=jsonObject.getString("lastname");
                        String id=jsonObject.getString("id");
                        Log.e("email","fff"+email);
                        Log.e("firstname","fff"+firstname);
                        Log.e("iddd","fff"+id);
                        Login_preference.setLogin_flag(getActivity(), "1");
                        Login_preference.setcustomer_id(getActivity(), id);
                        Login_preference.setemail(getActivity(),email);
                        Login_preference.setfirstname(getActivity(), firstname);
                        Login_preference.setlastname(getActivity(), lastname);

                        Intent intent=new Intent(getActivity(),NavigationActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                    nestedscrol_login.setVisibility(View.GONE);
                    lv_progress_login.setVisibility(View.GONE);
                }
                else {
                    nestedscrol_login.setVisibility(View.VISIBLE);
                    lv_progress_login.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                nestedscrol_login.setVisibility(View.GONE);
                lv_progress_login.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    private boolean isValidEmailAddress(String toString) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(toString);
        return m.matches();
    }
}
