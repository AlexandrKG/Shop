package shop;

import goods.*;
import reports.AccountBook;
import reports.SaleRecord;
import ui.Item;
import utl.Entry;
import utl.ShopGenerator;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ShopLocal implements Shop{
	private ArrayList<Goods> store;
	private AccountBook accountBook;
	private  ArrayList<Client> clients;
	private  ArrayList<Category> categories;

	public ShopLocal() {
		store = new ArrayList<>();
		accountBook = new AccountBook();
		clients = new ArrayList<>();
		categories = new ArrayList<>();
		initShop(true);
	}

	public  void initShop(boolean resetData) {
		if(resetData) {
			ShopGenerator shopGenerator = new ShopGenerator(this);
			shopGenerator.initShop();
		}

	}

	public AccountBook getAccountBook() {

		return accountBook;
	}

	public ArrayList<Goods> getStore() {
		return store;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void addCategory(String data) {
		if(categories != null) {
			if(existCategory(data)) {
				return;
			}
		}
		Category category = new Category(data);
		category.setId(System.currentTimeMillis());
		categories.add(category);
	}

	private boolean existCategory(String data) {
		if(findCategory(data) != null) {
			return true;
		}
		return false;
	}

	public Category findCategory(String data) {
		Category result = null;
		if(categories != null) {
			for (Category c : categories) {
				if (c.getName().equals(data)) {
					result = c;
					break;
				}
			}
		}
		return result;
	}


	public void addSubcategory(String categoryName, String subcategory) {
		Category category = findCategory(categoryName);
		if(category != null) {
			category.addSubcategory(subcategory);
		} else {
			throw new IllegalStateException(categoryName + " category doesn't exist");
		}
	}

	public void addGoods(Goods goods) {
		store.add(goods);
	}

	public void buyGoods(Client client, Goods goods, int number) {
		Calendar today = new GregorianCalendar();
		today.setTime(new Date());
		try {
			if (soldGoods(goods, number)) {
				Constructor constr = goods.getClass().getConstructor();
				Goods g = (Goods) constr.newInstance();
				g.setName(goods.getName());
				g.setSubcategory(goods.getSubcategory());
				g.setNumber(number);
				g.setPrice(goods.getPrice());
				client.buyGoods(g);
				addSaleRecord(client, g, number, today);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}



	public  void buyGoods(Client client, Goods goods, int number, Calendar data) {

		if (clients.contains(client)) {
			if(soldGoods(goods, number)) {
				goods.setNumber(number);
				client.buyGoods(goods);
				addSaleRecord(client, goods, number, data);
			}
		} else {
			System.out.println("We haven't client " + client);
		}
	}

	public  void addSaleRecord(Client client, Goods goods, int number, Calendar data) {
		SaleRecord sr = new SaleRecord();
		sr.setClient(client.getName());
		sr.setGoods(goods.getName());
		sr.setNumber(number);
		sr.setCost(goods.getPrice());
		sr.setData(data);
		sr.setIdClient(client.getIdClient());
		sr.setIdGoods(goods.getIdGoods());
		accountBook.setRecord(sr);
	}

	public Vector<Item> getClientsItem() {
		Vector<Item> result = new Vector<>();
		for(Client cl : clients) {
			result.add(new Item(cl, cl.getName()));
		}
		return result;
	}

	public Vector<Item> getGoodsItem(String category) {
		Vector<Item> result = new Vector<>();
		for(Goods g  : store) {
			if(g.getCategory().equals(category)) {
				result.add(new Item(g, g.getName()));
			}
		}
		return result;
	}

	public Vector<Item> getGoodsItem(String category,String subcategory) {
		Vector<Item> result = new Vector<>();
		for(Goods g  : store) {
			if(g.getCategory().equals(category) && g.getSubcategory().equals(subcategory)) {
				result.add(new Item(g, g.getName()));
			}
		}
		return result;
	}

	public Vector<String> getCategoriesVector() {
		Vector<String> result = new Vector<>();
		for (Category c : getCategories()) {
			result.add(c.getName());
		}
		return result;
	}

	public Vector<String> getSubcategoriesVector(String category) {
		Vector<String> result = new Vector<>();
		Category c = findCategory(category);
		if(c != null) {
			for (Entry e : c.getSubcategories()) {
				if (e != null) {
					result.add(e.getName());
				}
			}
		}
		return result;
	}

	public Vector<String> getCategoriesFromGoods() {
		Vector<String> result = new Vector<>();
		TreeSet<String> temp = new TreeSet<>();
		for(Goods g : store) {
			temp.add(g.getCategory());
		}
		for(String str : temp) {
			result.add(str);
		}
		return result;
	}

	public Vector<String> getSubCategoriesFromGoods(String category) {
		Vector<String> result = new Vector<>();
		TreeSet<String> temp = new TreeSet<>();
		for(Goods g : store) {
			if(g.getCategory().equals(category)) {
				temp.add(g.getSubcategory());
			}
		}
		for(String str : temp) {
			result.add(str);
		}
		return result;
	}

	public boolean soldGoods(Goods gd, int count) {
		Goods goods = null;
		String name = gd.getName();
		for(Goods g : store) {
			if(name.equals(g.getName()) ) {
				goods = g;
			}
		}
		int temp = goods.getNumber() - count;
		if (temp >= 0) {
			goods.setNumber(temp);
			goods.setSold(count + goods.getSold());
			System.out.println("We have sold " + String.valueOf(count)
					+ " " + name);
			return true;
		} else {
			System.out.println("We can't sail so many " + name);
			return false;
		}
	}

	public void addNewClient(Client client) {
		clients.add(client);
	}


}
