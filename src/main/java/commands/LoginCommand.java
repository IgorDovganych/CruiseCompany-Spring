package commands;

import dao.jdbc.UserDaoJdbc;

import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        String email = request.getParameter("email");
//        String hashedPassword = UserDaoJdbc.HashPasswordMD5(request.getParameter("password"));
//        User user = DaoFactoryInstance.getFactory().getUserDao().findUserByEmail(email);
//
//        if (user == null) {
//            request.setAttribute("error_message", "User with provided email doesn't exist, please try again");
//            return "/WEB-INF/pages/login.jsp";
//        } else {
//            System.out.println("Login servlet info : hash=" + hashedPassword);
//            System.out.println("Login servlet info : pass from db= " + user.getPassword());
//            if (hashedPassword.equals(user.getPassword())) {
//                if (user.getRole().equals("admin")) { //user with role "admin" logged in
//                    DaoFactoryInstance.getFactory().getUserDao().getAllUsers();
//                    request.getSession().setAttribute("user", user);
//                    return "/FinalProjectJavaEE/user_account_info";
//                } else if (user.getRole().equals("user")) { //user with role "user" logged in/
//                    request.getSession().setAttribute("user", user);
//                    return "/FinalProjectJavaEE/user_account_info";
//                }
//            } else {//wrong password
//                request.setAttribute("email", email);
//                request.setAttribute("error_message", "Wrong Password, please type again");
//                return "/WEB-INF/pages/login.jsp";
//            }
//        }
        return "/WEB-INF/pages/index.jsp";
    }
}
