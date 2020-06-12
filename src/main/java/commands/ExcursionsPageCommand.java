package commands;

import dao.ExcursionDao;

import exception.DaoException;
import model.Excursion;
import model.ExcursionsInPortContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcursionsPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        ExcursionDao excursionDao = DaoFactoryInstance.getFactory().getExcursionDao();
//        List<ExcursionsInPortContainer> excursionsInPortContainers = excursionDao.getAllExcursionsInAllPorts();
//        request.setAttribute("excursionsInPortContainers", excursionsInPortContainers);
        return "/WEB-INF/pages/excursions.jsp";
    }
}
