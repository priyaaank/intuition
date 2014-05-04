package com.poc.intuition.domain;

import java.util.List;

public class MonthlyStat {

    private Integer month;
    private Integer year;
    private Double amountSpent;
    private Integer transactionCount;
    private Double totalAmountSaved;
    private Double savingRate;
    private Double budget;
    private List<CategoryStat> categoryStats;

    public MonthlyStat(List<CategoryStat> categoryStats, int month, int year, double amountSpent, int transactionCount,
                       double budget, double totalAmountSaved, double savingRate) {
        this.categoryStats = categoryStats;
        this.month = new Integer(month);
        this.year = new Integer(year);
        this.transactionCount = new Integer(transactionCount);
        this.amountSpent = new Double(amountSpent);
        this.totalAmountSaved = totalAmountSaved;
        this.budget = budget;
        this.savingRate = savingRate;
    }

    public String getYear() {
        return year.toString();
    }

    public Double getAmountSpent() {
        return amountSpent;
    }

    public Double getBudget() {
        return budget;
    }

}
