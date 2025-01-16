package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static DBConnection instance;
   @Getter
    private Connection connection;
    private DBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Thogakade";
        String user = "root";
        String password = "1234";
        connection = DriverManager.getConnection(url,user,password);
    }
    public static DBConnection getInstance() throws SQLException {
        if(instance == null){
             instance = new DBConnection();
        }
        return instance;
    }


}
