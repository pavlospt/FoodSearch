package com.lifesum.foodsearch.interactors;

import com.lifesum.foodsearch.databases.resolvers.CachedFoodPutResolver;
import com.lifesum.foodsearch.databases.tables.FoodTable;
import com.lifesum.foodsearch.interactors.interfaces.IFoodInteractor;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;
import com.lifesum.foodsearch.models.responsemodels.ImageSearchResponseModel;
import com.lifesum.foodsearch.network.FoodSearchService;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by PavlosPT13.
 * Class that provides the IFoodInteractor implementation.
 */
public class FoodInteractorImpl implements IFoodInteractor {

    private Retrofit retrofit, retrofitImageSearch;
    private StorIOSQLite storIOSQLite;

    /*
    * Our constructor injects its dependencies from the Dagger Graph.
    * */
    @Inject
    public FoodInteractorImpl(@Named("lifesum") Retrofit retrofit,
                              @Named("googleImageSearch") Retrofit retrofitImageSearch,
                              StorIOSQLite storIOSQLite) {
        this.retrofit = retrofit;
        this.retrofitImageSearch = retrofitImageSearch;
        this.storIOSQLite = storIOSQLite;
    }

    /*
    * Searching for Food on the API Endpoint.*/
    @Override
    public Observable<BaseResponseModel> searchFood(String language, String country, String food) {
        return retrofit
                .create(FoodSearchService.class)
                .searchFood(FoodSearchService.AUTH_TOKEN, language, country, food)
                .subscribeOn(Schedulers.io());
    }

    /*
    * Search for Food image on the Google Custom Search API
    * */
    @Override
    public Observable<ImageSearchResponseModel> searchGoogleImage(String query) {
        return retrofitImageSearch.create(FoodSearchService.class)
                .searchGoogleImage(
                        FoodSearchService.GOOGLE_API_KEY,
                        FoodSearchService.GOOGLE_API_CX,
                        "medium",
                        "image",
                        query.replaceAll(" ", "%20")
                )
                .subscribeOn(Schedulers.io());
    }

    /*
    * Getting the list of contents from our SavedFoods table.
    * */
    @Override
    public Observable<List<FoodModel>> retrieveSavedFoods() {
        return storIOSQLite
                .get()
                .listOfObjects(FoodModel.class)
                .withQuery(Query.builder()
                        .table(FoodTable.TABLE_SAVED_FOODS)
                        .build())
                .prepare()
                .asRxObservable();
    }

    /*
    * Getting the list of contents from our CachedFoods table.
    * */
    @Override
    public Observable<List<FoodModel>> retrieveCachedFoods() {
        return storIOSQLite
                .get()
                .listOfObjects(FoodModel.class)
                .withQuery(
                        Query.builder()
                                .table(FoodTable.TABLE_CACHED_FOODS)
                                .build()
                )
                .prepare()
                .asRxObservable();
    }

    /*
    * Caching the list of passed FoodModels.
    * */
    @Override
    public void cacheFoods(List<FoodModel> foods) {
        storIOSQLite
                .delete()
                .byQuery(
                        DeleteQuery
                                .builder()
                                .table(FoodTable.TABLE_CACHED_FOODS)
                                .build()
                )
                .prepare()
                .executeAsBlocking();
        storIOSQLite
                .put()
                .objects(foods)
                .withPutResolver(new CachedFoodPutResolver())
                .prepare()
                .executeAsBlocking();
    }

    /*
    * Saving the passed FoodModel on our SavedFoods table.
    * */
    @Override
    public Observable<PutResult> saveFoodModel(FoodModel foodModel) {
        return storIOSQLite
                .put()
                .object(foodModel)
                .prepare()
                .asRxObservable();
    }

    /*
    * Checking if we have previously saved a FoodModel.
    * */
    @Override
    public Observable<FoodModel> checkForSavedFood(String id) {
        return storIOSQLite
                .get()
                .object(FoodModel.class)
                .withQuery(
                        Query.builder()
                                .table(FoodTable.TABLE_SAVED_FOODS)
                                .where(FoodTable.COLUMN_REMOTE_ID + " =?")
                                .whereArgs(id)
                                .build()
                )
                .prepare()
                .asRxObservable();
    }

    /*
    * Deleting a Food from our SavedFoods table.
    * */
    @Override
    public Observable<DeleteResult> deleteSavedFood(String id) {
        return storIOSQLite
                .delete()
                .byQuery(
                        DeleteQuery.builder()
                                .table(FoodTable.TABLE_SAVED_FOODS)
                                .where(FoodTable.COLUMN_REMOTE_ID + " =?")
                                .whereArgs(id)
                                .build()
                )
                .prepare()
                .asRxObservable();
    }

    /*
    * Searching on our SavedFoods table to find foods with name matching the term.
    * */
    @Override
    public Observable<List<FoodModel>> searchSavedFood(String term) {
        return storIOSQLite
                .get()
                .listOfObjects(FoodModel.class)
                .withQuery(
                        Query
                                .builder()
                                .table(FoodTable.TABLE_SAVED_FOODS)
                                .where(FoodTable.COLUMN_TITLE + " like '" + term + "%'")
                                .build()
                )
                .prepare()
                .asRxObservable();
    }

    /*
    * Loading a Food from the selected table, by id.
    * */
    @Override
    public Observable<FoodModel> loadSelectedFood(String id, String table) {
        return storIOSQLite
                .get()
                .object(FoodModel.class)
                .withQuery(
                        Query
                                .builder()
                                .table(table)
                                .where(FoodTable.COLUMN_REMOTE_ID + " =?")
                                .whereArgs(id)
                                .build()
                )
                .prepare()
                .asRxObservable();
    }
}
