package amu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amu.Mailer;
import amu.database.CustomerDAO;
import amu.model.Customer;
import amu.model.Validation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class RegisterCustomerAction extends HttpServlet implements Action {
    
    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (request.getMethod().equals("POST")) {
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.findByEmail(request.getParameter("email"));

            ArrayList<String> messages = new ArrayList<String>();
            request.setAttribute("messages", messages);
            
            if (customer == null) {
            	if(Validation.validatePassword(request.getParameter("password"))){
            		customer = new Customer();
            		
            		String email = Validation.sanitizeInput(request.getParameter("email"));
            		if(!Validation.validateEmail(email)){
            			messages.add("Please enter a valid e-mail address.\n");
            			return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
            		}
            		
            		String name = request.getParameter("name");
            		if(!Validation.validateStringLength(name, 255)){
            			messages.add("Name is too long.");
            			return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
            		}
	                
	                customer.setEmail(email);
	                customer.setName(Validation.sanitizeInput(name));
	                customer.setActivationToken(CustomerDAO.generateActivationCode());
	                customer = customerDAO.register(customer);
	                
	                customer.setPassword(CustomerDAO.hashPassword(request.getParameter("password")));
	                
	                ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "activateCustomer");
	                actionResponse.addParameter("email", customer.getEmail());
	                
	                StringBuilder sb = new StringBuilder();
	                sb.append("Welcome to Amu-Darya, the really apprehensive bookstore!\n\n");
	                sb.append("To activate your account, click <a href='http://");
	                sb.append(request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
	                sb.append(actionResponse.getURL() + actionResponse.getParameterString());
	                sb.append("&activationToken=" + customer.getActivationToken());
	                sb.append("'>here</a>, or use this activation code: " + customer.getActivationToken());
	               
	                Mailer.send(customer.getEmail(), "Activation required", sb.toString());
 
	                return actionResponse;
            	} else{
            		messages.add(Validation.getIssues());
            		return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
            	}
            } else {
                return new ActionResponse(ActionResponseType.REDIRECT, "registrationError");
            }
        }
        
        // Else we show the register form
        return new ActionResponse(ActionResponseType.FORWARD, "registerCustomer");
    }
}
