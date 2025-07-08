package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaLamViec;

public class CaLamViec_DAO {
    private ArrayList<CaLamViec> dsCLV = new ArrayList<>();

    public ArrayList<CaLamViec> getAllCaLamViec() {
        dsCLV.clear();
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CaLamViec");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maCaLV = rs.getString("maCaLV");
                String tenCaLV = rs.getString("tenCaLV");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
                boolean trangThai = rs.getBoolean("trangThai");

                CaLamViec caLV = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
                dsCLV.add(caLV);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCLV;
    }

    public boolean insertCaLamViec(CaLamViec ca) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "INSERT INTO CaLamViec (maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ca.getMaCaLV());
            stmt.setString(2, ca.getTenCaLV());
            stmt.setTimestamp(3, Timestamp.valueOf(ca.getThoiGianBatDau()));
            stmt.setTimestamp(4, Timestamp.valueOf(ca.getThoiGianKetThuc()));
            stmt.setBoolean(5, ca.getTrangThai());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCaLamViec(CaLamViec ca) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "UPDATE CaLamViec SET tenCaLV = ?, thoiGianBatDau = ?, thoiGianKetThuc = ?, trangThai = ? WHERE maCaLV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ca.getTenCaLV());
            stmt.setTimestamp(2, Timestamp.valueOf(ca.getThoiGianBatDau()));
            stmt.setTimestamp(3, Timestamp.valueOf(ca.getThoiGianKetThuc()));
            stmt.setBoolean(4, ca.getTrangThai());
            stmt.setString(5, ca.getMaCaLV());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CaLamViec getCaLamViecById(String maCaLV) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM CaLamViec WHERE maCaLV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CaLamViec(
                    rs.getString("maCaLV"),
                    rs.getString("tenCaLV"),
                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
                    rs.getBoolean("trangThai")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public CaLamViec getCaLamViecByMaCaLV(String maCaLV) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM CaLamViec WHERE maCaLV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tenCaLV = rs.getString("tenCaLV");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
                boolean trangThai = rs.getBoolean("trangThai");
                
                return new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
