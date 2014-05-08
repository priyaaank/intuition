package com.poc.intuition.experiments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import com.poc.intuition.R;
import com.poc.intuition.views.Dashboard;
import com.poc.intuition.views.adapters.CategorySelectionAdapter;

import java.util.ArrayList;

public class CategorySelectionDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    private final static String CATAGORY_LIST_KEY = "category_list";
    private ArrayList<String> categoryNameList;

    public static CategorySelectionDialog newInstance(ArrayList<String> categories) {
        CategorySelectionDialog dialog = new CategorySelectionDialog();
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(CATAGORY_LIST_KEY, categories);
        dialog.setArguments(arguments);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryNameList = getArguments().getStringArrayList(CATAGORY_LIST_KEY);
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog.setContentView(R.layout.category_selection_dialog);
        GridView categoryGridView = (GridView) dialog.findViewById(R.id.category_selection_grid);
        CategorySelectionAdapter adapter = new CategorySelectionAdapter(this.getActivity().getApplicationContext());
        adapter.markAsUserCategories(categoryNameList);
        categoryGridView.setAdapter(adapter);
        categoryGridView.setOnItemClickListener(this);

        dialog.show();

        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        String categoryName = categoryNameList.get(position);
        ((Dashboard)getActivity()).updateSelectedTransactionsWithCategory(categoryName);
    }
}
