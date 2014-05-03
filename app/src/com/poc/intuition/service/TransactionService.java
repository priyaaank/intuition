package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import com.poc.intuition.service.response.TransactionResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionService implements ServiceConstants {

    private static final String TAG = "TransactionService";
    private Context applicationContext;
    private IListener<TransactionResponse> transactionResponseListener;
    private UserSessionService userSessionService;
    private final String CURRENT_MONTH = "current";

    public TransactionService(Context applicationContext, IListener<TransactionResponse> listener) {
        this.applicationContext = applicationContext;
        this.transactionResponseListener = listener;
        this.userSessionService = new UserSessionService(applicationContext);
    }

    public void updateTransactionsWithCategory(List<String> transactionIds, String categoryId) {
        String username = userSessionService.loggedInUsername();
        List<String> params = new ArrayList<String>();
        params.add(username);
        params.add(categoryId);
        params.addAll(transactionIds);
        new TransactionCategoryEditTask().execute(params.toArray(new String[params.size()]));
    }

    public void findTransactionsForCurrentMonth() {
        String username = userSessionService.loggedInUsername();
        new TransactionsListingTask().execute(new String[]{username,CURRENT_MONTH});
    }

    public void findTransactionsForLastMonths(int monthCount) {
        String username = userSessionService.loggedInUsername();
        new TransactionsListingTask().execute(new String[]{username, new Integer(monthCount).toString()});
    }

    class TransactionsListingTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private final String SERVICE_URL = SERVICE_HOST + "/user/##USERNAME##/transactions/##PERIOD##/months";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            String period = params[1];
            String url = SERVICE_URL.replace("##USERNAME##", username).replace("##PERIOD##", period);
            return new HttpGetWrapper(url).makeServiceCall(null);
        }

        @Override
        protected void onPostExecute(GenericWebServiceResponse serviceResponse) {
            super.onPostExecute(serviceResponse);
            transactionResponseListener.serviceResponse(new TransactionResponse(serviceResponse.response()));
        }
    }

    class TransactionCategoryEditTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/transactions/update";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            String categoryId = params[1];
            List<String> transactionIds = new ArrayList<String>();
            for(int index = 2; index < params.length ; index++)
                transactionIds.add(params[index]);
            String url = SERVICE_URL.replace("##USERNAME##", username);
            return new HttpPutWrapper(url).makeServiceCall(requestForNewCategory(categoryId, transactionIds));
        }

        @Override
        protected void onPostExecute(GenericWebServiceResponse webServiceResponse) {
            super.onPostExecute(webServiceResponse);
            transactionResponseListener.serviceResponse(new TransactionResponse(webServiceResponse.response()));
        }

        private JSONObject requestForNewCategory(String category_id, List<String> transaction_ids) {
            JSONObject newCategoryRequest = new JSONObject();
            try {
                newCategoryRequest.put("category_id", category_id);
                newCategoryRequest.put("transaction_ids", new JSONArray(transaction_ids));
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
            return newCategoryRequest;
        }
    }

}
