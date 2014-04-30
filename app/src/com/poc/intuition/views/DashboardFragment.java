package com.poc.intuition.views;

import android.graphics.Interpolator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import com.poc.intuition.R;
import com.poc.intuition.widgets.RadiatorNeedle;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dashboard, container, false);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RotateAnimation rotateAnimation = new RotateAnimation(-90, 30, Animation.RELATIVE_TO_SELF, .5f,Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);

        getActivity().findViewById(R.id.needle).startAnimation(rotateAnimation);
    }
}
