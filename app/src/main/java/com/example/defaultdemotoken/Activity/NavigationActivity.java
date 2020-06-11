package com.example.defaultdemotoken.Activity;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.defaultdemotoken.Fragment.CartListFragment;
import com.example.defaultdemotoken.Fragment.HomeFragment;

import com.example.defaultdemotoken.Adapter.ChildDataAdapter;
import com.example.defaultdemotoken.CheckNetwork;
import com.example.defaultdemotoken.Fragment.MyAccountFragment;
import com.example.defaultdemotoken.Fragment.SearchFragment;
import com.example.defaultdemotoken.Fragment.WishlistFragment;
import com.example.defaultdemotoken.Login_preference;
import com.example.defaultdemotoken.Model.CategoriesModel;
import com.example.defaultdemotoken.Model.ChildData;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Retrofit.ApiClient;
import com.example.defaultdemotoken.Retrofit.ApiInterface;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView recv_category;
    //public static Toolbar toolbar;
    ImageView iv_navtoolbar_logo;
    public static DrawerLayout drawer;
    NavigationView navigationView;
    RelativeLayout relative_layout;
    public static BottomNavigationView bottom_navigation;
    public static AssetManager am;
    public static Typeface montserrat_medium, montserrat_regular, montserrat_semibold, montserratbold, montserrat_light;
    boolean doubleBackToExitPressedOnce = false;
    ApiInterface api;
    ImageView nav_iv_close;
    ChildDataAdapter categoryAdapter;
    public static TextView tv_bottomcount,tv_wishlist_count;
    LinearLayout lv_nodata_category,lv_home;
    String cartitem_count,wishlist_count;

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //setSupportActionBar(toolbar);
          //  changeToolbarFont(toolbar, Navigation_drawer_activity.this);
        AllocateMemory();

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.signs);

        View header = navigationView.getHeaderView(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(drawer)
                .build();
        mDrawerToggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_open) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("test");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("drawer title");
            }
        };
        drawer.addDrawerListener(mDrawerToggle);




         /*toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
    */
