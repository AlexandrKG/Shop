package db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceConnection {

    private static DataSourceConnection datasource;
    private ComboPooledDataSource cpds;

    public DataSourceConnection(String choiceDB) throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        if(choiceDB.equals("MySQL")) {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/shopdb");
            cpds.setUser("alex");
            cpds.setPassword("mysql");
        } else {
            cpds.setDriverClass("org.apache.derby.jdbc.EmbeddedDriver");
            cpds.setJdbcUrl("jdbc:derby:DerbyDB;create=true");
        }
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(10);
        cpds.setMaxStatements(20);

    }


    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
