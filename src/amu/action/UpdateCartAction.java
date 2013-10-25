package amu.action;

import java.util.ArrayList;

import amu.database.BookDAO;
import amu.model.Book;
import amu.model.Cart;
import amu.model.CartItem;
import amu.model.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class UpdateCartAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String[] isbn = request.getParameterValues("isbn");
        String[] quantity = request.getParameterValues("quantity");

        if (isbn != null && quantity != null && isbn.length == quantity.length) {
            
            for (int i = 0; i < isbn.length; i++) {
            	ArrayList<String> messages = new ArrayList<String>();
            	session.setAttribute("messages", messages);
            	
                CartItem item = cart.getItemByISBN(isbn[i]);
                
                if(!Validation.validateInt(request.getParameter(quantity[i]))){
                	messages.add("An error occurred.");
                	return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
                }
                
                if (item == null) {
                    BookDAO bookDAO = new BookDAO();
                    Book book = bookDAO.findByISBN(isbn[i]);
                    
                    if(!Validation.validateInt(request.getParameter("quantity"))){
                    	messages.add("An error occurred.");
                    	return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
                    }
                    
                    int _quantity = Integer.parseInt(request.getParameter("quantity"));
                    if(_quantity < 0){
                    	messages.add("Quantity cannot be negative.");
                    	return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
                    }
                   	cart.addItem(new CartItem(book, _quantity));
                } else {

                    int newQuantity = Integer.parseInt(request.getParameter(quantity[i]));
                    if(newQuantity < 0){
                    	messages.add("Quantity cannot be negative.");
                    	return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
                    }
                    item.setQuantity(Integer.parseInt(quantity[i]));
                    cart.updateItem(item);
                }
            }
        }

        return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
    }
}
