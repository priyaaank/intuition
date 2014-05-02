package com.poc.intuition.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.poc.intuition.R;
import com.poc.intuition.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionListingAdapter extends ArrayAdapter<Transaction> {

    private LayoutInflater mInflater;
    private int mResource;
    private List<Transaction> selectedTransactions;
    private List<Transaction> masterTransactionList;

    public TransactionListingAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
        selectedTransactions = new ArrayList<Transaction>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(convertView==null) {
            view = mInflater.inflate(mResource, null);

            holder = new ViewHolder();
            holder.rowContainer = (LinearLayout) view.findViewById(R.id.transaction_row_container);
            holder.merchantName = (TextView) view.findViewById(R.id.merchant_name);
            holder.transactionAmount = (TextView) view.findViewById(R.id.amount);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Transaction transaction = getItem(position);
        if(transaction != null) {
            holder.merchantName.setText(transaction.merchantName());
            holder.transactionAmount.setText(transaction.transactionAmount());
            String color = selectedTransactions.contains(transaction) ? "#2ecc71" : "#000000";
            holder.rowContainer.setBackgroundColor(Color.parseColor(color));
        }
        return view;
    }

    public void clearSelection() {
        selectedTransactions.clear();
    }

    public void removeSelection(int position) {
        selectedTransactions.remove(getItem(position));
    }

    public void addNewSelection(int position) {
        selectedTransactions.add(getItem(position));
    }

    public List<String> getDistinctCategories() {
        List<String> distinctCategories = new ArrayList<String>();
        for(Transaction transaction : masterTransactionList) {
            if(distinctCategories.contains(transaction.categoryName())) continue;
            distinctCategories.add(transaction.categoryName());
        }
        return distinctCategories;
    }

    public void addMasterTransactionList(List<Transaction> transactions) {
        this.masterTransactionList = transactions;
        addAll(transactions);
        notifyDataSetChanged();
    }

    public void filterByCategory(String categoryName) {
        clear();
        if("all".equalsIgnoreCase(categoryName)) {
            addAll(masterTransactionList);
        } else {
            for (Transaction transaction : masterTransactionList) {
                if (categoryName.equalsIgnoreCase(transaction.categoryName()))
                    add(transaction);
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        public LinearLayout rowContainer;
        public TextView merchantName;
        public TextView transactionAmount;
    }
}
