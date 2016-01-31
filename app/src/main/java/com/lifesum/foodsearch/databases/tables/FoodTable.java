package com.lifesum.foodsearch.databases.tables;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by PavlosPT13.
 * Class containing MetaData and Queries that create our SavedFoods and CachedFoods tables.
 */
public class FoodTable {

    @NonNull
    public static final String TABLE_SAVED_FOODS = "SavedFoods";

    @NonNull
    public static final String TABLE_CACHED_FOODS = "CachedFoods";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_CATEGORY_ID = "category_id";

    @NonNull
    public static final String COLUMN_HEAD_CATEGORY_ID = "head_category_id";

    @NonNull
    public static final String COLUMN_SHOW_ONLY_SAME_TYPE = "show_only_same_type";

    @NonNull
    public static final String COLUMN_FIBER = "fiber";

    @NonNull
    public static final String COLUMN_REMOTE_ID = "id";

    @NonNull
    public static final String COLUMN_PROTEIN = "protein";

    @NonNull
    public static final String COLUMN_UNSATURATED_FAT = "unsaturated_fat";

    @NonNull
    public static final String COLUMN_SATURATED_FAT = "saturated_fat";

    @NonNull
    public static final String COLUMN_CATEGORY = "category";

    @NonNull
    public static final String COLUMN_VERIFIED = "verified";

    @NonNull
    public static final String COLUMN_SODIUM = "sodium";

    @NonNull
    public static final String COLUMN_CARBO_HYDRATES = "carbo_hydrates";

    @NonNull
    public static final String COLUMN_MLINGRAM = "mlingram";

    @NonNull
    public static final String COLUMN_SUGAR = "sugar";

    @NonNull
    public static final String COLUMN_SOURCE = "source";

    @NonNull
    public static final String COLUMN_MEASUREMENT_ID = "measurement_id";

    @NonNull
    public static final String COLUMN_BRAND = "brand";

    @NonNull
    public static final String COLUMN_SERVING_CATEGORY = "serving_category";

    @NonNull
    public static final String COLUMN_PCS_INGRAM = "pcs_ingram";

    @NonNull
    public static final String COLUMN_POTASSIUM = "potassium";

    @NonNull
    public static final String COLUMN_FAT = "fat";

    @NonNull
    public static final String COLUMN_TYPE_OF_MEASUREMENT = "type_of_measurement";

    @NonNull
    public static final String COLUMN_DEFAULT_SERVING = "default_serving";

    @NonNull
    public static final String COLUMN_PCS_TEXT = "pcs_text";

    @NonNull
    public static final String COLUMN_TITLE = "title";

    @NonNull
    public static final String COLUMN_CALORIES = "calories";

    @NonNull
    public static final String COLUMN_CHOLESTEROL = "cholesterol";

    @NonNull
    public static final String COLUMN_GRAMS_PER_SERVING = "grams_per_serving";

    @NonNull
    public static final String COLUMN_SHOW_MEASUREMENT = "show_measurement";

    @NonNull
    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE_SAVED_FOODS)
            .build();

    @NonNull
    public static String createSavedFoodsQuery() {
        return "CREATE TABLE " + TABLE_SAVED_FOODS + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_CATEGORY_ID + " TEXT NULL, "
                + COLUMN_HEAD_CATEGORY_ID + " TEXT NULL, "
                + COLUMN_SHOW_ONLY_SAME_TYPE + " TEXT NULL, "
                + COLUMN_FIBER + " TEXT NULL, "
                + COLUMN_REMOTE_ID + " TEXT NULL, "
                + COLUMN_PROTEIN + " TEXT NULL, "
                + COLUMN_UNSATURATED_FAT + " TEXT  NULL, "
                + COLUMN_SATURATED_FAT + " TEXT NULL, "
                + COLUMN_CATEGORY + " TEXT NULL, "
                + COLUMN_VERIFIED + " TEXT NULL, "
                + COLUMN_SODIUM + " TEXT NULL, "
                + COLUMN_CARBO_HYDRATES + " TEXT NULL, "
                + COLUMN_MLINGRAM + " TEXT NULL, "
                + COLUMN_SUGAR + " TEXT NULL, "
                + COLUMN_SOURCE + " TEXT NULL, "
                + COLUMN_MEASUREMENT_ID + " TEXT NULL, "
                + COLUMN_BRAND + " TEXT NULL, "
                + COLUMN_SERVING_CATEGORY + " TEXT NULL, "
                + COLUMN_PCS_INGRAM + " TEXT NULL, "
                + COLUMN_POTASSIUM + " TEXT NULL, "
                + COLUMN_FAT + " TEXT NULL, "
                + COLUMN_TYPE_OF_MEASUREMENT + " TEXT NULL, "
                + COLUMN_DEFAULT_SERVING + " TEXT NULL, "
                + COLUMN_PCS_TEXT + " TEXT NULL, "
                + COLUMN_TITLE + " TEXT NULL, "
                + COLUMN_CALORIES + " TEXT NULL, "
                + COLUMN_CHOLESTEROL + " TEXT NULL, "
                + COLUMN_GRAMS_PER_SERVING + " TEXT NULL, "
                + COLUMN_SHOW_MEASUREMENT + " TEXT NULL"
                + ");";
    }

    @NonNull
    public static String createCachedFoodsQuery() {
        return "CREATE TABLE " + TABLE_CACHED_FOODS + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_CATEGORY_ID + " TEXT NULL, "
                + COLUMN_HEAD_CATEGORY_ID + " TEXT NULL, "
                + COLUMN_SHOW_ONLY_SAME_TYPE + " TEXT NULL, "
                + COLUMN_FIBER + " TEXT NULL, "
                + COLUMN_REMOTE_ID + " TEXT NULL, "
                + COLUMN_PROTEIN + " TEXT NULL, "
                + COLUMN_UNSATURATED_FAT + " TEXT  NULL, "
                + COLUMN_SATURATED_FAT + " TEXT NULL, "
                + COLUMN_CATEGORY + " TEXT NULL, "
                + COLUMN_VERIFIED + " TEXT NULL, "
                + COLUMN_SODIUM + " TEXT NULL, "
                + COLUMN_CARBO_HYDRATES + " TEXT NULL, "
                + COLUMN_MLINGRAM + " TEXT NULL, "
                + COLUMN_SUGAR + " TEXT NULL, "
                + COLUMN_SOURCE + " TEXT NULL, "
                + COLUMN_MEASUREMENT_ID + " TEXT NULL, "
                + COLUMN_BRAND + " TEXT NULL, "
                + COLUMN_SERVING_CATEGORY + " TEXT NULL, "
                + COLUMN_PCS_INGRAM + " TEXT NULL, "
                + COLUMN_POTASSIUM + " TEXT NULL, "
                + COLUMN_FAT + " TEXT NULL, "
                + COLUMN_TYPE_OF_MEASUREMENT + " TEXT NULL, "
                + COLUMN_DEFAULT_SERVING + " TEXT NULL, "
                + COLUMN_PCS_TEXT + " TEXT NULL, "
                + COLUMN_TITLE + " TEXT NULL, "
                + COLUMN_CALORIES + " TEXT NULL, "
                + COLUMN_CHOLESTEROL + " TEXT NULL, "
                + COLUMN_GRAMS_PER_SERVING + " TEXT NULL, "
                + COLUMN_SHOW_MEASUREMENT + " TEXT NULL"
                + ");";
    }

    @NonNull
    public static String clearCachedFoods() {
        return "DELETE * FROM ?";
    }

}
