package shop;

import db.DBManager;
import db.ManagerDerby;
import db.ManagerMySQL;
import ui.ShopSwingUI;
import ui.ShopUI;

import java.awt.SplashScreen;

public class LauncherShop {
	private static DBManager managerDB;

	public static void main(String[] args) throws Exception {


		SplashScreen splash = SplashScreen.getSplashScreen();
		Thread.sleep(3000);
		splash.close();

		if(args.length > 0 && args[0].equals("mysql")) {
			managerDB = new ManagerMySQL();
		} else {
			managerDB = new ManagerDerby();
		}

		Shop shop = new ShopDB(managerDB);
		ShopUI shopUI = new ShopSwingUI(shop);
	}

}
