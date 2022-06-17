package com.example.ex3.form;

import com.example.ex3.res.api.CallbackListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.example.ex3.R;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.viewModels.LoginViewModel;
import com.example.ex3.userView.ContactList;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;
    private LoginViewModel viewModel;

    private CallbackListener getListener(Context context) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code == 200) {
                    Intent intent = new Intent(context, ContactList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Wrong password or username", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(Login.this, R.string.cant_reach, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // the button that will check whether the details of the user are correct
        Button btnLogin = findViewById(R.id.btnLogin);
        // the button that will transfer to the register page
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

        btnGoToRegister.setOnClickListener(v -> {
            Intent toRegister = new Intent(this, Register.class);
            startActivity(toRegister);
        });

        btnLogin.setOnClickListener(v -> {
            // if all the necessary details correctly inserted, log the user in
            if (areLoginFieldsValid()) {
                User user = new User(etUsername.getText().toString(), etPassword.getText().toString());
                viewModel.Login(user, getListener(this));
            }
        });
    }

    private boolean areLoginFieldsValid() {
        int error = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            error = 1;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            error = 1;
        }
        return error == 0;
    }
}