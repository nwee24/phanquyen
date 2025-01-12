package Service;

import Model.NguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungService {
    private Connection conn;

    public NguoiDungService() {
        conn = SQLServerService.getConnection();
        if (conn == null) {
            throw new RuntimeException("Không thể kết nối đến cơ sở dữ liệu");
        }
    }

    public String login(String username, String password) {
        System.out.println("Attempting to login with username: " + username);
        String query = "SELECT CapDo FROM NguoiDung WHERE TaiKhoan = ? AND MatKhau = ? AND IsDeleted = 0";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Đăng nhập thành công với vai trò: " + rs.getString("CapDo"));
                return rs.getString("CapDo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Đăng nhập thất bại");
        return null;
    }


    public boolean addUser(String ten,String ngaySinh, String sdt,String username, String password, String role,  String currentRole) {
        if (currentRole.equals("ad3")) {
            System.out.println("Tài khoản ad3 không thể thêm tài khoản mới");
            return false;
        }
        if (currentRole.equals("ad2") && !role.equals("ad3")) {
            System.out.println("Tài khoản ad2 chỉ có thể thêm tài khoản ad3");
            return false;
        }

        Date sqlNgaySinh;
        try{
            sqlNgaySinh= Date.valueOf(ngaySinh);
        }catch (IllegalArgumentException e){
            sqlNgaySinh= new Date(System.currentTimeMillis());
            System.out.println("Ngay sinh khong hop le"+ sqlNgaySinh);
        }

        String sql = "INSERT INTO NguoiDung (Ten, NgaySinh, SDT, TaiKhoan, MatKhau, CapDo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ten);
            ps.setDate(2, sqlNgaySinh);
            ps.setString(3, sdt);
            ps.setString(4,username);
            ps.setString(5, password);
            ps.setString(6, role);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Thêm tài khoản thành công");
                return true;
            } else {
                System.out.println("Thêm tài khoản thất bại, không có hàng nào bị ảnh hưởng");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi SQL khi thêm tài khoản: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username, String currentRole) {
        String sqlCheckRole = "SELECT CapDo FROM NguoiDung WHERE TaiKhoan = ?";
        String sqlDelete = "DELETE FROM NguoiDung WHERE TaiKhoan = ?";
        try (PreparedStatement psCheckRole = conn.prepareStatement(sqlCheckRole)) {
            psCheckRole.setString(1, username);
            ResultSet rs = psCheckRole.executeQuery();

            if (rs.next()) {
                String targetRole = rs.getString("CapDo");

                if ((currentRole.equals("ad1") && !targetRole.equals("ad1")) ||
                        (currentRole.equals("ad2") && targetRole.equals("ad3"))) {
                    try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                        psDelete.setString(1, username);
                        return psDelete.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NguoiDung> getAllUsers(String currentRole) {
        List<NguoiDung> users = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung WHERE IsDeleted = 0";

        if (currentRole.equals("ad1")) {
            sql += " AND CapDo != 'ad1'";
        } else if (currentRole.equals("ad2")) {
            sql += " AND CapDo = 'ad3'";
        } else {
            return users;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiDung user = new NguoiDung(
                        rs.getInt("MaNV"),
                        rs.getString("Ten"),
                        rs.getString("NgaySinh"),
                        rs.getString("SDT"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getString("CapDo"),
                        rs.getBoolean("IsDeleted")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi sql khi lay danh sach nguoi dung"+ e.getMessage());
        }
        return users;
    }
}
