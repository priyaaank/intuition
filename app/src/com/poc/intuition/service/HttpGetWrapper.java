package com.poc.intuition.service;

import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HttpGetWrapper implements IRestfulWebService<String, GenericWebServiceResponse> {

    private static final String TAG = "HttpGetWrapper";
    private String serviceUrl;

    public HttpGetWrapper(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public GenericWebServiceResponse makeServiceCall(String requestObject) {
        GenericWebServiceResponse genericResponse = new GenericWebServiceResponse();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(serviceUrl);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if(200 == statusCode) {
                JSONObject responseJson = new JSONObject(EntityUtils.toString(response.getEntity()));
                genericResponse = new GenericWebServiceResponse(new Integer(statusCode), responseJson);
            }
        } catch(ClientProtocolException cpe) {
            Log.e(TAG, cpe.getMessage());
        } catch(IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return genericResponse;
    }
}
