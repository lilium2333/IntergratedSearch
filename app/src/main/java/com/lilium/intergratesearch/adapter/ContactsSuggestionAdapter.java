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

import com.lilium.intergratesearch.Entity.Contacts;
import com.lilium.intergratesearch.R;
import com.lilium.intergratesearch.Utils.TextHighLight;

import java.util.List;

public class ContactsSuggestionAdapter extends RecyclerView.Adapter<ContactsSuggestionAdapter.ViewHolder> {
    private List<Contacts> mContactsList;
    private Context mContext;
    private String mSearchContent;

    public ContactsSuggestionAdapter(List<Contacts> mContactsList,Context context) {
        this.mContactsList = mContactsList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public ContactsSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_contacts_list_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsSuggestionAdapter.ViewHolder viewHolder, int i) {
        final Contacts contacts=mContactsList.get(i);
        viewHolder.contactsName.setText(TextHighLight.matcherSearchContent(contacts.getContactsName(),new String[]{mSearchContent}));
        viewHolder.contactsNumber.setText(contacts.getContactsNumber());
        viewHolder.contactsview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contacts.getContactsNumber()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }
    public void swapData(List<Contacts> newContactsList){
        this.mContactsList=newContactsList;
        notifyDataSetChanged();
    }
    public void getSearchContent(String searchContent){
        this.mSearchContent=searchContent;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView contactsName;
        TextView contactsNumber;
        View contactsview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactsview=itemView;
            contactsName=(TextView) itemView.findViewById(R.id.contacts_name);
            contactsNumber=(TextView)itemView.findViewById(R.id.contacts_number);
        }
    }
}
