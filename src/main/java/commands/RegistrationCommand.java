package commands;


import dao.jdbc.UserDaoJdbc;
import exception.DaoException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String hashedPassword = UserDaoJdbc.HashPasswordMD5(request.getParameter("password"));
//        PrintWriter out = response.getWriter();
//        User user = DaoFactoryInstance.getFactory().getUserDao().createUser(name,email,hashedPassword);
//        request.getSession().setAttribute("user",user);
//        {
//            out.println("<script type=\"text/javascript\">");
//            out.println("alert('Registration is succesfull');");
//            out.println("location='user_account_info.jsp';");
//            out.println("</script>");
//        }
//        System.out.println("Log from Registration servlet : user added - " + user.toString());
//        //response.sendRedirect("index.jsp");
        return "/FinalProjectJavaEE/user_account_info";
    }
}
