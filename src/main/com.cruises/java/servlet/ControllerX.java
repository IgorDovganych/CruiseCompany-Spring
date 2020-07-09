package servlet;

import dao.CruiseDao;
import dao.ExcursionDao;
import dao.TicketDao;
import dao.UserDao;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Controller
public class ControllerX {

    Logger LOGGER = Logger.getLogger(ControllerX.class);

    @Autowired
    CruiseDao cruiseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    ExcursionDao excursionDao;



    @RequestMapping(value = "/cruises", method = RequestMethod.GET)
    public String getAllCruises(HttpServletRequest request) throws DaoException {
        request.setAttribute("cruises", cruiseDao.getAllCruises());
        return "cruises";
    }


    @RequestMapping(value = "/cruise_info", method = RequestMethod.GET)
    public String getCruiseInfo(HttpServletRequest request) throws DaoException {
        int cruiseId = parseInt(request.getParameter("id"));
        Cruise cruise = cruiseDao.getCruiseById(cruiseId);
        request.setAttribute("cruise", cruise);
        List<Excursion> allExcursions = excursionDao.getExcursionsByCruiseId(cruiseId);
        request.setAttribute("excursions", allExcursions);
        List<TicketType> ticketTypes = ticketDao.getAllTicketTypes();
        request.setAttribute("ticketTypes", ticketTypes);
        int amountOfPurchasedTickets = ticketDao.getPurchasedTicketsAmountByCruiseId(cruiseId);
        int availableTicketsAmount = cruise.getShip().getCapacity()-amountOfPurchasedTickets;
        request.setAttribute("availableTicketsAmount",availableTicketsAmount);
        return "cruise_info";
    }


    @Autowired
    TransactionManager transactionManager;

    @RequestMapping(value = "/purchase_ticket", method = RequestMethod.POST)
    public String purchaseTicket(HttpServletRequest request) throws DaoException {
        LOGGER.info("execution of PurchaseTicketCommand started");
        User user = (User) request.getSession().getAttribute("user");
        int cruiseId = parseInt(request.getParameter("cruiseId"));
        int ticketTypeId = parseInt(request.getParameter("ticketId"));
        String excursionIdsString = request.getParameter("excursionIds");
        LOGGER.info("length of excursion ids string from request is " + excursionIdsString.length());

        List<Integer> excursionIds = Arrays.stream(request.getParameter("excursionIds").split(","))
                .filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(toList());

        Cruise cruise = cruiseDao.getCruiseById(cruiseId);
        int amountOfPurchasedTickets = ticketDao.getPurchasedTicketsAmountByCruiseId(cruiseId);
        if (amountOfPurchasedTickets >= cruise.getShip().getCapacity()) {
            request.setAttribute("noTicketsMessage","Ticket has been purchased by another user, sorry");
            request.setAttribute("cruise",cruise);
            return "cruise_info";
        }

        transactionManager.doInTransaction(connection -> {
            int purchasedTicketId = ticketDao.purchaseTicket(connection, user.getId(), cruiseId, ticketTypeId);
            excursionDao.purchaseExcursions(connection, excursionIds, purchasedTicketId);
        });

        return "redirect:/user_account_info";
    }

    @RequestMapping(value = "/login_page", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String hashedPassword = PasswordHash.HashPasswordMD5(request.getParameter("password"));

        LOGGER.info("login method started");
        User user = userDao.findUserByEmail(email);
        if (user == null) {
            request.setAttribute("error_message", "User with provided email doesn't exist, please try again");
            return "login";
        } else {
            if (hashedPassword.equals(user.getPassword())) { //correct user, correct password
                request.getSession().setAttribute("user", user);
                    return "redirect:/user_account_info";
            } else {//wrong password
                request.setAttribute("email", email);
                request.setAttribute("error_message", "Wrong Password, please type again");
                return "login";
            }
        }
    }

    @RequestMapping(value = "/user_account_info", method = RequestMethod.GET)
    public String getUserAccountInfo(HttpServletRequest request) throws DaoException {
        User user = (User) request.getSession().getAttribute("user");
        long userId = user.getId();
        List<PurchasedTicket> purchasedTicketsForUser= ticketDao.getPurchasedTicketsForUserByUserId(userId);
        HashMap<Integer, Cruise> idCruiseHashMap = cruiseDao.getCruisesByIdsInHashMap();

        for (PurchasedTicket purchasedTicket:purchasedTicketsForUser) {
            Cruise cruise = idCruiseHashMap.get(purchasedTicket.getCruiseId());
            purchasedTicket.setCruise(cruise);
        }
        request.setAttribute("purchasedTicketsForUser",purchasedTicketsForUser);
        return "user_account_info";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/change_language", method = RequestMethod.GET)
    public String changeLanguage(HttpServletRequest request){
        String link = request.getParameter("link");
        String redirectLink = "";
        if(link!=""){
//            redirectLink = link.replace("/FinalProjectSpring", "redirect:");
             redirectLink = "redirect:" + link.replace("/CruiseCompany", "");
        }else {
             redirectLink = "redirect:/";
        }

        String parameters = request.getParameter("parameters");
        redirectLink += parameters.equals("") ? "" : ("?" + parameters);	//creating link with parameters for redirecting
        String language = request.getParameter("lang");
        request.getSession().setAttribute("language", language);
        LOGGER.info("link - " + redirectLink + "  language - " + language);
        return redirectLink;
    }

    @RequestMapping(value = "/registration_page", method = RequestMethod.GET)
    public String registrationPage(HttpServletRequest request){
       return "registration_page";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String hashedPassword = PasswordHash.HashPasswordMD5(request.getParameter("password"));
        PrintWriter out = response.getWriter();
        User user = userDao.createUser(name,email,hashedPassword);
        request.getSession().setAttribute("user",user);
        {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Registration is succesfull');");
            out.println("location='user_account_info.jsp';");
            out.println("</script>");
        }
        LOGGER.info("User " + user + " registered");

        return "redirect:/user_account_info";
    }


}
