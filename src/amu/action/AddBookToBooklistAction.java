package amu.action;

import java.util.HashMap;
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
		ActionResponse actionResponse = new ActionResponse(ActionResponseType.REDIRECT, "viewBook");
		HttpSession session = request.getSession(true);
		Customer customer = (Customer)session.getAttribute("customer");
	
		System.out.println("SelectedBooklist:" + request.getParameter("selectedBooklist"));
		
		
		if(request.getParameter("selectedBooklist") != null && customer != null){
			//Map<String, String> messages = new HashMap<String, String>();
            //request.setAttribute("messages", messages);
			
			BookDAO bookDAO = new BookDAO();
	        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
	        
	        System.out.println("BookId:" + book.getId());
			
	        BooklistDAO booklistDAO = new BooklistDAO();
	        booklistDAO.addBookToList(book.getId(), Integer.parseInt(request.getParameter("selectedBooklist")));
			
	        //messages.put("added", "The book was added to your booklist!");
		}

		
		return actionResponse;
		
	}

}
