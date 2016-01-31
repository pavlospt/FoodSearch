package com.lifesum.foodsearch.utils;

import com.lifesum.foodsearch.models.FoodModel;

/**
 * Created by PavlosPT13.
 * Class that contains the Events used from our EventBus.
 */
public class EventsProvider {

    public static class FoodSelectedEvent {
        private String foodId;
        private FoodModel foodModel;

        public FoodSelectedEvent(String foodId, FoodModel foodModel) {
            this.foodId = foodId;
            this.foodModel = foodModel;
        }

        public String getFoodId() {
            return foodId;
        }

        public FoodModel getFoodModel() {
            return foodModel;
        }
    }

}
