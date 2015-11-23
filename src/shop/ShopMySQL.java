package shop;


import db.DataSourceMySQL;
import db.LoaderData;
import db.ShopDerbyInit;
import db.ShopSQLGenerator;
import goods.Category;
import goods.Goods;
import reports.AccountBook;
import reports.SaleRecord;
import ui.Item;
import utl.DataUtl;
import utl.Entry;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ShopMySQL implements Shop{

    private final boolean flagMySQL = false; // false=Derby true=MySQL
    private ArrayList<Goods> store;
    private AccountBook accountBook;
    private ArrayList<Client> clients;
    private  ArrayList<Category> categories;
    private DataSourceMySQL dataSourceMySQL;
    private ShopSQLGenerator mySQLDB;
    private ShopDerbyInit derbyDB;
    private LoaderData loaderData;

    public ArrayList<Goods> getStore() {
        return store;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public DataSourceMySQL getDataSourceMySQL() {
        return dataSourceMySQL;
    }

    public ShopMySQL() {
        store = new ArrayList<>();
        accountBook = new AccountBook();
        clients = new ArrayList<>();
        categories = new ArrayList<>();
        try {
            dataSourceMySQL = new DataSourceMySQL(flagMySQL);
        } catch (IOException | SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
        initShop(false);
    }


    @Override
    public void initShop(boolean resetData) {
        if(!flagMySQL) {
            derbyDB = new ShopDerbyInit(this);
            derbyDB.initData();
        } else if(resetData) {
            mySQLDB = new ShopSQLGenerator(this);
            mySQLDB.initData();
        }
        loaderData = new LoaderData(this);
        loaderData.initDataFromBD();
    }

    @Override
    public void addCategory(String data) {
        if(categories != null) {
            if(existCategory(data)) {
                return;
            }
        }
        Category category = new Category(data);
        addNewCategoryToDB(category);
        categories.add(category);
    }

    private boolean existCategory(String data) {
        if(findCategory(data) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Category findCategory(String data) {
        Category result = null;
        if(categories != null) {
            for (Category c : categories) {
                if (c.getName().equals(data)) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void addSubcategory(String categoryName, String subcategory) {
        Category category = findCategory(categoryName);
        if(category != null) {
            category.addSubcategory(subcategory);
            Entry e = category.getSubcategory(subcategory);
            addNewSubcategoryToDB(category.getId(), e);
        } else {
            throw new IllegalStateException(categoryName + " category doesn't exist");
        }
    }

    @Override
    public void addGoods(Goods goods) {
        Category categ = findCategory(goods.getCategory());
        if(categ != null) {
            goods.setIdCategory(categ.getId());
            Entry subcateg = categ.getSubcategory(goods.getSubcategory());
            if(subcateg != null) {
                goods.setIdSubcategory(subcateg.getId());
                addNewGoodsToDB(goods);
                store.add(goods);
            }
        }
    }

    @Override
    public void buyGoods(Client client, Goods goods, int number) {
        Calendar today = new GregorianCalendar();
        today.setTime(new java.util.Date());
        try {
            if (soldGoods(goods, number)) {
                Constructor constr = goods.getClass().getConstructor();
                Goods g = (Goods) constr.newInstance();
                g.setName(goods.getName());
                g.setIdCategory(goods.getIdCategory());
                g.setIdSubcategory(goods.getIdSubcategory());
                g.setIdGoods(goods.getIdGoods());
                g.setCategory(goods.getCategory());
                g.setSubcategory(goods.getSubcategory());
                g.setNumber(number);
                g.setPrice(goods.getPrice());
                client.buyGoods(g);
                addSaleRecord(client, g, number, today);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buyGoods(Client client, Goods goods, int number, Calendar data) {
        if (clients.contains(client)) {
            if(soldGoods(goods, number)) {
                goods.setNumber(number);
                client.buyGoods(goods);
                addSaleRecord(client, goods, number, data);
            }
        } else {
            System.out.println("We haven't client " + client);
        }
    }

    @Override
    public void addSaleRecord(Client client, Goods goods, int number, Calendar data) {
        SaleRecord sr = new SaleRecord();
        sr.setClient(client.getName());
        sr.setGoods(goods.getName());
        sr.setNumber(number);
        sr.setCost(goods.getPrice());
        sr.setData(data);
        sr.setIdClient(client.getIdClient());
        sr.setIdGoods(goods.getIdGoods());
        addSaleRecordToDB(sr);
        accountBook.setRecord(sr);
    }

    @Override
    public Vector<Item> getClientsItem() {
        Vector<Item> result = new Vector<>();
        for(Client cl : clients) {
            result.add(new Item(cl, cl.getName()));
        }
        return result;
    }

    @Override
    public Vector<Item> getGoodsItem(String category) {
        Vector<Item> result = new Vector<>();
        for(Goods g  : store) {
            if(g.getCategory().equals(category)) {
                result.add(new Item(g, g.getName()));
            }
        }
        return result;
    }

    @Override
    public Vector<Item> getGoodsItem(String category, String subcategory) {
        Vector<Item> result = new Vector<>();
        for(Goods g  : store) {
            if(g.getCategory().equals(category) && g.getSubcategory().equals(subcategory)) {
                result.add(new Item(g, g.getName()));
            }
        }
        return result;
    }

    @Override
    public Vector<String> getCategoriesVector() {
        Vector<String> result = new Vector<>();
        for (Category c : getCategories()) {
            result.add(c.getName());
        }
        return result;
    }

    @Override
    public Vector<String> getSubcategoriesVector(String category) {
        Vector<String> result = new Vector<>();
        Category c = findCategory(category);
        if(c != null) {
            for (Entry e : c.getSubcategories()) {
                if (e != null) {
                    result.add(e.getName());
                }
            }
        }
        return result;
    }

    @Override
    public Vector<String> getCategoriesFromGoods() {
        Vector<String> result = new Vector<>();
        TreeSet<String> temp = new TreeSet<>();
        for(Goods g : store) {
            temp.add(g.getCategory());
        }
        for(String str : temp) {
            result.add(str);
        }
        return result;
    }

    @Override
    public Vector<String> getSubCategoriesFromGoods(String category) {
        Vector<String> result = new Vector<>();
        TreeSet<String> temp = new TreeSet<>();
        for(Goods g : store) {
            if(g.getCategory().equals(category)) {
                temp.add(g.getSubcategory());
            }
        }
        for(String str : temp) {
            result.add(str);
        }
        return result;
    }

    @Override
    public boolean soldGoods(Goods gd, int count) {

        int temp = gd.getNumber() - count;
        if (temp >= 0) {
            gd.setNumber(temp);
            gd.setSold(count + gd.getSold());
            buyGoodsFromDB(gd);
            System.out.println("We have sold " + String.valueOf(count)
                    + " " + gd.getName());
            return true;
        } else {
            System.out.println("We can't sail so many " + gd.getName());
            return false;
        }
    }

    @Override
    public void addNewClient(Client client) {
        addNewClientToDB(client);
        clients.add(client);
    }

    private void addNewClientToDB(Client client) {
        final String INSERT_NEW_CLIENTS =
                "INSERT INTO clients(name,gender,age,address,telephone) VALUES(?,?,?,?,?)";
        final String GET_CLIENT_ID = "SELECT * FROM clients WHERE name = ? AND address = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
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

    private void addNewCategoryToDB(Category categ) {
        final String INSERT_NEW_CATEGORY =
                "INSERT INTO category(name) VALUES(?)";
        final String GET_CATEGORY_ID = "SELECT * FROM category WHERE name = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
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

    private void addNewSubcategoryToDB(long categoryID, Entry subcategory) {
        final String INSERT_NEW_SUBCATEGORY =
                "INSERT INTO subcategory(idcategory,name) VALUES(?,?)";
        final String GET_SUBCATEGORY_ID = "SELECT * FROM subcategory " +
                "WHERE idcategory = ? AND name = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
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

    private void addNewGoodsToDB(Goods goods) {
        final String INSERT_NEW_GOODS =
                "INSERT INTO goods(idcategory, idsubcategory, name) VALUES(?,?,?)";
        final String GET_GOODS_ID = "SELECT * FROM goods " +
                "WHERE idcategory = ? AND idsubcategory = ? AND name = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start INSERT goods!");
            }

            preparedStatement = connection.prepareStatement(INSERT_NEW_GOODS);
            preparedStatement.setInt(1, (int)goods.getIdCategory());
            preparedStatement.setInt(2, (int)goods.getIdSubcategory());
            preparedStatement.setString(3, goods.getName());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(GET_GOODS_ID);
            preparedStatement.setInt(1, (int)goods.getIdCategory());
            preparedStatement.setInt(2, (int)goods.getIdSubcategory());
            preparedStatement.setString(3, goods.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int goodsID = 0;
            while (resultSet.next()) {
                goodsID = resultSet.getInt("id");
            }
            goods.setIdGoods(goodsID);

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

    private void buyGoodsFromDB(Goods goods) {
        final String UPDATE_GOODS_NUMBER =
                "UPDATE shop SET number = ?,sold = ? WHERE idgoods = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection open. Start UPDATE goods count in shop!");
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

    public void addSaleRecordToDB(SaleRecord saleRecord) {
        final String INSERT_NEW_RECORD =
                "INSERT INTO trade(idgoods,idclient,data,number,price) VALUES(?,?,?,?,?)";
        final String GET_RECORD_ID = "SELECT * FROM trade " +
                "WHERE idgoods = ? AND idclient = ? AND data = ?";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = dataSourceMySQL.getConnection();
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
