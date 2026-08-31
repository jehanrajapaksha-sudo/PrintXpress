package com.example.PrintXpress;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String orderNumber;
    private String productName;
    private String status;
    private String date;
    private int quantity;
    private String paperType;
    private String customText;
    private String deliveryOption;
    private int userId;

    public OrderEntity(int id, String orderNumber, String productName, String status, String date,
                       int quantity, String paperType, String customText, String deliveryOption, int userId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.status = status;
        this.date = date;
        this.quantity = quantity;
        this.paperType = paperType;
        this.customText = customText;
        this.deliveryOption = deliveryOption;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getPaperType() { return paperType; }
    public void setPaperType(String paperType) { this.paperType = paperType; }
    public String getCustomText() { return customText; }
    public void setCustomText(String customText) { this.customText = customText; }
    public String getDeliveryOption() { return deliveryOption; }
    public void setDeliveryOption(String deliveryOption) { this.deliveryOption = deliveryOption; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}