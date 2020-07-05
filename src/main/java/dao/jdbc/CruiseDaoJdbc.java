package dao.jdbc;

import dao.CruiseDao;
import exception.DaoException;
import model.Port;
import model.Cruise;
import model.Ship;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CruiseDaoJdbc implements CruiseDao {

    @Autowired
    Ship ship;

    @Autowired
    ConnectionPool connectionPool;

    @Autowired
    SessionFactory sessionFactory;

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(CruiseDaoJdbc.class);
    final static String GET_ALL_CRUISES = "SELECT * " +
            "FROM cruises\n" +
            "inner join ships on ship_id=ships.id\n" +
            "inner join route_points on cruises.route_id=route_points.route_id \n" +
            "inner join ports on route_points.port_id=ports.id\n" +
            "Order by cruises.id,port_sequence_number asc;";

    final static String GET_CRUISE_BY_ID = "SELECT * FROM cruises inner join ships on ship_id=ships.id " +
            "inner join route_points on cruises.route_id=route_points.route_id " +
            "inner join ports on route_points.port_id=ports.id " +
            "where cruises.id=? order by ships.id, port_sequence_number asc";

    final static String HQL_GET_CRUISE_BY_ID = "SELECT cruises.id, ship_id, ships.model as model, capacity, ports.name, start_date, end_date, base_price, isActive, port_sequence_number FROM cruises inner join ships as ships on ship_id=ships.id" +
            " inner join route_points on cruises.route_id=route_points.route_id " +
            " inner join ports on route_points.port_id=ports.id " +
            " where cruises.id=? order by ships.id, port_sequence_number asc";

    final static String INSERT_INTO_ROUTE_POINTS ="INSERT INTO route_points(route_id, port_id,port_sequence_number) " +
            "values(?,?,?)";
    final static String GET_MAX_VALUE_OF_ROUTE_ID = "SELECT MAX(route_id) as max_value FROM route_points";
    final static String INSERT_CRUISE = "INSERT INTO cruises(route_id, ship_id,start_date, end_date, base_price, isActive) values(?,?,?,?,?,?)";


    final static String GET_ALL_SHIPS = "SELECT * FROM ships";
    final static String GET_ALL_PORTS = "SELECT * FROM ports";

    final static String ACTIVATE_CRUISE = "update cruises set isActive =true where id = ?";
    final static String DEACTIVATE_CRUISE = "update cruises set isActive =false where id = ?";


    public List<Cruise> getAllCruises() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_CRUISES);
            List<Cruise> cruises = new ArrayList<>();
            while (resultSet.next()) {
                Ship ship = new Ship(resultSet.getInt("ships.id"),
                        resultSet.getInt("ships.capacity"),
                        resultSet.getString("ships.model"));
                List<String> route = new ArrayList<>();
                String routePoint = resultSet.getString("ports.name");
                route.add(routePoint);
                int currentRouteSequenceNumber = resultSet.getInt("port_sequence_number");
                int portsInCruise = 1;
                while (resultSet.next()) {
                    if (resultSet.getInt("port_sequence_number") > currentRouteSequenceNumber) {
                        route.add(resultSet.getString("ports.name"));
                    } else {
                        break;
                    }
                }
                resultSet.previous();
                Cruise cruise = new Cruise(resultSet.getInt("id"),
                        ship,
                        route,
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("base_price"),
                        resultSet.getBoolean("isActive"));
                cruises.add(cruise);
            }
            LOGGER.info("all cruises : "  + cruises.toString());
            return cruises;
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while getting cruises", e);
        }
    }


