package com.example.ex3.res.repositories;

import android.app.Application;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.tasks.LoginTask;

public class UserRepository {
    private ContactDao contactDao;
    private LoggedUserDao loggedUserDao;

    public UserRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        contactDao = db.contactDao();
        loggedUserDao = db.loggedUserDao();
    }

    public void Login(User user, CallbackListener listener) {
        new LoginTask(contactDao, loggedUserDao, user, listener).execute();
    }
}
