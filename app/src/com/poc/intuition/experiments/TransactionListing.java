package com.poc.intuition.experiments;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.poc.intuition.R;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.TransactionService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import com.poc.intuition.service.response.TransactionResponse;

import java.util.ArrayList;

public class TransactionListing extends ListActivity implements ISelectionManager {

    private TransactionListingAdapter transactionListingAdapter;
    private ContextualMultiTransactionSelectListener multiChoiceModeListener;
    private TransactionService transactionService;
    private PurchaseCategoryService purchaseCategoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_listing);
        getActionBar().setTitle("Transactions");
        purchaseCategoryService = new PurchaseCategoryService(getApplicationContext(), purchaseCategoryResponseListener());

        transactionListingAdapter = new TransactionListingAdapter(getApplicationContext(), R.layout.transaction_row);
        setListAdapter(transactionListingAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        multiChoiceModeListener = new ContextualMultiTransactionSelectListener(this);
        getListView().setMultiChoiceModeListener(multiChoiceModeListener);

        showTransactions();
    }

    private void showTransactions() {
        transactionService = new TransactionService(getApplicationContext(), transactionResponseListener());
        transactionService.findTransactionsForCurrentMonth("david");
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
        purchaseCategoryService.fetchCategoriesForUsername();
    }

    private IListener<TransactionResponse> transactionResponseListener() {
        return new IListener<TransactionResponse>() {
            @Override
            public void serviceResponse(TransactionResponse transactionResponse) {
                transactionListingAdapter.addAll(transactionResponse.transactions());
                transactionListingAdapter.notifyDataSetChanged();
            }
        };
    }

    private IListener<PurchaseCategoryResponse> purchaseCategoryResponseListener() {
        return new IListener<PurchaseCategoryResponse>() {
            @Override
            public void serviceResponse(PurchaseCategoryResponse response) {
                ArrayList<String> categoryNameList = new ArrayList<String>();
                for(PurchaseCategory category : response.purchaseCategories()) {
                    categoryNameList.add(category.getName());
                }
                CategorySelectionDialog categorySelectionDialog = CategorySelectionDialog.newInstance(categoryNameList);
                categorySelectionDialog.show(getFragmentManager(), "CategorySelectionDialog");
            }
        };
    }

    public void updateSelectedTransactionsWithCategory(String categoryName) {
        multiChoiceModeListener.markActionModeFinished();
    }
}
