package com.example.PrintXpress;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insert(OrderEntity order);

    @Update
    void update(OrderEntity order);

    @Delete
    void delete(OrderEntity order);

    @Query("SELECT * FROM orders WHERE userId = :userId")
    List<OrderEntity> getOrdersByUserId(int userId);

    @Query("SELECT * FROM orders")
    List<OrderEntity> getAllOrders();
}