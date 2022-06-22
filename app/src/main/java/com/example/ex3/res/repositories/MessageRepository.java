package com.example.ex3.res.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ex3.AppDB;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.tasks.AddContactTask;
import com.example.ex3.res.tasks.AddMessageTask;
import com.example.ex3.res.tasks.GetContactsTask;
import com.example.ex3.res.tasks.GetMessagesTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageRepository {
    private MessageDao dao;
    private MessageListData messageListData;
    private List<String> args;

    public MessageRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        dao = db.messageDao();
        messageListData = new MessageRepository.MessageListData(application);
        args = new ArrayList<>();
        this.args.add(db.loggedUserDao().getAll().get(0).getUsername());
        this.args.add(db.loggedUserDao().getAll().get(0).getToken());
    }

    public void reload(CallbackListener listener) {
        if (listener == null) {
            listener = CallbackListener.getDefault();
        }
        new GetMessagesTask(messageListData, dao, listener, args.get(1)).execute();
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        Application application;

        public MessageListData(Application application) {
            super();
            setValue(new LinkedList<>());
            this.application = application;
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
            {
                List<Message> list = dao.getAll();
                if (list.size() == 0) {
                    reload(null);
                }
                messageListData.postValue(list);
            }).start();
        }
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }

    public void add(Message message, CallbackListener listener) {
        new AddMessageTask(message, dao, listener, args).execute();
    }
}




