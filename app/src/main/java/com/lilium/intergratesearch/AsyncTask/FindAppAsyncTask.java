package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.lilium.intergratesearch.Entity.App;
import com.lilium.intergratesearch.Listner.FindAppListner;

import java.util.ArrayList;
import java.util.List;

public class FindAppAsyncTask extends AsyncTask<String, Void, List<App>> {
    private FindAppListner listner;
    private List<App> mAppList;
    private static final String TAG = "FindAppAsyncTask";

    public FindAppAsyncTask(FindAppListner listner, List<App> mAppList) {
        this.listner = listner;
        this.mAppList = mAppList;
    }

    @Override
    protected List<App> doInBackground(String... strings) {
        String text = strings[0];
        List<App> results = new ArrayList<>();
        for (App app : mAppList) {
            if (app.getAppName().toLowerCase().trim().contains(text.toLowerCase().trim())) {
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
