package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.lilium.intergratesearch.Entity.Contacts;
import com.lilium.intergratesearch.Listner.FindContactsListner;

import java.util.ArrayList;
import java.util.List;

public class FindContactsAsyncTask extends AsyncTask<String, Void, List<Contacts>> {
    private FindContactsListner mFindContactsListner;
    private List<Contacts> mContactsList;

    public FindContactsAsyncTask(FindContactsListner mFindContactsListner, List<Contacts> mContactsList) {
        this.mFindContactsListner = mFindContactsListner;
        this.mContactsList = mContactsList;
    }

    @Override
    protected List<Contacts> doInBackground(String... strings) {
        String text=strings[0];
        List<Contacts> contactsList=new ArrayList<>();
        for(Contacts contacts:mContactsList){
            if(contacts.getContactsName().toLowerCase().trim().contains(text.toLowerCase().trim())){
                contactsList.add(contacts);
            }
        }
        return contactsList;
    }

    @Override
    protected void onPostExecute(List<Contacts> contactsResult) {
        super.onPostExecute(contactsResult);
        if (mContactsList.size() > 0) {
            mFindContactsListner.onSuccess(contactsResult);
        }else{
            mFindContactsListner.onFailed();
        }
    }
}
