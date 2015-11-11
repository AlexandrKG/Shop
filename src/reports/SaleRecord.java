package reports;

import utl.DataUtl;

import java.util.Calendar;

public class SaleRecord {

	private Calendar data;
	private String goods;
	private long idGoods;
	private String client;
	private long idClient;
	private int number;
	private double cost;
	private long idRecord;

	public SaleRecord() {
		idRecord = System.currentTimeMillis();
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public long getId() {
		return idRecord;
	}

	public long getIdGoods() {
		return idGoods;
	}

	public long getIdClient() {
		return idClient;
	}

	public void setIdGoods(long idGoods) {
		this.idGoods = idGoods;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public void setIdRecord(long idRecord) {
		this.idRecord = idRecord;
	}

	public void printRecord() {
		System.out.print(DataUtl.txtData(this.data) + "; ");
		System.out.print(this.client + "; ");
		System.out.print(this.goods + "; ");
		System.out.print(this.number + "; ");
		System.out.print(this.cost + "; ");
		System.out.println("Summ: " + this.cost * this.number + ".");
	}

	public boolean printDataRecord(Calendar startData, Calendar stopData) {
		if (this.data.compareTo(startData) >= 0
				&& this.data.compareTo(stopData) <= 0) {
			System.out.print(DataUtl.txtData(this.data) + "; ");
			System.out.print(this.client + "; ");
			System.out.print(this.goods + "; ");
			System.out.print(this.number + "; ");
			System.out.print(this.cost + "; ");
			System.out.println("Summ: " + this.cost * this.number + ".");
			return true;
		}
		return false;
	}

}
