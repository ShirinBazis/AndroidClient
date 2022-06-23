package com.example.ex3.res.repositories;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.tasks.LoginTask;
import com.example.ex3.res.tasks.RegisterTask;

public class UserRepository {
    private ContactDao contactDao;
    private LoggedUserDao loggedUserDao;

    public UserRepository() {
        AppDB db = AppDB.getInstance();
        contactDao = db.contactDao();
        loggedUserDao = db.loggedUserDao();
    }

    public void Login(User user, CallbackListener listener) {
        new LoginTask(contactDao, loggedUserDao, user, listener).execute();
    }

    public void Register(NewUser newUser, CallbackListener listener) {
        new RegisterTask(newUser, listener).execute();
    }
}