//    public Cruise getCruiseById(int cruiseId) {
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement statement = connection.prepareStatement(GET_CRUISE_BY_ID)) {
//            statement.setInt(1, cruiseId);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Ship ship = new Ship(resultSet.getInt("ships.id"),
//                        resultSet.getInt("ships.capacity"),
//                        resultSet.getString("ships.model"));
//                Port port = new Port(resultSet.getInt("ports.id"),
//                        resultSet.getString("name"));
//
//                Date startDate = resultSet.getDate("start_date");
//                Date endDate = resultSet.getDate("end_date");
//                int price = resultSet.getInt("base_price");
//
//                List<String> route = new ArrayList<>();
//                String routePoint = resultSet.getString("ports.name");
//                route.add(routePoint);
//                while (resultSet.next()) {
//                    route.add(resultSet.getString("ports.name"));
//
//                }
//                Cruise cruise = new Cruise(cruiseId,
//                        ship,
//                        route,
//                        startDate,
//                        endDate,
//                        price);
//                LOGGER.info(cruise.toString());
//                return cruise;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public Cruise getCruiseById(int id) {
        LOGGER.info("method get cruise by id  started");
        Session session = this.sessionFactory.openSession();
        //Cruise cruise = (Cruise) session.get(Cruise.class,id);
        SQLQuery query = session.createSQLQuery(HQL_GET_CRUISE_BY_ID);
        query.setParameter(0, id);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List data = query.list();
        List<String> ports = new ArrayList<>();
        int cruiseId = 0;
        Date start_date = null;
        Date end_date = null;
        int basePrice = 0;
        boolean isActive = false;
        for (Object object : data) {
            Map row = (Map) object;
            cruiseId = (Integer) (row.get("id"));
            start_date = (Date) row.get("start_date");
            end_date = (Date) row.get("end_date");
            basePrice = (Integer) row.get("base_price");
            byte active = (Byte) row.get("isActive");
            isActive = active!=0;
            String model = (String) row.get("model");
            ship.setId((Integer) row.get("ship_id"));
            ship.setModel(model);
            int capacity = Integer.parseInt((String) row.get("capacity"));
            ship.setCapacity(capacity);
            ports.add((String) row.get("name"));
        }
        Cruise cruise = new Cruise(cruiseId, ship, ports, start_date, end_date, basePrice, isActive);
        LOGGER.info("get cruise by id returns : " + cruise.toString());
        session.close();
        return cruise;
    }

    public HashMap<Integer, Cruise> getCruisesByIdsInHashMap() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(GET_ALL_CRUISES);
            HashMap<Integer, Cruise> cruises = new HashMap<>();
            while (resultSet.next()) {
                Ship ship = new Ship(resultSet.getInt("ships.id"),
                        resultSet.getInt("ships.capacity"),
                        resultSet.getString("ships.model"));
                List<String> route = new ArrayList<>();
                String routePoint = resultSet.getString("ports.name");
                route.add(routePoint);
                int currentRouteSequenceNumber = resultSet.getInt("port_sequence_number");
                while (resultSet.next()) {
                    if (resultSet.getInt("port_sequence_number") > currentRouteSequenceNumber) {
                        route.add(resultSet.getString("ports.name"));
                    } else {
                        break;
                    }
                }
                resultSet.previous();
                int cruiseId = resultSet.getInt("id");
                Cruise cruise = new Cruise(cruiseId,
                        ship,
                        route,
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("base_price"),
                        resultSet.getBoolean("isActive"));

                cruises.put(cruiseId, cruise);
            }
            return cruises;
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while counting tickets", e);
        }
    }

    @Override
    public List<Ship> getAllShips() throws DaoException {
        LOGGER.info("getAllShips method started");
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_SHIPS);
            List<Ship> ships = new ArrayList<>();
            while (resultSet.next()) {
                Ship ship = new Ship(
                        resultSet.getInt("id"),
                        resultSet.getInt("capacity"),
                        resultSet.getString("model")
                );
                ships.add(ship);
            }
            return ships;
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while getting ships", e);
        }
    }

    @Override
    public List<Port> getAllPorts() throws DaoException {
        LOGGER.info("getAllPorts method started");
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_PORTS);
            List<Port> ports = new ArrayList<>();
            while (resultSet.next()) {
                Port port = new Port(
                        resultSet.getInt("id"),
                        resultSet.getString("name"));
                ports.add(port);
            }
            return ports;
        } catch (SQLException e) {
            LOGGER.warn(e);
            throw new DaoException("An exception occurred while getting ports", e);
        }
    }

    @Override
    public int insertRoute(List<Integer> portIds) throws DaoException{
        LOGGER.info("insertRoute method started");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ROUTE_POINTS);
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(GET_MAX_VALUE_OF_ROUTE_ID);
            int routeId = 0;
            while (rs.next()){
                routeId = rs.getInt("max_value");
            }
            routeId++;
            connection.setAutoCommit(false);
            int sequence_num = 1;
            for (int portId:portIds) {
                ps.setInt(1,routeId);
                ps.setInt(2,portId);
                ps.setInt(3,sequence_num);
                sequence_num++;
                ps.addBatch();
            }
            int[] updateCounts = ps.executeBatch();
            connection.commit();
            for (int i=0; i<updateCounts.length; i++) {
                if (updateCounts[i] >= 0) {
                    System.out.println("OK; updateCount=" + updateCounts[i]);
                }
            }
            System.out.println(updateCounts);
            return routeId;


        }catch (SQLException e){
            LOGGER.warn(e);
            throw new DaoException("An exception occured while inserting into route_points");
        }
    }

    @Override
    public void insertCruise(int route_id, int ship_id, Date startDate, Date endDate, int basePrice) throws DaoException {
        LOGGER.info("insertCruise method started");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_CRUISE)){
            ps.setInt(1,route_id);
            ps.setInt(2,ship_id);
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            ps.setDate(3, sqlStartDate);
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            ps.setDate(4, sqlEndDate);
            ps.setInt(5,basePrice);
            ps.setBoolean(6,true);
            ps.executeUpdate();

        }catch(SQLException e){
            LOGGER.warn(e);
            throw new DaoException("An exception while inserting cruise into cruise table");
        }
    }

    @Override
    public void activateCruise(int cruiseId) {
        LOGGER.info("method activateCruise started");
        Session session = sessionFactory.openSession();
        SQLQuery sql = session.createSQLQuery(ACTIVATE_CRUISE);
        sql.setInteger(0, cruiseId);
        sql.executeUpdate();
        session.close();
    }

    @Override
    public void deactivateCruise(int cruiseId) {
        LOGGER.info("method deactivateCruise started");
        Session session = sessionFactory.openSession();
        SQLQuery sql = session.createSQLQuery(DEACTIVATE_CRUISE);
        sql.setInteger(0, cruiseId);
        sql.executeUpdate();
        session.close();
    }
}
