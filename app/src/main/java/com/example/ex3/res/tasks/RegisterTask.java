package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.UsersAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.NewUser;


public class RegisterTask extends AsyncTask<Void, Void, Void> {

    private UsersAPI usersAPI;
    private NewUser newUser;
    private CallbackListener callbackListener;

    public RegisterTask(NewUser newUser, CallbackListener listener) {
        this.callbackListener = listener;
        this.usersAPI = new UsersAPI(null, null);
        this.newUser = newUser;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        usersAPI.Register(newUser, callbackListener);
        return null;
    }
}

