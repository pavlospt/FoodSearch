package com.lifesum.foodsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.lifesum.foodsearch.FoodSearchApp;
import com.lifesum.foodsearch.R;
import com.lifesum.foodsearch.adapters.SearchResultsRecyclerAdapter;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.models.responsemodels.BaseResponseModel;
import com.lifesum.foodsearch.presenters.MainPresenterImpl;
import com.lifesum.foodsearch.utils.EventsProvider;
import com.lifesum.foodsearch.views.IMainView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

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
 * Our Main Activity that shows the main search feature of the app.
 * It makes network calls, on the given endpoint and returns results of found foods.
 * Also contains a DrawerMenu that provides access to the SavedFoods list.
 */
public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String IMAGE_URL = "image_url_for_header";

    @Inject
    MainPresenterImpl mainPresenter;

    @Bind(R.id.et_search_term)
    EditText searchTermEditText;
    @Bind(R.id.rv_search_results)
    RecyclerView searchResultsRecyclerView;
    @Bind(R.id.t_toolbar)
    Toolbar toolbar;

    private LinearLayoutManager linearLayoutManager;
    private SearchResultsRecyclerAdapter searchResultsRecyclerAdapter;
    private Subscription searchSubscription;
    private ArrayList<FoodModel> searchedFoods, cachedFoods;

    private Drawer menuDrawer;
    private View headerView;
    private ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(this.toolbar);

        FoodSearchApp.from(this).getComponent().inject(this);

        headerView = LayoutInflater.from(this).inflate(R.layout.layout_menu_header, null, false);
        headerImageView = (ImageView) headerView.findViewById(R.id.iv_header_image);

        searchedFoods = new ArrayList<>();
        cachedFoods = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        searchResultsRecyclerAdapter = new SearchResultsRecyclerAdapter(this, searchedFoods);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchResultsRecyclerView.setLayoutManager(linearLayoutManager);
        searchResultsRecyclerView.setAdapter(this.searchResultsRecyclerAdapter);

        mainPresenter.attachView(this);
        mainPresenter.loadCachedFoods();

        menuDrawer = new DrawerBuilder()
                .withToolbar(this.toolbar)
                .withActivity(this)
                .withHeader(headerView)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.saved_foods_title)
                                .withIcon(R.drawable.ic_check_black_24dp)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        drawItemClicked(position);
                        return true;
                    }
                })
                .withSelectedItem(-1)
                .build();

        this.searchSubscription = RxTextView
                .textChangeEvents(searchTermEditText)
                .debounce(350, TimeUnit.MILLISECONDS)
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

        loadHeaderImageView();

    }

    /*
    * Act on Menu item click.
    * */
    private void drawItemClicked(int position) {
        Timber.e("Selected position:%s", position);
        if (position == 1) {
            startActivity(new Intent(this, SavedFoodsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    /*
    * Load Lifesum image on the header of the menu
    * */
    private void loadHeaderImageView() {
        Picasso.with(this)
                .load(IMAGE_URL)
                .into(this.headerImageView);
    }

    /*
    * Observe text changes on our search EditText.
    * Act on those changes based on the value of the current text.
    * If the current text has 0 length, this means the user cleared it, so we present any cached results,
    * that we have previously saved. Also before any new load we make sure to cache the previous results.
    * */
    private Observer<TextViewTextChangeEvent> searchTextChangeObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Timber.e("OnCompleted in searchTextObserver");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("OnError in searchTextObserver:%s", e.getMessage());
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Timber.e("Text received is:%s", textViewTextChangeEvent.text());
                String currentText = String.valueOf(textViewTextChangeEvent.text());
                if (currentText.length() == 0) {
                    Timber.e("Presenting cached foods.");
                    mainPresenter.loadCachedFoods();
                } else {
                    Timber.e("Searching for:%s", textViewTextChangeEvent.text());
                    cachePreviousFoods();
                    mainPresenter.searchFood(currentText);
                }
            }
        };
    }

    /*
    * Caching the response of the latest search
    * by asking the adapter for its current items,
    * which represent the latest search results.
    * */
    private void cachePreviousFoods() {
        ArrayList<FoodModel> foods = new ArrayList<>();
        foods.addAll(
                this.searchResultsRecyclerAdapter.getCurrentFoods()
        );
        if (foods.size() > 0) {
            if (foods.size() >= 10) {
                mainPresenter.cacheFoods(foods);
            } else {
                mainPresenter.cacheFoods((ArrayList<FoodModel>) foods.subList(0, 11));
            }
        }
    }

    /*
    * Showing the results of the API Call after the user has searched for a food.
    * */
    @Override
    public void presentFoodSearch(BaseResponseModel baseResponseModel) {
        searchedFoods = baseResponseModel.getFoodModels();
        searchResultsRecyclerAdapter.replaceData(searchedFoods);
        searchResultsRecyclerAdapter.notifyDataSetChanged();
    }

    /*
    * Presenting the previously cached results, if the user has cleared the EditText
    * */
    @Override
    public void presentCachedFoods(ArrayList<FoodModel> cachedFoods) {
        cachedFoods = cachedFoods;
        searchResultsRecyclerAdapter.replaceData(this.cachedFoods);
        searchResultsRecyclerAdapter.notifyDataSetChanged();
    }

    /*
    * Acting on the event of a click in one of our items on the RecyclerView
    * We navigate the user to the FoodDetails screen.
    * */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void foodSelected(EventsProvider.FoodSelectedEvent event) {
        Intent intent = new Intent(this, FoodDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(FoodModel.FOOD_MODEL, Parcels.wrap(event.getFoodModel()));
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        menuDrawer.setSelection(-1);
    }

    @Override
    protected void onDestroy() {
        this.mainPresenter.detachView();
        if (searchSubscription != null && !searchSubscription.isUnsubscribed())
            searchSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (this.menuDrawer.isDrawerOpen())
            menuDrawer.closeDrawer();
        else
            super.onBackPressed();
    }
}
