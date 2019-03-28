package com.lilium.intergratesearch.Entity;

import org.litepal.crud.DataSupport;

public class History extends DataSupport {
    public int id;
    public String historyBodyText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistoryBodyText() {
        return historyBodyText;
    }

    public void setHistoryBodyText(String historyBodyText) {
        this.historyBodyText = historyBodyText;
    }
}
