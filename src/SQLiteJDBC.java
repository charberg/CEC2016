import java.sql.*;

public final class SQLiteJDBC
{
    private SQLiteJDBC(){}

    public static void main(String args[]){
        insertFoodItem("test");
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
}