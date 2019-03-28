package com.lilium.intergratesearch.Listner;

import com.lilium.intergratesearch.Entity.Sms;

import java.util.List;

public interface SmsListner {
    void onSuccess(List<Sms> smsList);
    void onFailed();
}
