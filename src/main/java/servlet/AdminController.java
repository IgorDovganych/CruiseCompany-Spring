package servlet;

import dao.*;
import dao.jdbc.TransactionManager;
import exception.DaoException;
import model.*;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.PasswordHash;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

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

    @Autowired
    TransactionManager transactionManager;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getAllUsers(HttpServletRequest request) {
        int pageNum = 1;
        if(request.getParameter("page") == null){
            request.getSession().setAttribute("page", "1");

        }else {
            request.getSession().setAttribute("page",request.getParameter("page"));
            pageNum = Integer.parseInt(request.getParameter("page"));
        }

        int pageSize = 5;
        int numOfUsers = userDao.getAllUsers().size();
        int numOfPages = numOfUsers/pageSize;
        List<User> users = userDao.getAllUsersOnPage(pageNum,pageSize);
        request.setAttribute("users", users);
        request.setAttribute("numOfUsers", numOfUsers);
        request.setAttribute("numOfPages", numOfPages);
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

    @RequestMapping(value = "/admin/create_cruise_page",method = RequestMethod.GET)
    public String createCruisePage(HttpServletRequest request) throws DaoException {
        LOGGER.info("CreateCruisePageCommand started");
        List<Ship> ships = cruiseDao.getAllShips();
        request.setAttribute("ships",ships);
        List<Port> ports = portDao.getAllPorts();
        request.setAttribute("ports",ports);
        return "create_cruise";
    }

    @RequestMapping(value = "/admin/create_cruise",method = RequestMethod.POST)
    public String createCruise(HttpServletRequest request) throws DaoException, ParseException {
        int shipId = parseInt(request.getParameter("shipId"));
        String portIdsString = request.getParameter("portIds");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate"));
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate"));
        int basePrice = parseInt(request.getParameter("basePrice"));
        System.out.println("shipId = " + shipId);
        System.out.println("portIdsString = " + portIdsString);
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);
        System.out.println("basePrice = " + basePrice);

        List<Integer> portIds = Arrays.stream(request.getParameter("portIds").split(","))
                .filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(toList());
        transactionManager.doInTransaction(connection -> {
            int routeId = cruiseDao.insertRoute(portIds);
            cruiseDao.insertCruise(routeId, shipId, startDate,endDate,basePrice);
        });
        return "redirect:/cruises";
    }

    @RequestMapping(value = "/admin/activate_cruise",method = RequestMethod.GET)
    public String activateCruise(HttpServletRequest request){
        int cruiseId = Integer.parseInt(request.getParameter("id"));
        cruiseDao.activateCruise(cruiseId);
        return "redirect:/cruises";
    }

    @RequestMapping(value = "/admin/deactivate_cruise",method = RequestMethod.GET)
    public String deactivateCruise(HttpServletRequest request){
        int cruiseId = Integer.parseInt(request.getParameter("id"));
        cruiseDao.deactivateCruise(cruiseId);
        return "redirect:/cruises";
    }
}
