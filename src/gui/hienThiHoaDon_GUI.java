package gui;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.NhanVien_DAO;
import entity.NhanVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class hienThiHoaDon_GUI extends JFrame {

	private JTextField txtMaHD;
	private JTextField txtTenNV;
	private JTextField txtNgayLapHD;
	private JTextField txtSoBan;
	private DefaultTableModel tblModelHD;
	private JTable tbHD;
	private JTextField txtTongTien;
	private JComponent frame;
	private JTextField txtDiemTL;
	private JTextField txtKM;
	private JTextField txtthanhTien;
	private JLabel lblMaHD;
	private JLabel lblTenNV;
	private JLabel lblNgayLapHD;
	private JLabel lblSoBan;
	private HoaDon_GUI hoaDonGUI;
	private String maHD;
	private String maNV;
	private String maBan;
	private String thoiGianThanhToan;
	private JLabel lblTongTien;
	private JLabel lblDiemTL;
	private JLabel lblKM;
	private JLabel lblthanhTien;
	private String diemTL;
	private String tenKM;
	private double tienTT;
	private double tongTien;
	private static NhanVien_DAO NV_dao = new NhanVien_DAO();

	public hienThiHoaDon_GUI(HoaDon_GUI hoaDonGUI, String maHD, String maNV, String maBan, String thoiGianThanhToan, String diemTL, String tenKM, double tienTT, double tongTien) {
		setTitle("Hóa đơn");
		setSize(600, 700);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.WHITE);
		
		this.hoaDonGUI = hoaDonGUI;
		this.maHD = maHD;
		this.maNV = maNV;
		this.maBan = maBan;
		this.thoiGianThanhToan = thoiGianThanhToan;
		this.diemTL = diemTL;
		this.tenKM = tenKM;
		this.tienTT = tienTT;
		this.tongTien = tongTien;
		
		JPanel pnlTong = new JPanel(new BorderLayout());
		pnlTong.setBackground(Color.WHITE);
		
		JPanel pnlN = new JPanel(new BorderLayout());
		pnlN.setBackground(Color.WHITE);
		
		
		pnlTong.add(pnlN, BorderLayout.NORTH);

		JPanel pDiaChi;
		pnlN.add(pDiaChi = new JPanel(), BorderLayout.NORTH);
		pDiaChi.setLayout(new BoxLayout(pDiaChi, BoxLayout.Y_AXIS));
		pDiaChi.setBackground(Color.WHITE);

		JPanel panel1, panel2, panel3, panel4, panel5;
		panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		panel1.setBackground(Color.WHITE);
		panel2.setBackground(Color.WHITE);
		panel3.setBackground(Color.WHITE);
		panel4.setBackground(Color.WHITE);
		panel5.setBackground(Color.WHITE);

		panel1.add(new JLabel("24/4 Trương Quốc Dung, P8, Q Phú Nhuận"));

		JLabel lbNhaHang = new JLabel("Nhà Hàng Karem");
		panel2.add(lbNhaHang);

		panel3.add(new JLabel("Hóa Đơn Thanh Toán"));
		panel4.add(new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"));

		// Tạo JLabel chứa hình ảnh
		ImageIcon icon = new ImageIcon("resource\\icon\\logo01.png"); // Đường dẫn ảnh
		Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Chỉnh kích thước
		JLabel lbimg = new JLabel();
		lbimg.setIcon(new ImageIcon(img)); // Gắn icon vào label

		// Thêm ảnh dưới lbNhaHang
		panel5.add(lbimg);

		pDiaChi.add(Box.createVerticalStrut(10));
		pDiaChi.add(panel1);
		pDiaChi.add(Box.createVerticalStrut(-8));
		pDiaChi.add(panel2);
		pDiaChi.add(panel5);
		pDiaChi.add(Box.createVerticalStrut(-8));
		pDiaChi.add(panel3);
		pDiaChi.add(panel4);
		pDiaChi.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel pCenter;
		pnlN.add(pCenter = new JPanel(), BorderLayout.CENTER);
		
		pCenter.setLayout(new BorderLayout());
		pCenter.setBackground(Color.WHITE);
		JPanel pCNorth = new JPanel();
		pCenter.add(pCNorth, BorderLayout.NORTH);
		
		pCNorth.setLayout(new BoxLayout(pCNorth, BoxLayout.Y_AXIS));
		pCNorth.setBackground(Color.WHITE);
		JPanel p1, p2, p3, p4,p5;
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);
		p3.setBackground(Color.WHITE);
		p4.setBackground(Color.WHITE);
		p5.setBackground(Color.WHITE);
		
		JLabel lbl1, lbl2, lbl3, lbl4;
		p1.add(lbl1 = new JLabel("Mã hóa đơn:  "));
		p2.add(lbl2 = new JLabel("Nhân viên:  "));
		p3.add(lbl3 = new JLabel("Ngày lập:  "));
		p4.add(lbl4 = new JLabel("Bàn:  "));
		lbl2.setPreferredSize(lbl1.getPreferredSize());
		lbl3.setPreferredSize(lbl1.getPreferredSize());
		lbl4.setPreferredSize(lbl1.getPreferredSize());
		p1.add(lblMaHD = new JLabel());
		p2.add(lblTenNV = new JLabel());
		p3.add(lblNgayLapHD = new JLabel());
		p4.add(lblSoBan = new JLabel());
		lblMaHD.setBorder(null);
		lblTenNV.setBorder(null);
		lblNgayLapHD.setBorder(null);
		lblSoBan.setBorder(null);
		
		lblMaHD.setText(maHD);
		ArrayList<NhanVien> listNV = NV_dao.timKiemNV(maNV);
		for (NhanVien nv : listNV) {
			lblTenNV.setText(maNV);
		}
		lblNgayLapHD.setText(thoiGianThanhToan);
		lblSoBan.setText(maBan);
		
		pCNorth.add(Box.createVerticalStrut(10));
		pCNorth.add(p1);
		pCNorth.add(Box.createVerticalStrut(-5));
		pCNorth.add(p2);
		pCNorth.add(Box.createVerticalStrut(-5));
		pCNorth.add(p3);
		pCNorth.add(Box.createVerticalStrut(-5));
		pCNorth.add(p4);
		pCNorth.add(Box.createVerticalStrut(20));
		pCNorth.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel pnlC = new JPanel(new BorderLayout());
		pnlC.setBackground(Color.WHITE);
		JPanel pCCenter = new JPanel();
		pCCenter.setLayout(new BoxLayout(pCCenter, BoxLayout.Y_AXIS));
		String[] cols = {"STT", "Tên món", "Số lượng", "Đơn giá", "Thành Tiền"};
		tblModelHD = new DefaultTableModel(cols, 0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // Tất cả các ô đều không thể chỉnh sửa
		    }
		};
		tbHD = new JTable(tblModelHD);
		
		JTableHeader header = tbHD.getTableHeader();
		header.setBackground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 13));

		
		tbHD.setBorder(BorderFactory.createEmptyBorder());
		tbHD.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		tbHD.setShowGrid(false);
		tbHD.setRowSelectionAllowed(true);
		tbHD.setColumnSelectionAllowed(true);
		
		tbHD.setBackground(Color.WHITE);

		pCCenter.add(tbHD.getTableHeader(), BorderLayout.NORTH); 
		pCCenter.setBackground(Color.WHITE);
		pCCenter.add(tbHD); 
		
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tbHD.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbHD.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);  
		tbHD.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbHD.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tbHD.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		
		tbHD.setCellSelectionEnabled(false); // không cho chọn từng ô
		tbHD.setRowSelectionAllowed(false);  // không cho chọn dòng
		tbHD.setColumnSelectionAllowed(false); // không cho chọn cột

		
		DefaultTableModel modelFromHD= hoaDonGUI.getTableModel();
		for (int i = 0; i < modelFromHD.getRowCount(); i++) {
		    // Lấy tên món và ghi chú từ cột 1
			String tenMon = modelFromHD.getValueAt(i, 1).toString();

		    // Lấy số lượng từ cột 2
		    int soLuong = Integer.parseInt(modelFromHD.getValueAt(i, 2).toString());

		    // Lấy đơn giá từ cột 3
		    double donGia = Double.parseDouble(modelFromHD.getValueAt(i, 3).toString());

		    // Lấy thành tiền từ cột 4
		    double thanhTien = Double.parseDouble(modelFromHD.getValueAt(i, 4).toString().replace(",", ""));
		    
		    // Lấy ghi chú từ cột 5
		    String ghiChu = modelFromHD.getValueAt(i, 5).toString();
		    
		    // Thêm vào bảng hóa đơn
		    tblModelHD.addRow(new Object[]{i + 1, tenMon, soLuong, donGia, thanhTien});   
		    
		}
		
		pnlC.add(pCCenter, BorderLayout.CENTER);
		
		JPanel pnlTT = new JPanel();
		pnlTT.setBackground(Color.WHITE);
		pnlTT.setLayout(new BoxLayout(pnlTT, BoxLayout.Y_AXIS));
		lblTongTien = new JLabel();
		pnlTT.add(createLabelTextFieldRow("Tổng tiền:", lblTongTien, false, false));
		lblDiemTL = new JLabel();
		pnlTT.add(createLabelTextFieldRow("Điểm tích lũy:", lblDiemTL, false, false));
		lblKM = new JLabel();
		pnlTT.add(createLabelTextFieldRow("Khuyến mãi:", lblKM, false, false));
		lblthanhTien = new JLabel();
		pnlTT.add(createLabelTextFieldRow("Thành tiền:", lblthanhTien, false, false));
		
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String tongTienFormatted = currencyVN.format(tongTien);
		lblTongTien.setText(String.valueOf(tongTienFormatted));
		if (diemTL != null && !diemTL.trim().isEmpty()) {
		    try {
		        int diemSo = Integer.parseInt(diemTL);
		        String diemTLFormatted = currencyVN.format(diemSo);
		        lblDiemTL.setText(diemTLFormatted);
		    } catch (NumberFormatException e) {
		        System.err.println("Lỗi định dạng điểm tích lũy: " + diemTL);
		        lblDiemTL.setText(currencyVN.format(0)); 
		    }
		} else {
		    lblDiemTL.setText(currencyVN.format(0));
		}
