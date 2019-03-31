package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.github.promeg.pinyinhelper.Pinyin;
import com.lilium.intergratesearch.Entity.Sms;
import com.lilium.intergratesearch.Listner.FindSmsLisnter;

import java.util.ArrayList;
import java.util.List;

public class FindSmsAsyncTask extends AsyncTask<String,Void, List<Sms>> {
    private FindSmsLisnter mFindSmsLisnter;
    private List<Sms> mSmsList;
    public int smsResultSize;
    private boolean matched;
    public FindSmsAsyncTask(FindSmsLisnter mFindSmsLisnter,List<Sms> smsList,int configSmsNum,boolean matched) {
        this.mFindSmsLisnter = mFindSmsLisnter;
        this.mSmsList=smsList;
        this.smsResultSize=configSmsNum;
        this.matched=matched;

    }

    @Override
    protected List<Sms> doInBackground(String... strings) {
        String text=strings[0].toLowerCase().trim();
        List<Sms> results=new ArrayList<>();
        for(Sms sms:mSmsList){
            String smsBody="";
            String keywords="";
            if(matched){
                smsBody=Pinyin.toPinyin(sms.getSmsBody(),"").toLowerCase().trim();
                keywords=Pinyin.toPinyin(text,"").toLowerCase().trim();
            }else{
                smsBody=sms.getSmsBody().toLowerCase().trim();
                keywords=text.toLowerCase().trim();
            }
            if(smsBody.contains(keywords)){
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
