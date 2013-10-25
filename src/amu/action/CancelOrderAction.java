package amu.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.OrderDAO;
import amu.model.Customer;


class CancelOrderAction implements Action {

    @Override
    
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewCustomer");
            return actionResponse;
        }
        
        List<String> messages = new ArrayList<String>();
        request.setAttribute("messages", messages);
        
        if (request.getParameter("id") != null){
	        OrderDAO orderDAO = new OrderDAO();
	        
	        if (orderDAO.cancelOrder(Integer.parseInt(request.getParameter("id")))){
		        ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "cancelOrderSuccessful");
		        return actionResponse;
	        } else {
	        	ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "cancelOrderError");
		        return actionResponse;
		    }
	    }
        
        messages.add("An error occured.");
        return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
    }   
    
}
