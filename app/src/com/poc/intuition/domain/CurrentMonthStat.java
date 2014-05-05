package com.poc.intuition.domain;

import java.util.ArrayList;
import java.util.List;

public class CurrentMonthStat {

    private final Double recommendedBudgetAmount;
    private final Double actualBudgetAmount;
    private final Integer transactionCount;
    private final Double currentExpensesTotal;
    private final Double currentSavingRate;
    private final List<CategoryStat> categoryStats;

    public CurrentMonthStat(Double recommendedBudgetAmount, Double actualBudgetAmount, Integer transactionCount,
                            Double currentExpensesTotal, Double currentSavingRate, List<CategoryStat> categoryStats) {
        this.recommendedBudgetAmount = recommendedBudgetAmount;
        this.actualBudgetAmount = actualBudgetAmount;
        this.transactionCount = transactionCount;
        this.currentExpensesTotal = currentExpensesTotal;
        this.currentSavingRate = currentSavingRate;
        this.categoryStats = categoryStats;
    }

    public Double getRecommendedBudgetAmount() {
        return recommendedBudgetAmount;
    }

    public Double getCurrentExpensesTotal() {
        return currentExpensesTotal;
    }

    public Double getCurrentSavingRate() {
        return currentSavingRate;
    }

    public List<CategoryStat> getCategoryStats() {
        return categoryStats;
    }

    public List<Double> getCategoryExpenseList() {
        List<Double> expensesToReturn = new ArrayList<Double>();
        for(CategoryStat categoryStat : categoryStats) {
            expensesToReturn.add(categoryStat.totalAmountSpent());
        }
        return expensesToReturn;
    }
}
