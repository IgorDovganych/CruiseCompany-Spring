package dao.jdbc;

import dao.PortDao;
import model.Port;
import model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortDaoJdbc implements PortDao {

    @Autowired
    SessionFactory sessionFactory;

    private static final String GET_ALL_PORTS = "Select * from ports";

    @Override
    public List<Port> getAllPorts() {
        Session session = this.sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(GET_ALL_PORTS);
        query.addEntity(Port.class);
        List<Port> ports = query.list();
        return ports;
    }
}
