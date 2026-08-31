package com.example.PrintXpress;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SavedDesignDao {
    @Insert
    long insert(SavedDesign design);

    @Update
    void update(SavedDesign design);

    @Delete
    void delete(SavedDesign design);

    @Query("SELECT * FROM designs WHERE userId = :userId ORDER BY dateSaved DESC")
    List<SavedDesign> getDesignsByUserId(int userId);

    @Query("SELECT * FROM designs WHERE id = :id")
    SavedDesign getDesignById(int id);
}
