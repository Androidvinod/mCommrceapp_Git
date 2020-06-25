package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAddressFragment extends Fragment implements View.OnClickListener {
    View v;

    Toolbar toolbar_account_info;
    LinearLayout lv_edit_user_info,lv_edit_address,lv_progress_myadd,lv_add_new_address,lv_address,lv_addresssss,lv_delete_add,lv_main_fullname;

    TextView tv_add_new_add,tv_nodata,tv_username,tv_email_main,tv_titleinfo,tv_full,tv_fullname,tv_emailll,tv_email,tv_phone,tv_number,tv_myadressess,tv_kwaitaddree,tv_address;
    ScrollView scroll_myadd;
    String add_id;
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
        lv_add_new_address.setOnClickListener(this);
        lv_delete_add.setOnClickListener(this);


        if(Login_preference.getfirstname(getActivity()) == null  || Login_preference.getfirstname(getActivity()).equalsIgnoreCase("null"))
        {
            tv_username.setText("");
            tv_fullname.setText("");
            tv_full.setVisibility(View.GONE);
            tv_fullname.setVisibility(View.GONE);
            lv_main_fullname.setVisibility(View.GONE);
        }else {
            tv_username.setText(Login_preference.getfirstname(getActivity()) +" "+Login_preference.getlastname(getActivity()));
            tv_fullname.setText(Login_preference.getfirstname(getActivity()) +" "+Login_preference.getlastname(getActivity()));

        }
         tv_email_main.setText(Login_preference.getemail(getActivity()));
        tv_email.setText(Login_preference.getemail(getActivity()));
        tv_number.setText(Login_preference.getphone(getActivity()));

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            //CallGetWishlistApi(page_no);
            CallAddressApi();

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }



        return v;
    }

    private void CallAddressApi() {
        lv_progress_myadd.setVisibility(View.VISIBLE);
        scroll_myadd.setVisibility(View.GONE);
        calladdressgapi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response",""+response.body());
                Log.e("response_77",""+response);
                Log.e("status",""+response.body());

                if (response.code()==200) {

                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);
                    Log.e("response_77", "" + response);
                    Log.e("status", "=" + response.body());


                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        Log.e("debug_jsonobj", "=" + jsonObject);
                        JSONArray jsonArray =jsonObject.getJSONArray("addresses");

                        if(jsonArray.length()==0)
                        {
                        //    tv_nodata.setVisibility(View.VISIBLE);
                            lv_addresssss.setVisibility(View.GONE);
                            tv_address.setVisibility(View.GONE);
                            lv_edit_address.setVisibility(View.GONE);
                            lv_add_new_address.setVisibility(View.VISIBLE);

                        }else {
                          //  tv_nodata.setVisibility(View.GONE);
                            tv_address.setVisibility(View.VISIBLE);
                            lv_addresssss.setVisibility(View.VISIBLE);
                            lv_edit_address.setVisibility(View.VISIBLE);
                            lv_add_new_address.setVisibility(View.GONE);

                            for(int i=0;i<jsonArray.length();i++)
                            {

                                JSONObject object=jsonArray.getJSONObject(0);

                                if(getActivity()!=null)
                                {
                                  /*  if(jsonObject.optString("firstname") == null  || jsonObject.optString("firstname").equalsIgnoreCase("null"))
                                    {
                                        tv_username.setText("");
                                        tv_fullname.setText("");
                                        tv_full.setVisibility(View.GONE);
                                        tv_fullname.setVisibility(View.GONE);
                                        lv_main_fullname.setVisibility(View.GONE);
                                    }else {
                                        tv_username.setText(jsonObject.optString("firstname") +" "+jsonObject.optString("lastname"));
                                        tv_fullname.setText(jsonObject.optString("firstname") +" "+jsonObject.optString("lastname"));
                                        Login_preference.setfirstname(getActivity(),jsonObject.optString("firstname"));
                                        Login_preference.setlastname(getActivity(),jsonObject.optString("lastname"));

                                    }*/
                                    tv_number.setText(object.optString("telephone"));
                                    tv_email_main.setText(Login_preference.getemail(getActivity()));
                                    tv_email.setText(Login_preference.getemail(getActivity()));

                                         Login_preference.setphone(getActivity(),object.optString("telephone"));
                                    Login_preference.setemail(getActivity(),jsonObject.optString("email"));
                                    add_id=object.optString("id");

                                }

                                Log.e("debuaddid","="+add_id);
                               JSONArray streetarray=object.getJSONArray("street");

                               String street= String.valueOf(streetarray.get(0));
                               Log.e("debuh_street=","="+street);
                                tv_address.setText(object.optString("firstname")+" "+
                                        object.optString("lastname")+"\n"+street+" , "
                                        + object.optString("city")+", "+object.optString("postcode")+"\n"+"T :"+object.optString("telephone"));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    lv_progress_myadd.setVisibility(View.GONE);
                    scroll_myadd.setVisibility(View.VISIBLE);

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Call<ResponseBody> calladdressgapi() {
        ApiInterface apiinterface = ApiClient.getClient().create(ApiInterface.class);
        Log.e("debug_111",""+Login_preference.getcustomer_id(getActivity()));
        String url="http://dkbraende.demoproject.info/rest//V1/customers/"+Login_preference.getcustomer_id(getActivity());
        return apiinterface.getAddress("Bearer "+Login_preference.gettoken(getActivity()),url);
    }

    private void AllocateMemory() {
        lv_main_fullname=v.findViewById(R.id.lv_main_fullname);
        lv_delete_add=v.findViewById(R.id.lv_delete_add);
        lv_addresssss=v.findViewById(R.id.lv_addresssss);
        lv_address=v.findViewById(R.id.lv_address);
        lv_add_new_address=v.findViewById(R.id.lv_add_new_address);
        tv_add_new_add=v.findViewById(R.id.tv_add_new_add);
        tv_nodata=v.findViewById(R.id.tv_nodata);
        scroll_myadd=v.findViewById(R.id.scroll_myadd);
        toolbar_account_info=v.findViewById(R.id.toolbar_account_info);
        lv_progress_myadd=v.findViewById(R.id.lv_progress_myadd);
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
        tv_add_new_add.setTypeface(SplashActivity.montserrat_semibold);
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
            b.putString("address","edit");
            b.putString("address_id",add_id);

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();
        }else if(v==lv_add_new_address)
        {
            Bundle b=new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            b.putString("screen","edit address");
            b.putString("address","create");

            EditAddressFragment myFragment = new EditAddressFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();
        }else if(v==lv_delete_add)
        {
            Log.e("debuaddid55","="+add_id);
            if (CheckNetwork.isNetworkAvailable(getActivity())) {
                DelerteAddressApi(add_id);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Call<Boolean> delerteAddressApi(String add_id) {
        ApiInterface  customeapi = ApiClient.getClient().create(ApiInterface.class);

        String url="http://dkbraende.demoproject.info/rest/V1/addresses/"+add_id;
        Log.e("debug_url","="+url);
        Log.e("token","="+Login_preference.gettoken(getActivity()));

        return customeapi.deleteaddress("Bearer "+Login_preference.gettoken(getActivity()),url);
    }


    private void DelerteAddressApi(String add_id) {
        lv_progress_myadd.setVisibility(View.VISIBLE);
        scroll_myadd.setVisibility(View.GONE);
        delerteAddressApi(add_id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                lv_progress_myadd.setVisibility(View.GONE);
                scroll_myadd.setVisibility(View.VISIBLE);

                Log.e("debug1111",""+ response.body());
                Log.e("debug1111dsgdf",""+ response);

                if(response.code()==200 || response.isSuccessful())
                {
                    if(response.body()==true)
                    {
                        Toast.makeText(getActivity(), "Address Deleted Successfully", Toast.LENGTH_SHORT).show();
                        pushFragment(new MyAddressFragment() ,"account");
                    }
                }else {
                 //   Toast.makeText(getActivity(), "The password doesn't match this account. Verify the password and try again.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                lv_progress_myadd.setVisibility(View.GONE);
                scroll_myadd.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), ""+getActivity().getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
