package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.poc.intuition.R;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.experiments.CategorySelectionDialog;
import com.poc.intuition.experiments.ContextualMultiTransactionSelectListener;
import com.poc.intuition.experiments.ISelectionManager;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.TransactionService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import com.poc.intuition.service.response.TransactionResponse;
import com.poc.intuition.views.adapters.TransactionListingAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment implements ISelectionManager, ICategoryChangeListener, AdapterView.OnItemSelectedListener {

    private TransactionListingAdapter transactionListingAdapter;
    private ContextualMultiTransactionSelectListener multiChoiceModeListener;
    private TransactionService transactionService;
    private ListView transactionListingView;
    private PurchaseCategoryService purchaseCategoryService;
    IListener<PurchaseCategoryResponse> purchaseCategoryListener;
    private ArrayAdapter<String> categorySelectorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.transaction_listing, container, false);
        transactionListingView = (ListView) inflatedView.findViewById(R.id.transaction_list_view);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        purchaseCategoryService = PurchaseCategoryService.singleInstance(getActivity().getApplicationContext());
        purchaseCategoryListener = purchaseCategoryResponseListener();
        transactionListingAdapter = new TransactionListingAdapter(getActivity().getApplicationContext(), R.layout.transaction_row);
        transactionListingView.setAdapter(transactionListingAdapter);
        transactionListingView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        multiChoiceModeListener = new ContextualMultiTransactionSelectListener(this);
        transactionListingView.setMultiChoiceModeListener(multiChoiceModeListener);
        categorySelectorAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item, new ArrayList<String>());
        initializeCategorySelector();

        showTransactions();
    }

    private void initializeCategorySelector() {
        View actionBarView = getActivity().getActionBar().getCustomView();
        Spinner categorySelector = (Spinner) actionBarView.findViewById(R.id.category_selector);
        categorySelector.setVisibility(View.VISIBLE);
        categorySelector.setAdapter(categorySelectorAdapter);
        categorySelector.setOnItemSelectedListener(this);
    }

    private void showTransactions() {
        transactionService = new TransactionService(getActivity().getApplicationContext(), transactionResponseListener());
        transactionService.findTransactionsForLastMonths(2);
    }

    @Override
    public void onResume() {
        super.onResume();
        purchaseCategoryService.registerListener(purchaseCategoryListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        purchaseCategoryService.deregisterListener(purchaseCategoryListener);
    }

    @Override
    public void addNewSelection(int position) {
        transactionListingAdapter.addNewSelection(position);
        transactionListingAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeSelection(int position) {
        transactionListingAdapter.notifyDataSetChanged();
        transactionListingAdapter.removeSelection(position);
    }

    @Override
    public void clearSelection() {
        transactionListingAdapter.clearSelection();
        transactionListingAdapter.notifyDataSetChanged();
    }

    public void processCategoryChange() {
        purchaseCategoryService.fetchCategoriesForUser();
    }

    private IListener<TransactionResponse> transactionResponseListener() {
        return new IListener<TransactionResponse>() {
            @Override
            public void serviceResponse(TransactionResponse transactionResponse) {
                transactionListingAdapter.addMasterTransactionList(transactionResponse.transactions());
                updateActionBarToShowCategories();
            }
        };
    }

    private void updateActionBarToShowCategories() {
        List<String> distinctCategories = transactionListingAdapter.getDistinctCategories();
        categorySelectorAdapter.clear();
        categorySelectorAdapter.add("All");
        categorySelectorAdapter.addAll(distinctCategories);
        categorySelectorAdapter.notifyDataSetChanged();
    }

    private IListener<PurchaseCategoryResponse> purchaseCategoryResponseListener() {
        return new IListener<PurchaseCategoryResponse>() {
            @Override
            public void serviceResponse(PurchaseCategoryResponse response) {
                if(getActivity().getSupportFragmentManager().findFragmentByTag("CategorySelectionDialog") == null) {
                    ArrayList<String> categoryNameList = new ArrayList<String>();
                    for (PurchaseCategory category : response.purchaseCategories()) {
                        categoryNameList.add(category.getName());
                    }
                    CategorySelectionDialog categorySelectionDialog = CategorySelectionDialog.newInstance(categoryNameList);
                    categorySelectionDialog.show(getActivity().getSupportFragmentManager(), "CategorySelectionDialog");
                }
            }
        };
    }

    public void updateSelectedTransactionsWithCategory(String categoryName) {
        multiChoiceModeListener.markActionModeFinished();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoryName = categorySelectorAdapter.getItem(position);
        transactionListingAdapter.filterByCategory(categoryName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
