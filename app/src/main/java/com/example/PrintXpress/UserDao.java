package com.example.PrintXpress;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    // Manual SQL for registration
    @Query("INSERT INTO users (name, email, phone, password) VALUES (:name, :email, :phone, :password)")
    long insertUserManual(String name, String email, String phone, String password);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE (name = :loginId OR email = :loginId OR phone = :loginId) AND password = :password LIMIT 1")
    User login(String loginId, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
