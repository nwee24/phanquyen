package CuoiKiOOP;

import Model.NguoiDung;
import Service.NguoiDungService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageUI extends JFrame {
    private JTable tblUsers;
    private DefaultTableModel model;
    private NguoiDungService nguoiDungService;

    public ManageUI() {
        nguoiDungService = new NguoiDungService();

        setTitle("Quản lý tài khoản - Manager");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Username", "Role", "Số ĐT", "Ngày Sinh"}, 0);
        tblUsers = new JTable(model);
        add(new JScrollPane(tblUsers), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Thêm tài khoản");
        JButton btnDelete = new JButton("Xóa tài khoản");
        JButton btnRefresh = new JButton("Tải lại");
        JPanel pnlActions = new JPanel();
        pnlActions.add(btnAdd);
        pnlActions.add(btnDelete);
        pnlActions.add(btnRefresh);
        add(pnlActions, BorderLayout.SOUTH);

        loadUsers();

        btnRefresh.addActionListener(e -> loadUsers());
        btnDelete.addActionListener(e -> deleteUser());
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiDialogThemNguoiDung();
            }
        });
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<NguoiDung> users = nguoiDungService.getAllUsers("ad2");
        for (NguoiDung user : users) {
            if (user.getRole().equals("ad3")) {
                System.out.println("User loaded: " + user.getUsername() + ", " + user.getRole());
                model.addRow(new Object[]{
                        user.getId(), user.getUsername(), user.getRole(), user.getSdt(), user.getNgaySinh()
                });
            }
        }
    }

    private void deleteUser() {
        int selectedRow = tblUsers.getSelectedRow();
        if (selectedRow != -1) {
            String username = model.getValueAt(selectedRow, 1).toString();
            boolean success = nguoiDungService.deleteUser(username, "ad2");
            if (success) {
                System.out.println("User deleted: " + username);
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                loadUsers();
            } else {
                System.out.println("Failed to delete user: " + username);
                JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản!");
            }
        }
    }

    private void hienThiDialogThemNguoiDung() {
        JTextField tenField = new JTextField();
        JTextField ngaySinhField = new JTextField();
        JTextField sdtField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        String[] roles = {"ad3"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Tên:"));
        panel.add(tenField);
        panel.add(new JLabel("Ngày sinh (YYYY-MM-DD):"));
        panel.add(ngaySinhField);
        panel.add(new JLabel("Số ĐT:"));
        panel.add(sdtField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm tài khoản mới", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String ten = tenField.getText();
            String ngaySinh = ngaySinhField.getText();
            String sdt = sdtField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            boolean success = nguoiDungService.addUser(ten, ngaySinh, sdt, username, password, role, "ad2");
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm tài khoản!");
            }
        }
    }

}
