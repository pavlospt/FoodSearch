package com.lifesum.foodsearch.presenters;

import com.lifesum.foodsearch.interactors.FoodInteractorImpl;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.responsemodels.ImageSearchResponseModel;
import com.lifesum.foodsearch.presenters.interfaces.IFoodDetailsPresenter;
import com.lifesum.foodsearch.views.IFoodDetailsView;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by PavlosPT13.
 * The implementation of the IFoodDetailsPresenter.
 * Handles the interaction between the FoodDetailsPresenterImpl and our IFoodDetailsView
 * by acting as the Controller on an MVC architecture, or the presenter in the MVP.
 */
public class FoodDetailsPresenterImpl implements IFoodDetailsPresenter {

    private WeakReference<IFoodDetailsView> foodDetailsView;
    private FoodInteractorImpl foodInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public FoodDetailsPresenterImpl(FoodInteractorImpl foodInteractor) {
        this.foodInteractor = foodInteractor;
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(IFoodDetailsView view) {
        this.foodDetailsView = new WeakReference<>(view);

    }

    @Override
    public void detachView() {
        this.foodDetailsView.clear();
        if(!this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription.unsubscribe();
    }

    /*
    * Loading the selected food by id and the table chosen
    * */
    @Override
    public void loadSelectedFood(String id, String table) {
        checkCompositeSubscription();
        compositeSubscription.add(
            this.foodInteractor
                    .loadSelectedFood(id, table)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FoodModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Error on getting selected lesson:%s",e.getMessage());
                        }

                        @Override
                        public void onNext(FoodModel foodModel) {
                            if(foodModel != null){
                                if(doIfView())
                                    foodDetailsView.get().presentSelectedFood(foodModel);
                            }
                        }
                    })
        );
    }

    /*
    * Saving the passed FoodModel
    * */
    @Override
    public void saveCurrentFood(final FoodModel initialFoodModel) {
        checkCompositeSubscription();
        compositeSubscription.add(
            this.foodInteractor
                    .saveFoodModel(initialFoodModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PutResult>() {
                        @Override
                        public void onCompleted() {
                            Timber.e("Save food completed.");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Error saving foodModel:%s", e.getMessage());
                            if(doIfView())
                                foodDetailsView.get().showSaveError();
                        }

                        @Override
                        public void onNext(PutResult putResult) {
                            Timber.e("Successfully saved food model:%s", putResult.toString());
                            if(doIfView()){
                                foodDetailsView.get().showSaveSuccess();
                                foodDetailsView.get().setFoodIsSaved();
                            }
                        }
                    })
        );
    }

    /*
    * Checking if the current Food we are viewing is previously saved
    * on our SavedFoods table.
    * */
    @Override
    public void checkIfFoodIsSaved(String id) {
        checkCompositeSubscription();
        compositeSubscription.add(
            this.foodInteractor
                    .checkForSavedFood(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FoodModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Error checking if food is saved already:%s",e.getMessage());
                        }

                        @Override
                        public void onNext(FoodModel foodModel) {
                            if(doIfView()){
                                if(foodModel == null){
                                    foodDetailsView.get().setFoodIsNotSaved();
                                }else{
                                    foodDetailsView.get().setFoodIsSaved();
                                }
                            }
                        }
                    })
        );
    }

    /*
    * Deleting a FoodModel from our SavedFoods table, by id.
    * */
    @Override
    public void deleteSavedFood(String id) {
        checkCompositeSubscription();
        compositeSubscription.add(
            this.foodInteractor
                    .deleteSavedFood(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DeleteResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Error deleting saved food:%s", e.getMessage());
                            if(doIfView()){
                                foodDetailsView.get().showDeletionError();
                            }
                        }

                        @Override
                        public void onNext(DeleteResult deleteResult) {
                            Timber.e("Deleted saved food:%s", deleteResult.toString());
                            if(doIfView()){
                                if(deleteResult.numberOfRowsDeleted() > 0){
                                    foodDetailsView.get().setFoodIsNotSaved();
                                }else{
                                    foodDetailsView.get().setFoodIsSaved();
                                    foodDetailsView.get().showDeletionError();
                                }
                            }
                        }
                    })
        );
    }

    /*
    * Searching the Google Custom Search API, for an image based on our Food's title.
    * */
    @Override
    public void searchForImage(String foodTitle) {
        checkCompositeSubscription();
        compositeSubscription.add(
            this.foodInteractor.searchGoogleImage(foodTitle)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ImageSearchResponseModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Error on image search:%s", e.getMessage());
                        }

                        @Override
                        public void onNext(ImageSearchResponseModel imageSearchResponseModel) {
                            Timber.e("Image search responded with:%s", imageSearchResponseModel.toString());
                            if(doIfView()){
                                if(imageSearchResponseModel.getImageItems() != null
                                        && imageSearchResponseModel.getImageItems().size() > 0){
                                    foodDetailsView.get().showFoodImage(
                                            imageSearchResponseModel.getImageItems().get(0).getLink()
                                    );
                                }
                            }
                        }
                    })
        );
    }

    /*
    * Check if the WeakReference of our View is not null in order to avoid NullPointerExceptions.
    * */
    private boolean doIfView() {
        return this.foodDetailsView != null && this.foodDetailsView.get() != null;
    }

    /*
    * Making sure that our CompositeSubscription has not been unsubscribed.
    * */
    private void checkCompositeSubscription() {
        if(this.compositeSubscription == null || this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription = new CompositeSubscription();
    }
}
