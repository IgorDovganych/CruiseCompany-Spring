package dao;

import exception.DaoException;
import model.Excursion;
import model.ExcursionsInPortContainer;

import java.sql.Connection;
import java.util.List;

public interface ExcursionDao {

    /**
     * gets all excursions from database
     * @return list of excursions
     * @throws DaoException  when error inside connection occurs
     */
    List<ExcursionsInPortContainer> getAllExcursionsInAllPorts() throws DaoException;

    /**
     * gets all excursions from database
     * @param cruiseId - id of the cruise
     * @return list of excursions which are available in cruise
     * @throws DaoException  when error inside connection occurs
     */
    List<Excursion> getExcursionsByCruiseId(int cruiseId) throws DaoException;

    /**
     * creates new record in purchased excursions table in database
     * @param connection - connection from Connection Pool
     * @param excursionIds - id of excuirsions which has been purchased by user
     * @param purchasedTicketId - id of the ticket
     * @throws DaoException  when error inside connection occurs
     */
    void purchaseExcursions(Connection connection, List<Integer> excursionIds, int purchasedTicketId) throws DaoException;

    /**
     * deactivates excursion
     * @param excursionId - id of the excursion
     * @throws DaoException  when error inside connection occurs
     */
    void deactivateExcursion(int excursionId) throws DaoException;

    /**
     * activates excursion
     * @param excursionId - id of the excursion
     * @throws DaoException  when error inside connection occurs
     */
    void activateExcursion(int excursionId) throws DaoException;

    /**
     * inserts new excursion into the database
     * @param name - excursion name
     * @param description - some details of excursion
     * @param portId - id of the port where excursion takes place
     * @param price - excursion price
     * @throws DaoException  when error inside connection occurs
     */
    void createExcursion(String name, int portId, String description, int price);
}
