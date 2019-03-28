package com.lilium.intergratesearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilium.intergratesearch.Entity.Sms;
import com.lilium.intergratesearch.R;
import com.lilium.intergratesearch.Utils.TextHighLight;

import java.util.List;

public class SmsSuggestionAdapter extends RecyclerView.Adapter<SmsSuggestionAdapter.ViewHolder> {
    private Context mContext;
    private List<Sms> mSmsList;
    private String mSearchContent;

    public SmsSuggestionAdapter(Context mContext, List<Sms> mSmsList) {
        this.mContext = mContext;
        this.mSmsList = mSmsList;
    }

    @NonNull
    @Override
    public SmsSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_sms_list_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmsSuggestionAdapter.ViewHolder viewHolder, int i) {
        Sms sms=mSmsList.get(i);
        String smsName=sms.getSmsName();
        if(smsName==null){
            smsName="未命名";
        }
        viewHolder.smsNameAndNumber.setText(smsName+"   "+sms.getSmsNumber());
        final String phonenumber=sms.getSmsNumber();
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phonenumber));
                mContext.startActivity(intent);
            }
        });
        viewHolder.smsBody.setText(TextHighLight.matcherSearchContent(sms.getSmsBody(),new String[]{mSearchContent}));
    }

    @Override
    public int getItemCount() {
        return mSmsList.size();
    }

    public void swapData(List<Sms> newSmsList){
        this.mSmsList=newSmsList;
        notifyDataSetChanged();
    }
    public void getSearchContent(String searchContent){
        this.mSearchContent=searchContent;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView smsNameAndNumber;
        TextView smsBody;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            smsNameAndNumber=(TextView)itemView.findViewById(R.id.sms_name_number);
            smsBody=(TextView)itemView.findViewById(R.id.sms_body);
        }
    }
}
