package shop;

import goods.Goods;
import utl.Bag;

public class Client {

	private String name;
	private String gender;
	private int age;
	private String telephone;
	private String address;
	private long idClient;

	private Bag<Goods> purchases;

	public Client() {

	}

	public Client(String name) {
		this.name = name;
		purchases = new Bag();
		idClient = System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bag getPurchases() {
		return purchases;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getIdClient() {
		return idClient;
	}

	public void buyGoods(Goods gd) {
		purchases.add(gd);
		System.out.println(this.name + " has " + gd.getNumber() + " " + gd.getName());
	}
	
	public void showGoods() {
		if(purchases == null) {
			return;
		}
		System.out.println(this.name + "'s goods are:");
		for (Goods gd : purchases.getList()) {
			if (gd != null) {
				System.out.print(gd.getName() +"; ");
				System.out.print("Quantity is " + gd.getNumber() +"; ");
				System.out.print("Cost is " + gd.getPrice() +"; ");
				System.out.println("Summ: " + gd.getPrice()*gd.getNumber() +".");
			}
		}
	}

}
