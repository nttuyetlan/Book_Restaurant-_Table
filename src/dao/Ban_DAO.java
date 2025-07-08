package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ban;
import entity.DonDatBanTruoc;
import entity.KhuVuc;

public class Ban_DAO {
	ArrayList<Ban> dsBan;
	private static DonDatBanTruoc_DAO DDBT_dao = new DonDatBanTruoc_DAO();
	public Ban_DAO() {
		// TODO Auto-generated constructor stub
		dsBan = new ArrayList<Ban>();
	}
	public ArrayList<Ban> getAllBan(){
		ArrayList<Ban> dsBanAll = new ArrayList<>();
		try {
			Connection con = ConnectDB.getInstance().getConnection();

			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ban");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maBan = rs.getString("maBan");
				int soLuongGhe = rs.getInt("soLuongGhe");
				int trangThai = rs.getInt("trangThai");
				String maKV = rs.getString("maKV");

				Ban b = new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
				dsBanAll.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsBanAll;
	}
	
	public ArrayList<Ban> getBanTheoKhuVuc(String maKV) {
	    ArrayList<Ban> dsBan = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ban WHERE maKV = ?");
	        stmt.setString(1, maKV);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            int soLuongGhe = rs.getInt("soLuongGhe");
	            int trangThai = rs.getInt("trangThai");

	            Ban b = new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
	            dsBan.add(b);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsBan;
	}
	public ArrayList<Ban> getBanNgoaiTroi() {
	    return getBanTheoKhuVuc("KV01");
	}

	public ArrayList<Ban> getBanTang1() {
	    return getBanTheoKhuVuc("KV02");
	}

	public ArrayList<Ban> getBanTang2() {
	    return getBanTheoKhuVuc("KV03");
	}

	public ArrayList<Ban> getBanSanThuong() {
	    return getBanTheoKhuVuc("KV04");
	}
	public Ban getBanById(String maBan) {
	    Ban ban = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM Ban WHERE maBan = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maBan);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int soLuongGhe = rs.getInt("soLuongGhe");
	            int trangThai = rs.getInt("trangThai");
	            String maKV = rs.getString("maKV");
	            ban = new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ban;
	}

