package com.poc.intuition.experiments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.poc.intuition.R;

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
        dialog.show();

        ListView categoryListView = (ListView) dialog.findViewById(R.id.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, categoryNameList.toArray(new String[categoryNameList.size()]));
        categoryListView.setAdapter(adapter);
        categoryListView.setOnItemClickListener(this);

        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        String categoryName = categoryNameList.get(position);
        ((TransactionListing)getActivity()).updateSelectedTransactionsWithCategory(categoryName);
    }
}
