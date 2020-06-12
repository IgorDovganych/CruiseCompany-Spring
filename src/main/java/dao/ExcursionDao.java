package dao;

import exception.DaoException;
import model.Excursion;
import model.ExcursionsInPortContainer;

import java.sql.Connection;
import java.util.List;

public interface ExcursionDao {
    List<ExcursionsInPortContainer> getAllExcursionsInAllPorts() throws DaoException;
    List<Excursion> getExcursionsByCruiseId(int cruiseId) throws DaoException;
    void purchaseExcursions(Connection connection, List<Integer> excursionIds, int purchasedTicketId) throws DaoException;
    void deactivateExcursion(int excursionId) throws DaoException;

    void activateExcursion(int excursionId) throws DaoException;
    void createExcursion(String name, int portId, String description, int price);
}
