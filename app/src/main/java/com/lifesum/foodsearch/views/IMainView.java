package com.lifesum.foodsearch.views;

import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 */
public interface IMainView {

    void presentFoodSearch(BaseResponseModel baseResponseModel);

    void presentCachedFoods(ArrayList<FoodModel> cachedFoods);

}
