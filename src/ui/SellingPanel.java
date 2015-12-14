package ui;


import domain.Category;
import domain.Goods;
import domain.Client;
import domain.Subcategory;
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
    private Category categorySelecRefer;
    private Subcategory subcategorySelecRefer;
    private DefaultComboBoxModel<Item> modelCBG;
    private DefaultComboBoxModel<Item> modelCBSubCateg;
    private Goods goodsSelectRefer;
    private int numberGoods;
    private  DefaultComboBoxModel<Item> modelComboBoxCateg;
    private  SpinnerNumberModel spinnerNumberModel;

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
        modelComboBoxCateg = new DefaultComboBoxModel(shop.getCategoriesItem());
        comboCg = new JComboBox(modelComboBoxCateg);
        comboCg.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboCg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                Item item = (Item) cb.getSelectedItem();
                if (item != null) {
                    categorySelecRefer = (Category) item.getObj();
                } else {
                    categorySelecRefer = null;
                }
                updateModelSubcateg();
                updateModelGoods();
            }
        });
        if (modelComboBoxCateg.getSize() > 0) {
            categorySelecRefer =(Category)modelComboBoxCateg.getElementAt(0).getObj();
            comboCg.setSelectedIndex(0);
        }
        add(lCategory, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboCg, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lSubCateg = new JLabel("Subcategories:");
        modelCBSubCateg = new DefaultComboBoxModel(shop.getSubcategoriesItem(categorySelecRefer));
        JComboBox comboSC = new JComboBox(modelCBSubCateg);
        comboSC.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboSC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modelCBSubCateg != null) {
                    JComboBox cb = (JComboBox) e.getSource();
                    Item item = (Item) cb.getSelectedItem();
                    if (item != null) {
                        subcategorySelecRefer = (Subcategory) item.getObj();
                    } else {
                        subcategorySelecRefer = null;
                    }
                    updateModelGoods();
                }
            }
        });
        if (modelCBSubCateg.getSize() > 0) {
            subcategorySelecRefer =(Subcategory)modelCBSubCateg.getElementAt(0).getObj();
            comboSC.setSelectedIndex(0);
        }
        add(lSubCateg, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboSC, new GridBagConstraints(2, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lProducts = new JLabel("Goods:");
        modelCBG = new DefaultComboBoxModel(shop.getGoodsItem(categorySelecRefer,subcategorySelecRefer));
        comboG = new JComboBox(modelCBG);
        comboG.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        comboG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modelCBG != null && modelCBG.getSize() > 0) {
                    JComboBox cb = (JComboBox) e.getSource();
                    Item item = (Item) cb.getSelectedItem();
                    if (item != null) {
                        goodsSelectRefer = (Goods) item.getObj();
                    } else {
                        goodsSelectRefer = null;
                    }
                }
            }
        });
        if (modelCBG.getSize() > 0) {
            goodsSelectRefer = (Goods)modelCBG.getElementAt(0).getObj();
            comboG.setSelectedIndex(0);
        }
        add(lProducts, new GridBagConstraints(3, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));
        add(comboG, new GridBagConstraints(3, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0,
                new Insets(0, 0, 0, 0), 0, 0));

        JLabel lCount = new JLabel("Count of birds");
        spinnerNumberModel = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinner = new JSpinner(spinnerNumberModel);
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
                if(categorySelecRefer == null) {
                    JOptionPane.showMessageDialog((JFrame) shopUI.getFrame(),
                            "You mus select category");
                } else if(subcategorySelecRefer == null) {
                    JOptionPane.showMessageDialog((JFrame) shopUI.getFrame(),
                            "You mus select subcategory");
                } else if(goodsSelectRefer == null) {
                    JOptionPane.showMessageDialog((JFrame) shopUI.getFrame(),
                            "You mus select domain");
                } else if(numberGoods <= 0) {
                    JOptionPane.showMessageDialog((JFrame)shopUI.getFrame(),
                            "You mus set non-zero number of domain");
                } else {
                    shop.buyGoods(clientSelectRefer, goodsSelectRefer, numberGoods);
                    shopUI.showData();
                }
            }

        });

    }

    public void updateSellingForm() {
        System.out.println("updateSellingForm");
        updateComboBoxClient();
        updateComboBoxCateg();
        updateComboBoxSubcateg();
        updateComboBoxGoods();
        spinnerNumberModel.setValue(0);
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
            for (Item item : shop.getCategoriesItem()) {
                modelComboBoxCateg.addElement(item);
            }
            if (modelComboBoxCateg.getSize() > 0) {
                categorySelecRefer =(Category)modelComboBoxCateg.getElementAt(0).getObj();
            }
        }
    }

    private void updateComboBoxSubcateg() {
         updateModelSubcateg();
        if (modelCBSubCateg != null && modelCBSubCateg.getSize() > 0) {
            subcategorySelecRefer = (Subcategory) modelCBSubCateg.getElementAt(0).getObj();
        }
    }

    private void updateComboBoxGoods() {
        updateModelGoods();
        if (modelCBG != null && modelCBG.getSize() > 0) {
            comboG.setSelectedIndex(0);
        }
    }

    private void updateModelSubcateg() {
        subcategorySelecRefer = null;
        if (modelCBSubCateg != null) {
            modelCBSubCateg.removeAllElements();
            Vector<Item> vector = shop.getSubcategoriesItem(categorySelecRefer);
            if (vector != null) {
                for (Item it : vector) {
                    modelCBSubCateg.addElement(it);
                }
            }
        }
    }

    private void updateModelGoods() {
        goodsSelectRefer = null;
        if (modelCBG != null) {
            modelCBG.removeAllElements();
            Vector<Item> vector = shop.getGoodsItem(categorySelecRefer
                    , subcategorySelecRefer);
            if(vector != null) {
                for (Item it : vector) {
                    modelCBG.addElement(it);
                }
            }
        }
    }

}