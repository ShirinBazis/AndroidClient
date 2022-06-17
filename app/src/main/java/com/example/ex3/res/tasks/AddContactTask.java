package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.room.Dao;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

import java.util.List;

public class AddContactTask extends AsyncTask<Void, Void, Void> {

    private Contact contact;
    private CallbackListener callbackListener;
    private ContactsAPI contactAPI;
    private String username;

    public AddContactTask(Contact contact, ContactDao dao, CallbackListener listener, List<String> args) {
        this.contact = contact;
        this.callbackListener = listener;
        this.contactAPI = new ContactsAPI(null, dao, args.get(1));
        this.username = args.get(0);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contactAPI.addContact(contact, callbackListener, username);
        return null;
    }
}
