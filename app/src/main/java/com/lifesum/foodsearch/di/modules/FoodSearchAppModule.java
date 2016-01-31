package com.lifesum.foodsearch.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.lifesum.foodsearch.FoodSearchApp;
import com.lifesum.foodsearch.interactors.FoodInteractorImpl;
import com.lifesum.foodsearch.presenters.FoodDetailsPresenterImpl;
import com.lifesum.foodsearch.presenters.MainPresenterImpl;
import com.lifesum.foodsearch.presenters.SavedFoodsPresenterImpl;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by PavlosPT13.
 * Class FoodSearchAppModule that provides the instances of our Interactor and Presenters implementations.
 */
@Module
public class FoodSearchAppModule {

    private FoodSearchApp application;

    public FoodSearchAppModule(FoodSearchApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    /*
    * Add our FoodInteractor implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    FoodInteractorImpl provideLessonsInteractor(@Named("lifesum") Retrofit retrofit,
                                                @Named("googleImageSearch") Retrofit retrofitImageSearch,
                                                StorIOSQLite storIOSQLite) {
        return new FoodInteractorImpl(retrofit, retrofitImageSearch, storIOSQLite);
    }

    /*
    * Add our MainPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    MainPresenterImpl provideMainViewPresenter(FoodInteractorImpl foodInteractor){
        return new MainPresenterImpl(foodInteractor);
    }

    /*
    * Add our FoodDetailsPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    FoodDetailsPresenterImpl provideFoodDetailsPresenter(FoodInteractorImpl foodInteractor){
        return new FoodDetailsPresenterImpl(foodInteractor);
    }

    /*
    * Add our SavedFoodsPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    SavedFoodsPresenterImpl provideSavedFoodsPresenter(FoodInteractorImpl foodInteractor){
        return new SavedFoodsPresenterImpl(foodInteractor);
    }

}