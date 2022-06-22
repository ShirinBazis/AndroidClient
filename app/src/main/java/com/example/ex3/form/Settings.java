package com.example.ex3.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.utill.ChangeServer;
import com.example.ex3.utill.ChangeTheme;

public class Settings extends AppCompatActivity {

    private void startUtill() {
        Button changeServer = findViewById(R.id.btnChangeServer);
        Button changeTheme = findViewById(R.id.btnChangeTheme);
        changeServer.setOnClickListener(v -> {
            ChangeServer i = new ChangeServer();
            i.show(getSupportFragmentManager(), "");
        });
        changeTheme.setOnClickListener(v -> {
            ChangeTheme i = new ChangeTheme(() -> {
                if (Ex3.theme == 0) {
                    findViewById(R.id.settingsBkg).setBackgroundResource(R.drawable.dark_gradient_background);
                } else {
                    findViewById(R.id.settingsBkg).setBackgroundResource(R.drawable.gradient_background);
                }
                this.startUtill();
            });
            i.show(getSupportFragmentManager(), "");
        });
        findViewById(R.id.btnReset).setOnClickListener(v -> {
            Ex3.changeServer(getString(R.string.BaseServer));
            Toast.makeText(this, "Server was reset", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_settings);
        if (Ex3.theme == 0) {
            findViewById(R.id.settingsBkg).setBackgroundResource(R.drawable.dark_gradient_background);
        } else {
            findViewById(R.id.settingsBkg).setBackgroundResource(R.drawable.gradient_background);
        }
        startUtill();
    }
}