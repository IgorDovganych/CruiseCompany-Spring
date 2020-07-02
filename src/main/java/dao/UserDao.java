package dao;

import model.User;

import java.util.List;


public interface UserDao {
    User findUserByEmail(String email);

    User createUser(String name, String email, String password);

    List<User> getAllUsers();

    List<User> getAllUsersOnPage(int pageNum, int pageSize);

    User updateUserWithoutPasswordUpdate(int id, String name, String email, String role);

    User updateUser(int id, String name, String email, String password, String role);

    User getUserById(long id);
}
