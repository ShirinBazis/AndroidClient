package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.entities.Contact;

public class AddContactTask extends AsyncTask<Void, Void, Void> {

    private Contact contact;
    private ContactsAPI contactAPI;

    public AddContactTask(Contact contact, ContactsAPI contactAPI) {
        this.contact = contact;
        this.contactAPI = contactAPI;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contactAPI.addContact(contact);
        return null;
    }
}
