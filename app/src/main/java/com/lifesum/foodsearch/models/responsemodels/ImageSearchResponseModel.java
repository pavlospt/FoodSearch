package com.lifesum.foodsearch.models.responsemodels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.lifesum.foodsearch.models.ImageItemModel;
import com.lifesum.foodsearch.models.JsonProperties;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 * Class that represents the base response from the Google Custom Search API Endpoint.
 */
@JsonObject
public class ImageSearchResponseModel {

    @JsonField(name = JsonProperties.GIS_ITEMS)
    private ArrayList<ImageItemModel> imageItems;

    public ImageSearchResponseModel() {
    }

    public ImageSearchResponseModel(ArrayList<ImageItemModel> imageItems) {
        this.imageItems = imageItems;
    }

    public ArrayList<ImageItemModel> getImageItems() {
        return imageItems;
    }

    public void setImageItems(ArrayList<ImageItemModel> imageItems) {
        this.imageItems = imageItems;
    }
}
