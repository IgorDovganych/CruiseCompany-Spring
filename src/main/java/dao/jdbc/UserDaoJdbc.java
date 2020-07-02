package dao.jdbc;

import dao.UserDao;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {
    @Autowired
    ConnectionPool connectionPool;

    @Autowired
    SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbc.class);

    private static final String SELECT_BY_EMAIL = "select * from users where email = '";
    private static final String SELECT_USER_BY_ID = "select * from users where id = ";
    private static final String INSERT_USER = "insert into users(name, email, password, role) values(?, ?,?,'user')";
    private static final String SELECT_ALL_FROM_USERS = "select * from users";
    private static final String UPDATE_USER = "update users set name=(?),email=(?),role=(?),password=(?) where id=";
    private static final String UPDATE_USER_WITHOUT_PASSWORD_UPDATE = "update users set name=?,email=?,role=? where id=";

    @Override
    public User findUserByEmail(String email) {
        LOGGER.info("method find user by email started");
        Session session = this.sessionFactory.openSession();
        User user = (User) session.byNaturalId(User.class).using("email", email).load();
        session.close();
        return user;
    }

    @Override
    public User getUserById(long id) {
        LOGGER.info("method find user by id started");
        Session session = this.sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public User createUser(String name, String email, String password) {
        LOGGER.info("method createUser started");
        Session session = this.sessionFactory.openSession();
        User user = new User(name, email, password, "user");
        session.save(user);
        session.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("method getAllUsers started");
        Session session = this.sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(SELECT_ALL_FROM_USERS);
        query.addEntity(User.class);
        List<User> users = query.list();
        session.close();
        return users;
    }

    @Override
    public List<User> getAllUsersOnPage(int pageNum, int pageSize) {
        LOGGER.info("method getAllUsers on page " + pageNum + " started");
        Session session = this.sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(SELECT_ALL_FROM_USERS);
        query.setFirstResult(pageSize * (pageNum - 1));
        query.setMaxResults(pageSize);
        query.addEntity(User.class);
        List<User> users = query.list();
        session.close();
        return users;
    }

    @Override
    public User updateUser(int id, String name, String email, String password, String role) {
        LOGGER.info("method updateUser started");
        Session session = sessionFactory.openSession();
        SQLQuery sql = password == null ? session.createSQLQuery(UPDATE_USER_WITHOUT_PASSWORD_UPDATE + id) :
                session.createSQLQuery(UPDATE_USER + id);
        sql.setString(0, name);
        sql.setString(1, email);
        sql.setString(2, role);
        if (password != null) {
            sql.setString(3, password);
        }
        sql.executeUpdate();
        session.close();
        User user = getUserById(id);
        return user;
    }

    @Override
    public User updateUserWithoutPasswordUpdate(int id, String name, String email, String role) {
        return updateUser(id, name, email, null, role);
    }

}
