package com.lifesum.foodsearch.views;

import com.lifesum.foodsearch.models.FoodModel;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 */
public interface ISavedFoodsView {

    void presentSavedFoods(ArrayList<FoodModel> savedFoods);

    void presentSearchedFoods(ArrayList<FoodModel> searchedFoods);
}
