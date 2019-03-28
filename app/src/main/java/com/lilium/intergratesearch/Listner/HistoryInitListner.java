package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.History;

import java.util.LinkedList;

public interface HistoryInitListner {
    public void onSucess(LinkedList<History> histories);
    public void onFailed();
}
