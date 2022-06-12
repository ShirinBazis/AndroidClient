package com.example.ex3.res.viewModels;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.repositories.ContactRepository;
import com.example.ex3.userView.AddContact;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {
    private ContactRepository repository;

    private LiveData<List<Contact>> contacts;

    public ContactListViewModel(Application application) {
        super(application);
        repository = new ContactRepository(application);
        contacts = repository.getAll();
    }

    public LiveData<List<Contact>> get() {
        return contacts;
    }

    public void add(Contact contact, CallbackListener callbackListener) {
        repository.add(contact, callbackListener);
    }

    public void reload() {
        repository.reload();
    }
}