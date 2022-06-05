package com.example.ex3.res.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.tasks.GetContactsTask;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao dao;
    private ContactListData contactListData;

    public ContactRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        dao = db.contactDao();
        contactListData = new ContactListData();
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
                ContactListData.this.postValue(dao.getAll());
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }
}
