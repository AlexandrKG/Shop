package shop;

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

@RunWith(JUnit4.class)
public class ShopDBTests {

    private ShopDB shopDB;
    private DBManager manager;
    @Before
    public void init() {
        manager = new ManagerDerby();
        shopDB = new ShopDB(manager);
    }

    @Test
    public void checkStoreInit() {
        Assert.assertTrue(shopDB.getStore().size() > 0 );
    }

    @Test
    public void checkClientsInit() {
        Assert.assertTrue(shopDB.getClients().size() > 0 );
    }

    @Test
    public void checkCategoriesInit() {
        Assert.assertTrue(shopDB.getCategories().size() > 0 );
    }

    @Test
    public void testAddCategoryName() {
        shopDB.addCategory("NewCategoryName1");
        Assert.assertTrue(shopDB.existCategoryName("NewCategoryName1"));

    }

    @Test
    public void testGetCategory() {
        Category categ = new Category("NewCategoryName2");
        shopDB.getCategories().add(categ);
        Assert.assertEquals(categ, shopDB.getCategory(categ));
    }

    @Test
    public void testAddSubcategory() {
        Category categ = new Category("NewCategoryName3");
        shopDB.getCategories().add(categ);
        shopDB.addSubcategory("NewCategoryName3", "NewSubcategoryName3");
        boolean fl_subcateg = false;
        for(Subcategory sc : categ.getSubcategories()) {
            if(sc.getName().equals("NewSubcategoryName3")) {
                fl_subcateg = true;
                break;
            }
        }
        Assert.assertTrue(fl_subcateg);
    }

    public void testaddGoods() {
        Goods goods = new Goods();
        goods.setName("Goods100");
        shopDB.addGoods(goods);
        boolean fl_goods = false;
        for(Goods g : shopDB.getStore()) {
            if(g == goods) {
                fl_goods = true;
                break;
            }
        }
        Assert.assertTrue(fl_goods);
    }

    public void testaddClient() {
        Client client = new Client();
        client.setName("Client1");
        shopDB.addNewClient(client);
        boolean fl_client = false;
        for(Client c : shopDB.getClients()) {
            if(c == client) {
                fl_client = true;
                break;
            }
        }
        Assert.assertTrue(fl_client);
    }
}
