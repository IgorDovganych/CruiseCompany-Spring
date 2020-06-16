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
    private static final String UPDATE_USER = "update users set name=?,email=?,role=?,password=? where id=";
    private static final String UPDATE_USER_WITHOUT_PASSWORD_UPDATE = "update users set name=?,email=?,role=? where id=";


//    @Override
//    public User findUserByEmail(String email) {
//        try {
//            Connection connection = connectionPool.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(SELECT_BY_EMAIL + email + "'");
//            if (resultSet.next()) {
//                User user = new User((long) resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("role"));
//                connection.close();
//                System.out.println(user.toString());
//                return user;
//            } else return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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
        User user = (User) session.get(User.class,id);
        session.close();
        return user;
    }



    public static void main(String[] args) {
        System.out.println(new UserDaoJdbc().findUserByEmail("tanya@gmail.com"));

    }

    @Override
    public User createUser(String name, String email, String password) {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();            //getting auto-generated id for user
            long id;
            if (generatedKeys.next()) {
                System.out.println(generatedKeys);
                id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Id is missing");
            }
            User user = new User(id, name, email, password, "user");
            LOGGER.info(user.toString() + " is created");
//            connection.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<User> getAllUsers() {
//        try {
//            Connection connection = connectionPool.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FROM_USERS);
//            List<User> users = new ArrayList<>();
//            while (resultSet.next()) {
//                User user = new User(resultSet.getLong("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("email"),
//                        resultSet.getString("password"),
//                        resultSet.getString("role"));
//                users.add(user);
//            }
////            connection.close();
//            return users;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    @Override
    public List<User> getAllUsers() {
        LOGGER.info("method getAllUsers started");
        Session session = this.sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(SELECT_ALL_FROM_USERS);
        query.addEntity(User.class);
        List<User> users = query.list();
        return users;
    }

    @Override
    public User updateUser(int id, String name, String email, String password, String role) {
        try (
                Connection connection = connectionPool.getConnection()
        ) {
            String sql = password == null ? UPDATE_USER_WITHOUT_PASSWORD_UPDATE : UPDATE_USER;
            PreparedStatement statement = connection.prepareStatement(sql + id);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, role);
            if (password != null) {
                statement.setString(4, password);
            }
            statement.executeUpdate();
            LOGGER.info("User " + email + " was updated");
            ResultSet resultSet = statement.executeQuery(SELECT_USER_BY_ID + id);
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
                return user;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUserWithoutPasswordUpdate(int id, String name, String email, String role) {
        return updateUser(id, name, email, null, role);
    }

}
