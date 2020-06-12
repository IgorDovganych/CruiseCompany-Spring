package dao.jdbc;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

public class SessionFactoryInstance {
    private SessionFactory sessionFactory;


    public SessionFactory getSession(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
