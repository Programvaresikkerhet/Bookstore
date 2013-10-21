package amu.action;

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
        ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBook");
        session.setAttribute("customer", customer);
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
        
        if(book != null) {
        	actionResponse.addParameter("isbn", request.getParameter("isbn"));
            session.setAttribute("book", book);
            
        } 
        
        if(customer != null){
        	BooklistDAO booklistDAO = new BooklistDAO();
        	List<Booklist> list = booklistDAO.findBooklistByCustomer(customer);
        	session.setAttribute("booklist", list);     
        }
        
        
         
        
        return actionResponse;
    }
}
