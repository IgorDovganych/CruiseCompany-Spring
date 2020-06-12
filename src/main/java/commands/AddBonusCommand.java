package commands;

import dao.TicketDao;

import exception.DaoException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBonusCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddBonusCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        int ticketTypeId = Integer.parseInt(request.getParameter("ticketTypeId"));
//        String bonusName = request.getParameter("bonusName");
//        LOGGER.info("Adding bonus " + bonusName + " to ticketId " + ticketTypeId);
//        TicketDao ticketDao = DaoFactoryInstance.getFactory().getTicketDao();
//        ticketDao.insertBonusToTicket(ticketTypeId,bonusName);
        return "/FinalProjectJavaEE/admin/tickets";
    }
}
