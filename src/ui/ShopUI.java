package ui;

import reports.SaleRecord;
import shop.Shop;
import utl.DataUtl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShopUI {

	private Shop shop;
	private JFrame f;
	private JPanel pTable;
	private SellingPanel sellingPanel;
	private GoodsAddPanel goodsAddPanel;
	private JPanel clientAddPanel;
	private ClientTable clientTablePanel;
	private GoodsTable goodsTablePanel;
	private DefaultTableModel mod;
	private CategoryEditPanel categoryPanel;
	private SubcategoryEditPanel subcategoryEditPanel;

	public ShopUI(Shop shop) {
		this.shop = shop;

		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(300, 100);
		f.setMinimumSize(new Dimension(800, 600));

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
				EditSubcategory();
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
		
		createTablePanel();
		createSellingPanel();
		createGoodsAddPanel();
		createClientAddPanel();
		createClientTablePanel();
		createGoodsTablePanel();
		createEditCategoryPanel();
		createEditSubcategoryPanel();
		f.getContentPane().add(pTable);
		
		f.pack();
		f.setVisible(true);
	}

	private void showEditCategory() {
		f.getContentPane().removeAll();
		categoryPanel.updateCategoryEditPanel();
		f.getContentPane().add(categoryPanel);
		f.pack();
		f.repaint();
	}


	private void createEditCategoryPanel() {
		categoryPanel = new CategoryEditPanel(shop,this);
	}

	private void EditSubcategory() {
		f.getContentPane().removeAll();
		subcategoryEditPanel.updateSubcategoryEditPanel();
		f.getContentPane().add(subcategoryEditPanel);
		f.pack();
		f.repaint();

	}

	private void createEditSubcategoryPanel() {
		subcategoryEditPanel = new SubcategoryEditPanel(shop,this);
	}

	public void showData() {
		f.getContentPane().removeAll();
		updateTable();
		pTable.repaint();
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

	private void createGoodsAddPanel() {
		goodsAddPanel = new GoodsAddPanel(shop,this);
	}


	private void createClientAddPanel() {
		clientAddPanel = new ClientAddPanel(shop,this);
	}

	private void createClientTablePanel() {
		clientTablePanel = new ClientTable(shop,this);
	}

	private void createGoodsTablePanel() {
		goodsTablePanel = new GoodsTable(shop,this);
	}

	private void createSellingPanel() {
		sellingPanel = new SellingPanel(shop,this);
	}

	private void showAddGoodsForm() {
		f.getContentPane().removeAll();
		goodsAddPanel.updateGoodsAddPanel();
		f.getContentPane().add(goodsAddPanel);
		f.pack();
		f.repaint();
	}

	private void showAddClientForm() {
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

	private void updateTable() {
		
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
	
	private void createTablePanel() {
		pTable = new JPanel();
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
		pTable.add(sp);

	}

	public JFrame getF() {
		return f;
	}
}
