package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhuyenMai;

public class KhuyenMai_DAO {
	ArrayList<KhuyenMai> dsKM;
	
	public KhuyenMai_DAO() {
		// TODO Auto-generated constructor stub
		dsKM = new ArrayList<KhuyenMai>();
	}
	public ArrayList<KhuyenMai> getAllKhuyenMai(){
		dsKM.clear();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhuyenMai");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maKM = rs.getString("maKM");
				String tenKM = rs.getString("tenKM");
				String noidungKM = rs.getString("noiDungKM");
				double giaTriKM = rs.getDouble("giaTriKM");
				int soLuong = rs.getInt("soLuong");
				LocalDate ngayBatDauKM = rs.getDate("ngayBatDauKM").toLocalDate();
				LocalDate ngayKetThucKM = rs.getDate("ngayKetThucKM").toLocalDate();
				KhuyenMai km = new KhuyenMai(maKM, tenKM, noidungKM, giaTriKM, soLuong, ngayBatDauKM, ngayKetThucKM);
				dsKM.add(km);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsKM;
	}
	
	public String taoMaKhuyenMaiTuDong() {
	    String namHienTai = String.valueOf(LocalDate.now().getYear());
	    String prefix = "KM" + namHienTai;

	    int maxSTT = 0;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT maKM FROM KhuyenMai WHERE maKM LIKE ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, prefix + "%");
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maKM = rs.getString("maKM");
	            // Cắt phần số cuối: KM2025001 -> 001
	            String sttStr = maKM.substring(prefix.length());
	            try {
	                int stt = Integer.parseInt(sttStr);
	                if (stt > maxSTT) {
	                    maxSTT = stt;
	                }
	            } catch (NumberFormatException ignored) {}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Tăng số thứ tự lên 1
	    int nextSTT = maxSTT + 1;
	    return prefix + String.format("%03d", nextSTT);
	}

	
	public boolean insertKhuyenMai(KhuyenMai km) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO KhuyenMai VALUES(?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, km.getMaKM());
			stmt.setString(2, km.getTenKM());
			stmt.setString(3, km.getNoiDungKM());
			stmt.setDouble(4, km.getGiaTriKM());
			stmt.setInt(5, km.getSoLuong());
			stmt.setDate(6, Date.valueOf(km.getNgayBatDauKM()));
			stmt.setDate(7, Date.valueOf(km.getNgayKetThucKM()));
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteKhuyenMai(String maKM) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM KhuyenMai WHERE maKM=?");
			stmt.setString(1, maKM);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateKhuyenMai(KhuyenMai km) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE KhuyenMai SET tenKM=?, noiDungKM=?, giaTriKM=?, soLuong=?, ngayBatDauKM=?, ngayKetThucKM=? WHERE maKM=?");
			stmt.setString(1, km.getTenKM());
			stmt.setString(2, km.getNoiDungKM());
			stmt.setDouble(3, km.getGiaTriKM());
			stmt.setInt(4, km.getSoLuong());
			stmt.setDate(5, Date.valueOf(km.getNgayBatDauKM()));
			stmt.setDate(6, Date.valueOf(km.getNgayKetThucKM()));
			stmt.setString(7, km.getMaKM());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public KhuyenMai timKhuyenMai(String maKM) {
	    KhuyenMai km = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	    	Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM KhuyenMai WHERE maKM = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, maKM);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            km = new KhuyenMai(
	                rs.getString("maKM"),
	                rs.getString("tenKM"),
	                rs.getString("noiDungKM"),
	                rs.getDouble("giaTriKM"),
	                rs.getInt("soLuong"),
	                rs.getDate("ngayBatDauKM").toLocalDate(),
	                rs.getDate("ngayKetThucKM").toLocalDate()
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return km;
	}

	
	public KhuyenMai timKhuyenMaiTotNhat() {
	    LocalDate today = LocalDate.now();
	    ArrayList<KhuyenMai> dsKM = getAllKhuyenMai(); // Lấy toàn bộ khuyến mãi từ DB

	    KhuyenMai kmTotNhat = null;
	    double maxGiaTri = 0;

	    for (KhuyenMai km : dsKM) {
	        if ((km.getNgayBatDauKM().isBefore(today) || km.getNgayBatDauKM().isEqual(today)) &&
	            (km.getNgayKetThucKM().isAfter(today) || km.getNgayKetThucKM().isEqual(today)) &&
	            km.getGiaTriKM() > maxGiaTri) {

	            maxGiaTri = km.getGiaTriKM();
	            kmTotNhat = km;
	        }
	    }

	    return kmTotNhat;
	}

	
}
