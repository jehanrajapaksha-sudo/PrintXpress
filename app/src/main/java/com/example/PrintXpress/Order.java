package com.example.PrintXpress;

public class Order {
    private String orderId;
    private String productName;
    private String status;
    private String date;

    public Order(String orderId, String productName, String status, String date) {
        this.orderId = orderId;
        this.productName = productName;
        this.status = status;
        this.date = date;
    }

    public String getOrderId() { return orderId; }
    public String getProductName() { return productName; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
}