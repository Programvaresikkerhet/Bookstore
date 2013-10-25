package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.OrderDAO;
import amu.model.Customer;

import java.util.HashMap;
import java.util.Map;


class SaveOrderAction implements Action {

    @Override
    
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "viewCustomer");
            return actionResponse;
        } else {
        	if (request.getParameter("orderId") == null) {
    			return new ActionResponse(ActionResponseType.FORWARD, "editOrderError");
	        } else {
	        	
        		// Save or cancel
        		if (request.getParameter("action") == null) {
        			return new ActionResponse(ActionResponseType.FORWARD, "editOrderError");
        		} else {
        			if (request.getParameter("action").equals("Save")) {
        				if (request.getParameterMap().get("quantity") == null || request.getParameterMap().get("orderItemId") == null ||
        						request.getParameter("shippingAddress") == null) {
        					return new ActionResponse(ActionResponseType.FORWARD, "editOrderError");
        				} else {
	        				// Do saving...
	        				String[] quantities = request.getParameterMap().get("quantity");
	        				String[] orderItemIDs = request.getParameterMap().get("orderItemId");
	        				
	        				try {
	        					int shippingAddress = Integer.parseInt(request.getParameter("shippingAddress"));
	        					
	        					if (quantities.length != orderItemIDs.length) {
	        						return new ActionResponse(ActionResponseType.FORWARD, "editOrderError");
    	        				} else {
    	        					Map<Integer, Integer> orderItemsQuantity = new HashMap<Integer, Integer>();
    	        					
    	        					for (int i = 0; i < quantities.length; i++) {
    	        						try {
    	        							int quantity = Integer.parseInt(quantities[i]);
    	        							int itemId = Integer.parseInt(orderItemIDs[i]);
    	        							
    	        							orderItemsQuantity.put(itemId, quantity);
    	        						} catch (NumberFormatException ex) {
    	        							continue;
    	        						}
    	        					}
    	        					OrderDAO orderDAO = new OrderDAO();
    	        					orderDAO.editOrder(Integer.parseInt(request.getParameter("orderId")), shippingAddress, orderItemsQuantity, customer);
    	        					
    	        					return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
    	        				}
	        				} catch (NumberFormatException ex) {
	        					return new ActionResponse(ActionResponseType.FORWARD, "editOrderError");
	        				}
        				}
        			} else {
        				//cancel
        				return new ActionResponse(ActionResponseType.REDIRECT, "viewCustomer");
        			}            			
         		}
	        
	        }
        }
    }   
    
}