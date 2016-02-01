package com.lifesum.foodsearch.di.modules;

import android.content.Context;

import com.lifesum.foodsearch.FoodSearchApp;
import com.lifesum.foodsearch.interactors.FoodInteractorImpl;
import com.lifesum.foodsearch.interactors.interfaces.IFoodInteractor;
import com.lifesum.foodsearch.presenters.FoodDetailsPresenterImpl;
import com.lifesum.foodsearch.presenters.MainPresenterImpl;
import com.lifesum.foodsearch.presenters.SavedFoodsPresenterImpl;
import com.lifesum.foodsearch.presenters.interfaces.IFoodDetailsPresenter;
import com.lifesum.foodsearch.presenters.interfaces.IMainPresenter;
import com.lifesum.foodsearch.presenters.interfaces.ISaveFoodsPresenter;
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
    IFoodInteractor provideLessonsInteractor(@Named("lifesum") Retrofit retrofit,
                                             @Named("googleImageSearch") Retrofit retrofitImageSearch,
                                             StorIOSQLite storIOSQLite) {
        return new FoodInteractorImpl(retrofit, retrofitImageSearch, storIOSQLite);
    }

    /*
    * Add our MainPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    IMainPresenter provideMainViewPresenter(IFoodInteractor foodInteractor) {
        return new MainPresenterImpl(foodInteractor);
    }

    /*
    * Add our FoodDetailsPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    IFoodDetailsPresenter provideFoodDetailsPresenter(IFoodInteractor foodInteractor) {
        return new FoodDetailsPresenterImpl(foodInteractor);
    }

    /*
    * Add our SavedFoodsPresenterImpl implementation to Dagger Graph.
    * */
    @Singleton
    @Provides
    ISaveFoodsPresenter provideSavedFoodsPresenter(IFoodInteractor foodInteractor) {
        return new SavedFoodsPresenterImpl(foodInteractor);
    }

}