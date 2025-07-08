CREATE DATABASE QuanLyDatBanNhaHangKarem
GO

USE QuanLyDatBanNhaHangKarem
GO

/* Bảng Nhân viên*/
CREATE TABLE NhanVien (
    maNV VARCHAR(5) PRIMARY KEY CHECK (maNV LIKE 'NV[0-9][0-9][0-9]' OR maNV LIKE 'QL[0-9][0-9][0-9]'),
    tenNV NVARCHAR(100) NOT NULL,
	soCCCD NVARCHAR(12) NOT NULL CHECK (soCCCD LIKE '[0-9]%' AND LEN(soCCCD) = 12),
    hinhTheNV VARBINARY(MAX),
	sdtNV VARCHAR(10) NOT NULL CHECK (LEN(sdtNV) = 10 AND(sdtNV LIKE '03%' OR sdtNV LIKE '05%' OR sdtNV LIKE '07%' OR sdtNV LIKE '08%' OR sdtNV LIKE '09%')),
    emailNV VARCHAR(255) CHECK (emailNV IS NULL OR emailNV LIKE '_%@_%._%'),
	chucVu NVARCHAR(100) NOT NULL CHECK (chucVu IN (N'Quản lý', N'Nhân viên quầy')),
	ngayBatDauLV DATE NOT NULL,
	trangThaiLV BIT NOT NULL CHECK (trangThaiLV IN (0, 1)),
	gioiTinh NVARCHAR(4) NOT NULL CHECK (gioiTinh IN (N'Nam', N'Nữ')),
	diaChi NVARCHAR(255),
	ngaySinh DATE NOT NULL CHECK (ngaySinh <= DATEADD(YEAR, -18, GETDATE()))
);


INSERT INTO NhanVien
SELECT 'NV001', N'Nguyễn Thị Tuyết Lan','123456789012',BulkColumn,'0825678245', 'tuyetlannv@gmail.com', 
		N'Nhân viên quầy', '2022-01-01', 1, N'Nữ', N'An Giang', '2004-12-05'

FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhNhanVien\NV001_TuyetLan.jpg', SINGLE_BLOB) AS img;

INSERT INTO NhanVien
SELECT
    'NV002', N'Trần Minh Tú','987654321098', BulkColumn, '0932123456', 'minhtunv@gmail.com',
    N'Nhân viên quầy', '2023-02-15', 1, N'Nam', N'Đak Lak', '2004-03-20'
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhNhanVien\NV002_MinhTu.jpg', SINGLE_BLOB) AS img

INSERT INTO NhanVien
SELECT
    'NV003', N'Đỗ Minh Thư', '192837465000', BulkColumn, '0911222333', 'minhthunv@gmail.com',
    N'Nhân viên quầy', '2022-12-10', 1, N'Nữ', N'Bến Tre', '2004-01-01'
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhNhanVien\NV003_MinhThu.jpg', SINGLE_BLOB) AS img;

INSERT INTO NhanVien
SELECT
    'QL001', N'Phan Tôn Lộc Nguyên', '192837465000', BulkColumn, '0911222333', 'locnguyennv@gmail.com',
    N'Quản lý', '2022-01-10', 1, N'Nam', N'Long An', '2004-08-06'
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhNhanVien\QL001_LocNguyen.jpg', SINGLE_BLOB) AS img;

GO 

CREATE TABLE TaiKhoan (
    tenTaiKhoan VARCHAR(5) PRIMARY KEY,
    matKhau VARBINARY(64) NOT NULL,
	maNV VARCHAR(5) NOT NULL,
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE CASCADE
);

INSERT INTO TaiKhoan
VALUES ('NV001', HASHBYTES('SHA2_512', '05122004'), 'NV001'),
		('NV002', HASHBYTES('SHA2_512', '20032004'), 'NV002'),
		('NV003', HASHBYTES('SHA2_512', '01012004'), 'NV003'),
		('QL001', HASHBYTES('SHA2_512', '06082004'), 'QL001');
GO


/* Bảng ca làm việc */
CREATE TABLE CaLamViec (
    maCaLV VARCHAR(4) PRIMARY KEY CHECK (maCaLV IN ('CA01', 'CA02', 'CA03')),
    tenCaLV NVARCHAR(100) NOT NULL,
	thoiGianBatDau TIME NOT NULL,
	thoiGianKetThuc TIME NOT NULL,
	trangThai BIT NOT NULL,
	CHECK (thoiGianKetThuc > thoiGianBatDau)
);
GO

