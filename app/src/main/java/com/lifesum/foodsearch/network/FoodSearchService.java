package com.lifesum.foodsearch.network;

import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;
import com.lifesum.foodsearch.models.responsemodels.ImageSearchResponseModel;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by PavlosPT13.
 * Class that contains our API Calls and Endpoint Urls & Auth Tokens.
 * Tokens should be placed on our build.gradle and be excluded from the project,
 * but for the sake of time I included them in this Interface.
 */
public interface FoodSearchService {

    String ENDPOINT_BASE_URL = "url_endpoint";
    String GOOGLE_IMAGE_SEARCH_ENDPOINT_URL = "https://www.googleapis.com/customsearch/";
    String AUTH_TOKEN = "auth_token";
    String GOOGLE_API_KEY = "your_google_api_key";
    String GOOGLE_API_CX = "your_google_api_cx";

    @GET("{language}/{country}/{food}")
    Observable<BaseResponseModel> searchFood(
            @Header(QueryProperties.AUTHORIZATION_HEADER) String token,
            @Path(QueryProperties.LANGUAGE) String language,
            @Path(QueryProperties.COUNTRY) String country,
            @Path(QueryProperties.FOOD) String food
    );

    @GET("v1")
    Observable<ImageSearchResponseModel> searchGoogleImage(
            @Query(QueryProperties.KEY) String key,
            @Query(QueryProperties.CX) String cx,
            @Query(QueryProperties.SAFE) String safe,
            @Query(QueryProperties.SEARCH_TYPE) String searchType,
            @Query(QueryProperties.Q) String q
    );

}
