package amu.action;

import amu.database.BookDAO;
import amu.model.Book;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import amu.database.BooklistDAO;
import amu.model.Booklist;
import amu.model.Customer;
import javax.servlet.http.HttpSession;

class ViewBookAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        ActionResponse actionResponse = new ActionResponse(ActionResponseType.FORWARD, "viewBook");
    	
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findByISBN(request.getParameter("isbn"));
        
        BooklistDAO booklistDAO = new BooklistDAO();
        List<Booklist> list = booklistDAO.findBooklistByCustomer(customer);
        
        if(book != null) {
        	actionResponse.addParameter("isbn", request.getParameter("isbn"));
            session.setAttribute("book", book);
            session.setAttribute("booklist", list);
        }
        
        if(customer != null){
            session.setAttribute("customer", customer);
        }
        
        
        return actionResponse;
    }
}
