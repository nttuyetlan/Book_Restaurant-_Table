package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.DirectoryNotEmptyException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.MonAn_DAO;
import dao.NhanVien_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiMon;
import entity.MonAn;
import entity.NhanVien;

public class HoaDon_GUI extends JFrame implements ActionListener{
	
	private DatBanTrucTiep_GUI giaoDienTruoc;
	private JButton btnTroVe;
	private JLabel lblMaHoaDonValue;
	private JLabel lblNgayTaoValue;
	private JTextField txtSoDienThoai;
	private JTextField txtTenKhachHang;
	private JTextField txtDiaChi;
	private JComboBox cmbLoaiKhachHang;
	private JTextField txtTongTienHang;
	private JTextField txtDiemTichLuy;
	private JButton btnApDungDiem;
	private JTextField txtKhuyenMai;
	private JTextField txtTongTienThanhToan;
	private JTextField txtTongTienKhachTra;
	private ArrayList btnMenhGiaTien;
	private JTextField txtTienTraLai;
	private JButton btnThanhToan;
	private JLabel lblMaBanValue;
	private JButton btnSDT;
	private JLabel lblTenKHValue;
	private JLabel lblDiaChiValue;
	private JLabel lblDiemTLValue;
	private JLabel lblGhiChuValue;
	private JButton btnDiemTL;
	private JTextField txtTongTienKhachDua;
	private JTextField txtTongTienThoi;
	private JLabel lblTongTienHang;
	private JLabel lblTienTraLaiValue;
	private JLabel lblTongTienThanhToan;
	private JButton btnThemKH;
	private String maBan;
	
	private HoaDon_DAO hd_dao = new HoaDon_DAO();
	private MonAn_DAO ma_dao = new MonAn_DAO();
	private ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	private KhuyenMai_DAO km_dao = new KhuyenMai_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
	private Ban_DAO ban_dao = new Ban_DAO();
	private int trangThai;
	private Object rs;
	private JTextField txtNgayTaoValue;
	private JTable tblThongTinMon;
	private DefaultTableModel tblModel;
	private JTextField txtTenKH_Moi;
	private JLabel lblLoiTenKH;
	private JTextField txtSDTKH_Moi;
	private JLabel lblLoiSDTKH;
	private JTextField txtEmailKH_Moi;
	private JLabel lblLoiEmailKH;
	private JTextArea txaGhiChuKH_Moi;
	private JLabel lblDiemTichLuy;
	private String maKH;
	private JLabel lblTienDuaThemValue;
	private JButton btnXacNhan;
	private JPanel pnlMenhGia;
	private String maNV;
	private String maHD;
	private double tienTT;
	private JComboBox<String> cmbPTTT;
	private JLabel lblTenKH_Moi;
	private JLabel lblSDTKH_Moi;
	private JLabel lblEmailKH_Moi;
	private JLabel lblGCKH_Moi;
	private HoaDon hd;
	private String chuThich;
	private int diemTichLuy;
	private int diemTLCu;
	private Runnable loadBanList;
	private ArrayList<Ban> dsBan;
	private String labelDangChonKVBan;
	private HoaDon hoadon;
	private KhuyenMai kmTotNhat;
    private int soTienCoTheTru;
	private int soTienLe;
	private double tienTT_PhuThu;

