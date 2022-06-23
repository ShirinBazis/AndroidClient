package com.example.ex3.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.viewModels.RegisterViewModel;

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
        if (Ex3.theme == 0) {
            setTheme(R.style.darkTheme_Ex3);
        } else {
            setTheme(R.style.Theme_Ex3);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Ex3.theme == 0) {
            findViewById(R.id.registerBkg).setBackgroundResource(R.drawable.dark_gradient_background);
        } else {
            findViewById(R.id.registerBkg).setBackgroundResource(R.drawable.gradient_background);
        }
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
            if (areRegisterFieldsValid()) {
                NewUser newUser = new NewUser(etUsername.getText().toString(), etNickname.getText().toString(), etPassword.getText().toString());
                viewModel.Register(newUser, getListener(newUser));
            }
        });
    }

    private boolean areRegisterFieldsValid() {
        int error = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            error = 1;
        }
        if (etNickname.length() == 0) {
            etNickname.setError("Nickname is required");
            error = 1;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            error = 1;
        }
        if (etConfirm.length() == 0) {
            etConfirm.setError("Password confirm is required");
            error = 1;
        }
        if (!etUsername.getText().toString().matches("^(?=.*[a-zA-Z])[a-zA-Z]+$")) {
            etUsername.setError("Username must be without spaces and include letters too");
            error = 1;
        }
        if (!etNickname.getText().toString().matches("^(?=.*[a-zA-Z])[a-zA-Z]+$")) {
            etNickname.setError("Nickname must be without spaces and include letters too");
            error = 1;
        }
        if (!etPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
            etPassword.setError("Password must contain letters and numbers");
            error = 1;
        } else if (etPassword.length() < 4 || etPassword.length() > 20) {
            etPassword.setError("Password must be between 4 to 20 characters");
            error = 1;
        } else if (!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
            etConfirm.setError("This password is different from the previous one");
            error = 1;
        }
        return error == 0;
    }
}