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

    // return list of messages of specific contact
    @Query("SELECT * FROM message")
    List<Message> getAll();

    // return only the last message
    @Query("SELECT * FROM message WHERE contactName= :contactName ")
    Message get(String contactName);

    // send a message
    @Insert
    void insert(Message... messages);

    @Insert
    void insertList(List<Message> messages);

    @Query("DELETE FROM message")
    void clear();

    // get a message??????????
    @Update
    void update(Message... messages);


}
