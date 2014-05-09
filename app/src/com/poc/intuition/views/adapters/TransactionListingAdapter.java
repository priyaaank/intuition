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
            holder.transactionAmount.setText("$"+transaction.transactionAmount());
            String rowColor = "#00000000";
            String textColor = "#000000";
            if(selectedTransactions.contains(transaction)) {
                rowColor = "#864C9E";
                textColor = "#FFFFFF";
            }

            holder.rowContainer.setBackgroundColor(Color.parseColor(rowColor));
            holder.merchantName.setTextColor(Color.parseColor(textColor));
            holder.transactionAmount.setTextColor(Color.parseColor(textColor));
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
        clear();
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

    public List<String> selectedTransactionIds() {
        List<String> idsToReturn  = new ArrayList<String>();
        for(Transaction selectedTransaction : selectedTransactions) {
            idsToReturn.add(selectedTransaction.getId().toString());
        }
        return idsToReturn;
    }

    public static class ViewHolder{
        public LinearLayout rowContainer;
        public TextView merchantName;
        public TextView transactionAmount;
    }
}
