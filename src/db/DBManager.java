package db;


import domain.Category;
import domain.Goods;
import reports.SaleRecord;
import domain.Client;
import utl.DataUtl;
import domain.Entry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class  DBManager implements DBCoordinator{

    private DataSourceConnection dataSourceConnection;

    public DBManager() {

    }

    public abstract void initData();

    public DataSourceConnection getDataSourceConnection() {
        return dataSourceConnection;
    }

    public void setDataSourceConnection(DataSourceConnection dataSourceConnection) {
        this.dataSourceConnection = dataSourceConnection;
    }

    @Override
    public void addNewClient(Client client) {
        final String INSERT_NEW_CLIENTS =
                "INSERT INTO clients(name,gender,age,address,telephone) VALUES(?,?,?,?,?)";
        final String GET_CLIENT_ID = "SELECT * FROM clients WHERE name = ? AND address = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT client!");
            }

            preparedStatement = connection.prepareStatement(INSERT_NEW_CLIENTS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setString(5, client.getTelephone());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_CLIENT_ID);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            ResultSet resultSet = preparedStatement.executeQuery();
            int clientID = 0;
            while (resultSet.next()) {
                clientID = resultSet.getInt("id");
            }
            client.setIdClient(clientID);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addNewCategory(Category categ) {
        final String INSERT_NEW_CATEGORY =
                "INSERT INTO category(name) VALUES(?)";
        final String GET_CATEGORY_ID = "SELECT * FROM category WHERE name = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT category!");
            }

            preparedStatement = connection.prepareStatement(INSERT_NEW_CATEGORY);
            preparedStatement.setString(1, categ.getName());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_CATEGORY_ID);
            preparedStatement.setString(1, categ.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int categID = 0;
            while (resultSet.next()) {
                categID = resultSet.getInt("id");
            }
            categ.setId(categID);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addNewSubcategory(long categoryID, Entry subcategory) {
        final String INSERT_NEW_SUBCATEGORY =
                "INSERT INTO subcategory(idcategory,name) VALUES(?,?)";
        final String GET_SUBCATEGORY_ID = "SELECT * FROM subcategory " +
                "WHERE idcategory = ? AND name = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT subcategory!");
            }

            preparedStatement = connection.prepareStatement(INSERT_NEW_SUBCATEGORY);
            preparedStatement.setInt(1, (int)categoryID);
            preparedStatement.setString(2, subcategory.getName());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_SUBCATEGORY_ID);
            preparedStatement.setInt(1, (int)categoryID);
            preparedStatement.setString(2, subcategory.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int subcategID = 0;
            while (resultSet.next()) {
                subcategID = resultSet.getInt("id");
            }
            subcategory.setId(subcategID);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addNewGoods(Goods goods) {
        final String INSERT_NEW_GOODS =
                "INSERT INTO goods(idcategory, idsubcategory, name) VALUES(?,?,?)";
        final String GET_GOODS_ID = "SELECT * FROM goods " +
                "WHERE idcategory = ? AND idsubcategory = ? AND name = ?";
        final String INSERT_SHOP_NEW_GOODS =
                "INSERT INTO shop(idgoods, number, price) VALUES(?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT goods!");
            }

            preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
            preparedStatement.setInt(1, (int)goods.getCategory().getId());
            preparedStatement.setInt(2, (int)goods.getSubcategory().getId());
            preparedStatement.setString(3, goods.getName());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_GOODS_ID);
            preparedStatement.setInt(1, (int)goods.getCategory().getId());
            preparedStatement.setInt(2, (int)goods.getSubcategory().getId());
            preparedStatement.setString(3, goods.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int goodsID = 0;
            while (resultSet.next()) {
                goodsID = resultSet.getInt("id");
            }
            goods.setIdGoods(goodsID);

            preparedStatement = connection.prepareStatement(INSERT_SHOP_NEW_GOODS);
            preparedStatement.setInt(1, goodsID);
            preparedStatement.setInt(2, goods.getNumber());
            preparedStatement.setDouble(3,goods.getPrice());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void buyGoods(Goods goods) {
        final String UPDATE_GOODS_NUMBER =
                "UPDATE shop SET number = ?,sold = ? WHERE idgoods = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start UPDATE domain count in shop!");
            }

            preparedStatement = connection.prepareStatement(UPDATE_GOODS_NUMBER);
            preparedStatement.setInt(1, goods.getNumber());
            preparedStatement.setInt(2, goods.getSold());
            preparedStatement.setInt(3, (int) goods.getIdGoods());
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addSaleRecord(SaleRecord saleRecord) {
        final String INSERT_NEW_RECORD =
                "INSERT INTO trade(idgoods,idclient,data,number,price) VALUES(?,?,?,?,?)";
        final String GET_RECORD_ID = "SELECT * FROM trade " +
                "WHERE idgoods = ? AND idclient = ? AND data = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceConnection.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT record in trade!");
            }
            String strDate = DataUtl.txtData(saleRecord.getData());
            preparedStatement = connection.prepareStatement(INSERT_NEW_RECORD);
            preparedStatement.setInt(1, (int) saleRecord.getIdGoods());
            preparedStatement.setInt(2, (int)saleRecord.getIdClient());
            preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
            preparedStatement.setInt(4, saleRecord.getNumber());
            preparedStatement.setDouble(5, saleRecord.getCost());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_RECORD_ID);
            preparedStatement.setInt(1, (int) saleRecord.getIdGoods());
            preparedStatement.setInt(2, (int)saleRecord.getIdClient());
            preparedStatement.setDate(3, DataUtl.setDataSQL(strDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            int recordID = 0;
            while (resultSet.next()) {
                recordID = resultSet.getInt("id");
            }
            saleRecord.setIdRecord(recordID);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
