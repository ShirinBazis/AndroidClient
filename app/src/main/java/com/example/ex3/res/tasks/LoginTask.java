package com.example.ex3.res.tasks;

import android.os.AsyncTask;


import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.UsersAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.User;


public class LoginTask extends AsyncTask<Void, Void, Void> {

    private UsersAPI usersAPI;
    private User user;
    private CallbackListener callbackListener;

    public LoginTask(ContactDao contactDao, LoggedUserDao loggedUserDao, User user, CallbackListener listener) {
        this.callbackListener = listener;
        this.usersAPI = new UsersAPI(contactDao, loggedUserDao);
        this.user = user;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        usersAPI.Login(user, callbackListener);
        return null;
    }
}
