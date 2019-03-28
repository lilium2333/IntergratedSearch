package com.lilium.intergratesearch.AsyncTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.lilium.intergratesearch.Listner.AppListner;
import com.lilium.intergratesearch.Entity.App;

import java.util.ArrayList;
import java.util.List;

public class AppAysncTask extends AsyncTask<Void,Void, List<App>> {
    private AppListner listner;
    public AppAysncTask(AppListner listner) {
        this.listner=listner;
    }

    @Override
    protected List<App> doInBackground(Void... voids) {
        List<App> results=getMyAppList();
        return results;
    }
    private List<App> getMyAppList(){
        List<App> appList=new ArrayList<>();
        List<AppUtils.AppInfo> appInfo= (List<AppUtils.AppInfo>) AppUtils.getAppsInfo();
        for(AppUtils.AppInfo appinfoTemp:appInfo){
            if(AppUtils.isAppSystem(appinfoTemp.getPackageName())){
                continue;
            }
            String appName=appinfoTemp.getName();
            Bitmap appImage= ConvertUtils.drawable2Bitmap(appinfoTemp.getIcon());
            String appPackgeNmae=appinfoTemp.getPackageName();
            App app=new App(appName,appImage,appPackgeNmae);
            appList.add(app);
        }
        return appList;
    }

    @Override
    protected void onPostExecute(List<App> apps) {
        super.onPostExecute(apps);

        listner.onsuccess(apps);
    }
}
