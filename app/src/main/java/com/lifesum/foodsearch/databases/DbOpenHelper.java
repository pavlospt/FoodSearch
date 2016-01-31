package com.lifesum.foodsearch.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.lifesum.foodsearch.databases.tables.FoodTable;

/**
 * Created by PavlosPT13.
 * Our DatabaseHelper. Responsible only for creating the SavedFoods and CachedFoods table.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lifesum_db";

    public DbOpenHelper(@NonNull Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FoodTable.createSavedFoodsQuery());
        sqLiteDatabase.execSQL(FoodTable.createCachedFoodsQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //No need for an implementation here
    }
}
