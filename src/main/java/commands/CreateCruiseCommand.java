package commands;

import exception.DaoException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateCruiseCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateCruiseCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        LOGGER.info("CreateCruiseCommand started");
        return "/WEB-INF/pages/cruises.jsp";
    }
}
