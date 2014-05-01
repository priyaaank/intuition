package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.poc.intuition.R;

public class CategorySelectionFragment extends Fragment {

    private GridView categoryGrid;
    private CategoryImageAdapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.category_setup, container, false);
        categoryGrid = (GridView) inflatedView.findViewById(R.id.category_grid);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridAdapter = new CategoryImageAdapter(getActivity().getApplicationContext());
        categoryGrid.setAdapter(gridAdapter);
        categoryGrid.setOnItemClickListener(gridAdapter);
    }
}
