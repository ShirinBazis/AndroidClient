package com.example.ex3.res.api;

public interface CallbackListener {
    void onResponse(int code);

    void onFailure();

    static CallbackListener getDefault() {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {

            }

            @Override
            public void onFailure() {

            }
        };
    }
}
