package com.example.PrintXpress;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addresses")
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String label;
    private String details;
    private int userId;

    public Address(int id, String label, String details, int userId) {
        this.id = id;
        this.label = label;
        this.details = details;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
