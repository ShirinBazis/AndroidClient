package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ex3.R;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.viewModels.ContactListViewModel;

public class AddContact extends AppCompatActivity {
    private ContactListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        viewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        EditText etUsername, etNickname, etServer;
        etUsername = findViewById(R.id.etAddContactUsr);
        etNickname = findViewById(R.id.etAddContactNick);
        etServer = findViewById(R.id.etAddContactSrvr);
        Button addBtn = findViewById(R.id.btnAddContact);

        addBtn.setOnClickListener(v -> {
            if (etUsername.length() != 0 && etNickname.length() != 0 && etServer.length() != 0) {
                Contact newContact = new Contact(etUsername.getText().toString(),
                        etNickname.getText().toString(), etServer.getText().toString());
                viewModel.add(newContact);
                finish();
            }
        });
    }
}