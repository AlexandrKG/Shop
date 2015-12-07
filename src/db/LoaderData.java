package db;


import goods.Category;
import goods.Goods;
import reports.SaleRecord;
import shop.Client;
import shop.ShopDB;
import utl.DataUtl;
import utl.Entry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoaderData {
    private ShopDB shopDB;
    private DataSourceConnection dataSource;

    public LoaderData(ShopDB shopDB) {
        this.shopDB = shopDB;
        dataSource = shopDB.getQueriesToDB().getDataSourceConnection();
    }

    private void setClientsFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
        Client temp;
        while (resultSet.next()) {
            temp = new Client(resultSet.getString("name"));
            temp.setIdClient(resultSet.getInt("id"));
            temp.setGender(resultSet.getString("gender"));
            temp.setAge(resultSet.getInt("age"));
            temp.setAddress(resultSet.getString("address"));
            temp.setTelephone(resultSet.getString("telephone"));
            shopDB.getClients().add(temp);
        }
        statement.close();
    }
    private void setCategoriesFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM category");
        Category temp;
        while (resultSet.next()) {
            temp = new Category(resultSet.getString("name"));
            temp.setId(resultSet.getInt("id"));
            shopDB.getCategories().add(temp);
        }
        statement.close();
    }

    private void setSubcategoriesFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        for(int i = 0; i < shopDB.getCategories().size(); i++) {
            resultSet = statement.executeQuery("SELECT * FROM subcategory " +
                    "WHERE idcategory = " +
                    String.valueOf(shopDB.getCategories().get(i).getId()));
            Entry temp;
            while (resultSet.next()) {
                temp = new Entry();
                temp.setId(resultSet.getInt("id"));
                temp.setName(resultSet.getString("name"));
                shopDB.getCategories().get(i).addSubcategory(temp);
            }
        }
        statement.close();
    }

    private void setGoodsFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        String fields = "id, idcategory, idsubcategory, name, " +
                "number, price";
        ResultSet resultSet = statement.executeQuery("SELECT " + fields +
                " FROM goods INNER JOIN shop " +
                "ON id = idgoods");
        Goods g;
        while (resultSet.next()) {
            g = new Goods();
            g.setIdGoods(resultSet.getInt("id"));
            g.setIdCategory(resultSet.getInt("idcategory"));
            g.setIdSubcategory(resultSet.getInt("idsubcategory"));
            g.setName(resultSet.getString("name"));
            g.setNumber(resultSet.getInt("number"));
            g.setPrice(resultSet.getInt("price"));
            shopDB.getStore().add(g);
        }
        statement.close();

        for(Goods goods : shopDB.getStore()) {
            for(Category category : shopDB.getCategories()) {
                if(category.getId() == goods.getIdCategory()) {
                    goods.setCategory(category.getName());
                    for(Entry e : category.getSubcategories()) {
                        if(e.getId() == goods.getIdSubcategory()) {
                            goods.setSubcategory(e.getName());
                            break;
                        }
                    }
                    break;
                }
            }
        }

    }

    private void setTransactionsFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM trade");
        SaleRecord record;
        while (resultSet.next()) {
            record = new SaleRecord();
            record.setIdRecord(resultSet.getInt("id"));
            record.setIdClient(resultSet.getInt("idclient"));
            record.setIdGoods(resultSet.getInt("idgoods"));
            record.setNumber(resultSet.getInt("number"));
            record.setCost(resultSet.getInt("price"));
            record.setData(DataUtl.getStringData(resultSet.getDate("data")));
            shopDB.getAccountBook().getRegisterSale().add(record);
        }
        statement.close();

        for(SaleRecord rec : shopDB.getAccountBook().getRegisterSale()) {
            for (Client client : shopDB.getClients()) {
                if (client.getIdClient() == rec.getIdClient()) {
                    rec.setClient(client.getName());
                    break;
                }
            }
            for(Goods g : shopDB.getStore()) {
                if(g.getIdGoods() == rec.getIdGoods()) {
                    rec.setGoods(g.getName());
                    break;
                }
            }
        }
    }

    public void initDataFromBD() {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start init FROM BD!");
            }
            setClientsFromDB(connection);
            setCategoriesFromDB(connection);
            setSubcategoriesFromDB(connection);
            setGoodsFromDB(connection);
            setTransactionsFromDB(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection close. BD was read!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
