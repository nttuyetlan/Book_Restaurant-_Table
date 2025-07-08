package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private String soCCCD;
	private byte[] hinhTheNV;
	private String sdtNV;
	private String emailNV;
	private String chucVu;
	private LocalDate ngayBatDauLV;
	private boolean trangThaiLV;
	private String gioiTinh;
	private String diaChi;
	private LocalDate ngaySinh;
	
	public NhanVien(String maNV, String tenNV, String soCCCD, byte[] hinhTheNV, String sdtNV, String emailNV,
			String chucVu, LocalDate ngayBatDauLV, boolean trangThaiLV, String gioiTinh, String diaChi,
			LocalDate ngaySinh) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.soCCCD = soCCCD;
		this.hinhTheNV = hinhTheNV;
		this.sdtNV = sdtNV;
		this.emailNV = emailNV;
		this.chucVu = chucVu;
		this.ngayBatDauLV = ngayBatDauLV;
		this.trangThaiLV = trangThaiLV;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.ngaySinh = ngaySinh;
	}
	
	public NhanVien(String maNV, String tenNV, String gioiTinh, LocalDate ngaySinh, String sdtNV, String emailNV,
            String diaChi, String chucVu, boolean trangThaiLV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdtNV = sdtNV;
        this.emailNV = emailNV;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.trangThaiLV = trangThaiLV;
    }
	
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}
	
	public NhanVien(String maNV, String tenNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
    }


	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getSoCCCD() {
		return soCCCD;
	}
	public void setSoCCCD(String soCCCD) {
		this.soCCCD = soCCCD;
	}
	public byte[] getHinhTheNV() {
		return hinhTheNV;
	}
	public void setHinhTheNV(byte[] hinhTheNV) {
		this.hinhTheNV = hinhTheNV;
	}
	public String getSdtNV() {
		return sdtNV;
	}
	public void setSdtNV(String sdtNV) {
		this.sdtNV = sdtNV;
	}
	public String getEmailNV() {
		return emailNV;
	}
	public void setEmailNV(String emailNV) {
		this.emailNV = emailNV;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public LocalDate getNgayBatDauLV() {
		return ngayBatDauLV;
	}
	public void setNgayBatDauLV(LocalDate ngayBatDauLV) {
		this.ngayBatDauLV = ngayBatDauLV;
	}
	public boolean getTrangThaiLV() {
		return trangThaiLV;
	}
	public void setTrangThaiLV(boolean trangThaiLV) {
		this.trangThaiLV = trangThaiLV;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getMaNV() {
		return maNV;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}
	
}
