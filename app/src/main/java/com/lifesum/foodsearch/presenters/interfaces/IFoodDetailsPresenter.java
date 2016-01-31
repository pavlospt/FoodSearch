package com.lifesum.foodsearch.presenters.interfaces;

import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.views.IFoodDetailsView;

/**
 * Created by PavlosPT13.
 */
public interface IFoodDetailsPresenter extends IPresenter<IFoodDetailsView> {

    void loadSelectedFood(String id, String table);
    void saveCurrentFood(FoodModel foodModel);
    void checkIfFoodIsSaved(String id);
    void deleteSavedFood(String id);
    void searchForImage(String foodTitle);

}
