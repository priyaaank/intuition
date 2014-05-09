package com.poc.intuition.service;

import com.poc.intuition.domain.Transaction;

public class NewPurchaseResponse
{
    private Transaction transaction;
    private Double totalAmountSpent;
    private Double totalMonthlyBudget;
    private Double savingRate;

    public NewPurchaseResponse(Transaction transaction, Double totalAmountSpent, Double totalMonthlyBudget, Double savingRate) {
        this.transaction = transaction;
        this.totalAmountSpent = totalAmountSpent;
        this.totalMonthlyBudget = totalMonthlyBudget;
        this.savingRate = savingRate;
    }

    public String getMerchantName() {
        return transaction.merchantName();
    }

    public String getTransactionAmount() {
        return transaction.transactionAmount();
    }

    public Integer getTransactionId() {
        return transaction.getId();
    }

    public Double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public Double getTotalMonthlyBudget() {
        return totalMonthlyBudget;
    }

    public Double savingRate() {
        return savingRate;
    }
}
