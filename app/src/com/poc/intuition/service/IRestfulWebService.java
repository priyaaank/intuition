package com.poc.intuition.service;

public interface IRestfulWebService<Request, Response> {

    public Response makeServiceCall(Request requestObject);

}
