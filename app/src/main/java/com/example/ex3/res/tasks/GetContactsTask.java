package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

import java.util.List;

public class GetContactsTask extends AsyncTask<Void, Void, Void> {
    private CallbackListener callbackListener;
    private ContactsAPI contactAPI;

    public GetContactsTask(MutableLiveData<List<Contact>> postListData, ContactDao dao, CallbackListener listener) {
        this.callbackListener = listener;
        this.contactAPI = new ContactsAPI(postListData, dao);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contactAPI.getAllContact(callbackListener);
        return null;
    }
}