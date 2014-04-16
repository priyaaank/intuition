package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class LoginService implements ServiceConstants {

  private static final String TAG = "LoginService";
  private Context applicationContext;
  private IListener<Boolean> loginResponseListener;

  public LoginService(Context applicationContext, IListener<Boolean> listener) {
    this.applicationContext = applicationContext;
    this.loginResponseListener = listener;
  }

  public void loginForUserWithName(String username) {
    new LoginTask().execute(new String[]{username});
  }

  class LoginTask extends AsyncTask<String, Void, Integer> {

    private final String SERVICE_URL = SERVICE_HOST + "user/login/";

    @Override
    protected Integer doInBackground(String... params) {
      String username = params[0];
      GenericWebServiceResponse webServiceResponse = new HttpGetWrapper(SERVICE_URL + username).makeServiceCall(null);
      return webServiceResponse.statusCode();
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
      super.onPostExecute(statusCode);
      boolean isLoginSuccessful = statusCode.intValue() == 200;
      LoginService.this.loginResponseListener.serviceResponse(isLoginSuccessful);
    }
  }

}
