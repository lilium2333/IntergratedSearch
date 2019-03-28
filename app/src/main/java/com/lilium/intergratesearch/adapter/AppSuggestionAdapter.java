package com.lilium.intergratesearch.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.lilium.intergratesearch.Entity.App;
import com.lilium.intergratesearch.R;
import com.lilium.intergratesearch.Utils.TextHighLight;

import java.util.List;

public class AppSuggestionAdapter extends RecyclerView.Adapter<AppSuggestionAdapter.ViewHolder> {
    private List<App> mAppList;
    private static final String TAG = "AppSuggestionAdapter";
    private String mSearchContent;

    public AppSuggestionAdapter(List<App> mAppList) {
        this.mAppList = mAppList;
    }

    @NonNull
    @Override
    public AppSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_app_list_item, viewGroup, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.appView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion=viewHolder.getAdapterPosition();
                App app=mAppList.get(postion);
                AppUtils.launchApp(app.getAppPackgeName());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppSuggestionAdapter.ViewHolder viewHolder, int i) {
        App app=mAppList.get(i);
        viewHolder.appName.setText(TextHighLight.matcherSearchContent(app.getAppName(),new String[]{mSearchContent}));
        viewHolder.appImage.setImageBitmap(app.getAppImage());
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
    public void swapData(List<App> mNewAppList){
        this.mAppList=mNewAppList;
        notifyDataSetChanged();
    }
    public void getSearchContent(String searchContent){
        this.mSearchContent=searchContent;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View appView;
        ImageView appImage;
        TextView appName;
//        ImageButton appOpen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appView=itemView;
            appImage = (ImageView) itemView.findViewById(R.id.app_image);
            appName = (TextView) itemView.findViewById(R.id.app_Name);
//            appOpen=(ImageButton)itemView.findViewById(R.id.app_btn);
        }
    }
}
