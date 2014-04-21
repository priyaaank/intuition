package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import com.poc.intuition.service.response.TransactionResponse;

public class TransactionService implements ServiceConstants {

    private static final String TAG = "TransactionService";
    private Context applicationContext;
    private IListener<TransactionResponse> transactionResponseListener;
    private final String CURRENT_MONTH = "current";

    public TransactionService(Context applicationContext, IListener<TransactionResponse> listener) {
        this.applicationContext = applicationContext;
        this.transactionResponseListener = listener;
    }

    public void findTransactionsForCurrentMonth(String username) {
        new TransactionsListingTask().execute(new String[]{username,CURRENT_MONTH});
    }

    public void findTransactionsForLastMonths(String username, int monthCount) {
        new TransactionsListingTask().execute(new String[]{username, new Integer(monthCount).toString()});
    }

    class TransactionsListingTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private final String SERVICE_URL = SERVICE_HOST + "/user/##USERNAME##/transactions/##PERIOD##/months";

        @Override
        protected GenericWebServiceResponse doInBackground(String... params) {
            String username = params[0];
            return new HttpGetWrapper(SERVICE_URL + username).makeServiceCall(null);
        }

        @Override
        protected void onPostExecute(GenericWebServiceResponse serviceResponse) {
            super.onPostExecute(serviceResponse);
            transactionResponseListener.serviceResponse(new TransactionResponse(serviceResponse.response()));
        }
    }

}