	public HoaDon_GUI(DatBanTrucTiep_GUI giaoDienTruoc, String maHD, String maBan, int trangThai, String maNV, String chuThich, Runnable loadBanList, String labelDangChonKVBan, ArrayList<Ban> dsBan) {
		// TODO Auto-generated constructor stub
		setTitle("Hóa Đơn");
		
		this.giaoDienTruoc = giaoDienTruoc;
		this.maHD = maHD;
		this.maBan = maBan;
		this.trangThai = trangThai;
		this.maNV = maNV;
		this.chuThich = chuThich;
		this.loadBanList = loadBanList;
		this.labelDangChonKVBan = labelDangChonKVBan;
		this.dsBan = dsBan;
		
		JPanel pnlHeader = initHeader();
		pnlHeader.setPreferredSize(new Dimension(0, 50));
		add(pnlHeader, BorderLayout.NORTH);
		
		JPanel pnlCenter = initCenTer();
		pnlCenter.setPreferredSize(new Dimension(820, 0));
		add(pnlCenter, BorderLayout.WEST);
		
		
		JPanel pnlWest = initWest();
		pnlWest.setPreferredSize(new Dimension(700, 0));
		add(pnlWest, BorderLayout.EAST);
		
		capNhatMenhGiaTien();
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	

	private JPanel initWest() {
		// TODO Auto-generated method stub
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Thông tin khách hàng
        JPanel pnlKhachHang = new JPanel();
        pnlKhachHang.setLayout(new BoxLayout(pnlKhachHang, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Thông tin khách hàng");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        border.setTitleColor(new Color(255, 165, 0));
        pnlKhachHang.setBorder(border);

        JPanel pnlSDT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSDT;
		pnlSDT.add(lblSDT = new JLabel("Số điện thoại:"));
		lblSDT.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        pnlSDT.add(txtSoDienThoai = new JTextField(25));
        btnSDT = new JButton("Tìm");
        btnSDT.setFocusPainted(false);
        btnSDT.addActionListener(this);
        btnSDT.setForeground(Color.BLACK);
        pnlSDT.add(btnSDT);
        pnlKhachHang.add(pnlSDT);		

        JPanel pnlTenKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTenKH;
		pnlTenKH.add(lblTenKH = new JLabel("Tên khách hàng:"));
        lblTenKH.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        pnlTenKH.add(lblTenKHValue = new JLabel());
        pnlKhachHang.add(pnlTenKH);

        JPanel pnlGhiChu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblGhiChu;
        pnlGhiChu.add(lblGhiChu = new JLabel("Ghi chú:"));
        lblGhiChu.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		pnlGhiChu.add(lblGhiChuValue = new JLabel());
        pnlKhachHang.add(pnlGhiChu);
        
        JPanel pnlThemKH = new JPanel(new FlowLayout(FlowLayout.LEFT));   
        btnThemKH = new JButton("Thêm khách hàng");
        btnThemKH.setFocusPainted(false);
        btnThemKH.setForeground(Color.BLACK);
        pnlThemKH.add(btnThemKH);
        btnThemKH.addActionListener(this);
        pnlKhachHang.add(pnlThemKH);

        pnlMain.add(pnlKhachHang);
        pnlMain.add(Box.createVerticalStrut(15));

        // Thông tin thanh toán
        JPanel pnlThanhToan = new JPanel();
        pnlThanhToan.setLayout(new BoxLayout(pnlThanhToan, BoxLayout.Y_AXIS));
        TitledBorder border1 = BorderFactory.createTitledBorder("Thông tin thanh toán");
        border1.setTitleFont(new Font("Arial", Font.BOLD, 16));
        border1.setTitleColor(new Color(255, 165, 0));
        pnlThanhToan.setBorder(border1);

        JPanel pnlTongTienHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl1;
		pnlTongTienHang.add(lbl1 = new JLabel("Tổng tiền hóa đơn:"));
		lbl1.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        pnlTongTienHang.add(lblTongTienHang = new JLabel());
        lbl1.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
        pnlThanhToan.add(pnlTongTienHang);
        
        // Xử lý tổng tiền hóa đơn
		NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String formatted = vndFormat.format(hoadon.tinhTongTien());
		lblTongTienHang.setText(formatted);
		
		// Phụ thu
		JPanel pnlPhuThu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhuThu = new JLabel("Phụ thu:");
		lblPhuThu.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		lblPhuThu.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
		pnlPhuThu.add(lblPhuThu);

		String[] loaiPhuThu = {
		    "Phí phụ thu dịp lễ", "Phí vệ sinh", "Phí đền bù hư hỏng / mất mát", "Không áp dụng phụ thu"
		};
		JComboBox<String> cbPhuThu = new JComboBox<>(loaiPhuThu);
		cbPhuThu.setEditable(true);
		pnlPhuThu.add(cbPhuThu);

		JTextField txtGiaPhuThu = new JTextField(10);
		txtGiaPhuThu.setEditable(false);
		JLabel lblGiaPhuThu;
		pnlPhuThu.add(lblGiaPhuThu = new JLabel("Giá:"));
		lblGiaPhuThu.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 15));
		pnlPhuThu.add(txtGiaPhuThu);

		Map<String, String> giaPhuThuMap = new HashMap<>();
		giaPhuThuMap.put("Phí phụ thu dịp lễ", String.format("%,.0f", hoadon.tinhTongTien() * 0.05));
		giaPhuThuMap.put("Phí vệ sinh", "50,000");
		giaPhuThuMap.put("Phí đền bù hư hỏng / mất mát", "100,000");
		giaPhuThuMap.put("Không áp dụng phụ thu", "0");

		// Sự kiện khi chọn/nhập loại phụ thu
		Runnable capNhatGiaPhuThu = () -> {
		    String loai = cbPhuThu.getEditor().getItem().toString().trim();

		    if (giaPhuThuMap.containsKey(loai)) {
		        String gia = giaPhuThuMap.get(loai);
		        txtGiaPhuThu.setText(gia);
		        txtGiaPhuThu.setEditable(false); 
		        hoadon.setPhuThu(Double.parseDouble(gia.replace(",", "")));
		        tienTT_PhuThu = hoadon.tinhTienThanhToan();
				NumberFormat vndFormat2 = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String formatted_PhuThu = vndFormat2.format(tienTT_PhuThu);
				lblTongTienThanhToan.setText(formatted_PhuThu);
				capNhatMenhGiaTien();
				
		    } else {
		        txtGiaPhuThu.setText("");
		        txtGiaPhuThu.setEditable(true); 
		        txtGiaPhuThu.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                try {
		                	double giaPhuThu;
		                	if (txtGiaPhuThu.getText().isEmpty()) {
		                        giaPhuThu = 0;
		                    }else {
		                    	String txt = txtGiaPhuThu.getText().replace(",", "").trim();
		                    	giaPhuThu = Double.parseDouble(txt);
		                    }

		                    hoadon.setPhuThu(giaPhuThu);
		                    double tienTT_PhuThu = hoadon.tinhTienThanhToan();

		                    NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		                    lblTongTienThanhToan.setText(vndFormat.format(tienTT_PhuThu));

		                    capNhatMenhGiaTien();
		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(null, "Giá phụ thu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        });
		    }
		};

		// Khi chọn từ danh sách
		cbPhuThu.setSelectedItem("");

		// Khi nhập tay
		JTextField editor = (JTextField) cbPhuThu.getEditor().getEditorComponent();
		editor.getDocument().addDocumentListener(new DocumentListener() {
		    public void insertUpdate(DocumentEvent e) { capNhatGiaPhuThu.run(); }
		    public void removeUpdate(DocumentEvent e) { capNhatGiaPhuThu.run(); }
		    public void changedUpdate(DocumentEvent e) { capNhatGiaPhuThu.run(); }
		});

		capNhatGiaPhuThu.run();
		pnlThanhToan.add(pnlPhuThu);
		
		// Điểm tích lũy
        JPanel pnlDiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl2;
		pnlDiem.add(lbl2 = new JLabel("Điểm tích lũy:"));
		lbl2.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		lbl2.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
        JPanel diemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        diemPanel.add(lblDiemTichLuy = new JLabel());
        lblDiemTichLuy.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20) );
        diemPanel.add(btnApDungDiem = new JButton("Áp dụng"));
        btnApDungDiem.setFocusPainted(false);
        btnApDungDiem.setEnabled(false);
        btnApDungDiem.setForeground(Color.BLACK);
        pnlDiem.add(diemPanel);
        pnlThanhToan.add(pnlDiem);

        btnApDungDiem.addActionListener(new ActionListener() {

			@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Bước 1: Lấy tổng tiền
                    String tongTienText = lblTongTienThanhToan.getText().replaceAll("[^\\d]", "");
                    int tongTien = Integer.parseInt(tongTienText);

                    // Bước 2: Lấy điểm tích lũy
                    int diemTichLuy = Integer.parseInt(lblDiemTichLuy.getText());

                    soTienLe = tongTien % 1000;

                    // Trừ phần lẻ nếu đủ điểm
                    if (soTienLe > 0 && diemTichLuy >= soTienLe) {
                        tongTien -= soTienLe;
                        diemTichLuy -= soTienLe;
                    }

                    // Trừ phần chẵn (bội số 1.000) nếu còn đủ điểm
                    int maxTruChia1000 = (diemTichLuy / 1000) * 1000;
                    soTienCoTheTru = Math.min(maxTruChia1000, tongTien);

                    diemTichLuy -= soTienCoTheTru;

                    // Cập nhật giao diện
                    hoadon.setDiemTLSuDung(soTienCoTheTru + soTienLe);
            		tienTT = hoadon.tinhTienThanhToan();
            		
                    NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    lblTongTienThanhToan.setText(vndFormat.format(tienTT));
                    lblDiemTichLuy.setText(String.valueOf(diemTichLuy));
                    capNhatMenhGiaTien();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi áp dụng điểm tích lũy.");
                }
            }
        });
        