INSERT INTO CaLamViec
VALUES ('CA01', 'Ca sáng', '06:00:00.000', '14:00:00.000', 1),
		('CA02', 'Ca chiều', '14:00:00.000', '18:00:00.000', 1),
		('CA03', 'Ca tối', '18:00:00.000', '22:00:00.000', 1);
GO

CREATE TABLE ChiTietCaLamViec (
    maCaLV VARCHAR(4) NOT NULL CHECK (maCaLV IN ('CA01', 'CA02', 'CA03')),
	maNV VARCHAR(5) NOT NULL CHECK (maNV LIKE 'NV[0-9][0-9][0-9]' OR maNV LIKE 'QL[0-9][0-9][0-9]'),
	gioBatDauThucTe DATETIME,
	gioKetThucThucTe DATETIME,
	soTienBanDau DECIMAL(18,2),
	ghiChu NVARCHAR(255),
	soTienCuoiCa DECIMAL(18, 2),
	PRIMARY KEY (maCaLV, maNV),
	FOREIGN KEY (maCaLV) REFERENCES CaLamViec(maCaLV) ON DELETE CASCADE,
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE CASCADE,
	CHECK (gioKetThucThucTe > gioBatDauThucTe)
);
GO

INSERT INTO ChiTietCaLamViec
VALUES	('CA01', 'NV001', '2025-05-26 06:00:00', '2025-05-26 14:00:00', 1000000.00, N'Làm đúng giờ', 1500000.00),
		('CA02', 'NV002', '2025-05-26 14:00:00', '2025-05-26 18:00:00', 2000000.00, N'Bán tốt', 2500000.00),
		('CA03', 'NV003', '2025-05-26 18:00:00', '2025-05-26 22:00:00', 500000.00, N'Thanh toán cuối ca', 750000.00);

GO



CREATE TABLE KhachHang (
    maKH VARCHAR(5) NOT NULL PRIMARY KEY CHECK (maKH LIKE 'KH[0-9][0-9][0-9]'),
    tenKH NVARCHAR(100) NOT NULL,
	sdtKH VARCHAR(10) NOT NULL CHECK (LEN(sdtKH) = 10 AND(sdtKH LIKE '03%' OR sdtKH LIKE '05%' OR sdtKH LIKE '07%' OR sdtKH LIKE '08%' OR sdtKH LIKE '09%')),
    emailKH VARCHAR(255) CHECK (emailKH IS NULL OR emailKH LIKE '_%@_%._%'),
	ghiChuKH NVARCHAR(255),
	diemTL INT NOT NULL CHECK (diemTL >= 0)
);

INSERT INTO KhachHang
VALUES ('KH001', N'Nguyễn Văn An', '0912345678', 'an.nguyen@gmail.com', N'Khách thân thiết', 150),
		('KH002', N'Nguyễn Thị Bình', '0376543210', NULL, NULL, 0),
		('KH003', N'Phạm Minh Châu', '0855512345', 'chaupm@outlook.com', N'Thay đổi số điện thoại gần đây', 45),
		('KH004', N'Trần Quốc Dũng', '0987654321', 'dung.tran@gmail.com', N'Vừa đăng ký thành viên mới', 10);

GO

CREATE TABLE LoaiMon (
    maLoai VARCHAR(4) PRIMARY KEY CHECK (maLoai LIKE 'LM[0-9][0-9]'),
	tenLoai NVARCHAR(100) NOT NULL
);

INSERT INTO LoaiMon
VALUES ('LM01', N'Điểm tâm'),
		('LM02', N'Soup-Tiềm-Cháo'),
		('LM03', N'Gỏi'),
		('LM04', N'Hải sản'),
		('LM05', N'Gà-Bò-Heo-Ếch-Lươn'),
		('LM06', N'Mì-Miến-Hủ tiếu xào'),
		('LM07', N'Lẩu-Cơm'),
		('LM08', N'Canh-Rau'),
		('LM09', N'Món Thêm'),
		('LM10', N'Sinh tố-Nước ép');

GO

CREATE TABLE MonAn (
    maMonAn VARCHAR(5) PRIMARY KEY CHECK (maMonAn LIKE 'MA[0-9][0-9][0-9]'),
	tenMonAn NVARCHAR(100) NOT NULL,
	moTa NVARCHAR(255),
	donGia FLOAT(53) NOT NULL CHECK (donGia > 0),
	hinhMonAn VARBINARY(MAX),
	ngayCapNhat DATETIME DEFAULT GETDATE() NOT NULL CHECK (ngayCapNhat <= GETDATE()),
	thueMon FLOAT(53) NOT NULL CHECK (thueMon BETWEEN 0 AND 1),
	maLoai VARCHAR(4) NOT NULL,
	trangThai INT NOT NULL CHECK (trangThai IN (0, 1)),
	FOREIGN KEY (maLoai) REFERENCES LoaiMon(maLoai) ON DELETE CASCADE
);


