package com.example.ex3.res.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.repositories.ContactRepository;

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

    public void add(Contact contact) {
         repository.add(contact);
    }

    public void reload() {
        repository.reload();
    }
}
