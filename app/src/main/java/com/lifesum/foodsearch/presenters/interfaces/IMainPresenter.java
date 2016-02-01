package com.lifesum.foodsearch.presenters.interfaces;

import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.views.IMainView;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 */
public interface IMainPresenter extends IPresenter<IMainView> {

    void searchFood(String searchTerm);

    void cacheFoods(ArrayList<FoodModel> cachedFoods);

    void loadCachedFoods();
}
