package edu.nku.csc456.fall2015.service;

import java.util.List;

import edu.nku.csc456.fall2015.model.Chapter;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Benjamin on 8/22/2015.
 */
public interface Csc456ApiService {

    public static final String ENDPOINT_URL = "http://nku.benjamingbaxter.com/csc456/2015fall/app/api/";
    public static final String SLIDE_ENDPOINT_URL = "http://nku.benjamingbaxter.com/csc456/2015fall/";

    @GET("/chapters-grouped.php")
    Observable<List<Chapter>> getChapters();
}
