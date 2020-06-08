package com.example.defaultdemotoken.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.defaultdemotoken.Model.GoogleMapModel;

import com.example.defaultdemotoken.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;


public class GooglemapAdapter extends RecyclerView.Adapter<com.example.defaultdemotoken.Adapter.GooglemapAdapter.MyViewHolder> {

    Context context;
    private List<GoogleMapModel> googleMapModelList;

    public GooglemapAdapter(Context context, List<GoogleMapModel> googleMapModelList) {
        this.context = context;
        this.googleMapModelList = googleMapModelList;
    }

    @NonNull
    @Override
    public com.example.defaultdemotoken.Adapter.GooglemapAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View GoogleMapModelView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wishlist_row, viewGroup, false);
        return new com.example.defaultdemotoken.Adapter.GooglemapAdapter.MyViewHolder(GoogleMapModelView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final com.example.defaultdemotoken.Adapter.GooglemapAdapter.MyViewHolder holder, int position) {
        final GoogleMapModel GoogleMapModel = googleMapModelList.get(position);
        ///  holder.tv_package_name.setText(GoogleMapModel.getName());

    /*    FrameLayout view = (FrameLayout) ((MyViewHolder) holder).map_layout;
        FrameLayout frame = new FrameLayout(context);
        frame.setId(1010101); //you have to set unique id

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, context.getResources().getDisplayMetrics());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
        frame.setLayoutParams(layoutParams);

        view.addView(frame);

        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true);
        SupportMapFragment mapFrag = SupportMapFragment.newInstance(options);

        //Create the the class that implements OnMapReadyCallback and set up your map
        mapFrag.getMapAsync(new MapReadyCallback());

        FragmentManager fm = mapFrag.getChildFragmentManager();
        fm.beginTransaction().add(frame.getId(), mapFrag).commit();*/


    }


    @Override
    public int getItemCount() {
        return googleMapModelList.size();
    }

    /*   public void addAll(List<GoogleMapModel> categories) {
               for (GoogleMapModel result : categories) {
                   add(result);
               }
           }
   */
     /*   public void add(GoogleMapModel r) {
            GoogleMapModelList.add(r);
            notifyGoogleMapModelInserted(GoogleMapModelList.size() - 1);
        }
*/
      /*  @Override
        public int getGoogleMapModelCount() {
            return GoogleMapModelList.size();
        }
*/
    public class MyViewHolder extends RecyclerView.ViewHolder  implements OnMapReadyCallback {

         /*   TextView tv_package_price,tv_package_name;
            ImageView iv_package_img;
            LinearLayout lv_product_click;*/
         public RelativeLayout viewBackground, viewForeground;

        FrameLayout map_layout;

        public MyViewHolder(@NonNull View view) {
            super(view);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
           // FrameLayout view;
            // map_layout== (FrameLayout)view.findViewById(R.id.map_layout);
               /* iv_package_img = view.findViewById(R.id.iv_package_img);
                tv_package_name = view.findViewById(R.id.tv_package_name);
                tv_package_price = view.findViewById(R.id.tv_package_price);
                lv_product_click = view.findViewById(R.id.lv_product_click);*/
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }

    private class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
           // drawMap(googleMap);
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}

