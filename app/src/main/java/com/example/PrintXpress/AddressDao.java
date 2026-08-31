package com.example.PrintXpress;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    long insert(Address address);

    @Update
    void update(Address address);

    @Delete
    void delete(Address address);

    @Query("SELECT * FROM addresses WHERE userId = :userId")
    List<Address> getAddressesByUserId(int userId);

    @Query("SELECT * FROM addresses WHERE id = :id")
    Address getAddressById(int id);
}
