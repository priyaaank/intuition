package com.poc.intuition.views;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.poc.intuition.R;
import com.poc.intuition.domain.Transaction;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.TransactionService;
import com.poc.intuition.service.response.TransactionResponse;

public class TransactionListing extends ListActivity implements IListener<TransactionResponse> {

    private ArrayAdapter<Transaction> transactionArrayAdapter;
    private TransactionService transactionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_listing);

        transactionArrayAdapter = new TransactionListingAdapter(getApplicationContext(), R.layout.transaction_row);
        setListAdapter(transactionArrayAdapter);

        showTransactions();
    }

    private void showTransactions() {
        transactionService = new TransactionService(getApplicationContext(), this);
        transactionService.findTransactionsForCurrentMonth("david");
    }

    @Override
    public void serviceResponse(TransactionResponse transactionResponse) {
        transactionArrayAdapter.addAll(transactionResponse.transactions());
        transactionArrayAdapter.notifyDataSetChanged();
    }
}
