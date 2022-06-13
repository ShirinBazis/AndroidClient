package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

import java.util.List;

public class GetContactsTask extends AsyncTask<Void, Void, Void> {
    private MutableLiveData<List<Contact>> postListData;
    private ContactDao dao;
    private CallbackListener callbackListener;

    public GetContactsTask(MutableLiveData<List<Contact>> postListData, ContactDao dao, CallbackListener listener) {
        this.postListData = postListData;
        this.dao = dao;
        this.callbackListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ContactsAPI contactAPI = new ContactsAPI(postListData, dao);
        contactAPI.getAllContact(callbackListener);
        return null;
    }
}