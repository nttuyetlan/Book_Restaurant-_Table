	CREATE PROCEDURE sp_ThemDatBanTruoc
    @maNV VARCHAR(5),
    @maCaLam VARCHAR(5),
    @soLuongKhach INT,
    @thoiGianNhanBan DATETIME,
    @tienCoc FLOAT,
    @maKH VARCHAR(5),
    @maBan VARCHAR(7)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maDatBan VARCHAR(20);
    DECLARE @prefix VARCHAR(10) = @maNV + @maCaLam;
    DECLARE @soThuTu INT;

    SELECT @soThuTu = MAX(CAST(SUBSTRING(maDatBan, LEN(@prefix) + 1, 3) AS INT))
    FROM DatBanTruoc
    WHERE maDatBan LIKE @prefix + '%';

    SET @soThuTu = ISNULL(@soThuTu, 0) + 1;

    SET @maDatBan = @prefix + RIGHT('000' + CAST(@soThuTu AS VARCHAR), 3);

    INSERT INTO DatBanTruoc (
        maDatBan, thoiGianTao, soLuongKhach, thoiGianNhanBan, tienCoc,
        maKH, maNV, maBan
    )
    VALUES (
        @maDatBan, GETDATE(), @soLuongKhach, @thoiGianNhanBan, @tienCoc,
        @maKH, @maNV, @maBan
    );

    SELECT @maDatBan AS maDatBanMoi;
END;

EXEC sp_ThemDatBanTruoc
    @maNV = 'NV001',
    @maCaLam = 'CL01',
    @soLuongKhach = 4,
    @thoiGianNhanBan = DATEADD(HOUR, 2, GETDATE()),
    @tienCoc = 300000,
    @maKH = 'KH001',
    @maBan = 'B01N01';
