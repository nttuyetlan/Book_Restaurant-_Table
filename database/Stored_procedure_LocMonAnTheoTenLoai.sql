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
CREATE PROCEDURE ma_LocMonAnTheoTenLoai
    @TenLoai NVARCHAR(100)
AS
BEGIN
    SELECT ma.maMonAn, ma.tenMonAn, ma.moTa, ma.donGia, ma.hinhMonAn, ma.ngayCapNhat, ma.thueMon, lma.maLoai
    FROM MonAn ma
    JOIN LoaiMon lma ON ma.maLoai = lma.maLoai
    WHERE lma.tenLoai = @TenLoai
END

EXEC ma_LocMonAnTheoTenLoai @TenLoai = N'Điểm tâm';
