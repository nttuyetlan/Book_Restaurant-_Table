package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiMon;
import entity.MonAn;

public class MonAn_DAO {
	ArrayList<MonAn> dsMA;
	
	public MonAn_DAO() {
		// TODO Auto-generated constructor stub
		dsMA = new ArrayList<MonAn>();
	}
	
	public ArrayList<MonAn> getAllMonAn(){
		dsMA = new ArrayList<MonAn>();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM MonAn");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maMA = rs.getString("maMonAn");
				String tenMA = rs.getString("tenMonAn");
				String moTa = rs.getString("moTa");
				double donGia = rs.getDouble("donGia");
				byte[] data = rs.getBytes("hinhMonAn");
				LocalDateTime ngayCN = rs.getObject("ngayCapNhat", LocalDateTime.class);
				double thueMon = rs.getDouble("thueMon");
				String maloai = rs.getString("maLoai");
				int trangThai = rs.getInt("trangThai");
				MonAn mon = new MonAn(maMA, tenMA, moTa, donGia, data, ngayCN, thueMon, new LoaiMon(maloai), trangThai);
				dsMA.add(mon);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsMA;
	}
	
//	public boolean insertMA(MonAn mon) {
//		if (isMaMonAnExists(mon.getMaMonAn())) {
//	        System.err.println("Mã món ăn đã tồn tại: " + mon.getMaMonAn());
//	        return false;
//	    }
//		int n = 0;
//		try {
//			Connection con = ConnectDB.getInstance().getConnection();
//	        // Kiểm tra nếu kết nối đã bị đóng thì mở lại
//	        if (con == null || con.isClosed()) {
//	            ConnectDB.getInstance().connect();
//	            con = ConnectDB.getInstance().getConnection();
//	        }
//			PreparedStatement stmt = con.prepareStatement("INSERT INTO MonAn VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
//			stmt.setString(1, mon.getMaMonAn());
//			stmt.setString(2, mon.getTenMonAn());
//			stmt.setString(3, mon.getMoTa());
//			stmt.setDouble(4, mon.getDonGia());
//			stmt.setBytes(5, mon.getHinhMonAn());
//			Timestamp timestamp = Timestamp.valueOf(mon.getNgayCapNhat());
//			stmt.setTimestamp(6, timestamp);
//			stmt.setDouble(7, mon.getThueMon());
//			stmt.setString(8, mon.getLoai().getMaLoai());
//			
//			n = stmt.executeUpdate();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//	
	
