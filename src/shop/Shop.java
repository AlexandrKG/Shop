package shop;


import domain.Category;
import domain.Client;
import domain.Goods;
import domain.Subcategory;
import reports.AccountBook;
import ui.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public interface Shop {
    public void initShop();
    public AccountBook getAccountBook();
    public ArrayList<Client> getClients();
    public ArrayList<Category> getCategories();
    public ArrayList<Goods> getStore();
    public void addCategory(String data);
    public boolean existCategoryName(String data);
    public Category getCategory(Category category);
    public Category getCategory(long categoryID);
    public Category findCategoryName(String data);
    public void addSubcategory(String categoryName, String subcategory);
    public Subcategory getSubcategory(Category category, long subcategoryID);
    public void addGoods(Goods goods);
    public void buyGoods(Client client, Goods goods, int number);
    public  void buyGoods(Client client, Goods goods, int number, Calendar data);
    public  void addSaleRecord(Client client, Goods goods, int number, Calendar data);
    public Vector<Item> getClientsItem();
    public Vector<Item> getCategoriesItem();
    public Vector<Item> getGoodsItem(String category);
    public Vector<Item> getGoodsItem(String category,String subcategory);
    public Vector<Item> getGoodsItem(Category category,Subcategory subcategory);
    public Vector<String> getCategoriesVector();
    public Vector<String> getSubcategoriesVector(String category);
    public Vector<Item> getSubcategoriesItem(Category category);
    public Vector<String> getCategoriesFromGoods();
    public Vector<String> getSubCategoriesFromGoods(String category);
    public boolean soldGoods(Goods gd, int count);
    public void addNewClient(Client client);
}
