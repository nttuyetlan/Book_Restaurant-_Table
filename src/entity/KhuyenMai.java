package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhuyenMai {
	private String maKM;
	private String tenKM;
	private String noiDungKM;
	private double giaTriKM;
	private int soLuong;
	private LocalDate ngayBatDauKM;
	private LocalDate ngayKetThucKM;
	public KhuyenMai(String maKM, String tenKM, String noiDungKM, double giaTriKM, int soLuong, LocalDate ngayBatDauKM,
			LocalDate ngayKetThucKM) {
		super();
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.noiDungKM = noiDungKM;
		this.giaTriKM = giaTriKM;
		this.soLuong = soLuong;
		this.ngayBatDauKM = ngayBatDauKM;
		this.ngayKetThucKM = ngayKetThucKM;
	}
	public KhuyenMai(String maKM) {
		super();
		this.maKM = maKM;
	}
	public String getTenKM() {
		return tenKM;
	}
	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}
	public String getNoiDungKM() {
		return noiDungKM;
	}
	public void setNoiDungKM(String noiDungKM) {
		this.noiDungKM = noiDungKM;
	}
	public double getGiaTriKM() {
		return giaTriKM;
	}
	public void setGiaTriKM(double giaTriKM) {
		this.giaTriKM = giaTriKM;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public LocalDate getNgayBatDauKM() {
		return ngayBatDauKM;
	}
	public void setNgayBatDauKM(LocalDate ngayBatDauKM) {
		this.ngayBatDauKM = ngayBatDauKM;
	}
	public LocalDate getNgayKetThucKM() {
		return ngayKetThucKM;
	}
	public void setNgayKetThucKM(LocalDate ngayKetThucKM) {
		this.ngayKetThucKM = ngayKetThucKM;
	}
	public String getMaKM() {
		return maKM;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(maKM, other.maKM);
	}
	
	
}
