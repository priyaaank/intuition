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
import com.jjoe64.graphview.*;
import com.poc.intuition.R;
import com.poc.intuition.views.Dashboard;

public class ReceiptFragment extends Fragment {

    private static final String TAG = "ReceiptFragment";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.receipt, container, false);
        return inflatedView;
    }
}
