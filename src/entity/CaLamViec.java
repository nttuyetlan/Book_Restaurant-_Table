package entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class CaLamViec {
	private String maCaLV;
	private String tenCaLV;
	// Thời gian thực tế
	private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    
    private LocalDateTime gioBatDauThucTe; // Thời gian thực tế
    private LocalDateTime gioKetThucThucTe;
    
	private boolean trangThai;
	private NhanVien nhanVien;
	
	public CaLamViec(String maCaLV, String tenCaLV, LocalDateTime thoiGianBatDau, LocalDateTime thoiGianKetThuc, boolean trangThai) {
        this.maCaLV = maCaLV;
        this.tenCaLV = tenCaLV;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.trangThai = trangThai;
    }

    public CaLamViec(String maCaLV, String tenCaLV, LocalDateTime thoiGianBatDau, LocalDateTime thoiGianKetThuc, boolean trangThai, LocalDateTime gioBatDauThucTe, LocalDateTime gioKetThucThucTe) {
        this.maCaLV = maCaLV;
        this.tenCaLV = tenCaLV;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.trangThai = trangThai;
        this.gioBatDauThucTe = gioBatDauThucTe;
        this.gioKetThucThucTe = gioKetThucThucTe;
    }
//	public CaLamViec(String maCaLV, String tenCaLV, LocalDateTime thoiGianBatDau, LocalDateTime thoiGianKetThuc,
//			boolean trangThai, NhanVien nhanVien) {
//		super();
//		this.maCaLV = maCaLV;
//		this.tenCaLV = tenCaLV;
//		this.thoiGianBatDau = thoiGianBatDau;
//		this.thoiGianKetThuc = thoiGianKetThuc;
//		this.trangThai = trangThai;
//		this.nhanVien = nhanVien;
//	}

	public CaLamViec(String maCaLV) {
		super();
		this.maCaLV = maCaLV;
	}

	public String getMaCaLV() {
		return maCaLV;
	}

	public void setMaCaLV(String maCaLV) {
		this.maCaLV = maCaLV;
	}

	public String getTenCaLV() {
		return tenCaLV;
	}

	public void setTenCaLV(String tenCaLV) {
		this.tenCaLV = tenCaLV;
	}

	public LocalDateTime getThoiGianBatDau() {
		return thoiGianBatDau;
	}

	public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
		this.thoiGianBatDau = thoiGianBatDau;
	}

	public LocalDateTime getThoiGianKetThuc() {
		return thoiGianKetThuc;
	}

	public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
		this.thoiGianKetThuc = thoiGianKetThuc;
	}

	public boolean getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maCaLV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaLamViec other = (CaLamViec) obj;
		return Objects.equals(maCaLV, other.maCaLV);
	}
	
}

