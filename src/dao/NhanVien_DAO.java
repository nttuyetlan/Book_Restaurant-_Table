package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_DAO {
	ArrayList<NhanVien> dsNV;
		
	public NhanVien_DAO() {
			// TODO Auto-generated constructor stub
			dsNV = new ArrayList<NhanVien>();
	}
		public ArrayList<NhanVien> getAllNV(){
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM NhanVien");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String maNV = rs.getString("maNV");
					String tenNV = rs.getString("tenNV");
					String soCCCD = rs.getString("soCCCD");
					byte[] data = rs.getBytes("hinhTheNV");
					String sdtNV = rs.getString("sdtNV");
					String emailNV = rs.getString("emailNV");
					String chucVu = rs.getString("chucVu");
					LocalDate ngayBDLV = rs.getDate("ngayBatDauLV").toLocalDate();
					boolean trangThaiLV = rs.getBoolean("trangThaiLV");
					String gioiTinh = rs.getString("gioiTinh");
					String diaChi = rs.getString("diaChi");
					LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
					NhanVien nv = new NhanVien(maNV, tenNV, soCCCD, data, sdtNV, emailNV, chucVu, ngayBDLV, trangThaiLV, gioiTinh, diaChi, ngaySinh);
					dsNV.add(nv);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return dsNV;
		}
		
		public boolean insertNV(NhanVien nv) {
			int n = 0;
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement("INSERT INTO NhanVien VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, nv.getMaNV());
				stmt.setString(2, nv.getTenNV());
				stmt.setString(3, nv.getSoCCCD());
				stmt.setBytes(4, nv.getHinhTheNV());
				stmt.setString(5, nv.getSdtNV());
				stmt.setString(6, nv.getEmailNV());
				stmt.setString(7, nv.getChucVu());
				stmt.setDate(8, Date.valueOf(nv.getNgayBatDauLV()));
				stmt.setBoolean(9, nv.getTrangThaiLV());
				stmt.setString(10, nv.getGioiTinh());
				stmt.setString(11, nv.getDiaChi());
				stmt.setDate(12, Date.valueOf(nv.getNgaySinh()));
				
				n = stmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return n > 0;
		}
		public NhanVien getNhanVienById(String maNV) {
		    NhanVien nv = null;
		    try {
		        Connection con = ConnectDB.getInstance().getConnection();
		        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
		        PreparedStatement stmt = con.prepareStatement(sql);
		        stmt.setString(1, maNV);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            String tenNV = rs.getString("tenNV");
		            String soCCCD = rs.getString("soCCCD");
		            byte[] data = rs.getBytes("hinhTheNV");
		            String sdtNV = rs.getString("sdtNV");
		            String emailNV = rs.getString("emailNV");
		            String chucVu = rs.getString("chucVu");
		            LocalDate ngayBDLV = rs.getDate("ngayBatDauLV").toLocalDate();
		            boolean trangThaiLV = rs.getBoolean("trangThaiLV");
		            String gioiTinh = rs.getString("gioiTinh");
		            String diaChi = rs.getString("diaChi");
		            LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
		            nv = new NhanVien(maNV, tenNV, soCCCD, data, sdtNV, emailNV, chucVu, ngayBDLV, trangThaiLV, gioiTinh, diaChi, ngaySinh);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return nv;
		}
		public NhanVien getNhanVienTheoMa(String maNVTim) {
		    NhanVien nv = null;
		    try {
		        Connection con = ConnectDB.getInstance().getConnection();
		        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
		        PreparedStatement stmt = con.prepareStatement(sql);
		        stmt.setString(1, maNVTim);
		        ResultSet rs = stmt.executeQuery();
		        
		        if (rs.next()) {
		            String maNV = rs.getString("maNV");
		            String tenNV = rs.getString("tenNV");
		            String soCCCD = rs.getString("soCCCD");
		            byte[] data = rs.getBytes("hinhTheNV");
		            String sdtNV = rs.getString("sdtNV");
		            String emailNV = rs.getString("emailNV");
		            String chucVu = rs.getString("chucVu");
		            LocalDate ngayBDLV = rs.getDate("ngayBatDauLV").toLocalDate();
		            boolean trangThaiLV = rs.getBoolean("trangThaiLV");
		            String gioiTinh = rs.getString("gioiTinh");
		            String diaChi = rs.getString("diaChi");
		            LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
		            
		            nv = new NhanVien(maNV, tenNV, soCCCD, data, sdtNV, emailNV, chucVu, ngayBDLV, trangThaiLV, gioiTinh, diaChi, ngaySinh);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return nv;
		}

		public boolean deleteNV(String maNV) {
			int n = 0;
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement("DELETE FROM NhanVien WHERE maNV=?");
				stmt.setString(1, maNV);
				n = stmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return n > 0;
		}
		
		public boolean updateNV (NhanVien nv) {
			int n = 0;
			try {
				Connection con = ConnectDB.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement("UPDATE NhanVien SET tenNV=?, soCCCD=?, hinhTheNV=?, sdtNV=?, emailNV=?, chucVu=?, ngayBatDauLV=?, trangThaiLV=?, gioiTinh=?, diaChi=?, ngaySinh=? WHERE maNV=?");
				stmt.setString(1, nv.getTenNV());
				stmt.setString(2, nv.getSoCCCD());
				stmt.setBytes(3, nv.getHinhTheNV());
				stmt.setString(4, nv.getSdtNV());
				stmt.setString(5, nv.getEmailNV());
				stmt.setString(6, nv.getChucVu());
				stmt.setDate(7, Date.valueOf(nv.getNgayBatDauLV()));
				stmt.setBoolean(8, nv.getTrangThaiLV());
				stmt.setString(9, nv.getGioiTinh());
				stmt.setString(10, nv.getDiaChi());
				stmt.setDate(11, Date.valueOf(nv.getNgaySinh()));
				stmt.setString(12, nv.getMaNV());
				n = stmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return n > 0;
		}
	
	public ArrayList<NhanVien> timKiemNV(String keyword) {
	    ArrayList<NhanVien> dsNV = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        String sql = "SELECT * FROM NhanVien WHERE "
	                + "maNV LIKE ? OR "
	                + "tenNV LIKE ? OR "
	                + "soCCCD LIKE ? OR "
	                + "sdtNV LIKE ? OR "
	                + "emailNV LIKE ? OR "
	                + "chucVu LIKE ? OR "
	                + "diaChi LIKE ? OR "
	                + "gioiTinh LIKE ?";
	        
	        stmt = con.prepareStatement(sql);
	        String searchPattern = "%" + keyword + "%";
	        for (int i = 1; i <= 8; i++) {
	            stmt.setString(i, searchPattern);
	        }
	        
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            NhanVien nv = new NhanVien(rs.getString("maNV"));
	            nv.setTenNV(rs.getString("tenNV"));
	            nv.setSoCCCD(rs.getString("soCCCD"));
	            nv.setHinhTheNV(rs.getBytes("hinhTheNV"));
	            nv.setSdtNV(rs.getString("sdtNV"));
	            nv.setEmailNV(rs.getString("emailNV"));
	            nv.setChucVu(rs.getString("chucVu"));
	            nv.setNgayBatDauLV(rs.getDate("ngayBatDauLV").toLocalDate());
	            nv.setTrangThaiLV(rs.getBoolean("trangThaiLV"));
	            nv.setGioiTinh(rs.getString("gioiTinh"));
	            nv.setDiaChi(rs.getString("diaChi"));
	            nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
	            
	            dsNV.add(nv);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return dsNV;
	}
	
	public String getLastMaNV() {
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String lastMaNV = null;
	    
	    try {
	        // Sử dụng TOP 1 thay vì LIMIT 1
	        String sql = "SELECT TOP 1 maNV FROM NhanVien ORDER BY maNV DESC";
	        stmt = con.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            lastMaNV = rs.getString("maNV");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return lastMaNV;
	}
	
	public String getTenNhanVienByMaNV(String maNV) {
	    String tenNV = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = con.prepareStatement("SELECT tenNV FROM NhanVien WHERE maNV = ?");
	        stmt.setString(1, maNV);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            tenNV = rs.getString("tenNV");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tenNV;
	}
	
	public String generateNextEmployeeId(String chucVu) {
	    String prefix = chucVu.equals("Quản lý") ? "QL" : "NV";
	    String nextId = prefix + "001"; // Default if no existing IDs
	    
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT TOP 1 maNV FROM NhanVien WHERE maNV LIKE ? ORDER BY maNV DESC";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, prefix + "%");
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // Extract the highest existing ID
	            String highestId = rs.getString("maNV");
	            // Get the numeric part
	            String numericPart = highestId.substring(prefix.length());
	            // Increment and format with leading zeros
	            int nextNum = Integer.parseInt(numericPart) + 1;
	            nextId = prefix + String.format("%03d", nextNum);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return nextId;
	}
	
}