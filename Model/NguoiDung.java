package Model;

public class NguoiDung {
    private int id; // MaNV
    private String ten; // Ten
    private String ngaySinh; // NgaySinh
    private String Sdt; // SDT
    private String username; // TaiKhoan
    private String password; // MatKhau
    private String role; // CapDo
    private boolean isDeleted; // IsDeleted

    public NguoiDung(int id, String ten, String ngaySinh, String Sdt, String username, String password, String role, boolean isDeleted) {
        this.id = id;
        this.ten = ten;
        this.ngaySinh= ngaySinh;
        this.Sdt = Sdt;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public NguoiDung(int id, String name, String username, String password, String role, boolean isDeleted) {
        this.id = id;
        this.ten = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String birthDate) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String phoneNumber) {
        this.Sdt = Sdt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
