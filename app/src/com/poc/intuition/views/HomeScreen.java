package com.poc.intuition.views;

import android.app.Activity;
import android.os.Bundle;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.SpendingCategoryService;
import com.poc.intuition.service.response.SpendingCategoryResponse;

public class HomeScreen extends Activity implements IListener<SpendingCategoryResponse> {

  private SpendingCategoryService spendingCategoryService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

    spendingCategoryService = new SpendingCategoryService(this.getApplicationContext(), this);
    spendingCategoryService.fetchTransactionsForUsername("david");
  }

  @Override
  public void serviceResponse(SpendingCategoryResponse response) {

  }
}
