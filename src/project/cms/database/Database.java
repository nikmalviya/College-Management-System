package project.cms.database;

import java.sql.*;
import javafx.scene.control.Alert;

public class Database {

    private static Connection con;
    private static Database instance;
    private static String hostName;
    private static String userName;
    private static String password;
    public static ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        return res;
    }

    public static Boolean executeUpdate(String sql) throws SQLException {
        Boolean res;
        Statement stmt = con.createStatement();
        res = stmt.execute(sql);
        return res;
    }

    public static Database getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public static void init() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306?useSSL=true&requireSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", userName, password);
        } catch (SQLException ex) {
            Alert al = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            al.setHeaderText(String.valueOf(ex.getErrorCode()));
            al.showAndWait();
            System.exit(0);
        }
        instance = new Database();
    }
}    

    
