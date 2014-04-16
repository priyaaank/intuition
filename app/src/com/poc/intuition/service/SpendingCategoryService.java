package com.poc.intuition.service;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.service.response.SpendingCategoryResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SpendingCategoryService implements ServiceConstants {

  private static final String TAG = "SpendingCategoryService";
  private final IListener<SpendingCategoryResponse> listener;
  private final Context context;

  public SpendingCategoryService(Context applicationContext, IListener<SpendingCategoryResponse> listener) {
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
      listener.serviceResponse(new SpendingCategoryResponse(jsonResponse));
    }
  }
}
