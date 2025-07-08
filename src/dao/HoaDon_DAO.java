package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.data.category.DefaultCategoryDataset;

import connectDB.ConnectDB;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;

public class HoaDon_DAO {
	ArrayList<HoaDon> dsHD;
	private static ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private static Ban_DAO ban_dao = new Ban_DAO();
	private static DonDatBanTruoc_DAO ddbt_dao = new DonDatBanTruoc_DAO();
	private static KhachHang_DAO kh_dao = new KhachHang_DAO();
	private static NhanVien_DAO nv_dao = new NhanVien_DAO();
	private static KhuyenMai_DAO km_dao = new KhuyenMai_DAO();
	private static final Logger logger = Logger.getLogger(HoaDon_DAO.class.getName());
	
	public HoaDon_DAO() {
		// TODO Auto-generated constructor stub
		dsHD = new ArrayList<HoaDon>();
	}

	public ArrayList<HoaDon> getAllHoaDon() {
	ArrayList<HoaDon> dsHD = new ArrayList<>();
	try {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM HoaDon");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			String maHD = rs.getString("maHD");

			// thoiGianTaoDon - luôn có DEFAULT GETDATE()
			Timestamp timestamp = rs.getTimestamp("thoiGianTaoDon");
			LocalDateTime thoiGianTD = timestamp != null ? timestamp.toLocalDateTime() : null;

			double tienKHDua = rs.getDouble("tienKHDua");
			double phuThu = rs.getDouble("phuThu");

			String hinhThucTT = rs.getString("hinhThucThanhToan");

			String maBan = rs.getString("maBan");
			Ban b = maBan != null ? new Ban(maBan) : null;

			String maDonDatBanTruoc = rs.getString("maDonDatBanTruoc");
			DonDatBanTruoc ddbt = maDonDatBanTruoc != null ? new DonDatBanTruoc(maDonDatBanTruoc) : null;

			String maKH = rs.getString("maKH");
			KhachHang kh = maKH != null ? new KhachHang(maKH) : null;
			
			int soKhach = rs.getInt("soKhach");

			String maNV = rs.getString("maNV");
			NhanVien nv = maNV != null ? new NhanVien(maNV) : null;

			String maKM = rs.getString("maKM");
			KhuyenMai km = maKM != null ? new KhuyenMai(maKM) : null;

			Timestamp timestamp1 = rs.getTimestamp("thoiGianThanhToan");
			LocalDateTime thoiGianTT = timestamp1 != null ? timestamp1.toLocalDateTime() : null;

			int trangThaiHD = rs.getInt("trangThai");
			
			String chuThich = rs.getString("chuThich");
			
			int diemTLSuDung = rs.getInt("diemTLSuDung");
			
			double tongTien = rs.getDouble("tongThanhToan");
			
			ArrayList<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaHD(maHD);

			HoaDon hd = new HoaDon(maHD, thoiGianTT, tienKHDua, phuThu, hinhThucTT, b, ddbt, kh, soKhach, nv, km, thoiGianTD, trangThaiHD, chuThich, diemTLSuDung, tongTien, dsCTHD);
			dsHD.add(hd);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return dsHD;
}

	
	public boolean insertHoaDon(HoaDon hd) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO HoaDon VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, hd.getMaHD());
			Timestamp timestamp = Timestamp.valueOf(hd.getThoiGianTaoDon());
			stmt.setTimestamp(2, timestamp);
			stmt.setDouble(3, hd.getTienKHDua());
			stmt.setDouble(4, hd.getPhuThu());
			stmt.setString(5, hd.getHinhThucThanhToan());
			if (hd.getBan() == null) {
				stmt.setString(6, null);
			}else {
				stmt.setString(6, hd.getBan().getMaBan());
			}
			
			if (hd.getDonDatBanTruoc() == null) {
				stmt.setString(7, null);
			}else {
				stmt.setString(7, hd.getDonDatBanTruoc().getMaDonDatBanTruoc());
			}
			
			if (hd.getKH() == null) {
				stmt.setString(8, null);
			}else {
				stmt.setString(8, hd.getKH().getMaKH());
			}
			
			stmt.setInt(9, hd.getSoKhach());
			
			stmt.setString(10, hd.getNV().getMaNV());
			
