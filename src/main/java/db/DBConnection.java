package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    Connection connection;
    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "123456");

    }
    public Connection getConnection(){
        return connection;
    }

    public static DBConnection getInstance(){
        try {
            return  dbConnection==null?dbConnection= new DBConnection():dbConnection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
