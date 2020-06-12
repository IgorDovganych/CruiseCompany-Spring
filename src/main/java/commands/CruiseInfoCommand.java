package commands;


import exception.DaoException;
import model.Cruise;
import model.Excursion;
import model.TicketType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CruiseInfoCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CruiseInfoCommand.class);



    //@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
//        int cruiseId = Integer.parseInt(request.getParameter("id"));
//        Cruise cruise = DaoFactoryInstance.getFactory().getCruiseDao().getCruiseById(cruiseId);
//
//        request.setAttribute("cruise", cruise);
//        List<Excursion> allExcursions = DaoFactoryInstance.getFactory().getExcursionDao().getExcursionsByCruiseId(cruiseId);
//        request.setAttribute("excursions", allExcursions);
//        List<TicketType> ticketTypes = DaoFactoryInstance.getFactory().getTicketDao().getAllTicketTypes();
//        request.setAttribute("ticketTypes", ticketTypes);
//        int amountOfPurchasedTickets = DaoFactoryInstance.getFactory().getTicketDao()
//                .getPurchasedTicketsAmountByCruiseId(cruiseId);
//        int availableTicketsAmount = cruise.getShip().getCapacity()-amountOfPurchasedTickets;
//        request.setAttribute("availableTicketsAmount",availableTicketsAmount);
        return "/WEB-INF/pages/cruise_info.jsp";
    }

}
