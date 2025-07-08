package entity;

import java.util.Objects;

public class TaiKhoan {
	private String tenTaiKhoan;
	private String matKhau;
	private NhanVien nhanVien;
	public TaiKhoan(String tenTaiKhoan, String matKhau, NhanVien nhanVien) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
		this.nhanVien = nhanVien;
	}
	public TaiKhoan(String tenTaiKhoan) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}
	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	@Override
	public int hashCode() {
		return Objects.hash(matKhau);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(matKhau, other.matKhau);
	}
	
	
}
