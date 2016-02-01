package com.lifesum.foodsearch.adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifesum.foodsearch.R;
import com.lifesum.foodsearch.databases.tables.FoodTable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by PavlosPT13.
 * Adapter presenting some basic stats about the Food we are seeing.
 */
public class FoodDetailsRecyclerAdapter extends RecyclerView.Adapter<FoodDetailsRecyclerAdapter.FoodDetailsViewHolder> {

    private Context context;
    private ArrayList<Pair<String, String>> keysAndValues;

    public FoodDetailsRecyclerAdapter(Context context, ArrayList<Pair<String, String>> keysAndValues) {
        this.context = context;
        this.keysAndValues = keysAndValues;
    }

    @Override
    public FoodDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food_detail_item, parent, false);
        return new FoodDetailsViewHolder(view);
    }

    /*
    * Before parsing values we need to clear out the PCS_TEXT,
    * which is not a double and present it like a normal String.
    * We also need to format the properties of the Food that are null
    * and show them on the Screen as "0.00" double values.
    * */
    @Override
    public void onBindViewHolder(FoodDetailsViewHolder holder, int position) {
        Pair<String, String> currentPair = keysAndValues.get(position);
        if (currentPair.first.equalsIgnoreCase(FoodTable.COLUMN_PCS_TEXT.replaceAll("_", " "))) {
            String filteredGramsPerServing;
            if (TextUtils.isEmpty(currentPair.second)) {
                filteredGramsPerServing = String.format("%.2f", 0.0);
            } else if (TextUtils.isDigitsOnly(currentPair.second)) {
                filteredGramsPerServing = String.format("%.2f", Double.parseDouble(currentPair.second));
            } else {
                filteredGramsPerServing = currentPair.second;
            }
            holder.foodDetail.setText(String.format("%s: %s",
                    currentPair.first,
                    filteredGramsPerServing
            ));
        } else {
            holder.foodDetail.setText(String.format("%s: %.2f",
                    currentPair.first,
                    Double.parseDouble(
                            TextUtils.isEmpty(currentPair.second) ? "0" : currentPair.second
                    )
            ));
        }
    }

    @Override
    public int getItemCount() {
        return this.keysAndValues.size();
    }

    public class FoodDetailsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_food_detail)
        TextView foodDetail;

        public FoodDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
