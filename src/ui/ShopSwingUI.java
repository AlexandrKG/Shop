package ui;

import shop.Shop;
import shop.ShopLocal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ShopSwingUI implements ShopUI{

	private Shop shop;
	private JFrame f;
	private AccountBookPanel pTable;
	private SellingPanel sellingPanel;
	private GoodsAddPanel goodsAddPanel;
	private ClientAddPanel clientAddPanel;
	private ClientTable clientTablePanel;
	private GoodsTable goodsTablePanel;
	private CategoryEditPanel categoryPanel;
	private SubcategoryEditPanel subcategoryEditPanel;

	public ShopSwingUI(Shop shop) {
		this.shop = shop;
		setFrame();
	}

	public void setFrame()  {
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(300, 100);
		f.setMinimumSize(new Dimension(800, 600));
		createMenu();
		createAllPanels();
		f.getContentPane().add(pTable);
		f.pack();
		f.setVisible(true);
	}

	public void createMenu() {
		JMenuItem mBG = new JMenuItem("Buy Goods");
		mBG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSellingForm();
			}
		});

		JMenuItem mAG = new JMenuItem("Add Goods");
		mAG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddGoodsForm();

			}
		});

		JMenuItem mSG = new JMenuItem("Show Goods");
		mSG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showGoodsForm();

			}
		});

		JMenu mG = new JMenu("Goods");
		mG.add(mBG);
		mG.add(mAG);
		mG.add(mSG);

		JMenuItem mAC = new JMenuItem("Add Clients");
		mAC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddClientForm();
			}
		});

		JMenuItem mSC = new JMenuItem("Show Clients");
		mSC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showClientsForm();

			}
		});

		JMenu mC = new JMenu("Clients");
		mC.add(mSC);
		mC.add(mAC);

		JMenuItem mAB = new JMenuItem("Show All");
		mAB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showData();

			}
		});

		JMenu mB = new JMenu("Accoun Book");
		mB.add(mAB);

		JMenuItem mEC = new JMenuItem("Category edit");
		mEC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditCategory();
			}
		});

		JMenuItem mESC = new JMenuItem("Subcategory edit");
		mESC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditSubcategory();
			}
		});

		JMenu mCT = new JMenu("Edit");
		mCT.add(mEC);
		mCT.add(mESC);

		JMenuBar mb = new JMenuBar();
		mb.add(mG);
		mb.add(mC);
		mb.add(mB);
		mb.add(mCT);
		f.getRootPane().setJMenuBar(mb);
	}

	public void createAllPanels() {
		createTablePanel();
		createSellingPanel();
		createGoodsAddPanel();
		createClientAddPanel();
		createClientTablePanel();
		createGoodsTablePanel();
		createEditCategoryPanel();
		createEditSubcategoryPanel();
	}

	public JFrame getFrame() {
		return f;
	}

	public void createEditCategoryPanel() {
		categoryPanel = new CategoryEditPanel(shop,this);
	}

	public void createEditSubcategoryPanel() {
		subcategoryEditPanel = new SubcategoryEditPanel(shop,this);
	}

	public void createGoodsAddPanel() {
		goodsAddPanel = new GoodsAddPanel(shop,this);
	}

	public void createClientAddPanel() {
		clientAddPanel = new ClientAddPanel(shop,this);
	}

	public void createClientTablePanel() {
		clientTablePanel = new ClientTable(shop,this);
	}

	public void createGoodsTablePanel() {
		goodsTablePanel = new GoodsTable(shop,this);
	}

	public void createSellingPanel() {
		sellingPanel = new SellingPanel(shop,this);
	}

	public void createTablePanel() {
		pTable = new AccountBookPanel(shop,this);
	}

	public void showData() {
		f.getContentPane().removeAll();
		pTable.updateTable();
		f.getContentPane().add(pTable);
		f.pack();
		f.repaint();
	}

	public void showSellingForm() {
		f.getContentPane().removeAll();
		sellingPanel.updateSellingForm();
		f.getContentPane().add(sellingPanel);
		f.pack();
		f.repaint();
	}

	public void showAddGoodsForm() {
		f.getContentPane().removeAll();
		goodsAddPanel.updateGoodsAddPanel();
		f.getContentPane().add(goodsAddPanel);
		f.pack();
		f.repaint();
	}

	public void showAddClientForm() {
		f.getContentPane().removeAll();
		f.getContentPane().add(clientAddPanel);
		f.pack();
		f.repaint();
	}

	public void showClientsForm() {
		f.getContentPane().removeAll();
		clientTablePanel.updateClientTablePanel();
		f.getContentPane().add(clientTablePanel);
		f.pack();
		f.repaint();
	}

	public void showGoodsForm() {
		f.getContentPane().removeAll();
		goodsTablePanel.updateClientTablePanel();
		f.getContentPane().add(goodsTablePanel);
		f.pack();
		f.repaint();
	}
	public void showEditCategory() {
		f.getContentPane().removeAll();
		categoryPanel.updateCategoryEditPanel();
		f.getContentPane().add(categoryPanel);
		f.pack();
		f.repaint();
	}

	public void showEditSubcategory() {
		f.getContentPane().removeAll();
		subcategoryEditPanel.updateSubcategoryEditPanel();
		f.getContentPane().add(subcategoryEditPanel);
		f.pack();
		f.repaint();

	}


}
