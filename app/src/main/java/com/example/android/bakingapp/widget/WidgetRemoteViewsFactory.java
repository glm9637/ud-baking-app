package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe mRecipe;

    public WidgetRemoteViewsFactory(Context applicationContext, Intent intent){
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = WidgetHelper.loadFromFile(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipe==null?0:mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mRecipe==null || mRecipe.getIngredients().size()<position){
            return null;
        }
        Ingredient ingredient = mRecipe.getIngredients().get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_entry);
        rv.setTextViewText(R.id.txt_quantity,String.format("%s %s",ingredient.getQuantity(),ingredient.getMeasure()));
        rv.setTextViewText(R.id.txt_name, ingredient.getName());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
