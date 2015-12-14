package db;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManagerDerby extends DBManager{
    private ShopDerbyInit derbyDB;

    public ManagerDerby() {
        setDataSourceConnection( new DataSourceConnection("resources/derby.properties"));
    }

    public void initData() {
        Path path = Paths.get("DerbyDB");
        if (Files.notExists(path)) {
            System.out.println("Create new Derby");
            derbyDB = new ShopDerbyInit(getDataSourceConnection());
            derbyDB.initData();
        } else {
            System.out.println("Derby already exist");
        }
    }
}
