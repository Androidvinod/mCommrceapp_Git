package com.example.defaultdemotoken.Fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.defaultdemotoken.Activity.NavigationActivity;
import com.example.defaultdemotoken.Adapter.GooglemapAdapter;
import com.example.defaultdemotoken.Adapter.ProductListAdater;
import com.example.defaultdemotoken.Model.GoogleMapModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.RecyclerItemTouchHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.defaultdemotoken.Activity.NavigationActivity.bottom_navigation;
import static com.example.defaultdemotoken.Activity.NavigationActivity.drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    View v;
    GooglemapAdapter googlemapAdapter;
    Toolbar toolbar_wislist;
    LinearLayout lvnodata_wishlistlist,lv_progress_wishist;
    RecyclerView recv_wishlist;
    List<GoogleMapModel> googleMapModelList=new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_wishlist, container, false);
        bottom_navigation.getMenu().getItem(1).setChecked(true);
        AllocateMemory();
        ((NavigationActivity) getActivity()).setSupportActionBar(toolbar_wislist);
        ((NavigationActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.signs);

        toolbar_wislist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        toolbar_wislist.setTitle("WishList");
        toolbar_wislist.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        googlemapAdapter = new GooglemapAdapter(getActivity(),googleMapModelList);
        recv_wishlist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recv_wishlist.setAdapter(googlemapAdapter);
        for (int i=0; i<5;i++)
        {
            googleMapModelList.add(new GoogleMapModel(""));

        }

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recv_wishlist);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recv_wishlist);

        return v;
    }

    private void AllocateMemory() {
        toolbar_wislist=v.findViewById(R.id.toolbar_wislist);
        lvnodata_wishlistlist=v.findViewById(R.id.lvnodata_wishlistlist);
        lv_progress_wishist=v.findViewById(R.id.lv_progress_wishist);
        recv_wishlist=v.findViewById(R.id.recv_wishlist);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof GooglemapAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
         /*   String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());
*/
            // showing snack bar with Undo option
           /* Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/

            Toast.makeText(getActivity(), "testtt", Toast.LENGTH_SHORT).show();
        }
    }

}
