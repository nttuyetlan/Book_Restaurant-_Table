//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import connectDB.ConnectDB;
//import entity.CaLamViec;
//import entity.ChiTietCaLamViec;
//import entity.NhanVien;
//
//public class ChiTietCaLamViec_DAO {
//    private NhanVien_DAO nhanVienDAO;
//    private CaLamViec_DAO caLamViecDAO;
//
//    public ChiTietCaLamViec_DAO() {
//        this.nhanVienDAO = new NhanVien_DAO();
//        this.caLamViecDAO = new CaLamViec_DAO();
//    }
//
//    public boolean insertChiTietCaLamViec(ChiTietCaLamViec chiTiet) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "INSERT INTO chiTietCaLamViec (maCaLV, maNV, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu) VALUES (?, ?, ?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, chiTiet.getCaLamViec().getMaCaLV());
//            stmt.setString(2, chiTiet.getNhanVien().getMaNV());
//            stmt.setTimestamp(3, Timestamp.valueOf(chiTiet.getGioBatDauThucTe()));
//            if (chiTiet.getGioKetThucThucTe() != null) {
//                stmt.setTimestamp(4, Timestamp.valueOf(chiTiet.getGioKetThucThucTe()));
//            } else {
//                stmt.setNull(4, java.sql.Types.TIMESTAMP);
//            }
//            stmt.setDouble(5, chiTiet.getSoTienBanDau());
//            stmt.setString(6, chiTiet.getGhiChu());
//            int rows = stmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean updateChiTietCaLamViecEndTime(String maCaLV, String maNV, LocalDateTime endTime, String additionalNote) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "UPDATE chiTietCaLamViec SET gioKetThucThucTe = ?, ghiChu = CONCAT(ghiChu, ' | Kết ca: ', ?) WHERE maCaLV = ? AND maNV = ? AND gioKetThucThucTe IS NULL";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
//            stmt.setString(2, additionalNote);
//            stmt.setString(3, maCaLV);
//            stmt.setString(4, maNV);
//            int rows = stmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public ChiTietCaLamViec getChiTietCaLamViecById(String maCaLV) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "SELECT * FROM chiTietCaLamViec WHERE maCaLV = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, maCaLV);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                CaLamViec caLamViec = caLamViecDAO.getCaLamViecById(rs.getString("maCaLV"));
//                NhanVien nhanVien = nhanVienDAO.getNhanVienById(rs.getString("maNV"));
//                
//                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
//                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe") != null ? 
//                    rs.getTimestamp("gioKetThucThucTe").toLocalDateTime() : null;
//                double soTienBanDau = rs.getDouble("soTienBanDau");
//                String ghiChu = rs.getString("ghiChu");
//                
//                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu);
//            }
//            return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public ChiTietCaLamViec getChiTietCaLamViec(String maCaLV, String maNV) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "SELECT ct.*, c.*, n.* " +
//                        "FROM chiTietCaLamViec ct " +
//                        "JOIN CaLamViec c ON ct.maCaLV = c.maCaLV " +
//                        "JOIN NhanVien n ON ct.maNV = n.maNV " +
//                        "WHERE ct.maCaLV = ? AND ct.maNV = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, maCaLV);
//            stmt.setString(2, maNV);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                CaLamViec caLamViec = new CaLamViec(
//                    rs.getString("maCaLV"),
//                    rs.getString("tenCaLV"),
//                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
//                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
//                    rs.getBoolean("trangThai")
//                );
//
//                NhanVien nhanVien = new NhanVien(
//                    rs.getString("maNV"),
//                    rs.getString("tenNV"),
//                    rs.getString("gioiTinh"),
//                    rs.getDate("ngaySinh").toLocalDate(),
//                    rs.getString("sdtNV"),
//                    rs.getString("emailNV"),
//                    rs.getString("diaChi"),
//                    rs.getString("chucVu"),
//                    rs.getBoolean("trangThaiLV")
//                );
//
//                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
//                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe") != null ? 
//                    rs.getTimestamp("gioKetThucThucTe").toLocalDateTime() : null;
//                double soTienBanDau = rs.getDouble("soTienBanDau");
//                String ghiChu = rs.getString("ghiChu");
//
//                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu);
//            }
//            return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
//    public ChiTietCaLamViec getActiveShiftForEmployee(String maNV) {
//        ChiTietCaLamViec chiTiet = null;
//        
//        try {
//            Connection con = ConnectDB.getInstance().getConnection();
//            String sql = "SELECT * FROM ChiTietCaLamViec CT " +
//                        "JOIN CaLamViec C ON CT.maCaLV = C.maCaLV " +
//                        "WHERE CT.maNV = ? AND C.trangThai = 1 AND CT.gioKetThucThucTe IS NULL " +
//                        "ORDER BY CT.gioBatDauThucTe DESC";
//            
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, maNV);
//            
//            ResultSet rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                // Get CaLamViec data
//                String maCaLV = rs.getString("maCaLV");
//                String tenCaLV = rs.getString("tenCaLV");
//                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
//                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
//                boolean trangThai = rs.getBoolean("trangThai");
//                
//                CaLamViec caLamViec = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
//                
//                // Get NhanVien data
//                NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
//                
//                // Get ChiTietCaLamViec data
//                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
//                double soTienBanDau = rs.getDouble("soTienBanDau");
//                String ghiChu = rs.getString("ghiChu");
//                
//                chiTiet = new ChiTietCaLamViec(caLamViec, nv, gioBatDauThucTe, soTienBanDau, ghiChu);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return chiTiet;
//    }
//
//    public ArrayList<ChiTietCaLamViec> getAllCompletedShifts() {
//        ArrayList<ChiTietCaLamViec> completedShifts = new ArrayList<>();
//        
//        try {
//            Connection con = ConnectDB.getInstance().getConnection();
//            String sql = "SELECT * FROM ChiTietCaLamViec CT " +
//                         "JOIN CaLamViec C ON CT.maCaLV = C.maCaLV " +
//                         "WHERE CT.gioKetThucThucTe IS NOT NULL " +
//                         "ORDER BY CT.gioBatDauThucTe DESC";
//            
//            PreparedStatement stmt = con.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//            
//            while (rs.next()) {
//                // Get CaLamViec data
//                String maCaLV = rs.getString("maCaLV");
//                String tenCaLV = rs.getString("tenCaLV");
//                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
//                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
//                boolean trangThai = rs.getBoolean("trangThai");
//                
//                CaLamViec caLamViec = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
//                
//                // Get NhanVien data
//                String maNV = rs.getString("maNV");
//                NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
//                
//                // Get ChiTietCaLamViec data
//                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
//                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe").toLocalDateTime();
//                double soTienBanDau = rs.getDouble("soTienBanDau");
//                double soTienCuoiCa = rs.getDouble("soTienCuoiCa");
//                String ghiChu = rs.getString("ghiChu");
//                
//                ChiTietCaLamViec chiTiet = new ChiTietCaLamViec(caLamViec, nv, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, soTienCuoiCa, ghiChu);
//                completedShifts.add(chiTiet);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return completedShifts;
//    }
//    
//    public boolean updateChiTietCaLamViecEndShift(String maCaLV, String maNV, LocalDateTime endTime, double finalAmount, String note) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "UPDATE chiTietCaLamViec SET gioKetThucThucTe = ?, soTienCuoiCa = ?, ghiChu = ? " +
//                         "WHERE maCaLV = ? AND maNV = ? AND gioKetThucThucTe IS NULL";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
//            stmt.setDouble(2, finalAmount);
//            stmt.setString(3, note);
//            stmt.setString(4, maCaLV);
//            stmt.setString(5, maNV);
//            int rows = stmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    public ChiTietCaLamViec getActiveShiftByMaCaLV(String maCaLV) {
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String sql = "SELECT ct.*, c.*, n.* " +
//                        "FROM chiTietCaLamViec ct " +
//                        "JOIN CaLamViec c ON ct.maCaLV = c.maCaLV " +
//                        "JOIN NhanVien n ON ct.maNV = n.maNV " +
//                        "WHERE ct.maCaLV = ? AND ct.gioKetThucThucTe IS NULL";
//            
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, maCaLV);
//            ResultSet rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                CaLamViec caLamViec = new CaLamViec(
//                    rs.getString("maCaLV"),
//                    rs.getString("tenCaLV"),
//                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
//                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
//                    rs.getBoolean("trangThai")
//                );
//
//                NhanVien nhanVien = new NhanVien(
//                    rs.getString("maNV"),
//                    rs.getString("tenNV"),
//                    rs.getString("gioiTinh"),
//                    rs.getDate("ngaySinh").toLocalDate(),
//                    rs.getString("sdtNV"),
//                    rs.getString("emailNV"),
//                    rs.getString("diaChi"),
//                    rs.getString("chucVu"),
//                    rs.getBoolean("trangThaiLV")
//                );
//
//                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
//                double soTienBanDau = rs.getDouble("soTienBanDau");
//                String ghiChu = rs.getString("ghiChu");
//
//                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, soTienBanDau, ghiChu);
//            }
//            return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
//    public double getPreviousShiftFinalAmount(String maNV, String currentMaCaLV) {
//        double finalAmount = 0.0;
//        try {
//            Connection conn = ConnectDB.getInstance().getConnection();
//            String previousMaCaLV;
//            
//            if ("CA02".equals(currentMaCaLV)) {
//                previousMaCaLV = "CA01";
//            } else if ("CA03".equals(currentMaCaLV)) {
//                previousMaCaLV = "CA02";
//            } else {
//                return 0.0;
//            }
//
//            String sql = "SELECT TOP 1 soTienCuoiCa FROM ChiTietCaLamViec " +
//                        "WHERE maNV = ? AND maCaLV = ? AND gioKetThucThucTe IS NOT NULL " +
//                        "AND CAST(gioKetThucThucTe AS DATE) = CAST(GETDATE() AS DATE) " +
//                        "ORDER BY gioKetThucThucTe DESC";
//            
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, maNV);
//            stmt.setString(2, previousMaCaLV);
//            ResultSet rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                finalAmount = rs.getDouble("soTienCuoiCa");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return finalAmount;
//    }
//    
//}


