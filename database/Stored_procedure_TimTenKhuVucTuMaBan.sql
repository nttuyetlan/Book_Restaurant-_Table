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
CREATE PROCEDURE sp_TimTenKhuVucTuMaBan
    @maBan NVARCHAR(10)
AS
BEGIN
    SELECT 
        b.maBan, b.soLuongGhe, b.trangThai AS trangThaiBan, 
        kv.maKV, kv.tenKV, kv.trangThai AS trangThaiKhuVuc
    FROM Ban b
    INNER JOIN KhuVuc kv ON b.maKV = kv.maKV
    WHERE b.maBan = @maBan
END




