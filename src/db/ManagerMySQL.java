package db;


public class ManagerMySQL extends DBManager {
    private ShopSQLGenerator generatorMySQL;


    public ManagerMySQL() {
        setDataSourceConnection(new DataSourceConnection("resources/mysql.properties"));
    }

    public void initData() {
        if (false) {
            generatorMySQL = new ShopSQLGenerator(getDataSourceConnection());
            generatorMySQL.initData();
        }
    }
}
