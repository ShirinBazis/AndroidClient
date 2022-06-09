package com.example.ex3.res.api;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Invitation;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    private MutableLiveData<List<Contact>> ContactListData;
    private ContactDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private int res;
    private Object lock1 = new Object();

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public ContactsAPI(MutableLiveData<List<Contact>> contactListData, ContactDao dao) {
        this.ContactListData = contactListData;
        this.dao = dao;
        this.res = 0;
        retrofit = new Retrofit.Builder()
                .baseUrl(Ex3.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getAllContact() {
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

    public void addContact(@NonNull Contact contact) {
        Invitation invitation = new Invitation("leonardoR", contact.getId(), contact.getServer());
        Call<Void> callA = webServiceAPI.sendInvitation(invitation);
        callA.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Call<Void> callB = webServiceAPI.addContact(contact);
                    callB.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 201) {
                                new Thread(() -> {
                                    dao.insert(contact);
                                }).start();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}

