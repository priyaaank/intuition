package com.poc.intuition.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import com.poc.intuition.R;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;

import java.util.List;

public class CategorySelectionFragment extends Fragment implements IListener<PurchaseCategoryResponse> {

    private GridView categoryGrid;
    private Button setupComplete;
    private CategoryImageAdapter gridAdapter;
    private PurchaseCategoryService purchaseCategoryService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.category_setup, container, false);
        categoryGrid = (GridView) inflatedView.findViewById(R.id.category_grid);
        setupComplete = (Button) inflatedView.findViewById(R.id.setup_complete);
        setupComplete.setOnClickListener(completeSetup());
        this.purchaseCategoryService = PurchaseCategoryService.singleInstance(getActivity().getApplicationContext());
        return inflatedView;
    }

    private View.OnClickListener completeSetup() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] categoryIdsToBeDeleted = gridAdapter.idsToBeDeleted();
                String[] categoryNamesToBeCreated = gridAdapter.categoryNamesToBeCreated();
                purchaseCategoryService.deleteExistingCategoryByIds(categoryIdsToBeDeleted);
                purchaseCategoryService.createNewCategoryWithNames(categoryNamesToBeCreated);
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridAdapter = new CategoryImageAdapter(getActivity().getApplicationContext());
        categoryGrid.setAdapter(gridAdapter);
        categoryGrid.setOnItemClickListener(gridAdapter);
        purchaseCategoryService.registerListener(this);
        purchaseCategoryService.fetchCategoriesForUser();
    }

    @Override
    public void serviceResponse(PurchaseCategoryResponse response) {
        List<PurchaseCategory> categories = response.purchaseCategories();
        gridAdapter.preselectPurchaseCategories(categories);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        purchaseCategoryService.registerListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        purchaseCategoryService.deregisterListener(this);
    }
}
