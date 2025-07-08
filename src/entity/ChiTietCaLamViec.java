package entity;

import java.time.LocalDateTime;

public class ChiTietCaLamViec {
    private CaLamViec caLamViec;
    private NhanVien nhanVien;
    private LocalDateTime gioBatDauThucTe;
    private LocalDateTime gioKetThucThucTe;
    private double soTienBanDau; 
    private double soTienCuoiCa; 
    private String ghiChu;   

    // Constructor đầy đủ
    public ChiTietCaLamViec(CaLamViec caLamViec, NhanVien nhanVien, LocalDateTime gioBatDauThucTe, 
            LocalDateTime gioKetThucThucTe, double soTienBanDau, double soTienCuoiCa, String ghiChu) {
        this.caLamViec = caLamViec;
        this.nhanVien = nhanVien;
        this.gioBatDauThucTe = gioBatDauThucTe;
        this.gioKetThucThucTe = gioKetThucThucTe;
        this.soTienBanDau = soTienBanDau;
        this.soTienCuoiCa = soTienCuoiCa;
        this.ghiChu = ghiChu;
    }
    
    // Constructor không có soTienCuoiCa (dùng cho constructor cũ)
    public ChiTietCaLamViec(CaLamViec caLamViec, NhanVien nhanVien, LocalDateTime gioBatDauThucTe, 
            LocalDateTime gioKetThucThucTe, double soTienBanDau, String ghiChu) {
        this(caLamViec, nhanVien, gioBatDauThucTe, gioKetThucThucTe, soTienBanDau, 0, ghiChu);
    }

    // Constructor không có gioKetThucThucTe (dùng khi ca chưa kết thúc)
    public ChiTietCaLamViec(CaLamViec caLamViec, NhanVien nhanVien, LocalDateTime gioBatDauThucTe, 
            double soTienBanDau, String ghiChu) {
        this(caLamViec, nhanVien, gioBatDauThucTe, null, soTienBanDau, 0, ghiChu);
    }

    // Getters and setters
    public CaLamViec getCaLamViec() {
        return caLamViec;
    }

    public void setCaLamViec(CaLamViec caLamViec) {
        this.caLamViec = caLamViec;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDateTime getGioBatDauThucTe() {
        return gioBatDauThucTe;
    }

    public void setGioBatDauThucTe(LocalDateTime gioBatDauThucTe) {
        this.gioBatDauThucTe = gioBatDauThucTe;
    }

    public LocalDateTime getGioKetThucThucTe() {
        return gioKetThucThucTe;
    }

    public void setGioKetThucThucTe(LocalDateTime gioKetThucThucTe) {
        this.gioKetThucThucTe = gioKetThucThucTe;
    }

    public double getSoTienBanDau() {
        return soTienBanDau;
    }

    public void setSoTienBanDau(double soTienBanDau) {
        this.soTienBanDau = soTienBanDau;
    }
    
    public double getSoTienCuoiCa() {
        return soTienCuoiCa;
    }

    public void setSoTienCuoiCa(double soTienCuoiCa) {
        this.soTienCuoiCa = soTienCuoiCa;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ChiTietCaLamViec{" +
                "maCaLV='" + caLamViec.getMaCaLV() + '\'' +
                ", maNV='" + nhanVien.getMaNV() + '\'' +
                ", gioBatDauThucTe=" + gioBatDauThucTe +
                ", gioKetThucThucTe=" + gioKetThucThucTe +
                ", soTienBanDau=" + soTienBanDau +
                ", soTienCuoiCa=" + soTienCuoiCa +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
