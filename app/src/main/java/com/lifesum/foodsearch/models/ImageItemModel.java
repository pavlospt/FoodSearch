package com.lifesum.foodsearch.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by PavlosPT13.
 * Class that represents an Item from the results of the Google Custom Search API.
 * We are only interested on the link, that contains an image based on our Food.
 */
@JsonObject
public class ImageItemModel {

    @JsonField(name = JsonProperties.GIS_LINK)
    private String link;

    public ImageItemModel() {
    }

    public ImageItemModel(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
