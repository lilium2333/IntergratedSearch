package com.lilium.intergratesearch.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lilium.intergratesearch.Entity.History;
import com.lilium.intergratesearch.Listner.HistoryClickLisnter;
import com.lilium.intergratesearch.R;

import org.litepal.crud.DataSupport;

import java.util.LinkedList;

public class HistorySuggestionAdapter extends RecyclerView.Adapter<HistorySuggestionAdapter.ViewHolder> {
    private LinkedList<History> mHistoryList=new LinkedList<>();
    private HistoryClickLisnter mClickListner;

    public HistorySuggestionAdapter(LinkedList<History> mHistoryList,HistoryClickLisnter lisnter) {
        this.mHistoryList = mHistoryList;
        this.mClickListner=lisnter;
    }

    @NonNull
    @Override
    public HistorySuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.histroy_list_item,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                History history=mHistoryList.get(position);
                mClickListner.callback(history);
            }
        });
        viewHolder.historyClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                History history=mHistoryList.get(position);
                DataSupport.deleteAll(History.class,"id=?",String.valueOf(history.getId()));
                mHistoryList.remove(position);
                notifyItemRemoved(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySuggestionAdapter.ViewHolder viewHolder, int i) {
        History history=mHistoryList.get(i);
        viewHolder.historyBody.setText(history.getHistoryBodyText());
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
    public void swapData(LinkedList<History> histories){
        this.mHistoryList=histories;
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView historyBody;
        ImageButton historyClear;
        View historyView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyView=itemView;
            historyBody=(TextView)itemView.findViewById(R.id.hitory_body);
            historyClear=(ImageButton)itemView.findViewById(R.id.histoy_clear);
        }
    }
}
