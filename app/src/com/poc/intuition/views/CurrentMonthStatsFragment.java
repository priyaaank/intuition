package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.poc.intuition.R;
import com.poc.intuition.domain.CurrentMonthStat;
import com.poc.intuition.service.response.UserStatisticsResponse;
import com.poc.intuition.widgets.DonutExpenseSplit;
import com.poc.intuition.widgets.StackedExpensePredictor;

import java.util.Calendar;
import java.util.List;

public class CurrentMonthStatsFragment extends Fragment {

    private LinearLayout overviewGraphContainer;
    private LinearLayout categoryWiseGraphContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_month_stats, container, false);
        overviewGraphContainer = (LinearLayout) view.findViewById(R.id.overview_graph_container);
        categoryWiseGraphContainer = (LinearLayout) view.findViewById(R.id.category_graph_containers);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewTreeObserver overviewObserver = overviewGraphContainer.getViewTreeObserver();
        overviewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                populateOverviewGraph();
                overviewGraphContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        ViewTreeObserver categoryWiseObserver = overviewGraphContainer.getViewTreeObserver();
        categoryWiseObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                populateCategoryWiseGraph();
                categoryWiseGraphContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void populateCategoryWiseGraph() {
        UserStatisticsResponse userStatistics = ((Dashboard) getActivity()).getUserStatistics();
        CurrentMonthStat currentMonthStats = userStatistics.currentMonthStat();
        Double totalAmountSpent = currentMonthStats.getCurrentExpensesTotal();
        List<Double> categoryStats = currentMonthStats.getCategoryExpenseList();

        DonutExpenseSplit expenseSplit = new DonutExpenseSplit(getActivity().getApplicationContext(), totalAmountSpent, categoryStats.toArray(new Double[categoryStats.size()]));
        categoryWiseGraphContainer.addView(expenseSplit);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        expenseSplit.setAnimation(animation);
        expenseSplit.animate();

    }

    private int todaysDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private int noOfDaysInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void populateOverviewGraph() {
        UserStatisticsResponse userStatistics = ((Dashboard) getActivity()).getUserStatistics();
        Double maximumAmountForCurrentMonth = userStatistics.maximumMonthlyExpenditure();
        Double currentTotalSpentAmount = userStatistics.currentMonthSpentAmount();
        Double predictedAmountForSpending = userStatistics.getCurrentMonthBudget();

        RelativeLayout overviewGraph = new StackedExpensePredictor(getActivity().getApplicationContext(), maximumAmountForCurrentMonth,
                currentTotalSpentAmount, predictedAmountForSpending, noOfDaysInCurrentMonth(),
                todaysDay(), overviewGraphContainer.getWidth(), true).build();
        overviewGraphContainer.addView(overviewGraph);
    }


}
