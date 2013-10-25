package amu.action;

import java.util.ArrayList;

import amu.Config;
import amu.Mailer;
import amu.model.Customer;
import amu.model.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class CustomerSupportAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
            actionResponse.addParameter("from", "customerSupport");
            return actionResponse;
        }

        if (request.getMethod().equals("POST")) {
        	
        	ArrayList<String> messages = new ArrayList<String>();
        	request.setAttribute("messages",  messages);
            
            String subject = Validation.sanitizeInput(request.getParameter("subject"));
            String content = Validation.sanitizeInput(request.getParameter("content"));
            String fromAddr = Validation.sanitizeInput(request.getParameter("fromAddr"));
            String fromName = Validation.sanitizeInput(request.getParameter("fromName"));
            
            if(!Validation.validateStringLength(subject, 255)){
            	messages.add("Subject too long.");
            	return new ActionResponse(ActionResponseType.FORWARD, "customerSupport");
            }
            
            Mailer.send(Config.EMAIL_FROM_ADDR, subject, content, fromAddr, fromName);
            // TODO: Send receipt to customer
            return new ActionResponse(ActionResponseType.REDIRECT, "customerSupportSuccessful");
        } 

        return new ActionResponse(ActionResponseType.FORWARD, "customerSupport");
    }
}
