package com.example.ex3.res.tasks;

import android.os.AsyncTask;

import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.api.ContactsAPI;
import com.example.ex3.res.api.MessagesAPI;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Message;

import java.util.List;

public class SendMessageTask extends AsyncTask<Void, Void, Void> {
    private Message message;
    private CallbackListener callbackListener;
    private MessagesAPI messagesAPI;
    private String username;

    public SendMessageTask(Message message, MessageDao dao, CallbackListener listener, List<String> args) {
        this.message = message;
        this.callbackListener = listener;
        this.messagesAPI = new MessagesAPI(null, dao, args.get(1));
        this.username = args.get(0);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        messagesAPI.sendMessage(message, callbackListener, username);
        return null;
    }
}
