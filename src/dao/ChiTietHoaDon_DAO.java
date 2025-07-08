package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.MonAn;

public class ChiTietHoaDon_DAO {
	
	private MonAn_DAO monAn_dao = new MonAn_DAO();
	private HoaDon_DAO hd_dao = new HoaDon_DAO();
	private static final Logger logger = Logger.getLogger(HoaDon_DAO.class.getName());
	
	ArrayList<ChiTietHoaDon> dsCTHD;
	
	public ChiTietHoaDon_DAO() {
		// TODO Auto-generated constructor stub
		dsCTHD = new ArrayList<ChiTietHoaDon>();
	}
	public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon(){
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM ChiTietHoaDon");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maHD = rs.getString("maHD");
				String maMonAn = rs.getString("maMonAn");
				int soLuong = rs.getInt("soLuong");
				String ghiChu = rs.getString("ghiChu");
				ChiTietHoaDon cthd = new ChiTietHoaDon(new HoaDon(maHD), new MonAn(maMonAn), soLuong, ghiChu);
				dsCTHD.add(cthd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsCTHD;
	}
	
	public boolean insertChiTietHoaDon(ChiTietHoaDon cthd) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO ChiTietHoaDon VALUES(?, ?, ?, ?)");
			stmt.setString(1, cthd.getHD().getMaHD());
			stmt.setString(2, cthd.getMonAn().getMaMonAn());
			stmt.setInt(3, cthd.getSoLuong());
			stmt.setString(4, cthd.getGhiChu());
			
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateChiTietHoaDon(ChiTietHoaDon cthd) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "UPDATE ChiTietHoaDon SET soLuong = ?, ghiChu = ? WHERE maHD = ? AND maMonAn = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        
	        stmt.setInt(1, cthd.getSoLuong());
	        stmt.setString(2, cthd.getGhiChu());
	        stmt.setString(3, cthd.getHD().getMaHD());
	        stmt.setString(4, cthd.getMonAn().getMaMonAn());

	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public ChiTietHoaDon getChiTietTheoMaHDVaMaMon(String maHD, String maMonAn) {
	    ChiTietHoaDon cthd = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD = ? AND maMonAn = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        stmt.setString(2, maMonAn);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            HoaDon hd = hd_dao.timHoaDonTheoMaHoaDon(maHD);
	            MonAn monAn = monAn_dao.timTenMonAnVaGiaMonAnTheoMa(maMonAn);
	            int soLuong = rs.getInt("soLuong");
	            String ghiChu = rs.getString("ghiChu");

	            cthd = new ChiTietHoaDon(hd, monAn, soLuong, ghiChu);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return cthd;
	}

	
	public ArrayList<ChiTietHoaDon> getChiTietHoaDonTheoMaHD(String maHD) {
	    ArrayList<ChiTietHoaDon> danhSach = new ArrayList<>();
	    String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD = ?";

	    try {
	    	Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maHoaDon  = rs.getString("maHD");
	            String maMonAn =  rs.getString("maMonAn");
	            int soLuong = rs.getInt("soLuong");
	            String ghiChu = rs.getString("ghiChu");
	            MonAn monAn = monAn_dao.timTenMonAnVaGiaMonAnTheoMa(maMonAn);
	            
	            ChiTietHoaDon ct = new ChiTietHoaDon(new HoaDon(maHD), monAn, soLuong, ghiChu);
	            danhSach.add(ct);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return danhSach;
	}

	public boolean deleteChiTietHoaDonTheoMaHD(String maHD) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("DELETE FROM ChiTietHoaDon WHERE maHD = ?");
	        stmt.setString(1, maHD);

	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public boolean deleteChiTietTheoMaHDVaMaMon(String maHD, String maMonAn) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "DELETE FROM ChiTietHoaDon WHERE maHD = ? AND maMonAn = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        stmt.setString(2, maMonAn);

	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public boolean xoaChiTietHoaDonTheoMaHD(String maHD) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("DELETE FROM ChiTietHoaDon WHERE maHD = ?");
	        stmt.setString(1, maHD);
	        n = stmt.executeUpdate(); // Trả về số dòng bị xóa
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public  ArrayList<ChiTietHoaDon> layDanhSachChiTietTheoMaHD(String maHD) {
	    ArrayList<ChiTietHoaDon> danhSach = new ArrayList<>();
	    String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD = ?";

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maMonAn = rs.getString("maMonAn");
	            int soLuong = rs.getInt("soLuong");
	            String ghiChu = rs.getString("ghiChu");

	            MonAn monAn = monAn_dao.timTenMonAnVaGiaMonAnTheoMa(maMonAn);
	            HoaDon hoaDon = hd_dao.timHoaDonTheoMaHD_DDBT(maHD);

	            ChiTietHoaDon cthd = new ChiTietHoaDon(hoaDon, monAn, soLuong, ghiChu);
	            danhSach.add(cthd);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return danhSach;
	}
	
	public boolean updateMaHD(String oldMaHD, String newMaHD) {
        int n = 0;
        try (Connection con = ConnectDB.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement("UPDATE ChiTietHoaDon SET maHD = ? WHERE maHD = ?")) {
            stmt.setString(1, newMaHD);
            stmt.setString(2, oldMaHD);
            n = stmt.executeUpdate();
            if (n > 0) {
                logger.info("Cập nhật mã hóa đơn trong ChiTietHoaDon: " + oldMaHD + " thành " + newMaHD);
            } else {
                logger.warning("Không tìm thấy chi tiết hóa đơn nào với maHD: " + oldMaHD);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật mã hóa đơn từ " + oldMaHD + " thành " + newMaHD, e);
        }
        return n > 0;
    }
}
