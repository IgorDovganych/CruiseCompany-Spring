package dao;

import exception.DaoException;
import model.Cruise;
import model.Port;
import model.Ship;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface CruiseDao {
    List<Cruise> getAllCruises() throws DaoException;


    Cruise getCruiseById(int cruiseId);

    HashMap<Integer, Cruise> getCruisesByIdsInHashMap() throws DaoException;

    List<Ship> getAllShips() throws DaoException;
    List<Port> getAllPorts() throws DaoException;
    int insertRoute(List<Integer> portIds) throws DaoException;
    void insertCruise(int route_id, int ship_id, Date startDate, Date endDate, int basePrice) throws DaoException;
}