package CuoiKiOOP;

import Service.NguoiDungService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private NguoiDungService nguoiDungService;

    public LoginUI() {
        nguoiDungService = new NguoiDungService();

        setTitle("Đăng nhập");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Tên đăng nhập:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Đăng nhập");
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                String role = nguoiDungService.login(username, password);

                if (role != null) {
                    dispose();
                    if (role.equals("ad1")) {
                        new AdminUI().setVisible(true);
                    } else if (role.equals("ad2")) {
                        new ManageUI().setVisible(true);
                    } else if (role.equals("ad3")) {
                        new EmployeeUI().setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginUI().setVisible(true);
    }
}
