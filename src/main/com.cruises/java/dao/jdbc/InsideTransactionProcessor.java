package dao.jdbc;

import exception.DaoException;

import java.sql.Connection;

public interface InsideTransactionProcessor {
    void process(Connection connection) throws DaoException;
}
