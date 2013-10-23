package amu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Booklist implements Serializable{
	
	private Integer id;
	private String title;
	private String description;
	private Customer customer;
	private List<Book> list;
	
	//To make a new book list
	public Booklist(int id, String title, String description, Customer customer){
		this.id = id;
		this.title = title;
		this.description = description;
		this.customer = customer;
		this.list = new ArrayList<Book>();
	}
	public Booklist(){
		
	}

	public void addBook(Book book){
		this.list.add(book);
	} 
	
	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<Book> getBooklist() {
		return list;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	
	
	
	
	
	

}