        JPanel pnlKhuyenMai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl3;
		pnlKhuyenMai.add(lbl3 = new JLabel("Khuyến mãi:"));
		lbl3.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		lbl3.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
		
		JLabel lblKhuyenMai = new JLabel();
		pnlKhuyenMai.add(lblKhuyenMai);
		pnlThanhToan.add(pnlKhuyenMai);
		
		// Xử lý khuyến mãi
		kmTotNhat = km_dao.timKhuyenMaiTotNhat();
        if (kmTotNhat != null) {
            hoadon.setKM(kmTotNhat);
            lblKhuyenMai.setText(hoadon.getKM().getTenKM() + ": " + hoadon.getKM().getGiaTriKM() * 100 + "%");
        } else {
            hoadon.setKM(null);
            lblKhuyenMai.setText("Không có khuyến mãi nào");
		}

        JPanel pnlTongThanhToan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl4;
		pnlTongThanhToan.add(lbl4 = new JLabel("Tổng tiền thanh toán:"));
		lbl4.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        lbl4.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
        pnlTongThanhToan.add(lblTongTienThanhToan = new JLabel());
        pnlThanhToan.add(pnlTongThanhToan);
        
		int diemTichLuy = 0;
        if (!lblDiemTichLuy.getText().isEmpty()) {
            diemTichLuy = Integer.parseInt(lblDiemTichLuy.getText());
        }
        hoadon.setDiemTLSuDung(diemTichLuy);
		tienTT = hoadon.tinhTienThanhToan();
		NumberFormat vndFormat2 = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String formatted2 = vndFormat2.format(tienTT);
		lblTongTienThanhToan.setText(formatted2);
        
        JPanel pnlPTTT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl5;
        pnlPTTT.add(lbl5 = new JLabel("Phương thức thanh toán:"));
		lbl5.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        lbl5.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
        String[] phuongThucTT = {"Tiền mặt", "Chuyển khoản"};
        pnlPTTT.add(cmbPTTT = new JComboBox<String>(phuongThucTT));
        
