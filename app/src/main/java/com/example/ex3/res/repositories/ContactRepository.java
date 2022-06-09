package com.example.ex3.res.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.tasks.AddContactTask;
import com.example.ex3.res.tasks.GetContactsTask;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao dao;
    private ContactListData contactListData;
    private final ContactsAPI contactAPI;

    public ContactRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        dao = db.contactDao();
        contactListData = new ContactListData();
        contactAPI = new ContactsAPI(contactListData, dao);
    }

    public void reload() {
        new GetContactsTask(contactListData, dao).execute();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
            {
                List<Contact> list = dao.getAll();
                if (list.size() == 0) {
                    reload();
                }
                contactListData.postValue(list);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(Contact contact) {
        new AddContactTask(contact, contactAPI).execute();
    }
}
