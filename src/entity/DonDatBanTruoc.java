
package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class DonDatBanTruoc {
	private String maDonDatBanTruoc;
	private LocalDateTime thoiGianTao;
	private int soLuongKhach;
	private LocalDateTime thoiGianNhanBan;
	private double tienCoc;
	private KhachHang KH;
	private NhanVien NV;
	private Ban ban;
	private int trangThai;

	public DonDatBanTruoc(String maDonDatBanTruoc, LocalDateTime thoiGianTao, int soLuongKhach,
			LocalDateTime thoiGianNhanBan, double tienCoc, KhachHang KH, NhanVien NV, Ban ban, int trangThai) {
		super();
		this.maDonDatBanTruoc = maDonDatBanTruoc;
		this.thoiGianTao = thoiGianTao;
		this.soLuongKhach = soLuongKhach;
		this.thoiGianNhanBan = thoiGianNhanBan;
		this.tienCoc = tienCoc;
		this.KH = KH;
		this.NV = NV;
		this.ban = ban;
		this.trangThai = trangThai;
	}

	public DonDatBanTruoc(String maDonDatBanTruoc) {
		super();
		this.maDonDatBanTruoc = maDonDatBanTruoc;
	}

	public String getMaDonDatBanTruoc() {
		return maDonDatBanTruoc;
	}
	
	
	public void setMaDonDatBanTruoc(String maDonDatBanTruoc) {
		this.maDonDatBanTruoc = maDonDatBanTruoc;
	}
	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(LocalDateTime thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public int getSoLuongKhach() {
		return soLuongKhach;
	}

	public void setSoLuongKhach(int soLuongKhach) {
		this.soLuongKhach = soLuongKhach;
	}

	public LocalDateTime getThoiGianNhanBan() {
		return thoiGianNhanBan;
	}

	public void setThoiGianNhanBan(LocalDateTime thoiGianNhanBan) {
		this.thoiGianNhanBan = thoiGianNhanBan;
	}

	public double getTienCoc() {
		return tienCoc;
	}

	public void setTienCoc(double tienCoc) {
		this.tienCoc = tienCoc;
	}

	public KhachHang getKH() {
		return KH;
	}

	public void setKH(KhachHang kH) {
		KH = kH;
	}

	public NhanVien getNV() {
		return NV;
	}

	public void setNV(NhanVien nV) {
		NV = nV;
	}

	public Ban getBan() {
		return ban;
	}

	public void setMaBan(Ban ban) {
		this.ban = ban;
	}

	public int getTrangThai() {
	    return trangThai;
	}

	public void setTrangThai(int trangThai) {
	    this.trangThai = trangThai;
	}

	// Phương thức tiện ích để lấy chuỗi trạng thái
	public String getTrangThaiText() {
	    return trangThai == 1 ? "Đã đến" : "Chưa đến";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maDonDatBanTruoc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonDatBanTruoc other = (DonDatBanTruoc) obj;
		return Objects.equals(maDonDatBanTruoc, other.maDonDatBanTruoc);
	}
}

