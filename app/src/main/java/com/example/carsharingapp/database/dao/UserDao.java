package com.example.carsharingapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carsharingapp.database.models.User;

@Dao
public interface UserDao {
    // returns primary key from email
    @Query("SELECT id FROM Users WHERE email = :mail;")
    int idUserMail(String mail);
    // returns primary key from password
    @Query("SELECT id FROM Users WHERE password = :pass;")
    int idUserPass(String pass);
    // returns user from primary key
    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1;")
    User findUserById(int id);
    // returns name from primary key
    @Query("SELECT name FROM Users WHERE id = :id;")
    String findUserName(int id);
    // returns user from email
    @Query("SELECT * FROM Users where email = :email")
    User findUserByEmail(String email);

    @Insert
    long insert(User user);

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
