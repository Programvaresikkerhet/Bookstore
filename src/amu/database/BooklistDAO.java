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
	
	public boolean addBookToList(int bookId, int listId){
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		
		try{
			connection = Database.getConnection();
			String query = "INSERT INTO book_x_list(book_id, list_id) VALUES('"
					+ bookId + "', '" + listId + "')";
			statement = connection.prepareStatement(query);
			
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
}
