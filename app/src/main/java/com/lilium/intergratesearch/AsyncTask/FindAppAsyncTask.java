package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.github.promeg.pinyinhelper.Pinyin;
import com.lilium.intergratesearch.Entity.App;
import com.lilium.intergratesearch.Listner.FindAppListner;

import java.util.ArrayList;
import java.util.List;

public class FindAppAsyncTask extends AsyncTask<String, Void, List<App>> {
    private FindAppListner listner;
    private List<App> mAppList;
    private static final String TAG = "FindAppAsyncTask";
    private boolean matcher;

    public FindAppAsyncTask(FindAppListner listner, List<App> mAppList,boolean matcher) {
        this.listner = listner;
        this.mAppList = mAppList;
        this.matcher=matcher;
    }

    @Override
    protected List<App> doInBackground(String... strings) {
        String text = strings[0];
        List<App> results = new ArrayList<>();
        for (App app : mAppList) {
            String appName="";
            String keywords="";
            if(matcher){
                appName=Pinyin.toPinyin(app.getAppName(),"").toLowerCase().trim();
                keywords=Pinyin.toPinyin(text,"").toLowerCase().trim();
            }else{
                appName=app.getAppName().toLowerCase().trim();
                keywords=text.toLowerCase().trim();
            }
            if (appName.contains(keywords)) {
                results.add(app);
            }
        }
        return results;

    }

    @Override
    protected void onPostExecute(List<App> apps) {
        super.onPostExecute(apps);
        if (apps.size() > 0) {
            listner.onSuccess(apps);
        }else{
            listner.onFailed();
        }
    }


}
