package reports;

import utl.DataUtl;

import java.util.ArrayList;
import java.util.Calendar;

public class AccountBook {
	private ArrayList<SaleRecord> registerSale;

	public AccountBook() {
		registerSale = new ArrayList<>();
	}
	
	public void setRecord(SaleRecord record) {
		registerSale.add(record);
	}

	public ArrayList<SaleRecord> getRegisterSale() {
		return registerSale;
	}

	public void showDataSale(Calendar startData, Calendar stopData) {
		
		if(registerSale == null) {
			return;
		}
		String total = "";
		int trans = 0;
		int numGoods = 0;
		double profit = 0.0; 
		System.out.println(" Sale since :"+ DataUtl.txtData(startData) + " to "
				+ DataUtl.txtData(stopData));
		for (SaleRecord record : registerSale) {
			if (record != null) {
				if (record.printDataRecord(startData, stopData)) {
					trans++;
					numGoods += record.getNumber();
					profit += record.getNumber()*record.getCost();
				}
			}
		}
		total += "Transactions: " + trans + "; ";
		total += "Goods: " + numGoods + "; ";
		total += "Profit: " + profit;
		System.out.println(total);
	}


	
}
