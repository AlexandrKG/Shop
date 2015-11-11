package shop;


import goods.Category;
import goods.Goods;
import reports.AccountBook;
import ui.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public interface Shop {
    public AccountBook getAccountBook();
    public ArrayList<Client> getClients();
    public ArrayList<Category> getCategories();
    public ArrayList<Goods> getStore();
    public void initShop(boolean resetData);
    public void addCategory(String data);
//    public boolean existCategory(String data);
    public Category findCategory(String data);
    public void addSubcategory(String categoryName, String subcategory);
    public void addGoods(Goods goods);
    public void buyGoods(Client client, Goods goods, int number);
    public  void buyGoods(Client client, Goods goods, int number, Calendar data);
    public  void addSaleRecord(Client client, Goods goods, int number, Calendar data);
    public Vector<Item> getClientsItem();
    public Vector<Item> getGoodsItem(String category);
    public Vector<Item> getGoodsItem(String category,String subcategory);
    public Vector<String> getCategoriesVector();
    public Vector<String> getSubcategoriesVector(String category);
    public Vector<String> getCategoriesFromGoods();
    public Vector<String> getSubCategoriesFromGoods(String category);
    public boolean soldGoods(Goods gd, int count);
    public void addNewClient(Client client);
}
