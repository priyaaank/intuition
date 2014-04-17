package com.poc.intuition.service.response;

import android.util.Log;
import com.poc.intuition.domain.PurchaseCategory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PurchaseCategoryResponse {

  private static final String TAG = "PurchaseCategoryStatisticResponse";
  private final String USERNAME_TAG = "user";
  private final String NAME_TAG = "name";
  private final String ID_TAG = "id";
  private final String CATEGORIES_TAG = "categories";


  private List<PurchaseCategory> purchaseCategories;
  private String userName;

  public PurchaseCategoryResponse(JSONObject purchaseCategoryResponse) {
    purchaseCategories = new ArrayList<PurchaseCategory>();
    parsePurchaseCategoriesFromJson(purchaseCategoryResponse);
  }

  private void parsePurchaseCategoriesFromJson(JSONObject purchaseCategoryJsonResponse) {
    try {
      this.userName = purchaseCategoryJsonResponse.getString(USERNAME_TAG);
      JSONArray categoriesArray = purchaseCategoryJsonResponse.getJSONArray(CATEGORIES_TAG);
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
    Integer categoryId = ((JSONObject)categoriesObject).getInt(ID_TAG);
    purchaseCategories.add(new PurchaseCategory(categoryId, categoryName));
  }
}
