package shop;

import ui.ShopUI;

import java.awt.SplashScreen;

public class LauncherShop {

	public static void main(String[] args) throws Exception {
		
		SplashScreen splash = SplashScreen.getSplashScreen();
		Thread.sleep(3000);
		splash.close();
		
		Shop shop = new Shop();
		ShopUI shopUI = new ShopUI(shop);
	}

}
