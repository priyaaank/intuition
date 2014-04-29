package com.poc.intuition.domain;

public class CategoryStat {

    private PurchaseCategory category;
    private Double amountSpent;
    private Integer transactionCount;

    public CategoryStat(PurchaseCategory category, Double amountSpent, Integer transactionCount) {
        this.category = category;
        this.amountSpent = amountSpent;
        this.transactionCount = transactionCount;
    }

}
