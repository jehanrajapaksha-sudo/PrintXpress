package com.example.PrintXpress;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "designs")
public class SavedDesign {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String filePath;
    private String dateSaved;
    private int userId;

    public SavedDesign(int id, String name, String filePath, String dateSaved, int userId) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.dateSaved = dateSaved;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getDateSaved() { return dateSaved; }
    public void setDateSaved(String dateSaved) { this.dateSaved = dateSaved; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
