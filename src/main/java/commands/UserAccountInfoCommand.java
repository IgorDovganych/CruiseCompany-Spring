package commands;

import dao.CruiseDao;
import dao.TicketDao;
import dao.jdbc.CruiseDaoJdbc;
import exception.DaoException;
import model.Cruise;
import model.PurchasedTicket;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAccountInfoCommand implements Command {

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserAccountInfoCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        TicketDao ticketDao = DaoFactoryInstance.getFactory().getTicketDao();
//        User user = (User) request.getSession().getAttribute("user");
//        long userId = user.getId();
//        List<PurchasedTicket> purchasedTicketsForUser= ticketDao.getPurchasedTicketsForUserByUserId(userId);
//
//        LOGGER.info("Purchased ticket" + purchasedTicketsForUser.toString());
//        CruiseDao cruiseDaoDao = DaoFactoryInstance.getFactory().getCruiseDao();
//
//        HashMap<Integer, Cruise> idCruiseHashMap = cruiseDaoDao.getCruisesByIdsInHashMap();
//
//        for (PurchasedTicket purchasedTicket:purchasedTicketsForUser) {
//            Cruise cruise = idCruiseHashMap.get(purchasedTicket.getCruiseId());
//            purchasedTicket.setCruise(cruise);
//        }
//        request.setAttribute("purchasedTicketsForUser",purchasedTicketsForUser);
        return "/WEB-INF/pages/user_account_info.jsp";
    }
}
