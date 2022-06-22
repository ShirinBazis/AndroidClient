package com.example.ex3;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Ex3 extends Application {
    public static Context context;
    public static String server = "10.0.2.2:7290";
    public static int theme = 1;

    public static void changeServer(String newServer) {
        server = newServer;
    }

    public static void changeTheme(int newTheme) {
        theme = newTheme;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
