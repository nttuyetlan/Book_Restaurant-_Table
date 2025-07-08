package entity;

import java.util.Objects;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String sdtKH;
	private String emailKH;
	private String ghiChuKH;
	private int diemTL;
	public KhachHang(String maKH, String tenKH, String sdtKH, String emailKH, String ghiChuKH, int diemTL) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.sdtKH = sdtKH;
		this.emailKH = emailKH;
		this.ghiChuKH = ghiChuKH;
		this.diemTL = diemTL;
	}
	
	public KhachHang(String maKH, String tenKH) {
        super();
        this.maKH = maKH;
        this.tenKH = tenKH;
    }
	
	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getSdtKH() {
		return sdtKH;
	}
	public void setSdtKH(String sdtKH) {
		this.sdtKH = sdtKH;
	}
	public String getEmailKH() {
		return emailKH;
	}
	public void setEmailKH(String emailKH) {
		this.emailKH = emailKH;
	}
	public String getGhiChuKH() {
		return ghiChuKH;
	}
	public void setGhiChuKH(String ghiChuKH) {
		this.ghiChuKH = ghiChuKH;
	}
	public int getDiemTL() {
		return diemTL;
	}
	public void setDiemTL(int diemTL) {
		this.diemTL = diemTL;
	}
	public String getMaKH() {
		return maKH;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKH);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKH, other.maKH);
	}
	
}