//		String KMFormatted = currencyVN.format(KM);
		lblKM.setText(tenKM);
		String tienTTFormatted = currencyVN.format(tienTT);
		lblthanhTien.setText(tienTTFormatted);
		
		pnlC.add(pnlTT, BorderLayout.SOUTH);
		pnlTong.add(pnlC, BorderLayout.CENTER);
		
		
		JPanel pnlS = new JPanel();
		pnlS.setBackground(Color.WHITE);
		pnlS.setLayout(new BoxLayout(pnlS, BoxLayout.Y_AXIS));
		

		// Thêm viền 1px màu đen ở phía dưới
		pnlS.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));

		// Tạo các JLabel cho nội dung
		JLabel lb1 = new JLabel("Cảm ơn quý khách đã sử dụng dịch vụ.");
		JLabel lb2 = new JLabel("Hotline: 099999999");
		JLabel lb3 = new JLabel("Hẹn gặp lại quý khách!");

		// Căn giữa từng dòng
		lb1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb3.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Thêm khoảng cách giữa các dòng nếu cần
		pnlS.add(Box.createVerticalStrut(10));
		pnlS.add(lb1);
		pnlS.add(Box.createVerticalStrut(5));
		pnlS.add(lb2);
		pnlS.add(Box.createVerticalStrut(5));
		pnlS.add(lb3);
		pnlS.add(Box.createVerticalStrut(10));

		// Thêm pnlS vào BorderLayout.SOUTH
		pnlTong.add(pnlS, BorderLayout.SOUTH);


		
		pnlTong.setPreferredSize(new Dimension(580, 600)); // hoặc điều chỉnh cao tùy layout
		JScrollPane scrollPane = new JScrollPane(pnlTong,
		        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // cuộn mượt hơn
		add(scrollPane);

		
		
	}
	
	private JPanel createLabelTextFieldRow(String labelText, JLabel lblTongTien, boolean editable, boolean hasBorder) {
	    JPanel panel = new JPanel(new GridLayout(1,2)); // Khoảng cách giữa label và textfield
	    panel.setBackground(Color.WHITE);
//	    panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding cho panel
	    JPanel pnllb = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	    pnllb.setBackground(Color.WHITE);
	    JLabel label = new JLabel(labelText);
	    label.setFont(new Font("Arial", Font.BOLD, 13));
	    pnllb.add(label);
	    
	    panel.add(pnllb);

	    lblTongTien.setFont(label.getFont());

	    if (!hasBorder) {
	    	lblTongTien.setBorder(null);
	    }

	    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
	    textPanel.setBackground(Color.WHITE);
	    textPanel.setOpaque(false);
	    textPanel.add(lblTongTien);

	    panel.add(textPanel);

	    return panel;
	}

}

