package com.poc.intuition.service;

import com.poc.intuition.domain.Transaction;

public class NewPurchaseResponse
{
    private Transaction transaction;
    private Double totalAmountSpent;
    private Double totalMonthlyBudget;

    public NewPurchaseResponse(Transaction transaction, Double totalAmountSpent, Double totalMonthlyBudget) {
        this.transaction = transaction;
        this.totalAmountSpent = totalAmountSpent;
        this.totalMonthlyBudget = totalMonthlyBudget;
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
}
