package commands;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandDoesntExist implements Command {
    private static final Logger LOGGER = Logger.getLogger(CommandDoesntExist.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("command doesn't exist- " + request.getServletPath());
        return "/CruiseCompany";
    }
}
