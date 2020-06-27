package dao.jdbc;


import dao.UserDao;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class myTs {
    @Mock
    UserDao userDao;

    @Mock
    SessionFactory sessionFactory;

    @Test
    public void myTest(){
        userDao.getAllUsers();
    }

}
