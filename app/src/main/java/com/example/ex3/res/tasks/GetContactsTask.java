package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.ex3.res.api.ContactAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

import java.util.List;

public class GetContactsTask extends AsyncTask<Void, Void, Void> {
    private MutableLiveData<List<Contact>> postListData;
    private ContactDao dao;

    public GetContactsTask(MutableLiveData<List<Contact>> postListData, ContactDao dao) {
        this.postListData = postListData;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ContactAPI contactAPI = new ContactAPI(postListData, dao);
        contactAPI.get();
        return null;
    }
}
