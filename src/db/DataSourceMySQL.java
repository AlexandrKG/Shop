package db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceMySQL {

    private static DataSourceMySQL     datasource;
    private ComboPooledDataSource cpds;

    public DataSourceMySQL(boolean choiceDB) throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        if(choiceDB) {
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

//    public DataSourceMySQL getInstance() throws IOException, SQLException, PropertyVetoException {
//        if (datasource == null) {
//            datasource = new DataSourceMySQL();
//            return datasource;
//        } else {
//            return datasource;
//        }
//    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
