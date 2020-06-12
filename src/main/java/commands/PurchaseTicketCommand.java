package commands;

import dao.CruiseDao;
import dao.ExcursionDao;
import dao.TicketDao;
import dao.jdbc.CruiseDaoJdbc;
import dao.jdbc.InsideTransactionProcessor;
import dao.jdbc.TransactionManager;
import exception.DaoException;
import model.Cruise;
import model.Excursion;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class PurchaseTicketCommand implements Command {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(PurchaseTicketCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
//        LOGGER.info("execution of PurchaseTicketCommand started");
//        User user = (User) request.getSession().getAttribute("user");
//        int cruiseId = parseInt(request.getParameter("cruiseId"));
//        int ticketTypeId = parseInt(request.getParameter("ticketId"));
//        String excursionIdsString = request.getParameter("excursionIds");
//        LOGGER.info("length of excursion ids string from request is " + excursionIdsString.length());
//
//        List<Integer> excursionIds = Arrays.stream(request.getParameter("excursionIds").split(","))
//                .filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(toList());
//
////        List<Integer> excursionIds = Arrays.stream(request.getParameter("excursionIds").split(","))
////                .map(Integer::parseInt).collect(toList());
//
//        CruiseDao cruiseDao = DaoFactoryInstance.getFactory().getCruiseDao();
//        TicketDao ticketDao = DaoFactoryInstance.getFactory().getTicketDao();
//        Cruise cruise = cruiseDao.getCruiseById(cruiseId);
//        int amountOfPurchasedTickets = ticketDao.getPurchasedTicketsAmountByCruiseId(cruiseId);
//        if (amountOfPurchasedTickets >= cruise.getShip().getCapacity()) {
//            request.setAttribute("noTicketsMessage","Ticket has been purchased by another user, sorry");
//            request.setAttribute("cruise",cruise);
//            return "/WEB-INF/pages/cruise_info.jsp";
//        }
//
//        ExcursionDao excursionDao = DaoFactoryInstance.getFactory().getExcursionDao();
//
//        TransactionManager.doInTransaction(connection -> {
//            int purchasedTicketId = ticketDao.purchaseTicket(connection, user.getId(), cruiseId, ticketTypeId);
//            excursionDao.purchaseExcursions(connection, excursionIds, purchasedTicketId);
//        });

        return "/FinalProjectJavaEE/user_account_info";
    }
}