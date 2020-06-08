package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.defaultdemotoken.Adapter.Filter_By_Adapter;
import com.example.defaultdemotoken.Adapter.ProductCategoryAdapter;
import com.example.defaultdemotoken.Adapter.SortByAdapter;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterListFragment extends Fragment {
    Toolbar toolbar_filter;
    TextView tv_refine_toolbar_title,tv_refine_sort_by,tv_refine_filter_by,tv_clearall_filter,tv_show_result;
    LinearLayout lvnodata_filter,lv_filter_progress,lv_sort,lv_filter;
    CoordinatorLayout cordinator_main_filter;
    RecyclerView rv_sort_by,rv_filter_by;
    Filter_By_Adapter filter_by_adapter;
    private List<HomebannerModel> filtrelist=new ArrayList<>();
    SortByAdapter sortByAdapter;

    public FilterListFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_filter_list, container, false);
        AllocateMemory(v);
        setHasOptionsMenu(true);

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_filter);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.closse);


        filter_by_adapter = new Filter_By_Adapter(getActivity(),filtrelist);
        rv_filter_by.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv_filter_by.setAdapter(filter_by_adapter);

        sortByAdapter = new SortByAdapter(getActivity(),filtrelist);
        rv_sort_by.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv_sort_by.setAdapter(sortByAdapter);


        for (int i=0;i<5;i++)
        {
            filtrelist.add(new HomebannerModel(" "));
        }

        return v;

    }

    private void AllocateMemory(View v) {
        toolbar_filter=v.findViewById(R.id.toolbar_filter);
        tv_refine_toolbar_title=v.findViewById(R.id.tv_refine_toolbar_title);
        lvnodata_filter=v.findViewById(R.id.lvnodata_filter);
        lv_filter_progress=v.findViewById(R.id.lv_filter_progress);
        cordinator_main_filter=v.findViewById(R.id.cordinator_main_filter);
        lv_sort=v.findViewById(R.id.lv_sort);
        tv_refine_sort_by=v.findViewById(R.id.tv_refine_sort_by);
        rv_sort_by=v.findViewById(R.id.rv_sort_by);
        lv_filter=v.findViewById(R.id.lv_filter);
        tv_refine_filter_by=v.findViewById(R.id.tv_refine_filter_by);
        rv_filter_by=v.findViewById(R.id.rv_filter_by);
        tv_clearall_filter=v.findViewById(R.id.tv_clearall_filter);
        tv_show_result=v.findViewById(R.id.tv_show_result);

        tv_refine_toolbar_title.setTypeface(SplashActivity.montserrat_semibold);
        tv_refine_sort_by.setTypeface(SplashActivity.montserrat_semibold);
        tv_refine_filter_by.setTypeface(SplashActivity.montserrat_semibold);

        tv_clearall_filter.setTypeface(SplashActivity.montserrat_medium);
        tv_show_result.setTypeface(SplashActivity.montserrat_medium);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
       // inflater.inflate(R.menu.menu_clear_all, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {


            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
