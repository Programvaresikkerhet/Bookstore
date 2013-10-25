package amu.action;

import java.util.ArrayList;

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
        
        ArrayList<String> messages = new ArrayList<String>();
        request.setAttribute("messages",  messages);

    	String title = Validation.sanitizeInput(request.getParameter("booklistTitle"));
    	String description = Validation.sanitizeInput(request.getParameter("booklistDescription"));
        
        if(customer == null){
        	actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewBooklists");
            return actionResponse;
        }
        
        else{
        	if(Validation.validateStringLength(title, 50)){
        		if(Validation.validateStringLength(description, 100)){
		        	actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "viewBooklists");
		        	BooklistDAO boolistDAO = new BooklistDAO();
		        	
		        	boolistDAO.addBooklist(title, description, customer);
		        	
		        	System.out.println("Title: " + title + " Description: " + description);
        		} else{
        			messages.add("Description can contain a maximum of 100 characters.");
        			return new ActionResponse(ActionResponseType.FORWARD, "viewBooklists");
        		}
        	} else{
        		messages.add("Title can contain a maximum of 50 characters.");
        		return new ActionResponse(ActionResponseType.FORWARD, "viewBooklists");
        	}
        	
        }
        
        
        return actionResponse;
	}
	

}
