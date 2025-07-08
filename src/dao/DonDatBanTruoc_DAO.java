
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ban;
import entity.DonDatBanTruoc;
import entity.KhachHang;
import entity.NhanVien;

public class DonDatBanTruoc_DAO {
	ArrayList<DonDatBanTruoc> dsDDBT;

	public DonDatBanTruoc_DAO() {
		// TODO Auto-generated constructor stub
		dsDDBT = new ArrayList<DonDatBanTruoc>();
	}

	public ArrayList<DonDatBanTruoc> getAllDonDatBanTruoc() {
		ArrayList<DonDatBanTruoc> dsDDBTAll = new ArrayList<>();
		Ban_DAO banDao = new Ban_DAO();
		KhachHang_DAO khDao = new KhachHang_DAO();
		NhanVien_DAO nvDao = new NhanVien_DAO();
		
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM DonDatBanTruoc");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maDDBT = rs.getString("maDonDatBanTruoc");
				Timestamp timestamp = rs.getTimestamp("thoiGianTao");
				LocalDateTime thoiGianT = timestamp.toLocalDateTime();
				int soLuongKhach = rs.getInt("soLuongKhach");
				Timestamp timestamp1 = rs.getTimestamp("thoiGianNhanBan");
				LocalDateTime thoiGianN = timestamp1.toLocalDateTime();
				double tienCoc = rs.getDouble("tienCoc");
				String maKH = rs.getString("maKH");
				String maNV = rs.getString("maNV");
				String maBan = rs.getString("maBan");
				Ban ban = banDao.getBan(maBan);
				KhachHang kh = khDao.getKhachHangById(maKH);
				NhanVien nv = nvDao.getNhanVienById(maNV);
				int trangThai = rs.getInt("trangThai"); 
	            
	            DonDatBanTruoc ddbt = new DonDatBanTruoc(maDDBT, thoiGianT, soLuongKhach, thoiGianN, tienCoc, kh, nv, ban, trangThai);
	            dsDDBTAll.add(ddbt); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsDDBTAll;
	}

