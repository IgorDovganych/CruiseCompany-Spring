package commands;

import exception.DaoException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
        request.setAttribute("id",request.getParameter("id"));
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("role", request.getParameter("role"));
        return "/WEB-INF/pages/update_user_page.jsp";
    }
}
