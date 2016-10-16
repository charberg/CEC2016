import java.sql.*;
import java.util.*;

public final class SQLiteJDBC
{

    public static void main(String args[]){
        ArrayList<FoodStock> foodStocks = new ArrayList<>();
        foodStocks.add(new FoodStock("test3", new java.util.Date(), 42, 123, 12, 0));
        foodStocks.add(new FoodStock("test3", new java.util.Date(), 42, 1234, 12, 0));
        bulkInsert(foodStocks);
    }
    private SQLiteJDBC(){}

    public static void bulkInsert(ArrayList<FoodStock> foodStocks){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(true);
            PreparedStatement foodItemStatement;
            PreparedStatement foodStockStatement;
            foodItemStatement = c.prepareStatement("INSERT INTO food_items(name, restock_limit, popularity)  " +
                    "SELECT ?, ?, ? WHERE NOT EXISTS(SELECT * FROM food_items WHERE name = ?)");
            foodStockStatement = c. prepareStatement("INSERT INTO food_stock(name, batch_number, stock, exp)  VALUES (?, ?, ?, ?)");
            for(FoodStock foodStock : foodStocks){
                foodItemStatement.setString(1, foodStock.getName());
                foodItemStatement.setInt(2, foodStock.getRestockLimit());
                foodItemStatement.setInt(3, foodStock.getPopularity());
                foodItemStatement.setString(4, foodStock.getName());
                foodItemStatement.executeUpdate();
                foodStockStatement.setString(1, foodStock.getName());
                foodStockStatement.setInt(2, foodStock.getBatchNumber());
                foodStockStatement.setInt(3, foodStock.getStock());
                foodStockStatement.setDate(4, toSQLDate(foodStock.getExpiryDate()));
                foodStockStatement.executeUpdate();
            }
            foodItemStatement.close();
            foodStockStatement.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void insertFoodItem(FoodItem foodItem){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("INSERT INTO food_items(name, restock_limit, popularity)  VALUES (?, ?, ?)");
            s.setString(1, foodItem.getName());
            s.setInt(2, foodItem.getRestockLimit());
            s.setInt(3, foodItem.getPopularity());
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void insertFoodStock(FoodStock foodStock){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("INSERT INTO food_stock(name, batch_number, stock, exp)  VALUES (?, ?, ?, ?)");
            s.setString(1, foodStock.getName());
            s.setInt(2, foodStock.getBatchNumber());
            s.setInt(3, foodStock.getStock());
            s.setDate(4, toSQLDate(foodStock.getExpiryDate()));
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static ArrayList<FoodItem> selectFoodItem(){
        Connection c = null;
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("SELECT food_stock.name, restock_limit, popularity, SUM(stock) AS stockTotal " +
                    "FROM food_items INNER JOIN food_stock WHERE food_items.name = food_stock.name GROUP BY food_items.name");
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                foodItems.add(new FoodItem(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
            }
            s.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return foodItems;
    }

    public static ArrayList<FoodStock> selectFoodStock(){
        Connection c = null;
        ArrayList<FoodStock> foodStocks = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("SELECT food_stock.name, exp, stock, batch_number, restock_limit, popularity " +
                    "FROM food_stock INNER JOIN food_items ON food_stock.name = food_items.name");
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                foodStocks.add(new FoodStock(rs.getString(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }
            s.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return foodStocks;
    }

    private static java.sql.Date toSQLDate(java.util.Date d){
        return new java.sql.Date(d.getTime());
    }
}