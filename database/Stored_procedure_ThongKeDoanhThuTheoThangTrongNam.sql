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
CREATE PROCEDURE sp_ThongKeDoanhThuTheoThangTrongNam
    @Nam INT
AS
BEGIN
    SELECT 
        MONTH(thoiGianThanhToan) AS Thang,
        SUM(tongThanhToan) AS TongDoanhThu
    FROM HoaDon
    WHERE YEAR(thoiGianThanhToan) = @Nam
    GROUP BY MONTH(thoiGianThanhToan)
    ORDER BY Thang
END

GO
