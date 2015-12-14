package ui;


import domain.Client;
import shop.Shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClientTable extends JPanel {
    private Shop shop;
    private DefaultTableModel mod;
    private  String[] columnNames = {"Name","Gender","Age","Address","Telephone"};

    public  ClientTable(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        Object[][] dataTable = setDataTable();
        mod = new DefaultTableModel(dataTable, columnNames);
        JTable tTransactions = new JTable(mod);
        JScrollPane sp = new JScrollPane(tTransactions);
        this.add(sp);
    }

    private Object[][] setDataTable() {
        Object[][] dataTable = new Object[shop.getClients().size()][];
        int i = 0;
        for (Client cl : shop.getClients()) {
            if (cl != null) {
                Object[] co = {cl.getName(),cl.getGender(),cl.getAge(),cl.getAddress(),cl.getTelephone()};
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