        cmbPTTT.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selected = cmbPTTT.getSelectedItem().toString();
                    if (selected.equalsIgnoreCase("Tiền mặt")) {
                        txtTongTienKhachDua.setEditable(true);
                        txtTongTienKhachDua.setText("");
                        pnlMenhGia.setVisible(true);
                        btnThanhToan.setEnabled(false);
                    } else if (selected.equalsIgnoreCase("Chuyển khoản")) {
                    	String tongTienStr = lblTongTienThanhToan.getText();
                    	tongTienStr = tongTienStr.replaceAll("[^\\d]", "");

                    	double tongTien = Double.parseDouble(tongTienStr);
                        
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formattedTien = formatter.format(tongTien);

                        txtTongTienKhachDua.setText(formattedTien);
                        txtTongTienKhachDua.setEditable(false);
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        lblTienDuaThemValue.setText(currencyVN.format(0));
                        lblTienTraLaiValue.setText(currencyVN.format(0));
                        pnlMenhGia.setVisible(false);
                        btnThanhToan.setEnabled(true);
                        hoadon.setTienKHDua(tongTien);
                    }
                }
            }
        });


        pnlThanhToan.add(pnlPTTT);
        
        JPanel pnlKhachDua = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl6;
		pnlKhachDua.add(lbl6 = new JLabel("Tiền khách đưa:"));
		lbl6.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        lbl6.setPreferredSize(new Dimension(250, lbl1.getPreferredSize().height));
        pnlKhachDua.add(txtTongTienKhachDua = new JTextField(15));
        pnlKhachDua.add(btnXacNhan = new JButton("Xác nhận"));
        btnXacNhan.setFocusable(false);
        btnXacNhan.addActionListener(this);
        btnXacNhan.setForeground(Color.BLACK);
        pnlThanhToan.add(pnlKhachDua);

        pnlMain.add(pnlThanhToan);
        pnlMain.add(Box.createVerticalStrut(15));

        // Các mệnh giá tiền
        pnlMenhGia = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMenhGia.setPreferredSize(new Dimension(0, 60));
        btnMenhGiaTien = new ArrayList<>();
         // Lấy tổng tiền từ label
        String tongTienStr = lblTongTienThanhToan.getText().replaceAll("[^\\d]", "").trim();
        int tongTien = tongTienStr.isEmpty() ? 0 : Integer.parseInt(tongTienStr);


        // Làm tròn lên bội số gần nhất của 10.000
        int baseTien = (int) (Math.ceil(tongTien / 10000.0) * 10000);

        // Tạo các mệnh giá gợi ý: từ baseTien lên, mỗi bước +10,000, giới hạn 10 mức
        int soLuongGoiy = 10;
        for (int i = 0; i < soLuongGoiy; i++) {
            int gia = baseTien + i * 10_000;

            JButton btnGia = new JButton(String.format("%,d", gia));
            btnGia.setFocusPainted(false);

            btnGia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txtTongTienKhachDua.setText(String.format("%,d", gia));
                }
            });

            btnMenhGiaTien.add(btnGia);
            pnlMenhGia.add(btnGia);
        }

        // Cập nhật lại giao diện
        pnlMenhGia.revalidate();
        pnlMenhGia.repaint();

        pnlMain.add(pnlMenhGia);
        pnlMain.add(Box.createVerticalStrut(15));

        // Tiền trả lại và nút thanh toán
        JPanel pnlTraLai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTienTraLai = new JLabel("Tiền trả lại:");
        lblTienTraLaiValue = new JLabel();
        pnlTraLai.add(lblTienTraLai);
        pnlTraLai.add(lblTienTraLaiValue);
        pnlMain.add(pnlTraLai);
        
        // Tiền đưa thêm
        JPanel pnlDuaThem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDuaThem = new JLabel("Tiền đưa thêm:");
        lblTienDuaThemValue = new JLabel();
        pnlDuaThem.add(lblDuaThem);
        pnlDuaThem.add(lblTienDuaThemValue);
        pnlMain.add(pnlDuaThem);
        pnlMain.add(Box.createVerticalStrut(15));

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBackground(new Color(40, 167, 69));
        btnThanhToan.setForeground(new Color(255, 255, 255));
        btnThanhToan.setPreferredSize(new Dimension(200, 50));
        btnThanhToan.setEnabled(false);
        btnThanhToan.addActionListener(this);
        
        pnlButton.add(btnThanhToan);
        pnlMain.add(pnlButton);
		return pnlMain;
	}

	private void capNhatMenhGiaTien() {
	    pnlMenhGia.removeAll();
	    btnMenhGiaTien = new ArrayList<>();

	    String tongTienStr = lblTongTienThanhToan.getText().replaceAll("[^\\d]", "").trim();
	    if (tongTienStr.isEmpty()) return;

	    int tongTien = Integer.parseInt(tongTienStr);
	    int soNut = 10;

	    // Mệnh giá tiền thị trường
	    int[] menhGiaThiTruong = {1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000};

	    // Bắt đầu với giá gốc
	    List<Integer> danhSachGia = new ArrayList<>();
	    danhSachGia.add(tongTien);

	    int giaHienTai = tongTien;

	    int chiSoMenhGia = 0;
	    while (danhSachGia.size() < soNut && chiSoMenhGia < menhGiaThiTruong.length) {
	        int buoc = menhGiaThiTruong[chiSoMenhGia];

	        // Làm tròn lên số chia hết cho mệnh giá đó
	        int giaTiepTheo = (int) (Math.ceil(giaHienTai / (double) buoc) * buoc);

	        // Nếu khác giá hiện tại thì mới thêm vào
	        if (giaTiepTheo > giaHienTai) {
	            danhSachGia.add(giaTiepTheo);
	            giaHienTai = giaTiepTheo;
	        }

	        chiSoMenhGia++;
	    }

	    // Tạo các nút
	    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	    for (int gia : danhSachGia) {
	        final int giaFinal = gia;
	        JButton btnGia = new JButton(currencyVN.format(giaFinal));
	        btnGia.setFocusPainted(false);
	        btnGia.setForeground(Color.BLACK);

	        btnGia.addActionListener(e -> txtTongTienKhachDua.setText(String.format("%,d", giaFinal)));
	        

	        btnMenhGiaTien.add(btnGia);
	        pnlMenhGia.add(btnGia);
	    }

	    pnlMenhGia.revalidate();
	    pnlMenhGia.repaint();
	}




	private JPanel initCenTer() {
		// TODO Auto-generated method stub
		JPanel pnlMain = new JPanel(new BorderLayout());

        // Panel cho thông tin nhập liệu
        JPanel pnlPanel = new JPanel();
        JPanel pnlPanel0 = new JPanel();
        JPanel pnlPanel1 = new JPanel();
        JPanel pnlPanel2 = new JPanel();
        JPanel pnlPanel3 = new JPanel();
        pnlPanel.setLayout(new BoxLayout(pnlPanel, BoxLayout.Y_AXIS));
        JLabel lblTieuDe = new JLabel("Thông tin đơn");
        lblTieuDe.setForeground(new Color(255, 165, 0));
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel lblMaHoaDon = new JLabel("Mã hóa đơn:");
        lblMaHoaDonValue = new JLabel(); 
        JLabel lblMaBan = new JLabel("Mã bàn:");
        lblMaBanValue = new JLabel(); 
        JLabel lblNgayTao = new JLabel("Ngày tạo:");
        txtNgayTaoValue = new JTextField(50);
        
        lblMaBan.setPreferredSize(new Dimension(lblMaHoaDon.getPreferredSize().width, 20));
        lblNgayTao.setPreferredSize(new Dimension(lblMaHoaDon.getPreferredSize().width, 20));
        
        pnlPanel0.add(lblTieuDe);
        pnlPanel1.add(lblMaHoaDon);
        pnlPanel1.add(lblMaHoaDonValue);
        pnlPanel2.add(lblMaBan);
        pnlPanel2.add(lblMaBanValue);
//        pnlPanel3.add(lblNgayTao);
//        pnlPanel3.add(txtNgayTaoValue);
        
        pnlPanel.add(pnlPanel0);
        pnlPanel.add(pnlPanel1);
        pnlPanel.add(pnlPanel2);
        pnlPanel.add(pnlPanel3);

        pnlMain.add(pnlPanel, BorderLayout.NORTH);

        // Panel cho bảng thông tin món
        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JLabel lblThongTinMon = new JLabel("Thông tin món"); 
        lblThongTinMon.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        lblThongTinMon.setForeground(new Color(255, 165, 0));
        lblThongTinMon.setFont(new Font("Arial", Font.BOLD, 20));
        lblThongTinMon.setHorizontalAlignment(SwingConstants.CENTER);
        pnlForm.add(lblThongTinMon, BorderLayout.NORTH);

        String[] columnNames = {"STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền", "Ghi chú"};
        tblModel = new DefaultTableModel(columnNames, 0);
        tblThongTinMon = new JTable(tblModel);
        JScrollPane scrPane = new JScrollPane(tblThongTinMon);
        scrPane.setViewportView(tblThongTinMon);
        tblThongTinMon.setRowHeight(25);
        tblThongTinMon.setAutoCreateRowSorter(true);
        tblThongTinMon.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        pnlForm.add(scrPane, BorderLayout.CENTER);
        pnlMain.add(pnlForm, BorderLayout.CENTER);
		
        // Xử lý
        lblMaHoaDonValue.setText(maHD);
		lblMaBanValue.setText(maBan);
		
		DefaultTableModel modelFromDatBan = giaoDienTruoc.getTableModel();
//		double tongTien = 0;
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
		for (int i = 0; i < modelFromDatBan.getRowCount(); i++) {
		    // Lấy tên món và ghi chú từ cột 1
		    Object tenVaGhiChu = modelFromDatBan.getValueAt(i, 1); 
		    String current = tenVaGhiChu != null ? tenVaGhiChu.toString() : "";

		    String tenMon = "";
		    String ghiChu = "";

		    if (current.startsWith("<html>")) {
		        current = current.substring(6, current.length() - 7); // Bỏ <html> và </html>
		        String[] parts = current.split("<br>");
		        if (parts.length >= 1) tenMon = parts[0].trim();
		        if (parts.length >= 2) {
		            String ghiChuPart = parts[1]; // ví dụ: <i style='...'>ghi chú</i>
		            int tagOpen = ghiChuPart.indexOf(">");
		            int tagClose = ghiChuPart.indexOf("</i>");
		            if (tagOpen != -1 && tagClose != -1) {
		                ghiChu = ghiChuPart.substring(tagOpen + 1, tagClose).trim();
		            }
		        }
		    } else {
		        tenMon = current.trim();
		    }
		    
		    // Lấy số lượng từ cột 3
		    int soLuong = Integer.parseInt(modelFromDatBan.getValueAt(i, 3).toString());

		    // Lấy đơn giá từ cột 5
		    String donGiaStr = modelFromDatBan.getValueAt(i, 5).toString().replaceAll("[^\\d.]", "");
		    double donGia = Double.parseDouble(donGiaStr);

		    // Lấy thành tiền từ cột 6
		    String thanhTienStr = modelFromDatBan.getValueAt(i, 6).toString().replaceAll("[^\\d.]", "");
		    double thanhTien = Double.parseDouble(thanhTienStr);
		    DecimalFormat formatter = new DecimalFormat("#,###");
            String formattedTien = formatter.format(thanhTien);

		    // Thêm vào bảng hóa đơn
		    tblModel.addRow(new Object[]{i + 1, tenMon, soLuong, donGia, formattedTien, ghiChu});
		    String maMA = ma_dao.timMaMonAnTheoTen(tenMon);
		    ChiTietHoaDon cthd = new ChiTietHoaDon(
		            new HoaDon(maHD),
		            ma_dao.timTenMonAnVaGiaMonAnTheoMa(maMA),
		            soLuong,
		            ghiChu
		    );
		    dsCTHD.add(cthd);
		}
		if (hd_dao.timHoaDonTheoMaHoaDon(maHD) != null) {
			hoadon = hd_dao.timHoaDonTheoMaHoaDon(maHD);
		} else {
		    hoadon = new HoaDon(maHD);
		    hoadon.setSoKhach(1);
		}
		hoadon.setDanhSachChiTiet(dsCTHD);
		
		return pnlMain;
	}

	public HoaDon_GUI() {
		// TODO Auto-generated constructor stub
		
	}
	
	private JPanel initHeader() {
		// TODO Auto-generated method stub
		JPanel pnlHoaDon = new JPanel();
		pnlHoaDon.setLayout(new BorderLayout());
		
		ImageIcon iconTroVe = new ImageIcon("resource/icon/TroVe.png");
		Image img = iconTroVe.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
		btnTroVe = new JButton();
		btnTroVe.setIcon(new ImageIcon(img));
		btnTroVe.setPreferredSize(new Dimension(50, 50));
		btnTroVe.setBackground(new Color(30, 129, 191));
		btnTroVe.addActionListener(this);
		pnlHoaDon.add(btnTroVe, BorderLayout.WEST);
		
		JPanel tam = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		tam.setOpaque(true); 
		tam.setBackground(new Color(30, 129, 191));
		
		JLabel lblHoaDon;
		lblHoaDon = new JLabel("HÓA ĐƠN");
		lblHoaDon.setFont(new Font("Arial", Font.BOLD, 24));
		lblHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		lblHoaDon.setForeground(Color.WHITE);
		lblHoaDon.setAlignmentX(CENTER_ALIGNMENT);
		tam.add(lblHoaDon);
		
		pnlHoaDon.add(tam, BorderLayout.CENTER);
		pnlHoaDon.setBackground(new Color(30, 129, 191));
		pnlHoaDon.setOpaque(true);
		
		return pnlHoaDon;
	}


	
	public boolean kiemTraHopLeThongTinKH() {
		boolean hopLe = true;
		lblLoiTenKH.setText("");
		lblLoiSDTKH.setText("");
		lblLoiEmailKH.setText("");

	    // Kiểm tra tên khách hàng
	    String ten = txtTenKH_Moi.getText().trim();
	    if (ten.isEmpty()) {
	        lblLoiTenKH.setText("Tên khách hàng không được để trống.");
	        txtTenKH_Moi.requestFocus();
	        hopLe = false;
	    }

	    // Kiểm tra số điện thoại
	    String sdt = txtSDTKH_Moi.getText().trim();
	    if (sdt.isEmpty()) {
	        lblLoiSDTKH.setText("Số điện thoại không được để trống.");
	        txtSDTKH_Moi.requestFocus();
	        hopLe = false;
	    } else if (!sdt.matches("^(03|05|07|08|09)\\d{8}$")) {
	        lblLoiSDTKH.setText("Số điện thoại phải có một trong những đầu số 03, 05, 07, 08, 09 và phải đủ 10 số.");
	        txtSDTKH_Moi.requestFocus();
	        hopLe = false;
	    }

	    // Kiểm tra email
	    String email = txtEmailKH_Moi.getText().trim();
	    if (email.isEmpty()) {
	    	lblLoiEmailKH.setText("Email không được bỏ trống.");
	    	txtEmailKH_Moi.requestFocus();
	    	hopLe = false;
	    }else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	        lblLoiEmailKH.setText("Email không hợp lệ.");
	        txtEmailKH_Moi.requestFocus();
	        hopLe = false;
	    }

	    return hopLe;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o == btnTroVe) {
			giaoDienTruoc.setVisible(true); 
			dispose();
		}else if(o == btnSDT) {
			String text = txtSoDienThoai.getText().trim();
			if(text.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại khách hàng");
				return;
			}
			KhachHang kh = kh_dao.getKhachHangTheoSDTKhachHang(text);
			if(kh != null) {
				maKH = kh.getMaKH();
				lblTenKHValue.setText(kh.getTenKH());
				lblGhiChuValue.setText(kh.getGhiChuKH());
				String chuoi = Integer.toString(kh.getDiemTL());
				lblDiemTichLuy.setText(chuoi);
				btnApDungDiem.setEnabled(true);
			 }else
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
		}else if(o == btnThemKH) {
			JDialog dialog = new JDialog(HoaDon_GUI.this, "Thêm Khách Hàng Mới", true); 
            dialog.setSize(450, 430);
            dialog.setLocationRelativeTo(null); 
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            
            JPanel pnlTenKH = new JPanel();
			pnlTenKH.add(lblTenKH_Moi = new JLabel("Tên khách hàng:   "));
			lblTenKH_Moi.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    		pnlTenKH.add(txtTenKH_Moi = new JTextField(25));
    		JPanel pnlLoiTenKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		lblLoiTenKH = new JLabel(" "); 
    		lblLoiTenKH.setForeground(Color.RED);
    		lblLoiTenKH.setFont(new Font("Arial", Font.PLAIN, 11));
    		lblLoiTenKH.setPreferredSize(new Dimension(200, 10));
    		pnlLoiTenKH.add(lblLoiTenKH);
    		
    		
    		JPanel pnlSDTKH_Moi = new JPanel();
            pnlSDTKH_Moi.add(lblSDTKH_Moi = new JLabel("Số điện thoại:"));
            lblSDTKH_Moi.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            lblSDTKH_Moi.setPreferredSize(new Dimension(lblTenKH_Moi.getPreferredSize().width, 20));
			pnlSDTKH_Moi.add(txtSDTKH_Moi = new JTextField(25));
			JPanel pnlLoiSDTKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
			lblLoiSDTKH = new JLabel(" "); 
			lblLoiSDTKH.setForeground(Color.RED);
			lblLoiSDTKH.setFont(new Font("Arial", Font.PLAIN, 11));
			lblLoiSDTKH.setPreferredSize(new Dimension(420, 10));
			pnlLoiSDTKH.add(lblLoiSDTKH);
			
			
			JPanel pnlEmailKH_Moi = new JPanel();
            pnlEmailKH_Moi.add(lblEmailKH_Moi = new JLabel("Email khách hàng:"));
            lblEmailKH_Moi.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            lblEmailKH_Moi.setPreferredSize(new Dimension(lblTenKH_Moi.getPreferredSize().width, 20));
			pnlEmailKH_Moi.add(txtEmailKH_Moi = new JTextField(25));
			JPanel pnlLoiEmailKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
			lblLoiEmailKH = new JLabel(" "); 
			lblLoiEmailKH.setForeground(Color.RED);
			lblLoiEmailKH.setFont(new Font("Arial", Font.PLAIN, 11));
			lblLoiEmailKH.setPreferredSize(new Dimension(200, 10));
			pnlLoiEmailKH.add(lblLoiEmailKH);
			
    		
			JPanel pnlGCKH_Moi = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlGCKH_Moi.add(lblGCKH_Moi = new JLabel("Ghi chú:"));
            lblGCKH_Moi.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            lblGCKH_Moi.setPreferredSize(new Dimension(lblTenKH_Moi.getPreferredSize().width, 20));
            txaGhiChuKH_Moi = new JTextArea(5, 10);
            txaGhiChuKH_Moi.setLineWrap(true);
            txaGhiChuKH_Moi.setWrapStyleWord(true);
            JScrollPane scrGhiChuKH_Moi = new JScrollPane(txaGhiChuKH_Moi);
            scrGhiChuKH_Moi.setPreferredSize(new Dimension(250, 100));
            pnlGCKH_Moi.add(scrGhiChuKH_Moi);
            
            JPanel pnlButton = new JPanel();
            JButton btnLuuKH_Moi = new JButton("Lưu thông tin");
            JButton btnHuyKH_Moi = new JButton("Hủy bỏ");
            btnLuuKH_Moi.setFocusPainted(false);
            btnHuyKH_Moi.setFocusPainted(false);
            btnLuuKH_Moi.setBackground(new Color(13, 184, 50));
            btnHuyKH_Moi.setBackground(new Color(206, 8, 8));
            btnLuuKH_Moi.setForeground(Color.WHITE);
            btnHuyKH_Moi.setForeground(Color.WHITE);
            pnlButton.add(btnLuuKH_Moi);
            pnlButton.add(btnHuyKH_Moi);
            
            btnHuyKH_Moi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose(); 
                }
            });
            
            btnLuuKH_Moi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(kiemTraHopLeThongTinKH()) {
						String maKH = kh_dao.taoMaKhachHangTuDong();
						String tenKH = txtTenKH_Moi.getText().trim();
						String sdtKH = txtSDTKH_Moi.getText().trim();
						String emailKH = txtEmailKH_Moi.getText().trim();
						String ghiChuKH = txaGhiChuKH_Moi.getText().trim();
						int diemTL = 0;
						KhachHang kh = new KhachHang(maKH, tenKH, sdtKH, emailKH, ghiChuKH, diemTL);
						if (kh_dao.insertKhachHang(kh)) {
							JOptionPane.showMessageDialog(null, "Thêm Khách Hàng thành công!");
							lblTenKHValue.setText(tenKH);
							lblGhiChuValue.setText(ghiChuKH);
							lblDiemTichLuy.setText("0");
							txtSoDienThoai.setText(sdtKH);
						}else {
							JOptionPane.showMessageDialog(null, "Thêm Khách Hàng thất bại!");
						}
						dialog.dispose();
					}
				}
			});

			
            dialog.add(Box.createVerticalStrut(20));
            dialog.add(pnlTenKH);
            dialog.add(pnlLoiTenKH);
            dialog.add(Box.createVerticalStrut(20));
            dialog.add(pnlSDTKH_Moi);
            dialog.add(pnlLoiSDTKH);
            dialog.add(Box.createVerticalStrut(20));
            dialog.add(pnlEmailKH_Moi);
            dialog.add(pnlLoiEmailKH);
            dialog.add(Box.createVerticalStrut(20));
            dialog.add(pnlGCKH_Moi);
            dialog.add(Box.createVerticalStrut(20));
            dialog.add(pnlButton);
            dialog.setVisible(true);
        }else if(o == btnXacNhan) {   	
        	if (txtTongTienKhachDua.getText().isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Vui lòng nhập tiền khách đưa.");
		        return;
		    }
        	
            String tienKhachStr = txtTongTienKhachDua.getText().replace(",", "");;
            double tienKhachDua = Double.parseDouble(tienKhachStr);
            if (tienKhachDua <= 0) {
				JOptionPane.showMessageDialog(null, "Tiền khách đưa phải lớn hơn 0.");
				return;
			}
            hoadon.setTienKHDua(tienKhachDua);
            hoadon.setDiemTLSuDung(soTienCoTheTru + soTienLe);
        	double tienTraLai = hoadon.tinhTienTraLai();
            
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String tienTraLaiFormatted = currencyVN.format(tienTraLai);
            
            if(tienTraLai >= 0) {
            	lblTienTraLaiValue.setText(tienTraLaiFormatted);
            	lblTienDuaThemValue.setText(currencyVN.format(0));
            	btnThanhToan.setEnabled(true);
            }else {
            	String tienDuaThemFormatted = currencyVN.format(-tienTraLai);
                lblTienDuaThemValue.setText(tienDuaThemFormatted);
                lblTienTraLaiValue.setText(currencyVN.format(0));
                btnThanhToan.setEnabled(false);
            }
        }else if(o == btnThanhToan) {
            LocalDateTime now = LocalDateTime.now();
    		String thoiGianThanhToan = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    		hoadon.setHinhThucThanhToan(cmbPTTT.getSelectedItem().toString());
    		// Trường hợp khách ăn tại nhà hàng hoặc mang về
            if(lblMaBanValue.getText() != "") {
            	// Trường hợp khách vãng lai hoặc thành viên 
            	if(lblTenKHValue.getText() != "") {
            		ban_dao.capNhatTrangThaiBan(maBan, 1);
            		
                	diemTichLuy = hoadon.tinhDiemTL();
                	diemTLCu = Integer.parseInt(lblDiemTichLuy.getText());

            		KhachHang kh = kh_dao.getKhachHangTheoSDTKhachHang(txtSoDienThoai.getText());
            		kh.setDiemTL(diemTichLuy + diemTLCu);
            		kh_dao.capNhatDiemTichLuy(kh.getMaKH(), kh.getDiemTL());
            		
            		NhanVien nv = nv_dao.getNhanVienTheoMa(maNV);
            		hd = new HoaDon(hoadon.getMaHD(), now, hoadon.getTienKHDua(), hoadon.getPhuThu(), hoadon.getHinhThucThanhToan(), new Ban(hoadon.getBan().getMaBan()), null, kh, hoadon.getSoKhach(), nv, hoadon.getKM(), hoadon.getThoiGianTaoDon(), 1, chuThich, hoadon.getDiemTLSuDung(), hoadon.getTongThanhToan(), hoadon.getDanhSachChiTiet());
            		hd_dao.updateHoaDon(hd);
            	}
            	else {
            		ban_dao.capNhatTrangThaiBan(maBan, 1);
            		
            		NhanVien nv = nv_dao.getNhanVienTheoMa(maNV);
            		hd = new HoaDon(hoadon.getMaHD(), now, hoadon.getTienKHDua(), hoadon.getPhuThu(), hoadon.getHinhThucThanhToan(), new Ban(hoadon.getBan().getMaBan()), null, null, hoadon.getSoKhach(), nv, hoadon.getKM(), hoadon.getThoiGianTaoDon(), 1, chuThich, hoadon.getDiemTLSuDung(), hoadon.getTongThanhToan(), hoadon.getDanhSachChiTiet());
            		hd_dao.updateHoaDon(hd);
            		
            	}	
            }
            else {
            	if (lblTenKHValue.getText() != "") {   
					
            		diemTichLuy = hoadon.tinhDiemTL();
                	diemTLCu = Integer.parseInt(lblDiemTichLuy.getText());
                	
                	KhachHang kh = kh_dao.getKhachHangTheoSDTKhachHang(txtSoDienThoai.getText());
                	kh.setDiemTL(diemTichLuy + diemTLCu);
                	  	
            		kh_dao.capNhatDiemTichLuy(kh.getMaKH(), diemTichLuy + diemTLCu);
            		
            		NhanVien nv = nv_dao.getNhanVienTheoMa(maNV);
            		hd = new HoaDon(hoadon.getMaHD(), now, hoadon.getTienKHDua(), hoadon.getPhuThu(), hoadon.getHinhThucThanhToan(), null, null, kh, hoadon.getSoKhach(), nv, hoadon.getKM(), now, 1, chuThich, hoadon.getDiemTLSuDung(), hoadon.getTongThanhToan(), hoadon.getDanhSachChiTiet());
            		hd_dao.insertHoaDon(hd);
            	}else {
					
            		NhanVien nv = nv_dao.getNhanVienTheoMa(maNV);
            		hd = new HoaDon(hoadon.getMaHD(), now, hoadon.getTienKHDua(), hoadon.getPhuThu(), hoadon.getHinhThucThanhToan(), null, null, null, hoadon.getSoKhach(), nv, hoadon.getKM(), now, 1, chuThich, hoadon.getDiemTLSuDung(), hoadon.getTongThanhToan(), hoadon.getDanhSachChiTiet());
            		hd_dao.insertHoaDon(hd);
            	}
            }
            
            cthd_dao.deleteChiTietHoaDonTheoMaHD(hd.getMaHD());
         // Thêm món ăn vào chi tiết hóa đơn
            for (ChiTietHoaDon cthd : hd.getDanhSachChiTiet()) {
				cthd_dao.insertChiTietHoaDon(cthd);
			}
            
            String tenKM = "Không có khuyến mãi";
            if (hd.getKM() != null) {
				tenKM = hd.getKM().getTenKM();
			}
        	String diemTL1 = String.valueOf(diemTichLuy + diemTLCu);
        	String maBan = null;
        	if (hd.getBan() != null) {
        	    maBan = hd.getBan().getMaBan();
        	}
        	hienThiHoaDon_GUI hienThiHD = new hienThiHoaDon_GUI(this, hd.getMaHD(), maNV, maBan, thoiGianThanhToan, diemTL1, tenKM, hd.tinhTienThanhToan(), hd.tinhTongTien());

        	
        	hienThiHD.setVisible(true);
        	
        	// Tạo timer để trì hoãn 5 giây
        	new javax.swing.Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // a. Đóng giao diện cũ
                    giaoDienTruoc.dispose();
                    HoaDon_GUI.this.dispose();
                    
                    // b. Mở lại giao diện mới
                    SwingUtilities.invokeLater(() -> {
                        new DatBanTrucTiep_GUI();
                    });

                    // c. Đóng hóa đơn (nếu muốn)
                    hienThiHD.dispose();

                    // d. Dừng timer (nên làm)
                    ((javax.swing.Timer) e.getSource()).stop();
                }
            }).start();
        	    	
        }
	}
	
	public DefaultTableModel getTableModel() {
	    return (DefaultTableModel) tblThongTinMon.getModel();
	}

}
