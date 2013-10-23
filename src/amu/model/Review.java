package amu.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

public class Review implements Serializable {
    private int id;
    private String reviewText;
    private Date reviewDate;
    private Book book;
    private int likes;
    private int dislikes; 
    
    public Review(int id, String reviewText, Date reviewDate, Book book, int likes, int dislikes) {
        this.id = id;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.book = book;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
    public Review(int id, String reviewText, Date reviewDate, Book book) {
        this.id = id;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.book = book;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
