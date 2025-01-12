package CuoiKiOOP;

import Model.SanPham;
import Service.SanPhamService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class EmployeeUI extends JFrame {
    private JTable tblProducts;
    private DefaultTableModel model;

    public EmployeeUI() {
        setTitle("Danh sách sản phẩm");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Tên sản phẩm", "Giá", "Mô tả"}, 0);
        tblProducts = new JTable(model);
        add(new JScrollPane(tblProducts), BorderLayout.CENTER);

        JButton btnSearch = new JButton("Tìm kiếm sản phẩm");
        JButton btnRefresh = new JButton("Tải lại danh sách");
        JPanel pnlActions = new JPanel();
        pnlActions.add(btnSearch);
        pnlActions.add(btnRefresh);
        add(pnlActions, BorderLayout.SOUTH);

        loadProducts();

        btnRefresh.addActionListener(e -> loadProducts());
        btnSearch.addActionListener(e -> searchProduct());
    }

    private void loadProducts() {
        model.setRowCount(0);
        SanPhamService sanPhamService = null;
        Vector<SanPham> products = sanPhamService.layTatCaSanPham();
        for (SanPham sp : products) {
            model.addRow(new Object[]{
                    sp.getMaSp(), sp.getTenSp(), sp.getSoLuong(), sp.getDonGia()
            });
        }
    }

    private void searchProduct() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên sản phẩm cần tìm:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.setRowCount(0);
            SanPhamService sanPhamService = null;
            Vector<SanPham> products = sanPhamService.timKiemSanPham(keyword);
            for (SanPham sp : products) {
                model.addRow(new Object[]{
                        sp.getMaSp(), sp.getTenSp(), sp.getSoLuong(), sp.getDonGia()
                });
            }
        }
    }
}
