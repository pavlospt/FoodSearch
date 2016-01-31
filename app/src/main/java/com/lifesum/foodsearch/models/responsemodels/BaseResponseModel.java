package com.lifesum.foodsearch.models.responsemodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.JsonProperties;
import com.lifesum.foodsearch.models.ServingCategoryModel;
import com.lifesum.foodsearch.models.ServingSizeModel;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 * Class that represents the Response of the LifeSum API Endpoint for Food Search.
 */
@JsonObject
public class BaseResponseModel {

    @JsonField(name = JsonProperties.ROOT_FOOD)
    private ArrayList<FoodModel> foodModels;

    @JsonField(name = JsonProperties.ROOT_LANGUAGE_REQUESTED)
    private String languageRequested;

    @JsonField(name = JsonProperties.ROOT_SERVING_CATEGORIES)
    private ArrayList<ServingCategoryModel> servingCategoryModels;

    @JsonField(name = JsonProperties.ROOT_SERVING_SIZES)
    private ArrayList<ServingSizeModel> servingSizeModels;

    @JsonField(name = JsonProperties.ROOT_TITLE_COMPLETED)
    private String titleCompleted;

    @JsonField(name = JsonProperties.ROOT_TITLE_REQUESTED)
    private String titleRequested;

    public BaseResponseModel() {
    }

    public BaseResponseModel(ArrayList<FoodModel> foodModels, String languageRequested,
                             ArrayList<ServingCategoryModel> servingCategoryModels,
                             ArrayList<ServingSizeModel> servingSizeModels, String titleCompleted,
                             String titleRequested) {
        this.foodModels = foodModels;
        this.languageRequested = languageRequested;
        this.servingCategoryModels = servingCategoryModels;
        this.servingSizeModels = servingSizeModels;
        this.titleCompleted = titleCompleted;
        this.titleRequested = titleRequested;
    }

    public ArrayList<FoodModel> getFoodModels() {
        return foodModels;
    }

    public void setFoodModels(ArrayList<FoodModel> foodModels) {
        this.foodModels = foodModels;
    }

    public String getLanguageRequested() {
        return languageRequested;
    }

    public void setLanguageRequested(String languageRequested) {
        this.languageRequested = languageRequested;
    }

    public ArrayList<ServingCategoryModel> getServingCategoryModels() {
        return servingCategoryModels;
    }

    public void setServingCategoryModels(ArrayList<ServingCategoryModel> servingCategoryModels) {
        this.servingCategoryModels = servingCategoryModels;
    }

    public ArrayList<ServingSizeModel> getServingSizeModels() {
        return servingSizeModels;
    }

    public void setServingSizeModels(ArrayList<ServingSizeModel> servingSizeModels) {
        this.servingSizeModels = servingSizeModels;
    }

    public String getTitleCompleted() {
        return titleCompleted;
    }

    public void setTitleCompleted(String titleCompleted) {
        this.titleCompleted = titleCompleted;
    }

    public String getTitleRequested() {
        return titleRequested;
    }

    public void setTitleRequested(String titleRequested) {
        this.titleRequested = titleRequested;
    }
}
