package dao;

import exception.DaoException;
import model.PurchasedTicket;
import model.TicketType;

import java.sql.Connection;
import java.util.List;

public interface TicketDao {
    List<TicketType> getAllTicketTypes();
    int purchaseTicket(Connection connection, long userId, int cruiseId, int ticketTypeId) throws DaoException;
    int getPurchasedTicketsAmountByCruiseId(int cruiseId) throws DaoException;
    List<PurchasedTicket> getPurchasedTicketsForUserByUserId(long userId) throws DaoException;
    void deleteBonusFromTicket(String bonusName) throws DaoException;
    void insertBonusToTicket(int ticketId, String bonusName) throws DaoException;
}
