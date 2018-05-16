package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

public class BottomNavigationAdapter extends RecyclerView.Adapter<BottomNavigationAdapter.BottomNavigationViewHolder> {

    private LayoutInflater mInflater;
    private Recipe mRecipe;
    private int mSelection;
    private ItemSelected mItemSelectedListener;
	
	/**
	 * @return the currently selected position
	 */
	public int getCurrentPosition() {
        return mSelection;
    }

    public interface ItemSelected{
        void onIngredientsSelected(int position,Recipe recipe);
        void onStepSelected(int position, Step step);
    }

    public BottomNavigationAdapter(Context context, Recipe recipe){
        mInflater = LayoutInflater.from(context);
        mRecipe = recipe;
        mSelection = 0;
    }

    public void setItemSelectedListener(ItemSelected itemSelectedListener){
        mItemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public BottomNavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BottomNavigationViewHolder(mInflater.inflate(R.layout.bottom_navigation_entry,parent,false));
    }
	
	/**
	 * Binds the data to the ViewHolder
	 * @param holder the view to display
	 * @param position the position of the entry
	 */
	@Override
    public void onBindViewHolder(@NonNull BottomNavigationViewHolder holder, int position) {
        if(position == mSelection){
            holder.mTitle.setBackgroundResource(R.color.secondaryColor);
        }else {
            holder.mTitle.setBackgroundResource(R.color.secondaryLightColor);
        }
        if(position==0){
            holder.mTitle.setText(R.string.ingredients);
        }else {
            position--;
            holder.mTitle.setText(mRecipe.getSteps().get(position).getShortDescription());
        }
    }
	
	/**
	 * switches the selected item
	 * @param selection the new selected position
	 */
	private void setSelectedItem(int selection){
        int previousSelection = mSelection;
        mSelection = selection;
        notifyItemChanged(previousSelection);
        notifyItemChanged(mSelection);
    }
	
	/**
	 * if possible selects the previous item
	 */
	public void selectPreviousItem(){
        if(mSelection>0){
            selectItem(mSelection-1);
        }
    }
	
	/**
	 * if possible selects the next item
	 */
    public void selectNextItem(){
        if(mSelection<getItemCount()-1){
            selectItem(mSelection+1);
        }
    }
	
	/**
	 * selects the item at the given position
	 * @param position the position of the item to select
	 */
    public void selectItem(int position){
        setSelectedItem(position);
        if(mItemSelectedListener!=null){
            if(position==0){
                mItemSelectedListener.onIngredientsSelected(position,mRecipe);
            }else {
                mItemSelectedListener.onStepSelected(position,mRecipe.getSteps().get(position-1));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size()+1;
    }


    class BottomNavigationViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;

        public BottomNavigationViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView;
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   selectItem(getAdapterPosition());
                }
            });
        }
    }

}
