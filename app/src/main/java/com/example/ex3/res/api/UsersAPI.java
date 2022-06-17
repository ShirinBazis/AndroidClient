package com.example.ex3.res.api;

import android.util.JsonReader;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.LoggedUser;
import com.example.ex3.res.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersAPI {
    private ContactDao contactDao;
    private LoggedUserDao loggedUserDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UsersAPI(ContactDao contactDao, LoggedUserDao loggedUserDao) {
        this.contactDao = contactDao;
        this.loggedUserDao = loggedUserDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(Ex3.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void Login(User user, CallbackListener lListener) {
        Call<ResponseBody> call = webServiceAPI.login(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    List<LoggedUser> list = loggedUserDao.getAll();
                    LoggedUser temp = new LoggedUser(user.getUsername());
                    try {
                        assert response.body() != null;
                        temp.setToken(response.body().string());
                        if (list.size() != 0 && !Objects.equals(list.get(0).getUsername(), user.getUsername())) {
                            contactDao.clear();
                            loggedUserDao.clear();
                        } else if (list.size() != 0) {
                            list.get(0).setToken(temp.getToken());
                            loggedUserDao.update(list.get(0));
                        } else {
                            loggedUserDao.insert(temp);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                lListener.onResponse(response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                lListener.onFailure();
            }
        });
    }
}