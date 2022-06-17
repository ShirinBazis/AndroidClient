package com.example.ex3.res.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ex3.res.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(String id);

    @Insert
    void insert(Contact... contact);

    @Insert
    void insertList(List<Contact> contacts);

    //@Update
    //void update(Post... posts);

    //@Delete
    //  void delete(Post... posts);

    @Query("DELETE FROM contact")
    void clear();
}
