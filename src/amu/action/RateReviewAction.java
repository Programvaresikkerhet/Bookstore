
package amu.action;

import amu.database.BookDAO;
import amu.database.ReviewDAO;
import amu.model.Book;
import amu.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class RateReviewAction implements Action {
	@Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
               
        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewBook");
            return actionResponse;
        }
		
        System.out.println(request.getParameter("id"));
        System.out.println(request.getParameter("likes"));
        System.out.println("blablabla");
        System.out.println(request.getParameter("dislikes"));
        System.out.println(request.getParameter("isbn"));
        
        if (request.getParameter("id") != null && request.getParameter("isbn") != null)
        {
	        ReviewDAO reviewDAO = new ReviewDAO();
	        
	        if (request.getParameter("likes") != null){
	        	reviewDAO.SaveReviewLike(Integer.parseInt(request.getParameter("id")), customer.getId(), 1);
	        	
	        	
	        } else if (request.getParameter("dislikes") !=  null) {
	        	reviewDAO.SaveReviewLike(Integer.parseInt(request.getParameter("id")), customer.getId(), 2);
	        	
	        } else {
	        	
	        	
	        }
	        
	        
	        
	        
	        BookDAO bookDAO = new BookDAO();
	        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
	        
	        if (book != null) {
	            request.setAttribute("book", book);
	             
	        }
        }
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}