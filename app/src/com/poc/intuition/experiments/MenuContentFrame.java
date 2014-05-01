package com.poc.intuition.experiments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.poc.intuition.R;
import com.poc.intuition.experiments.DashboardFragment;

public class MenuContentFrame extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        attachDefaultFragment();
    }

    private void attachDefaultFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, dashboardFragment, "Dashboard")
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .commit();
    }

}


