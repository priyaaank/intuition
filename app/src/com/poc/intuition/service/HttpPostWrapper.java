package com.poc.intuition.service;

import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpPostWrapper implements IRestfulWebService<JSONObject, GenericWebServiceResponse> {

    private static final String TAG = "HttpPostWrapper";
    private final String serviceUrl;

    public HttpPostWrapper(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public GenericWebServiceResponse makeServiceCall(JSONObject requestObject) {
        GenericWebServiceResponse webResponse = new GenericWebServiceResponse();

        try {
            JSONObject response = null;
            StringEntity requestEntity = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(serviceUrl);
            requestEntity = new StringEntity(requestObject.toString());
            httpPost.setEntity(requestEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
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
