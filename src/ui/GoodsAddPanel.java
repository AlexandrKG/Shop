package ui;


import goods.Goods;
import shop.Shop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoodsAddPanel extends JPanel {

    private Shop shop;
    private String categorySelec;
    private String subCategorySelec;
    private String goodsNamne;
    private  int numberGoods;
    private  double costGoods;
    private  DefaultComboBoxModel<String> modelComboBoxSubCateg;
    private  DefaultComboBoxModel<String> modelComboBoxCateg;

    public GoodsAddPanel (final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        setLayout(new GridBagLayout());

        JLabel lName = new JLabel("New Goods");
        add(lName, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JTextField lGoods = new JTextField();
        lGoods.setColumns(25);
        add(lGoods, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCategoryName = new JLabel("Set Category");
        add(lCategoryName, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

//        Vector<String> modelComboBoxCateg = this.shop.getCategory();
        modelComboBoxCateg = new DefaultComboBoxModel(this.shop.getCategoriesVector());
        JComboBox comboCg = new JComboBox(modelComboBoxCateg);
        comboCg.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboCg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                categorySelec = (String) cb.getSelectedItem();
                updateComboBoxSubCateg();
//                if (modelComboBoxSubCateg != null) {
//                    modelComboBoxSubCateg.removeAllElements();
//                    for (String str : shop.getSubCategory(categorySelec)) {
//                        modelComboBoxSubCateg.addElement(str);
//                    }
//                }
            }
        });
        if(modelComboBoxCateg.getSize() > 0) {
            categorySelec = modelComboBoxCateg.getElementAt(0);
            comboCg.setSelectedIndex(0);
        }
        add(comboCg, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lSubCtg = new JLabel("Set Subcategory");
        add(lSubCtg, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

   //     modelComboBoxSubCateg = new DefaultComboBoxModel(this.shop.getSubCategory(categorySelec));
        modelComboBoxSubCateg = new DefaultComboBoxModel(this.shop.getSubcategoriesVector(categorySelec));
        JComboBox comboSCg = new JComboBox(modelComboBoxSubCateg);
        comboSCg.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboSCg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                subCategorySelec = (String) cb.getSelectedItem();
            }
        });
        if(modelComboBoxSubCateg.getSize() > 0) {
            comboSCg.setSelectedIndex(0);
            subCategorySelec = modelComboBoxSubCateg.getElementAt(0);
        }

        add(comboSCg, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCount = new JLabel("Set quantity");
        add(lCount, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setMaximumSize(new Dimension(100, 20));
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                numberGoods = (int) js.getValue();

            }
        };
        spinner.addChangeListener(listener);
        add(spinner, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCost = new JLabel("Set cost");
        add(lCost, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        final JSpinner spCost = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 0.1));
        spCost.setMaximumSize(new Dimension(100, 20));
        ChangeListener listenerCost = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                costGoods = (double) js.getValue();

            }
        };
        spCost.addChangeListener(listenerCost);
        add(spCost, new GridBagConstraints(1, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        JButton bANG = new JButton("Add New Goods");
        add(bANG, new GridBagConstraints(1, 5, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        bANG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goodsName = lGoods.getText();
                if(goodsName.isEmpty()) {
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(), "You mus set goods");
                } else if(categorySelec == null){
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(), "You mus select category");
                } else if(subCategorySelec == null){
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(), "You mus select subcategory");
                } else {
                    Goods g = new Goods();
                    g.setName(lGoods.getText());
                    g.setCategory(categorySelec);
                    g.setSubcategory(subCategorySelec);
                    g.setNumber(numberGoods);
                    g.setPrice(costGoods);
                    shop.addGoods(g);
                    shopUI.showGoodsForm();
                }
                spCost.setValue(0.0);
                spinner.setValue(0);
            }
        });


    }

    public void updateGoodsAddPanel() {
        updateComboBoxCateg();
        updateComboBoxSubCateg();
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

    private void updateComboBoxSubCateg() {
        if(modelComboBoxSubCateg != null) {
            modelComboBoxSubCateg.removeAllElements();
            for (String str : shop.getSubcategoriesVector(categorySelec)) {
                modelComboBoxSubCateg.addElement(str);
            }
            if (modelComboBoxSubCateg.getSize() > 0) {
                subCategorySelec = modelComboBoxSubCateg.getElementAt(0);
            }
        }
    }
}
