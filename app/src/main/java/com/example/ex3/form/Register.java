package com.example.ex3.form;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.ex3.R;

public class Register extends AppCompatActivity {

    boolean isAllFieldsChecked = false;
    EditText etUsername, etNickname, etPassword, etConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.btnRegister);
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
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean CheckAllFields() {
        int flag = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("This field is required");
            flag = 1;
        }
        if (etNickname.length() == 0) {
            etNickname.setError("This field is required");
            flag = 1;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            flag = 1;
        }
        if (!etPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
            etPassword.setError("Password must contain letters and numbers");
            flag = 1;
        }
        if (etPassword.length() < 8) {
            etPassword.setError("Password must be minimum 8 characters");
            flag = 1;
        }
        if (etConfirm.length() == 0) {
            etConfirm.setError("You must confirm the password");
            flag = 1;
        }
        else if (etPassword.getText().toString().equals(etConfirm.getText().toString())) {
            etConfirm.setError("This field doesn't match the password");
            flag = 1;
        }
        return flag != 1;
    }
}