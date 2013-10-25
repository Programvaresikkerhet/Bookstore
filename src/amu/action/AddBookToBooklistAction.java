package amu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.BookDAO;
import amu.database.BooklistDAO;
import amu.model.Book;
import amu.model.Customer;

public class AddBookToBooklistAction implements Action {

	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBook");
		HttpSession session = request.getSession(true);
		Customer customer = (Customer)session.getAttribute("customer");		
		
		if(request.getParameter("selectedBooklist") != null && customer != null){
			List<String> messages = new ArrayList<String>();
            
			BookDAO bookDAO = new BookDAO();
	        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
			
		    BooklistDAO booklistDAO = new BooklistDAO();
		    boolean alreadyInCustomersBooklist = bookDAO.bookInBooklist(book.getId(), 
		      		Integer.parseInt(request.getParameter("selectedBooklist")));
		       
		    if(alreadyInCustomersBooklist){
		       	//The booklist is already in b
		       	messages.add("This book already exists in the specified booklist!");
		    }
		    else{
		      	//The book is not added to the booklist
		      	booklistDAO.addBookToList(book.getId(), Integer.parseInt(request.getParameter("selectedBooklist")));
		       	messages.add("The book was added to the booklist!");
		    }
	   
	        request.setAttribute("book", book);
	        request.setAttribute("messages", messages);
		}

		
		return actionResponse;
		
	}

}