	public ArrayList<Ban> getBanTheoKhuVucVaTrangThai(String maKV, int trangThai) {
	    ArrayList<Ban> dsBan = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM Ban WHERE maKV = ? AND trangThai = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maKV);
	        stmt.setInt(2, trangThai);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            int soLuongGhe = rs.getInt("soLuongGhe");
	            Ban b = new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
	            dsBan.add(b);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsBan;
	}

	public ArrayList<Ban> getBanTheoTrangThai(int trangThai) {
	    ArrayList<Ban> dsBan = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        if (con == null) {
	            System.err.println("Chưa kết nối đến CSDL!");
	            return dsBan;
	        }
	        String sql = "SELECT * FROM Ban WHERE trangThai = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, trangThai);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            int soLuongGhe = rs.getInt("soLuongGhe");
	            String maKV = rs.getString("maKV");

	            Ban b = new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
	            dsBan.add(b);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsBan;
	}

	public Ban getBan(String maBan) {
		Ban ban = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ban WHERE maBan=?");
			stmt.setString(1, maBan);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int soLuongGhe = rs.getInt("soLuongGhe");
				int trangThai = rs.getInt("trangThai");
				String maKV = rs.getString("maKV");
				ban= new Ban(maBan, soLuongGhe, trangThai, new KhuVuc(maKV));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return ban;
	}
	
	public Ban sp_TimTenKhuVucTuMaBan(String maBan) {
		Ban ban = null;
		KhuVuc kv = null;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("{CALL sp_TimTenKhuVucTuMaBan(?)}");
			stmt.setString(1, maBan);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maban = rs.getString("maBan");
				int soLuongGhe = rs.getInt("soLuongGhe");
				int trangThaiBan = rs.getInt("trangThaiBan");
				String maKV = rs.getString("maKV");
				String tenKV = rs.getString("tenKV");
				boolean trangThaiKV = rs.getBoolean("trangThaiKhuVuc");
				kv = new KhuVuc(maKV, tenKV, trangThaiKV);
				ban = new Ban(maban, soLuongGhe, trangThaiBan, kv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return ban;
	}
	
	public ArrayList<Ban> getBanPhuHop(List<Integer> banPhuHop,String khuVucChon) {
	    ArrayList<Ban> dsBanAll = getAllBan();
	    ArrayList<Ban> dsBanPhuHop = new ArrayList<>();
	    
	    if (khuVucChon.equals("Tất cả")) {
	    	for (Ban ban : dsBanAll) {
		        if (banPhuHop.contains(ban.getSoLuongGhe()) && ban.getTrangThai() == 1) { // Lọc bàn còn trống
		            dsBanPhuHop.add(ban);
		        }
		    }
	    }else
	    {
	    	for (Ban ban : dsBanAll) {
		        if (banPhuHop.contains(ban.getSoLuongGhe()) && ban.getTrangThai() == 1 && ban.getKV().getMaKV().equals(khuVucChon)) { 
		        	dsBanPhuHop.add(ban);
		        }
		    }
	    }

	    

	    return dsBanPhuHop;
	}

	
	public boolean insertBan(Ban b) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO Ban VALUES(?, ?, ?, ?)");
			stmt.setString(1, b.getMaBan());
			stmt.setInt(2, b.getSoLuongGhe());
			stmt.setInt(3, b.getTrangThai());
			stmt.setString(4, b.getKV().getMaKV());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteBan(String maBan) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM Ban WHERE maBan=?");
			stmt.setString(1, maBan);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateBan(Ban b) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE Ban SET soLuongGhe=?, trangThai=?, maKV=? WHERE maBan=?");
			stmt.setInt(1, b.getSoLuongGhe());
			stmt.setInt(2, b.getTrangThai());
			stmt.setString(3, b.getKV().getMaKV());
			stmt.setString(4, b.getMaBan());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean capNhatTrangThaiBan(String maBan, int trangThaiMoi) {
	    String sql = "UPDATE Ban SET trangThai = ? WHERE maBan = ?";
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, trangThaiMoi);
	        ps.setString(2, maBan);

	        int rowsUpdated = ps.executeUpdate(); 
	        return rowsUpdated > 0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static ArrayList<Ban> getBanPhuHopChoChuyenBan(int sucChua) {
	    ArrayList<Ban> danhSachBanPhuHop = new ArrayList<>();
	    try{
	    	Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM Ban WHERE TrangThai = 1 AND SoLuongGhe >= ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, sucChua);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maBan = rs.getString("MaBan");
	            int soLuongGhe = rs.getInt("SoLuongGhe");
	            int trangThai = rs.getInt("TrangThai");
	            String maKV = rs.getString("maKV");
	            KhuVuc kv = new KhuVuc(maKV);
	            Ban ban = new Ban(maBan, soLuongGhe, trangThai, kv);
	            danhSachBanPhuHop.add(ban);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return danhSachBanPhuHop;
	}
	
	public String generateMaBan(String maKV, int fallbackCount) {
	    try {
	        // Kết nối cơ sở dữ liệu
	        Connection con = ConnectDB.getInstance().getConnection();

	        // Kiểm tra maKV hợp lệ (chỉ chấp nhận KV01, KV02, KV03, KV04)
	        if (!maKV.matches("KV[0-3][0-9]") || Integer.parseInt(maKV.substring(2)) > 4) {
	            throw new IllegalArgumentException("Mã khu vực không hợp lệ. Chỉ chấp nhận KV01 đến KV04.");
	        }

	        // Lấy xx (phần đầu) từ maKV, ví dụ: "KV01" -> "01"
	        String khuVucPart = maKV.substring(2);

	        // Đếm số bàn hiện có trong khu vực này
	        String countQuery = "SELECT COUNT(*) as count FROM Ban WHERE maBan LIKE ?";
	        PreparedStatement countStmt = con.prepareStatement(countQuery);
	        countStmt.setString(1, "KV" + khuVucPart + "B%");
	        ResultSet countRs = countStmt.executeQuery();
	        int countInKhuVuc = 0;
	        if (countRs.next()) {
	            countInKhuVuc = countRs.getInt("count");
	        }

	        // Tăng số thứ tự bàn trong khu vực lên 1 và định dạng thành 2 chữ số
	        int nextBanId = countInKhuVuc + 1;
	        String banIdStr = String.format("%02d", nextBanId);

	        // Tạo mã bàn theo định dạng KVxxBxx
	        return "KV" + khuVucPart + "B" + banIdStr;
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Nếu lỗi, trả về mã mặc định dựa trên fallbackCount
	        String khuVucPart = maKV.substring(2);
	        return "KV" + khuVucPart + "B" + String.format("%02d", fallbackCount);
	    }
	}
	public ArrayList<Ban> getBanDaBiTrungThoiGian(LocalDateTime thoiGianNhanBan) {
		ArrayList<Ban> dsBanTrung = new ArrayList<>();
		ArrayList<DonDatBanTruoc> dsAll = DDBT_dao.getAllDonDatBanTruoc();

		
		for (DonDatBanTruoc ddbt : dsAll) {
	        LocalDateTime thoiGianN = ddbt.getThoiGianNhanBan();
	        
	        
	        int sl =ddbt.getBan().getSoLuongGhe();
	        int thoiGianDuKien = 40 + (sl - 1) * 30; 
	        
	        
	        LocalDateTime start = thoiGianN.minusMinutes(thoiGianDuKien);
	        LocalDateTime end = thoiGianN.plusMinutes(thoiGianDuKien);
	        
	        if (thoiGianNhanBan.isAfter(start) && thoiGianNhanBan.isBefore(end)&& ddbt.getTrangThai() == 0) {
	            dsBanTrung.add(ddbt.getBan());
	        }
	    }

	    return dsBanTrung;
	}
	

	
	public ArrayList<Ban> locBanTheoTrangThai (ArrayList<Ban> dsBan, int trangThaiMongMuon) {
	    ArrayList<Ban> ketQua = new ArrayList<>();
	    for (Ban ban : dsBan) {
	        if (ban.getTrangThai() == trangThaiMongMuon) {
	            ketQua.add(ban);
	        }
	    }
	    return ketQua;
	}

}
