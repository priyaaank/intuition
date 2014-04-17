package com.poc.intuition.domain;

public class PurchaseCategoryStatistic {

  private final Double totalAmountSpent;
  private final Integer transactionCount;
  private final String name;

  public PurchaseCategoryStatistic(String categoryName, Integer categoryTransactionCount, Double totalAmountSpent) {
    this.name = categoryName;
    this.transactionCount = categoryTransactionCount;
    this.totalAmountSpent = totalAmountSpent;
  }

}
