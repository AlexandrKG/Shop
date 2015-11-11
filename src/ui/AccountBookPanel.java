package ui;


import reports.SaleRecord;
import shop.Shop;
import utl.DataUtl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountBookPanel   extends JPanel {
    private Shop shop;
    private DefaultTableModel mod;

    public AccountBookPanel(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        String[] columnNames = {"Data","Customer","Product","Numbers","Cost"};
        Object[][] dataTable = new Object[shop.getAccountBook().getRegisterSale().size()][];
        int i = 0;
        for (SaleRecord record : shop.getAccountBook().getRegisterSale()) {
            if (record != null) {
                Object[] co = {DataUtl.txtData(record.getData()),record.getClient(),
                        record.getGoods(),record.getNumber(),record.getCost()};
                dataTable[i] = co;
            }
            i++;
        }
        mod = new DefaultTableModel(dataTable, columnNames);
        JTable tTransactions = new JTable(mod);
        JScrollPane sp = new JScrollPane(tTransactions);
        add(sp);

    }

    public void updateTable() {

        String[] columnNames = {"Data","Customer","Product","Numbers","Cost"};
        Object[][] dataTable = new Object[shop.getAccountBook().getRegisterSale().size()][];
        int i = 0;
        for (SaleRecord record : shop.getAccountBook().getRegisterSale()) {
            if (record != null) {
                Object[] co = {DataUtl.txtData(record.getData()),record.getClient(),
                        record.getGoods(),record.getNumber(),record.getCost()};
                dataTable[i] = co;
            }
            i++;
        }
        mod.setDataVector(dataTable, columnNames);
    }

    private void addRowTable() {
        int last = shop.getAccountBook().getRegisterSale().size()-1;
        SaleRecord record = shop.getAccountBook().getRegisterSale().get(last);

        Object[] co = {DataUtl.txtData(record.getData()),record.getClient(),
                record.getGoods(),record.getNumber(),record.getCost()};
        mod.addRow(co);
    }
}
