package commands;

import exception.DaoException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        String link = request.getParameter("link");
        String parameters = request.getParameter("parameters");
        if (link.equals("")) {
            link = "/FinalProjectJavaEE";
        }
        if (link.equals("/Airline/login")) {
            link = "/Airline/login_page";
        }
        if (link.equals("/Airline/registration")) {
            link = "/Airline/registration_page";
        }
        link += parameters.equals("") ? "" : ("?" + parameters);	//creating link with parameters for redirecting
        String language = request.getParameter("lang");
        request.getSession().setAttribute("language", language);
        LOGGER.info("link - " + link + "  language - " + language);
        return link;
    }
}
