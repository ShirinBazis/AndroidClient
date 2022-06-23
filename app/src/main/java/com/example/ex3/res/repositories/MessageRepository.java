package com.example.ex3.res.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.tasks.SendMessageTask;
import com.example.ex3.res.tasks.GetMessagesTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageRepository {
    private MessageDao dao;
    private MessageListData messageListData;
    private List<String> args;
    private String contactId;

    public MessageRepository(Application application, String contactId) {
        AppDB db = AppDB.getInstance();
        dao = db.messageDao();
        this.contactId = contactId;
        messageListData = new MessageRepository.MessageListData(application, contactId);
        args = new ArrayList<>();
        this.args.add(db.loggedUserDao().getAll().get(0).getUsername());
        this.args.add(db.loggedUserDao().getAll().get(0).getToken());
    }

    public void reload(String id, CallbackListener listener) {
        if (listener == null) {
            listener = CallbackListener.getDefault();
        }
        if (id == null) {
            new GetMessagesTask(contactId, messageListData, dao, listener, args.get(1)).execute();
        } else new GetMessagesTask(id, messageListData, dao, listener, args.get(1)).execute();
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        Application application;
        private final String contactId;

        public MessageListData(Application application, String contactId) {
            super();
            this.contactId = contactId;
            setValue(new LinkedList<>());
            this.application = application;
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
            {
                List<Message> list = dao.get(contactId);
                if (list.size() == 0) {
                    reload(null, CallbackListener.getDefault());
                }
                messageListData.postValue(list);
            }).start();
        }
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }

    public void send(Message message, CallbackListener listener) {
        new SendMessageTask(message, dao, listener, args).execute();
    }
}




