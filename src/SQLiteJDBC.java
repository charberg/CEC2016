import java.sql.*;
import java.util.*;

public final class SQLiteJDBC
{
    private SQLiteJDBC(){}

    public static void main(String args[]){
        insertFoodStock("test", 123, 12, new java.util.Date());
    }

    public static void insertFoodItem(String name) {
        insertFoodItem(name, null);
    }
    public static void insertFoodItem(String name, Integer restockLimit){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            if(restockLimit != null){
                s = c.prepareStatement("INSERT INTO food_items(name, restock_limit)  VALUES (?, ?)");
                s.setInt(2, restockLimit);
            } else {
                s = c.prepareStatement("INSERT INTO food_items(name)  VALUES (?)");
            }
            s.setString(1, name);
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void insertFoodStock(String name, Integer batchNumber, Integer stock, java.util.Date expiryDate){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("INSERT INTO food_stock(name, batch_number, stock, exp)  VALUES (?, ?, ?, ?)");
            s.setString(1, name);
            s.setInt(2, batchNumber);
            s.setInt(3, stock);
            s.setDate(4, toSQLDate(expiryDate));
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