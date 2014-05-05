package com.poc.intuition.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.poc.intuition.R;
import com.poc.intuition.domain.PurchaseCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<DisplayPurchaseCategory> allDisplayCategories;
    private List<PurchaseCategory> selectedCategories;

    public CategoryImageAdapter(Context c) {
        mContext = c;
        allDisplayCategories = new ArrayList<DisplayPurchaseCategory>();
        initializeAllCategories();
    }

    private void initializeAllCategories() {
//        Household = "Household"
//        FuelAndTransport = "Fuel & Transport"
//        Shopping = "Shopping"
//        Medical = "Medical & Insurance"
//        Recreation = "Recreation"
//        FoodAndDrinks = "Food & Drinks"
//        DiningOut = "Dining"
//        Holiday = "Holiday"
//        Investment = "Investment"
//        FeesAndCharges = "Fees & Charges"
//        Mortgage = "Mortgage"
//        Uncategorized = "Uncategorized"
        allDisplayCategories.add(new DisplayPurchaseCategory("Food n Drinks", R.drawable.food_selected, R.drawable.food_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Dining", R.drawable.food_selected, R.drawable.food_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Recreation", R.drawable.food_selected, R.drawable.food_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Medical n Insurance", R.drawable.medical_selected, R.drawable.medical_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Investment", R.drawable.gifts_selected, R.drawable.gifts_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Shopping", R.drawable.shopping_selected, R.drawable.shopping_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Fuel n Transport", R.drawable.travel_selected, R.drawable.travel_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Household", R.drawable.household_selected, R.drawable.household_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Fees n Charges", R.drawable.entertainment_selected, R.drawable.entertainment_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Holiday", R.drawable.entertainment_selected, R.drawable.entertainment_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Mortgage", R.drawable.entertainment_selected, R.drawable.entertainment_unselected));
        allDisplayCategories.add(new DisplayPurchaseCategory("Uncategorized", R.drawable.holidays_selected, R.drawable.holidays_unselected));
    }

    public void preselectPurchaseCategories(List<PurchaseCategory> selectedCategories) {
        this.selectedCategories = selectedCategories;
        for(PurchaseCategory category : selectedCategories) {
            for(DisplayPurchaseCategory displayPurchaseCategory : allDisplayCategories) {
                if(category.getName().equalsIgnoreCase(displayPurchaseCategory.categoryName)) {
                    displayPurchaseCategory.updatePurchaseCategory(category);
                    displayPurchaseCategory.select();
                }
            }
        }
        this.notifyDataSetInvalidated();
    }

    public int getCount() {
        return allDisplayCategories.size();
    }

    public Object getItem(int position) {
        return allDisplayCategories.get(position);
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

        Integer image = allDisplayCategories.get(position).drawable();
        imageView.setImageResource(image);
        return imageView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(allDisplayCategories.get(position).purchaseCategory != null && allDisplayCategories.get(position).purchaseCategory.getName().equalsIgnoreCase("uncategorized")) return;
        allDisplayCategories.get(position).toggleSelection();
        this.notifyDataSetInvalidated();
    }

    public String[] idsToBeDeleted() {
        List<String> idsToBeDeleted = new ArrayList<String>();
        for(DisplayPurchaseCategory category : allDisplayCategories) {
            if(!category.isSelected && category.purchaseCategory != null) {
                idsToBeDeleted.add(category.purchaseCategory.getId().toString());
            }
        }
        return idsToBeDeleted.toArray(new String[idsToBeDeleted.size()]);
    }

    public String[] categoryNamesToBeCreated() {
        List<String> namesToBeCreated = new ArrayList<String>();
        for(DisplayPurchaseCategory category : allDisplayCategories) {
            if(category.isSelected && category.purchaseCategory == null) {
                namesToBeCreated.add(category.categoryName);
            }
        }
        return namesToBeCreated.toArray(new String[namesToBeCreated.size()]);
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

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof PurchaseCategory || o instanceof DisplayPurchaseCategory)) return false;
            if (o instanceof DisplayPurchaseCategory) {
                return this.categoryName.equalsIgnoreCase(((PurchaseCategory)o).getName());
            }
            if (o instanceof DisplayPurchaseCategory) {
                return this.equals(o);
            }

            return false;
        }

        public void updatePurchaseCategory(PurchaseCategory category) {
            this.purchaseCategory = category;
        }
    }
}
