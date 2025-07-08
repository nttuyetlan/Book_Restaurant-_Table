package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.LoaiMon;

public class KhachHang_DAO {
	ArrayList<KhachHang> dsKH;
	
	public KhachHang_DAO() {
		// TODO Auto-generated constructor stub
		dsKH = new ArrayList<KhachHang>();
	}
	
	public ArrayList<KhachHang> getAllKhachHang(){
		ArrayList<KhachHang> dsAll_KH = new ArrayList<KhachHang>();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhachHang");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maKH = rs.getString("maKH");
				String tenKH = rs.getString("tenKH");
				String sdtKH = rs.getString("sdtKH");
				String emailKH = rs.getString("emailKH");
				String ghiChu = rs.getString("ghiChuKH");
				int diemTL = rs.getInt("diemTL");
				KhachHang kh = new KhachHang(maKH, tenKH, sdtKH, emailKH, ghiChu, diemTL);
				dsAll_KH.add(kh);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsAll_KH;
	}
	
	public String taoMaKhachHangTuDong() {
	    String sql = "SELECT TOP 1 maKH FROM KhachHang WHERE maKH LIKE 'KH%' ORDER BY maKH DESC";
	    try {
	    	Connection con = ConnectDB.getInstance().getConnection();
	    	PreparedStatement ps = con.prepareStatement(sql);
	    	ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String maCu = rs.getString("maKH"); 
	            String so = maCu.substring(2);      
	            int soMoi = Integer.parseInt(so) + 1;
	            return String.format("KH%03d", soMoi); 
	        } else {
	            return "KH001";
	        }

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
	public KhachHang getKhachHangById(String maKH) {
	    KhachHang kh = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maKH);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            String tenKH = rs.getString("tenKH");
	            String sdtKH = rs.getString("sdtKH");
	            String emailKH = rs.getString("emailKH");
	            String ghiChuKH = rs.getString("ghiChuKH");
	            int diemTL = rs.getInt("diemTL");
	            kh = new KhachHang(maKH, tenKH, sdtKH, emailKH, ghiChuKH, diemTL);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return kh;
	}

	
	public boolean insertKhachHang(KhachHang kh) {
		int n = 0;
		
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO KhachHang VALUES(?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, kh.getMaKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setString(3, kh.getSdtKH());
			stmt.setString(4, kh.getEmailKH());
			stmt.setString(5, kh.getGhiChuKH());
			stmt.setInt(6, kh.getDiemTL());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteKhachHang(String maKH) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM KhachHang WHERE maKH=?");
			stmt.setString(1, maKH);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateKhachHang(KhachHang kh) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE KhachHang SET tenKH=?, sdtKH=?, emailKH=?, ghiChuKH=?, diemTL=? WHERE maKH=?");
			stmt.setString(1, kh.getTenKH());
			stmt.setString(2, kh.getSdtKH());
			stmt.setString(3, kh.getEmailKH());
			stmt.setString(4, kh.getGhiChuKH());
			stmt.setInt(5, kh.getDiemTL());
			stmt.setString(6, kh.getMaKH());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public KhachHang getKhachHangTheoSDTKhachHang(String SDT) {
	    KhachHang kh = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhachHang WHERE sdtKH=?");
	        stmt.setString(1, SDT);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	        	String maKH = rs.getString("maKH");
				String tenKH = rs.getString("tenKH");
				String sdtKH = rs.getString("sdtKH");
				String emailKH = rs.getString("emailKH");
				String ghiChu = rs.getString("ghiChuKH");
				int diemTL = rs.getInt("diemTL");
				kh = new KhachHang(maKH, tenKH, sdtKH, emailKH, ghiChu, diemTL);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return kh;
	}
	
	public boolean capNhatDiemTichLuy(String maKH, int diemMoi) {
	    Connection con = null;
	    PreparedStatement stmt = null;
	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "UPDATE KhachHang SET diemTL = ? WHERE maKH = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setInt(1, diemMoi);
	        stmt.setString(2, maKH);

	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public String getTenKhachHangByMaKH(String maKH) {
	    String tenKH = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT tenKH FROM KhachHang WHERE maKH = ?");
	        stmt.setString(1, maKH);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            tenKH = rs.getString("tenKH");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tenKH;
	}

}
