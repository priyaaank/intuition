package com.poc.intuition.domain;

public class PurchaseCategory {

  private Integer id;
  private String name;

  public PurchaseCategory(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
