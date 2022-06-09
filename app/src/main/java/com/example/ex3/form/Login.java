package com.example.ex3.form;
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
        // the button that will transfer to the register page
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnGoToRegister.setOnClickListener(v -> {
            Intent toRegister = new Intent(this, Register.class);
            // the button that will check whether the details of the user are correct
            startActivity(toRegister);
        });
    }

    private boolean CheckAllFields() {
        // need to check if this user really exists
        // need to check if this is the right password of the user
        return true;
    }
}