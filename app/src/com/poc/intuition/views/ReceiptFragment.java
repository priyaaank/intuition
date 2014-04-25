package com.poc.intuition.views;

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

public class ReceiptFragment extends Fragment {

    private static final String TAG = "ReceiptFragment";
    private LinearLayout graphHolder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphView.GraphViewData[] {
                new GraphView.GraphViewData(1, 2.0d)
                , new GraphView.GraphViewData(2, 1.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(3, 2.5d)
                , new GraphView.GraphViewData(4, 1.0d)
        });

        GraphView graphView = new LineGraphView(
                this.getActivity().getApplicationContext() // context
                , "" // heading
        );
        graphView.addSeries(exampleSeries); // data
        graphView.setBackgroundColor(Color.parseColor("#3498db"));
        graphView.setHorizontalLabels(new String[]{});
        graphView.setVerticalLabels(new String[]{});
        graphView.setMinimumWidth(20);

        graphHolder.addView(graphView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.receipt, container, false);
        graphHolder = (LinearLayout) inflatedView.findViewById(R.id.graph_holder);
        return inflatedView;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);

        anim.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "Animation started.");
            }

            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "Animation repeating.");
            }

            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "Animation ended.");
                ((HomeScreen)ReceiptFragment.this.getActivity()).onAnimationEnd(animation);
            }
        });

        return anim;
    }
}
