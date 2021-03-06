package amu.database;

import amu.model.Book;
import amu.model.Publisher;
import amu.model.Title;

import java.sql.*;
import java.util.logging.*;

public class BookDAO {
    public Book findByISBN(String isbn) {
        Book book = null;
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = Database.getConnection();
            
            String query = "SELECT * FROM book, publisher, title "
            		+ "WHERE book.isbn13 = ? "
            		+ "AND book.title_id = title.id "
            		+ "AND book.publisher_id = publisher_id;";
            
            statement = connection.prepareStatement(query);
            
            statement.setString(1, isbn);
            resultSet = statement.executeQuery();
            
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, "findByISBN SQL Query: " + query);
            
            if (resultSet.next()) {
                AuthorDAO authorDAO = new AuthorDAO(); 
                ReviewDAO reviewDAO = new ReviewDAO();
                book = new Book();
                book.setId(resultSet.getInt("book.id"));
                book.setTitle(new Title(resultSet.getInt("title.id"), resultSet.getString("title.name")));
                book.setPublisher(new Publisher(resultSet.getInt("publisher.id"), resultSet.getString("publisher.name")));
                book.setPublished(resultSet.getString("book.published"));
                book.setEdition(resultSet.getInt("book.edition"));
                book.setBinding(resultSet.getString("book.binding"));
                book.setIsbn10(resultSet.getString("book.isbn10"));
                book.setIsbn13(resultSet.getString("book.isbn13"));
                book.setDescription(resultSet.getString("book.description"));
                book.setAuthor(authorDAO.findByBookID(resultSet.getInt("book.id")));
                book.setPrice(resultSet.getFloat("book.price"));
                book.setAverageRate(getRateForBook(resultSet.getInt("book.id")));
                book.setReviews(reviewDAO.getReviewsForBook(book));
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        
        return book;
    }
    public float getRateForBook(int bookId) {
    	float rate;
    	    	
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    	
        try {
            connection = Database.getConnection();
            
            String query = "SELECT ifnull(AVG(rate),0) FROM ratebook_x_customer WHERE book_id=?";
             
            statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            
            resultSet = statement.executeQuery();
            
           
            if (resultSet.next()) {
                rate = resultSet.getFloat("ifnull(AVG(rate),0)");
                
                rate =  (float) (Math.round(rate*100.0)/100.0);
            	return rate;
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        return 0;
    }
    
    public boolean RateBook(int customerId, int bookId, int rate) {
    	
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    	
        try {
            connection = Database.getConnection();
            
            String query = "UPDATE ratebook_x_customer SET rate=? WHERE book_id=? AND customer_id=?";
            
            statement = connection.prepareStatement(query);
            
            statement.setInt(1, rate);
            statement.setInt(2, bookId);
            statement.setInt(3, customerId);

            if (statement.executeUpdate() > 0) {
                return true;
            } else {
            	
            	query = "INSERT INTO ratebook_x_customer (book_id, customer_id, rate) VALUES (?, ?, ?)";
                
            	
            	statement.close();
            	
                statement = connection.prepareStatement(query);
                statement.setInt(1, bookId);
                statement.setInt(2, customerId);
                statement.setInt(3, rate);

                if (statement.executeUpdate() > 0) {
                    return true;
                }
            }
            
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        return false;
    }
    public boolean bookInBooklist(int bookId, int booklistId) {
     	    	
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    	
        try {
            connection = Database.getConnection();
            //book_id, list_id
            String query = "SELECT * FROM book_x_list WHERE book_id = ? AND list_id = ?";
             
            statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            statement.setInt(2, booklistId);
            
            resultSet = statement.executeQuery();
           
            if (resultSet.next()) {
                return true;
            }
            else{
            	return false;
            }
            
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        
        return false;
    }
    
    
}
