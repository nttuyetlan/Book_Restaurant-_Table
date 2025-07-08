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
CREATE PROCEDURE sp_ThongKeDoanhThuTheoNamTuCombo
    @NamTu INT,
    @NamDen INT
AS
BEGIN
    ;WITH CTE_Years AS (
        SELECT @NamTu AS Nam
        UNION ALL
        SELECT Nam + 1
        FROM CTE_Years
        WHERE Nam + 1 <= @NamDen
    )
    SELECT 
        y.Nam,
        ISNULL(SUM(hd.tongThanhToan), 0) AS TongDoanhThu
    FROM CTE_Years y
    LEFT JOIN HoaDon hd ON YEAR(hd.thoiGianThanhToan) = y.Nam
    GROUP BY y.Nam
    ORDER BY y.Nam
    OPTION (MAXRECURSION 100)
END

GO
