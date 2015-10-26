package goods;

public class Goods {
	
	
	protected String category;
	protected String subcategory;
	protected String name;
	protected int number;
	protected double price;
	protected int sold;
	private long idGoods;
	private long idCategory;
	private long idSubcategory;

	public Goods() {
		
		this(0,0.0,0);	
	}
	
	public Goods(int number,double price, int sold) {
		this.number = number;
		this.price = price;
		this.sold = sold;
	}
	
	public void printGoods() {
		System.out.print(this.name +"; ");
		System.out.print("Category is " + this.category +"; ");
		System.out.print("Subcategory is " + this.subcategory +"; ");
		System.out.print("Quantity in stock is " + this.number +"; ");
		System.out.print("Price is " + this.price +"; ");
		System.out.println("Sold: " + this.sold +".");
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
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public long getIdGoods() {
		return idGoods;
	}
}
