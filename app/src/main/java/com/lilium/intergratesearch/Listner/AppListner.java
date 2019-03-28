package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.App;

import java.util.List;

public interface AppListner {
    void onsuccess(List<App> appList);
    void onFailed();
}
