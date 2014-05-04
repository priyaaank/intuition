package com.poc.intuition.service.response;

import android.util.Log;
import com.poc.intuition.domain.*;
import com.poc.intuition.service.UserStatisticsService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserStatisticsResponse {

    private static final String MONTH_TAG = "month";
    private static final String YEAR_TAG = "year";
    private static final String TOTAL_AMOUNT_SPENT = "total_amount_spent";
    private static final String TRANSACTION_COUNT_TAG = "total_number_of_transactions";
    private static final String RECOMMENDED_BUDGET_TAG = "recommended_budget";
    private static final String ACTUAL_BUDGET_TAG = "actual_budget";
    private static final String CURRENT_TRANSACTION_COUNT_TAG = "current_transaction_count";
    private static final String SAVINGS_RATE_TAG = "savings_rate";
    private static final String EXPENSES_TOTAL_TAG = "current_expense_total";
    private static final String CATEGORY_STAT_TAG = "category_stats";
    private static final String CATEGORY_NAME_TAG = "category_name";
    private static final String CATEGORY_ID_TAG = "category_id";
    private static final String MAXIMUM_EXPENDITURE_TAG = "maximum_monthly_expenditure";
    private static final String MINIMUM_EXPENDITURE_TAG = "minimum_monthly_expenditure";
    private static final String AVERAGE_EXPENDITURE_TAG = "average_monthly_expenditure";
    private static final String TAG = "UserStatisticResponse";
    private static final String USERNAME_TAG = "user";
    private final static String HISTORIC_TAG = "historic";
    private static final String CURRENT_MONTH_TAG = "current_month";
    private static final String OVERALL_TAG = "overall";
    private static final String BUDGET_TAG = "budget";
    private static final String AMOUNT_SAVED_TAG = "total_amount_saved";
    private static final String SAVING_RATE_TAG = "saving_rate";


    private String username;
    private List<MonthlyStat> historicStats;
    private CurrentMonthStat currentMonthStat;
    private GroupStats groupStats;

    public UserStatisticsResponse(JSONObject response) {
        historicStats = new ArrayList<MonthlyStat>();
        initializeObjectWithJSONResponse(response);
    }

    public Double getRecommendedBudget() {
        return currentMonthStat.getRecommendedBudgetAmount();
    }

    private void initializeObjectWithJSONResponse(JSONObject response) {
        try {
            this.username = response.getString(USERNAME_TAG);
             extractHistoricStatsFrom(response);
            currentMonthStat = extractCurrentMonthStatsFrom(response);
            groupStats = extractGroupStats(response);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private GroupStats extractGroupStats(JSONObject response) throws JSONException, ParseException  {
        JSONObject groupStatsObject = response.getJSONObject(OVERALL_TAG);
        double maximumMonthlyExpenditure = groupStatsObject.getDouble(MAXIMUM_EXPENDITURE_TAG);
        double minimumMonthlyExpenditure = groupStatsObject.getDouble(MINIMUM_EXPENDITURE_TAG);
        double averageMonthlyExpenditure = groupStatsObject.getDouble(AVERAGE_EXPENDITURE_TAG);
        return new GroupStats(maximumMonthlyExpenditure, minimumMonthlyExpenditure, averageMonthlyExpenditure);
    }

    private CurrentMonthStat extractCurrentMonthStatsFrom(JSONObject response) throws JSONException, ParseException  {
        JSONObject currentMonthObject = response.getJSONObject(CURRENT_MONTH_TAG);
        double recommendedBudget = currentMonthObject.getDouble(RECOMMENDED_BUDGET_TAG);
        double actualBudget = currentMonthObject.getDouble(ACTUAL_BUDGET_TAG);
        int transactionCount = currentMonthObject.getInt(CURRENT_TRANSACTION_COUNT_TAG);
        double currentSavingsRate = currentMonthObject.getDouble(SAVINGS_RATE_TAG);
        double currentExpensesTotal = currentMonthObject.getDouble(EXPENSES_TOTAL_TAG);
        List<CategoryStat> categoryStatsForCurrentMonth = getCategoryStatsFrom(currentMonthObject);
        return new CurrentMonthStat(recommendedBudget, actualBudget, transactionCount, currentExpensesTotal, currentSavingsRate, categoryStatsForCurrentMonth);
    }

    private void extractHistoricStatsFrom(JSONObject response) throws JSONException, ParseException  {
        JSONArray historicMonthlyStats = response.getJSONArray(HISTORIC_TAG);
        int monthCount = historicMonthlyStats.length();
        for(int index = 0; index < monthCount; index++) {
            JSONObject monthlyStat = (JSONObject) historicMonthlyStats.get(index);
            int month = monthlyStat.getInt(MONTH_TAG);
            int year = monthlyStat.getInt(YEAR_TAG);
            double amountSpent = monthlyStat.getDouble(TOTAL_AMOUNT_SPENT);
            int transactionCount = monthlyStat.getInt(TRANSACTION_COUNT_TAG);
            double budgetAmount =  monthlyStat.getDouble(BUDGET_TAG);
            double totalAmountSaved =  monthlyStat.getDouble(AMOUNT_SAVED_TAG);
            double savingRate =  monthlyStat.getDouble(SAVING_RATE_TAG);
            List<CategoryStat> categoryStatsForMonth = getCategoryStatsFrom(monthlyStat);
            historicStats.add(new MonthlyStat(categoryStatsForMonth, month, year, amountSpent, transactionCount, budgetAmount, totalAmountSaved, savingRate));
        }
    }

    private List<CategoryStat> getCategoryStatsFrom(JSONObject response) throws JSONException {
        List<CategoryStat> statsToReturn = new ArrayList<CategoryStat>();
        JSONArray categoryStats = response.getJSONArray(CATEGORY_STAT_TAG);
        int categoryStatsCount = categoryStats.length();
        for(int index = 0; index < categoryStatsCount; index++) {
            JSONObject categoryStatObject = (JSONObject) categoryStats.get(index);
            int transactionCount = categoryStatObject.getInt(TRANSACTION_COUNT_TAG);
            double amountSpent = categoryStatObject.getDouble(TOTAL_AMOUNT_SPENT);
            String categoryName = categoryStatObject.getString(CATEGORY_NAME_TAG);
            int categoryId = categoryStatObject.getInt(CATEGORY_ID_TAG);
            statsToReturn.add(new CategoryStat(new PurchaseCategory(categoryId, categoryName), amountSpent, transactionCount));
        }
        return statsToReturn;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return historicStats;
    }

    public Double maximumMonthlyExpenditure() {
        return groupStats.maximumMonthlyExpenditure();
    }
}
