package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.lilium.intergratesearch.Entity.SearchEngine;
import com.lilium.intergratesearch.Listner.InitSearchEngineListner;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class InitSearchEngineAsyncTask extends AsyncTask<Void, Void, List<SearchEngine>> {

    private InitSearchEngineListner mSearchEngineListner;

    public InitSearchEngineAsyncTask(InitSearchEngineListner mSearchEngineListner) {
        this.mSearchEngineListner = mSearchEngineListner;
    }

    @Override
    protected List<SearchEngine> doInBackground(Void... voids) {
        List<SearchEngine> results=new ArrayList<>();
        SearchEngine searchEngineBaidu = new SearchEngine();
        searchEngineBaidu.setName("百度");
        searchEngineBaidu.setUrl("https://www.baidu.com/s?wd=%s");
        results.add(searchEngineBaidu);
        SearchEngine searchEngineGoogle = new SearchEngine();
        searchEngineGoogle.setName("Google");
        searchEngineGoogle.setUrl("https://www.google.com.hk/search?q=%s");
        results.add(searchEngineGoogle);
        List<SearchEngine> searchEngineList=DataSupport.findAll(SearchEngine.class);
        results.addAll(searchEngineList);
        return results;


    }

    @Override
    protected void onPostExecute(List<SearchEngine> searchEnginesResluts) {
        super.onPostExecute(searchEnginesResluts);
        if (searchEnginesResluts != null && searchEnginesResluts.size() >=2) {
            mSearchEngineListner.onSuccess(searchEnginesResluts);
        } else {
            mSearchEngineListner.onFailed();
        }
    }
}
