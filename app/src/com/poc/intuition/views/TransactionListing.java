package com.poc.intuition.views;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.TransactionService;
import com.poc.intuition.service.response.TransactionResponse;

public class TransactionListing extends ListActivity implements IListener<TransactionResponse> {

    private TransactionListingAdapter transactionListingAdapter;
    private TransactionService transactionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_listing);
        getActionBar().setTitle("Transactions");

        transactionListingAdapter = new TransactionListingAdapter(getApplicationContext(), R.layout.transaction_row);
        setListAdapter(transactionListingAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new ContextualMultiTransactionSelectListener(transactionListingAdapter));

        showTransactions();
    }

    private void showTransactions() {
        transactionService = new TransactionService(getApplicationContext(), this);
        transactionService.findTransactionsForCurrentMonth("david");
    }

    @Override
    public void serviceResponse(TransactionResponse transactionResponse) {
        transactionListingAdapter.addAll(transactionResponse.transactions());
        transactionListingAdapter.notifyDataSetChanged();
    }
}
