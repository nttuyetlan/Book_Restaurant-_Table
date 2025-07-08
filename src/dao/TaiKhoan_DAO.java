package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
    ArrayList<TaiKhoan> dsTK;
    
    public TaiKhoan_DAO() {
        dsTK = new ArrayList<TaiKhoan>();
    }
    
    public ArrayList<TaiKhoan> getAllTK(){
        dsTK.clear(); // Xóa danh sách cũ
        try {
            ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
            // Sửa query để lấy dữ liệu dưới dạng binary hoặc string hex
            PreparedStatement stmt = con.prepareStatement(
                "SELECT tenTaiKhoan, CONVERT(VARCHAR(MAX), matKhau, 2) AS matKhauHex, maNV FROM TaiKhoan");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String tenTK = rs.getString("tenTaiKhoan");
                String matKhauHex = rs.getString("matKhauHex");
                String maNV = rs.getString("maNV");
                TaiKhoan tk = new TaiKhoan(tenTK, matKhauHex, new NhanVien(maNV));
                dsTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsTK;
    }
    
    // Phương thức mã hóa mật khẩu bằng SHA-512
    public String hashPasswordSHA512(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean checkPassword(String inputPassword, String storedHashHex) {
        try {
            String hashedInput = hashPasswordSHA512(inputPassword);
            
            if (storedHashHex.startsWith("0x")) {
                storedHashHex = storedHashHex.substring(2);
            }
            
            return hashedInput.equalsIgnoreCase(storedHashHex);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean insertTK(TaiKhoan tk) {
        int n = 0;
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO TaiKhoan VALUES(?, HASHBYTES('SHA2_512', ?), ?)");
            stmt.setString(1, tk.getTenTaiKhoan());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getNhanVien().getMaNV());
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    public boolean deleteTK(String tenTaiKhoan) {
        int n = 0;
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM TaiKhoan WHERE tenTaiKhoan=?");
            stmt.setString(1, tenTaiKhoan);
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    public boolean updateTK(TaiKhoan tk) {
        int n = 0;
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE TaiKhoan SET matKhau=HASHBYTES('SHA2_512', ?) WHERE tenTaiKhoan=?");
            stmt.setString(1, tk.getMatKhau()); 
            stmt.setString(2, tk.getTenTaiKhoan());
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    public TaiKhoan checkLogin(String username, String password) {
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT tk.tenTaiKhoan, CONVERT(VARCHAR(MAX), tk.matKhau, 2) AS matKhauHex, tk.maNV " +
                "FROM TaiKhoan tk " +
                "WHERE tk.tenTaiKhoan = ? AND tk.matKhau = HASHBYTES('SHA2_512', ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tenTK = rs.getString("tenTaiKhoan");
                String matKhauHex = rs.getString("matKhauHex");
                String maNV = rs.getString("maNV");
                return new TaiKhoan(tenTK, matKhauHex, new NhanVien(maNV));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 // Thêm vào class TaiKhoan_DAO
    public TaiKhoan findAccountByUsername(String username) {
        try {
        	ConnectDB.getInstance();
 			Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "SELECT tenTaiKhoan, CONVERT(VARCHAR(MAX), matKhau, 2) AS matKhauHex, maNV " +
                "FROM TaiKhoan WHERE tenTaiKhoan = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tenTK = rs.getString("tenTaiKhoan");
                String matKhauHex = rs.getString("matKhauHex");
                String maNV = rs.getString("maNV");
                return new TaiKhoan(tenTK, matKhauHex, new NhanVien(maNV));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String username, String newPassword) {
        int n = 0;
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE TaiKhoan SET matKhau=HASHBYTES('SHA2_512', ?) WHERE tenTaiKhoan=?");
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // Phương thức kiểm tra mã NV cho tài khoản (dùng để xác thực)
    public boolean validateEmployeeCode(String username, String soCCCD) {
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT tk.tenTaiKhoan " +
                         "FROM TaiKhoan tk JOIN NhanVien nv ON tk.maNV = nv.maNV " +
                         "WHERE tk.tenTaiKhoan = ? AND nv.soCCCD = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, soCCCD);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true nếu có tài khoản hợp lệ
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private String generateRandomPassword(int length) {
        String digits = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }


}