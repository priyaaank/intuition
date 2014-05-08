package com.poc.intuition.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.poc.intuition.R;
import com.poc.intuition.domain.PurchaseCategory;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectionAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<DisplayPurchaseCategory> allDisplayCategories;
    private List<DisplayPurchaseCategory> categoriesToList;

    public CategorySelectionAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        allDisplayCategories = new ArrayList<DisplayPurchaseCategory>();
        categoriesToList = new ArrayList<DisplayPurchaseCategory>();
        initializeAllDisplayCategories();
    }

    private void initializeAllDisplayCategories() {
        allDisplayCategories.add(new DisplayPurchaseCategory("Food n Drinks", R.drawable.drinks_selected, R.drawable.drinks_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Dining", R.drawable.dinning_selected, R.drawable.dinning_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Recreation", R.drawable.entertainment_selected, R.drawable.entertainment_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Medical n Insurance", R.drawable.medical_selected, R.drawable.medical_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Investment", R.drawable.investment_selected, R.drawable.investment_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Shopping", R.drawable.shopping_selected, R.drawable.shopping_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Fuel n Transport", R.drawable.fuel_selected, R.drawable.fuel_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Household", R.drawable.household_selected, R.drawable.household_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Fees n Charges", R.drawable.fees_selected, R.drawable.fees_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Holiday", R.drawable.holidays_selected, R.drawable.holidays_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Mortgage", R.drawable.loans_selected, R.drawable.loans_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Uncategorized", R.drawable.unknown_selected, R.drawable.unknown_unselected));
    }

    public void markAsUserCategories(List<String> categories) {
        for(String categoryName : categories) {
            for(DisplayPurchaseCategory displayPurchaseCategory : allDisplayCategories) {
                if(categoryName.equalsIgnoreCase(displayPurchaseCategory.categoryName)) {
                    displayPurchaseCategory.updatePurchaseCategory(new PurchaseCategory(-1, categoryName));
                    categoriesToList.add(displayPurchaseCategory);
                }
            }
        }

        this.notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return categoriesToList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesToList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view =  mInflater.inflate(R.layout.category_icon_layout, null);

            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.category_icon);
            holder.textView = (TextView) view.findViewById(R.id.category_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DisplayPurchaseCategory category = categoriesToList.get(position);
        holder.image.setImageDrawable(context.getResources().getDrawable(category.drawable()));
        holder.textView.setText(category.categoryName);
        return view;
    }

    class DisplayPurchaseCategory {

        private String categoryName;
        private Integer selectedDrawable;
        private Integer unselectedDrawable;
        private boolean isSelected;
        private PurchaseCategory purchaseCategory;

        public DisplayPurchaseCategory(String categoryName, Integer selectedDrawable, Integer unselectedDrawable) {
            this.categoryName = categoryName;
            this.selectedDrawable = selectedDrawable;
            this.unselectedDrawable = unselectedDrawable;
            this.isSelected = false;
            this.purchaseCategory = null;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void select() {
            this.isSelected = true;
        }

        public void toggleSelection() {
            isSelected = !isSelected;
        }

        public int drawable() {
            return isSelected ? selectedDrawable : unselectedDrawable;
        }

        public boolean representsPurchaseCategory(PurchaseCategory category) {
            if(this.categoryName.equalsIgnoreCase(category.getName())) return true;
            return false;
        }

        public void updatePurchaseCategory(PurchaseCategory category) {
            this.purchaseCategory = category;
        }
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView textView;
    }
}
