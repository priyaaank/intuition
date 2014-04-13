package com.poc.intuition.domain;

public class SpendingCategory {

  private final Double totalAmountSpent;
  private final Integer transactionCount;
  private final String name;

  public SpendingCategory(String categoryName, Integer categoryTransactionCount, Double totalAmountSpent) {
    this.name = categoryName;
    this.transactionCount = categoryTransactionCount;
    this.totalAmountSpent = totalAmountSpent;
  }

}
