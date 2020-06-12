package servlet;

import dao.*;
import exception.DaoException;
import model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.PasswordHash;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {
    Logger LOGGER = Logger.getLogger(AdminController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    ExcursionDao excursionDao;

    @Autowired
    CruiseDao cruiseDao;

    @Autowired
    PortDao portDao;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getAllUsers(HttpServletRequest request) {
        request.setAttribute("users", userDao.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/admin/update_user_page", method = RequestMethod.GET)
    public String updateUserPage(HttpServletRequest request){
        long id = Integer.parseInt(request.getParameter("id"));
        User user = userDao.getUserById(id);
        request.setAttribute("user",user);
        return "update_user_page";
    }

    @RequestMapping(value = "/admin/update_user", method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        if (password.equals("")) {//password is null , doesn't need to be changed
            userDao.updateUserWithoutPasswordUpdate(id,name,email,role);
        } else {//password is updating
            String hashedPassword = PasswordHash.HashPasswordMD5(password);
            userDao.updateUser(id,name, email, hashedPassword, role);
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/tickets", method = RequestMethod.GET)
    public String getAllTickets(HttpServletRequest request){
        List<TicketType> ticketTypes = ticketDao.getAllTicketTypes();
        request.setAttribute("ticketTypes", ticketTypes);
        return "tickets";
    }

    //TODO QUESTION FOR IGOR 2 same methods POST and GET - why?
    @RequestMapping(value = "/admin/add_bonus", method = RequestMethod.POST)
    public String addBonus(HttpServletRequest request) throws DaoException {
        int ticketTypeId = Integer.parseInt(request.getParameter("ticketTypeId"));
        String bonusName = request.getParameter("bonusName");
        LOGGER.info("Adding bonus " + bonusName + " to ticketId " + ticketTypeId);
        ticketDao.insertBonusToTicket(ticketTypeId,bonusName);
       return  "redirect:/admin/tickets";
    }

    @RequestMapping(value = "/admin/delete_bonus", method = RequestMethod.GET)
    public String deleteBonus(HttpServletRequest request) throws DaoException {
        String bonusName = request.getParameter("bonusName");
        ticketDao.deleteBonusFromTicket(bonusName);
        return  "redirect:/admin/tickets";
    }


    @RequestMapping(value = "/admin/excursions", method = RequestMethod.GET)
    public String excursionsPage(HttpServletRequest request) throws DaoException {
        List<ExcursionsInPortContainer> excursionsInPortContainers = excursionDao.getAllExcursionsInAllPorts();
        request.setAttribute("excursionsInPortContainers", excursionsInPortContainers);
        return "excursions";
    }

    @RequestMapping(value = "/admin/activate_excursion", method = RequestMethod.GET)
    public String activateExcursion(HttpServletRequest request) throws DaoException {
        int excursionId = Integer.parseInt(request.getParameter("excursionId"));
        excursionDao.activateExcursion(excursionId);
        return "redirect:/admin/excursions";
    }

    @RequestMapping(value = "/admin/deactivate_excursion", method = RequestMethod.GET)
    public String deactivateExcursion(HttpServletRequest request) throws DaoException {
        int excursionId = Integer.parseInt(request.getParameter("excursionId"));
        excursionDao.deactivateExcursion(excursionId);
        return "redirect:/admin/excursions";
    }

    @RequestMapping(value = "/admin/create_excursion_page",method = RequestMethod.GET)
    public String createExcursionPage(HttpServletRequest request){
        List<Port> ports = portDao.getAllPorts();
        request.setAttribute("ports", ports);
        return "create_excursion" ;
    }

    @RequestMapping(value = "/admin/create_excursion",method = RequestMethod.POST)
    public String createExcursion(HttpServletRequest request){
        int portId = Integer.parseInt(request.getParameter("port"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        if(name.equals("")){
            request.setAttribute("description", description);
            request.setAttribute("port",portId);
            request.setAttribute("price",price);
            List<Port> ports = portDao.getAllPorts();
            request.setAttribute("ports", ports);
            request.setAttribute("error_message", "errorNoName");
            return "create_excursion";
        }

        if(description.equals("")){
            request.setAttribute("name", name);
            request.setAttribute("port",portId);
            request.setAttribute("price",price);
            List<Port> ports = portDao.getAllPorts();
            request.setAttribute("ports", ports);
            request.setAttribute("error_message", "errorNoDescription");
            return "create_excursion";
        }

        System.out.println(portId + " " + name + " " + description +" " + price);

        excursionDao.createExcursion(name,portId,description,price);
        return "redirect:/admin/excursions" ;
    }


}
