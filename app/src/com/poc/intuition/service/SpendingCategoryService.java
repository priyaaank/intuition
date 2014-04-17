package com.poc.intuition.service;


import android.content.Context;
import android.os.AsyncTask;
import com.poc.intuition.service.response.PurchaseCategoryStatisticResponse;
import org.json.JSONObject;

public class SpendingCategoryService implements ServiceConstants {

  private static final String TAG = "SpendingCategoryService";
  private final IListener<PurchaseCategoryStatisticResponse> listener;
  private final Context context;

  public SpendingCategoryService(Context applicationContext, IListener<PurchaseCategoryStatisticResponse> listener) {
    this.context = applicationContext;
    this.listener = listener;
  }

  public void fetchTransactionsForUsername(String username) {
    new SpendingCategoryTask().execute(new String[]{username});
  }

  class SpendingCategoryTask extends AsyncTask<String, Void, JSONObject> {

    private static final String SERVICE_URL = SERVICE_HOST + "user/david/transactions/2/months/categorize";

    @Override
    protected JSONObject doInBackground(String... params) {
      String username = params[0];
      return new HttpGetWrapper(SERVICE_URL).makeServiceCall(null).response();
    }

    @Override
    protected void onPostExecute(JSONObject jsonResponse) {
      super.onPostExecute(jsonResponse);
      listener.serviceResponse(new PurchaseCategoryStatisticResponse(jsonResponse));
    }
  }
}
