package com.poc.intuition.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.poc.intuition.R;
import com.poc.intuition.domain.Transaction;

public class TransactionListingAdapter extends ArrayAdapter<Transaction> {

    private LayoutInflater mInflater;
    private int mResource;

    public TransactionListingAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(convertView==null) {
            view = mInflater.inflate(mResource, null);

            holder = new ViewHolder();
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
        }
        return view;
    }

    public static class ViewHolder{
        public TextView merchantName;
        public TextView transactionAmount;
    }
}
