package com.example.ex3.res.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Invitation;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    private MutableLiveData<List<Contact>> ContactListData;
    private ContactDao dao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public ContactsAPI(MutableLiveData<List<Contact>> contactListData, ContactDao dao, String token) {
        this.ContactListData = contactListData;
        this.dao = dao;
        retrofit = new Retrofit.Builder()
                .baseUrl(Ex3.context.getString(R.string.BaseUrl)).client((
                        new OkHttpClient.Builder()).addInterceptor(
                        chain -> chain.proceed(chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token).build())).build())
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    private WebServiceAPI contactWebService(String server) {
        String url = "http://" + server + "/api/";
        Retrofit tempRetrofit = new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return tempRetrofit.create(WebServiceAPI.class);
    }

    public void getAllContact(CallbackListener listener) {
        Call<List<Contact>> call = webServiceAPI.getContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.code() == 200) {
                    new Thread(() -> {
                        dao.clear();
                        dao.insertList(response.body());
                        ContactListData.postValue(dao.getAll());
                        listener.onResponse(response.code());
                    }).start();
                } else {
                    listener.onResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public void addContact(@NonNull Contact contact, CallbackListener listener, String username) {
        Invitation invitation = new Invitation(username, contact.getId(), contact.getServer());
        Call<Void> callA = contactWebService(contact.getServer()).sendInvitation(invitation);
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
                            listener.onResponse(response.code());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            listener.onFailure();
                        }
                    });
                } else {
                    listener.onResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}

