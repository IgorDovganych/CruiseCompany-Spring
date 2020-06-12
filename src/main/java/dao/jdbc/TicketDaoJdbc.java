package dao.jdbc;

import dao.TicketDao;
import exception.DaoException;
import model.Excursion;
import model.PurchasedTicket;
import model.TicketType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketDaoJdbc implements TicketDao {
    
    @Autowired
    ConnectionPool connectionPool;
    
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(TicketDaoJdbc.class);

    final static String GET_ALL_TICKETS = "SELECT * FROM bonuses right join tickets on ticket_id=id order by id";
    final static String INSERT_INTO_PURCHASED_TICKETS =
            "insert into purchased_tickets (user_id, cruise_id, ticket_id) values (?,?,?)";
    final static String GET_PURCHASED_TICKETS_AMOUNT_BY_CRUISE_ID =
            "select count(id) from purchased_tickets where cruise_id = ?";
    final static String GET_ALL_PURCHASED_TICKETS_BY_USER_ID = "select purchased_tickets.id, purchased_tickets.cruise_id, tickets.type, e.name, e.id, ports.name, e.price, e.description, e.isActive\n" +
            "from purchased_tickets left join purchased_excursions on purchased_tickets.id = purchased_excursions.purchased_ticket_id\n" +
            "left join tickets on purchased_tickets.ticket_id = tickets.id\n" +
            "left join excursions as e on purchased_excursions.excursion_id = e.id\n" +
            "left join ports on e.port_id = ports.id\n" +
            "where purchased_tickets.user_id = ?\n" +
            "order by purchased_tickets.id";
    final static String DELETE_BONUS_FROM_TICKET = "DELETE FROM bonuses WHERE name=?";
    final static String INSERT_BONUS_TO_TICKET = "INSERT INTO bonuses(ticket_id, name) values(?,?)";

    @Override
    public List<TicketType> getAllTicketTypes() {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_TICKETS);
            List<TicketType> ticketTypes = new ArrayList<>();
            while (resultSet.next()) {
                List<String> bonuses = new ArrayList<>();
                String firstBonuseInTicket = resultSet.getString("name");
                bonuses.add(firstBonuseInTicket);
                int currentTicketId = resultSet.getInt("id");
                while (resultSet.next()) {
                    if (resultSet.getInt("id") == currentTicketId) {
                        String bonuse = resultSet.getString("name");
                        bonuses.add(bonuse);
                    } else {
                        break;
                    }
                }
                resultSet.previous();
                TicketType ticketType = new TicketType(resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price_multiplier"),
                        bonuses);
                LOGGER.info(ticketType.toString());
                ticketTypes.add(ticketType);
            }
            return ticketTypes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int purchaseTicket(Connection connection, long userId, int cruiseId, int ticketTypeId) throws DaoException {
        int id;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_PURCHASED_TICKETS, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, userId);
            statement.setInt(2, cruiseId);
            statement.setInt(3, ticketTypeId);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();            //getting auto-generated id for user
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Id is missing");
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("", e); //TODO create message
        }
        return id;
    }

    @Override
    public int getPurchasedTicketsAmountByCruiseId(int cruiseId) throws DaoException {
        int amountOfPurchasedTickets;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PURCHASED_TICKETS_AMOUNT_BY_CRUISE_ID)) {
            statement.setInt(1, cruiseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                amountOfPurchasedTickets = resultSet.getInt(1);
            } else {
                throw new SQLException("Id is missing");
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while counting tickets", e);
        }
        return amountOfPurchasedTickets;
    }

    @Override
    public List<PurchasedTicket> getPurchasedTicketsForUserByUserId(long userId) throws DaoException {

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PURCHASED_TICKETS_BY_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<PurchasedTicket> purchasedTickets = new ArrayList<>();
            while (resultSet.next()) {
                int CruiseId = resultSet.getInt("cruise_id");
                int purchasedTicketId = resultSet.getInt("purchased_tickets.id");
                String ticketType = resultSet.getString("type");
                List<Excursion> excursionsForOneCruise = new ArrayList<>();
                if (resultSet.getInt("e.id") != 0) {
                    Excursion excursion = new Excursion(resultSet.getInt("e.id"),
                            resultSet.getString("e.name"),
                            resultSet.getString("ports.name"),
                            resultSet.getInt("e.price"),
                            resultSet.getString("e.description"),
                            resultSet.getBoolean("e.isActive"));
                    excursionsForOneCruise.add(excursion);
                }
                while (resultSet.next()) {
                    if (resultSet.getInt("purchased_tickets.id") == purchasedTicketId && resultSet.getInt("e.id") != 0) {
                        excursionsForOneCruise.add(new Excursion(resultSet.getInt("e.id"),
                                resultSet.getString("e.name"),
                                resultSet.getString("ports.name"),
                                resultSet.getInt("e.price"),
                                resultSet.getString("e.description"),
                                resultSet.getBoolean("e.isActive")
                        ));
                    } else {
                        resultSet.previous();
                        break;
                    }
                }
                PurchasedTicket purchasedTicket = new PurchasedTicket(CruiseId, excursionsForOneCruise, ticketType);
                purchasedTickets.add(purchasedTicket);
            }
            return purchasedTickets;
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while counting tickets", e);
        }
    }

    @Override
    public void deleteBonusFromTicket(String bonusName) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BONUS_FROM_TICKET)) {
            statement.setString(1, bonusName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while deleting bonus", e);
        }
    }

    @Override
    public void insertBonusToTicket(int ticketId, String bonusName) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BONUS_TO_TICKET)) {
            statement.setInt(1, ticketId);
            statement.setString(2, bonusName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while deleting bonus", e);
        }
    }
}
