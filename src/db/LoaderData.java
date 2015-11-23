package db;


import goods.Category;
import goods.Goods;
import reports.SaleRecord;
import shop.Client;
import shop.ShopMySQL;
import utl.DataUtl;
import utl.Entry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoaderData {
    private ShopMySQL shopMySQL;
    private DataSourceMySQL dataSource;

    public LoaderData(ShopMySQL shop) {
        shopMySQL = shop;
        dataSource = shop.getDataSourceMySQL();
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
            shopMySQL.getClients().add(temp);
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
            shopMySQL.getCategories().add(temp);
        }
        statement.close();
    }

    private void setSubcategoriesFromDB(Connection connection)  throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        for(int i = 0; i < shopMySQL.getCategories().size(); i++) {
//        for(Category category : shopMySQL.getCategories()) {
            resultSet = statement.executeQuery("SELECT * FROM subcategory " +
                    "WHERE idcategory = " +
                    String.valueOf(shopMySQL.getCategories().get(i).getId()));
            Entry temp;
            while (resultSet.next()) {
                temp = new Entry();
                temp.setId(resultSet.getInt("id"));
                temp.setName(resultSet.getString("name"));
                shopMySQL.getCategories().get(i).addSubcategory(temp);
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
            shopMySQL.getStore().add(g);
        }
        statement.close();

        for(Goods goods : shopMySQL.getStore()) {
            for(Category category : shopMySQL.getCategories()) {
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
            shopMySQL.getAccountBook().getRegisterSale().add(record);
        }
        statement.close();

        for(SaleRecord rec : shopMySQL.getAccountBook().getRegisterSale()) {
            for (Client client : shopMySQL.getClients()) {
                if (client.getIdClient() == rec.getIdClient()) {
                    rec.setClient(client.getName());
                    break;
                }
            }
            for(Goods g : shopMySQL.getStore()) {
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
