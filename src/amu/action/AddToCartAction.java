package amu.action;

import java.util.ArrayList;

import amu.database.BookDAO;
import amu.model.Book;
import amu.model.Cart;
import amu.model.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddToCartAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        if (request.getParameter("isbn") != null && request.getParameter("quantity") != null)
        {
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.findByISBN(request.getParameter("isbn"));
            
            ArrayList<String> messages = new ArrayList<String>();
            session.setAttribute("messages", messages);
            
            //We don't want to add negative quantities to our cart!
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            if(quantity <= 0){
            	messages.add("Quantity must be 1 or higher.");
            	ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBook");
            	actionResponse.addParameter("isbn", request.getParameter("isbn"));
            	return actionResponse;
            }
            
            cart.addItem(new CartItem(book, quantity));
        }

        return new ActionResponse(ActionResponseType.REDIRECT, "viewCart");
    }
    
}
