package com.example.ex3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ex3.res.dao.ContactDao;
import com.example.ex3.res.entities.Contact;

@Database(entities = {Contact.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static volatile AppDB appDB;

    public static AppDB getInstance(final Context context) {
        if (appDB == null) {
            synchronized (AppDB.class) {
                if (appDB == null) {
                    appDB = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "app_db")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return appDB;
    }
}
