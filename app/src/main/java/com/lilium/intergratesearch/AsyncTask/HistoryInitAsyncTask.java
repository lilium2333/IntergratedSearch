package com.lilium.intergratesearch.AsyncTask;

import android.os.AsyncTask;

import com.lilium.intergratesearch.Entity.History;
import com.lilium.intergratesearch.Listner.HistoryInitListner;

import org.litepal.crud.DataSupport;

import java.util.LinkedList;
import java.util.List;

public class HistoryInitAsyncTask extends AsyncTask<Void, Void, LinkedList<History>> {
    private HistoryInitListner mListner;

    public HistoryInitAsyncTask(HistoryInitListner listner) {
        this.mListner = listner;
    }

    @Override
    protected LinkedList<History> doInBackground(Void... voids) {
        LinkedList<History> results = new LinkedList<>();
        List<History> histories = DataSupport.findAll(History.class);
        if (histories == null) {
            return results;
        } else{
            for(History historyTemp:histories){
                results.addFirst(historyTemp);
            }
            return results;
        }

    }

    @Override
    protected void onPostExecute(LinkedList<History> histories) {
        super.onPostExecute(histories);
        if (histories.size() == 0) {
            mListner.onFailed();
        } else {
            mListner.onSucess(histories);
        }

    }
}
