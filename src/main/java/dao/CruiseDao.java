package dao;

import exception.DaoException;
import model.Cruise;
import model.Ship;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface CruiseDao {

    /**
     * gets all cruises from database
     * @return   list of cruises
     * @exception DaoException if a database access error occurs
     * @throws DaoException  when error inside connection occurs
     */
    List<Cruise> getAllCruises() throws DaoException;

    /**
     * gets specific cruise from database
     * @param cruiseId - id of the cruise
     * @return   cruise
     */
    Cruise getCruiseById(int cruiseId);

    /**
     * puts cruises from database in HashMap
     * @return   cruise
     * @throws DaoException  when error inside connection occurs
     */
    HashMap<Integer, Cruise> getCruisesByIdsInHashMap() throws DaoException;

    /**
     * gets all ships from database
     * @return all ships
     * @throws DaoException  when error inside connection occurs
     */
    List<Ship> getAllShips() throws DaoException;

    /**
     * inserts route into the table inside the database
     * @param portIds all port id's on the route in sequence order
     * @return id of the route
     * @throws DaoException  when error inside connection occurs
     */
    int insertRoute(List<Integer> portIds) throws DaoException;

    /**
     * inserts cruise into the table inside the database
     * @param route_id - id of the route
     * @param ship_id - ship id
     * @param startDate - date of start
     * @param endDate - date of the end
     * @param basePrice - base ticket price
     * @throws DaoException  when error inside connection occurs
     */
    void insertCruise(int route_id, int ship_id, Date startDate, Date endDate, int basePrice) throws DaoException;

    /**
     * activates cruise
     * @param cruiseId - id of the cruise
     */
    void activateCruise(int cruiseId);

    /**
     * deactivates cruise so end user cannot see it
     * @param cruiseId - id of the cruise
     */
    void deactivateCruise(int cruiseId);
}