			if (hd.getKM() == null) {
				stmt.setString(11, null);
			}else {
				stmt.setString(11, hd.getKM().getMaKM());
			}
			
			if (hd.getThoiGianThanhToan() == null) {
				stmt.setTimestamp(12, null);
			}else {
				Timestamp timestamp1 = Timestamp.valueOf(hd.getThoiGianThanhToan());
				stmt.setTimestamp(12, timestamp1);
			}
			stmt.setInt(13, hd.getTrangThai());
			stmt.setString(14, hd.getChuThich());
			stmt.setInt(15, hd.getDiemTLSuDung());
			stmt.setDouble(16, hd.getTongThanhToan());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteHoaDon(String maHD) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM HoaDon WHERE maHD=?");
			stmt.setString(1, maHD);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateHoaDon(HoaDon hd) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE HoaDon SET thoiGianTaoDon=?, tienKHDua=?, phuThu=?, hinhThucThanhToan=?, maBan=?, maDonDatBanTruoc=?, maKH=?, soKhach=?, maNV=?, maKM=?, thoiGianThanhToan=?, trangThai=?, chuThich=?, diemTLSuDung=?, tongThanhToan=? WHERE maHD=?");
			Timestamp timestamp = Timestamp.valueOf(hd.getThoiGianTaoDon());
			stmt.setTimestamp(1, timestamp);
			stmt.setDouble(2, hd.getTienKHDua());
			stmt.setDouble(3, hd.getPhuThu());
			stmt.setString(4, hd.getHinhThucThanhToan());
			stmt.setString(5, hd.getBan().getMaBan());
			if (hd.getDonDatBanTruoc() == null) {
				stmt.setString(6, null);
			}else {
				stmt.setString(6, hd.getDonDatBanTruoc().getMaDonDatBanTruoc());
			}
			
			if (hd.getKH() == null) {
				stmt.setString(7, null);
			}else {
				stmt.setString(7, hd.getKH().getMaKH());
			}
			
			stmt.setInt(8, hd.getSoKhach());
			
			stmt.setString(9, hd.getNV().getMaNV());
			
			if (hd.getKM() == null) {
				stmt.setString(10, null);
			}else {
				stmt.setString(10, hd.getKM().getMaKM());
			}
			
