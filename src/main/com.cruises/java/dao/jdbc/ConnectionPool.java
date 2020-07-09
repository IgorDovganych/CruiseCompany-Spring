package dao.jdbc;

import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

@Component
public class ConnectionPool {

    private javax.sql.DataSource dataSource = getDataSource();

    @PostConstruct
    private javax.sql.DataSource getDataSource() {
        javax.sql.DataSource dataSource = null;
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/CruiseCompany");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
