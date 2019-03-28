package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.Contacts;

import java.util.List;

public interface FindContactsListner {
    void onSuccess(List<Contacts> contactsList);
    void onFailed();
}
