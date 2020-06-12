package commands;


import exception.DaoException;
import model.TicketType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TicketsPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//
//        List<TicketType> ticketTypes = DaoFactoryInstance.getFactory().getTicketDao().getAllTicketTypes();
//        request.setAttribute("ticketTypes", ticketTypes);
        return "/WEB-INF/pages/tickets.jsp";
    }
}
