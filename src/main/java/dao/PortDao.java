package dao;

import exception.DaoException;
import model.Port;

import java.util.List;

public interface PortDao {

    /**
     * gets all ports from database
     * @return list of ports
     */
    List<Port> getAllPorts();
}
