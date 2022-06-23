package com.example.ex3.res.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.repositories.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();
    }

    public void Login(User user, CallbackListener listener) {
        repository.Login(user, listener);
    }
}

