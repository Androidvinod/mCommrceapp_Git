package com.example.defaultdemotoken.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Activity.SplashActivity;
import com.example.defaultdemotoken.Adapter.BestSellingAdapter;
import com.example.defaultdemotoken.Adapter.GooglemapAdapter;
import com.example.defaultdemotoken.Adapter.HomeCategoryAdapter;
import com.example.defaultdemotoken.Adapter.HomeTopSliderAdapter;
import com.example.defaultdemotoken.Adapter.ViewPagerAdapter;
import com.example.defaultdemotoken.EnhancedWrapContentViewPager;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator2;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;


public class HomeFragment extends Fragment implements View.OnClickListener {
    public static Toolbar toolbar_home;
    SearchView searchView;
    TextView tv_sale,tv_home_titlee,tv_search_title,tv_browse_cat,tv_seeall_browse,tv_best_selling,tv_best_selling_seeall,tv_new_see_all,tv_new_arrivals,tv_home_off;

    //HomeTopSliderAdapter homeTopSliderAdapter;
    View v;
    RecyclerView recv_categories,recv_bestselling,recv_newarrivals;

    HomeCategoryAdapter homeCategoryAdapter;
    BestSellingAdapter bestSellingAdapter;
    BestSellingAdapter bestSellingAdapter1;
    ViewPagerAdapter viewPagerAdapter;
    private List<HomebannerModel> HomebannerModelList=new ArrayList<>();
    private List<HomebannerModel> HomebannerModelList1=new ArrayList<>();
    private List<HomebannerModel> imagslider=new ArrayList<>();
    int scrollCount = 0;
    LinearLayout lv_see_all_categories;
    EnhancedWrapContentViewPager viewPager;
    CircleIndicator indicatorr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         v = inflater.inflate(R.layout.fragment_home, container, false);
        bottom_navigation.getMenu().getItem(2).setChecked(true);
        setHasOptionsMenu(true);
        AllocateMemory(v);
        setFontFamily();

        hideKeyboard(getActivity());
        //SearchView_Focus();

        if(getActivity()!=null)
        {
            Log.e("debu_home","=");

            ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_home);
            ((NavigationActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

            toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("debu_home","=kj");

                    drawer.openDrawer(GravityCompat.START);

                }
            });
        }



        String mystring=new String("USE CODE : FASH20");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        tv_sale.setText(content);
        AttachRecyclerview();


        for (int i=0;i<5;i++)
        {
            HomebannerModelList.add(new HomebannerModel(" "));
            HomebannerModelList1.add(new HomebannerModel(" "));
        }
     ;

        lv_see_all_categories.setOnClickListener(this);


        return v;
    }
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(getActivity()!=null)
            {
                getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < imagslider.size() - 1) {

                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

            }

        }
    }
    private void setFontFamily() {
        tv_home_off.setTypeface(SplashActivity.montserrat_bold);
        tv_new_arrivals.setTypeface(SplashActivity.montserrat_bold);
        tv_new_see_all.setTypeface(SplashActivity.montserrat_semibold);
        tv_best_selling_seeall.setTypeface(SplashActivity.montserrat_semibold);
        tv_best_selling.setTypeface(SplashActivity.montserrat_bold);
        tv_seeall_browse.setTypeface(SplashActivity.montserrat_medium);
        tv_browse_cat.setTypeface(SplashActivity.montserrat_bold);
        tv_home_titlee.setTypeface(SplashActivity.montserrat_bold);
        tv_search_title.setTypeface(SplashActivity.montserrat_regular);
        tv_sale.setTypeface(SplashActivity.montserrat_bold);

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
    private void AttachRecyclerview() {
           homeCategoryAdapter = new HomeCategoryAdapter(getActivity(),HomebannerModelList);
        recv_categories.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recv_categories.setAdapter(homeCategoryAdapter);

        bestSellingAdapter = new BestSellingAdapter(getActivity(),HomebannerModelList);
        recv_bestselling.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recv_bestselling.setAdapter(bestSellingAdapter);

        bestSellingAdapter1 = new BestSellingAdapter(getActivity(),HomebannerModelList1);
        recv_newarrivals.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recv_newarrivals.setAdapter(bestSellingAdapter1);




        //set image slider data
        for (int i=0;i<5;i++)
        {
            imagslider.add((new HomebannerModel("")));
        }

        viewPagerAdapter = new ViewPagerAdapter(getActivity(),imagslider);
        viewPager.setAdapter(viewPagerAdapter);
        //indicatorr.createIndicators(5,0);
        indicatorr.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(indicatorr.getDataSetObserver());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 2000);

    }


    /**
     * Autoscroll detected from here, where counter, time and runnable is declared.
     */
   /* public void autoScrollAnother() {


        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

               // recv_top_banner.smoothScrollToPosition((scrollCount++));
                if (scrollCount == homeTopSliderAdapter.getItemCount() ) {
                    //stockListModels.addAll(stockListModels);
                    homeTopSliderAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

   */

 /*   private void autoScrollSlider() {

        final int speedScroll = 2000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() { //0<5
                if(count < homeTopSliderAdapter.getItemCount()){
                    if(count==homeTopSliderAdapter.getItemCount()-1){ //0==4
                        flag = false;                   //flag=fa
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag)
                        count++;
                    else
                        count=0;
                    //autoScrollSlider();

                 //   recv_top_banner.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }
*/
    private void SearchView_Focus() {
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
        searchView.setIconified(true);

    }
    private void AllocateMemory(View v) {
        viewPager=v.findViewById(R.id.viewPager);

        indicatorr = v.findViewById(R.id.indicatorr);
        lv_see_all_categories = v.findViewById(R.id.lv_see_all_categories);
        tv_home_off = v.findViewById(R.id.tv_home_off);
        tv_new_arrivals = v.findViewById(R.id.tv_new_see_all);
        tv_new_see_all = v.findViewById(R.id.tv_new_see_all);
        tv_best_selling_seeall = v.findViewById(R.id.tv_best_selling_seeall);
        tv_best_selling = v.findViewById(R.id.tv_best_selling);
        tv_seeall_browse = v.findViewById(R.id.tv_seeall_browse);
        tv_browse_cat = v.findViewById(R.id.tv_browse_cat);
        tv_search_title = v.findViewById(R.id.tv_search_title);
        tv_home_titlee = v.findViewById(R.id.tv_home_titlee);
        tv_sale = v.findViewById(R.id.tv_sale);
        recv_newarrivals = v.findViewById(R.id.recv_newarrivals);
        recv_bestselling = v.findViewById(R.id.recv_bestselling);
        recv_categories = v.findViewById(R.id.recv_categories);
         searchView = v.findViewById(R.id.search);
        toolbar_home = v.findViewById(R.id.toolbar_home);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        /*  switch (item.getItemId()) {
         *//*case R.id.cart:
                pushFragment(new CartFragment());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:*//*
                //return super.onOptionsItemSelected(item);
        }
    */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==lv_see_all_categories)
        {
            Bundle b=new Bundle();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
           b.putString("categoryid",String.valueOf(91));
          b.putString("categoryname","1/2 Palle Træpiller ");
            ProductListFragment myFragment = new ProductListFragment();
            myFragment.setArguments(b);
            activity.getSupportFragmentManager().beginTransaction()

                    .addToBackStack(null).replace(R.id.framlayout, myFragment)
                    .commit();

        }
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

}