INSERT INTO MonAn
SELECT 'MA001', N'Hủ Tiếu Cá', N'Hủ tiếu với Cá', 92000, BulkColumn, GETDATE(), 0.1, 'LM01', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\ĐIỂM TÂM\Hủ_Tiếu_Cá.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA002', N'Hủ Tiếu Gà', N'Hủ tiếu với Gà', 92000, BulkColumn, GETDATE(), 0.2, 'LM01', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\ĐIỂM TÂM\Hủ_Tiếu_Gà.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA003', N'Cháo Bồ Câu', NULL, 204000, BulkColumn, GETDATE(), 0.1, 'LM02', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\SOUP - TIỀM - CHÁO\Cháo_Bồ_Câu_(Cắt_Mếng).jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA004', N'Cháo Cá Lóc', NULL, 218000, BulkColumn, GETDATE(), 0.3, 'LM02', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\SOUP - TIỀM - CHÁO\Cháo_Cá_Lóc.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA005', N'Gỏi Xoài Khô Sặc', NULL, 183000, BulkColumn, GETDATE(), 0.1, 'LM03', 0
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Xoài_Khô_Sặc_Bổi.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA006', N'Salad Trộn', NULL, 86000, BulkColumn, GETDATE(), 0.1, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Salad_Trộn.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA007', N'Gỏi Củ Hũ Dừa Tôm', NULL, 193000, BulkColumn, GETDATE(), 0.1, 'LM03', 0
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Củ_Hũ_Dừa_Tôm_Thịt.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA008', N'Gỏi Hải Sản Thái Lan', NULL, 211000, BulkColumn, GETDATE(), 0.14, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Hải_Sản_Thái_Lan.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA009', N'Gỏi Bò Nướng Mè', NULL, 204000, BulkColumn, GETDATE(), 0.1, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gòi_Bò_Nướng_Mè.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA010', N'Gỏi Ngó Sen Tôm', NULL, 172000, BulkColumn, GETDATE(), 0.2, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Ngó_Sen_Tôm_Thịt.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA011', N'Rau Càng Cua Trộn', NULL, 96000, BulkColumn, GETDATE(), 0.15, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Rau_Càng_Cua_trộn.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA012', N'Gỏi Bò Một Nắng Đu Đủ', NULL, 193000, BulkColumn, GETDATE(), 0.12, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Bò_Một_Nắng_Đu_Đủ.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA013', N'Gỏi Tép Rong Bông So Đũa', NULL, 161000, BulkColumn, GETDATE(), 0.13, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Tép_Rong_Bông_So_Đũa.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA014', N'Gỏi Gà Tre Rau Răm', NULL, 421000, BulkColumn, GETDATE(), 0.2, 'LM03', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GỎI\Gỏi_Gà_Tre_Rau_Răm_(_01_con).jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA015', N'Nghêu Hấp Thái', NULL, 161000, BulkColumn, GETDATE(), 0.15, 'LM04', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\HẢI SẢN\Nghêu_Hấp_Thái.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA016', N'Mực Xào Hành Cần', N'Mực xào thơm ngon cay nồng', 161000, BulkColumn, GETDATE(), 0.1, 'LM04', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\HẢI SẢN\Mực_Xào_Hành_Cần.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA017', N'Bò Lúc Lắc', N'Thịt bò mềm sốt đậm đà', 193000, BulkColumn, GETDATE(), 0.12, 'LM05', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GÀ - BÒ - HEO - ẾCH - LƯƠN\Bò_Lúc_Lắc.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA018', N'Gà Xào Rừng', NULL, 139000, BulkColumn, GETDATE(), 0.1, 'LM05', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GÀ - BÒ - HEO - ẾCH - LƯƠN\Gà_Xào_Gừng.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA019', N'Ếch Chiên Bơ', NULL, 150000, BulkColumn, GETDATE(), 0.1, 'LM05', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\GÀ - BÒ - HEO - ẾCH - LƯƠN\Ếch_Chiên_Bơ.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA020', N'Nui Sốt Bò Bằm', NULL, 139000, BulkColumn, GETDATE(), 0.1, 'LM06', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÌ - MIẾN - HỦ TIẾU XÀO\Nui_Sốt_Bò_Bằm.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA021', N'Mì Xào Giòn', N'Mì xào giòn với rau củ', 129000, BulkColumn, GETDATE(), 0.12, 'LM06', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÌ - MIẾN - HỦ TIẾU XÀO\Mì_Xào_Giòn.png', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA022', N'Cơm Chiên Trái Thơm', NULL, 161000, BulkColumn, GETDATE(), 0.2, 'LM07', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\LẨU - CƠM\Cơm_Chiên_Trái_Thơm.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA023', N'Lẩu Hải Sản Thái Lan', N'Lẩu thanh đạm kiểu Nhật với đậu hũ, nấm', 46300, BulkColumn, GETDATE(), 0.1, 'LM07', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\LẨU - CƠM\Lẩu_Hải_Sản_Thái_Lan.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA024', N'Cơm Chiên Tôm Tươi', N'Cơm chiên trứng, lạp xưởng, rau củ', 150000, BulkColumn, GETDATE(), 0.15, 'LM07', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\LẨU - CƠM\Cơm_Chiên_Tôm_Tươi.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA025', N'Canh Khoai Mỡ', NULL, 139000, BulkColumn, GETDATE(), 0.1, 'LM08', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\CANH - RAU\Canh_Khoai_Mỡ.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA026', N'Cải Bó Xôi Xào', N'Món rau dân dã, thơm lừng mùi tỏi', 107000, BulkColumn, GETDATE(), 0.1, 'LM08', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\CANH - RAU\Cải_Bó_Xôi_Xào.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA027', N'Khăn Lạnh', NULL, 5000, BulkColumn, GETDATE(), 0.1, 'LM09', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÓN THÊM\khan-uot-nha-hang-1024x578.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA028', N'Bia', NULL, 25000, BulkColumn, GETDATE(), 0.1, 'LM09', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÓN THÊM\bia.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA029', N'Nước Suối', NULL, 15000, BulkColumn, GETDATE(), 0.1, 'LM09', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÓN THÊM\Nuoc_Suoi.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA030', N'Sting', NULL, 20000, BulkColumn, GETDATE(), 0.1, 'LM09', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\MÓN THÊM\Nuoc_tang_luc.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA031', N'Sinh Tố Bơ', NULL, 65000, BulkColumn, GETDATE(), 0.05, 'LM10', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\SINH TỐ - NƯỚC ÉP\Sinh_Tố_Bơ.jpg', SINGLE_BLOB) AS ImageFile;

