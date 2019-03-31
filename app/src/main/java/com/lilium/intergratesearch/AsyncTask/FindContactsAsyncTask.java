package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.github.promeg.pinyinhelper.Pinyin;
import com.lilium.intergratesearch.Entity.Contacts;
import com.lilium.intergratesearch.Listner.FindContactsListner;

import java.util.ArrayList;
import java.util.List;

public class FindContactsAsyncTask extends AsyncTask<String, Void, List<Contacts>> {
    private FindContactsListner mFindContactsListner;
    private List<Contacts> mContactsList;
    private boolean matched;

    public FindContactsAsyncTask(FindContactsListner mFindContactsListner, List<Contacts> mContactsList,boolean matched) {
        this.mFindContactsListner = mFindContactsListner;
        this.mContactsList = mContactsList;
        this.matched=matched;
    }

    @Override
    protected List<Contacts> doInBackground(String... strings) {
        String text=strings[0];
        List<Contacts> contactsList=new ArrayList<>();
        for(Contacts contacts:mContactsList){
            String contactsName="";
            String keywords="";
            if(matched){
                contactsName=Pinyin.toPinyin(contacts.getContactsName(),"").toLowerCase().trim();
                keywords=Pinyin.toPinyin(text,"").toLowerCase().trim();
            }else{
                contactsName=contacts.getContactsName().toLowerCase().trim();
                keywords=text.toLowerCase().trim();
            }
            if(contactsName.contains(keywords)){
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
