package ui;


import goods.Goods;
import shop.Client;
import shop.Shop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SellingPanel  extends JPanel {
    private Shop shop;
    private DefaultComboBoxModel<Item> modelComboBoxCl;
    private JComboBox comboCl;
    private JComboBox comboCg;
    private JComboBox comboG;
    private Client clientSelectRefer;
    private String categorySelec;
    private String subcategorySelec;
    private DefaultComboBoxModel<Item> modelCBG;
    private DefaultComboBoxModel<String> modelCBSubCateg;
    private Goods goodsSelectRefer;
    private int numberGoods;
    private  DefaultComboBoxModel<String> modelComboBoxCateg;

    public SellingPanel(final Shop shop, final ShopUI shopUI) {
        this.shop = shop;
        setLayout(new GridBagLayout());

        JLabel lName = new JLabel("Client's name");
        add(lName, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));


        modelComboBoxCl = new DefaultComboBoxModel(shop.getClientsItem());
        comboCl = new JComboBox(modelComboBoxCl);
        comboCl.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboCl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                Item item = (Item) cb.getSelectedItem();
                if (item != null) {
                    clientSelectRefer = (Client) item.getObj();
                }
            }
        });
        if (modelComboBoxCl.getSize() > 0) {
            comboCl.setSelectedIndex(0);
        }
        add(comboCl, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCategory = new JLabel("Categories:");
        modelComboBoxCateg = new DefaultComboBoxModel(shop.getCategory());
        comboCg = new JComboBox(modelComboBoxCateg);
        comboCg.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboCg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                categorySelec = (String) cb.getSelectedItem();
                if (modelCBSubCateg != null && modelCBG != null) {
                    modelCBSubCateg.removeAllElements();
                    for (String str : shop.getSubCategory(categorySelec)) {
                        modelCBSubCateg.addElement(str);
                    }
                    modelCBG.removeAllElements();
                    for (Item item : shop.getGoodsItem(categorySelec
                            , modelCBSubCateg.getElementAt(0))) {
                        modelCBG.addElement(item);
                    }

                }
            }
        });
        if (modelComboBoxCateg.getSize() > 0) {
            categorySelec = modelComboBoxCateg.getElementAt(0);
            comboCg.setSelectedIndex(0);
        }
        add(lCategory, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboCg, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lSubCateg = new JLabel("Subcategories:");
        String subcategorySelec = "";
        modelCBSubCateg = new DefaultComboBoxModel(shop.getSubCategory(categorySelec));
        JComboBox comboSC = new JComboBox(modelCBSubCateg);
        comboSC.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboSC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                if (modelCBG != null) {
                    modelCBG.removeAllElements();
                    for (Item item : shop.getGoodsItem(categorySelec
                            , (String) cb.getSelectedItem())) {
                        modelCBG.addElement(item);
                    }
                }
            }
        });
        if (modelCBSubCateg.getSize() > 0) {
            comboSC.setSelectedIndex(0);
        }
        add(lSubCateg, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboSC, new GridBagConstraints(2, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lProducts = new JLabel("Goods:");
        modelCBG = new DefaultComboBoxModel(shop.getGoodsItem(categorySelec));
        comboG = new JComboBox(modelCBG);
        comboG.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                Item item = (Item) cb.getSelectedItem();
                if (item != null) {
                    goodsSelectRefer = (Goods) item.getObj();
                }
            }
        });
        if (modelCBG.getSize() > 0) {
            comboG.setSelectedIndex(0);
        }
        add(lProducts, new GridBagConstraints(3, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboG, new GridBagConstraints(3, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCount = new JLabel("Count of birds");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setMaximumSize(new Dimension(100, 20));
        add(lCount, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(spinner, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                numberGoods = (int) js.getValue();
            }
        };

        spinner.addChangeListener(listener);

        JButton b1 = new JButton("Buy");
        add(b1, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(numberGoods <= 0) {
                    JOptionPane.showMessageDialog(shopUI.getF(), "You mus set non-zero number of goods");
                } else {
                    shop.buyGoods(clientSelectRefer, goodsSelectRefer, numberGoods);
                    shopUI.showData();
                }
            }

        });

    }

    public void updateSellingForm() {
        updateComboBoxClient();
        updateComboBoxCateg();
        updateComboBoxSubcateg();
        updateComboBoxGoods();
    }

    private void updateComboBoxClient() {
        if (modelComboBoxCl != null) {
            modelComboBoxCl.removeAllElements();
            for (Item item : shop.getClientsItem()) {
                modelComboBoxCl.addElement(item);
            }
        }
        if (modelComboBoxCl.getSize() > 0) {
            comboCg.setSelectedIndex(0);
        }
    }

    private void updateComboBoxCateg() {
        if(modelComboBoxCateg != null) {
            modelComboBoxCateg.removeAllElements();
            for (String str : shop.getCategory()) {
                modelComboBoxCateg.addElement(str);
            }
            if (modelComboBoxCateg.getSize() > 0) {
                categorySelec = modelComboBoxCateg.getElementAt(0);
            }
        }
    }

    private void updateComboBoxSubcateg() {
        if(modelCBSubCateg != null) {
            modelCBSubCateg.removeAllElements();
            for (String str : shop.getSubCategory(categorySelec)) {
                modelCBSubCateg.addElement(str);
            }
            if (modelCBSubCateg.getSize() > 0) {
                subcategorySelec = modelCBSubCateg.getElementAt(0);
            }
        }
    }

    private void updateComboBoxGoods() {
        if (modelCBG != null) {
            modelCBG.removeAllElements();
            for (Item item : shop.getGoodsItem(categorySelec
                    , subcategorySelec)) {
                modelCBG.addElement(item);
            }
            if (modelCBG.getSize() > 0) {
                comboG.setSelectedIndex(0);
            }
        }
    }
}