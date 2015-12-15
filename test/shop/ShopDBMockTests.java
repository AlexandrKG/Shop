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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class ShopDBMockTests {

    private ShopDB shopDB;
    private DBManager mockedManager;
    private ShopDB spyShopDB;

    @Before
    public void init() {
        mockedManager = mock(DBManager.class);
        doNothing().when(mockedManager).initData();
        shopDB = new ShopDB(mockedManager);
        spyShopDB = spy(shopDB);
        doNothing().when(spyShopDB).initShop();
    }

    @Test
    public void checkAddGoods() {
        Goods goods = new Goods();
        doNothing().when(mockedManager).addNewGoods(goods);
        goods.setName("MockGoods");
        spyShopDB.addGoods(goods);
        boolean fl_goods = false;
        for (Goods g : spyShopDB.getStore()) {
            if (g == goods) {
                fl_goods = true;
                break;
            }
        }
        Assert.assertTrue(fl_goods);

    }

    @Test
    public void checkAddCategory() {
        String nameCateg = "TestCateg";
        spyShopDB.addCategory(nameCateg);
        Assert.assertTrue(spyShopDB.existCategoryName(nameCateg));
    }

    @Test
    public void checkAddSubcategory() {
        Category categ = new Category("NewCategoryName");
        spyShopDB.getCategories().add(categ);
        spyShopDB.addSubcategory("NewCategoryName", "NewSubcategoryName");
        boolean fl_subcateg = false;
        for(Subcategory sc : categ.getSubcategories()) {
            if(sc.getName().equals("NewSubcategoryName")) {
                fl_subcateg = true;
                break;
            }
        }
        Assert.assertTrue(fl_subcateg);
    }

    @Test
    public void testAddClient() {
        Client client = new Client();
        client.setName("Client");
        spyShopDB.addNewClient(client);
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
