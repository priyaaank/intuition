package com.poc.intuition.experiments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjoe64.graphview.*;
import com.poc.intuition.R;
import com.poc.intuition.views.Dashboard;

public class ReceiptFragment extends Fragment {

    private static final String TAG = "ReceiptFragment";
    private static final String MERCHANT_NAME = "merchant_name";
    private static final String TRANSACTION_AMOUNT = "transaction_amount";

    private View inflatedReceiptView;
    private String transactionAmount;
    private String merchantName;

    public static ReceiptFragment newInstance(String merchantName, String transactionAmount) {
        ReceiptFragment fragment = new ReceiptFragment();
        Bundle arguments = new Bundle();
        arguments.putString(MERCHANT_NAME, merchantName);
        arguments.putString(TRANSACTION_AMOUNT, transactionAmount);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        merchantName = arguments.getString(MERCHANT_NAME);
        transactionAmount = arguments.getString(TRANSACTION_AMOUNT);
        ((TextView)inflatedReceiptView.findViewById(R.id.transaction_amount)).setText("$"+transactionAmount);
        ((TextView)inflatedReceiptView.findViewById(R.id.transaction_merchant_name)).setText(merchantName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedReceiptView = inflater.inflate(R.layout.receipt, container, false);
        return inflatedReceiptView;
    }
}
