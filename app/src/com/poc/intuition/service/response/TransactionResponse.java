package com.poc.intuition.service.response;

import android.util.Log;
import com.poc.intuition.domain.PurchaseCategory;
import com.poc.intuition.domain.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransactionResponse {

    private static final String TAG = "TransactionResponse";

    private Date rangeStartDate;
    private Date rangeEndDate;
    private String username;
    private List<Transaction> transactionList;
    private SimpleDateFormat dateFormatter;

    private static final String USERNAME_TAG = "user";
    private static final String TRANSACTIONS_TAG = "transactions";
    private static final String START_DATE_TAG = "start_date";
    private static final String END_DATE_TAG = "end_date";
    private static final String TRANSACTION_ID_TAG = "id";
    private static final String MERCHANT_ID_TAG = "merchant_id";
    private static final String MERCHANT_NAME_TAG = "merchant_name";
    private static final String TRANSACTION_DATE_TAG = "transaction_date";
    private static final String CATEGORY_NAME_TAG = "category_name";
    private static final String CATEGORY_ID_TAG = "category_id";
    private static final String PRICE_TAG = "price";

    private final String DATE_FORMAT = "yyyy-MM-dd";


    public TransactionResponse(JSONObject transactionResponse) {
        transactionList = new ArrayList<Transaction>();
        dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        parseTransactionResponse(transactionResponse);
    }

    public List<Transaction> transactions() {
        return Collections.unmodifiableList(transactionList);
    }

    private void parseTransactionResponse(JSONObject transactionResponse) {
        try {
            this.username = (transactionResponse).getString(USERNAME_TAG);
            this.rangeStartDate = dateFormatter.parse(transactionResponse.getString(START_DATE_TAG));
            this.rangeEndDate = dateFormatter.parse(transactionResponse.getString(END_DATE_TAG));
            JSONArray transactionsArray = transactionResponse.getJSONArray(TRANSACTIONS_TAG);
            int transactionCount = transactionsArray.length();
            for(int transactionCountIndex = 0; transactionCountIndex < transactionCount; transactionCountIndex++) {
                addTransactionFromResponse(transactionsArray.get(transactionCountIndex));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void addTransactionFromResponse(Object transactionJsonObject) throws JSONException, ParseException {
        Integer transactionId = ((JSONObject)transactionJsonObject).getInt(TRANSACTION_ID_TAG);
        Integer merchantId = ((JSONObject)transactionJsonObject).getInt(MERCHANT_ID_TAG);
        String merchantName = ((JSONObject)transactionJsonObject).getString(MERCHANT_NAME_TAG);
        Date transactionDate = dateFormatter.parse(((JSONObject) transactionJsonObject).getString(TRANSACTION_DATE_TAG));
        Double transactionAmount = ((JSONObject) transactionJsonObject).getDouble(PRICE_TAG);
        String categoryId = ((JSONObject) transactionJsonObject).getString(CATEGORY_ID_TAG);
        String categoryName = ((JSONObject) transactionJsonObject).getString(CATEGORY_NAME_TAG);
        transactionList.add(new Transaction(transactionId, merchantId, merchantName, transactionDate, new PurchaseCategory(new Integer(categoryId), categoryName), transactionAmount));
    }

}
