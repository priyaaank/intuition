package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.poc.intuition.R;
import com.poc.intuition.experiments.DashboardFragment;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.UserStatisticsService;
import com.poc.intuition.service.response.UserStatisticsResponse;

public class Dashboard extends FragmentActivity implements IListener<UserStatisticsResponse> {

    private SlidingMenu slidingMenu;
    private UserStatisticsService userStatisticsService;
    private UserStatisticsResponse userStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
        lookupUserStats();
        updateActionBar();
        attachDefaultFragment();
        attachMenuNavigationEvents();
    }

    public UserStatisticsResponse getUserStatistics() {
        return userStats;
    }

    private void lookupUserStats() {
        userStatisticsService = UserStatisticsService.singleInstance(getApplicationContext());
        userStatisticsService.registerListener(this);
        userStatisticsService.findUserStatsForLastMonths(10);
    }

    private void updateActionBar() {
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.home_action_bar,
                null);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(actionBarLayout);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        actionBarLayout.findViewById(R.id.action_bar_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });
    }

    private void toggleMenu() {
        if(slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else {
            slidingMenu.showMenu();
        }
    }

    private void attachMenuNavigationEvents() {
        findViewById(R.id.transaction_history_link).setOnClickListener(navigateToTransactionHistory());
        findViewById(R.id.historic_spending_overview_link).setOnClickListener(navigateToSpendingOverview());
        findViewById(R.id.dashboard_link).setOnClickListener(navigateToDashboard());
    }

    private View.OnClickListener navigateToDashboard() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          attachDefaultFragment();
            }
        };
    }

    private View.OnClickListener navigateToSpendingOverview() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showContent();
                HistoricSpendingFragment historicSpendingFragment = new HistoricSpendingFragment();
                attachFragmentWithTagToContentView(historicSpendingFragment, "HistoricSpendingOverview");
            }
        };
    }

    private View.OnClickListener navigateToTransactionHistory() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showContent();
                TransactionHistoryFragment transactionHistoryFragment = new TransactionHistoryFragment();
                attachFragmentWithTagToContentView(transactionHistoryFragment, "TransactionHistory");
            }
        };
    }

    private void attachDefaultFragment() {
        slidingMenu.showContent();
        DashboardFragment dashboardFragment = new DashboardFragment();
        attachFragmentWithTagToContentView(dashboardFragment, "Dashboard");
    }

    private void attachFragmentWithTagToContentView(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public void updateSelectedTransactionsWithCategory(String categoryName) {
        TransactionHistoryFragment transactionHistory = (TransactionHistoryFragment) getSupportFragmentManager().findFragmentByTag("TransactionHistory");
        transactionHistory.updateSelectedTransactionsWithCategory(categoryName);
    }

    @Override
    public void serviceResponse(UserStatisticsResponse response) {
        this.userStats = response;
    }
}