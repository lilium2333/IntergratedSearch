package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.SearchEngine;

import java.util.List;

public interface InitSearchEngineListner {
    void onSuccess(List<SearchEngine> searchEngineList);
    void onFailed();
}
