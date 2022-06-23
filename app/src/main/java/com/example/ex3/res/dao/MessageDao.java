package com.example.ex3.res.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> getAll();

    @Query("SELECT * FROM message WHERE contactName= :contactName ")
    List<Message> get(String contactName);

    @Insert
    void insert(Message... messages);

    @Insert
    void insertList(List<Message> messages);

    @Query("DELETE FROM message WHERE contactName=:id ")
    void clear(String id);

    @Query("DELETE FROM message")
    void clearAll();

}
