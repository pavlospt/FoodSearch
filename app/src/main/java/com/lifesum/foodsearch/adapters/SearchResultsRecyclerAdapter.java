package com.lifesum.foodsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifesum.foodsearch.R;
import com.lifesum.foodsearch.models.FoodModel;
import com.lifesum.foodsearch.utils.EventsProvider;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by PavlosPT13.
 * Adapter that shows the list of our Foods, either the cached or the searched ones.
 */
public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter<SearchResultsRecyclerAdapter.SearchViewHolder> {


    private Context context;
    private ArrayList<FoodModel> foodsList;

    public SearchResultsRecyclerAdapter(Context context, ArrayList<FoodModel> foodsList) {
        this.context = context;
        this.foodsList = foodsList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_item, parent, false);
        return new SearchViewHolder(view);
    }

    /*
    * We format the value of the carbohydrates and checking the verified value,
    * on which we present either a black or a gray verified icon.
    * */
    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final FoodModel tempFoodModel = foodsList.get(position);
        holder.titleValue.setText(tempFoodModel.getTitle());
        holder.carbohydrates.setText(
                String.format(
                        "%.2f %s",
                        tempFoodModel.getCarboHydrates(),
                        context.getResources().getString(R.string.carbohydrates)
                )
        );
        if (tempFoodModel.isVerified()) {
            holder.verified.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_verified_black_24dp, context.getTheme()));
        } else {
            holder.verified.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_verified_grey600_24dp, context.getTheme()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(
                        new EventsProvider.FoodSelectedEvent(
                                String.valueOf(tempFoodModel.getId()), tempFoodModel
                        )
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    public ArrayList<FoodModel> getCurrentFoods() {
        return this.foodsList;
    }

    public void replaceData(ArrayList<FoodModel> freshFoods) {
        this.foodsList.clear();
        this.foodsList.addAll(freshFoods);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title_value)
        TextView titleValue;
        @Bind(R.id.tv_carbohydrates)
        TextView carbohydrates;
        @Bind(R.id.iv_verified)
        ImageView verified;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
