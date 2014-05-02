package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.poc.intuition.R;
import com.poc.intuition.service.UserStatisticsService;
import com.viewpagerindicator.CirclePageIndicator;

public class SetupWizard extends FragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private UserStatisticsService userStatisticsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        userStatisticsService = UserStatisticsService.singleInstance(getApplicationContext());
        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.pager_indicator);
        titleIndicator.setViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CategorySelectionFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
