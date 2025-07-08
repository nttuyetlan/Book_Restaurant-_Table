package dao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import entity.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;

public class ThongKeMonAn_DAO {
    private Connection connection;
    
    public ThongKeMonAn_DAO() {
        // Khởi tạo kết nối database
    	try {
            // Make sure database is connected
            ConnectDB.getInstance().connect();
            // Then get the connection
            this.connection = ConnectDB.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }		
    }
    
    /**
     * Lấy thống kê doanh thu theo loại món ăn
     * @param month tháng (null nếu tất cả)
     * @param year năm
     * @return Map<String, Double> - key: tên loại món, value: doanh thu
     */
    public Map<String, Double> getDoanhThuTheoLoaiMon(Integer month, int year) {
        Map<String, Double> result = new HashMap<>();
        String sql = "SELECT lm.tenLoai, SUM(cthd.soLuong * ma.donGia * (1 + ma.thueMon)) as doanhThu " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN LoaiMon lm ON ma.maLoai = lm.maLoai " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE YEAR(hd.thoiGianThanhToan) = ? " +
                    (month != null ? "AND MONTH(hd.thoiGianThanhToan) = ? " : "") +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY lm.maLoai, lm.tenLoai " +
                    "ORDER BY doanhThu DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, year);
            if (month != null) {
                stmt.setInt(2, month);
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("tenLoai"), rs.getDouble("doanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Lấy top món ăn bán chạy theo doanh thu
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @param topCount số lượng top
     * @return Map<String, Double> - key: tên món ăn, value: doanh thu
     */
    public Map<String, Double> getTopMonAnTheoDoanhThu(LocalDate startDate, LocalDate endDate, int topCount) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        Map<String, Double> result = new LinkedHashMap<>();
        String sql = "SELECT TOP " + topCount + " ma.tenMonAn, " +
                    "SUM(cthd.soLuong * ma.donGia * (1 + ma.thueMon)) as doanhThu " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ? " +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY ma.maMonAn, ma.tenMonAn " +
                    "ORDER BY doanhThu DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No data found for getTopMonAnTheoDoanhThu with startDate=" + startDate + ", endDate=" + endDate);
            }
            while (rs.next()) {
                String tenMonAn = rs.getString("tenMonAn");
                double doanhThu = rs.getDouble("doanhThu");
                System.out.println("MonAn: " + tenMonAn + ", DoanhThu: " + doanhThu);
                result.put(tenMonAn, doanhThu);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getTopMonAnTheoDoanhThu: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Lấy top món ăn bán chạy theo số lượng
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @param topCount số lượng top
     * @return Map<String, Integer> - key: tên món ăn, value: số lượng bán
     */
    public Map<String, Integer> getTopMonAnTheoSoLuong(LocalDate startDate, LocalDate endDate, int topCount) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT TOP " + topCount + " ma.tenMonAn, SUM(cthd.soLuong) as soLuong " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE hd.thoiGianThanhToan IS NOT NULL " +
                    "AND CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ? " +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY ma.maMonAn, ma.tenMonAn " +
                    "ORDER BY soLuong DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
            }
            while (rs.next()) {
                String tenMonAn = rs.getString("tenMonAn");
                int soLuong = rs.getInt("soLuong");
                result.put(tenMonAn, soLuong);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getTopMonAnTheoSoLuong: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Lấy tổng doanh thu trong khoảng thời gian
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return tổng doanh thu
     */
    public double getTongDoanhThu(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SUM(hd.tongThanhToan) as tongDoanhThu\r\n"
        		+ "FROM HoaDon hd\r\n"
        		+ "WHERE CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ?\r\n"
        		+ "AND hd.trangThai = 1";
        
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(startDate));
                stmt.setDate(2, Date.valueOf(endDate));
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("tongDoanhThu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }

    /**
     * Lấy món ăn bán chạy nhất trong khoảng thời gian
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return tên món ăn bán chạy nhất
     */
    public String getMonAnBanChayNhat(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        String sql = "SELECT TOP 1 ma.tenMonAn " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ? " +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY ma.maMonAn, ma.tenMonAn " +
                    "ORDER BY SUM(cthd.soLuong) DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenMonAn = rs.getString("tenMonAn");
                return tenMonAn;
            } else {
                System.out.println("No data found for getMonAnBanChayNhat with startDate=" + startDate + ", endDate=" + endDate);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getMonAnBanChayNhat: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "Không có dữ liệu";
    }
    
    /**
     * Lấy loại món ăn được ưa thích nhất
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return tên loại món được ưa thích nhất
     */
    public String getLoaiMonUaThichNhat(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        String sql = "SELECT TOP 1 lm.tenLoai " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN LoaiMon lm ON ma.maLoai = lm.maLoai " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ? " +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY lm.maLoai, lm.tenLoai " +
                    "ORDER BY SUM(cthd.soLuong) DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenLoai = rs.getString("tenLoai");
                System.out.println("LoaiMonUaThichNhat: " + tenLoai);
                return tenLoai;
            } else {
                System.out.println("No data found for getLoaiMonUaThichNhat with startDate=" + startDate + ", endDate=" + endDate);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getLoaiMonUaThichNhat: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "Không có dữ liệu";
    }
    
    /**
     * Lấy báo cáo chi tiết thống kê món ăn
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return List<Object[]> - mỗi Object[] chứa: [tenMonAn, tenLoai, soLuong, doanhThu]
     */
    public List<Object[]> getBaoCaoChiTiet(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT ma.tenMonAn, lm.tenLoai, SUM(cthd.soLuong) as soLuong, " +
                    "SUM(cthd.soLuong * ma.donGia * (1 + ma.thueMon)) as doanhThu " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN MonAn ma ON cthd.maMonAn = ma.maMonAn " +
                    "JOIN LoaiMon lm ON ma.maLoai = lm.maLoai " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE CAST(hd.thoiGianThanhToan AS DATE) BETWEEN ? AND ? " +
                    "AND hd.trangThai = 1 " +
                    "GROUP BY ma.maMonAn, ma.tenMonAn, lm.tenLoai " +
                    "ORDER BY doanhThu DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No data found for getBaoCaoChiTiet with startDate=" + startDate + ", endDate=" + endDate);
            }
            while (rs.next()) {
                Object[] row = {
                    rs.getString("tenMonAn"),
                    rs.getString("tenLoai"),
                    rs.getInt("soLuong"),
                    rs.getDouble("doanhThu")
                };
                System.out.println("BaoCaoChiTiet: " + row[0] + ", " + row[1] + ", " + row[2] + ", " + row[3]);
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBaoCaoChiTiet: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
}