-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_ThongKeDoanhThuTuanTrongThang
    @Thang INT,
    @Nam INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Tuần 1: ngày 1 - 7
    SELECT N'Tuần 1' AS Tuan, ISNULL(SUM(tongThanhToan), 0) AS DoanhThu
    FROM HoaDon
    WHERE MONTH(thoiGianThanhToan) = @Thang AND YEAR(thoiGianThanhToan) = @Nam AND DAY(thoiGianThanhToan) BETWEEN 1 AND 7

    UNION ALL

    -- Tuần 2: ngày 8 - 14
    SELECT N'Tuần 2', ISNULL(SUM(tongThanhToan), 0)
    FROM HoaDon
    WHERE MONTH(thoiGianThanhToan) = @Thang AND YEAR(thoiGianThanhToan) = @Nam AND DAY(thoiGianThanhToan) BETWEEN 8 AND 14

    UNION ALL

    -- Tuần 3: ngày 15 - 21
    SELECT N'Tuần 3', ISNULL(SUM(tongThanhToan), 0)
    FROM HoaDon
    WHERE MONTH(thoiGianThanhToan) = @Thang AND YEAR(thoiGianThanhToan) = @Nam AND DAY(thoiGianThanhToan) BETWEEN 15 AND 21

    UNION ALL

    -- Tuần 4: ngày 22 -> cuối tháng
    SELECT N'Tuần 4', ISNULL(SUM(tongThanhToan), 0)
    FROM HoaDon
    WHERE MONTH(thoiGianThanhToan) = @Thang AND YEAR(thoiGianThanhToan) = @Nam AND DAY(thoiGianThanhToan) >= 22
END

GO
