package commands;


import exception.DaoException;
import model.Cruise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllCruisesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        List<Cruise> cruises = DaoFactoryInstance.getFactory().getCruiseDao().getAllCruises();
//        request.setAttribute("cruises",cruises);
        return "/WEB-INF/pages/cruises.jsp";
    }
}
