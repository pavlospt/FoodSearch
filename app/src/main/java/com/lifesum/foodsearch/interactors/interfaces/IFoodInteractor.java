package com.lifesum.foodsearch.interactors.interfaces;

import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;
import com.lifesum.foodsearch.models.responsemodels.ImageSearchResponseModel;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import rx.Observable;

/**
 * Created by PavlosPT13.
 */
public interface IFoodInteractor {

    Observable<BaseResponseModel> searchFood(String language, String country, String food);

    Observable<List<FoodModel>> retrieveSavedFoods();

    Observable<List<FoodModel>> retrieveCachedFoods();

    Observable<FoodModel> loadSelectedFood(String id, String table);

    Observable<PutResult> saveFoodModel(FoodModel foodModel);

    Observable<FoodModel> checkForSavedFood(String id);

    Observable<DeleteResult> deleteSavedFood(String id);

    Observable<ImageSearchResponseModel> searchGoogleImage(String query);

    Observable<List<FoodModel>> searchSavedFood(String term);

    void cacheFoods(List<FoodModel> foods);

}
