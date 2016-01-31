package com.lifesum.foodsearch.presenters;

import com.lifesum.foodsearch.interactors.FoodInteractorImpl;
import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.presenters.interfaces.IMainPresenter;
import com.lifesum.foodsearch.views.IMainView;

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
 * The implementation of the IMainPresenter.
 * Handles the interaction between the MainPresenterImpl and our IMainView
 * by acting as the Controller on an MVC architecture, or the presenter in the MVP.
 */
public class MainPresenterImpl implements IMainPresenter {


    private WeakReference<IMainView> mainView;
    private FoodInteractorImpl foodInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public MainPresenterImpl(FoodInteractorImpl foodInteractor) {
        this.foodInteractor = foodInteractor;
        this.compositeSubscription = new CompositeSubscription();
    }

    /*
    * Searching for food on the API Endpoint
    * */
    @Override
    public void searchFood(String searchTerm) {
        checkCompositeSubscription();
        compositeSubscription.add(
                foodInteractor
                        .searchFood("en","en",searchTerm)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseResponseModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.e("Error on food search:%s", e.getMessage());
                            }

                            @Override
                            public void onNext(BaseResponseModel baseResponseModel) {
                                Timber.e("Models received:%s",baseResponseModel.toString());
                                if(doIfView())
                                    mainView.get().presentFoodSearch(baseResponseModel);
                            }
                        })
        );
    }

    /*
    * Caching foods in order to present a non empty RecyclerView state when the app loads.
    * */
    @Override
    public void cacheFoods(ArrayList<FoodModel> cachedFoods) {
        this.foodInteractor
                .cacheFoods(cachedFoods);
    }

    /*
    * Loading our cached foods results.
    * */
    @Override
    public void loadCachedFoods() {
        this.foodInteractor
                .retrieveCachedFoods()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FoodModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error on retrieving cached foods:%s", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FoodModel> foodModels) {
                        Timber.e("Retrieved cached foods:%s",foodModels.toString());
                        if(doIfView()){
                            if(foodModels.size() > 0){
                                mainView.get().presentCachedFoods(prepareListToReturn(foodModels));
                            }
                        }
                    }
                });
    }

    @Override
    public void attachView(IMainView view) {
        this.mainView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.mainView.clear();
        if(!this.compositeSubscription.isUnsubscribed())
            this.compositeSubscription.unsubscribe();
    }

    /*
    * Check if the WeakReference of our View is not null in order to avoid NullPointerExceptions.
    * */
    private boolean doIfView() {
        return this.mainView != null && this.mainView.get() != null;
    }

    /*
    * Making sure that our CompositeSubscription has not been unsubscribed.
    * */
    private void checkCompositeSubscription() {
        if(this.compositeSubscription == null || this.compositeSubscription.isUnsubscribed())
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
