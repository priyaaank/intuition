package com.poc.intuition.domain;

import java.util.Comparator;

public class CategoryStat {

    private PurchaseCategory category;
    private Double amountSpent;
    private Integer transactionCount;
    private Double expectedExpense;

    public CategoryStat(PurchaseCategory category, Double amountSpent, Integer transactionCount, Double expectedExpense) {
        this.category = category;
        this.amountSpent = amountSpent;
        this.transactionCount = transactionCount;
        this.expectedExpense = expectedExpense;
    }

    public Double totalAmountSpent() {
        return amountSpent;
    }

    public String getName() {
        return category.getName();
    }

    public static Comparator<CategoryStat> CategoryStatComparator = new Comparator<CategoryStat>() {

        @Override
        public int compare(CategoryStat lhs, CategoryStat rhs) {
            if (lhs.amountSpent == rhs.amountSpent) return 0;
            if (lhs.amountSpent > rhs.amountSpent) return -1;
            return 1;
        }
    };

    public Double getExpectedExpense() {
        return expectedExpense;
    }
}