			if (hd.getThoiGianThanhToan() == null) {
				stmt.setTimestamp(11, null);
			}else {
				Timestamp timestamp1 = Timestamp.valueOf(hd.getThoiGianThanhToan());
				stmt.setTimestamp(11, timestamp1);
			}
			stmt.setInt(12, hd.getTrangThai());
			stmt.setString(13, hd.getChuThich());
			stmt.setInt(14, hd.getDiemTLSuDung());
			stmt.setDouble(15, hd.getTongThanhToan());
			stmt.setString(16, hd.getMaHD());
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
	            // Nếu ngoài khung giờ làm việc, bạn có thể xử lý đặc biệt hoặc trả null
	            return null;
	        }

	        // Tạo prefix để tìm số thứ tự trong ngày và ca
	        String prefix = "HD" + ngay + ca;

	        // Tìm mã lớn nhất trong ngày và ca này
	        String sql = "SELECT TOP 1 maHD FROM HoaDon WHERE maHD LIKE ? ORDER BY maHD DESC";
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, prefix + "%");
	        ResultSet rs = ps.executeQuery();

	        int stt = 1;
	        if (rs.next()) {
	            String maCu = rs.getString("maHD");
	            String so = maCu.substring(prefix.length()); // Lấy phần STT
	            stt = Integer.parseInt(so) + 1;
	        }
	        
	        System.out.println(prefix + String.format("%03d", stt));
	        return prefix + String.format("%03d", stt); // Ghép lại thành mã mới

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Không có");
	        return null;
	    }
	}

	public HoaDon timHoaDonTheoBanVaTrangThai(String maBan, int trangThai) {
	    String sql = "SELECT * FROM HoaDon WHERE maBan = ? AND trangThai = ?";

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maBan);
	        stmt.setInt(2, trangThai);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	            	 String maHD1 = rs.getString("maHD");
		                Timestamp ts = rs.getTimestamp("thoiGianTaoDon");
		                LocalDateTime thoiGianTaoDon = ts.toLocalDateTime();
		                double tienKHDua = rs.getDouble("tienKHDua");
		                double phuThu = rs.getDouble("phuThu");
		                String hinhThucTT = rs.getString("hinhThucThanhToan");
		                String maBan1 = rs.getString("maBan");
		                String maDonDatBanTruoc = rs.getString("maDonDatBanTruoc");
		                String maKH = rs.getString("maKH");
		                int soKhach = rs.getInt("soKhach");
		                String maNV = rs.getString("maNV");
		                String maKM = rs.getString("maKM");
		                Timestamp tstt = rs.getTimestamp("thoiGianThanhToan");
		                LocalDateTime thoiGianThanhToan = tstt.toLocalDateTime();
		                String chuThich = rs.getString("chuThich");
		                int trangThaiHD = rs.getInt("trangThai");
		                int diemTLSuDung = rs.getInt("diemTLSuDung");
		                double tongTien = rs.getDouble("tongThanhToan");
		                
		                ArrayList<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaHD(maHD1);
		                
		                HoaDon hoaDonInfo = new HoaDon(maHD1, thoiGianThanhToan, tienKHDua, phuThu, hinhThucTT, new Ban(maBan1), new DonDatBanTruoc(maDonDatBanTruoc), new KhachHang(maKH), soKhach, new NhanVien(maNV), new KhuyenMai(maKM), thoiGianTaoDon, trangThaiHD, chuThich, diemTLSuDung, tongTien, dsCTHD);
	                return hoaDonInfo;
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return null;
	}
	
	public ArrayList<HoaDon> timHoaDonTheoTrangThai(int trangThai) {
		String sql = "SELECT * FROM HoaDon WHERE trangThai = ?";
		ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, trangThai);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maHD1 = rs.getString("maHD");
				Timestamp ts = rs.getTimestamp("thoiGianTaoDon");
				LocalDateTime thoiGianTaoDon = ts.toLocalDateTime();
				double tienKHDua = rs.getDouble("tienKHDua");
				double phuThu = rs.getDouble("phuThu");
				String hinhThucTT = rs.getString("hinhThucThanhToan");
				String maBan = rs.getString("maBan");
				String maDonDatBanTruoc = rs.getString("maDonDatBanTruoc");
				String maKH = rs.getString("maKH");
				int soKhach = rs.getInt("soKhach");
				String maNV = rs.getString("maNV");
				String maKM = rs.getString("maKM");
				Timestamp tstt = rs.getTimestamp("thoiGianThanhToan");
				LocalDateTime thoiGianThanhToan = tstt.toLocalDateTime();
				String chuThich = rs.getString("chuThich");
				int trangThaiHD = rs.getInt("trangThai");
				int diemTLSuDung = rs.getInt("diemTLSuDung");
				double tongTien = rs.getDouble("tongThanhToan");

				Ban ban = ban_dao.getBan(maBan);
				DonDatBanTruoc ddbt = ddbt_dao.getDonDatBanTheoMa(maDonDatBanTruoc);
				KhachHang kh = kh_dao.getKhachHangById(maKH);
				NhanVien nv = nv_dao.getNhanVienById(maNV);
				ArrayList<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaHD(maHD1);
				
				HoaDon hoaDonInfo = new HoaDon(maHD1, thoiGianThanhToan, tienKHDua, phuThu, hinhThucTT, ban, ddbt, kh, soKhach, nv, new KhuyenMai(maKM), thoiGianTaoDon, trangThaiHD, chuThich, diemTLSuDung, tongTien, dsCTHD);
				
				dsHoaDon.add(hoaDonInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dsHoaDon;  
	}
	
	public HoaDon timHoaDonTheoMaHoaDon(String maHD) {
	    String sql = "SELECT * FROM HoaDon WHERE maHD = ?";

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String maHD1 = rs.getString("maHD");
	                Timestamp ts = rs.getTimestamp("thoiGianTaoDon");
	                LocalDateTime thoiGianTaoDon = ts.toLocalDateTime();
	                double tienKHDua = rs.getDouble("tienKHDua");
	                double phuThu = rs.getDouble("phuThu");
	                String hinhThucTT = rs.getString("hinhThucThanhToan");
	                String maBan = rs.getString("maBan");
	                String maDonDatBanTruoc = rs.getString("maDonDatBanTruoc");
	                String maKH = rs.getString("maKH");
	                int soKhach = rs.getInt("soKhach");
	                String maNV = rs.getString("maNV");
	                String maKM = rs.getString("maKM");
	                Timestamp tstt = rs.getTimestamp("thoiGianThanhToan");
	                LocalDateTime thoiGianThanhToan;
	                if (tstt == null) {
	                	thoiGianThanhToan = null;
	    			}else {
	    				thoiGianThanhToan = tstt.toLocalDateTime();
	    			}
	                int trangThaiHD = rs.getInt("trangThai");
	                int diemTLSuDung = rs.getInt("diemTLSuDung");
	                double tongTien = rs.getDouble("tongThanhToan");
	                
	                ArrayList<ChiTietHoaDon> dsCTHD = cthd_dao.getChiTietHoaDonTheoMaHD(maHD1);
	                
	                HoaDon hoaDonInfo = new HoaDon(maHD1, thoiGianThanhToan, tienKHDua, phuThu, hinhThucTT, new Ban(maBan), new DonDatBanTruoc(maDonDatBanTruoc), new KhachHang(maKH), soKhach, new NhanVien(maNV), new KhuyenMai(maKM), thoiGianTaoDon, trangThaiHD, null, diemTLSuDung, tongTien, dsCTHD);
	                return hoaDonInfo;
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return null;
		
	}
	
	public int demTongSoHoaDon() {
	    int count = 0;
	    String sql = "SELECT COUNT(*) FROM HoaDon";
	    
	    try{
	    	 Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public int demHoaDonCoMaBan() {
	    int count = 0;
	    String sql = "SELECT COUNT(*) FROM HoaDon WHERE maBan IS NOT NULL";

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public DefaultCategoryDataset getDoanhThuTheoNgay(LocalDate tuNgay, LocalDate denNgay) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {         
        	Connection con = ConnectDB.getInstance().getConnection();
        	CallableStatement stmt = con.prepareCall("{call sp_ThongKeDoanhThu_TheoThoiGianBatDau_KetThuc(?, ?)}");
            stmt.setDate(1, Date.valueOf(tuNgay));
            stmt.setDate(2, Date.valueOf(denNgay));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String ngay = rs.getString("NgayThanhToan");
                double doanhThu = rs.getDouble("TongDoanhThu");

                dataset.addValue(doanhThu, "Doanh thu", ngay);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }
	
	public DefaultCategoryDataset getDoanhThuTheoTuanTrongThang(int thang, int nam) {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        CallableStatement stmt = con.prepareCall("{call sp_ThongKeDoanhThuTuanTrongThang(?, ?)}");
	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String tuan = rs.getString("Tuan"); 
	            double doanhThu = rs.getDouble("DoanhThu");

	            dataset.addValue(doanhThu, "Doanh thu", tuan);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return dataset;
	}
	
	public DefaultCategoryDataset getDoanhThuTheoThangTrongNam(int nam) {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        CallableStatement stmt = con.prepareCall("{call sp_ThongKeDoanhThuTheoThangTrongNam(?)}");
	        stmt.setInt(1, nam);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            int thang = rs.getInt("Thang");
	            double doanhThu = rs.getDouble("TongDoanhThu");
	            dataset.addValue(doanhThu, "Doanh thu", "Tháng " + thang);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return dataset;
	}
	
	public DefaultCategoryDataset getDoanhThuTheoNam(int namTu, int namDen) {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        CallableStatement stmt = con.prepareCall("{call sp_ThongKeDoanhThuTheoNamTuCombo(?, ?)}");
	        stmt.setInt(1, namTu);
	        stmt.setInt(2, namDen);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            int nam = rs.getInt("Nam");
	            double doanhThu = rs.getDouble("TongDoanhThu");
	            dataset.addValue(doanhThu, "Doanh thu", "Năm " + nam);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return dataset;
	}

	


	public HoaDon getHoaDonTheoMaDonDatBanTruoc(String maDon) {
	    HoaDon hd = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM HoaDon WHERE maDonDatBanTruoc = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maDon);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String maHD = rs.getString("maHD");
	            Timestamp timestamp = rs.getTimestamp("thoiGianTaoDon");
	            LocalDateTime thoiGianTD = timestamp != null ? timestamp.toLocalDateTime() : null;

	            String maBan = rs.getString("maBan");
	            Ban b = maBan != null ? new Ban(maBan) : null;

	            DonDatBanTruoc ddbt = new DonDatBanTruoc(maDon);

	            String maKH = rs.getString("maKH");
	            KhachHang kh = maKH != null ? new KhachHang(maKH) : null;

	            String maNV = rs.getString("maNV");
	            NhanVien nv = maNV != null ? new NhanVien(maNV) : null;

	            int trangThaiHD = rs.getInt("trangThai");
	            int soKhach = rs.getInt("soKhach");
	            String chuThich = rs.getString("chuThich");

	            hd = new HoaDon(maHD, b, ddbt, kh, nv, thoiGianTD, trangThaiHD, soKhach, chuThich);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return hd;
	}


	public HoaDon timHoaDonTheoMaHD_DDBT(String maHD) {
	    String sql = "SELECT * FROM HoaDon WHERE maHD = ?";

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                LocalDateTime thoiGianTaoDon = rs.getTimestamp("thoiGianTaoDon").toLocalDateTime();
	                String maBan = rs.getString("maBan");
	                String maDonDatBanTruoc = rs.getString("maDonDatBanTruoc");
	                String maKH = rs.getString("maKH");
	                String maNV = rs.getString("maNV");
	                int soKhach = rs.getInt("soKhach");
	                int trangThai = rs.getInt("trangThai");
	                String chuThich = rs.getString("chuThich");

	                return new HoaDon(
	                    maHD,
	                    new Ban(maBan),
	                    new DonDatBanTruoc(maDonDatBanTruoc),
	                    new KhachHang(maKH),
	                    new NhanVien(maNV),
	                    thoiGianTaoDon,
	                    trangThai,
	                    soKhach,
	                    chuThich
	                );
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public boolean updateTrangThaiVaThoiGianTaoDon(String maHD, int trangThai, LocalDateTime thoiGianTao, LocalDateTime thoiGianTaoDuDoan) {
	    int n = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "UPDATE HoaDon SET trangThai = ?, thoiGianTaoDon = ?,thoiGianThanhToan= ?  WHERE maHD = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        
	        stmt.setInt(1, trangThai);
	        stmt.setTimestamp(2, Timestamp.valueOf(thoiGianTao));
	        stmt.setTimestamp(3, Timestamp.valueOf(thoiGianTaoDuDoan));

	        stmt.setString(4, maHD);
	        
	        n = stmt.executeUpdate();
	        stmt.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public boolean updateShiftCode(String maHD, String newShiftCode) {
        try {
            // Lấy hóa đơn hiện tại
            HoaDon hd = timHoaDonTheoMaHoaDon(maHD);
            if (hd == null) {
                logger.log(Level.WARNING, "Không tìm thấy hóa đơn: " + maHD);
                return false;
            }

            // Tạo mã hóa đơn mới dựa trên mã ca mới
            String oldShiftCode = maHD.substring(10, 14); // Lấy phần CA0x từ maHD (ví dụ: CA01)
            String datePart = maHD.substring(2, 10); // Lấy phần ngày (ddMMyyyy)
            String sequencePart = maHD.substring(14); // Lấy phần số thứ tự (001, 002, ...)
            String newMaHD = "HD" + datePart + newShiftCode + sequencePart;

            // Kiểm tra xem mã hóa đơn mới đã tồn tại chưa
            if (timHoaDonTheoMaHoaDon(newMaHD) != null) {
                logger.log(Level.WARNING, "Mã hóa đơn mới đã tồn tại: " + newMaHD);
                return false;
            }

            // Cập nhật mã hóa đơn trong cơ sở dữ liệu
            Connection con = ConnectDB.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE HoaDon SET maHD = ? WHERE maHD = ?");
            stmt.setString(1, newMaHD);
            stmt.setString(2, maHD);
            int n = stmt.executeUpdate();

            if (n > 0) {
                // Cập nhật chi tiết hóa đơn nếu có
                cthd_dao.updateMaHD(maHD, newMaHD);
                logger.info("Cập nhật mã ca cho hóa đơn: " + maHD + " thành " + newMaHD);
                return true;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật mã ca cho hóa đơn: " + maHD, e);
        }
        return false;
    }
}
