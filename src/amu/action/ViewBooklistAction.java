package amu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.BooklistDAO;
import amu.model.Book;
import amu.model.Customer;

public class ViewBooklistAction implements Action{

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
                
        if(request.getParameter("id")!=null){
        	
        	ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBooklist");
        	actionResponse.addParameter("id", request.getParameter("id"));
        	BooklistDAO booklistDAO = new BooklistDAO();
        	List<Book> books = booklistDAO.findBooksInBooklist(Integer.parseInt(request.getParameter("id")), customer);
        	session.setAttribute("books", books);
        	
        	return actionResponse;
        }
		
		return new ActionResponse(ActionResponseType.REDIRECT, "loginCustomer");
	}
	
	
}