INSERT INTO MonAn
SELECT 'MA032', N'Nước Ép Dưa Hấu', N'Giải khát mát lạnh, giàu vitamin C', 54000, BulkColumn, GETDATE(), 0.05, 'LM10', 1
FROM OPENROWSET(BULK N'D:\HK2-Year3\PTUD\DatBan_NhaHang\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKarem\QuanLyDatBanNhaHangKaremEclipse\resource\hinhMonAn\SINH TỐ - NƯỚC ÉP\Nước_Ép_Dưa_Hấu.png', SINGLE_BLOB) AS ImageFile;
GO

CREATE TABLE KhuVuc (
    maKV VARCHAR(4) PRIMARY KEY CHECK (maKV LIKE 'KV%'),
    tenKV NVARCHAR(50) NOT NULL UNIQUE,
	trangThai BIT NOT NULL
);

INSERT INTO KhuVuc
VALUES ('KV01', N'Ngoài trời', 1),
		('KV02', N'Tầng 1', 1),
		('KV03', N'Tầng 2', 0),
		('KV04', N'Sân thượng', 1);

GO
CREATE TABLE Ban (
    maBan VARCHAR(7) PRIMARY KEY,
    soLuongGhe INT NOT NULL CHECK (soLuongGhe > 0),
	trangThai INT NOT NULL CHECK (trangThai IN (1, 2, 3, 4)), --1: bàn trống 2: có khách 3: đã đặt  4: bảo trì, sửa chữa
	maKV VARCHAR(4) NOT NULL,
	FOREIGN KEY (maKV) REFERENCES KhuVuc(maKV) ON DELETE CASCADE,
);

INSERT INTO Ban
VALUES ('KV01B01', 2, 1, 'KV01'),
		('KV01B02', 2, 1, 'KV01'),
		('KV01B03', 4, 4, 'KV01'),
		('KV01B04', 8, 1, 'KV01'),
		('KV01B05', 4, 4, 'KV01'),
		('KV02B01', 2, 1, 'KV02'),
		('KV02B02', 4, 1, 'KV02'),
		('KV02B03', 8, 1, 'KV02'),
		('KV02B04', 2, 1, 'KV02'),
		('KV02B05', 4, 1, 'KV02'),
		('KV02B06', 8, 1, 'KV02'),
		('KV02B07', 12, 1, 'KV02'),
		('KV03B01', 4, 1, 'KV03'),
		('KV03B02', 8, 1, 'KV03'),
		('KV03B03', 12, 1, 'KV03'),
		('KV03B04', 2, 1, 'KV03'),
		('KV03B05', 2, 1, 'KV03'),
		('KV03B06', 4, 1, 'KV03'),
		('KV03B07', 8, 1, 'KV03'),
		('KV03B08', 4, 1, 'KV03'),
		('KV03B09', 8, 1, 'KV03');
