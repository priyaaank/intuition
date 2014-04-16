package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import org.json.JSONObject;

public class PurchaseCategoryService implements ServiceConstants {

  private static final String TAG = "PurchaseCategoryService";
  private final IListener<PurchaseCategoryResponse> listener;
  private final Context context;

  public PurchaseCategoryService(Context applicationContext, IListener<PurchaseCategoryResponse> listener) {
    this.context = applicationContext;
    this.listener = listener;
  }

  public void fetchCategoriesForUsername(String username) {
    new SpendingCategoryTask().execute(new String[]{username});
  }

  class SpendingCategoryTask extends AsyncTask<String, Void, JSONObject> {

    private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/categories";

    @Override
    protected JSONObject doInBackground(String... params) {
        String username = params[0];
        String url = SERVICE_URL.replace("##USERNAME##",username);
        return new HttpGetWrapper(url).makeServiceCall(null).response();
    }

    @Override
    protected void onPostExecute(JSONObject jsonResponse) {
      super.onPostExecute(jsonResponse);
      listener.serviceResponse(new PurchaseCategoryResponse(jsonResponse));
    }
  }

}
