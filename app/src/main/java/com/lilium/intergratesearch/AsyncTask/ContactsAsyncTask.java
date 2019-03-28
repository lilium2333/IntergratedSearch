package com.lilium.intergratesearch.AsyncTask;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.lilium.intergratesearch.Entity.Contacts;
import com.lilium.intergratesearch.Listner.ContactsListner;

import java.util.ArrayList;
import java.util.List;

public class ContactsAsyncTask extends AsyncTask<Void, Void, List<Contacts>> {
    private Context mContext;
    private ContactsListner contactsListner;

    public ContactsAsyncTask(Context context, ContactsListner listner) {
        this.mContext = context;
        this.contactsListner=listner;
    }

    @Override
    protected List<Contacts> doInBackground(Void... voids) {
        List<Contacts> contactsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor =mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String contactsName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactsNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contacts contacts=new Contacts(contactsName,contactsNumber);
                    contactsList.add(contacts);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return contactsList;

    }

    @Override
    protected void onPostExecute(List<Contacts> contactsResult) {
        super.onPostExecute(contactsResult);
        contactsListner.onSuccess(contactsResult);

    }
}
