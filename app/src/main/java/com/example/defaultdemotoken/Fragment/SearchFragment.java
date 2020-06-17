package com.example.defaultdemotoken.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.BestSellingAdapter;
import com.example.defaultdemotoken.Adapter.ProductListAdater;
import com.example.defaultdemotoken.Adapter.SearchProductListAdapter;
import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.Model.ProductModel.Item;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    View v;
    Toolbar toolbar_Search;
    RecyclerView recv_search;
    SearchView searchView;
    SearchProductListAdapter searchProductListAdapter;
    List<HomebannerModel> results = new ArrayList<>();

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
        recv_search=v.findViewById(R.id.recv_search);
        searchView=v.findViewById(R.id.searchView);

        sarchViewFocuable();
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

        for(int i=0;i<10;i++)
        {
            results.add(new HomebannerModel(""));
        }

        searchProductListAdapter = new SearchProductListAdapter(getActivity(),results);
        recv_search.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recv_search.setAdapter(searchProductListAdapter);




        return v;
    }
    private void sarchViewFocuable() {
        EditText searchEdit = ((EditText)searchView.findViewById(R.id.search_src_text));
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchView.setBackground(null);

        searchView.setIconifiedByDefault(false);
      //  searchView.setFocusable(true);
       // searchView.requestFocus();
        searchView.isInEditMode();
        searchView.requestFocusFromTouch();
        //searchView.setIconified(false);
        searchView.setQueryHint("Search Here");

    }
}
