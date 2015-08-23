package edu.nku.csc456.fall2015.service;

import retrofit.RestAdapter;

/**
 * Created by Benjamin on 8/22/2015.
 */
public class ApiServiceFactory {

    private static Csc456ApiService csc456ApiService;

    public static Csc456ApiService getCsc456ApiService() {
        if( csc456ApiService == null ) {
            csc456ApiService = new RestAdapter.Builder()
                    .setEndpoint(Csc456ApiService.ENDPOINT_URL).build()
                    .create(Csc456ApiService.class);
        }
        return csc456ApiService;
    }
}
