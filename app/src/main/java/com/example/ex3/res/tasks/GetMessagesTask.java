package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.MessagesAPI;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Message;

import java.util.List;

public class GetMessagesTask extends AsyncTask<Void, Void, Void> {
    private CallbackListener callbackListener;
    private MessagesAPI messagesAPI;

    public GetMessagesTask(MutableLiveData<List<Message>> postListData, MessageDao dao,
                           CallbackListener listener, String token) {
        this.callbackListener = listener;
        this.messagesAPI = new MessagesAPI(postListData, dao, token);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        messagesAPI.getAllMessages(callbackListener);
        return null;
    }
}
