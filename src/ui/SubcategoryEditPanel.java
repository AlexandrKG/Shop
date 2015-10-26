package ui;


import goods.Category;
import shop.Shop;
import utl.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class SubcategoryEditPanel  extends JPanel {
    private Shop shop;
    private String categorySelec;
    private DefaultTableModel mod;
    private  String[] columnNames = {"Category","ID","Subcategory"};
    private  DefaultComboBoxModel<String> modelComboBoxCateg;

    public SubcategoryEditPanel(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        setLayout(new GridBagLayout());

//        JLabel lCategoryName = new JLabel("Category");
//        add(lCategoryName, new GridBagConstraints(0, 0, 1, 1, 0, 0,
//                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));

//        Vector<String> modelComboBoxCateg = this.shop.getCategory();
        modelComboBoxCateg = new DefaultComboBoxModel(shop.getCategoriesVector());
        JComboBox comboCg = new JComboBox(modelComboBoxCateg);
        comboCg.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboCg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                categorySelec = (String) cb.getSelectedItem();
                updateSubcategoryTable();
            }
        });
        if(modelComboBoxCateg.getSize() > 0) {
            categorySelec = modelComboBoxCateg.getElementAt(0);
            comboCg.setSelectedIndex(0);
        }
        add(comboCg, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        Object[][] dataTable = setDataTable();
        mod = new DefaultTableModel(dataTable, columnNames);
        JTable tTransactions = new JTable(mod);
        JScrollPane sp = new JScrollPane(tTransactions);
        setLayout(new GridBagLayout());
        add(sp, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JTextField tCategory = new JTextField();
        tCategory.setColumns(25);
        add(tCategory, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        JButton bANG = new JButton("Add New Subcategory");
        add(bANG, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        bANG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tCategory.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(shopUI.getF(), "You mus set new Subcategory");
                } else {
                    shop.addSubcategory(categorySelec, tCategory.getText());
                    updateSubcategoryTable();
                    tCategory.setText("");
                }
            }
        });
    }

    private Object[][] setDataTable() {

        Object[][] dataTable = null;
        Category c = shop.findCategory(categorySelec);
        if(c != null) {
            dataTable = new Object[c.getSubcategories().size()][];
            int i = 0;
            for (Entry e : c.getSubcategories()) {
                if (e != null) {
                    Object[] co = {categorySelec,String.valueOf(e.getId()), e.getName()};
                    dataTable[i] = co;
                }
                i++;
            }
        }
        return dataTable;
    }

    public void updateSubcategoryEditPanel() {
        updateComboBoxCateg();
        updateSubcategoryTable();
    }

    private void updateSubcategoryTable() {
        if (mod != null) {
            Object[][] dataTable = setDataTable();
            mod.setDataVector(dataTable, columnNames);
        }
    }

    private void updateComboBoxCateg() {
        if(modelComboBoxCateg != null) {
            modelComboBoxCateg.removeAllElements();
            for (String str : shop.getCategoriesVector()) {
                modelComboBoxCateg.addElement(str);
            }
            if (modelComboBoxCateg.getSize() > 0) {
                categorySelec = modelComboBoxCateg.getElementAt(0);
            }
        }
    }
}
