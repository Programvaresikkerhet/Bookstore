package amu.model;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Order {
    
    private Integer id;
    private Integer customerId;
    private Address address;
    private Calendar createdDate;
    private String value;
    private int status;
    private ArrayList<OrderItem> items = new ArrayList<OrderItem>();
        
    public Order(){
    	this.id = null;
        this.customerId = null;
        this.createdDate = null;
        this.value = "";
        this.status = 0;
        this.items = null;
    }

    public Order(int id, int customerId, Calendar createdDate, String value, int status, ArrayList<OrderItem> orderItems, Address address) {
        this.id = id;
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.value = value;
        this.status = status;
        this.items = orderItems;
        this.address = address;
    }

    public Order(int customerId, Address address, String subtotal) {
        this.id = null;
        this.customerId = customerId;
        this.createdDate = null;
        this.value = subtotal;
        this.status = 0;
        this.items = new ArrayList<OrderItem>();
        this.address = address;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }
    
    public ArrayList<OrderItem> getItems() {
        return items;
    }
    
    public void addOrderItem(OrderItem item) {
     	this.items.add(item);
    }
    
    public String getStatusText() {
        switch (status)
        {
            case 2: 
                return "Delivered";
            case 1:
                return "Shipped";
            case 0:
            default:
                return "Pending";
            case -1:
                return "Canceled";
        }
    }
}
