package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class MonAn {
	private String maMonAn;
	private String tenMonAn;
	private String moTa;
	private double donGia;
	private byte[] hinhMonAn;
	private LocalDateTime ngayCapNhat;
	private double thueMon;
	private LoaiMon loai;
	private int trangThai;
	
	public MonAn(String maMonAn, String tenMonAn, String moTa, double donGia, byte[] hinhMonAn,
			LocalDateTime ngayCapNhat, double thueMon, LoaiMon loai, int trangThai) {
		super();
		this.maMonAn = maMonAn;
		this.tenMonAn = tenMonAn;
		this.moTa = moTa;
		this.donGia = donGia;
		this.hinhMonAn = hinhMonAn;
		this.ngayCapNhat = ngayCapNhat;
		this.thueMon = thueMon;
		this.loai = loai;
		this.trangThai = trangThai;
	}
	public MonAn(String maMonAn) {
		super();
		this.maMonAn = maMonAn;
	}
	
	public String getTenMonAn() {
		return tenMonAn;
	}
	public void setTenMonAn(String tenMonAn) {
		this.tenMonAn = tenMonAn;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public byte[] getHinhMonAn() {
		return hinhMonAn;
	}
	public void setHinhMonAn(byte[] hinhMonAn) {
		this.hinhMonAn = hinhMonAn;
	}
	public LocalDateTime getNgayCapNhat() {
		return ngayCapNhat;
	}
	public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
		this.ngayCapNhat = ngayCapNhat;
	}
	public double getThueMon() {
		return thueMon;
	}
	public void setThueMon(double thueMon) {
		this.thueMon = thueMon;
	}
	public LoaiMon getLoai() {
		return loai;
	}
	public void setLoai(LoaiMon loai) {
		this.loai = loai;
	}
	public String getMaMonAn() {
		return maMonAn;
	}
	
	public double tinhGiaSauThue() {
		return donGia*thueMon + donGia;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maMonAn);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonAn other = (MonAn) obj;
		return Objects.equals(maMonAn, other.maMonAn);
	}
	
	
}
