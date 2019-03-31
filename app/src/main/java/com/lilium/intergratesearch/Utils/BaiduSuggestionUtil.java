package com.lilium.intergratesearch.Utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lilium.intergratesearch.Entity.BaiduEntiy;
import com.lilium.intergratesearch.Entity.BaiduSuggestion;

import java.util.ArrayList;
import java.util.List;

public class BaiduSuggestionUtil {
    public static List<BaiduEntiy> parseBaiduSuggestion(String responseBody, Context context){
        int mbaiduCount=context.getSharedPreferences("config",Context.MODE_PRIVATE).getInt("config_baidu_count",5);
        List<BaiduEntiy> baiduEntiyList=new ArrayList<>();
        try{
            Gson gson=new Gson();
            String responseJsonTemp=responseBody.replace("window.baidu.sug(","");
            String responseJson=responseJsonTemp.substring(0,responseJsonTemp.length()-2);
            Log.d("baidutest", "parseBaiduSuggestion: "+responseJson);
            BaiduSuggestion baiduSuggestion=gson.fromJson(responseJson,BaiduSuggestion.class);
            for(String suggestion:baiduSuggestion.getS()){
                BaiduEntiy baiduEntiy=new BaiduEntiy();
                baiduEntiy.setKeywords(baiduSuggestion.getQ());
                baiduEntiy.setSuggestion(suggestion);
                baiduEntiyList.add(baiduEntiy);
                if(baiduEntiyList.size()>=mbaiduCount){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return baiduEntiyList;

    }
}