	public boolean insertMA(MonAn mon) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        if (con == null || con.isClosed()) {
	            ConnectDB.getInstance().connect();
	            con = ConnectDB.getInstance().getConnection();
	        }
	        String sql = "INSERT INTO MonAn (maMonAn, tenMonAn, moTa, donGia, hinhMonAn, ngayCapNhat, thueMon, maLoai, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, mon.getMaMonAn());
	        stmt.setString(2, mon.getTenMonAn());
	        stmt.setString(3, mon.getMoTa());
	        stmt.setDouble(4, mon.getDonGia());
	        stmt.setBytes(5, mon.getHinhMonAn());
	        stmt.setTimestamp(6, Timestamp.valueOf(mon.getNgayCapNhat()));
	        stmt.setDouble(7, mon.getThueMon());
	        stmt.setString(8, mon.getLoai().getMaLoai());
	        stmt.setInt(9, mon.getTrangThai()); // Add the missing column
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}

	
	private boolean isMaMonAnExists(String maMonAn) {
	    try {
	    	Connection con = ConnectDB.getInstance().getConnection();
	    	// Kiểm tra nếu kết nối đã bị đóng thì mở lại
	        if (con == null || con.isClosed()) {
	            ConnectDB.getInstance().connect();
	            con = ConnectDB.getInstance().getConnection();
	        }
	    		PreparedStatement stmt = con.prepareStatement(
	             "SELECT 1 FROM MonAn WHERE maMonAn = ?");
	         
	        stmt.setString(1, maMonAn);
	        return stmt.executeQuery().next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean deleteMonAn(String maMonAn) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM MonAn WHERE maMonAn=?");
			stmt.setString(1, maMonAn);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateMonAn(MonAn mon) {
	    int n = 0;
	    try {
			Connection con = ConnectDB.getInstance().getConnection();
	        // Kiểm tra nếu kết nối đã bị đóng thì mở lại
	        if (con == null || con.isClosed()) {
	            ConnectDB.getInstance().connect();
	            con = ConnectDB.getInstance().getConnection();
	        }
	        
	    	PreparedStatement stmt = con.prepareStatement("UPDATE MonAn SET tenMonAn=?, moTa=?, donGia=?, hinhMonAn=?, ngayCapNhat=?, thueMon=?, maLoai=? WHERE maMonAn=?");
	        
	        stmt.setString(1, mon.getTenMonAn());
	        stmt.setString(2, mon.getMoTa());
	        stmt.setDouble(3, mon.getDonGia());
	        stmt.setBytes(4, mon.getHinhMonAn());
	        stmt.setTimestamp(5, Timestamp.valueOf(mon.getNgayCapNhat()));
	        stmt.setDouble(6, mon.getThueMon());
	        stmt.setString(7, mon.getLoai().getMaLoai()); // Sử dụng getMaLoai() thay vì đối tượng LoaiMon
	        stmt.setString(8, mon.getMaMonAn());
	        
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    return n > 0;
	}
	
	public String generateMaMonAn() {
	    String prefix = "MA";
	    String newMaMonAn;
	    
	    try (Connection con = ConnectDB.getInstance().getConnection();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT MAX(CAST(SUBSTRING(maMonAn, 3, LEN(maMonAn)-2) AS INT)) FROM MonAn WHERE maMonAn LIKE 'MA%'")) {
	        
	        if (rs.next() && rs.getString(1) != null) {
	            int maxNumber = rs.getInt(1);
	            newMaMonAn = String.format("%s%03d", prefix, maxNumber + 1);
	        } else {
	            newMaMonAn = prefix + "001";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Fallback: sử dụng timestamp nếu có lỗi
	        newMaMonAn = prefix + System.currentTimeMillis() % 1000;
	    }
	    
	    return newMaMonAn;
	}
	
	public String getLastMaMonAn() {
	    try (Connection conn = ConnectDB.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT TOP 1 maMonAn FROM MonAn ORDER BY maMonAn DESC")) {
	        
	        if (rs.next()) {
	            return rs.getString("maMonAn");
	        }
	        return null;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	// Lọc món ăn theo tên loại
	public ArrayList<MonAn> locMonAnTheoTenLoai_SP(String tenLoai) {
	    ArrayList<MonAn> dsLoc = new ArrayList<MonAn>();

	    try  {
	        Connection con = ConnectDB.getInstance().getConnection();
	        CallableStatement stmt = con.prepareCall("{call ma_LocMonAnTheoTenLoai(?)}");
	        stmt.setString(1, tenLoai);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maMA = rs.getString("maMonAn");
	            String tenMA = rs.getString("tenMonAn");
	            String moTa = rs.getString("moTa");
	            double donGia = rs.getDouble("donGia");
	            byte[] data = rs.getBytes("hinhMonAn");
	            LocalDateTime ngayCN = rs.getObject("ngayCapNhat", LocalDateTime.class);
	            double thueMon = rs.getDouble("thueMon");
	            String maloai = rs.getString("maLoai");
	            int trangThai = rs.getInt("trangThai");

	            MonAn mon = new MonAn(maMA, tenMA, moTa, donGia, data, ngayCN, thueMon, new LoaiMon(maloai), trangThai);
	            dsLoc.add(mon);
	        }
	    } catch (Exception e) {
	        System.err.println("Lỗi khi gọi stored procedure lọc món ăn theo tên loại:");
	        e.printStackTrace();
	    }

	    return dsLoc;
	}

	
	
	public ArrayList<MonAn> getMonAnTheoTenMon(String tenMon) {
	    ArrayList<MonAn> dsMon = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM MonAn WHERE tenMonAn LIKE ?");
	        stmt.setString(1, "%" + tenMon + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maMA = rs.getString("maMonAn");
	            String tenMA = rs.getString("tenMonAn");
	            String moTa = rs.getString("moTa");
	            double donGia = rs.getDouble("donGia");
	            byte[] data = rs.getBytes("hinhMonAn");
	            LocalDateTime ngayCN = rs.getObject("ngayCapNhat", LocalDateTime.class);
	            double thueMon = rs.getDouble("thueMon");
	            String maloai = rs.getString("maLoai");
	            int trangThai = rs.getInt("trangThai");

	            MonAn mon = new MonAn(maMA, tenMA, moTa, donGia, data, ngayCN, thueMon, new LoaiMon(maloai), trangThai);
	            dsMon.add(mon);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return dsMon;
	}
	
	public String timMaMonAnTheoTen(String tenMonAn) {
	    String sql = "SELECT maMonAn FROM MonAn WHERE tenMonAn = ?";
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, tenMonAn);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("maMonAn");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public MonAn timTenMonAnVaGiaMonAnTheoMa(String maMonAn) {
	    String sql = "SELECT * FROM MonAn WHERE maMonAn = ?";
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, maMonAn);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	String tenMonAn = rs.getString("tenMonAn");
	        	String moTa = rs.getString("moTa");
	        	double donGia = rs.getDouble("donGia");
	        	double thueMon = rs.getDouble("thueMon");
	        	String maLoai = rs.getString("maLoai");
	        	int trangThai = rs.getInt("trangThai");
	            return new MonAn(maMonAn, tenMonAn, moTa, donGia, null, null, thueMon, new LoaiMon(maLoai), trangThai);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public boolean capNhatTrangThaiMonAn(String tenMonAn, int trangThai) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE MonAn SET trangThai=? WHERE tenMonAn=?");
	        stmt.setInt(1, trangThai);
	        stmt.setString(2, tenMonAn);
	        
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public MonAn get1MonAnTheoTenMon(String tenMon) {
	    MonAn mon = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM MonAn WHERE tenMonAn = ?");
	        stmt.setString(1, tenMon);
	        ResultSet rs = stmt.executeQuery();
	
	        if (rs.next()) {
	            String maMA = rs.getString("maMonAn");
	            String tenMA = rs.getString("tenMonAn");
	            String moTa = rs.getString("moTa");
	            double donGia = rs.getDouble("donGia");
	            byte[] data = rs.getBytes("hinhMonAn");
	            LocalDateTime ngayCN = rs.getObject("ngayCapNhat", LocalDateTime.class);
	            double thueMon = rs.getDouble("thueMon");
	            String maloai = rs.getString("maLoai");
	            int trangThai = rs.getInt("trangThai");
	
	            mon = new MonAn(maMA, tenMA, moTa, donGia, data, ngayCN, thueMon, new LoaiMon(maloai), trangThai);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    return mon;
	}
	
}
