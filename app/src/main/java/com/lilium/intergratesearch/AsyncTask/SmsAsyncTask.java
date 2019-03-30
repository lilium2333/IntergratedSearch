package com.lilium.intergratesearch.AsyncTask;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Telephony;
import androidx.annotation.RequiresApi;

import com.lilium.intergratesearch.Entity.Sms;
import com.lilium.intergratesearch.Listner.SmsListner;

import java.util.ArrayList;
import java.util.List;

public class SmsAsyncTask extends AsyncTask<Void, Void, List<Sms>> {
    private Context mContext;
    private SmsListner mSmsLisnter;
//    private static final Uri SMS_INBOX = Uri.parse("content://sms");

    public SmsAsyncTask(Context mContext, SmsListner mSmsLisnter) {
        this.mContext = mContext;
        this.mSmsLisnter = mSmsLisnter;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected List<Sms> doInBackground(Void... voids) {
        List<Sms> results = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection = new String[]{"address", "person", "body", "date"};
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, projection, null, null, "date desc");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String smsName = cursor.getString(cursor.getColumnIndex("person"));
                    String smsNumber = cursor.getString(cursor.getColumnIndex("address"));
                    String smsBody = cursor.getString(cursor.getColumnIndex("body"));
                    Sms sms = new Sms(smsName, smsNumber, smsBody);
                    results.add(sms);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(List<Sms> smsListResult) {
        super.onPostExecute(smsListResult);
        if (smsListResult.size() > 0) {
            mSmsLisnter.onSuccess(smsListResult);
        } else {
            mSmsLisnter.onFailed();
        }

    }
}
