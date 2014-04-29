package com.poc.intuition.domain;

public class GroupStats {

    private Double maximumMonthlyExpenditure;
    private Double minimumMonthlyExpenditure;
    private Double averageMonthlyExpenditure;

    public GroupStats(Double maximumMonthlyExpenditure, Double minimumMonthlyExpenditure, Double averageMonthlyExpenditure) {
        this.maximumMonthlyExpenditure = maximumMonthlyExpenditure;
        this.minimumMonthlyExpenditure = minimumMonthlyExpenditure;
        this.averageMonthlyExpenditure = averageMonthlyExpenditure;
    }

}
