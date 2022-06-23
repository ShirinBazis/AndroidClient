package com.example.ex3.res.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Invitation;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.entities.Transfer;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesAPI {
    private MutableLiveData<List<Message>> messageListData;
    private MessageDao dao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public MessagesAPI(MutableLiveData<List<Message>> messageListData, MessageDao dao, String token) {
        this.messageListData = messageListData;
        this.dao = dao;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + Ex3.server + "/api/").client((
                        new OkHttpClient.Builder()).addInterceptor(
                        chain -> chain.proceed(chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token).build())).build())
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getMessages(String id, CallbackListener listener) {
        Call<List<Message>> call = webServiceAPI.getMessages(id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    new Thread(() -> {
                        dao.clear(id);
                        List<Message> temp = response.body();
                        assert temp != null;
                        for (Message m : temp) {
                            m.setContactName(id);
                        }
                        dao.insertList(temp);
                        messageListData.postValue(dao.get(id));
                        listener.onResponse(response.code());
                    }).start();
                } else {
                    listener.onResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                if (listener != null)
                    listener.onFailure();
            }
        });
    }

    private WebServiceAPI contactWebService(String server) {
        String url = "http://" + server + "/api/";
        Retrofit tempRetrofit = new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return tempRetrofit.create(WebServiceAPI.class);
    }

    public void sendMessage(@NonNull Message message, CallbackListener listener, String username) {
        Contact findContact = AppDB.getInstance().contactDao().get(message.getContactName());
        Transfer transfer = new Transfer(username, message.getContactName(), message.getContent());
        Call<Void> callA = contactWebService(findContact.getServer()).transfer(transfer);
        callA.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Call<Void> callB = webServiceAPI.sendMessage(message.getContactName(), message);
                    callB.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 201) {
                                new Thread(() -> {
                                    dao.insert(message);
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
