package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.poc.intuition.R;
import com.poc.intuition.experiments.DashboardFragment;

public class Dashboard extends FragmentActivity {

    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
        attachDefaultFragment();
        attachMenuNavigationEvents();
    }

    private void attachMenuNavigationEvents() {
        findViewById(R.id.transaction_history_link).setOnClickListener(navigateToTransactionHistory());
    }

    private View.OnClickListener navigateToTransactionHistory() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showContent();
                TransactionHistoryFragment transactionHistoryFragment = new TransactionHistoryFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, transactionHistoryFragment, "TransactionHistory")
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .commit();
            }
        };
    }

    private void attachDefaultFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, dashboardFragment, "Dashboard")
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .commit();
    }

    public void updateSelectedTransactionsWithCategory(String categoryName) {
        TransactionHistoryFragment transactionHistory = (TransactionHistoryFragment) getSupportFragmentManager().findFragmentByTag("TransactionHistory");
        transactionHistory.updateSelectedTransactionsWithCategory(categoryName);
    }
}
