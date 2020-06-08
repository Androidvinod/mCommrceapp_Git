package com.example.defaultdemotoken.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.defaultdemotoken.Model.HomebannerModel;
import com.example.defaultdemotoken.R;
import com.example.defaultdemotoken.Rounded;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<HomebannerModel> homebannerModelList;
    public ViewPagerAdapter(Context context,    List<HomebannerModel> HomebannerModelList) {
        this.context = context;
        this.homebannerModelList = HomebannerModelList;
    }

    @Override
    public int getCount() {
        return homebannerModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.first_home_banner_row, null);
        Rounded imageView = (Rounded) view.findViewById(R.id.img);
        //imageView.setImageResource(images[position]);
      //  imageView.setImageResource(homebannerModelList.get(position).getIma());

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}