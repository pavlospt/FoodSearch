package com.lifesum.foodsearch.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lifesum.foodsearch.FoodSearchApp;
import com.lifesum.foodsearch.R;
import com.lifesum.foodsearch.adapters.FoodDetailsRecyclerAdapter;
import com.lifesum.foodsearch.databases.tables.FoodTable;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.presenters.FoodDetailsPresenterImpl;
import com.lifesum.foodsearch.views.IFoodDetailsView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PavlosPT13.
 * FoodDetails Screen.
 */
public class FoodDetailsActivity extends AppCompatActivity implements IFoodDetailsView, View.OnClickListener {

    @Inject
    FoodDetailsPresenterImpl foodDetailsPresenter;

    @Bind(R.id.rv_food_details)
    RecyclerView foodDetailsRecyclerView;
    @Bind(R.id.t_toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab_save_food)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.iv_food_image)
    ImageView foodImage;

    private LinearLayoutManager linearLayoutManager;
    private FoodDetailsRecyclerAdapter foodDetailsRecyclerAdapter;
    private MaterialDialog deleteDialog;

    private FoodModel loadedFoodModel;
    private boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ButterKnife.bind(this);

        FoodSearchApp.from(this).getComponent().inject(this);

        foodDetailsPresenter.attachView(this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodDetailsRecyclerView.setLayoutManager(this.linearLayoutManager);

        floatingActionButton.setOnClickListener(this);

        if (getIntent().hasExtra(FoodModel.FOOD_MODEL)) {
            FoodModel tempFoodModel = Parcels.unwrap(getIntent().getParcelableExtra(FoodModel.FOOD_MODEL));
            loadPassedFoodModel(tempFoodModel);
        } else if (getIntent().hasExtra(FoodModel.FOOD_MODEL_SELECTED_ID)) {
            int selectedFoodId = getIntent().getIntExtra(FoodModel.FOOD_MODEL_SELECTED_ID, -1);
            if (selectedFoodId != -1) {
                foodDetailsPresenter.loadSelectedFood(String.valueOf(selectedFoodId), FoodTable.TABLE_SAVED_FOODS);
                ;
            }
        }

        deleteDialog = new MaterialDialog.Builder(this)
                .autoDismiss(true)
                .cancelable(false)
                .title(R.string.delete_action)
                .content(String.format(getString(R.string.deletion_message), loadedFoodModel.getTitle()))
                .negativeText(R.string.changed_mind)
                .negativeColorRes(R.color.colorAccent)
                .positiveText(R.string.delete_action)
                .positiveColorRes(R.color.colorAccent)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteSavedFood();
                    }
                })
                .build();
    }

    /*
    * We delete the current food, from our SavedFoods table.
    * */
    private void deleteSavedFood() {
        foodDetailsPresenter.deleteSavedFood(String.valueOf(loadedFoodModel.getId()));
    }

    @Override
    protected void onDestroy() {
        foodDetailsPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_save_food) {
            if (isSaved) {
                deleteDialog.show();
            } else {
                foodDetailsPresenter.saveCurrentFood(loadedFoodModel);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    /*
    * Loading the current food requested by id.
    * */
    @Override
    public void presentSelectedFood(FoodModel foodModel) {
        loadPassedFoodModel(foodModel);
    }

    /*
    * Showing a Snackbar informing the user about an error on the process of saving the food.
    * */
    @Override
    public void showSaveError() {
        Snackbar
                .make(findViewById(R.id.rl_root_layout), R.string.could_not_save_food, Snackbar.LENGTH_LONG)
                .show();
    }

    /*
    * Showing a Snackbar informing the user about the success of saving the food.
    * */
    @Override
    public void showSaveSuccess() {
        Snackbar
                .make(findViewById(R.id.rl_root_layout), R.string.successfully_saved_food, Snackbar.LENGTH_LONG)
                .show();
    }

    /*
    * Showing a Snackbar informing the user that he has already saved this food.
    * Usage of this method might not occur ever.
    * */
    @Override
    public void showFoodAlreadyExists() {
        Snackbar
                .make(findViewById(R.id.rl_root_layout), R.string.food_already_saved, Snackbar.LENGTH_LONG)
                .show();
    }

    /*
    * Showing a Snackbar informing the user about an error while deleting the saved food.
    * */
    @Override
    public void showDeletionError() {
        Snackbar
                .make(findViewById(R.id.rl_root_layout), R.string.deletion_error, Snackbar.LENGTH_LONG)
                .show();
    }

    /*
    * Presenting the Image we received from the Google CustomSearch API
    * based on the food we are viewing.
    * Should have placed a placeholder, to indicate the loading progress, but left it apart
    * for the sake of time.
    * */
    @Override
    public void showFoodImage(String url) {
        Picasso.with(this)
                .load(url)
                .into(foodImage);
    }

    /*
    * Setting the Icon of our FAB to indicate that the current food is saved.
    * */
    @Override
    public void setFoodIsSaved() {
        isSaved = true;
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_36dp, getTheme()));
    }

    /*
    * Setting the Icon of our FAB to indicate that the current food is not saved.
    * */
    @Override
    public void setFoodIsNotSaved() {
        isSaved = false;
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_content_save_white_36dp, getTheme()));
    }

    /*
    * Loading details for the screen based on the passed FoodModel
    * Checking if the food is saved on our SavedFoods table, initiating image search,
    * setting the title and assigning the Toolbar.
    * */
    private void loadPassedFoodModel(FoodModel foodModel) {
        loadedFoodModel = foodModel;
        foodDetailsPresenter.checkIfFoodIsSaved(String.valueOf(foodModel.getId()));
        foodDetailsPresenter.searchForImage(foodModel.getTitle());
        foodDetailsRecyclerAdapter = new FoodDetailsRecyclerAdapter(this, foodModel.asPairs());
        foodDetailsRecyclerView.setAdapter(foodDetailsRecyclerAdapter);
        toolbar.setTitle(foodModel.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
