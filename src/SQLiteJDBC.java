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



    private static java.sql.Date toSQLDate(java.util.Date d){
        return new java.sql.Date(d.getTime());
    }
}