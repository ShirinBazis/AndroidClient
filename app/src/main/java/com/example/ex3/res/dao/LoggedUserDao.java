package com.example.ex3.res.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ex3.res.entities.LoggedUser;

import java.util.List;


@Dao
public interface LoggedUserDao {

    @Query("SELECT * FROM loggeduser")
    List<LoggedUser> getAll();

    @Query("SELECT * FROM loggeduser WHERE username = :username")
    LoggedUser get(String username);

    @Insert
    void insert(LoggedUser... loggedUser);

    @Update
    void update(LoggedUser... loggedUser);

    //@Delete
    //  void delete(Post... posts);

    @Query("DELETE FROM loggedUser")
    void clear();
}
