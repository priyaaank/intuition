package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import org.json.JSONException;
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
        new SpendingCategoryListTask().execute(new String[]{username});
    }

    public void createNewCategoryWithName(String username, String categoryName) {
        new SpendingCategoryCreateTask().execute(new String[]{username, categoryName});
    }

    class SpendingCategoryListTask extends AsyncTask<String, Void, JSONObject> {

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

    class SpendingCategoryCreateTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/category/new";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            String categoryName = params[1];
            String url = SERVICE_URL.replace("##USERNAME##", username);
            return new HttpPostWrapper(url).makeServiceCall(requestForNewCategory(categoryName));
        }

        @Override
        protected void onPostExecute(GenericWebServiceResponse webServiceResponse) {
            super.onPostExecute(webServiceResponse);
            //TODO, figure out how listeners should get response
            //listener.serviceResponse();
        }

        private JSONObject requestForNewCategory(String categoryName) {
            JSONObject newCategoryRequest = new JSONObject();
            try {
                newCategoryRequest.put("category_name", categoryName);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
            return newCategoryRequest;
        }
    }

}
