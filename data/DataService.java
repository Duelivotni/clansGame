package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataService {
    private static final String JDBS_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";  
    private static final String USER = "sa"; 
    private static final String PASS = "";

    private Connection connection = null;
    private Statement statement = null;
    
    public void closeConnection() throws SQLException {
        if (connection != null) {
            System.out.println("Closing connection to database..");
            connection.close();
        }
    }

    public Connection getConnection() throws SQLException {
        System.out.println("Connecting to database..");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }

    public void setUpDatabase() {
        try {
            Class.forName(JDBS_DRIVER);
            System.out.println("Connecting to database..");
            connection = this.getConnection();

            System.out.println("Creating tables for clans and user transactions..");
            statement = connection.createStatement();

            statement.executeUpdate(
                "create table if not exists clan " +
                "(id uuid default random_uuid() not null primary key, " +
                "name varchar(100) not null, " +
                "gold integer default 100 not null, " +
                "health_points integer default 100 not null, " +
                "exp integer default 0 not null)"
            );
            statement.executeUpdate(
                "create table if not exists user_transaction " +
                "(id uuid default random_uuid() not null primary key, " +
                "user_id uuid not null, " +
                "clan_id uuid not null, " +
                "gold_ammount integer not null, " +
                "transaction_type varchar(100) not null, " +
                "date_time timestamp default current_timestamp() not null)"
            );
            System.out.println("Tables have been created");
        } catch (Exception e) {
            System.out.println("Failed to create tables: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) this.closeConnection();
            } catch (SQLException se) {
                System.out.println("Failed to close statement or connection: " + se.getMessage());
                se.printStackTrace();
            }
        }
    }
}
