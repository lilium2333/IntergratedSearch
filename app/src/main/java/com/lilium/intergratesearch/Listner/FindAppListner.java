package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.App;

import java.util.List;

public interface FindAppListner {
    void onSuccess(List<App> appList);
    void onFailed();
}
