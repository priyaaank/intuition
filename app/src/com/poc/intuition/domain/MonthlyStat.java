package com.poc.intuition.domain;

import java.util.List;

public class MonthlyStat {

    private Integer month;
    private Integer year;
    private Double amountSpent;
    private Integer transactionCount;
    private List<CategoryStat> categoryStats;

    public MonthlyStat(List<CategoryStat> categoryStats, int month, int year, double amountSpent, int transactionCount) {
        this.categoryStats = categoryStats;
        this.month = new Integer(month);
        this.year = new Integer(year);
        this.transactionCount = new Integer(transactionCount);
        this.amountSpent = new Double(amountSpent);
    }

}
