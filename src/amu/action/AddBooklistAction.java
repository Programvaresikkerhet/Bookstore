package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.BooklistDAO;
import amu.model.Customer;
import amu.model.Validation;

public class AddBooklistAction implements Action{

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");		
        ActionResponse actionResponse = null;
        
        if(customer == null){
        	actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewBooklist");
            return actionResponse;
        }
        
        else{
        	actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "viewBooklists");
        	BooklistDAO boolistDAO = new BooklistDAO();
        	
        	String title = Validation.sanitizeInput(request.getParameter("booklistTitle"));
        	String description = Validation.sanitizeInput(request.getParameter("booklistDescription"));
        	
        	boolistDAO.addBooklist(title, description, customer);
        	
        	System.out.println("Title: " + title + " Description: " + description);
        	
        }
        
        
        return actionResponse;
	}
	

}
