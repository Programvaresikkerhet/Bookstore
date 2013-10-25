package amu.model;
import java.io.Serializable;

public class OrderItem implements Serializable {
	private int orderItmId;
    private int bookId;
    private int quantity;
    private String bookTitle;
    private float price;

    public OrderItem(int orderItmId, int bookId, int quantity, float price, String bookTitle)
    {
    	this.orderItmId = orderItmId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.bookTitle = bookTitle;
    }
    
    public OrderItem(){
        this.orderItmId = 0;
        this.bookId = 0;
        this.quantity = 0;
        this.price = 0;
        this.bookTitle = "";
    }
    
    public int getOrderItmId() {
        return orderItmId;
    }

    public void setOrderItmId(int orderItmId) {
        this.orderItmId = orderItmId;
    }
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    void addQuantity(int quantity) {
       this.quantity += quantity;
    }
    
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}