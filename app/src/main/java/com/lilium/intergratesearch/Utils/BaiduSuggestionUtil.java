package com.lilium.intergratesearch.Utils;

import com.google.gson.Gson;
import com.lilium.intergratesearch.Entity.BaiduEntiy;
import com.lilium.intergratesearch.Entity.BaiduSuggestion;

import java.util.ArrayList;
import java.util.List;

public class BaiduSuggestionUtil {
    public static List<BaiduEntiy> parseBaiduSuggestion(String responseBody){
        List<BaiduEntiy> baiduEntiyList=new ArrayList<>();
        try{
            Gson gson=new Gson();
            BaiduSuggestion baiduSuggestion=gson.fromJson(responseBody,BaiduSuggestion.class);
            for(BaiduSuggestion.GBean suggestion:baiduSuggestion.getG()){
                BaiduEntiy baiduEntiy=new BaiduEntiy();
                baiduEntiy.setKeywords(baiduSuggestion.getQ());
                baiduEntiy.setSuggestion(suggestion.getQ());
                baiduEntiyList.add(baiduEntiy);
                if(baiduEntiyList.size()>=5){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return baiduEntiyList;

    }
}
