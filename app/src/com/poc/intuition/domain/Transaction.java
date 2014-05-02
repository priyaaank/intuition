package com.poc.intuition.domain;

import java.util.Date;

public class Transaction {

    private Integer id;
    private Integer merchantId;
    private String merchantName;
    private Date transactionDate;
    private PurchaseCategory category;
    private Double transactionAmount;

    public Transaction(Integer id, Integer merchantId, String merchantName, Date transactionDate, PurchaseCategory category, Double transactionAmount) {
        this.id = id;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.transactionDate = transactionDate;
        this.category = category;
        this.transactionAmount = transactionAmount;
    }

    public String merchantName() {
        return this.merchantName;
    }

    public String transactionAmount() {
        return this.transactionAmount.toString();
    }

    public String categoryName() {
       return this.category.getName();
    }
}
