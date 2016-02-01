package com.lifesum.foodsearch.views;

import com.lifesum.foodsearch.models.FoodModel;

/**
 * Created by PavlosPT13.
 */
public interface IFoodDetailsView {

    void presentSelectedFood(FoodModel foodModel);

    void showSaveError();

    void showSaveSuccess();

    void showFoodAlreadyExists();

    void setFoodIsSaved();

    void setFoodIsNotSaved();

    void showDeletionError();

    void showFoodImage(String url);
}
