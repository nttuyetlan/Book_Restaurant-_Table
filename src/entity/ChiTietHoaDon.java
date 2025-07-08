package entity;

import java.util.Objects;

public class ChiTietHoaDon {
	private HoaDon HD;
	private MonAn monAn;
	private int soLuong;
	private String ghiChu;
	public ChiTietHoaDon(HoaDon HD, MonAn monAn, int soLuong, String ghiChu) {
		super();
		this.HD = HD;
		this.monAn = monAn;
		this.soLuong = soLuong;
		this.ghiChu = ghiChu;
	}
	public ChiTietHoaDon(HoaDon HD, MonAn monAn) {
		super();
		this.HD = HD;
		this.monAn = monAn;
	}
	public HoaDon getHD() {
		return HD;
	}
	public void setHD(HoaDon HD) {
		this.HD = HD;
	}
	public MonAn getMonAn() {
		return monAn;
	}
	public void setMonAn(MonAn monAn) {
		this.monAn = monAn;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	public double tinhThanhTien() {
		return soLuong * monAn.tinhGiaSauThue();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(HD, monAn);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(HD, other.HD) && Objects.equals(monAn, other.monAn);
	}
	
}
