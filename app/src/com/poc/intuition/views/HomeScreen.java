package com.poc.intuition.views;

import android.app.Activity;
import android.os.Bundle;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.SpendingCategoryService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import com.poc.intuition.service.response.SpendingCategoryResponse;

public class HomeScreen extends Activity implements IListener<PurchaseCategoryResponse> {

  private PurchaseCategoryService purchaseCategoryService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

    purchaseCategoryService = new PurchaseCategoryService(this.getApplicationContext(), this);
    purchaseCategoryService.fetchCategoriesForUsername("david");
  }

  @Override
  public void serviceResponse(PurchaseCategoryResponse response) {


  }
}
