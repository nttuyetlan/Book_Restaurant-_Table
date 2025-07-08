package entity;

import java.util.Objects;

public class Ban {
	private String maBan;
	private int soLuongGhe;
	private int trangThai;
	private KhuVuc KV;
	
	public Ban(String maBan, int soLuongGhe, int trangThai, KhuVuc KV) {
		super();
		this.maBan = maBan;
		this.soLuongGhe = soLuongGhe;
		this.trangThai = trangThai;
		this.KV = KV;
	}
	
	public Ban(String maBan) {
		super();
		this.maBan = maBan;
	}
	public String getMaBan() {
		return maBan;
	}
	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}
	public int getSoLuongGhe() {
		return soLuongGhe;
	}
	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	public KhuVuc getKV() {
		return KV;
	}
	public void setKV(KhuVuc KV) {
		this.KV = KV;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maBan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		return Objects.equals(maBan, other.maBan);
	}
	
	
}
