package dao;

import exception.DaoException;
import model.PurchasedTicket;
import model.TicketType;

import java.sql.Connection;
import java.util.List;

public interface TicketDao {

    /**
     * gets all ticket types from database
     * @return list of ticket types
     */
    List<TicketType> getAllTicketTypes();

    /**
     * inserts new record into the table purchased_tickets
     * @param connection - connection from the Connection Pool
     * @param cruiseId - cruise id
     * @param ticketTypeId - ticket type id
     * @param userId - user id
     * @return id of the purchased ticket
     * @throws DaoException  when error inside the connection occurs
     */
    int purchaseTicket(Connection connection, long userId, int cruiseId, int ticketTypeId) throws DaoException;

    /**
     * gets amount of purchased tickets on the specific cruise
     * @param cruiseId - cruise id
     * @return amount of purchased tickets
     * @throws DaoException  when error inside the connection occurs
     */
    int getPurchasedTicketsAmountByCruiseId(int cruiseId) throws DaoException;

    /**
     * gets list of purchased tickets for the user
     * @param userId - user id
     * @return list of purchased tickets
     * @throws DaoException  when error inside the connection occurs
     */
    List<PurchasedTicket> getPurchasedTicketsForUserByUserId(long userId) throws DaoException;

    /**
     * deletes bonus from the ticket type table in database
     * @param bonusName - name of the bonus
     * @throws DaoException  when error inside the connection occurs
     */
    void deleteBonusFromTicket(String bonusName) throws DaoException;

    /**
     * insert new bonus to the ticket type table in database
     * @param ticketId - ticket id
     * @param bonusName - name of the bonus
     * @throws DaoException  when error inside the connection occurs
     */
    void insertBonusToTicket(int ticketId, String bonusName) throws DaoException;
}
