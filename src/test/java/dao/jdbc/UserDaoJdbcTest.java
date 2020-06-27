package dao.jdbc;


import dao.UserDao;
import model.User;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;
import util.PasswordHash;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoJdbcTest {

    private static final String SELECT_ALL_FROM_USERS = "select * from users";

    @InjectMocks
    UserDaoJdbc userDaoJdbc;

    @Mock
    SessionFactory sessionFactory;

    @Mock
    SQLQuery sqlQuery;

    @Mock
    Session session;

    @Mock
    NaturalIdLoadAccess naturalIdLoadAccess;

    @Test
    public void findUserByEmailTest(){
        //setup
        String email = "email@gmail.com";
        User user = new User(1L, "YA", email, PasswordHash.HashPasswordMD5("password"), "user");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.byNaturalId(User.class)).thenReturn(naturalIdLoadAccess);
        when(naturalIdLoadAccess.using("email", email)).thenReturn(naturalIdLoadAccess);
        when(naturalIdLoadAccess.load()).thenReturn(user);

        //act
        User actualUser =  userDaoJdbc.findUserByEmail(email);

        //assert
        verify(session, times(1)).close();
        // verify(mockedSessionFactory, times(2)).close();
        assertEquals(user,actualUser);
    }

    @Test
    public void getUserByIdTest(){
        //setup
        long id = 1L;
        User user = new User(1L, "YA", "email", PasswordHash.HashPasswordMD5("password"), "user");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(User.class,id)).thenReturn(user);

        //act
        User actualUser = userDaoJdbc.getUserById(id);

        //assert
        verify(session, times(1)).close();
        assertEquals(user,actualUser);
    }

    @Test
    public void getAllUsersTest(){
        //setup
        List<User> users = new ArrayList<>();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createSQLQuery(SELECT_ALL_FROM_USERS)).thenReturn(sqlQuery);
        when(sqlQuery.addEntity(User.class)).thenReturn(sqlQuery);
        when(sqlQuery.list()).thenReturn(users);

        //act
        List<User> actualResult = userDaoJdbc.getAllUsers();

        //assert
        verify(session,times(1)).close();
        assertEquals(users,actualResult);
    }

    @Test
    public void createUserTest(){
        //setup
        User user = new User( "Igor", "email@gmail.com", "pass", "user");
        when(sessionFactory.openSession()).thenReturn(session);

        //act
        User actualUser = userDaoJdbc.createUser("Igor", "email@gmail.com", "pass");

        //assert
        verify(session,times(1)).close();
        verify(sessionFactory,times(1)).openSession();
        verify(session,times(1)).save(user);
       // assertEquals(user,actualUser);
    }
}


