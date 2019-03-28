package com.lilium.intergratesearch.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilium.intergratesearch.Entity.SearchEngine;
import com.lilium.intergratesearch.Listner.SearchEngineClickListner;
import com.lilium.intergratesearch.Listner.SearchEngineLongClickLisnter;
import com.lilium.intergratesearch.R;

import java.util.List;

public class SearchEngineAdapter extends RecyclerView.Adapter<SearchEngineAdapter.ViewHolder> {
    private List<SearchEngine> mSearchEngineList;
    private SearchEngineClickListner mSearchEngineClickListner;
    private SearchEngineLongClickLisnter mSearchEngineLongClickLisnter;

    public SearchEngineAdapter(List<SearchEngine> mSearchEngineList, SearchEngineClickListner mSearchEngineClickListner, SearchEngineLongClickLisnter searchEngineLongClickLisnter) {
        this.mSearchEngineList = mSearchEngineList;
        this.mSearchEngineClickListner = mSearchEngineClickListner;
        this.mSearchEngineLongClickLisnter=searchEngineLongClickLisnter;
    }

    @NonNull
    @Override
    public SearchEngineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_engine_item,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.searchEngineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                SearchEngine searchEngine=mSearchEngineList.get(position);
                mSearchEngineClickListner.callBack(searchEngine);

            }
        });
        viewHolder.searchEngineView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int postistion=viewHolder.getAdapterPosition();
                SearchEngine searchEngine=mSearchEngineList.get(postistion);
                mSearchEngineLongClickLisnter.callback(searchEngine,postistion);
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEngineAdapter.ViewHolder viewHolder, int i) {
        SearchEngine searchEngine=mSearchEngineList.get(i);
        viewHolder.searchEngineItem.setText(searchEngine.getName());
    }

    @Override
    public int getItemCount() {
        return mSearchEngineList.size();
    }
    public void swapData(List<SearchEngine> newSearchEngineList){
        this.mSearchEngineList=newSearchEngineList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View searchEngineView;
        TextView searchEngineItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchEngineView=itemView;
            searchEngineItem=(TextView) itemView.findViewById(R.id.search_engine_item);
        }
    }
}
