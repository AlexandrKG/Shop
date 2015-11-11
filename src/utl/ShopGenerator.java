package utl;


import goods.Bird;
import goods.Coop;
import goods.Goods;
import shop.Client;
import shop.ShopLocal;

public class ShopGenerator {

    private ShopLocal shop;
    public ShopGenerator(ShopLocal shop) {
        this.shop = shop;
    }

    public  void initShop() {
        initStore();
        initClients();
        saleGenerator();
    }

    private  void initStore() {

        Goods gd;
        gd = new Bird("Canary");
        gd.setSubcategory("Song-bird");
        gd.setNumber(50);
        gd.setPrice(10.0);
        setGoods(gd);

        gd = new Bird("Eagl");
        gd.setSubcategory("Wild");
        gd.setNumber(30);
        gd.setPrice(50.0);
        setGoods(gd);

        gd = new Bird("Duck");
        gd.setSubcategory("Poultry");
        gd.setNumber(70);
        gd.setPrice(5.0);
        setGoods(gd);

        gd = new Bird("Parrot");
        gd.setSubcategory("Wild");
        gd.setNumber(40);
        gd.setPrice(15.0);
        setGoods(gd);

        gd = new Bird("Flamingo");
        gd.setSubcategory("Wild");
        gd.setNumber(30);
        gd.setPrice(150.0);
        setGoods(gd);

        gd = new Coop();
        gd.setName("Gold Coop");
        gd.setSubcategory("ForWildBirds");
        gd.setNumber(3);
        gd.setPrice(100.0);
        setGoods(gd);

    }

    private  void setGoods(Goods gd) {
        shop.getStore().add(gd);
        shop.addCategory(gd.getCategory());
        shop.addSubcategory(gd.getCategory(), gd.getSubcategory());
    }

    private  void initClients() {
        Client temp;
        temp = new Client("Anna");
        temp.setIdClient(System.currentTimeMillis());
        temp.setAddress("str.Pobedi,19");
        temp.setAge(20);
        temp.setGender("Female");
        shop.getClients().add(temp);

        temp = new Client("Petr");
        temp.setIdClient(System.currentTimeMillis());
        temp.setAddress("str.Lesi Ukrainki,10");
        temp.setAge(35);
        temp.setGender("Male");
        shop.getClients().add(temp);

        temp = new Client("Nikolay");
        temp.setIdClient(System.currentTimeMillis());
        temp.setAddress("str.Tampere,3");
        temp.setAge(19);
        temp.setGender("Male");
        shop.getClients().add(temp);
    }

    private  void saleGenerator() {
        Bird bird;
        bird = new Bird("Duck");
        bird.setNumber(1);
        bird.setPrice(5.0);
        shop.buyGoods(shop.getClients().get(0), bird, 1, DataUtl.setData("12/07/2015"));

        bird = new Bird("Eagl");
        bird.setNumber(1);
        bird.setPrice(50.0);
        shop.buyGoods(shop.getClients().get(0), bird, 1, DataUtl.setData("13/07/2015"));

        bird = new Bird("Flamingo");
        bird.setNumber(1);
        bird.setPrice(150.0);
        shop.buyGoods(shop.getClients().get(1), bird, 1, DataUtl.setData("14/07/2015"));

        bird = new Bird("Duck");
        bird.setNumber(1);
        bird.setPrice(5.0);
        shop.buyGoods(shop.getClients().get(2), bird, 1, DataUtl.setData("15/07/2015"));
    }
}
