package com.example.defaultdemotoken.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.defaultdemotoken.Adapter.Complete_Order_Adapter;
import com.example.defaultdemotoken.Adapter.Current_Order_Adapter;

import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;

import java.util.ArrayList;
import java.util.List;


public class OrderListFragment extends Fragment {
    LinearLayout lv_progress_order,lvnodata_orderlist;
    View v;
    TextView tv_order_title,tv_orders_history_title,tv_current_orders_title;
    Toolbar toolbar_orderlist;
    Current_Order_Adapter current_order_adapter;
    RecyclerView recv_current_order,recv_complete_order;
    Complete_Order_Adapter complete_order_adapter;
    private List<HomebannerModel> homebannerModelList=new ArrayList<>();
    private List<HomebannerModel> list=new ArrayList<>();
    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_order_list, container, false);
        allocateMemory(v);
        attachRecyclrview();

        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_orderlist);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        return v;
    }

    private void attachRecyclrview() {

        for (int i=0;i<2;i++)
        {
            homebannerModelList.add(new HomebannerModel(""));
            list.add(new HomebannerModel(""));
        }

        current_order_adapter = new Current_Order_Adapter(getActivity(),homebannerModelList);
        recv_current_order.setLayoutManager(new  LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_current_order.setAdapter(current_order_adapter);


        complete_order_adapter = new Complete_Order_Adapter(getActivity(),list);
        recv_complete_order.setLayoutManager(new  LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recv_complete_order.setAdapter(complete_order_adapter);
    }

    private void allocateMemory(View v) {
        lv_progress_order=v.findViewById(R.id.lv_progress_order);
        lvnodata_orderlist=v.findViewById(R.id.lvnodata_orderlist);
        tv_order_title=v.findViewById(R.id.tv_order_title);
        toolbar_orderlist=v.findViewById(R.id.toolbar_orderlist);
        tv_orders_history_title=v.findViewById(R.id.tv_orders_history_title);
        tv_current_orders_title=v.findViewById(R.id.tv_current_orders_title);
        recv_complete_order=v.findViewById(R.id.recv_complete_order);
        recv_current_order=v.findViewById(R.id.recv_current_order);

        tv_current_orders_title.setTypeface(SplashActivity.montserrat_bold);
        tv_orders_history_title.setTypeface(SplashActivity.montserrat_bold);
        tv_order_title.setTypeface(SplashActivity.montserrat_bold);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.notofication, menu);
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

        }
        return super.onOptionsItemSelected(item);
    }
}
