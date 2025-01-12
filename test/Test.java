package test;

import CuoiKiOOP.AdminUI;
import CuoiKiOOP.ManageUI;
import CuoiKiOOP.QuanLiSanPham;
import Service.NguoiDungService;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog("Tên đăng nhập:");
        String password = JOptionPane.showInputDialog("Mật khẩu:");

        NguoiDungService nguoiDungService = new NguoiDungService();
        
        System.out.println("Đang đăng nhập với tài khoản: " + username);
        String role = nguoiDungService.login(username, password);
        System.out.println("Kết quả đăng nhập: " + role);

        if (role == null) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng!");
        } else {
            switch (role) {
                case "ad1":
                    System.out.println("Mở AdminUI");
                    new AdminUI().setVisible(true);
                    break;
                case "ad2":
                    System.out.println("Mở ManageUI");
                    new ManageUI().setVisible(true);
                    break;
                case "ad3":
                    System.out.println("Mở QuanLiSanPham");
                    new QuanLiSanPham("Quản lý sản phẩm").showWindow();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Không xác định quyền truy cập!");
            }
        }
    }
}
