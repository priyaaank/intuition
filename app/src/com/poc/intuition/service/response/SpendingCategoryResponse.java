package com.poc.intuition.service.response;

import android.util.Log;
import com.poc.intuition.domain.SpendingCategory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpendingCategoryResponse {

  private static final String TAG = "SpendingCategoryResponse";
  private final String USERNAME_TAG = "user";
  private final String TOTAL_PRICE_TAG = "total_spending";
  private final String CATEGORIES_TAG = "categories";
  private final String NAME_TAG = "name";
  private final String COUNT_TAG = "count";
  private final String PRICE_TAG = "price_total";


  private List<SpendingCategory> spendingCategories;
  private Double totalAmountAcrossCategories;
  private String userName;

  public SpendingCategoryResponse(JSONObject spendingCategoryJsonResponse) {
    spendingCategories = new ArrayList<SpendingCategory>();
    parseSpendingCategoriesFromJson(spendingCategoryJsonResponse);
  }

  private void parseSpendingCategoriesFromJson(JSONObject spendingCategoryJsonResponse) {
    try {
      this.userName = spendingCategoryJsonResponse.getString(USERNAME_TAG);
      JSONArray categoriesArray = spendingCategoryJsonResponse.getJSONArray(CATEGORIES_TAG);
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
    spendingCategories.add(new SpendingCategory(categoryName, transactionCount, totalCategoryPrice));
  }

}
