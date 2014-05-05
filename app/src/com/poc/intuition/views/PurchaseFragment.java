package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.poc.intuition.R;
import com.poc.intuition.experiments.ReceiptFragment;
import com.poc.intuition.widgets.RadiatorNeedle;

public class PurchaseFragment extends Fragment {

    private static final String AMOUNT_SPENT = "amount_spent";
    private static final String TOTAL_AMOUNT = "total_amount";
    private static final String SAVINGS_RATE = "savings_rate";

    private Double amountSpent;
    private Double totalAmount;
    private Double savingsRate;

    private RadiatorNeedle expenseRadiatorDial;

    public static PurchaseFragment NewInstance(Double amountSpent, Double totalAmount, Double savingsRate) {
        PurchaseFragment fragment  = new PurchaseFragment();
        Bundle arguments = new Bundle();
        arguments.putDouble(AMOUNT_SPENT, amountSpent);
        arguments.putDouble(TOTAL_AMOUNT, totalAmount);
        arguments.putDouble(SAVINGS_RATE, savingsRate);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        amountSpent = getArguments().getDouble(AMOUNT_SPENT);
        totalAmount = getArguments().getDouble(TOTAL_AMOUNT);
        savingsRate = getArguments().getDouble(SAVINGS_RATE);

        View view = inflater.inflate(R.layout.purchase_view, container, false);
        expenseRadiatorDial = (RadiatorNeedle) view.findViewById(R.id.needle);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReceiptFragment receiptFragment = new ReceiptFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up)
                .add(R.id.receipt_container, receiptFragment, "ReceiptFragment")
                .addToBackStack("ReceiptFragment")
                .commit();
        RotateAnimation rotateAnimation = new RotateAnimation(-90, getSpinAngle(), Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        expenseRadiatorDial.startAnimation(rotateAnimation);
    }

    public float getSpinAngle() {
        return ((((float)(amountSpent/totalAmount))*180) - 90);
    }
}