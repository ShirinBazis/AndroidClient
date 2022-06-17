package com.example.ex3.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.viewModels.LoginViewModel;
import com.example.ex3.res.viewModels.RegisterViewModel;
import com.example.ex3.userView.ContactList;

public class Register extends AppCompatActivity {

    EditText etUsername, etNickname, etPassword, etConfirm;
    private RegisterViewModel viewModel;

    private CallbackListener getListener(NewUser newUser) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code == 200) {
                    //pass to the previous page
                    finish();
                    Toast.makeText(Ex3.context, "Successfully added " + newUser.getUsername(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "This username is already in use, please choose other name", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(Register.this, R.string.cant_reach, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.btnRegister);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        etUsername = findViewById(R.id.editTextUsername);
        etNickname = findViewById(R.id.editTextNickname);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirm = findViewById(R.id.editTextConfirmPassword);
        // the button that will transfer to the login page
        Button btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
        btnRegister.setOnClickListener(v -> {
            // if all the necessary details correctly inserted, register the user
            if (CheckAllFields()) {
                NewUser newUser = new NewUser(etUsername.getText().toString(),etNickname.getText().toString(), etPassword.getText().toString());
                viewModel.Register(newUser, getListener(newUser));
            }
        });
    }

    private boolean CheckAllFields() {
        int flag = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            flag = 1;
        }
        if (etNickname.length() == 0) {
            etNickname.setError("Nickname is required");
            flag = 1;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            flag = 1;
        }
        if (etConfirm.length() == 0) {
            etConfirm.setError("You must confirm the password");
            flag = 1;
        }
        if (!etUsername.getText().toString().matches("^(?=.*[a-zA-Z])[a-zA-Z]+$")) {
            etUsername.setError("Username must be without spaces and include letters too");
            flag = 1;
        }
        if (!etNickname.getText().toString().matches("^(?=.*[a-zA-Z])[a-zA-Z]+$")) {
            etNickname.setError("Nickname must be without spaces and include letters too");
            flag = 1;
        }
        if (!etPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
            etPassword.setError("Password must contains letters and numbers");
            flag = 1;
        }
        else if (etPassword.length() < 4 || etPassword.length() > 20) {
            etPassword.setError("Password must be between 4 to 20 characters");
            flag = 1;
        }
        else if (!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
            etConfirm.setError("This field doesn't match the password");
            flag = 1;
        }
        return flag != 1;
    }
}