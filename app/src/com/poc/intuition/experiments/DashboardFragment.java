package com.poc.intuition.experiments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.poc.intuition.R;
import com.poc.intuition.widgets.StackedExpensePredictor;

public class DashboardFragment extends Fragment {

    private LinearLayout graphContainer;
    private WebView chartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dashboard, container, false);
        graphContainer = (LinearLayout) inflatedView.findViewById(R.id.graph_container);
        chartView = (WebView) inflatedView.findViewById(R.id.chart_view);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewTreeObserver observer = graphContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                RelativeLayout stackedBar1 = new StackedExpensePredictor(getActivity().getApplicationContext(), 300f, 100f, 200f, 31, 12, graphContainer.getWidth(), true).build();
                graphContainer.addView(stackedBar1);
                graphContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        CustomWebViewClient client = new CustomWebViewClient();
        chartView.setWebViewClient(client);
        chartView.getSettings().setJavaScriptEnabled(true);
        chartView.loadUrl("file:///android_asset/html/index.html");
        chartView.setVerticalScrollBarEnabled(true);
        chartView.setHorizontalScrollBarEnabled(false);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            //do not let navigate away from page
            return false;
        }
    }




    //        Code to rotate the needle of gauge
    //        RotateAnimation rotateAnimation = new RotateAnimation(-90, 30, Animation.RELATIVE_TO_SELF, .5f,Animation.RELATIVE_TO_SELF, .5f);
    //        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
    //        rotateAnimation.setDuration(2000);
    //        rotateAnimation.setFillAfter(true);
    //
    //        getActivity().findViewById(R.id.needle).startAnimation(rotateAnimation);

}
