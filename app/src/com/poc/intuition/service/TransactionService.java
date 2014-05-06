package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.domain.Transaction;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import com.poc.intuition.service.response.TransactionResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements ServiceConstants {

    private static final String TAG = "TransactionService";
    private Context applicationContext;
    private List<IListener<TransactionResponse>> transactionResponseListeners;
    private IListener<Transaction> transactionCreationListener;
    private UserSessionService userSessionService;
    private final String CURRENT_MONTH = "current";
    private static TransactionService instance;

    public static TransactionService singleInstance(Context applicationContext) {
        if(instance == null) {
            instance = new TransactionService(applicationContext);
        }
        return instance;
    }

    private TransactionService(Context applicationContext) {
        this.applicationContext = applicationContext;
        transactionResponseListeners = new ArrayList<IListener<TransactionResponse>>();
        this.userSessionService = new UserSessionService(applicationContext);
    }

    public void registerListener(IListener<TransactionResponse> listener) {
        if(!transactionResponseListeners.contains(listener)) transactionResponseListeners.add(listener);
    }

    public void deregisterListener(IListener<TransactionResponse> listener) {
        if(transactionResponseListeners.contains(listener)) transactionResponseListeners.remove(listener);
    }

    public void updateTransactionsWithCategory(List<String> transactionIds, String categoryId) {
        String username = userSessionService.loggedInUsername();
        List<String> params = new ArrayList<String>();
        params.add(username);
        params.add(categoryId);
        params.addAll(transactionIds);
        new TransactionCategoryEditTask().execute(params.toArray(new String[params.size()]));
    }

    public void findLatestTransactionId() {
        String username = userSessionService.loggedInUsername();
        new LatestTransactionFetchTask().execute(new String[]{username});
    }

    public void findTransactionsForCurrentMonth() {
        String username = userSessionService.loggedInUsername();
        new TransactionsListingTask().execute(new String[]{username,CURRENT_MONTH});
    }

    public void findTransactionsForLastMonths(int monthCount) {
        String username = userSessionService.loggedInUsername();
        new TransactionsListingTask().execute(new String[]{username, new Integer(monthCount).toString()});
    }

    public void registerTransactionCreationListener(IListener<Transaction> listener) {
        this.transactionCreationListener = listener;
    }

    public void deregisterTransactionCreationListener() {
        this.transactionCreationListener = null;
    }

    private void latestTransactionIs(Transaction transaction) {
        int currentId = userSessionService.getLastKnownLatestTransactionId();
        if(currentId != transaction.getId().intValue()) {
            userSessionService.setLatestTransactionIdForUser(transaction.getId().intValue());
            if(transactionCreationListener != null) transactionCreationListener.serviceResponse(transaction);
        }
    }

    private void notifyListeners(TransactionResponse response) {
        for(IListener<TransactionResponse> listener : transactionResponseListeners) {
            listener.serviceResponse(response);
        }
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
            notifyListeners(new TransactionResponse(serviceResponse.response()));
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
            notifyListeners(new TransactionResponse(webServiceResponse.response()));
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

    class LatestTransactionFetchTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private static final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/transactions/latest";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            String url = SERVICE_URL.replace("##USERNAME##", username);
            return new HttpGetWrapper(url).makeServiceCall(null);
        }

        @Override
        protected void onPostExecute(GenericWebServiceResponse webServiceResponse) {
            super.onPostExecute(webServiceResponse);
            JSONObject responseObject = webServiceResponse.response();
            try {
                if(responseObject != null) {
                    Transaction transaction = new TransactionResponse().extractTransactionFromJsonResponse(responseObject);
                    TransactionService.this.latestTransactionIs(transaction);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

}
