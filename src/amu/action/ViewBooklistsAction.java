package amu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import amu.database.BookDAO;
import amu.database.BooklistDAO;
import amu.model.Book;
import amu.model.Booklist;
import amu.model.Customer;

public class ViewBooklistsAction implements Action {
	
	@Override
	public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		
        Customer customer = (Customer) session.getAttribute("customer");
        ActionResponse actionResponse = null;

        System.out.println("jeg kom meg inn i booklists");

        actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBooklists");
        BooklistDAO booklistDAO = new BooklistDAO();
        List<Booklist> list = booklistDAO.findAllBooklists();
                    
        session.setAttribute("booklist", list);
        session.setAttribute("customer", customer);
        
        return actionResponse;
		
	}
}
