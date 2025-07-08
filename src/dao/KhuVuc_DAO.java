package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Ban;
import entity.KhuVuc;

public class KhuVuc_DAO {
	ArrayList<KhuVuc> dsKV;
	
	public KhuVuc_DAO() {
		// TODO Auto-generated constructor stub
		dsKV = new ArrayList<KhuVuc>();
	}
	public ArrayList<KhuVuc> getAllKhuVuc(){
		ArrayList<KhuVuc> dsKV = new ArrayList<>();
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhuVuc");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maKV = rs.getString("maKV");
				String tenKV = rs.getString("tenKV");
				boolean trangThai = rs.getBoolean("trangThai");
				KhuVuc kv = new KhuVuc(maKV, tenKV, trangThai);
				dsKV.add(kv);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsKV;
	}
	public KhuVuc getKhuVucTheoMa(String maKV) {
		KhuVuc KV = null;
		
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM KhuVuc WHERE maKV=?");
			stmt.setString(1, maKV);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenKV = rs.getString("tenKV");
				boolean trangThai = rs.getBoolean("trangThai");
				KV = new KhuVuc(maKV, tenKV, trangThai);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return KV;
		
	}
	
	
	
	public boolean insertKhuVuc(KhuVuc kv) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO KhuVuc VALUES(?, ?, ?)");
			stmt.setString(1, kv.getMaKV());
			stmt.setString(2, kv.getTenKV());
			stmt.setBoolean(3, kv.getTrangThai());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean deleteKhuVuc(String maKV) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("DELETE FROM KhuVuc WHERE maKV=?");
			stmt.setString(1, maKV);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	public boolean updateKhuVuc(KhuVuc kv) {
		int n = 0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE KhuVuc SET tenKV=?, trangThai=? WHERE maKV=?");
			stmt.setString(1, kv.getTenKV());
			stmt.setBoolean(2, kv.getTrangThai());
			stmt.setString(3, kv.getMaKV());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
}	
