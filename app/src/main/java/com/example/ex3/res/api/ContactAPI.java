package com.example.ex3.res.api;

import androidx.lifecycle.MutableLiveData;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {
    private MutableLiveData<List<Contact>> ContactListData;
    private ContactDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ContactAPI(MutableLiveData<List<Contact>> contactListData, ContactDao dao) {
        this.ContactListData = contactListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(Ex3.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Contact>> call = webServiceAPI.getContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                new Thread(() -> {
                    dao.clear();
                    dao.insertList(response.body());
                    ContactListData.postValue(dao.getAll());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }
}
