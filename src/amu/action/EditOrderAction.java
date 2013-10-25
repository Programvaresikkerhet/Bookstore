package amu.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.OrderDAO;
import amu.model.Order;
import amu.database.AddressDAO;
import amu.model.Address;
import amu.model.Customer;

class EditOrderAction implements Action {

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
	        AddressDAO addressDAO = new AddressDAO();
    		
	        Order order = null;
    		List<Address> addresses = addressDAO.browse(customer);
    		order = orderDAO.getOrder(Integer.parseInt(request.getParameter("id")), customer);
    		
    		if (order == null) {
				return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
    		} else {
 		        request.setAttribute("order", order);
		        request.setAttribute("addresses", addresses);
		        
		        ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "editOrder");
		        return actionResponse;
    		}
	    }
        
        messages.add("An error occured.");
        return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
    }   
    
}