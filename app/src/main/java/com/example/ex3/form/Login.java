package com.example.ex3.form;

import com.example.ex3.Ex3;
import com.example.ex3.res.api.CallbackListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import com.example.ex3.R;
import com.example.ex3.res.entities.User;
import com.example.ex3.res.viewModels.LoginViewModel;
import com.example.ex3.userView.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;
    private LoginViewModel viewModel;
    private int flag = Ex3.theme;

    private CallbackListener getListener(Context context) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code == 200) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    // the user doesn't exist or the password is wrong
                    Toast.makeText(Login.this, "Wrong password or username", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(Login.this, R.string.cant_reach, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void startUtill() {
        // the button that will check whether the details of the user are correct
        Button btnLogin = findViewById(R.id.btnLogin);
        // the button that will transfer to the register page
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        // the button that will check whether the details of the user are correct
        Button btnSetings = findViewById(R.id.btnSettings);
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
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Login.this, instanceIdResult -> {
                    String newToken = instanceIdResult.getToken();
                    User user = new User(etUsername.getText().toString(), etPassword.getText().toString(), newToken);
                    viewModel.Login(user, getListener(this));
                });
            }
        });

        btnSetings.setOnClickListener(v -> {
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Ex3.theme == 0) {
            setTheme(R.style.darkTheme_Ex3);
        } else {
            setTheme(R.style.Theme_Ex3);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Ex3.theme == 0) {
            findViewById(R.id.loginBkg).setBackgroundResource(R.drawable.dark_gradient_background);
        } else {
            findViewById(R.id.loginBkg).setBackgroundResource(R.drawable.gradient_background);
        }
        startUtill();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (flag != Ex3.theme) {
            flag = Ex3.theme;
            if (Ex3.theme == 0) {
                findViewById(R.id.loginBkg).setBackgroundResource(R.drawable.dark_gradient_background);
            } else {
                findViewById(R.id.loginBkg).setBackgroundResource(R.drawable.gradient_background);
            }
            startUtill();
        }
    }
}