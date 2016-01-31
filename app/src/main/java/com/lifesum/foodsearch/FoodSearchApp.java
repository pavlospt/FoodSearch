package com.lifesum.foodsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.lifesum.foodsearch.di.components.DaggerFoodSearchAppComponent;
import com.lifesum.foodsearch.di.components.FoodSearchAppComponent;
import com.lifesum.foodsearch.di.modules.ApiModule;
import com.lifesum.foodsearch.di.modules.DbModule;
import com.lifesum.foodsearch.di.modules.FoodSearchAppModule;

import timber.log.Timber;

/**
 * Created by PavlosPT13.
 * Our Application Class.
 * Responsible for the Timber DebugTree plant and
 * the instantiation of the Dagger Graph.
 */
public class FoodSearchApp extends MultiDexApplication {

    private FoodSearchAppComponent predicktorAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        predicktorAppComponent = DaggerFoodSearchAppComponent.builder()
                .foodSearchAppModule(new FoodSearchAppModule(this))
                .dbModule(new DbModule())
                .apiModule(new ApiModule())
                .build();
    }

    public FoodSearchAppComponent getComponent() {
        return predicktorAppComponent;
    }

    public static FoodSearchApp from(@NonNull Context context) {
        return (FoodSearchApp) context.getApplicationContext();
    }
}
