package com.poc.intuition.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.poc.intuition.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<Integer> selectedThumbnails;

    public CategoryImageAdapter(Context c) {
        mContext = c;
        selectedThumbnails = new ArrayList<Integer>();
    }

    public int getCount() {
        return selectedCategoryImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(92, 92));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Integer image = selectedThumbnails.contains(unselectedCategoryImages[position]) ? selectedCategoryImages[position] : unselectedCategoryImages[position];
        imageView.setImageResource(image);
        return imageView;
    }

    private Integer[] selectedCategoryImages = {
            R.drawable.education_selected, R.drawable.entertainment_selected,
            R.drawable.gifts_selected, R.drawable.food_selected,
            R.drawable.holidays_selected, R.drawable.household_selected,
            R.drawable.medical_selected, R.drawable.shopping_selected,
            R.drawable.travel_selected, R.drawable.travel_selected
    };

    private Integer[] unselectedCategoryImages = {
            R.drawable.education_unselected, R.drawable.entertainment_unselected,
            R.drawable.gifts_unselected, R.drawable.food_unselected,
            R.drawable.holidays_unselected, R.drawable.household_unselected,
            R.drawable.medical_unselected, R.drawable.shopping_unselected,
            R.drawable.travel_unselected, R.drawable.travel_unselected
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int clickedImage = unselectedCategoryImages[position];
        if(selectedThumbnails.contains(clickedImage)) {
            selectedThumbnails.remove(clickedImage);
        } else {
            selectedThumbnails.add(clickedImage);
        }
    }
}
