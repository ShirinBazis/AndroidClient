package com.example.ex3.res.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.repositories.ContactRepository;
import com.example.ex3.res.repositories.MessageRepository;

import java.util.List;

public class ChatViewViewModel extends AndroidViewModel {
    private MessageRepository repository;

    private LiveData<List<Message>> messages;

    public ChatViewViewModel(Application application) {
        super(application);
        repository = new MessageRepository(application);
        messages = repository.getAll();
    }

    public LiveData<List<Message>> get() {
        return messages;
    }

    public void add(Message message, CallbackListener listener) {
        repository.add(message, listener);
    }

    public void reload(CallbackListener listener) {
        repository.reload(listener);
    }
}
