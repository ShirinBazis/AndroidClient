package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.room.Dao;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

public class AddContactTask extends AsyncTask<Void, Void, Void> {

    private Contact contact;
    private CallbackListener callbackListener;
    private ContactDao dao;

    public AddContactTask(Contact contact, ContactDao dao, CallbackListener listener) {
        this.contact = contact;
        this.dao = dao;
        this.callbackListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ContactsAPI contactAPI = new ContactsAPI(null, dao);
        contactAPI.addContact(contact, callbackListener);
        return null;
    }
}
