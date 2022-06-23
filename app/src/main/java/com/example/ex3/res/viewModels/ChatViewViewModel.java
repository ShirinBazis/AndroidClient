package com.example.ex3.res.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.repositories.MessageRepository;

import java.util.List;

public class ChatViewViewModel extends AndroidViewModel {
    private MessageRepository repository;
    Application application;

    public ChatViewViewModel(Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<Message>> get(String contactId) {
        repository = new MessageRepository(application, contactId);
        return repository.getAll();
    }

    public void sendMessage(Message message, CallbackListener listener) {
        repository.send(message, listener);
    }

    public void reload(String id, CallbackListener listener) {
        repository.reload(id, listener);
    }
}
