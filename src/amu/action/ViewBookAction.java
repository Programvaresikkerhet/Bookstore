package amu.action;

import java.util.ArrayList;
import java.util.List;

import amu.database.BookDAO;
import amu.database.BooklistDAO;
import amu.model.Book;
import amu.model.Booklist;
import amu.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class ViewBookAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession(true);
        Customer customer = (Customer) session.getAttribute("customer");
    	
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
        
        BooklistDAO booklistDAO = new BooklistDAO();
        List<Booklist> list = booklistDAO.findBooklistByCustomer(customer);
        
        if(book != null) {
            request.setAttribute("book", book);
            request.setAttribute("booklist", list);
        }
        
        return new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    }
}
