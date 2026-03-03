package ra.coursemanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/course_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123456";

    //Đóng và mở kết nối
    public static Connection getConnection() {
        //Khai báo Driver
        try {
            Class.forName(DRIVER);
            //Mở kết nối
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}




