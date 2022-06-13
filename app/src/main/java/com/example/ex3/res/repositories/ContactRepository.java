package com.example.ex3.res.repositories;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.tasks.AddContactTask;
import com.example.ex3.res.tasks.GetContactsTask;
import com.example.ex3.userView.AddContact;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao dao;
    private ContactListData contactListData;

    public ContactRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        dao = db.contactDao();
        contactListData = new ContactListData(application);
    }

    public void reload(CallbackListener callbackListener) {
        if (callbackListener == null) {
            callbackListener = CallbackListener.getDefault();
        }
        new GetContactsTask(contactListData, dao, callbackListener).execute();
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
                    reload(null);
                }
                contactListData.postValue(list);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(Contact contact, CallbackListener callbackListener) {
        new AddContactTask(contact, dao, callbackListener).execute();
    }
}
