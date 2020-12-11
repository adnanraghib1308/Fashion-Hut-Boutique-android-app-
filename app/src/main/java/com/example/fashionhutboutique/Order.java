package com.example.fashionhutboutique;

import java.io.Serializable;

public class Order {
    String id;
    String billNumber;
    String amount;
    String bookingDate;
    String deliveryDate;
    String orderStatus;

    public Order(){

    }

    public Order(String id, String billNumber, String amount, String bookingDate, String deliveryDate) {
        this.id = id;
        this.billNumber = billNumber;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.deliveryDate = deliveryDate;
        this.orderStatus = "Waiting for stitch";
    }



    public String getId() {
        return id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getAmount() {
        return amount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getOrderStatus(){
        return  orderStatus;
    }
}
