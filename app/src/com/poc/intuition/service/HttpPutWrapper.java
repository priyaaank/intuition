package com.poc.intuition.service;

import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpPutWrapper implements IRestfulWebService<JSONObject, GenericWebServiceResponse> {

    private static final String TAG = "HttpPutWrapper";
    private String serviceUrl;

    public HttpPutWrapper(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public GenericWebServiceResponse makeServiceCall(JSONObject requestObject) {
        GenericWebServiceResponse webResponse = new GenericWebServiceResponse();

        try {
            JSONObject response = null;
            StringEntity requestEntity = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(serviceUrl);
            requestEntity = new StringEntity(requestObject.toString());
            httpPut.setEntity(requestEntity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPut);
            response = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
            webResponse = new GenericWebServiceResponse(new Integer(httpResponse.getStatusLine().getStatusCode()), response);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return webResponse;
    }
}
