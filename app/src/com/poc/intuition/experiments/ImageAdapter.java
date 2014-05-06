package com.poc.intuition.experiments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.digitalaria.gama.carousel.Carousel;
import com.poc.intuition.R;
import com.poc.intuition.widgets.CategoryHealthRadiator;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c)
    {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 12;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
//        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.car_item, parent, false);
            view.setLayoutParams(new Carousel.LayoutParams(250, 250));

//            ViewHolder holder = new ViewHolder();
//            holder.textView = (TextView)view.findViewById(R.id.itemImage);
            RelativeLayout widget = new CategoryHealthRadiator(mContext, 2000d, 500d, 250, 250).build();
            ((LinearLayout)view.findViewById(R.id.container)).addView(widget);

//            view.setTag(holder);
//        }

//        ViewHolder holder = (ViewHolder)view.getTag();
//        holder.textView.setText("This is pos:" + position);
//
        return view;
    }

    private class ViewHolder {
        TextView textView;
    }
}