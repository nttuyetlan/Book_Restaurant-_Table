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
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_ThongKeDoanhThu_TheoThoiGianBatDau_KetThuc
    @NgayBatDau DATE,
    @NgayKetThuc DATE
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        CONVERT(DATE, thoiGianThanhToan) AS NgayThanhToan,
        SUM(tongThanhToan) AS TongDoanhThu
    FROM 
        HoaDon
    WHERE 
        thoiGianThanhToan IS NOT NULL
        AND thoiGianThanhToan BETWEEN @NgayBatDau AND @NgayKetThuc
    GROUP BY 
        CONVERT(DATE, thoiGianThanhToan)
    ORDER BY 
        NgayThanhToan;
END
GO
