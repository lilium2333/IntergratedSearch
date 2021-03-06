package com.lilium.intergratesearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lilium.intergratesearch.Entity.BaiduEntiy;
import com.lilium.intergratesearch.Listner.BaiduSubmitListner;
import com.lilium.intergratesearch.R;
import com.lilium.intergratesearch.Utils.TextHighLight;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaiduSuggestionAdapter extends RecyclerView.Adapter<BaiduSuggestionAdapter.ViewHolder> {
    private List<BaiduEntiy> mBaiduList;
    private Context mContext;
    private BaiduSubmitListner mListner;
    private String mSearchContent = "";

    public BaiduSuggestionAdapter(List<BaiduEntiy> mBaiduList, Context context, BaiduSubmitListner listner) {
        this.mBaiduList = mBaiduList;
        this.mContext = context;
        this.mListner = listner;
    }

    @NonNull
    @Override
    public BaiduSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_baidu_list_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.baiduSuggestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                BaiduEntiy baiduEntiy = mBaiduList.get(position);
                Uri url = Uri.parse("https://www.baidu.com/s?wd=" + baiduEntiy.getSuggestion());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                mContext.startActivity(intent);

            }
        });
        viewHolder.baiduSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                BaiduEntiy baiduEntiy = mBaiduList.get(position);
                mListner.callback(baiduEntiy.getSuggestion());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaiduSuggestionAdapter.ViewHolder viewHolder, int i) {
        BaiduEntiy baiduEntiy = mBaiduList.get(i);
//        viewHolder.baiduSuggestionItem.setText(baiduEntiy.getSuggestion());
        viewHolder.baiduSuggestionItem.setText(TextHighLight.matcherSearchContent(baiduEntiy.getSuggestion(),new String[]{mSearchContent}, getColor()));
    }
    private String getColor(){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        String color=sharedPreferences.getString("config_color","#F44336");
        return color;
    }

    @Override
    public int getItemCount() {
        return mBaiduList.size();
    }

    public void getSearchContent(String searchContent) {
        this.mSearchContent = searchContent;
    }

    public void swapData(List<BaiduEntiy> baiduEntiyList) {
        this.mBaiduList = baiduEntiyList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView baiduSuggestionItem;
        View baiduSuggestionView;
        ImageButton baiduSubmit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baiduSuggestionView = itemView;
            baiduSuggestionItem = (TextView) itemView.findViewById(R.id.baidu_suggestion_item);
            baiduSubmit = (ImageButton) itemView.findViewById(R.id.baidu_submit);

        }
    }
}
