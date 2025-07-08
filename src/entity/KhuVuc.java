package entity;

import java.util.Objects;

public class KhuVuc {
	private String maKV;
	private String tenKV;
	private boolean trangThai;
	public KhuVuc(String maKV, String tenKV, boolean trangThai) {
		super();
		this.maKV = maKV;
		this.tenKV = tenKV;
		this.trangThai = trangThai;
	}
	public KhuVuc(String maKV) {
		super();
		this.maKV = maKV;
	}
	public String getMaKV() {
		return maKV;
	}
	public void setMaKV(String maKV) {
		this.maKV = maKV;
	}
	public String getTenKV() {
		return tenKV;
	}
	public void setTenKV(String tenKV) {
		this.tenKV = tenKV;
	}
	public boolean getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKV);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuVuc other = (KhuVuc) obj;
		return Objects.equals(maKV, other.maKV);
	}
	
}
