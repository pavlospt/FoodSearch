package com.lifesum.foodsearch.di.components;

import com.lifesum.foodsearch.activities.FoodDetailsActivity;
import com.lifesum.foodsearch.activities.MainActivity;
import com.lifesum.foodsearch.activities.SavedFoodsActivity;
import com.lifesum.foodsearch.di.modules.ApiModule;
import com.lifesum.foodsearch.di.modules.DbModule;
import com.lifesum.foodsearch.di.modules.FoodSearchAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PavlosPT13.
 * Main Dagger Component of our Application.
 */
@Singleton
@Component(modules = {
        FoodSearchAppModule.class,
        ApiModule.class,
        DbModule.class
})
public interface FoodSearchAppComponent {
    void inject(MainActivity mainActivity);
    void inject(FoodDetailsActivity foodDetailsActivity);
    void inject(SavedFoodsActivity savedFoodsActivity);
}