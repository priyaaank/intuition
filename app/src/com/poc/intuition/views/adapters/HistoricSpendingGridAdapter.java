package com.poc.intuition.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.poc.intuition.R;
import com.poc.intuition.domain.MonthlyStat;
import com.poc.intuition.widgets.CategoryHealthRadiator;

import java.util.ArrayList;
import java.util.List;

public class HistoricSpendingGridAdapter extends BaseAdapter {

    private List<MonthlyStat> historicSpendingList;
    private Context context;
    private LayoutInflater mInflater;

    public HistoricSpendingGridAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        historicSpendingList = new ArrayList<MonthlyStat>();
    }

    @Override
    public int getCount() {
        return historicSpendingList.size();
    }

    @Override
    public Object getItem(int position) {
        return historicSpendingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            view =  mInflater.inflate(R.layout.historic_spending_item, null);

            holder = new ViewHolder();
            holder.graphHolder = (RelativeLayout) view.findViewById(R.id.historic_graph_holder);
            holder.textView = (TextView) view.findViewById(R.id.month_and_year_value);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MonthlyStat monthlyStat = historicSpendingList.get(position);
        holder.graphHolder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                CategoryHealthRadiator categoryHealthRadiator = new CategoryHealthRadiator(context, monthlyStat.getBudget(), monthlyStat.getAmountSpent(), holder.graphHolder.getHeight(), holder.graphHolder.getWidth());
                categoryHealthRadiator = (monthlyStat.getAmountSpent() >= monthlyStat.getBudget()) ? categoryHealthRadiator.setStyleForOverspending() : categoryHealthRadiator.setStyleForHistoricGraph();
                holder.graphHolder.addView(categoryHealthRadiator.build());
                holder.graphHolder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        holder.textView.setText(monthlyStat.getTimespanLabel());
        return view;
    }

    public void setHistoricMonthlyStats(List<MonthlyStat> montlyStats) {
        this.historicSpendingList.clear();
        this.historicSpendingList.addAll(montlyStats);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView textView;
        public RelativeLayout graphHolder;
    }
}
