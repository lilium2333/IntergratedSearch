package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.lilium.intergratesearch.Entity.Sms;
import com.lilium.intergratesearch.Listner.FindSmsLisnter;

import java.util.ArrayList;
import java.util.List;

public class FindSmsAsyncTask extends AsyncTask<String,Void, List<Sms>> {
    private FindSmsLisnter mFindSmsLisnter;
    private List<Sms> mSmsList;
    public int smsResultSize=3;
    public FindSmsAsyncTask(FindSmsLisnter mFindSmsLisnter,List<Sms> smsList) {
        this.mFindSmsLisnter = mFindSmsLisnter;
        this.mSmsList=smsList;
    }

    @Override
    protected List<Sms> doInBackground(String... strings) {
        String text=strings[0].toLowerCase().trim();
        List<Sms> results=new ArrayList<>();
        for(Sms sms:mSmsList){
            if(sms.getSmsBody().toLowerCase().trim().contains(text)){
                results.add(sms);
                if(results.size()>=smsResultSize){
                    break;
                }
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(List<Sms> smsListResults) {
        super.onPostExecute(smsListResults);
        mFindSmsLisnter.onSuccess(smsListResults);
    }
}