/*
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_open)
        {
            public void onDrawerClosed(View view)
            {
              //  getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        toggle.syncState();

        drawer.addDrawerListener(toggle);*/

        //  setNavigationIcon_headerview();
        Bootom_Navigation_view();
        AttachRecyclerView();
        bottom_navigation.setItemIconTintList(null);
        api = ApiClient.getClient().create(ApiInterface.class);

        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.white));
    //    bottom_navigation.setItemTextAppearanceActive(NavigationActivity.this,R .style.BottomNavigationVieww);
     //   bottom_navigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
       // bottom_navigation.setItemTextColor(getResources().getDrawable(R.drawable.bottom_selection));
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("");

        //setFontStyle();
        //SetTypeface();

        //bind data to recyclerview

        //calling api of categiry list for side menu
        if (CheckNetwork.isNetworkAvailable(NavigationActivity.this)) {
            getCategoryList();
        } else {
            //    noInternetDialog(NavigationActivity.this);
            Toast.makeText(NavigationActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }


    }
    public DrawerLayout getmDrawerLayout() {
        return drawer;
    }


    private void AttachRecyclerView() {
        categoryAdapter = new ChildDataAdapter(NavigationActivity.this);
        recv_category.setLayoutManager(new LinearLayoutManager(NavigationActivity.this));
        recv_category.setItemAnimator(new DefaultItemAnimator());
        recv_category.setAdapter(categoryAdapter);
    }
    private void getCategoryList() {
        lv_nodata_category.setVisibility(View.GONE);
        recv_category.setVisibility(View.VISIBLE);

        callCategoryApi().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
              //CategoriesModel CategoriesModel = response.body();

                if(response.isSuccessful() || response.code()==200)
                {

                    CategoriesModel model = response.body();
                    List<ChildData> results = fetchResults(response);
                    categoryAdapter.addAll(results);
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                // String error=  t.printStackTrace();
                Toast.makeText(NavigationActivity.this, "" + getResources().getString(R.string.wentwrong), Toast.LENGTH_SHORT).show();
                Log.e("debug_175125", "pages: " + t);
            }
        });
    }

    private Call<CategoriesModel> callCategoryApi() {
        Log.e("debug11w","ccvff"+ Login_preference.gettoken(NavigationActivity.this));
        return api.categories("Bearer "+Login_preference.gettoken(NavigationActivity.this));

    }
    private List<ChildData> fetchResults(Response<CategoriesModel> response) {
        Log.e("newin_home_209", "" + response.body());
        CategoriesModel CategoriesModel = response.body();
        return CategoriesModel.getChildrenData();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            return false;
        }
    };

    /*public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShifting(false);

                  ///  item .setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }*/
    private void Bootom_Navigation_view() {


        //code for big one icon size of bottom navigation item00
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(2).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }



     //   BottomNavigationViewHelper.disableShiftMode(bottom_navigation);

        bottom_navigation.setItemIconTintList(null);
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = bottom_navigation.getMenu();
        selectFragment(menu.getItem(2));

        //cartitem_count = Login_preference.getCart_item_count(NavigationActivity.this);
     //   BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);
    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
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

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.bottom_search:
                 pushFragment(new SearchFragment(), "search");
                 break;

            case R.id.bottom_wishlist:
                pushFragment(new WishlistFragment(), "wishlist");
                break;

            case R.id.bottom_home:
                HomeFragment fragment = new HomeFragment();
                if (fragment == null)
                    return;
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (ft != null) {
                        bottom_navigation.setVisibility(View.VISIBLE);
                        ft.replace(R.id.framlayout, fragment);

                        ft.commit();
                    }
                }
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.bottom_cart:
                pushFragment(new CartListFragment(), "cart");
                break;

            case R.id.bottom_account:
                pushFragment(new MyAccountFragment(), "account");
                break;
        }
    }

    public static void Check_String_NULL_Value(TextView textview, String text) {
        if (text == null|| text.equalsIgnoreCase("null") == true ) {
            //textview.setHint("");
            textview.setText("");
        } else {
            textview.setText(Html.fromHtml(NavigationActivity.Convert_String_First_Letter(text)));
        }
    }

    public static String Convert_String_First_Letter(String convert_string) {
        String upperString;

        if (convert_string.length() > 0) {
            upperString = convert_string.substring(0, 1).toUpperCase() + convert_string.substring(1);
        } else {
            upperString = " ";
        }
        return upperString;
    }

    private void AllocateMemory() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        // toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        relative_layout = findViewById(R.id.relative_layout);
        recv_category = findViewById(R.id.recv_category);
        nav_iv_close = findViewById(R.id.nav_iv_close);
        lv_nodata_category = findViewById(R.id.lv_nodata_category);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setFontStyle() {
        //holder.txt_pack_title.setTypeface(Home.typeface);
        //holder.txt_pack_title.setText(package_model.getPackage_title());
        am = NavigationActivity.this.getAssets();
        montserrat_medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "montserrat_medium.ttf"));
        montserrat_regular = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "montserrat_regular.ttf"));
        montserrat_semibold = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "montserrat_semibold.ttf"));
        montserratbold = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "montserratbold.ttf"));
        montserrat_light = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "montserrat_light.ttf"));
    }

    private void setNavigationIcon_headerview() {



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_open_drawer_description) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                relative_layout.setTranslationX(slideX);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);

        nav_iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        Log.e("count", "" + count);
        if (count == 0) {
            if (doubleBackToExitPressedOnce) {
                Log.e("flage", "" + doubleBackToExitPressedOnce);
                super.onBackPressed();
                super.finish();
                return;
            }
            Log.e("flage1", "" + doubleBackToExitPressedOnce);
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "" + getResources().getString(R.string.back), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            Log.e("count_494", "" + count);
            super.onBackPressed();
        }
    }
}
