package com.poc.intuition.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.UserSessionService;
import com.poc.intuition.service.UserStatisticsService;
import com.poc.intuition.service.response.UserStatisticsResponse;
import com.viewpagerindicator.CirclePageIndicator;

public class SetupWizard extends FragmentActivity implements IListener<UserStatisticsResponse> {

    private static final int NUM_PAGES = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private UserStatisticsService userStatisticsService;
    private UserSessionService userSessionService;
    private ProgressDialog progressDialog;
    private UserStatisticsResponse userStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        updateActionBar();
        showProgressDialog();
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        initializeSessionService();
        lookupUserStats();
        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.pager_indicator);
        titleIndicator.setViewPager(pager);
    }

    private void updateActionBar() {
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate( R.layout.setup_action_bar, null);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(actionBarLayout);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.color.text_purple));
    }

    private void initializeSessionService() {
        userSessionService = new UserSessionService(getApplicationContext());
    }

    private void lookupUserStats() {
        userStatisticsService = UserStatisticsService.singleInstance(getApplicationContext());
        userStatisticsService.registerListener(this);
        userStatisticsService.findUserStatsForLastMonths(18);
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Preparing", "Getting ready...");
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    public void finishSetup() {
        String budgetAmount = ((EditText)findViewById(R.id.budget_value)).getText().toString();
        userSessionService.markUserOnboardingComplete();
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void serviceResponse(UserStatisticsResponse response) {
        hideProgressDialog();
        userStats  = response;
        ((EditText)findViewById(R.id.budget_value)).setText(userStats.getRecommendedBudget().toString());
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BudgetSetupFragment();
                case 1:
                    return new CategorySelectionFragment();
            }
            return new CategorySelectionFragment ();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }


    }
}
