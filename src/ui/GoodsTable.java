package ui;


import domain.Goods;
import shop.Shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GoodsTable extends JPanel {

    private Shop shop;
    private DefaultTableModel mod;
    private  String[] columnNames = {"Name","Category","Subcategory","Number","Price","Sold"};

    public  GoodsTable(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        Object[][] dataTable = setDataTable();
        mod = new DefaultTableModel(dataTable, columnNames);
        JTable tTransactions = new JTable(mod);
        JScrollPane sp = new JScrollPane(tTransactions);
        this.add(sp);
    }

    private Object[][] setDataTable() {
        Object[][] dataTable = new Object[shop.getStore().size()][];
        int i = 0;
        for (Goods g : shop.getStore()) {
            if (g != null) {
                Object[] co = {g.getName(),g.getCategory().getName(),
                        g.getSubcategory().getName(),g.getNumber(),g.getPrice(),g.getSold()};
                dataTable[i] = co;
            }
            i++;
        }
        return dataTable;
    }

    public void updateClientTablePanel() {
        Object[][] dataTable = setDataTable();
        mod.setDataVector(dataTable, columnNames);
    }
}
