package amu.action;


import java.util.ArrayList;
import java.util.List;

import amu.database.AddressDAO;
import amu.database.BookDAO;
import amu.database.CreditCardDAO;
import amu.database.OrderDAO;
import amu.database.ReviewDAO;
import amu.model.Address;
import amu.model.Book;
import amu.model.Customer;
import amu.model.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddRateAction implements Action {
	@Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
        
        ArrayList<String> messages = new ArrayList<String>();
        request.setAttribute("messages", messages);
        
        if (customer == null) {
        	
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            
            actionResponse.addParameter("from", "viewBook");
            actionResponse.addParameter("book_isbn", "46");
            
            return actionResponse;
        }
        
        if (request.getParameter("id") != null && request.getParameter("rate") != null && 
        									      request.getParameter("isbn") != null)
        {            
        	BookDAO bookDAO = new BookDAO();
        	
        	if(!Validation.validateInt(request.getParameter("rate"))){
        		messages.add("An error occurred.");
        		return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
        	}
        	
        	int rating = Integer.parseInt(request.getParameter("rate"));
        	
        	if(Validation.validateRating(rating)){
 
		        boolean result = bookDAO.RateBook(customer.getId(), Integer.parseInt(request.getParameter("id")),
		        		 		 rating);
		        
		        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
		        if (result) {
		            request.setAttribute("book", book);
		        }
        	} else{
        		messages.add("An error occurred while trying to add rating.");
        		return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
        	}
        }
         
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}