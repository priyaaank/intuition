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
    private IListener<PurchaseCategoryResponse> listener;
    private final Context context;
    private UserSessionService userSessionService;
    private static PurchaseCategoryService singleInstance;
    private PurchaseCategoryResponse purchaseCategoryResponse;

    private PurchaseCategoryService(Context applicationContext) {
        this.context = applicationContext;
        this.userSessionService = new UserSessionService(context);
    }

    public static PurchaseCategoryService singleInstance(Context applicationContext) {
        if(singleInstance == null) {
            singleInstance = new PurchaseCategoryService(applicationContext);
        }
        return singleInstance;
    }

    public void registerListener(IListener<PurchaseCategoryResponse> listener) {
        this.listener = listener;
    }

    public void fetchCategoriesForUser() {
        if(purchaseCategoryResponse != null  && listener != null) listener.serviceResponse(purchaseCategoryResponse);
        String loggedInUsername = userSessionService.loggedInUsername();
        new SpendingCategoryListTask().execute(new String[]{loggedInUsername});
    }

    public void createNewCategoryWithName(String categoryName) {
        String loggedInUsername = userSessionService.loggedInUsername();
        new SpendingCategoryCreateTask().execute(new String[]{loggedInUsername, categoryName});
    }

    public void editExistingCategoryNameForId(Integer categoryId, String newCategryName) {
        String loggedInUsername = userSessionService.loggedInUsername();
        new SpendingCategoryEditTask().execute(new String[]{loggedInUsername, categoryId.toString(), newCategryName});
    }

    public void deleteExistingCategoryByIds(String[] categoryIds) {
        String loggedInUsername = userSessionService.loggedInUsername();
        for(String categoryId : categoryIds) {
            new SpendingCategoryDeleteTask().execute(new String[]{loggedInUsername, categoryId});
        }
    }

    private void updatePurchaseCategories(PurchaseCategoryResponse purchaseCategoryResponse) {
        this.purchaseCategoryResponse = purchaseCategoryResponse;
        if(listener != null) listener.serviceResponse(this.purchaseCategoryResponse);
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
            PurchaseCategoryService.this.updatePurchaseCategories(new PurchaseCategoryResponse(jsonResponse));
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

    class SpendingCategoryEditTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/category/";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            String categoryId = params[1];
            String newCategoryName = params[2];
            String url = SERVICE_URL.replace("##USERNAME##", username);
            url = url + categoryId;
            return new HttpPutWrapper(url).makeServiceCall(requestForNewCategory(newCategoryName));
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

    class SpendingCategoryDeleteTask extends AsyncTask<String, Void, JSONObject> {

        private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/category/";

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String categoryId = params[1];
            String url = SERVICE_URL.replace("##USERNAME##",username);
            url = url + categoryId;
            return new HttpDeleteWrapper(url).makeServiceCall(null).response();
        }

        @Override
        protected void onPostExecute(JSONObject jsonResponse) {
            super.onPostExecute(jsonResponse);
//            if(listener != null) listener.serviceResponse(new PurchaseCategoryResponse(jsonResponse));
            fetchCategoriesForUser();
        }
    }

}
