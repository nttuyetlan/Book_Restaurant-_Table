package entity;

import java.util.Objects;

public class LoaiMon {
	private String maLoai;
	private String tenLoai;
	public LoaiMon(String maLoai, String tenLoai) {
		super();
		this.maLoai = maLoai;
		this.tenLoai = tenLoai;
	}
	public LoaiMon(String maLoai) {
		super();
		this.maLoai = maLoai;
	}
	public String getMaLoai() {
		return maLoai;
	}
	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}
	public String getTenLoai() {
		return tenLoai;
	}
	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maLoai);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiMon other = (LoaiMon) obj;
		return Objects.equals(maLoai, other.maLoai);
	}
	@Override
	public String toString() {
		return tenLoai;
	}
}
