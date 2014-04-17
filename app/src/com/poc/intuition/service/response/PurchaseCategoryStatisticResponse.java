package com.poc.intuition.service.response;

import android.util.Log;
import com.poc.intuition.domain.PurchaseCategoryStatistic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PurchaseCategoryStatisticResponse {

  private static final String TAG = "PurchaseCategoryStatisticResponse";
  private final String USERNAME_TAG = "user";
  private final String TOTAL_PRICE_TAG = "total_spending";
  private final String CATEGORIES_TAG = "categories";
  private final String NAME_TAG = "name";
  private final String COUNT_TAG = "count";
  private final String PRICE_TAG = "price_total";


  private List<PurchaseCategoryStatistic> spendingCategories;
  private Double totalAmountAcrossCategories;
  private String userName;

  public PurchaseCategoryStatisticResponse(JSONObject purchaseCategoryStatisticResponse) {
    spendingCategories = new ArrayList<PurchaseCategoryStatistic>();
    parseSpendingCategoriesFromJson(purchaseCategoryStatisticResponse);
  }

  private void parseSpendingCategoriesFromJson(JSONObject purchaseCategoryStatisticResponse) {
    try {
      this.userName = purchaseCategoryStatisticResponse.getString(USERNAME_TAG);
      this.totalAmountAcrossCategories = purchaseCategoryStatisticResponse.getDouble(TOTAL_PRICE_TAG);
      JSONArray categoriesArray = purchaseCategoryStatisticResponse.getJSONArray(CATEGORIES_TAG);
      int categoryObjectCount = categoriesArray.length();
      for(int index = 0; index < categoryObjectCount; index++) {
        addCategoryToList(categoriesArray.get(index));
      }
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  private void addCategoryToList(Object categoriesObject) throws JSONException {
    String categoryName = ((JSONObject)categoriesObject).getString(NAME_TAG);
    Integer transactionCount = ((JSONObject)categoriesObject).getInt(COUNT_TAG);
    Double totalCategoryPrice = ((JSONObject)categoriesObject).getDouble(PRICE_TAG);
    spendingCategories.add(new PurchaseCategoryStatistic(categoryName, transactionCount, totalCategoryPrice));
  }

}