package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaLamViec;
import entity.ChiTietCaLamViec;
import entity.NhanVien;

public class ChiTietCaLamViec_DAO {
    private NhanVien_DAO nhanVienDAO;
    private CaLamViec_DAO caLamViecDAO;

    public ChiTietCaLamViec_DAO() {
        this.nhanVienDAO = new NhanVien_DAO();
        this.caLamViecDAO = new CaLamViec_DAO();
    }

    public boolean insertChiTietCaLamViec(ChiTietCaLamViec chiTiet) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "INSERT INTO chiTietCaLamViec (maCaLV, maNV, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chiTiet.getCaLamViec().getMaCaLV());
            stmt.setString(2, chiTiet.getNhanVien().getMaNV());
            stmt.setTimestamp(3, Timestamp.valueOf(chiTiet.getGioBatDauThucTe()));
            if (chiTiet.getGioKetThucThucTe() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(chiTiet.getGioKetThucThucTe()));
            } else {
                stmt.setNull(4, java.sql.Types.TIMESTAMP);
            }
            stmt.setDouble(5, chiTiet.getSoTienBanDau());
            stmt.setString(6, chiTiet.getGhiChu());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateChiTietCaLamViecEndTime(String maCaLV, String maNV, LocalDateTime endTime, String additionalNote) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "UPDATE chiTietCaLamViec SET gioKetThucThucTe = ?, ghiChu = CONCAT(ghiChu, ' | Kết ca: ', ?) WHERE maCaLV = ? AND maNV = ? AND gioKetThucThucTe IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
            stmt.setString(2, additionalNote);
            stmt.setString(3, maCaLV);
            stmt.setString(4, maNV);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ChiTietCaLamViec getChiTietCaLamViecById(String maCaLV) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM chiTietCaLamViec WHERE maCaLV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CaLamViec caLamViec = caLamViecDAO.getCaLamViecById(rs.getString("maCaLV"));
                NhanVien nhanVien = nhanVienDAO.getNhanVienById(rs.getString("maNV"));
                
                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe") != null ? 
                    rs.getTimestamp("gioKetThucThucTe").toLocalDateTime() : null;
                double soTienBanDau = rs.getDouble("soTienBanDau");
                String ghiChu = rs.getString("ghiChu");
                
                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ChiTietCaLamViec getChiTietCaLamViec(String maCaLV, String maNV) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT ct.*, c.*, n.* " +
                        "FROM chiTietCaLamViec ct " +
                        "JOIN CaLamViec c ON ct.maCaLV = c.maCaLV " +
                        "JOIN NhanVien n ON ct.maNV = n.maNV " +
                        "WHERE ct.maCaLV = ? AND ct.maNV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            stmt.setString(2, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CaLamViec caLamViec = new CaLamViec(
                    rs.getString("maCaLV"),
                    rs.getString("tenCaLV"),
                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
                    rs.getBoolean("trangThai")
                );

                NhanVien nhanVien = new NhanVien(
                    rs.getString("maNV"),
                    rs.getString("tenNV"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("sdtNV"),
                    rs.getString("emailNV"),
                    rs.getString("diaChi"),
                    rs.getString("chucVu"),
                    rs.getBoolean("trangThaiLV")
                );

                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe") != null ? 
                    rs.getTimestamp("gioKetThucThucTe").toLocalDateTime() : null;
                double soTienBanDau = rs.getDouble("soTienBanDau");
                String ghiChu = rs.getString("ghiChu");

                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, ghiChu);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ChiTietCaLamViec getActiveShiftForEmployee(String maNV) {
        ChiTietCaLamViec chiTiet = null;
        
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM ChiTietCaLamViec CT " +
                        "JOIN CaLamViec C ON CT.maCaLV = C.maCaLV " +
                        "WHERE CT.maNV = ? AND C.trangThai = 1 AND CT.gioKetThucThucTe IS NULL " +
                        "ORDER BY CT.gioBatDauThucTe DESC";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Get CaLamViec data
                String maCaLV = rs.getString("maCaLV");
                String tenCaLV = rs.getString("tenCaLV");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
                boolean trangThai = rs.getBoolean("trangThai");
                
                CaLamViec caLamViec = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
                
                // Get NhanVien data
                NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
                
                // Get ChiTietCaLamViec data
                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                double soTienBanDau = rs.getDouble("soTienBanDau");
                String ghiChu = rs.getString("ghiChu");
                
                chiTiet = new ChiTietCaLamViec(caLamViec, nv, gioBatDauThucTe, soTienBanDau, ghiChu);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return chiTiet;
    }

    public ArrayList<ChiTietCaLamViec> getAllCompletedShifts() {
        ArrayList<ChiTietCaLamViec> completedShifts = new ArrayList<>();
        
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM ChiTietCaLamViec CT " +
                         "JOIN CaLamViec C ON CT.maCaLV = C.maCaLV " +
                         "WHERE CT.gioKetThucThucTe IS NOT NULL " +
                         "ORDER BY CT.gioBatDauThucTe DESC";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Get CaLamViec data
                String maCaLV = rs.getString("maCaLV");
                String tenCaLV = rs.getString("tenCaLV");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();
                boolean trangThai = rs.getBoolean("trangThai");
                
                CaLamViec caLamViec = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, trangThai);
                
                // Get NhanVien data
                String maNV = rs.getString("maNV");
                NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
                
                // Get ChiTietCaLamViec data
                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe").toLocalDateTime();
                double soTienBanDau = rs.getDouble("soTienBanDau");
                double soTienCuoiCa = rs.getDouble("soTienCuoiCa");
                String ghiChu = rs.getString("ghiChu");
                
                ChiTietCaLamViec chiTiet = new ChiTietCaLamViec(caLamViec, nv, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, soTienCuoiCa, ghiChu);
                completedShifts.add(chiTiet);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return completedShifts;
    }
    
    public boolean updateChiTietCaLamViecEndShift(String maCaLV, String maNV, LocalDateTime endTime, double finalAmount, String note) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "UPDATE chiTietCaLamViec SET gioKetThucThucTe = ?, soTienCuoiCa = ?, ghiChu = ? " +
                         "WHERE maCaLV = ? AND maNV = ? AND gioKetThucThucTe IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
            stmt.setDouble(2, finalAmount);
            stmt.setString(3, note);
            stmt.setString(4, maCaLV);
            stmt.setString(5, maNV);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves the final amount from the previous completed shift for a specific employee.
     * This is useful when opening a new shift to carry over the closing amount from the last shift.
     * 
     * @param maNV The employee ID to check
     * @return The final amount from the employee's last completed shift, or 0 if no previous shift exists
     */
    public double getPreviousShiftFinalAmount(String maNV, String currentMaCaLV) {
        double finalAmount = 0.0;
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String previousMaCaLV;
            
            if ("CA02".equals(currentMaCaLV)) {
                previousMaCaLV = "CA01";
            } else if ("CA03".equals(currentMaCaLV)) {
                previousMaCaLV = "CA02";
            } else {
                return 0.0;
            }

            String sql = "SELECT TOP 1 soTienCuoiCa FROM ChiTietCaLamViec " +
                        "WHERE maNV = ? AND maCaLV = ? AND gioKetThucThucTe IS NOT NULL " +
                        "AND CAST(gioBatDauThucTe AS DATE) = CAST(GETDATE() AS DATE) " +
                        "ORDER BY gioKetThucThucTe DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNV);
            stmt.setString(2, previousMaCaLV);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                finalAmount = rs.getDouble("soTienCuoiCa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return finalAmount;
    }
    
    public ChiTietCaLamViec getActiveShiftByMaCaLV(String maCaLV) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT ct.*, c.*, n.* " +
                        "FROM chiTietCaLamViec ct " +
                        "JOIN CaLamViec c ON ct.maCaLV = c.maCaLV " +
                        "JOIN NhanVien n ON ct.maNV = n.maNV " +
                        "WHERE ct.maCaLV = ? AND ct.gioKetThucThucTe IS NULL";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                CaLamViec caLamViec = new CaLamViec(
                    rs.getString("maCaLV"),
                    rs.getString("tenCaLV"),
                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
                    rs.getBoolean("trangThai")
                );

                NhanVien nhanVien = new NhanVien(
                    rs.getString("maNV"),
                    rs.getString("tenNV"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("sdtNV"),
                    rs.getString("emailNV"),
                    rs.getString("diaChi"),
                    rs.getString("chucVu"),
                    rs.getBoolean("trangThaiLV")
                );

                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                double soTienBanDau = rs.getDouble("soTienBanDau");
                String ghiChu = rs.getString("ghiChu");

                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, soTienBanDau, ghiChu);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ChiTietCaLamViec getShiftByMaCaLVAndDate(String maCaLV, LocalDate date) {
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT ct.*, c.*, n.* " +
                        "FROM chiTietCaLamViec ct " +
                        "JOIN CaLamViec c ON ct.maCaLV = c.maCaLV " +
                        "JOIN NhanVien n ON ct.maNV = n.maNV " +
                        "WHERE ct.maCaLV = ? AND CAST(ct.gioBatDauThucTe AS DATE) = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maCaLV);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                CaLamViec caLamViec = new CaLamViec(
                    rs.getString("maCaLV"),
                    rs.getString("tenCaLV"),
                    rs.getTimestamp("thoiGianBatDau").toLocalDateTime(),
                    rs.getTimestamp("thoiGianKetThuc").toLocalDateTime(),
                    rs.getBoolean("trangThai")
                );

                NhanVien nhanVien = new NhanVien(
                    rs.getString("maNV"),
                    rs.getString("tenNV"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("sdtNV"),
                    rs.getString("emailNV"),
                    rs.getString("diaChi"),
                    rs.getString("chucVu"),
                    rs.getBoolean("trangThaiLV")
                );

                LocalDateTime gioBatDauThucTe = rs.getTimestamp("gioBatDauThucTe").toLocalDateTime();
                LocalDateTime gioKetThucThucTe = rs.getTimestamp("gioKetThucThucTe") != null ? 
                    rs.getTimestamp("gioKetThucThucTe").toLocalDateTime() : null;
                double soTienBanDau = rs.getDouble("soTienBanDau");
                double soTienCuoiCa = rs.getDouble("soTienCuoiCa");
                String ghiChu = rs.getString("ghiChu");

                return new ChiTietCaLamViec(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, soTienCuoiCa, ghiChu);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

}

