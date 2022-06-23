package com.example.ex3.res.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.tasks.AddContactTask;
import com.example.ex3.res.tasks.GetContactsTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao dao;
    private ContactListData contactListData;
    private List<String> args;

    public ContactRepository(Application application) {
        AppDB db = AppDB.getInstance();
        dao = db.contactDao();
        contactListData = new ContactListData(application);
        args = new ArrayList<>();
        this.args.add(db.loggedUserDao().getAll().get(0).getUsername());
        this.args.add(db.loggedUserDao().getAll().get(0).getToken());
    }

    public void reload(CallbackListener listener) {
        if (listener == null) {
            listener = CallbackListener.getDefault();
        }
        new GetContactsTask(contactListData, dao, listener, args.get(1)).execute();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        Application application;

        public ContactListData(Application application) {
            super();
            setValue(new LinkedList<>());
            this.application = application;
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
            {
                List<Contact> list = dao.getAll();
                if (list.size() == 0) {
                    reload(CallbackListener.getDefault());
                }
                contactListData.postValue(list);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(Contact contact, CallbackListener listener) {
        new AddContactTask(contact, dao, listener, args).execute();
    }
}
