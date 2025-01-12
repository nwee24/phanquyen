package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerService {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBQLSP;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Kết nối thành công");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
        return conn;
    }
}
