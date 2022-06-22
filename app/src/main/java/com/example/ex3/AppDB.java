package com.example.ex3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.dao.LoggedUserDao;
import com.example.ex3.res.dao.MessageDao;
import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.LoggedUser;
import com.example.ex3.res.entities.Message;

@Database(entities = {Contact.class, LoggedUser.class, Message.class}, version = 4)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();

    public abstract LoggedUserDao loggedUserDao();

    private static volatile AppDB appDB;

    public static AppDB getInstance(final Context context) {
        if (appDB == null) {
            synchronized (AppDB.class) {
                if (appDB == null) {
                    appDB = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "app_db")
                            .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return appDB;
    }
}
