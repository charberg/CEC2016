import java.sql.*;
import java.util.*;

public final class SQLiteJDBC
{
    private SQLiteJDBC(){}

    public static void updateFoodItem(FoodItem foodItem){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("UPDATE food_items SET restock_limit = ?, popularity = ? WHERE name = ?");
            s.setString(3, foodItem.getName());
            s.setInt(1, foodItem.getRestockLimit());
            s.setInt(2, foodItem.getPopularity());
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void updateFoodStock(FoodStock foodStock){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("UPDATE food_stock SET stock = ?, exp = ? WHERE name = ? AND batch_number = ?");
            s.setInt(1, foodStock.getStock());
            s.setTimestamp(2, toSQLDate(foodStock.getExpiryDate()));
            s.setString(3, foodStock.getName());
            s.setInt(4, foodStock.getBatchNumber());
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void bulkInsert(ArrayList<FoodStock> foodStocks){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
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
                foodStockStatement.setTimestamp(4, toSQLDate(foodStock.getExpiryDate()));
                foodStockStatement.executeUpdate();
            }
            foodItemStatement.close();
            foodStockStatement.close();
            c.commit();
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
            s.setTimestamp(4, toSQLDate(foodStock.getExpiryDate()));
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

    public static void insertEmployee(Employee employee){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("INSERT INTO employees(name, start_monday, end_monday, start_tuesday, end_tuesday, start_wednesday, " +
                    "end_wednesday, start_thursday, end_thursday, start_friday, end_friday, start_saturday, end_saturday, overnight)   " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            s.setString(1, employee.getName());
            s.setTimestamp(2, toSQLDate(employee.startTimes.get("monday")));
            s.setTimestamp(3, toSQLDate(employee.endTimes.get("monday")));
            s.setTimestamp(4, toSQLDate(employee.startTimes.get("tuesday")));
            s.setTimestamp(5, toSQLDate(employee.endTimes.get("tuesday")));
            s.setTimestamp(6, toSQLDate(employee.startTimes.get("wednesday")));
            s.setTimestamp(7, toSQLDate(employee.endTimes.get("wednesday")));
            s.setTimestamp(8, toSQLDate(employee.startTimes.get("thursday")));
            s.setTimestamp(9, toSQLDate(employee.endTimes.get("thursday")));
            s.setTimestamp(10, toSQLDate(employee.startTimes.get("friday")));
            s.setTimestamp(11, toSQLDate(employee.endTimes.get("friday")));
            s.setTimestamp(12, toSQLDate(employee.startTimes.get("saturday")));
            s.setTimestamp(13, toSQLDate(employee.endTimes.get("saturday")));
            s.setBoolean(14, employee.getOvernight());
            s.executeUpdate();
            s.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static ArrayList<Employee> selectEmployees(){
        Connection c = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(false);
            PreparedStatement s;
            s = c.prepareStatement("SELECT employee_ID, name, start_monday, end_monday, start_tuesday, end_tuesday, start_wednesday, " +
                    "end_wednesday, start_thursday, end_thursday, start_friday, end_friday, start_saturday, end_saturday, overnight " +
                    "FROM employees");
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), rs.getDate(6)
                        , rs.getDate(7), rs.getDate(8), rs.getDate(9), rs.getDate(10), rs.getDate(11), rs.getDate(12), rs.getDate(13),
                        rs.getDate(14), rs.getBoolean(15)));
            }
            s.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return employees;

    }

    public static void bulkInsertEmployees(ArrayList<Employee> employees){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leos.db");
            c.setAutoCommit(true);
            PreparedStatement s = c.prepareStatement("INSERT INTO employees(name, start_monday, end_monday, start_tuesday, end_tuesday, start_wednesday, " +
                            "end_wednesday, start_thursday, end_thursday, start_friday, end_friday, start_saturday, end_saturday, overnight)   " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(Employee employee : employees){
                s.setString(1, employee.getName());
                s.setTimestamp(2, toSQLDate(employee.startTimes.get("monday")));
                s.setTimestamp(3, toSQLDate(employee.endTimes.get("monday")));
                s.setTimestamp(4, toSQLDate(employee.startTimes.get("tuesday")));
                s.setTimestamp(5, toSQLDate(employee.endTimes.get("tuesday")));
                s.setTimestamp(6, toSQLDate(employee.startTimes.get("wednesday")));
                s.setTimestamp(7, toSQLDate(employee.endTimes.get("wednesday")));
                s.setTimestamp(8, toSQLDate(employee.startTimes.get("thursday")));
                s.setTimestamp(9, toSQLDate(employee.endTimes.get("thursday")));
                s.setTimestamp(10, toSQLDate(employee.startTimes.get("friday")));
                s.setTimestamp(11, toSQLDate(employee.endTimes.get("friday")));
                s.setTimestamp(12, toSQLDate(employee.startTimes.get("saturday")));
                s.setTimestamp(13, toSQLDate(employee.endTimes.get("saturday")));
                s.setBoolean(14, employee.getOvernight());
                s.executeUpdate();
            }
            s.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private static java.sql.Timestamp toSQLDate(java.util.Date d){
        if(d == null)
            return null;
        return new java.sql.Timestamp(d.getTime());
    }
}