package shop;


import db.*;
import domain.*;
import reports.AccountBook;
import reports.SaleRecord;
import ui.Item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ShopDB implements Shop{

    private ArrayList<Goods> store;
    private AccountBook accountBook;
    private ArrayList<Client> clients;
    private  ArrayList<Category> categories;
    private DBManager queriesToDB;
    private LoaderData loaderData;

    public ShopDB(DBManager managerDB) {
        store = new ArrayList<>();
        accountBook = new AccountBook();
        clients = new ArrayList<>();
        categories = new ArrayList<>();
        queriesToDB = managerDB;

        initShop();
    }

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

    public DBManager getQueriesToDB() {
        return queriesToDB;
    }

    @Override
    public void initShop() {
        queriesToDB.initData();
        loaderData = new LoaderData(this);
        loaderData.initDataFromBD();
    }

    @Override
    public void addCategory(String data) {
        if(categories != null) {
            if(existCategoryName(data)) {
                return;
            }
        }
        Category category = new Category(data);
        queriesToDB.addNewCategory(category);
        categories.add(category);
    }

    @Override
    public boolean existCategoryName(String data) {
        if(findCategoryName(data) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Category findCategoryName(String data) {
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
    public Category getCategory(Category category) {
        Category result = null;
        if(categories != null) {
            for (Category c : categories) {
                if (c == category) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void addSubcategory(String categoryName, String subcategory) {
        Category category = findCategoryName(categoryName);
        if(category != null) {
            category.addSubcategory(subcategory);
            Entry e = category.getSubcategory(subcategory);
            queriesToDB.addNewSubcategory(category.getId(), e);
        } else {
            throw new IllegalStateException(categoryName + " category doesn't exist");
        }
    }

    @Override
    public void addGoods(Goods goods) {
        queriesToDB.addNewGoods(goods);
        store.add(goods);
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
        queriesToDB.addSaleRecord(sr);
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
    public Vector<Item> getGoodsItem(Category category,Subcategory subcategory) {
        Vector<Item> result = new Vector<>();
        for(Goods g  : store) {
            if(g.getCategory() == category && g.getSubcategory() == subcategory) {
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

    public Vector<Item> getCategoriesItem() {
        Vector<Item> result = new Vector<>();
        for(Category c : getCategories()) {
            result.add(new Item(c, c.getName()));
        }
        return result;
    }

    @Override
    public Vector<String> getSubcategoriesVector(String category) {
        Vector<String> result = new Vector<>();
        Category c = findCategoryName(category);
        if(c != null) {
            for (Entry e : c.getSubcategories()) {
                if (e != null) {
                    result.add(e.getName());
                }
            }
        }
        return result;
    }

    public Vector<Item> getSubcategoriesItem(Category category) {
        Vector<Item> result = new Vector<>();
        if(category != null) {
            for (Subcategory sc : category.getSubcategories()) {
                result.add(new Item(sc, sc.getName()));
            }
        }
        return result;
    }

    @Override
    public Vector<String> getCategoriesFromGoods() {
        Vector<String> result = new Vector<>();
        TreeSet<String> temp = new TreeSet<>();
        for(Goods g : store) {
            temp.add(g.getCategory().getName());
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
            if(g.getCategory().getName().equals(category)) {
                temp.add(g.getSubcategory().getName());
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
            queriesToDB.buyGoods(gd);
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
        queriesToDB.addNewClient(client);
        clients.add(client);
    }

    @Override
    public Category getCategory(long categoryID) {
        Category result = null;
        if(categories != null) {
            for (Category c : categories) {
                if (c.getId() == categoryID) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Subcategory getSubcategory(Category category, long subcategoryID) {
        Subcategory result = null;
        if(category != null) {
            result = category.getSubcategory(subcategoryID);
        }
        return result;
    }
}
