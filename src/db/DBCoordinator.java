package db;

import goods.Category;
import goods.Goods;
import reports.SaleRecord;
import shop.Client;
import utl.Entry;

public interface DBCoordinator {

    public void addNewClient(Client client);
    public void addNewCategory(Category categ);
    public void addNewSubcategory(long categoryID, Entry subcategory);
    public void addNewGoods(Goods goods);
    public void buyGoods(Goods goods);
    public void addSaleRecord(SaleRecord saleRecord);

}
