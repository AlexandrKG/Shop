package db;


import shop.ShopMySQL;
import utl.DataUtl;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopDerbyInit {

    private DataSourceMySQL dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ShopDerbyInit(ShopMySQL shop) {
        dataSource = shop.getDataSourceMySQL();
    }

    private static final String CREATE_CATEGORY = "CREATE TABLE category " +
            " (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS " +
            "AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            " name VARCHAR(100) NOT NULL)";

    private static final String CREATE_CLIENTS = "CREATE TABLE clients " +
            " (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS " +
            "AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            " name VARCHAR(100) NOT NULL," +
            " gender VARCHAR(6) NOT NULL," +
            " age INT NOT NULL," +
            " address VARCHAR(200) NOT NULL," +
            " telephone VARCHAR(20) NOT NULL" +
            ")";

    private static final String CREATE_GOODS = "CREATE TABLE goods " +
            " (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS " +
            "AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            " idcategory INT NOT NULL," +
            " idsubcategory INT NOT NULL," +
            " name VARCHAR(100) NOT NULL" +
            ")";

    private static final String CREATE_SHOP = "CREATE TABLE shop " +
            " (idgoods INT NOT NULL," +
            " number INT NOT NULL," +
            " price DECIMAL(8,2) NOT NULL," +
            " sold INT" +
            ")";

    private static final String CREATE_SUBCATEGORY = "CREATE TABLE subcategory " +
            " (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS " +
            "AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            " idcategory INT NOT NULL," +
            " name VARCHAR(100) NOT NULL)";

    private static final String CREATE_TRADE = "CREATE TABLE trade " +
            " (id INT PRIMARY KEY NOT NULL GENERATED ALWAYS " +
            "AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            " idgoods INT NOT NULL," +
            " idclient INT NOT NULL," +
            " data DATE NOT NULL," +
            " number INT NOT NULL," +
            " price DECIMAL(8,2) NOT NULL" +
            ")";

    private static final String INSERT_NEW_CLIENTS =
            "INSERT INTO clients(name,age,gender,address,telephone) VALUES(?,?,?,?,?)";
    private static final String INSERT_NEW_CATEGORIES =
            "INSERT INTO category(name) VALUES(?)";
    private static final String INSERT_NEW_SUBCATEGORIES =
            "INSERT INTO subcategory(idcategory,name) VALUES(?,?)";
    private static final String INSERT_NEW_GOODS =
            "INSERT INTO goods(idcategory,idsubcategory,name) VALUES(?,?,?)";
    private static final String INSERT_NEW_SHOPS =
            "INSERT INTO shop(idgoods,number,price) VALUES(?,?,?)";
    private static final String INSERT_NEW_TRADE =
            "INSERT INTO trade(idgoods,idclient,data,number,price) VALUES(?,?,?,?,?)";


    private void createTables() throws SQLException {
        System.out.println("START create TABLES");

        preparedStatement = connection.prepareStatement(CREATE_CATEGORY);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(CREATE_CLIENTS);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(CREATE_GOODS);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(CREATE_SHOP);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(CREATE_SUBCATEGORY);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(CREATE_TRADE);
        preparedStatement.execute();

        System.out.println("TABLES was created!");
    }

    private void initCategories() throws SQLException {

        preparedStatement = connection.prepareStatement(INSERT_NEW_CATEGORIES);
        preparedStatement.setString(1, "Birds");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_CATEGORIES);
        preparedStatement.setString(1, "Coops");
        preparedStatement.execute();

        System.out.println("CATEGORIES was initialized!");
    }
    private void initClients() throws SQLException {

        preparedStatement = connection.prepareStatement(INSERT_NEW_CLIENTS);
        preparedStatement.setString(1, "Anna");
        preparedStatement.setInt(2, 30);
        preparedStatement.setString(3, "Female");
        preparedStatement.setString(4, "str.Pobedi,19");
        preparedStatement.setString(5, "351-77-76");
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(INSERT_NEW_CLIENTS);
        preparedStatement.setString(1, "Petr");
        preparedStatement.setInt(2, 30);
        preparedStatement.setString(3, "Male");
        preparedStatement.setString(4, "str.Lesi Ukrainki,10");
        preparedStatement.setString(5, "333-55-55");
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(INSERT_NEW_CLIENTS);
        preparedStatement.setString(1, "Nikolay");
        preparedStatement.setInt(2, 30);
        preparedStatement.setString(3, "Male");
        preparedStatement.setString(4, "str.Tampere,3");
        preparedStatement.setString(5, "333-22-22");
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(INSERT_NEW_CLIENTS);
        preparedStatement.setString(1, "Leonid");
        preparedStatement.setInt(2, 30);
        preparedStatement.setString(3, "Male");
        preparedStatement.setString(4, "str.Builders,3");
        preparedStatement.setString(5, "333-11-11");
        preparedStatement.execute();

        System.out.println("CLIENTS was initialized!");

    }

    private void initSubcategories() throws SQLException {

        preparedStatement = connection.prepareStatement(INSERT_NEW_SUBCATEGORIES);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Song-bird");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SUBCATEGORIES);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Wild");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SUBCATEGORIES);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Poultry");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SUBCATEGORIES);
        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "ForWildBirds");
        preparedStatement.execute();

        System.out.println("SUBCATEGORIES was initialized!");
    }

    private void initGoods() throws SQLException {

        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 1);
        preparedStatement.setString(3, "Canary");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        preparedStatement.setString(3, "Eagl");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 3);
        preparedStatement.setString(3, "Duck");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        preparedStatement.setString(3, "Parrot");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        preparedStatement.setString(3, "Flamingo");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
        preparedStatement.setInt(1, 2);
        preparedStatement.setInt(2, 4);
        preparedStatement.setString(3, "Gold Coop");
        preparedStatement.execute();

        System.out.println("GOODS was initialized!");
    }

    private void initShop() throws SQLException {

        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 50);
        preparedStatement.setDouble(3, 10.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 2);
        preparedStatement.setInt(2, 30);
        preparedStatement.setDouble(3, 50.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 3);
        preparedStatement.setInt(2, 70);
        preparedStatement.setDouble(3, 5.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 4);
        preparedStatement.setInt(2, 40);
        preparedStatement.setDouble(3, 15.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 5);
        preparedStatement.setInt(2, 30);
        preparedStatement.setDouble(3, 150.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_SHOPS);
        preparedStatement.setInt(1, 6);
        preparedStatement.setInt(2, 3);
        preparedStatement.setDouble(3, 100.0);
        preparedStatement.execute();

        System.out.println("SHOPS was initialized!");
    }

    private void initTrade() throws SQLException {

        String strDate;
        preparedStatement = connection.prepareStatement(INSERT_NEW_TRADE);
        strDate = "12/07/2015";
        preparedStatement.setInt(1, 3);
        preparedStatement.setInt(2, 1);
        preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
        preparedStatement.setInt(4, 1);
        preparedStatement.setDouble(5, 5.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_TRADE);
        strDate = "13/07/2015";
        preparedStatement.setInt(1, 2);
        preparedStatement.setInt(2, 2);
        preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
        preparedStatement.setInt(4, 1);
        preparedStatement.setDouble(5, 50.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_TRADE);
        strDate = "14/07/2015";
        preparedStatement.setInt(1, 5);
        preparedStatement.setInt(2, 3);
        preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
        preparedStatement.setInt(4, 1);
        preparedStatement.setDouble(5, 150.0);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(INSERT_NEW_TRADE);
        strDate = "15/07/2015";
        preparedStatement.setInt(1, 3);
        preparedStatement.setInt(2, 4);
        preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
        preparedStatement.setInt(4, 1);
        preparedStatement.setDouble(5, 5.0);
        preparedStatement.execute();

        System.out.println("TRADE was initialized!");
    }


    public void initData() {

        connection = null;
        preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start initBD!");
            }
            createTables();
            initCategories();
            initClients();
            initSubcategories();
            initGoods();
            initShop();
            initTrade();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close. BD was initialized!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
