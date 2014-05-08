package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.poc.intuition.R;
import com.poc.intuition.domain.MonthlyStat;
import com.poc.intuition.service.response.UserStatisticsResponse;
import com.poc.intuition.views.adapters.HistoricSpendingGridAdapter;
import com.poc.intuition.widgets.StackedExpensePredictor;

import java.util.List;

public class HistoricSpendingFragment extends Fragment {

    private LinearLayout spendingGraphsHolder;
    private GridView historicSpendingGraphsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.historic_spending, container, false);
        historicSpendingGraphsContainer = (GridView) inflatedView.findViewById(R.id.historic_spending_container);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewTreeObserver observer = historicSpendingGraphsContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                populateHistoricCharts();
                historicSpendingGraphsContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void populateHistoricCharts() {
        UserStatisticsResponse userStatistics = ((Dashboard) getActivity()).getUserStatistics();
        List<MonthlyStat> montlyStats = userStatistics.getMonthlyStats();
        HistoricSpendingGridAdapter adapter = new HistoricSpendingGridAdapter(getActivity().getApplicationContext());
        adapter.setHistoricMonthlyStats(montlyStats.subList(0, 12));
        historicSpendingGraphsContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void populateUserCharts() {
        String currentYear = "";
        UserStatisticsResponse userStatistics = ((Dashboard) getActivity()).getUserStatistics();
        Double maximumSpentAmount = userStatistics.maximumMonthlyExpenditure();
        List<MonthlyStat> montlyStats = userStatistics.getMonthlyStats();
        for(MonthlyStat monthlyStat : montlyStats) {
            if(!monthlyStat.getYear().equalsIgnoreCase(currentYear)) {
                addYearSeperator(monthlyStat.getYear());
                currentYear = monthlyStat.getYear();
            }

            RelativeLayout barGraph = new StackedExpensePredictor(getActivity().getApplicationContext(), maximumSpentAmount, monthlyStat.getAmountSpent(), monthlyStat.getBudget(), 30, 0, spendingGraphsHolder.getWidth(), false).build();
            View graphWrapper = getActivity().getLayoutInflater().inflate(R.layout.graph_wrapper, null, false);
            spendingGraphsHolder.addView(graphWrapper);
            spendingGraphsHolder.addView(barGraph);
        }
    }

    private void addYearSeperator(String year) {
        View seperator = getActivity().getLayoutInflater().inflate(R.layout.year_seperator, null, false);
        ((TextView)seperator.findViewById(R.id.year_value)).setText(year);
        spendingGraphsHolder.addView(seperator);
    }
}
