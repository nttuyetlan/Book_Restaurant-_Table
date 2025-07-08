package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiMon;


public class LoaiMon_DAO {
	ArrayList<LoaiMon> dsLoaiMon;
	
	public LoaiMon_DAO() {
		// TODO Auto-generated constructor stub
		dsLoaiMon = new ArrayList<LoaiMon>();
	}
	
	public ArrayList<LoaiMon> getAllLoaiMon(){
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM LoaiMon");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maLoai = rs.getString("maLoai");
				String tenLoai = rs.getString("tenLoai");
				LoaiMon loai = new LoaiMon(maLoai, tenLoai);
				dsLoaiMon.add(loai);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsLoaiMon;
	}
	
	public boolean insertLoaiMon(LoaiMon loaiMon) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO LoaiMon VALUES(?, ?)");
			stmt.setString(1, loaiMon.getMaLoai());
			stmt.setString(2, loaiMon.getTenLoai());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteLoaiMon(String maLoai) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM LoaiMon WHERE maLoai=?");
			stmt.setString(1, maLoai);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateLoaiMon(LoaiMon loaiMon) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE LoaiMon SET tenLoai=? WHERE maLoai=?");
			stmt.setString(1, loaiMon.getTenLoai());
			stmt.setString(2, loaiMon.getMaLoai());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
}
