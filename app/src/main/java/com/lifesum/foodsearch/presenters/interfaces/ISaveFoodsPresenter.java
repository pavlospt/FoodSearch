package com.lifesum.foodsearch.presenters.interfaces;

import com.lifesum.foodsearch.views.ISavedFoodsView;

/**
 * Created by PavlosPT13.
 */
public interface ISaveFoodsPresenter extends IPresenter<ISavedFoodsView> {

    void loadSavedFoods();
    void searchSavedFood(String term);

}
