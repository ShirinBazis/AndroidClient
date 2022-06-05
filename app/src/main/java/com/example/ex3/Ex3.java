package com.example.ex3;

import android.app.Application;
import android.content.Context;

public class Ex3 extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
