package db;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceConnection {

    private static DataSourceConnection datasource;
    private ComboPooledDataSource cpds;

    public DataSourceConnection(String fileName) {
        try {
            setConnection(fileName);
        } catch (IOException | SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(String fileName)
            throws IOException, SQLException, PropertyVetoException {

        InputStream inputStream;
        Properties property = new Properties();
        inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        property.load(inputStream);

        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(property.getProperty("dbDriverClass"));
        cpds.setJdbcUrl(property.getProperty("dbJdbcUrl"));
        cpds.setUser(property.getProperty("dbUser"));
        cpds.setPassword(property.getProperty("dbPassword"));

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(10);
        cpds.setMaxStatements(20);

    }


    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
