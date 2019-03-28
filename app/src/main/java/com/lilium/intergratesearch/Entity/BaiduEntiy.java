package com.lilium.intergratesearch.Entity;

public class BaiduEntiy {
    private String keywords;
    private String suggestion;
    public BaiduEntiy(){

    }

    public BaiduEntiy(String keywords, String suggestion) {
        this.keywords = keywords;
        this.suggestion = suggestion;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
