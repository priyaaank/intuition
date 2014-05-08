package com.poc.intuition.domain;

import java.util.HashMap;
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

    private static final HashMap<Integer, String> monthNumToTextMapping = new HashMap<Integer, String>();

    static {
        monthNumToTextMapping.put(new Integer(1), "Jan");
        monthNumToTextMapping.put(new Integer(2), "Feb");
        monthNumToTextMapping.put(new Integer(3), "Mar");
        monthNumToTextMapping.put(new Integer(4), "Apr");
        monthNumToTextMapping.put(new Integer(5), "May");
        monthNumToTextMapping.put(new Integer(6), "Jun");
        monthNumToTextMapping.put(new Integer(7), "Jul");
        monthNumToTextMapping.put(new Integer(8), "Aug");
        monthNumToTextMapping.put(new Integer(9), "Sep");
        monthNumToTextMapping.put(new Integer(10), "Oct");
        monthNumToTextMapping.put(new Integer(11), "Nov");
        monthNumToTextMapping.put(new Integer(12), "Dec");
    }

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

    public List<CategoryStat> getCategoryStats() {
        return categoryStats;
    }

    public String getTimespanLabel() {
        return monthNumToTextMapping.get(month)+" '"+year.toString().substring(2,4);
    }
}
