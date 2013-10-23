package amu.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amu.model.*;

public class BooklistDAO {
	
	public List<Book> findBooksInBooklist(int id, Customer customer){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try{
			List<Book> list = new ArrayList<Book>();
			connection = Database.getConnection();
			String query = "SELECT title.name FROM book, book_x_list, title "
					+ "WHERE book.id = book_x_list.book_id "
					+ "AND book.title_id = title.id " 
					+ "AND book_x_list.list_id = ?;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
            resultSet = statement.executeQuery();
            
            
            //TODO: Fix the object 
            //TODO: FIx the query so I can set Author and title!!!
            
            while(resultSet.next()){
            	Book book = new Book();
            	Title title = new Title();
            	title.setName(resultSet.getString("title.name"));
            	book.setTitle(title);
            	list.add(book);
            }
            
            return list;
			
            
		}
		catch(SQLException exception){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		}
		finally{
			Database.close(connection, statement);
		}
		
		return null;
	}
	
	public boolean addBookToList(int bookId, int listId){
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		
		try{
			connection = Database.getConnection();
			String query = "INSERT INTO book_x_list(book_id, list_id) VALUES(?, ?);";
			statement = connection.prepareStatement(query);
			
			statement.setInt(1, bookId);
			statement.setInt(2, listId);
			
			if (statement.executeUpdate() > 0) {
                return true;
            }
		}
		catch(SQLException exception){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		}
		finally{
			Database.close(connection, statement);
		}
		
		return false;
	}
	public List<Booklist> findBooklistByCustomer(Customer cust){
		List<Booklist> booklist = new ArrayList<Booklist>();
		
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
		Customer customer = cust;
		
		try{
			connection = Database.getConnection();
            String query = 	"SELECT list.title, list.description, list.id FROM list_x_customer, list "
            		+ "WHERE list.id = list_x_customer.id_list AND id_customer=?";
            
            statement = connection.prepareStatement(query);
            statement.setInt(1, customer.getId());
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
            	booklist.add(new Booklist(resultSet.getInt("id"), resultSet.getString("title"), 
            			resultSet.getString("description") , customer));
            }            
		}

		catch(SQLException exception){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);

		}
		finally{
			Database.close(connection, statement, resultSet);
		}
		
		return booklist;
	}
	
	public boolean addBooklist(String title, String description, Customer customer){
		PreparedStatement statement = null;
		Connection connection = Database.getConnection();

		try{
			
			String query = "INSERT INTO list (title, description) VALUES('"+ title +"' , '"+ description+"');";
			
			statement = connection.prepareStatement(query);
			
			
			if(statement.executeUpdate()>0){
				String query2 = "INSERT INTO list_x_customer (id_list, id_customer) VALUES (LAST_INSERT_ID()," + customer.getId() +");";
				statement.close();
				statement = connection.prepareStatement(query2);
				if(statement.executeUpdate()>0){
					return true;
				}		
			}
		}
		catch(SQLException exception){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		}
		finally{
			Database.close(connection, statement);
		}
		
		return false;
	}
	
	public List<Booklist> findAllBooklists(){
		List<Booklist> list = new ArrayList<Booklist>();
		
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    
		try{
			connection = Database.getConnection();
            String query = 	"SELECT id, title, description FROM list;";
            
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
            	Booklist booklist  = new Booklist();
            	booklist.setId(resultSet.getInt("id"));
            	booklist.setTitle(resultSet.getString("title"));
            	booklist.setDescription(resultSet.getString("description"));
            	list.add(booklist);
            }
		}

		catch(SQLException exception){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);

		}
		finally{
			Database.close(connection, statement, resultSet);
		}
		
		return list;

	}
}
