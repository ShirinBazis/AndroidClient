package com.example.ex3.form;
import com.example.ex3.userView.contactList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ex3.R;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    boolean isAllFieldsChecked = false;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.btnLogin);
        // the button that will transfer to the register page
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnGoToRegister.setOnClickListener(v -> {
            Intent toRegister = new Intent(this, Register.class);
            // the button that will check whether the details of the user are correct
            startActivity(toRegister);
        });
        btnLogin.setOnClickListener(v -> {
            // if all the necessary details correctly inserted, register the user
            isAllFieldsChecked = CheckLoginFields();
            if (isAllFieldsChecked) {
                //Intent intent = new Intent(this, contactList.class);
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
            }
        });
    }

    private boolean CheckLoginFields() {
        int flag = 0;
        if (etUsername.length() == 0) {
            etUsername.setError("Username is required");
            flag = 1;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            flag = 1;
        }

        //check if this user really exists
        //check if this is the right password of the user

//        else if (etPassword.getText().toString().equals(etConfirm.getText().toString())) {
//            etConfirm.setError("This field doesn't match the password");
//            flag = 1;
//        }
        return flag != 1;
    }
}