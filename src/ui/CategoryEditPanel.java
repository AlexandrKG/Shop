package ui;


import goods.Category;
import shop.Shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryEditPanel  extends JPanel {
    private Shop shop;
    private DefaultTableModel mod;
    private  String[] columnNames = {"ID","Name"};

    public CategoryEditPanel(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        Object[][] dataTable = setDataTable();
        mod = new DefaultTableModel(dataTable, columnNames);
        JTable tTransactions = new JTable(mod);
        JScrollPane sp = new JScrollPane(tTransactions);
        setLayout(new GridBagLayout());
        add(sp, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JTextField tCategory = new JTextField();
        tCategory.setColumns(25);
        add(tCategory, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        JButton bANG = new JButton("Add New Category");
        add(bANG, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        bANG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tCategory.getText().isEmpty()) {
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(), "You mus set new Category");
                } else {
                    shop.addCategory(tCategory.getText());
                    updateCategoryEditPanel();
                    tCategory.setText("");
                }
            }
        });
    }

    private Object[][] setDataTable() {
        Object[][] dataTable = new Object[shop.getCategories().size()][];
        int i = 0;
        for (Category c : shop.getCategories()) {
            if (c != null) {
                Object[] co = {String.valueOf(c.getId()),c.getName()};
                dataTable[i] = co;
            }
            i++;
        }
        return dataTable;
    }

    public void updateCategoryEditPanel() {
        Object[][] dataTable = setDataTable();
        mod.setDataVector(dataTable, columnNames);
    }

}