	public ArrayList<DonDatBanTruoc> getAllDonDatBanTruoc1() {
	    dsDDBT.clear();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM DonDatBanTruoc");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maDDBT = rs.getString("maDonDatBanTruoc");
	            Timestamp timestamp = rs.getTimestamp("thoiGianTao");
	            LocalDateTime thoiGianT = timestamp.toLocalDateTime();
	            int soLuongKhach = rs.getInt("soLuongKhach");
	            Timestamp timestamp1 = rs.getTimestamp("thoiGianNhanBan");
	            LocalDateTime thoiGianN = timestamp1.toLocalDateTime();
	            double tienCoc = rs.getDouble("tienCoc");
	            // Use getString only if the column exists
	            int trangThai = rs.getInt("trangThai");
	            String maKH = rs.getString("maKH");
	            String maNV = rs.getString("maNV");
	            String maBan = rs.getString("maBan");

	            // Get complete customer and employee objects instead of just names
	            KhachHang_DAO khDAO = new KhachHang_DAO();
	            NhanVien_DAO nvDAO = new NhanVien_DAO();

	            // Get the complete KhachHang object with all fields
	            KhachHang kh = khDAO.getKhachHangById(maKH);
	            if (kh == null) {
	                // Fallback if getKhachHangById fails
	                String tenKH = khDAO.getTenKhachHangByMaKH(maKH);
	                kh = new KhachHang(maKH, tenKH);
	            }

	            // Get the employee info
	            String tenNV = nvDAO.getTenNhanVienByMaNV(maNV);
	            NhanVien nv = new NhanVien(maNV, tenNV);
	            Ban ban = new Ban(maBan);

	            DonDatBanTruoc ddbt = new DonDatBanTruoc(maDDBT, thoiGianT, soLuongKhach, thoiGianN, tienCoc,
	                    kh, nv, ban, trangThai);
	            dsDDBT.add(ddbt);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsDDBT;
	}

	
	public boolean insertDonDatBanTruoc(DonDatBanTruoc ddbt) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO DonDatBanTruoc VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, ddbt.getMaDonDatBanTruoc());
			Timestamp timestamp = Timestamp.valueOf(ddbt.getThoiGianTao());
			stmt.setTimestamp(2, timestamp);
			stmt.setInt(3, ddbt.getSoLuongKhach());
			Timestamp timestamp1 = Timestamp.valueOf(ddbt.getThoiGianNhanBan());
			stmt.setTimestamp(4, timestamp1);
			stmt.setDouble(5, ddbt.getTienCoc());
			stmt.setString(6, ddbt.getKH().getMaKH());
			stmt.setString(7, ddbt.getNV().getMaNV());
			stmt.setString(8,  ddbt.getBan().getMaBan());
			stmt.setInt(9, ddbt.getTrangThai());

			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	 public boolean updateTrangThai(String maDonDatBan, int trangThai) {
	        int n = 0;
	        try {
	            Connection con = ConnectDB.getInstance().getConnection();
	            PreparedStatement stmt = con.prepareStatement(
	                "UPDATE DonDatBanTruoc SET trangThai = ? WHERE maDonDatBanTruoc = ?");
	            stmt.setInt(1, trangThai);
	            stmt.setString(2, maDonDatBan);
	            n = stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return n > 0;
	    }
	public boolean deleteDonDatBanTruoc(String maDonDatBanTruoc) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM DonDatBanTruoc WHERE maDonDatBanTruoc=?");
			stmt.setString(1, maDonDatBanTruoc);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean updateDonDatBanTruoc(DonDatBanTruoc ddbt) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE DonDatBanTruoc SET thoiGianTao=?, soLuongKhach=?, thoiGianNhanBan=?, tienCoc=?, maKH=?, maNV=?, maBan=?, trangThai=? WHERE maDonDatBanTruoc=?");
			Timestamp timestamp = Timestamp.valueOf(ddbt.getThoiGianTao());
			stmt.setTimestamp(1, timestamp);
			stmt.setInt(2, ddbt.getSoLuongKhach());
			Timestamp timestamp1 = Timestamp.valueOf(ddbt.getThoiGianNhanBan());
			stmt.setTimestamp(3, timestamp1);
			stmt.setDouble(4, ddbt.getTienCoc());
			stmt.setString(5, ddbt.getKH().getMaKH());
			stmt.setString(6, ddbt.getNV().getMaNV());
			stmt.setString(7, ddbt.getBan().getMaBan());
			stmt.setInt(8, ddbt.getTrangThai());
			stmt.setString(9, ddbt.getMaDonDatBanTruoc());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}


	public String taoMaHoaDonTuDong() {
		try {
			// Lấy ngày hiện tại
			LocalDateTime now = LocalDateTime.now();
			String ngay = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

			// Xác định ca làm việc
			int gio = now.getHour();
			String ca;
			if (gio >= 3 && gio < 14) {
				ca = "CA01";
			} else if (gio >= 14 && gio < 18) {
				ca = "CA02";
			} else if (gio >= 18 && gio < 22) {
				ca = "CA03";
			} else {
				return null; // Không làm việc ngoài khung giờ này
			}

			// Tạo prefix để tìm số thứ tự trong ngày và ca
			String prefix = "DDB" + ngay + ca;

			// Truy vấn cơ sở dữ liệu để tìm mã hóa đơn lớn nhất
			String sql = "SELECT TOP 1 maDonDatBanTruoc FROM DonDatBanTruoc WHERE maDonDatBanTruoc LIKE ? ORDER BY maDonDatBanTruoc DESC";
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, prefix + "%");
			ResultSet rs = ps.executeQuery();

			int stt = 1;
			if (rs.next()) {
				String maCu = rs.getString("maDonDatBanTruoc");
				String so = maCu.substring(prefix.length()); // Lấy phần số thứ tự
				stt = Integer.parseInt(so) + 1;
			}

			// Ghép lại mã hóa đơn mới và trả về
			return prefix + String.format("%04d", stt);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public DonDatBanTruoc getDonDatBanTheoMa(String ma) {
	    DonDatBanTruoc ddbt = null;
	    Ban_DAO banDao = new Ban_DAO();
		KhachHang_DAO khDao = new KhachHang_DAO();
		NhanVien_DAO nvDao = new NhanVien_DAO();
		
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM DonDatBanTruoc WHERE maDonDatBanTruoc = ?");
	        stmt.setString(1, ma);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            String maDDBT = rs.getString("maDonDatBanTruoc");
	            LocalDateTime thoiGianT = rs.getTimestamp("thoiGianTao").toLocalDateTime();
	            int soLuongKhach = rs.getInt("soLuongKhach");
	            LocalDateTime thoiGianN = rs.getTimestamp("thoiGianNhanBan").toLocalDateTime();
	            double tienCoc = rs.getDouble("tienCoc");
	            String maKH = rs.getString("maKH");
	            String maNV = rs.getString("maNV");
	            String maBan = rs.getString("maBan");
	            int trangThai = rs.getInt("trangThai");

	            Ban ban = banDao.getBan(maBan);
				KhachHang kh = khDao.getKhachHangById(maKH);
				NhanVien nv = nvDao.getNhanVienById(maNV);

	            ddbt = new DonDatBanTruoc(maDDBT, thoiGianT, soLuongKhach, thoiGianN, tienCoc, kh, nv, ban, trangThai);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ddbt;
	}
	
	public ArrayList<Object[]> getChiTietMonAnByMaDon(String maDonDatBanTruoc) {
	    ArrayList<Object[]> dsChiTiet = new ArrayList<>();
	    
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT ma.tenMonAn, ma.donGia, ctdmt.soLuong, ma.donGia * ctdmt.soLuong as thanhTien, ctdmt.ghiChu " +
	                     "FROM ChiTietDatMonTruoc ctdmt " +
	                     "JOIN MonAn ma ON ctdmt.maMonAn = ma.maMonAn " +
	                     "WHERE ctdmt.maDonDatBanTruoc = ?";
	        
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maDonDatBanTruoc);
	        ResultSet rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	            Object[] chiTiet = new Object[5];
	            chiTiet[0] = rs.getString("tenMonAn");
	            chiTiet[1] = rs.getDouble("donGia");
	            chiTiet[2] = rs.getInt("soLuong");
	            chiTiet[3] = rs.getDouble("thanhTien");
	            chiTiet[4] = rs.getString("ghiChu");
	            dsChiTiet.add(chiTiet);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return dsChiTiet;
	}
	
	public ArrayList<DonDatBanTruoc> timDonDatBanTruocTheoTrangThai(int trangThai) {
		ArrayList<DonDatBanTruoc> dsDDBT = new ArrayList<DonDatBanTruoc>();
		try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT * FROM DonDatBanTruoc WHERE trangThai = ?");
	        stmt.setInt(1, trangThai);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            String maDDBT = rs.getString("maDonDatBanTruoc");
	            LocalDateTime thoiGianT = rs.getTimestamp("thoiGianTao").toLocalDateTime();
	            int soLuongKhach = rs.getInt("soLuongKhach");
	            LocalDateTime thoiGianN = rs.getTimestamp("thoiGianNhanBan").toLocalDateTime();
	            double tienCoc = rs.getDouble("tienCoc");
	            String maKH = rs.getString("maKH");
	            String maNV = rs.getString("maNV");
	            String maBan = rs.getString("maBan");

	            KhachHang kh = new KhachHang(maKH);
	            NhanVien nv = new NhanVien(maNV);
	            Ban ban = new Ban(maBan);

	            DonDatBanTruoc ddbt = new DonDatBanTruoc(maDDBT, thoiGianT, soLuongKhach, thoiGianN, tienCoc, kh, nv, ban, trangThai);
	            dsDDBT.add(ddbt);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsDDBT;
	}

	public String getMaDonDatBanTruocTrungThoiGian(String maBan, LocalDateTime thoiGianNhanBan) {
	    String dsMaDDBT = "";
	    ArrayList<DonDatBanTruoc> dsAll = getAllDonDatBanTruoc();

	    for (DonDatBanTruoc ddbt : dsAll) {
	        if (!ddbt.getBan().getMaBan().equalsIgnoreCase(maBan)) {
	            continue; // bỏ qua nếu mã bàn không trùng
	        }

	        
	        LocalDateTime thoiGianN = ddbt.getThoiGianNhanBan();
	        int sl =ddbt.getBan().getSoLuongGhe();
	        int thoiGianDuKien = 50 + (sl - 1) * 30; // Thời gian dự kiến tính theo số lượng ghế
	        
	        
	        LocalDateTime start = thoiGianN.minusMinutes(thoiGianDuKien);
	        LocalDateTime end = thoiGianN.plusMinutes(thoiGianDuKien);

	        if (thoiGianNhanBan.isAfter(start) && thoiGianNhanBan.isBefore(end)) {
	            dsMaDDBT = ddbt.getMaDonDatBanTruoc();
	        }
	    }

	    return dsMaDDBT;
	}
}