GO

CREATE TABLE DonDatBanTruoc (
    maDonDatBanTruoc VARCHAR(20) PRIMARY KEY,
    thoiGianTao DATETIME DEFAULT GETDATE() NOT NULL,
	soLuongKhach INT NOT NULL CHECK (soLuongKhach > 0),
	thoiGianNhanBan DATETIME NOT NULL,
	tienCoc FLOAT(53) NOT NULL CHECK (tienCoc > 0),
	maKH VARCHAR(5) NOT NULL,
	maNV VARCHAR(5) NOT NULL,
	maBan VARCHAR(7) NOT NULL,
	trangThai int NOT NULL,
	FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
	FOREIGN KEY (maBan) REFERENCES Ban(maBan)
);

INSERT INTO DonDatBanTruoc (maDonDatBanTruoc, thoiGianTao, soLuongKhach, thoiGianNhanBan, tienCoc, maKH, maNV, maBan, trangThai) VALUES
('NV001CL01001', '2025-04-15', 4, '2025-06-10 12:31:45.520', 500000, 'KH001', 'NV001', 'KV01B01', 0),
('NV001CL01002', '2025-04-15', 3, '2025-06-11 12:32:55.147', 300000, 'KH002', 'NV002', 'KV02B06', 1),
('NV001CL01003', '2025-04-16', 5, '2025-06-12 12:36:13.510', 500000, 'KH001', 'NV001', 'KV03B09', 0);

GO

CREATE TABLE KhuyenMai (
    maKM VARCHAR(60) PRIMARY KEY,
    tenKM NVARCHAR(100) NOT NULL,
	noiDungKM NVARCHAR(255),
	giaTriKM FLOAT(53) NOT NULL CHECK (giaTriKM > 0),
	soLuong INT NOT NULL CHECK (soLuong >= 0),
	ngayBatDauKM DATE NOT NULL CHECK (ngayBatDauKM >= GETDATE()),
	ngayKetThucKM DATE NOT NULL,
	CHECK (ngayKetThucKM >= ngayBatDauKM)
);

INSERT INTO KhuyenMai
VALUES 
('KM001', N'Giảm giá cuối tháng', N'Giảm giá 10% cho tất cả đơn hàng dịp lễ', 0.1, 300, '2025-05-27', '2025-06-15'),
('KM002', N'Quốc tế Thiếu Nhi', N'Ưu đãi 20% cho khách hàng đặt bàn online', 0.2, 50, '2025-05-27', '2025-06-09'),
('KM003', N'Xuân đi Hè về', N'Giảm 15% cho hóa đơn có trẻ em dưới 12 tuổi', 0.15, 100, '2025-05-27', '2025-06-02');


GO

CREATE TABLE HoaDon (
    maHD VARCHAR(18) PRIMARY KEY,
    thoiGianTaoDon DATETIME DEFAULT GETDATE() NOT NULL,
	tienKHDua FLOAT(53) CHECK (tienKHDua >= 0),
	phuThu FLOAT(53) CHECK (phuThu >= 0),
	hinhThucThanhToan NVARCHAR(50),
	maBan VARCHAR(7),
	maDonDatBanTruoc VARCHAR(20),
	maKH VARCHAR(5),
	soKhach INT NOT NULL, 
	maNV VARCHAR(5) NOT NULL,
	maKM VARCHAR(60),
	thoiGianThanhToan DATETIME,
	trangThai int NOT NULL,
	chuThich NVARCHAR(50),
	diemTLSuDung INT,
	tongThanhToan FLOAT(53),
	FOREIGN KEY (maBan) REFERENCES Ban(maBan),
	FOREIGN KEY (maDonDatBanTruoc) REFERENCES DonDatBanTruoc(maDonDatBanTruoc),
	FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
	FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM)
);
GO

CREATE TABLE ChiTietHoaDon (
    maHD VARCHAR(18) NOT NULL,
    maMonAn VARCHAR(5) NOT NULL,
	soLuong INT NOT NULL CHECK (soLuong > 0),
	ghiChu NVARCHAR(255),
	PRIMARY KEY (maHD, MaMonAn),
	FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
	FOREIGN KEY (maMonAn) REFERENCES MonAn(maMonAn),
);
GO






