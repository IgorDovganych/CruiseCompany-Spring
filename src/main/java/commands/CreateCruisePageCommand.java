package commands;

import dao.CruiseDao;
import exception.DaoException;
import model.Port;
import model.Ship;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateCruisePageCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateCruisePageCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        LOGGER.info("CreateCruisePageCommand started");
//        CruiseDao cruiseDao = DaoFactoryInstance.getFactory().getCruiseDao();
//        List<Ship> ships = cruiseDao.getAllShips();
//        request.setAttribute("ships",ships);
//        List<Port> ports = cruiseDao.getAllPorts();
//        request.setAttribute("ports",ports);

        return "/WEB-INF/pages/create_cruise.jsp";

    }
}
