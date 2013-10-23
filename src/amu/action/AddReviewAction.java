package amu.action;

import amu.database.BookDAO;
import amu.database.ReviewDAO;
import amu.model.Book;
import amu.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class AddReviewAction implements Action {
	@Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
        
        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewBook");
            return actionResponse;
        }
		
        if (request.getParameter("id") != null && request.getParameter("review_text") != null && request.getParameter("isbn") != null)
        {
            ReviewDAO reviewDAO = new ReviewDAO();
            reviewDAO.addReview(request.getParameter("review_text"), Integer.parseInt(request.getParameter("id")));
            
            
            BookDAO bookDAO = new BookDAO();
	        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
	        
	        if (book != null) {
	            request.setAttribute("book", book);
	        }
        }
        
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}