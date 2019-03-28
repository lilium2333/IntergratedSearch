package com.lilium.intergratesearch.Utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    public static Gson getSkipIdGson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return f.getName().equals("id")|f.getName().equals("is_CRI");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return clazz.getName().equals("exchange");
                    }
                }).create();
        return gson;
    }
}
