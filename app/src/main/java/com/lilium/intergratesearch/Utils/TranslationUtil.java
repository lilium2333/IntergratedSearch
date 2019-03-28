package com.lilium.intergratesearch.Utils;

import android.util.Log;

import com.google.gson.Gson;
import com.lilium.intergratesearch.Entity.Translation;

public class TranslationUtil {
    private static final String TAG = "TranslationUtil";
    //判断纯英文
    public static boolean isEnglish(String content){

        if(content == null){                    //获取内容为空则返回false
            return false;
        }

        content = content.replace(" ","");      //去掉内容中的空格

        return content.matches("^[a-zA-Z]*");   //判断是否是全英文，是则返回true，反之返回false

    }
    public static String parseEnglishToChinese(String result){
        String meanText="";
        try{
            Gson gson=GsonUtil.getSkipIdGson();
            Translation translation=gson.fromJson(result,Translation.class);
//            Log.d(TAG, "parseEnglishToChinese: "+translation.getWord_name());
            for(Translation.SymbolsBean symbolsBean:translation.getSymbols()){
                for(Translation.SymbolsBean.PartsBean meanMsg:symbolsBean.getParts()){
                    Log.d(TAG, "parseEnglishToChinese: "+meanMsg.getPart());
                    meanText+=meanMsg.getPart()+" - ";
                    for(String mean:meanMsg.getMeans()){
                        meanText+=mean+" ; ";
                        Log.d(TAG, "parseEnglishToChinese: "+mean);
                    }
//                    meanText = meanText.substring(0,meanText.length()-2);
                    meanText.replace(",","，");
                    meanText += "\n";

                }
            }
            meanText = meanText.substring(0,meanText.length()-1);


        }catch (Exception e){
            e.printStackTrace();
        }
        return meanText;
    }
}
