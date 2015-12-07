package shop;


import db.*;
import goods.Category;
import goods.Goods;
import reports.AccountBook;
import reports.SaleRecord;
import ui.Item;
import utl.Entry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ShopDB implements Shop{

    private static final String flagDB = "Derby"; // "MySQL" or "Derby"
    private ArrayList<Goods> store;
    private AccountBook accountBook;
    private ArrayList<Client> clients;
    private  ArrayList<Category> categories;
    private Queries queriesToDB;

    public ShopDB() {
        store = new ArrayList<>();
        accountBook = new AccountBook();
        clients = new ArrayList<>();
        categories = new ArrayList<>();
        queriesToDB = new Queries(this);

        initShop(false);
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

    public String getFlagDB() {
        return flagDB;
    }

    public Queries getQueriesToDB() {
        return queriesToDB;
    }

    @Override
    public void initShop(boolean resetData) {
        queriesToDB.initData(resetData);
    }

    @Override
    public void addCategory(String data) {
        if(categories != null) {
            if(existCategory(data)) {
                return;
            }
        }
        Category category = new Category(data);
        queriesToDB.addNewCategory(category);
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
            queriesToDB.addNewSubcategory(category.getId(), e);
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
                queriesToDB.addNewGoods(goods);
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

}
