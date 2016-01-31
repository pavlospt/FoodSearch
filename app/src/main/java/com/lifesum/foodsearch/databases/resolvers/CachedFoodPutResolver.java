package com.lifesum.foodsearch.databases.resolvers;

import android.support.annotation.NonNull;

import com.lifesum.foodsearch.databases.tables.FoodTable;
import com.lifesum.foodsearch.models.FoodModel;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;

/**
 * Created by PavlosPT13.
 * Custom PutResolver for StorIO, in order to insert our FoodModel on our SavedFoods table.
 */
public class CachedFoodPutResolver extends PutResolver<FoodModel> {

    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull FoodModel object) {
        storIOSQLite.internal().beginTransaction();

        try {
            storIOSQLite.internal().insert(
                    InsertQuery
                            .builder()
                            .table(FoodTable.TABLE_CACHED_FOODS)
                            .build(),
                    object.asContentValues());
            storIOSQLite.internal().setTransactionSuccessful();
        } finally {
            storIOSQLite.internal().endTransaction();
        }
        return PutResult.newUpdateResult(1, FoodTable.TABLE_CACHED_FOODS);
    }
}
