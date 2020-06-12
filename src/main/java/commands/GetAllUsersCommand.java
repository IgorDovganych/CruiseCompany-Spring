package commands;


import exception.DaoException;
import filter.AdminFilter;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllUsersCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        LOGGER.info("Command 'get all users started'");
//        List<User> users = DaoFactoryInstance.getFactory().getUserDao().getAllUsers();
//        request.setAttribute("users", users);
        return "/WEB-INF/pages/users.jsp";
    }
}
