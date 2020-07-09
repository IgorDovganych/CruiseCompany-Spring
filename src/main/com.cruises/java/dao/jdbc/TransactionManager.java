package dao.jdbc;

import exception.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TransactionManager {

    @Autowired
    ConnectionPool connectionPool;

    private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);


    public void doInTransaction(InsideTransactionProcessor instance) {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                instance.process(connection);
                connection.commit();
            } catch (DaoException e) {
                connection.rollback();
                LOGGER.warn("exception", e);
            }
        } catch (SQLException e) {
            LOGGER.warn("exception", e);
        }

    }

}
