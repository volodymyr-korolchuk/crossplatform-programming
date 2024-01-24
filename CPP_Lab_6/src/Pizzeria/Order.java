package Pizzeria;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements DataDisplayer, Serializable {
    private int number;

    private String address;

    private String customer;

    private LocalDateTime deliveryTime;

    private List<String> itemList;

    public Order(int number, String address, String customer, LocalDateTime deliveryTime, List<String> itemList) {
        this.number = number;
        this.address = address;
        this.customer = customer;
        this.deliveryTime = deliveryTime;
        this.itemList = itemList;
    }

    public int getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    @Override
    public void print() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDeliveryTime = deliveryTime.format(formatter);

        System.out.printf("ORDER #%s %nAddress: %s %nCustomer: %s %nDelivery time: %s %nItems: %s%n%n",
            number,
            address,
            customer,
            formattedDeliveryTime,
            new ArrayList<>(itemList)
        );
    }
}
