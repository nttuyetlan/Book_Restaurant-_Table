package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HoaDon {
	private String maHD;
	private LocalDateTime thoiGianThanhToan;
	private double tienKHDua;
	private double phuThu;
	private String hinhThucThanhToan;
	private Ban ban;
	private DonDatBanTruoc DonDatBanTruoc;
	private KhachHang KH;
	private int soKhach;
	private NhanVien NV;
	private KhuyenMai KM;
	private LocalDateTime thoiGianTaoDon;
	private int trangThai;
	private String chuThich;
	private int diemTLSuDung;
	private double tongThanhToan;
	
	private ArrayList<ChiTietHoaDon> danhSachChiTiet;

	public HoaDon(String maHD, LocalDateTime thoiGianThanhToan, double tienKHDua, double phuThu,
			String hinhThucThanhToan, Ban ban, entity.DonDatBanTruoc donDatBanTruoc, KhachHang kH, int soKhach,
			NhanVien nV, KhuyenMai kM, LocalDateTime thoiGianTaoDon, int trangThai, String chuThich, int diemTLSuDung,
			double tongThanhToan, ArrayList<ChiTietHoaDon> danhSachChiTiet) {
		super();
		this.maHD = maHD;
		this.thoiGianThanhToan = thoiGianThanhToan;
		this.tienKHDua = tienKHDua;
		this.phuThu = phuThu;
		this.hinhThucThanhToan = hinhThucThanhToan;
		this.ban = ban;
		DonDatBanTruoc = donDatBanTruoc;
		KH = kH;
		this.soKhach = soKhach;
		NV = nV;
		KM = kM;
		this.thoiGianTaoDon = thoiGianTaoDon;
		this.trangThai = trangThai;
		this.chuThich = chuThich;
		this.diemTLSuDung = diemTLSuDung;
		this.tongThanhToan = tongThanhToan;
		this.danhSachChiTiet = danhSachChiTiet;
	}

	public HoaDon(String maHD, Ban ban, entity.DonDatBanTruoc donDatBanTruoc, KhachHang kH, NhanVien nV, LocalDateTime thoiGianTaoDon, int trangThai, int soLuongKH, String chuThich) {
		super();
		this.maHD = maHD;
		this.ban = ban;
		DonDatBanTruoc = donDatBanTruoc;
		KH = kH;
		NV = nV;
		this.thoiGianTaoDon = thoiGianTaoDon;
		this.trangThai = trangThai;
		this.soKhach = soLuongKH;
		this.chuThich = chuThich;
	}
	
	public HoaDon(String maHD, Ban ban, entity.DonDatBanTruoc donDatBanTruoc, KhachHang kH, NhanVien nV, LocalDateTime thoiGianTaoDon, int trangThai, int soLuongKH) {
		super();
		this.maHD = maHD;
		this.ban = ban;
		DonDatBanTruoc = donDatBanTruoc;
		KH = kH;
		NV = nV;
		this.thoiGianTaoDon = thoiGianTaoDon;
		this.trangThai = trangThai;
		this.soKhach = soLuongKH;
		
	}
	
	public HoaDon(String maHD, Ban ban, entity.DonDatBanTruoc donDatBanTruoc, KhachHang kH, NhanVien nV, LocalDateTime thoiGianTaoDon, int trangThai) {
		super();
		this.maHD = maHD;
		this.ban = ban;
		DonDatBanTruoc = donDatBanTruoc;
		KH = kH;
		NV = nV;
		this.thoiGianTaoDon = thoiGianTaoDon;
		this.trangThai = trangThai;
	}


	
	public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}
	

	public LocalDateTime getThoiGianThanhToan() {
		return thoiGianThanhToan;
	}


	public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) {
		this.thoiGianThanhToan = thoiGianThanhToan;
	}


	public double getTienKHDua() {
		return tienKHDua;
	}


	public void setTienKHDua(double tienKHDua) {
		this.tienKHDua = tienKHDua;
	}


	public double getPhuThu() {
		return phuThu;
	}


	public void setPhuThu(double phuThu) {
		this.phuThu = phuThu;
	}


	public String getHinhThucThanhToan() {
		return hinhThucThanhToan;
	}


	public void setHinhThucThanhToan(String hinhThucThanhToan) {
		this.hinhThucThanhToan = hinhThucThanhToan;
	}


	public Ban getBan() {
		return ban;
	}


	public void setBan(Ban ban) {
		this.ban = ban;
	}


	public DonDatBanTruoc getDonDatBanTruoc() {
		return DonDatBanTruoc;
	}


	public void setDonDatBanTruoc(DonDatBanTruoc donDatBanTruoc) {
		DonDatBanTruoc = donDatBanTruoc;
	}


	public KhachHang getKH() {
		return KH;
	}


	public void setKH(KhachHang kH) {
		KH = kH;
	}
	
	public int getSoKhach() {
		return soKhach;
	}
	
	public void setSoKhach(int soKhach) {
		this.soKhach = soKhach;
	}


	public NhanVien getNV() {
		return NV;
	}


	public void setNV(NhanVien nV) {
		NV = nV;
	}


	public KhuyenMai getKM() {
		return KM;
	}


	public void setKM(KhuyenMai kM) {
		KM = kM;
	}


	public LocalDateTime getThoiGianTaoDon() {
		return thoiGianTaoDon;
	}


	public void setThoiGianTaoDon(LocalDateTime thoiGianTaoDon) {
		this.thoiGianTaoDon = thoiGianTaoDon;
	}


	public int getTrangThai() {
		return trangThai;
	}


	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}


	public String getChuThich() {
		return chuThich;
	}


	public void setChuThich(String chuThich) {
		this.chuThich = chuThich;
	}
	
	public int getDiemTLSuDung() {
		return diemTLSuDung;
	}
	
	public void setDiemTLSuDung(int diemTLSuDung) {
		this.diemTLSuDung = diemTLSuDung;
	}
	
	public double getTongThanhToan() {
		return tongThanhToan;
	}
	
	public void setTongThanhToan(double tongThanhToan) {
		this.tongThanhToan = tongThanhToan;
	}

	public ArrayList<ChiTietHoaDon> getDanhSachChiTiet() {
		return danhSachChiTiet;
	}


	public void setDanhSachChiTiet(ArrayList<ChiTietHoaDon> danhSachChiTiet) {
		this.danhSachChiTiet = danhSachChiTiet;
	}


	public String getMaHD() {
		return maHD;
	}
	
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	
	public double tinhTongTien() {
		double tong = 0;
		for (ChiTietHoaDon cthd : danhSachChiTiet) {
			tong += cthd.tinhThanhTien();
		}
		return tong;
	}
	
	public double tinhTienKhuyenMai() {
		if (KM == null) {
	        return 0.0; 
	    }
	    return tinhTongTien() * KM.getGiaTriKM();
	}
	
	public double quyDoiDiemTL() {
		double tienTL = (double) diemTLSuDung;
		return tienTL;
	}
	
	public double tinhTienThanhToan() {
//		double tongTien = tinhTongTien();
		double tienKM = tinhTienKhuyenMai();
		double tienQuyDoi = quyDoiDiemTL();
		double tongThanhToan = tinhTongTien() + phuThu;
		
		int diemSuDung = (int) Math.min(tinhTongTien(), tienQuyDoi);
		
		this.tongThanhToan = tongThanhToan - tienKM - diemSuDung;
		return tongThanhToan - tienKM - diemSuDung;
	}
	
	public double tinhTienTraLai() {
		return tienKHDua - tinhTienThanhToan();
	}
	
	public int tinhDiemTL() {
		return (int) (tinhTongTien() / 1000 * 10);
	}

	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD);
	}
}
