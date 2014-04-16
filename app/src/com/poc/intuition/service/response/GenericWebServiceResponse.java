package com.poc.intuition.service.response;

import org.json.JSONObject;

public class GenericWebServiceResponse {

    private Integer statusCode;
    private JSONObject responseEntity;

    public GenericWebServiceResponse() {
        this.statusCode = -1;
    }

    public GenericWebServiceResponse(final Integer statusCode, final JSONObject responseEntity) {
        this.responseEntity = responseEntity;
        this.statusCode = statusCode;
    }

    public boolean isSuccessResponse() {
        return statusCode.intValue() == 200;
    }

    public Integer statusCode() {
        return this.statusCode;
    }

    public JSONObject response() {
        return responseEntity;
    }
}
