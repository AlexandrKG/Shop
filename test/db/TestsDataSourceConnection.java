package db;

import db.DBManager;
import db.ManagerDerby;
import domain.Category;
import domain.Client;
import domain.Goods;
import domain.Subcategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class TestsDataSourceConnection {

    DataSourceConnection dataSourceConnection;
    Connection connection;
    Connection[] connections;
    @Before
    public void init() {
        dataSourceConnection = new DataSourceConnection("resources/derby.properties");
        connections = new Connection[100];
    }

    @Test
    public void testOpenConnection() {
        connection = null;
        try {
            connection = dataSourceConnection.getConnection();
            Assert.assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCloseConnection() {
        connection = null;
        try {
            connection = dataSourceConnection.getConnection();
            connection.close();
            Assert.assertTrue(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testOpenArrayConnections() {
        try {
            for(int i = 0; i < 100; i++) {
                connections[i] = dataSourceConnection.getConnection();
                Assert.assertFalse(connections[i].isClosed());
                connections[i].close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
