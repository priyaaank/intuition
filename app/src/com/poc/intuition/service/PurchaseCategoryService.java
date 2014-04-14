package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
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
      JSONObject responseJson = null;

      try {
        HttpClient client = new DefaultHttpClient();
        String url = SERVICE_URL.replace("##USERNAME##",username);
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        if(200 == response.getStatusLine().getStatusCode()) {
          responseJson = new JSONObject(EntityUtils.toString(response.getEntity()));
        }
      } catch(ClientProtocolException cpe) {
        Log.e(TAG, cpe.getMessage());
      } catch(IOException ioe) {
        Log.e(TAG, ioe.getMessage());
      } catch (JSONException e) {
        Log.e(TAG, e.getMessage());
      }

      return responseJson;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResponse) {
      super.onPostExecute(jsonResponse);
      listener.serviceResponse(new PurchaseCategoryResponse(jsonResponse));
    }
  }

}
