package com.poc.intuition.views;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.poc.intuition.R;
import com.poc.intuition.domain.CurrentMonthStat;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.domain.Transaction;
import com.poc.intuition.experiments.DashboardFragment;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.NewPurchaseResponse;
import com.poc.intuition.service.TransactionService;
import com.poc.intuition.service.UserStatisticsService;
import com.poc.intuition.service.response.UserStatisticsResponse;

import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends FragmentActivity {

    public static final int USER_STATS_MONTH_COUNT = 18;
    private SlidingMenu slidingMenu;
    private UserStatisticsService userStatisticsService;
    private TransactionService transactionService;
    private UserStatisticsResponse userStats;
    private IListener<NewPurchaseResponse> transactionCreationListener;
    private Timer transactionPoller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
        transactionCreationListener = getTransactionCreationListener();
        startTransactionPolling();
        lookupUserStats();
        updateActionBar();
        attachDefaultFragment();
        attachMenuNavigationEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        transactionService.registerTransactionCreationListener(transactionCreationListener);
        if(transactionPoller == null) {
            transactionPoller = new Timer();
            transactionPoller.schedule(new TransactionPoller(), 5000, 5000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        transactionService.deregisterTransactionCreationListener();
        if(transactionPoller != null) {
            transactionPoller.cancel();
            transactionPoller = null;
        }
    }

    private void startTransactionPolling() {
        transactionService = TransactionService.singleInstance(this.getApplicationContext());
        transactionService.registerTransactionCreationListener(getTransactionCreationListener());
    }

    public UserStatisticsResponse getUserStatistics() {
        return userStats;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    private void lookupUserStats() {
        userStatisticsService = UserStatisticsService.singleInstance(getApplicationContext());
        userStatisticsService.registerListener(getUserStatisticsListener());
        userStatisticsService.findUserStatsForLastMonths(USER_STATS_MONTH_COUNT);
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
        findViewById(R.id.this_month_summary).setOnClickListener(navigateToCurrentMonth());
        findViewById(R.id.purchase_link).setOnClickListener(navigateToPurchaseScreen());
    }

    private View.OnClickListener navigateToPurchaseScreen() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachPurchaseFragment(300d, 200d, 20d);
            }
        };
    }

    private void attachPurchaseFragment(double totalAmount, double amountSpent, double savingRate) {
        slidingMenu.showContent();
        PurchaseFragment purchaseFragment = PurchaseFragment.NewInstance(totalAmount, amountSpent, savingRate);
        attachFragmentWithTagToContentView(purchaseFragment, "PurchaseFragment");
    }

    private View.OnClickListener navigateToCurrentMonth() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showContent();
                CurrentMonthStatsFragment currentMonthStatsFragment = new CurrentMonthStatsFragment();
                attachFragmentWithTagToContentView(currentMonthStatsFragment, "CurrentMonthStatistics");
            }
        };
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

    private IListener<UserStatisticsResponse> getUserStatisticsListener() {
        return new IListener<UserStatisticsResponse>() {
            @Override
            public void serviceResponse(UserStatisticsResponse response) {
                userStats = response;
            }
        };
    }

    private IListener<NewPurchaseResponse> getTransactionCreationListener() {
        return new IListener<NewPurchaseResponse>() {
            @Override
            public void serviceResponse(final NewPurchaseResponse newPurchase) {
                userStatisticsService.findUserStatsForLastMonths(USER_STATS_MONTH_COUNT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        attachPurchaseFragment(newPurchase.getTotalAmountSpent(), newPurchase.getTotalMonthlyBudget(), 20d);
                    }
                });
            }
        };
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Logout").setMessage("Do you want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dashboard.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    class TransactionPoller extends TimerTask {
        @Override
        public void run() {
            transactionService.findLatestTransactionId();
        }
    }
}
