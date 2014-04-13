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

public class SpendingCategoryService {

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

    private static final String SERVICE_URL = "http://192.168.2.3:9292/user/david/transactions/2/months/categorize";

    @Override
    protected JSONObject doInBackground(String... params) {
      String username = params[0];
      JSONObject responseJson = null;

      try {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(SERVICE_URL);
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
      listener.serviceResponse(new SpendingCategoryResponse(jsonResponse));
    }
  }
}
