package domain;

public class Goods {

	private String name;
	private long idGoods;
	private Category category;
	private Subcategory subcategory;
	private int number;
	private double price;
	private int sold;

	public Goods() {

	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public long getIdGoods() {
		return idGoods;
	}

	public void setIdGoods(long idGoods) {
		this.idGoods = idGoods;
	}

}
