package com.lifesum.foodsearch.di.modules;

import com.github.aurae.retrofit.LoganSquareConverterFactory;
import com.lifesum.foodsearch.network.FoodSearchService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by PavlosPT13.
 * Class APIModule, that provides two different Retrofit clients for our Network needs.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.e(message);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    @Named("lifesum")
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(FoodSearchService.ENDPOINT_BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("googleImageSearch")
    Retrofit providesGoogleImageSearch(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(FoodSearchService.GOOGLE_IMAGE_SEARCH_ENDPOINT_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

}