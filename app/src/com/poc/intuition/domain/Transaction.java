package com.poc.intuition.domain;

import java.util.Date;

public class Transaction {

    private Integer id;
    private Integer merchantId;
    private String merchantName;
    private Date transactionDate;
    private PurchaseCategory category;

    public Transaction(Integer id, Integer merchantId, String merchantName, Date transactionDate, PurchaseCategory category) {
        this.id = id;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.transactionDate = transactionDate;
        this.category = category;
    }

}
