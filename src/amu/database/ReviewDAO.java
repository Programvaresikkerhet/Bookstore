package amu.database;

import amu.model.Book;
import amu.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ReviewDAO {
	Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    
    public List<Review> getReviewsForBook(Book book) {
        List<Review> reviews = new ArrayList<Review>();

        try {
            connection = Database.getConnection();
            String query = "SELECT review.review_id, review.review_text, review.reviewed, "
            		 + "SUM(ifnull(likes,0)), SUM(ifnull(dislikes,0)) "
          			 + "FROM  review " 
           			 + "LEFT JOIN review_x_mark ON review.review_id = review_x_mark.review_id "
           			 + "WHERE review.book_id =? "
           			 + "GROUP BY review.review_id";
            
            statement = connection.prepareStatement(query);

            statement.setInt(1, book.getId());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	reviews.add(new Review(resultSet.getInt("review.review_id"), 
            			resultSet.getString("review.review_text"),
            			resultSet.getDate("review.reviewed"), book, 
            			resultSet.getInt("SUM(ifnull(likes,0))"),resultSet.getInt("SUM(ifnull(dislikes,0))")));
            	
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }

        return reviews;
    }

    public boolean SaveReviewLike(int id, int customerId, int likes) {
        try {
        	
        	System.out.println("likes: " + likes);
        	
        	connection = Database.getConnection();
            String query = "SELECT * FROM  review_x_mark " 
           			 + "WHERE review_id=? AND customer_id=?";
            
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, customerId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	return false;
            } else {
            	connection = Database.getConnection();

                query = "INSERT INTO review_x_mark (review_id, customer_id, likes, dislikes) VALUES (?, ?, ?, ?)";
                
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                statement.setInt(2, customerId);
                statement.setInt(3, (likes == 1 ? 1 : 0));
                statement.setInt(4, (likes == 2 ? 1 : 0));

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

    public boolean addReview(String reviewText, int bookId) {

        try {
            connection = Database.getConnection();
            
            String query = "INSERT INTO review (book_id, review_text) VALUES (?, ?)";
             
            statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            statement.setString(2, reviewText);

            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        return false;
    }
  
    
}