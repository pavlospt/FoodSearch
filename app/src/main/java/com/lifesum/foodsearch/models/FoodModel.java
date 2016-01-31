package com.lifesum.foodsearch.models;

import android.content.ContentValues;
import android.support.v4.util.Pair;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.lifesum.foodsearch.databases.tables.FoodTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import org.parceler.Parcel;
import org.parceler.apache.commons.lang.WordUtils;

import java.util.ArrayList;

/**
 * Created by PavlosPT13.
 * Class that represents our FoodModel. Class is either constructed by JSON parsing,
 * or SQLite mapping from our Database. This class is also parcelable, so that we can pass
 * it to our FoodDetails Activity if needed.
 */
@Parcel
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
@StorIOSQLiteType(table = FoodTable.TABLE_SAVED_FOODS)
public class FoodModel {

    public static final String FOOD_MODEL = "FoodModel";
    public static final String FOOD_MODEL_SELECTED_ID = "selectedId";

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_ID, key = true)
    Long primaryId;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_CATEGORY_ID)
    @JsonField(name = JsonProperties.F_CATEGORY_ID)
    public int categoryId;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_HEAD_CATEGORY_ID)
    @JsonField(name = JsonProperties.F_HEAD_CATEGORY_ID)
    public int headCategoryId;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SHOW_ONLY_SAME_TYPE)
    @JsonField(name = JsonProperties.F_SHOW_ONLY_SAME_TYPE)
    public int showOnlySameType;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_FIBER)
    @JsonField(name = JsonProperties.F_FIBER)
    public double fiber;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_REMOTE_ID)
    @JsonField(name = JsonProperties.F_ID)
    public int id;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_PROTEIN)
    @JsonField(name = JsonProperties.F_PROTEIN)
    public double protein;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_UNSATURATED_FAT)
    @JsonField(name = JsonProperties.F_UNSATURATED_FAT)
    public double unsaturatedFat;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SATURATED_FAT)
    @JsonField(name = JsonProperties.F_SATURATED_FAT)
    public double saturatedFat;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_CATEGORY)
    @JsonField(name = JsonProperties.F_CATEGORY)
    public String category;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_VERIFIED)
    @JsonField(name = JsonProperties.F_VERIFIED)
    public boolean verified;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SODIUM)
    @JsonField(name = JsonProperties.F_SODIUM)
    public int sodium;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_CARBO_HYDRATES)
    @JsonField(name = JsonProperties.F_CARBOHYDRATES)
    public double carboHydrates;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_MLINGRAM)
    @JsonField(name = JsonProperties.F_MLINGRAM)
    public double mlingram;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SUGAR)
    @JsonField(name = JsonProperties.F_SUGAR)
    public double sugar;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SOURCE)
    @JsonField(name = JsonProperties.F_SOURCE)
    public int source;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_MEASUREMENT_ID)
    @JsonField(name = JsonProperties.F_MEASUREMENT_ID)
    public int measurementId;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_BRAND)
    @JsonField(name = JsonProperties.F_BRAND)
    public String brand;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SERVING_CATEGORY)
    @JsonField(name = JsonProperties.F_SERVING_CATEGORY)
    public int servingCategory;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_PCS_INGRAM)
    @JsonField(name = JsonProperties.F_PCS_INGRAM)
    public int pcsIngram;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_POTASSIUM)
    @JsonField(name = JsonProperties.F_POTASSIUM)
    public double potassium;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_FAT)
    @JsonField(name = JsonProperties.F_FAT)
    public double fat;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_TYPE_OF_MEASUREMENT)
    @JsonField(name = JsonProperties.F_TYPE_OF_MEASUREMENT)
    public int typeOfMeasurement;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_DEFAULT_SERVING)
    @JsonField(name = JsonProperties.F_DEFAULT_SERVING)
    public int defaultServing;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_PCS_TEXT)
    @JsonField(name = JsonProperties.F_PCS_TEXT)
    public String pcsText;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_TITLE)
    @JsonField(name = JsonProperties.F_TITLE)
    public String title;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_CALORIES)
    @JsonField(name = JsonProperties.F_CALORIES)
    public int calories;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_CHOLESTEROL)
    @JsonField(name = JsonProperties.F_CHOLESTEROL)
    public int cholesterol;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_GRAMS_PER_SERVING)
    @JsonField(name = JsonProperties.F_GRAMS_PER_SERVING)
    public int gramsPerServing;

    @StorIOSQLiteColumn(name = FoodTable.COLUMN_SHOW_MEASUREMENT)
    @JsonField(name = JsonProperties.F_SHOW_MEASUREMENT)
    public int showMeasurement;

    public FoodModel() {
    }

    public FoodModel(int categoryId, int headCategoryId, int showOnlySameType, double fiber,
                     int id, double protein, double unsaturatedFat, double saturatedFat,
                     String category, boolean verified, int sodium, double carboHydrates,
                     double mlingram, double sugar, int source, int measurementId,
                     String brand, int servingCategory, int pcsIngram, double potassium,
                     double fat, int typeOfMeasurement, int defaultServing, String pcsText,
                     String title, int calories, int cholesterol, int gramsPerServing,
                     int showMeasurement) {
        this.categoryId = categoryId;
        this.headCategoryId = headCategoryId;
        this.showOnlySameType = showOnlySameType;
        this.fiber = fiber;
        this.id = id;
        this.protein = protein;
        this.unsaturatedFat = unsaturatedFat;
        this.saturatedFat = saturatedFat;
        this.category = category;
        this.verified = verified;
        this.sodium = sodium;
        this.carboHydrates = carboHydrates;
        this.mlingram = mlingram;
        this.sugar = sugar;
        this.source = source;
        this.measurementId = measurementId;
        this.brand = brand;
        this.servingCategory = servingCategory;
        this.pcsIngram = pcsIngram;
        this.potassium = potassium;
        this.fat = fat;
        this.typeOfMeasurement = typeOfMeasurement;
        this.defaultServing = defaultServing;
        this.pcsText = pcsText;
        this.title = title;
        this.calories = calories;
        this.cholesterol = cholesterol;
        this.gramsPerServing = gramsPerServing;
        this.showMeasurement = showMeasurement;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getHeadCategoryId() {
        return headCategoryId;
    }

    public void setHeadCategoryId(int headCategoryId) {
        this.headCategoryId = headCategoryId;
    }

    public int getShowOnlySameType() {
        return showOnlySameType;
    }

    public void setShowOnlySameType(int showOnlySameType) {
        this.showOnlySameType = showOnlySameType;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getUnsaturatedFat() {
        return unsaturatedFat;
    }

    public void setUnsaturatedFat(double unsaturatedFat) {
        this.unsaturatedFat = unsaturatedFat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public double getCarboHydrates() {
        return carboHydrates;
    }

    public void setCarboHydrates(double carboHydrates) {
        this.carboHydrates = carboHydrates;
    }

    public double getMlingram() {
        return mlingram;
    }

    public void setMlingram(double mlingram) {
        this.mlingram = mlingram;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(int measurementId) {
        this.measurementId = measurementId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getServingCategory() {
        return servingCategory;
    }

    public void setServingCategory(int servingCategory) {
        this.servingCategory = servingCategory;
    }

    public int getPcsIngram() {
        return pcsIngram;
    }

    public void setPcsIngram(int pcsIngram) {
        this.pcsIngram = pcsIngram;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public int getTypeOfMeasurement() {
        return typeOfMeasurement;
    }

    public void setTypeOfMeasurement(int typeOfMeasurement) {
        this.typeOfMeasurement = typeOfMeasurement;
    }

    public int getDefaultServing() {
        return defaultServing;
    }

    public void setDefaultServing(int defaultServing) {
        this.defaultServing = defaultServing;
    }

    public String getPcsText() {
        return pcsText;
    }

    public void setPcsText(String pcsText) {
        this.pcsText = pcsText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getGramsPerServing() {
        return gramsPerServing;
    }

    public void setGramsPerServing(int gramsPerServing) {
        this.gramsPerServing = gramsPerServing;
    }

    public int getShowMeasurement() {
        return showMeasurement;
    }

    public void setShowMeasurement(int showMeasurement) {
        this.showMeasurement = showMeasurement;
    }

    /*
    * Representing the current FoodModel as ContentValues, in order to save it on the Database.
    * */
    public ContentValues asContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodTable.COLUMN_CATEGORY_ID, this.categoryId);
        contentValues.put(FoodTable.COLUMN_HEAD_CATEGORY_ID, this.headCategoryId);
        contentValues.put(FoodTable.COLUMN_SHOW_ONLY_SAME_TYPE, this.showOnlySameType);
        contentValues.put(FoodTable.COLUMN_FIBER, this.fiber);
        contentValues.put(FoodTable.COLUMN_REMOTE_ID, this.id);
        contentValues.put(FoodTable.COLUMN_PROTEIN, this.protein);
        contentValues.put(FoodTable.COLUMN_UNSATURATED_FAT, this.unsaturatedFat);
        contentValues.put(FoodTable.COLUMN_SATURATED_FAT, this.saturatedFat);
        contentValues.put(FoodTable.COLUMN_CATEGORY, this.category);
        contentValues.put(FoodTable.COLUMN_VERIFIED, this.verified);
        contentValues.put(FoodTable.COLUMN_SODIUM, this.sodium);
        contentValues.put(FoodTable.COLUMN_CARBO_HYDRATES, this.carboHydrates);
        contentValues.put(FoodTable.COLUMN_MLINGRAM, this.mlingram);
        contentValues.put(FoodTable.COLUMN_SUGAR, this.sugar);
        contentValues.put(FoodTable.COLUMN_SOURCE, this.source);
        contentValues.put(FoodTable.COLUMN_MEASUREMENT_ID, this.measurementId);
        contentValues.put(FoodTable.COLUMN_BRAND, this.brand);
        contentValues.put(FoodTable.COLUMN_SERVING_CATEGORY, this.servingCategory);
        contentValues.put(FoodTable.COLUMN_PCS_INGRAM, this.pcsIngram);
        contentValues.put(FoodTable.COLUMN_POTASSIUM, this.potassium);
        contentValues.put(FoodTable.COLUMN_FAT, this.fat);
        contentValues.put(FoodTable.COLUMN_TYPE_OF_MEASUREMENT, this.typeOfMeasurement);
        contentValues.put(FoodTable.COLUMN_DEFAULT_SERVING, this.defaultServing);
        contentValues.put(FoodTable.COLUMN_PCS_TEXT, this.pcsText);
        contentValues.put(FoodTable.COLUMN_TITLE, this.title);
        contentValues.put(FoodTable.COLUMN_CALORIES, this.calories);
        contentValues.put(FoodTable.COLUMN_CHOLESTEROL, this.cholesterol);
        contentValues.put(FoodTable.COLUMN_GRAMS_PER_SERVING, this.gramsPerServing);
        contentValues.put(FoodTable.COLUMN_SHOW_MEASUREMENT, this.showMeasurement);
        return contentValues;
    }

    /*
    * Getting key Food properties to present in the FoodDetails Screen,
    * as a list of keys and values.
    * */
    public ArrayList<Pair<String, String>> asPairs() {
        ArrayList<Pair<String, String>> toReturn = new ArrayList<>();
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_FIBER.replaceAll("_", " ")), String.valueOf(this.fiber)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_PROTEIN.replaceAll("_", " ")), String.valueOf(this.protein)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_UNSATURATED_FAT.replaceAll("_", " ")), String.valueOf(this.unsaturatedFat)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_SATURATED_FAT.replaceAll("_", " ")), String.valueOf(this.saturatedFat)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_SODIUM.replaceAll("_", " ")), String.valueOf(this.sodium)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_CARBO_HYDRATES.replaceAll("_", " ")), String.valueOf(this.carboHydrates)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_MLINGRAM.replaceAll("_", " ")), String.valueOf(this.mlingram)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_SUGAR.replaceAll("_", " ")), String.valueOf(this.sugar)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_PCS_INGRAM.replaceAll("_", " ")), String.valueOf(this.pcsIngram)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_POTASSIUM.replaceAll("_", " ")), String.valueOf(this.potassium)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_FAT.replaceAll("_", " ")), String.valueOf(this.fat)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_PCS_TEXT.replaceAll("_", " ")), this.pcsText));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_CALORIES.replaceAll("_", " ")), String.valueOf(this.calories)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_CHOLESTEROL.replaceAll("_", " ")), String.valueOf(this.cholesterol)));
        toReturn.add(new Pair<>(WordUtils.capitalize(FoodTable.COLUMN_GRAMS_PER_SERVING.replaceAll("_", " ")), String.valueOf(this.gramsPerServing)));
        return toReturn;
    }

}
