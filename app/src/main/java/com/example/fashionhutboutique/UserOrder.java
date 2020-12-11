package com.example.fashionhutboutique;

public class UserOrder {
    String billNumber;
    String amount;
    String bookingDate;
    String deliveryDate;

    public  UserOrder(){

    }
    public UserOrder(String billNumber, String amount, String bookingDate, String deliveryDate) {
        this.billNumber = billNumber;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.deliveryDate = deliveryDate;
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
}
