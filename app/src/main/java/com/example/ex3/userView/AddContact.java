package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.viewModels.ContactListViewModel;

public class AddContact extends AppCompatActivity {
    private ContactListViewModel viewModel;
    EditText etUsername, etNickname, etServer;

    private CallbackListener getListener(Contact newContact) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code == 201) {
                    finish();
                    Toast.makeText(Ex3.context, "Successfully added " + newContact.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddContact.this, "Couldn't add contact", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(AddContact.this, R.string.cant_reach, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private boolean CheckLoginFields() {
        int flag = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            flag = 1;
        }
        if (etNickname.length() == 0) {
            etNickname.setError("Nickname is required");
            flag = 1;
        }
        if (etServer.length() == 0) {
            etServer.setError("Server is required");
            flag = 1;
        }
        return flag != 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        viewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        etUsername = findViewById(R.id.etAddContactUsr);
        etNickname = findViewById(R.id.etAddContactNick);
        etServer = findViewById(R.id.etAddContactSrvr);
        Button addBtn = findViewById(R.id.btnAddContact);

        addBtn.setOnClickListener(v -> {
            if (CheckLoginFields()) {
                Contact newContact = new Contact(etUsername.getText().toString(),
                        etNickname.getText().toString(), etServer.getText().toString());
                viewModel.add(newContact, getListener(newContact));
            }
        });
    }
}