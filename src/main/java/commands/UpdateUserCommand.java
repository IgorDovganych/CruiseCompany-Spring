package commands;


import dao.jdbc.UserDaoJdbc;
import exception.DaoException;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String role = request.getParameter("role");
//        String password = request.getParameter("password");
//        if (password.equals("")) {
//            System.out.println("Log from Update_user servlet: password is null , doesn't need to be changed");
//            DaoFactoryInstance.getFactory().getUserDao().updateUserWithoutPasswordUpdate(id,name,email,role);
//            List<User> users = DaoFactoryInstance.getFactory().getUserDao().getAllUsers();
//            request.setAttribute("users", users);
//
//        } else {
//            System.out.println("Log from Update_user servlet: password is not null");
//            String hashedPassword = UserDaoJdbc.HashPasswordMD5(password);
//            DaoFactoryInstance.getFactory().getUserDao().updateUser(id,name, email, hashedPassword, role);
//            List<User> users = DaoFactoryInstance.getFactory().getUserDao().getAllUsers();
//            request.setAttribute("users", users);
//
//        }
        return "/WEB-INF/pages/users.jsp";
    }
}
