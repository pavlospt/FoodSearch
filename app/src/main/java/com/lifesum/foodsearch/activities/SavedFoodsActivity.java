package com.lifesum.foodsearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.lifesum.foodsearch.FoodSearchApp;
import com.lifesum.foodsearch.R;
import com.lifesum.foodsearch.adapters.SearchResultsRecyclerAdapter;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.presenters.SavedFoodsPresenterImpl;
import com.lifesum.foodsearch.utils.EventsProvider;
import com.lifesum.foodsearch.views.ISavedFoodsView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;


/**
 * Created by PavlosPT13.
 * Screen containing the list of our SavedFoods table contents.
 */
public class SavedFoodsActivity extends AppCompatActivity implements ISavedFoodsView{

    @Bind(R.id.t_toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_saved_foods)
    RecyclerView savedFoodsRecyclerView;
    @Bind(R.id.et_search_term)
    EditText searchTermEditText;

    @Inject
    SavedFoodsPresenterImpl savedFoodsPresenter;

    private LinearLayoutManager linearLayoutManager;
    private SearchResultsRecyclerAdapter savedFoodsRecyclerAdapter;
    private ArrayList<FoodModel> savedFoods;
    private Subscription searchSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_foods);

        ButterKnife.bind(this);

        this.toolbar.setTitle(getResources().getString(R.string.saved_foods_title));
        this.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FoodSearchApp.from(this).getComponent().inject(this);

        this.savedFoodsPresenter.attachView(this);

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.savedFoodsRecyclerView.setLayoutManager(linearLayoutManager);
        this.savedFoods = new ArrayList<>();
        this.savedFoodsRecyclerAdapter = new SearchResultsRecyclerAdapter(this, this.savedFoods);
        this.savedFoodsRecyclerView.setAdapter(savedFoodsRecyclerAdapter);

        this.savedFoodsPresenter.loadSavedFoods();

        this.searchSubscription = RxTextView
                .textChangeEvents(searchTermEditText)
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        String searchTerm = String.valueOf(textViewTextChangeEvent.text());
                        return searchTerm.length() > 3 || searchTerm.length() == 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchTextChangeObserver());

    }

    /*
    * Observe text changes on our search EditText.
    * Act on those changes based on the value of the current text.
    * If the current text has 0 length, this means the user cleared it, so we present any cached results,
    * that we have previously saved. Also before any new load we make sure to cache the previous results.
    * */
    private Observer<TextViewTextChangeEvent> searchTextChangeObserver(){
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Timber.e("OnCompleted in searchTextObserver");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("OnError in searchTextObserver:%s",e.getMessage());
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Timber.e("Text received is:%s",textViewTextChangeEvent.text());
                String currentText = String.valueOf(textViewTextChangeEvent.text());
                if(currentText.length() == 0){
                    Timber.e("Presenting full list of saved foods.");
                    presentFoods(savedFoods);
                }else{
                    Timber.e("Searching for:%s",currentText);
                    savedFoodsPresenter.searchSavedFood(currentText);
                }
            }
        };
    }

    /*
    * Presenting the passed foods list.
    * Either the full saved foods or the sublist of the searched saved foods.
    * */
    private void presentFoods(ArrayList<FoodModel> foods) {
        this.savedFoodsRecyclerAdapter.replaceData(foods);
        this.savedFoodsRecyclerAdapter.notifyDataSetChanged();
    }

    /*
    * Getting the results list of our SavedFoods from the Presenter.
    * */
    @Override
    public void presentSavedFoods(ArrayList<FoodModel> savedFoods) {
        this.savedFoods = savedFoods;
        presentFoods(this.savedFoods);
    }

    /*
    * Getting the search results on our SavedFoods table.
    * */
    @Override
    public void presentSearchedFoods(ArrayList<FoodModel> searchedFoods) {
        presentFoods(searchedFoods);
    }

    /*
    * Acting on the event of a click in one of our items on the RecyclerView
    * We navigate the user to the FoodDetails screen.
    * */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void foodSelected(EventsProvider.FoodSelectedEvent event){
        Intent intent = new Intent(this, FoodDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(FoodModel.FOOD_MODEL, Parcels.wrap(event.getFoodModel()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onPause() {
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        savedFoodsPresenter.detachView();
        if(searchSubscription != null && !searchSubscription.isUnsubscribed())
            searchSubscription.unsubscribe();
        super.onDestroy();
    }
}
