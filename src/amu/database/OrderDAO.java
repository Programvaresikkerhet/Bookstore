package amu.database;

import amu.model.Customer;
import amu.model.Order;
import amu.model.Address;
import amu.model.Cart;
import amu.model.CartItem;
import amu.model.OrderItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    
    public List<Order> browse(Customer customer) {
        List<Order> orders = new ArrayList<Order>();

        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM `order` WHERE customer_id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, customer.getId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AddressDAO addressDAO = new AddressDAO();
                Calendar createdDate = Calendar.getInstance();
                createdDate.setTime(resultSet.getDate("created"));
                orders.add(new Order(resultSet.getInt("id"),
                        customer.getId(), 
                        createdDate, 
                        resultSet.getString("value"), 
                        resultSet.getInt("status"),
                        getOrderItems(resultSet.getInt("id")),
                        addressDAO.read(resultSet.getInt("address_id"),customer)));
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }

        return orders;
    }
    
    public Order getOrder(int orderId, Customer customer) {
    	Order order = new Order();
        try {
        	connection = Database.getConnection();
            String query = "SELECT * FROM `order` WHERE id = ? AND customer_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.setInt(2, customer.getId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AddressDAO addressDAO = new AddressDAO();
                Calendar createdDate = Calendar.getInstance();
                createdDate.setTime(resultSet.getDate("created"));
                order = new Order(resultSet.getInt("id"),
                				resultSet.getInt("customer_id"), 
		                        createdDate, 
		                        resultSet.getString("value"), 
		                        resultSet.getInt("status"),
		                        getOrderItems(resultSet.getInt("id")),
		                        addressDAO.read(resultSet.getInt("address_id"),customer));
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSet);
        }
        return order;
    }
    
    public boolean cancelOrder(int orderId) {
        try {
            connection = Database.getConnection();
            String query = "UPDATE `order` SET status=-1 WHERE id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);

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
    
    public boolean editOrder(int order_id, int shippingAddress, Map<Integer, Integer> orderItmQty,Customer customer) {
        try {
        	Order order = new Order();
        	OrderDAO orderDAO = new OrderDAO();
    		order = orderDAO.getOrder(order_id, customer);
        	
    		//cancel
        	boolean result = cancelOrder(order_id);
        	
        	if (result){
        		float value = 0;
        		
        		if (order.getAddress().getId() != shippingAddress){
        			AddressDAO addressDAO = new AddressDAO();
        			Address addr = addressDAO.read(shippingAddress, customer);
        			order.setAddress(addr);
        		}
        		
        		//update items
        		for (OrderItem orderItem : order.getItems()) {
        			orderItem.setQuantity(orderItmQty.get(orderItem.getOrderItmId()));
        			value = value + orderItem.getQuantity()*orderItem.getPrice();
             	}
        		
        		//updateValue
        		order.setValue(String.valueOf(value));
        		
        		//recreate
        		result = orderDAO.add(order);
        		
        		if (result){
        			return true;
        		} else {
        			return false;
        		}
	        } else {
	        	return false;
	        }
        } finally {
            Database.close(connection, statement, resultSet);
        }
    }
    
    public ArrayList<OrderItem> getOrderItems(int orderId){
    	ResultSet resultSetOrd = null;
    	ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    	
    	try {
            connection = Database.getConnection();
            String query = "SELECT order_item_id, book_id, SUM(quantity),order_items.price, title.name " 
            			 + "FROM order_items "
            			 + "INNER JOIN book ON order_items.book_id = book.id "
            			 + "INNER JOIN title ON book.title_id = title.id "
            			 + "WHERE order_id =? GROUP BY book_id";
            
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            resultSetOrd = statement.executeQuery();

            while (resultSetOrd.next()) {
                OrderItem itm = new OrderItem(resultSetOrd.getInt("order_item_id"),resultSetOrd.getInt("book_id"),
                		resultSetOrd.getInt("SUM(quantity)"), resultSetOrd.getFloat("order_items.price"),
                		resultSetOrd.getString("title.name"));

                orderItems.add(itm);
            }
        } catch (SQLException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
        } finally {
            Database.close(connection, statement, resultSetOrd);
        }
        return orderItems;
    }
        
    public boolean add(Order order) {

        try {
            connection = Database.getConnection();

            String query = "INSERT INTO `order` (customer_id, address_id, created, value, status) VALUES (?, ?, CURDATE(), ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getCustomerId());
            statement.setInt(2, order.getAddress().getId());
            statement.setBigDecimal(3, new BigDecimal(order.getValue()));
            statement.setInt(4, order.getStatus());
            statement.executeUpdate();
            
            resultSet = statement.getGeneratedKeys();
            
            if (resultSet.next()) {
            	int orderId = resultSet.getInt(1);
            	for (OrderItem orderItem : order.getItems()) {
            		query = "INSERT INTO `order_items` (order_id, book_id, quantity, price, status) "
               		     + "VALUES (?, ?, ?, ?, ?)";
		            statement = connection.prepareStatement(query);
		            statement.setInt(1, orderId);
		            statement.setInt(2, orderItem.getBookId());
		            statement.setInt(3, orderItem.getQuantity());
		            statement.setFloat(4, orderItem.getPrice());
		            statement.setInt(5, 0);
                    
		            if (statement.executeUpdate() <= 0) {
                        return false;
                    }
            	}
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
