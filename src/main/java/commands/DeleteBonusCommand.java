package commands;

import dao.TicketDao;
import exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBonusCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        String bonusName = request.getParameter("bonusName");
//        TicketDao ticketDao = DaoFactoryInstance.getFactory().getTicketDao();
//        ticketDao.deleteBonusFromTicket(bonusName);
        return "/FinalProjectJavaEE/admin/tickets";
    }
}
