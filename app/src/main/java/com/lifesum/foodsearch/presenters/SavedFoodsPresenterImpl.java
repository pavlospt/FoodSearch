package com.lifesum.foodsearch.presenters;

import com.lifesum.foodsearch.interactors.FoodInteractorImpl;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.presenters.interfaces.ISaveFoodsPresenter;
import com.lifesum.foodsearch.views.IMainView;
import com.lifesum.foodsearch.views.ISavedFoodsView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by PavlosPT13.
 * The implementation of the SavedFoodPresenter.
 * Handles the interaction between the FoodInteractorImpl and our ISavedFoodsView
 * by acting as the Controller on an MVC architecture, or the presenter in the MVP.
 */
public class SavedFoodsPresenterImpl implements ISaveFoodsPresenter {

    private WeakReference<ISavedFoodsView> savedFoodsView;
    private FoodInteractorImpl foodInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public SavedFoodsPresenterImpl(FoodInteractorImpl foodInteractor) {
        this.foodInteractor = foodInteractor;
    }

    @Override
    public void attachView(ISavedFoodsView view) {
        this.savedFoodsView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.savedFoodsView.clear();
        if(!this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription.unsubscribe();
    }

    /*
    * Loading the contents of the SavedFood table.
    * */
    @Override
    public void loadSavedFoods() {
        checkCompositeSubscription();
        compositeSubscription.add(
                this.foodInteractor
                        .retrieveSavedFoods()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FoodModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error getting saved foods:%s", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FoodModel> foodModels) {
                        Timber.e("Loaded all saved foods:%s",foodModels.toString());
                        if(foodModels != null){
                            if(doIfView()){
                                //The received List with object is unmodifiable
                                //so we can not cast it to an ArrayList. For this case
                                //we create a temp ArrayList and fill it with the List values.
                                savedFoodsView.get().presentSavedFoods(prepareListToReturn(foodModels));
                            }
                        }
                    }
                })
        );
    }

    /*
    * Searching on the contents of our SavedFoods table.
    * */
    @Override
    public void searchSavedFood(String term) {
        checkCompositeSubscription();
        compositeSubscription.add(
                this.foodInteractor
                    .searchSavedFood(term)
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FoodModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error searching for saved food:%s", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FoodModel> foodModels) {
                        Timber.e("Saved food search result:%s", foodModels.toString());
                        if(foodModels != null){
                            if(doIfView()){
                                savedFoodsView.get().presentSearchedFoods(prepareListToReturn(foodModels));
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
        return this.savedFoodsView != null && this.savedFoodsView.get() != null;
    }

    /*
    * Making sure that our CompositeSubscription has not been unsubscribed.
    * */
    private void checkCompositeSubscription() {
        if (this.compositeSubscription == null || this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription = new CompositeSubscription();
    }

    /** The received List with object is unmodifiable
     * so we can not cast it to an ArrayList. For this case
     * we create a temp ArrayList and fill it with the List values.
     * */
    private ArrayList<FoodModel> prepareListToReturn(List<FoodModel> passedFoods) {
        ArrayList<FoodModel> toReturn = new ArrayList<>(passedFoods.size());
        toReturn.addAll(passedFoods);
        return toReturn;
    }
}
