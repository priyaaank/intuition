package com.poc.intuition.service;

import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HttpDeleteWrapper implements IRestfulWebService<String, GenericWebServiceResponse> {

    private static final String TAG = "HttpDeleteWrapper";
    private String serviceUrl;

    public HttpDeleteWrapper(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public GenericWebServiceResponse makeServiceCall(String requestObject) {
        GenericWebServiceResponse genericResponse = new GenericWebServiceResponse();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(serviceUrl);
            HttpResponse response = client.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            if(200 == statusCode) {
                JSONObject responseJson = null;
                String responseString = EntityUtils.toString(response.getEntity());
                if(responseString.length() > 0) responseJson = new JSONObject(responseString);
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
