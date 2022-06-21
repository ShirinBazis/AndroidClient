package com.example.ex3.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.ex3.R;
import com.example.ex3.utill.ChangeServer;
import com.example.ex3.utill.ChangeTheme;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button changeServer = findViewById(R.id.btnChangeServer);
        Button changeTheme = findViewById(R.id.btnChangeTheme);
        changeServer.setOnClickListener(v -> {
            ChangeServer i = new ChangeServer();
            i.show(getSupportFragmentManager(), "");
        });
        changeTheme.setOnClickListener(v -> {
            ChangeTheme i = new ChangeTheme();
            i.show(getSupportFragmentManager(), "");
        });
    }

}