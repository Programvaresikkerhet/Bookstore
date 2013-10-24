package amu.action;


import java.util.List;

import amu.database.AddressDAO;
import amu.database.BookDAO;
import amu.database.CreditCardDAO;
import amu.database.OrderDAO;
import amu.database.ReviewDAO;
import amu.model.Address;
import amu.model.Book;
import amu.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddRateAction implements Action {
	@Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
        
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
 
	        boolean result = bookDAO.RateBook(customer.getId(), Integer.parseInt(request.getParameter("id")),
	        		 		 Integer.parseInt(request.getParameter("rate")));
	        
	        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
	        if (result) {
	            request.setAttribute("book", book);
	             
	        }
        }
         
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}