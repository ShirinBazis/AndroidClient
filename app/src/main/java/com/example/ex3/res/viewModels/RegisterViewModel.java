package com.example.ex3.res.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.repositories.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository();
    }

    public void Register(NewUser newUser, CallbackListener listener) {
        repository.Register(newUser, listener);
    }
}
