package com.poc.intuition.service;

import android.content.Context;
import android.os.AsyncTask;
import com.poc.intuition.service.response.GenericWebServiceResponse;
import com.poc.intuition.service.response.UserStatisticsResponse;

public class UserStatisticsService implements ServiceConstants {

    private static final String TAG = "UserStatisticsService";
    private Context applicationContext;
    private IListener<UserStatisticsResponse> userStatsListener;
    private UserSessionService userSessionService;
    private static UserStatisticsService singleInstance;

    public static UserStatisticsService singleInstance(Context applicationContext) {
        if(singleInstance == null) {
            singleInstance = new UserStatisticsService(applicationContext);
        }
        return  singleInstance;
    }

    private UserStatisticsService(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.userSessionService = new UserSessionService(applicationContext);
    }

    public void registerListener(IListener<UserStatisticsResponse> listener) {
        this.userStatsListener = listener;
    }
    public void deregisterListener() {
        this.userStatsListener = null;
    }

    public void findUserStatsForLastMonths(int monthCount) {
        String loggedInUsername = userSessionService.loggedInUsername();
        new UserStatisticsFetchTask().execute(new String[]{loggedInUsername, new Integer(monthCount).toString()});
    }

    class UserStatisticsFetchTask extends AsyncTask<String, Void, GenericWebServiceResponse> {

        private final String SERVICE_URL = SERVICE_HOST + "user/##USERNAME##/stats/last/##PERIOD##/months";

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
            if(userStatsListener!=null) userStatsListener.serviceResponse(new UserStatisticsResponse(serviceResponse.response()));
        }
    }
}
