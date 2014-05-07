package com.poc.intuition.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.poc.intuition.R;
import com.poc.intuition.domain.CategoryStat;
import com.poc.intuition.domain.CurrentMonthStat;
import com.poc.intuition.service.response.UserStatisticsResponse;
import com.poc.intuition.views.Dashboard;
import com.poc.intuition.widgets.CategoryHealthRadiator;
import com.poc.intuition.widgets.GradientBar;

import java.util.List;

public class DashboardFragment extends Fragment {

    private LinearLayout categoryRadiatorContainer;
    private LinearLayout gradientBarHolder;
    private WebView chartView;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dashboard, container, false);
        categoryRadiatorContainer = (LinearLayout) inflatedView.findViewById(R.id.category_holder);
        gradientBarHolder = (LinearLayout) inflatedView.findViewById(R.id.gradient_bar_container);
//        chartView = (WebView) inflatedView.findViewById(R.id.chart_view);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        populateGradientBar();
        populateCategoryGraphs();

//        CustomWebViewClient client = new CustomWebViewClient();
//        chartView.setWebViewClient(client);
//        chartView.getSettings().setJavaScriptEnabled(true);
//        chartView.loadUrl("file:///android_asset/html/index.html");
//        chartView.setVerticalScrollBarEnabled(true);
//        chartView.setHorizontalScrollBarEnabled(false);
    }

    private void populateGradientBar() {
        final ViewTreeObserver gradientBarObserver = gradientBarHolder.getViewTreeObserver();
        gradientBarObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                RelativeLayout widget = new GradientBar(getActivity().getApplicationContext(), 2000d, 1200d, gradientBarHolder.getHeight(), gradientBarHolder.getWidth()).build();
                gradientBarHolder.addView(widget);
                gradientBarObserver.removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void populateCategoryGraphs() {

        final UserStatisticsResponse userStatistics = ((Dashboard) getActivity()).getUserStatistics();
        final CurrentMonthStat currentMonthStats = userStatistics.currentMonthStat();
        final Double totalAmountSpent = currentMonthStats.getCurrentExpensesTotal();
        final List<CategoryStat> categoryStats = currentMonthStats.getCategoryStats();
        ViewTreeObserver categoryObserver = categoryRadiatorContainer.getViewTreeObserver();
        categoryObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for(CategoryStat categoryStat : categoryStats) {
                    if(categoryStat.totalAmountSpent() > 0 && totalAmountSpent > 0 ) {
                        View wrapperView = inflater.inflate(R.layout.category_graph_wrapper_view, null);
                        RelativeLayout graphWrapper = (RelativeLayout) wrapperView.findViewById(R.id.category_graph_holder);
                        RelativeLayout widget = new CategoryHealthRadiator(getActivity().getApplicationContext(), totalAmountSpent, categoryStat.totalAmountSpent(), categoryRadiatorContainer.getHeight(), categoryRadiatorContainer.getHeight()).build();
                        graphWrapper.addView(widget);
                        ((TextView) wrapperView.findViewById(R.id.category_name)).setText(categoryStat.getName());
                        categoryRadiatorContainer.addView(wrapperView);
                    }
                }
                categoryRadiatorContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            return false;
        }
    }

}