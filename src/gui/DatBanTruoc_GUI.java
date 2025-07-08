package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;


import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Component;

import connectDB.ConnectDB;
import entity.*;
import dao.*;

public class DatBanTruoc_GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int widthSplitPane;
	private int heightSplitPane;
	private JLabel lblDonDatBanTruoc;
	private JLabel lblMaDon;
	private JTextField txtMaDon;
	private JMenu mnuDatBanTruoc;
	private JMenu mnuDatBanTrucTiep;
	private JMenu mnuGiaoCa;
	private JMenu mnuThongKe;
	private JMenu mnuQuanLyKhachHang;
	private JMenu mnuTimKiem;
	private JMenu mnuTroGiup;
	private JMenuItem mniDBTT1;
	private JMenuItem mniDBTT2;
	private JMenuItem mniDBT1;
	private JMenuItem mniDBT2;
	private JMenuItem mniMoCa;
	private JMenuItem mniKetCa;
	private JMenuItem mniThemKH;
	private JMenuItem mniCNKH;
	private JMenuItem mniTKDonDatBan;
	private JMenuItem mniTKDonDatMon;
	private JMenuItem mniTKBan;
	private JMenuItem mniTKMonAn;
	private JMenuItem mniKhuyenMai;
	private JMenuItem mniTKKH;
	private JMenuItem mniTKNV;
	private JMenuItem mniHDSD;
	private JMenuItem mniChungToi;
	private JMenuBar mnuFunction;
	private JTextField txtMaBan;
	private JTextField txtsoKH;
	private JTextField txtTenKH;
	private JTextField txtTongTien;
	private JTextField txtSĐTKH;
	private JLabel lblTimKiemKH;
	private JLabel lblThemKH;
	private JTextField txtKhuVuc;
	private JTextField txtPhong;
	private JTextField txtSLGhe;
	private JTextField txtTienCoc;
	private JTextField txtTTTenKH;
	private JTextField txtTTSLKH;
	private JTextField txtTTSDTKH;
	private JTextField txtTTEmailKH;
	private JTextField txtTTDMaBan;
	private JTextField txtTTDSKH;
	private JTextField txtTTDTenKH;
	private JTextField txtTTDTienCoc;
	private JTextField txtTTDTongTienMon;
	private JTextField txtTTDTongTienCoc;
	private JSplitPane splitPane;
	private JPanel pnlThongTin;
	private Component pnlMenuBanMonAn;
	private JPanel pnlCardBan;
//	private Object pnlMenuBanMonAn;
	private JPanel pnlCardMonAn;
	private ArrayList<Ban> dsBan;
	private ButtonGroup groupKVBan;
	private String labelDangChonKVBan = "Tất cả";
	private Ban_DAO ban_dao = new Ban_DAO();
	private KhuVuc_DAO khuVuc_dao = new KhuVuc_DAO();
//	private Phong_DAO phong_dao = new Phong_DAO();
	private Runnable loadBanList;
	private JPanel selectedPanel;
	private JButton btnTimKiemBanTheoSLKH;
	private JTextField txtSLKH;
	private JToggleButton btnTatCaMon;
	private JTextField txtTimMon;
	private JButton btnTimMon;
	private JToggleButton btnGoi;
	private JToggleButton btnDiemTam;
	private JToggleButton btnLauCom;
	private JToggleButton btnCanhRau;
	private JToggleButton btnHaiSan;
	private JToggleButton btnMonThem;
	private JToggleButton btnSoup;
	private JToggleButton btnGaBoHeoEch;
	private JToggleButton btnMiHuTieu;
	private JToggleButton btnNuocEp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectDB.getInstance().connect();
					DatBanTruoc_GUI frame = new DatBanTruoc_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public DatBanTruoc_GUI() {
		dsBan = new ArrayList<Ban>();
		setupFrame();
		initUI_Menu();
		setupSplitPane();
	}

	private void setupFrame() {
//		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource\\icon\\logo01.png"));
//		setAutoRequestFocus(false);
		setFont(new Font("Arial", Font.BOLD, 20));
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Đặt bàn trước");

	}

	private void setupSplitPane() {
		splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		widthSplitPane = splitPane.getPreferredSize().width;
		heightSplitPane = splitPane.getPreferredSize().height;
		pnlThongTin = createLeftPanelThongTin();
		pnlThongTin.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlMenuBanMonAn = new JPanel(new BorderLayout());
		pnlMenuBanMonAn = createRightPanelMenuMonAn();
		splitPane.setResizeWeight(0.5);
	}

// ======================================= Bên trái 
	private JPanel createLeftPanelThongTin() {
		JPanel pnlThongTin = new JPanel(new BorderLayout());
		pnlThongTin.setPreferredSize(new Dimension(widthSplitPane / 2 + 40, heightSplitPane));
		pnlThongTin.setBackground(new Color(128, 255, 128));
		splitPane.setLeftComponent(pnlThongTin);

//------Thanh  tiêu đề 
		JPanel pnlTTNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
		pnlTTNorth.setPreferredSize(new Dimension(0, 35));
		pnlTTNorth.setBackground(new Color(252, 233, 205));

		// Oval panel
		JPanel pnlOval = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.RED);
				g2d.fillOval(0, (getHeight() - 10) / 2, 20, 10);
			}
		};
		pnlOval.setPreferredSize(new Dimension(30, 30));
		pnlOval.setOpaque(false);
		pnlTTNorth.add(pnlOval);

		lblDonDatBanTruoc = new JLabel("Đơn đặt bàn trước");
		lblDonDatBanTruoc.setForeground(Color.RED);
		lblDonDatBanTruoc.setFont(new Font("Arial", Font.BOLD, 18));
		pnlTTNorth.add(lblDonDatBanTruoc);

		pnlTTNorth.add(Box.createRigidArea(new Dimension(20, 0)));

		lblMaDon = new JLabel("Mã đơn: ");
		lblMaDon.setForeground(Color.RED);
		lblMaDon.setFont(new Font("Arial", Font.BOLD, 18));
		pnlTTNorth.add(lblMaDon);

		txtMaDon = new JTextField(30);
		txtMaDon.setEnabled(false);
		txtMaDon.setEditable(false);
		txtMaDon.setBorder(null);
		txtMaDon.setOpaque(false);
		pnlTTNorth.add(txtMaDon);
//----- from thông tin và các nút button
		JPanel pnlRightCardThongTin = new JPanel(new CardLayout(0, 0));
		JPanel pnlCardTTBan = CardTTBan();
		pnlRightCardThongTin.add(pnlCardTTBan, "pnlCardTTBan");
		JPanel pnlCardTTMonAn = initUI_Left();
		pnlRightCardThongTin.add(pnlCardTTMonAn, "CardTTMonAn");

		pnlThongTin.add(pnlTTNorth, BorderLayout.NORTH);
		pnlThongTin.add(pnlRightCardThongTin, BorderLayout.CENTER);
		pnlThongTin.add(createPanelTTButton(), BorderLayout.SOUTH);

		return pnlThongTin;
	}

//------ Card Thông tin bàn 
	private JPanel CardTTBan() {
		JPanel pnlCardTTBan = new JPanel(new GridLayout(2, 1));

		JPanel pnlThongTinBan = new JPanel(new BorderLayout());

		JPanel pnlLabelTTBan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLabelTTBan.setBackground(new Color(30, 129, 191));
		pnlLabelTTBan.setPreferredSize(new Dimension(0, 35));
		pnlLabelTTBan.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel lblThongTinBan = new JLabel("Thông tin bàn ");
		lblThongTinBan.setFont(new Font("Arial", Font.BOLD, 18));
		lblThongTinBan.setForeground(Color.WHITE);
		pnlLabelTTBan.add(lblThongTinBan);
		pnlThongTinBan.add(pnlLabelTTBan, BorderLayout.NORTH);

		JPanel pnlCenterTTBan = new JPanel();
		pnlCenterTTBan.setLayout(new BoxLayout(pnlCenterTTBan, BoxLayout.Y_AXIS));

		txtMaBan = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Mã bàn:", txtMaBan, false, 90, 30, false));

		txtKhuVuc = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Khu vực:", txtKhuVuc, false, 80, 30, false));

		txtPhong = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Phòng:", txtPhong, false, 100, 30, false));

		txtSLGhe = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Số lượng ghế:", txtSLGhe, false, 50, 30, false));

		txtTienCoc = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Tiền cọc:", txtTienCoc, false, 90, 30, false));

		pnlThongTinBan.add(pnlCenterTTBan, BorderLayout.CENTER);

		JPanel pnlThongTinKhachHang = new JPanel(new BorderLayout());
		JPanel pnlLabelTTKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLabelTTKhachHang.setBackground(new Color(30, 129, 191));
		pnlLabelTTKhachHang.setPreferredSize(new Dimension(0, 35));
		pnlLabelTTKhachHang.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel lblThongTinKhachHang = new JLabel("Thông tin khách hàng ");
		lblThongTinKhachHang.setFont(new Font("Arial", Font.BOLD, 18));
		lblThongTinKhachHang.setForeground(Color.WHITE);
		pnlLabelTTKhachHang.add(lblThongTinKhachHang);
		pnlThongTinKhachHang.add(pnlLabelTTKhachHang, BorderLayout.NORTH);

		JPanel pnlCenterTTKH = new JPanel();
		pnlCenterTTKH.setLayout(new BoxLayout(pnlCenterTTKH, BoxLayout.Y_AXIS));

		txtTTTenKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("Tên khách hàng:", txtTTTenKH, true, 40, 30, true));

		txtTTSLKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("Số lượng khách hàng:", txtTTSLKH, true, 5, 30, true));

		txtTTSDTKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("SĐT khách hàng:", txtTTSDTKH, true, 40, 30, true));

		txtTTEmailKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("Email khách hàng:", txtTTEmailKH, true, 30, 30, true));

		pnlThongTinKhachHang.add(pnlCenterTTKH, BorderLayout.CENTER);

		pnlCardTTBan.add(pnlThongTinBan);
		pnlCardTTBan.add(pnlThongTinKhachHang);

		return pnlCardTTBan;
	}

//------ Card Thông tin món ăn 
	private JPanel initUI_Left() {
//	private void initUI_Left(JPanel pnlLeft) {
		// TODO Auto-generated method stub

		JPanel pnlLeft = new JPanel(new BorderLayout());
//		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));

		// Form thông tin món đặt
		String[] columnNames = { "", "Tên món ăn", "Số lượng", "Đơn giá", "Thành tiền", "" };
		Object[][] data = { { "❌", "Canh khoai mỡ", 1, 139000, 139000, "📝" },
				{ "❌", "Cơm đùi gà Rô Ti", 2, 183000, 366000, "📝" },
				{ "❌", "Cơm đùi gà Rô Ti", 2, 183000, 366000, "📝" } };

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable tblForm = new JTable(model);
		tblForm.setFocusable(false);
		tblForm.setDefaultEditor(Object.class, null);

		// Điều chỉnh kích thước các cột
		TableColumnModel columnModel = tblForm.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(150);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(10);

		// Tuỳ chỉnh giao diện cột "Số lượng"
		tblForm.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (isSelected) {
					c.setBackground(new Color(184, 207, 229)); // màu khi được chọn
				} else {
					c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(204, 229, 255)); // tô dòng chẵn/lẻ
				}

				return c;
			}
		});

		tblForm = new JTable(model) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				// Tô màu cả dòng chẵn/lẻ
				if (!isRowSelected(row)) {
					c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(204, 229, 255));
				} else {
					c.setBackground(new Color(30, 129, 191));
				}

				return c;
			}
		};

		// Tăng kích thước bảng
		tblForm.setFont(new Font("Arial", Font.PLAIN, 12));
		tblForm.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
		tblForm.setIntercellSpacing(new Dimension(5, 5));

		// Bỏ viền bảng
		tblForm.setShowGrid(false);
		tblForm.setIntercellSpacing(new Dimension());
		tblForm.setBorder(null);

		// Bỏ nền và viền header
		JTableHeader tbhForm = tblForm.getTableHeader();
		tbhForm.setOpaque(false);
		tbhForm.setBackground(new Color(255, 255, 255));
		tbhForm.setForeground(Color.BLUE);
		tbhForm.setFont(new Font("Arial", Font.BOLD, 13));
		tbhForm.setBorder(null);

		// Bỏ viền header toàn cục
		UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());

		// Canh tên thuộc tính và giá trị thẳng hàng
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tblForm.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblForm.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		tblForm.setPreferredScrollableViewportSize(new Dimension(500, 150));

		// Thêm bảng vào giao diện
		JScrollPane scrFrom = new JScrollPane();
		scrFrom.setViewportView(tblForm);
		tblForm.setRowHeight(35);
		tblForm.setAutoCreateRowSorter(true);
		tblForm.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrFrom.setBorder(null);
		scrFrom.setPreferredSize(new Dimension(pnlLeft.getWidth(), 300));

		JPanel pnlTTDonMonAn = new JPanel(new BorderLayout());
		JPanel pnlTTDonTien = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTTDonTien.setPreferredSize(new Dimension(0, 40));
		pnlTTDonTien.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		txtTTDTongTienMon = new JTextField();
		pnlTTDonTien.add(createLabelTextFieldRow("Tổng tiền món: ", txtTTDTongTienMon, false, 5, 13, false));
//		pnlTTDonTien.add(Box.createHorizontalStrut(10));
		txtTTDTongTienCoc = new JTextField();
		pnlTTDonTien.add(createLabelTextFieldRow("Tổng tiền cọc món: ", txtTTDTongTienCoc, false, 0, 14, false));

		pnlTTDonMonAn.add(scrFrom, BorderLayout.CENTER);
		pnlTTDonMonAn.add(pnlTTDonTien, BorderLayout.SOUTH);

		pnlLeft.add(pnlTTDonMonAn, BorderLayout.CENTER);

		// Form thông tin đơn
		JPanel pnlTTDon = new JPanel();
		pnlTTDon.setLayout(new BoxLayout(pnlTTDon, BoxLayout.Y_AXIS));

		JPanel pnlLabelTTDon = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLabelTTDon.setBackground(new Color(30, 129, 191));
		pnlLabelTTDon.setPreferredSize(new Dimension(0, 35));
		pnlLabelTTDon.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel lblTTDon = new JLabel("Thông tin đơn");
		lblTTDon.setFont(new Font("Arial", Font.BOLD, 16));
		lblTTDon.setForeground(Color.WHITE);
		pnlLabelTTDon.add(lblTTDon);
		pnlTTDon.add(pnlLabelTTDon);

		JPanel pnlTTDonMaBanSKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtTTDMaBan = new JTextField();
		pnlTTDonMaBanSKH.add(createLabelTextFieldRow("Mã bàn: ", txtTTDMaBan, false, 60, 15, false));
		txtTTDSKH = new JTextField();
		pnlTTDonMaBanSKH.add(createLabelTextFieldRow("Số khách hàng: ", txtTTDSKH, false, 3, 15, false));
		pnlTTDon.add(pnlTTDonMaBanSKH);

		JPanel pnlTTDonTenKHCoc = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtTTDTenKH = new JTextField();
		pnlTTDonTenKHCoc.add(createLabelTextFieldRow("Tên khách hàng: ", txtTTDTenKH, false, 0, 15, false));
		txtTTDTienCoc = new JTextField();
		pnlTTDonTenKHCoc.add(createLabelTextFieldRow("Tổng tiền cọc: ", txtTTDTienCoc, false, 10, 15, false));
		pnlTTDon.add(pnlTTDonTenKHCoc);

		pnlLeft.add(pnlTTDon, BorderLayout.SOUTH);
		return pnlLeft;
	}

	private JPanel createPanelTTButton() {
		JPanel pnlTTButton = new JPanel();
		pnlTTButton.setLayout(new BoxLayout(pnlTTButton, BoxLayout.Y_AXIS));

// Tìm khách hàng 
		JPanel pnlTimKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTimKhachHang.setPreferredSize(new Dimension(pnlTTButton.getWidth(), 35));
		JLabel lblSDTKH;
		pnlTimKhachHang.add(lblSDTKH = new JLabel("SĐT khách hàng:"));
		lblSDTKH.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		pnlTimKhachHang.add(txtSĐTKH = new JTextField(40));
		// Tạo icon tìm kiếm
		ImageIcon iconTimMon = new ImageIcon("resource\\icon\\TimKiem.png");
		Image scaleIconTimMon = iconTimMon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		lblTimKiemKH = new JLabel(new ImageIcon(scaleIconTimMon));
		lblTimKiemKH.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		pnlTimKhachHang.add(lblTimKiemKH);
		// Tạo icon thêm khách hàng
		ImageIcon iconThemKH = new ImageIcon("resource\\icon\\ThemKH.png");
		Image scaleIconThemKH = iconThemKH.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		lblThemKH = new JLabel(new ImageIcon(scaleIconThemKH));
		pnlTimKhachHang.add(lblThemKH);
		pnlTimKhachHang.setBackground(new Color(194, 232, 255));
		pnlTTButton.add(pnlTimKhachHang);

//Thời gian  
		JPanel pnlThoiGianGhiChu = new JPanel(new GridLayout(1, 3));
		pnlThoiGianGhiChu.setPreferredSize(new Dimension(pnlTTButton.getWidth(), 35));

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		// Đặt ngày mặc định là hôm qua
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		dateChooser.setDate(calendar.getTime());
		JTextField editorNgay = (JTextField) dateChooser.getDateEditor().getUiComponent();
		editorNgay.setForeground(Color.WHITE);
		editorNgay.setBackground(new Color(30, 129, 191));
		editorNgay.setCaretColor(Color.WHITE);
		editorNgay.setHorizontalAlignment(SwingConstants.CENTER);
		// Thêm sự kiện PropertyChange để đảm bảo giữ màu khi chọn ngày mới
		dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
			editorNgay.setForeground(Color.WHITE);
			editorNgay.setBackground(new Color(30, 129, 191));
		});

		pnlThoiGianGhiChu.add(dateChooser);

		JPanel pnlGio = new JPanel(new BorderLayout());
		pnlGio.setBackground(new Color(30, 129, 191));
		pnlGio.setPreferredSize(new Dimension(100, 30));

		// Tạo SpinnerDateModel với phút là đơn vị tăng/giảm
		SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
		JSpinner spinnerGio = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerGio, "HH:mm");
		spinnerGio.setEditor(editor);

		// Tuỳ chỉnh text field
		JTextField txtGio = editor.getTextField();
		txtGio.setBackground(new Color(30, 129, 191));
		txtGio.setForeground(Color.WHITE);
		txtGio.setCaretColor(Color.WHITE);
		txtGio.setBorder(null);
		txtGio.setHorizontalAlignment(SwingConstants.CENTER);

		// Icon đồng hồ
		ImageIcon rawIcon = new ImageIcon("resource\\icon\\clock.png");
		Image img = rawIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		JLabel lblIcon = new JLabel(new ImageIcon(img));
		lblIcon.setPreferredSize(new Dimension(44, 24));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		pnlGio.add(spinnerGio, BorderLayout.CENTER);
		pnlGio.add(lblIcon, BorderLayout.EAST);

		pnlThoiGianGhiChu.add(pnlGio);

//Ghi chú 		
		JButton btnGhiChu = taoButtonIcon("Ghi chú", "resource\\icon\\GhiChu.png", new Color(30, 129, 191), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnGhiChu.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		pnlThoiGianGhiChu.add(btnGhiChu);

		pnlTTButton.add(pnlThoiGianGhiChu);
// Tạm tính - lưu

		JPanel pnlTamTinhLuu = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		pnlTamTinhLuu.setPreferredSize(new Dimension(0, 50));
		pnlTamTinhLuu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		pnlTamTinhLuu.setMinimumSize(new Dimension(0, 50));

		JButton btnTamTinh = taoButtonIcon("Tạm tính", "resource\\icon\\TamTinh.png", new Color(50, 210, 29),
				Color.WHITE, new Font("Segoe UI", Font.BOLD, 14));
		btnTamTinh.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.75;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		pnlTamTinhLuu.add(btnTamTinh, gbc);

		JButton btnLuuDon = taoButtonIcon("Lưu đơn đặt bàn", "", new Color(255, 65, 65), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnLuuDon.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		gbc.gridx = 1;
		gbc.weightx = 0.25;
		pnlTamTinhLuu.add(btnLuuDon, gbc);

		pnlTTButton.add(pnlTamTinhLuu);

		return pnlTTButton;
	}

//====================================== Bên phải
// đang
// Giao diện bên phải  bàn món ăn 
	private JPanel createRightPanelMenuMonAn() {
		JPanel pnlnMenuBanMonAnfn = new JPanel(new BorderLayout());
		splitPane.setRightComponent(pnlnMenuBanMonAnfn);

		// Card Layout bàn món ăn
		JPanel pnlCardBanMonAn = new JPanel();
		CardLayout cardLayoutBanMonAn = new CardLayout(0, 0);
		pnlCardBanMonAn.setLayout(cardLayoutBanMonAn);
		pnlnMenuBanMonAnfn.add(pnlCardBanMonAn, BorderLayout.CENTER);

		pnlCardBan = new JPanel(new BorderLayout());
		createpnlCardBan();
		pnlCardBanMonAn.add(pnlCardBan, "CardBan");

		pnlCardMonAn = new JPanel();
		pnlCardBanMonAn.add(pnlCardMonAn, "CardMonAn");

		// Button điều hướng

		JPanel pnlBtnBanMonAn = new JPanel(new GridLayout(1, 2));
		pnlnMenuBanMonAnfn.add(pnlBtnBanMonAn, BorderLayout.NORTH);

		JButton btnBan = new JButton("Bàn ");
		JButton btnMon = new JButton("Món ăn");
		Color selectedColor = new Color(0xFFA500);
		Color unselectedColor = new Color(0xFCE9CD);
		btnBan.setPreferredSize(new Dimension(0, 36));

		btnBan.setBackground(selectedColor);
		btnMon.setBackground(unselectedColor);

		btnBan.addActionListener(e -> {
			cardLayoutBanMonAn.show(pnlCardBanMonAn, "CardBan");
			((CardLayout) ((JPanel) pnlThongTin.getComponent(1)).getLayout()).show((JPanel) pnlThongTin.getComponent(1),
					"pnlCardTTBan");

			// Cập nhật màu
			btnBan.setBackground(selectedColor);
			btnMon.setBackground(unselectedColor);
		});
		pnlBtnBanMonAn.add(btnBan);
		btnMon.addActionListener(e -> {
			cardLayoutBanMonAn.show(pnlCardBanMonAn, "CardMonAn");
			((CardLayout) ((JPanel) pnlThongTin.getComponent(1)).getLayout()).show((JPanel) pnlThongTin.getComponent(1),
					"CardTTMonAn");

			// Cập nhật màu
			btnMon.setBackground(selectedColor);
			btnBan.setBackground(unselectedColor);
		});

		pnlBtnBanMonAn.add(btnMon);

		return pnlnMenuBanMonAnfn;
	}

// Tạo card các bàn 
	private void createpnlCardBan() {
//		JPanel pnlCardBan = new JPanel(new BorderLayout());

		JPanel pnlNorthCardBan = new JPanel();
		pnlNorthCardBan.setLayout(new BoxLayout(pnlNorthCardBan, BoxLayout.Y_AXIS));
		pnlNorthCardBan.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 35));
// ----- Tìm kiếm 
		JPanel pnlSLKhachHang = new JPanel();
		pnlSLKhachHang.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 35));
		JLabel lbSLKhachHang = new JLabel("Số lượng khách hàng:");
		pnlSLKhachHang.add(lbSLKhachHang);
//		lbSLKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pnlSLKhachHang.add(txtSLKH = new JTextField(40));
		// Tạo button tìm kiếm
		btnTimKiemBanTheoSLKH = new JButton("Tìm");
		btnTimKiemBanTheoSLKH.addActionListener(e -> {
			String input = txtSLKH.getText().trim();

			if (input.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng khách.");
				return;
			}

			try {
				int soKhach = Integer.parseInt(input);
				int[] loaiBan = { 2, 4, 8, 12 };

				List<Integer> banPhuHop = new ArrayList<>();
				for (int ghe : loaiBan) {
					if (ghe >= soKhach) {
						banPhuHop.add(ghe);
					}
				}

				if (banPhuHop.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Không có loại bàn nào phù hợp.");
				} else {
					ArrayList<Ban> cacBanPhuHop = ban_dao.getBanPhuHop(banPhuHop, labelDangChonKVBan);
					if (cacBanPhuHop.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Không có bàn nào trống phù hợp.");
					} else {
						dsBan = cacBanPhuHop;
						loadBanList.run();
					}
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.");
			}
			txtSLKH.setText("");
		});

		pnlSLKhachHang.add(btnTimKiemBanTheoSLKH);
		pnlSLKhachHang.setBackground(new Color(194, 232, 255));

		pnlNorthCardBan.add(pnlSLKhachHang);
		pnlCardBan.add(pnlNorthCardBan, BorderLayout.NORTH);
// ---- Các btn 
//		
		JPanel pnlCenterCardBan = new JPanel(new BorderLayout());
		pnlCenterCardBan.setBackground(Color.WHITE);

		// ==== PANEL CHỨA CÁC NÚT LOC ====
		JPanel pnlscrBtnCardBan = new JPanel();
		pnlscrBtnCardBan.setPreferredSize(new Dimension(pnlCenterCardBan.getWidth(), 40));
		pnlscrBtnCardBan.setBackground(Color.white);

		JPanel pnlBtnCardBan = new JPanel();
		pnlBtnCardBan.setBackground(Color.white);
		String[] buttonLabelsBan = { "Tất cả", "Ngoài trời", "Tầng 1", "Tầng 2", "Sân thượng" };
		groupKVBan = new ButtonGroup();
		JToggleButton btnTatCaBan = null;

		// ==== PANEL CHỨA CÁC BÀN ====
		JPanel pnlCacBan = new JPanel(new BorderLayout());
		pnlCacBan.setPreferredSize(new Dimension(0, 900));
		pnlCacBan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 900));

		JPanel pnlScrBan = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnlScrBan.setBackground(Color.WHITE);

		pnlScrBan.removeAll();
		pnlScrBan.revalidate();
		pnlScrBan.repaint();

		// Load DAO
		ban_dao = new Ban_DAO();
		dsBan = ban_dao.getAllBan();

		int itemWidth = 180;
		int itemHeight = 250;
		int hGap = 10;
		int vGap = 10;
		int columns = 4;
		int totalWidth = (itemWidth * columns) + (hGap * (columns + 1));

		selectedPanel = null;

		// Load danh sách mặc định (tất cả bàn)
		loadBanList = () -> {
			pnlScrBan.removeAll();
			for (Ban ban : dsBan) {
				int trang_thai = ban.getTrangThai();
				int sl = ban.getSoLuongGhe();

				Color color = getColorTheoTrangThai(trang_thai);
				String iconPath = getIconTheoSoLuongGhe(sl);

				pnlScrBan.add(create_1_BanPanel(ban.getMaBan(), color, iconPath));
			}

			int totalItems = pnlScrBan.getComponentCount();
			int rows = (int) Math.ceil((double) totalItems / columns);
			int totalHeight = (itemHeight * rows) + (vGap * (rows + 1));
			pnlScrBan.setPreferredSize(new Dimension(totalWidth, totalHeight));

			pnlScrBan.revalidate();
			pnlScrBan.repaint();
		};

		// Gắn sự kiện cho từng nút lọc
		for (String labelChonKVBan : buttonLabelsBan) {
			JToggleButton btnChonKVBan = createToggleButton(labelChonKVBan);
			groupKVBan.add(btnChonKVBan);
			pnlBtnCardBan.add(btnChonKVBan);

			if (labelChonKVBan.equals("Tất cả"))
				btnTatCaBan = btnChonKVBan;

			btnChonKVBan.addActionListener(e -> {
				switch (labelChonKVBan) {
				case "Tất cả" -> {
					dsBan = ban_dao.getAllBan();
					labelDangChonKVBan = "Tất cả";
				}
				case "Ngoài trời" -> {
					dsBan = ban_dao.getBanNgoaiTroi();
					labelDangChonKVBan = "KV01";
				}
				case "Tầng 1" -> {
					dsBan = ban_dao.getBanTang1();
					labelDangChonKVBan = "KV02";
				}
				case "Tầng 2" -> {
					dsBan = ban_dao.getBanTang2();
					labelDangChonKVBan = "KV03";
				}
				case "Sân thượng" -> {
					dsBan = ban_dao.getBanSanThuong();
					labelDangChonKVBan = "KV04";
				}
				}
				loadBanList.run();
			});
		}
		if (btnTatCaBan != null)
			btnTatCaBan.setSelected(true);
		
		JScrollPane scrButton = new JScrollPane(pnlBtnCardBan);
		scrButton.setBorder(null);
		scrButton.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrButton.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pnlscrBtnCardBan.add(scrButton);

		pnlCenterCardBan.add(pnlscrBtnCardBan, BorderLayout.CENTER);
		pnlCacBan.add(pnlCenterCardBan, BorderLayout.NORTH);

		// Scroll chính chứa bàn
		JScrollPane scrPane = new JScrollPane(pnlScrBan);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pnlCacBan.add(scrPane, BorderLayout.CENTER);
		pnlCardBan.add(pnlCacBan, BorderLayout.CENTER);

		// Load danh sách bàn lần đầu
		loadBanList.run();

//		// Thêm vào vùng hiển thị chính
//		pnlCardBan.add(pnlCenterCardBan, BorderLayout.CENTER);
//		reatePanelLoadBan();

		// Panel dưới
		JPanel pnlSouthCardBan = new JPanel(new BorderLayout());
		pnlSouthCardBan.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 120));
//		pnlCardBan.add(pnlSouthCardBan, BorderLayout.SOUTH);

		// Chú thích
		JLabel lblChuThich = new JLabel("Chú thích");
		lblChuThich.setFont(new Font("Arial", Font.BOLD, 16));
		lblChuThich.setForeground(Color.WHITE);
		JPanel pnlChuThich = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlChuThich.setBackground(new Color(30, 129, 191));
		pnlChuThich.setPreferredSize(new Dimension(0, 35));
		lblChuThich.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		pnlChuThich.add(lblChuThich);

		JPanel pnlbtnChuThich = new JPanel(new GridLayout(2, 2));

		JButton btnBanTrong = new JButton();
		JButton btnBanCoKhach = new JButton();
		JButton btnBanDaDat = new JButton();

		JPanel pnlbtnBanTrong = createPanelChuThich(btnBanTrong, new Color(0x8EF49A), "Bàn trống", 1);
		JPanel pnlbtnBanCoKhach = createPanelChuThich(btnBanCoKhach, new Color(0xFF8A8A), "Bàn có khách", 2);
		JPanel pnlbtnBanDaDat = createPanelChuThich(btnBanDaDat, new Color(0xACACAC), "Bàn đã đặt", 3);

		pnlbtnChuThich.add(pnlbtnBanTrong);
		pnlbtnChuThich.add(pnlbtnBanCoKhach);
		pnlbtnChuThich.add(pnlbtnBanDaDat);

		pnlSouthCardBan.add(pnlChuThich, BorderLayout.NORTH);
		pnlSouthCardBan.add(pnlbtnChuThich, BorderLayout.CENTER);
		pnlCardBan.add(pnlSouthCardBan, BorderLayout.SOUTH);

	}

	// Hàm trả về màu theo trạng thái
	private Color getColorTheoTrangThai(int trangThai) {
		return switch (trangThai) {
		case 1 -> new Color(0x8EF49A);
		case 2 -> new Color(0xFF8A8A);
		case 3 -> new Color(0xACACAC);
		default -> Color.black;
		};
	}

	// Hàm trả về đường dẫn icon theo số lượng ghế
	private String getIconTheoSoLuongGhe(int soLuong) {
		return switch (soLuong) {
		case 2 -> "resource\\icon\\ban2.png";
		case 4 -> "resource\\icon\\ban4.png";
		case 8 -> "resource\\icon\\ban8.png";
		case 12 -> "resource\\icon\\ban12.png";
		default -> "resource\\icon\\ban_default.png";
		};
	}

// đang
	// btn ghi chú
	private JPanel createPanelChuThich(JButton btn, Color color, String labelText, int trangThai) {
		btn.setPreferredSize(new Dimension(20, 20));
		btn.setBackground(color);
		btn.setFocusable(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK)));

		btn.addActionListener(e -> {
			if (labelDangChonKVBan == "Tất cả") {
				dsBan = ban_dao.getBanTheoTrangThai(trangThai);
				loadBanList.run();
			} else {
				dsBan = ban_dao.getBanTheoKhuVucVaTrangThai(labelDangChonKVBan, trangThai);
				loadBanList.run();
			}
			if (dsBan == null) {
				JOptionPane.showMessageDialog(null, "Không có bàn hợp lệ!!!");
			}

		});

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createHorizontalStrut(30)); // cách trái
		panel.add(btn);
		panel.add(new JLabel(labelText));
		return panel;
	}

//	tạo Bàn giao diện 1 bàn 
	private JPanel create_1_BanPanel(String tenBan, Color bgColor, String pathIcon) {
		JPanel pnlbtnBan = new JPanel(new BorderLayout());
		pnlbtnBan.setPreferredSize(new Dimension(180, 250));

		JLabel lblTenBan = new JLabel(tenBan, JLabel.CENTER);
		lblTenBan.setFont(new Font("Arial", Font.BOLD, 14));
		lblTenBan.setForeground(Color.WHITE);
		JPanel pnlTenBan = new JPanel();
		pnlTenBan.setBackground(new Color(30, 129, 191));
		pnlTenBan.setPreferredSize(new Dimension(180, 35));
		pnlTenBan.add(lblTenBan);

		JPanel pnlCenterBan = new JPanel(new BorderLayout());
		pnlCenterBan.setPreferredSize(new Dimension(180, 175));
		pnlCenterBan.setBackground(bgColor);

		ImageIcon iconBan = new ImageIcon(pathIcon);
		Image imageBan = iconBan.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
		JLabel lblImageBan = new JLabel(new ImageIcon(imageBan));
		lblImageBan.setHorizontalAlignment(JLabel.CENTER);
		pnlCenterBan.add(lblImageBan, BorderLayout.CENTER);

		JPanel pnlChiTiet = new JPanel();
		pnlChiTiet.setPreferredSize(new Dimension(180, 40));
		pnlChiTiet.setBackground(bgColor);
		JButton btnChiTiet = createCustomButton("Chi tiết");
		pnlChiTiet.add(btnChiTiet);

		if (bgColor.equals(new Color(0x8EF49A))) {
			btnChiTiet.setEnabled(false); // Vô hiệu hóa nút
		}

		// Sự kiện khi nhấn nút
		btnChiTiet.addActionListener(e -> JOptionPane.showMessageDialog(null, "Chi tiết : " + tenBan));

		// Sự kiện khi nhấp đúp vào toàn bộ panel
		pnlbtnBan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
// đang
				Ban banChon = ban_dao.getBan(tenBan);
				KhuVuc kvChon = khuVuc_dao.getKhuVucTheoMa(banChon.getKV().getMaKV());
				
//				if (banChon.getPhong() != null && banChon.getPhong().getMaPhong() != null) {
//				    Phong phongChon = phong_dao.getPhongTheoMa(banChon.getPhong().getMaPhong());
//				    if (phongChon != null) {
//				        txtPhong.setText(phongChon.getTenPhong());
//				    } else {
//				        txtPhong.setText("Không tìm thấy phòng");
//				    }
//				} else {
//				    txtPhong.setText("");
//				}

				
				
				txtMaBan.setText(banChon.getMaBan());
				txtKhuVuc.setText(kvChon.getTenKV());
				txtSLGhe.setText(Integer.toString(banChon.getSoLuongGhe()));
				double tienCoc = 50000 * banChon.getSoLuongGhe();

		        // Định dạng kiểu tiền Việt Nam
		        NumberFormat formatVN = NumberFormat.getInstance(new Locale("vi", "VN"));
				txtTienCoc.setText(formatVN.format(tienCoc) + " VND");

				if (selectedPanel != null) {
					selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Viền mặc định
				}

				// Đặt viền đỏ cho panel được chọn
				pnlbtnBan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); // Viền đỏ với độ dày 3px

				// Cập nhật panel được chọn
				selectedPanel = pnlbtnBan;
				
			}

			private String String(String maKV) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		pnlbtnBan.add(pnlTenBan, BorderLayout.NORTH);
		pnlbtnBan.add(pnlCenterBan, BorderLayout.CENTER);
		pnlbtnBan.add(pnlChiTiet, BorderLayout.SOUTH);

		return pnlbtnBan;
	}
	
	
// Tạo card món ăn 
	

	/**
	 * Tạo một JPanel chứa một dòng thông tin gồm JLabel và JTextField, có thể tùy
	 * chỉnh khoảng cách, độ dài ô nhập và có viền hoặc không.
	 *
	 * @param labelText        Nội dung hiển thị của JLabel (ví dụ: "Mã bàn:")
	 * @param textField        JTextField được truyền từ bên ngoài để tái sử dụng và
	 *                         thao tác
	 * @param editable         Cho phép người dùng chỉnh sửa (true) hoặc chỉ đọc
	 *                         (false)
	 * @param labelSpacing     Khoảng cách ngang giữa JLabel và JTextField (tính
	 *                         bằng pixel)
	 * @param textFieldColumns Số cột của JTextField (xác định độ dài hiển thị)
	 * @param hasBorder        Nếu false thì sẽ xoá viền của JTextField, ngược lại
	 *                         giữ nguyên
	 * @return JPanel chứa dòng thông tin đã cấu hình sẵn
	 */
	private JPanel createLabelTextFieldRow(String labelText, JTextField textField, boolean editable, int labelSpacing,
			int textFieldColumns, boolean hasBorder) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createHorizontalStrut(10)); // Cách trái

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(label);

		panel.add(Box.createHorizontalStrut(labelSpacing)); // Khoảng cách giữa label và textField

		textField.setColumns(textFieldColumns);
		textField.setFont(label.getFont());
		textField.setEditable(editable);
		if (!hasBorder) {
			textField.setBorder(null); // Xoá viền nếu cần
			textField.setCaretColor(null);
			textField.setCaretColor(textField.getBackground());
		}
		panel.add(textField);

		return panel;
	}

// ================== Button 
	public JButton taoButtonIcon(String text, String iconPath, Color background, Color foreground, Font font) {
		// Tạo icon từ đường dẫn
		ImageIcon icon = new ImageIcon(iconPath);
		JButton button = new JButton(" " + text, icon);

		// Tuỳ chỉnh button
		button.setBackground(background);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		button.setFont(font);
		button.setBorderPainted(true);
		button.setHorizontalAlignment(SwingConstants.CENTER);

		return button;
	}

// btn tất cả món, bàn .....

	// Hàm vẽ nền bo góc cho nút
	private void paintRoundedBackground(Graphics g, AbstractButton btn, boolean selected, boolean hover,
			boolean pressed) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (pressed) {
			g2.setColor(new Color(200, 0, 0));
		} else if (hover) {
			g2.setColor(new Color(255, 80, 80));
		} else if (selected) {
			g2.setColor(Color.RED);
		} else {
			g2.setColor(new Color(33, 129, 191));
		}

		g2.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 20, 20);
		g2.dispose();
	}

	private JToggleButton createToggleButton(String text) {
		JToggleButton btn = new JToggleButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				paintRoundedBackground(g, this, isSelected(), getModel().isRollover(), getModel().isPressed());
				super.paintComponent(g);
			}
		};

		btn.setFocusPainted(false);
		btn.setFont(new Font("Arial", Font.BOLD, 12));
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		btn.setContentAreaFilled(false);
		btn.setOpaque(false);

		return btn;
	}

// nút chi tiết 
	private JButton createCustomButton(String text) {
		JButton btn = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isEnabled()) {
					// Vẽ màu nền mờ (disabled)
					createCustomButtonBackground(g, this, true, false, false);
				} else {
					createCustomButtonBackground(g, this, false, getModel().isRollover(), getModel().isPressed());
				}
				super.paintComponent(g);
			}
		};

		// Thiết lập các thuộc tính cho nút
		btn.setFocusPainted(false);
		btn.setFont(new Font("Arial", Font.BOLD, 12));
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		btn.setContentAreaFilled(false);
		btn.setOpaque(false);

		return btn;
	}

// set bo góc + màu nút chi tiết 
	private void createCustomButtonBackground(Graphics g, JButton btn, boolean isDisabled, boolean isHover,
			boolean isPressed) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color backgroundColor;

		// Xử lý màu nền tùy thuộc vào trạng thái của nút
		if (isDisabled) {
			backgroundColor = new Color(30, 129, 191, 50);
		} else if (isPressed) {
			backgroundColor = new Color(30, 129, 191, 20);
		} else {
			backgroundColor = new Color(0x1E81BF);
		}

		g2.setColor(backgroundColor);
		g2.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 20, 20);

		g2.dispose();
	}

// Menu 
	private void initUI_Menu() {

		// Thay đổi màu cho menu chính và các item trong submenu
		UIManager.put("Menu.selectionBackground", new Color(204, 204, 204));
		UIManager.put("Menu.selectionForeground", Color.WHITE);
		UIManager.put("MenuItem.selectionBackground", new Color(229, 229, 229));

		// Màu nền và chữ của menu chính
		UIManager.put("Menu.background", new Color(30, 129, 191));
		UIManager.put("Menu.foreground", Color.WHITE);

		// Màu nền và chữ cho các item trong menu chính
		UIManager.put("MenuItem.background", new Color(255, 255, 255));
		UIManager.put("MenuItem.foreground", new Color(0, 0, 0));
		UIManager.put("MenuItem.font", new Font("Arial", Font.PLAIN, 13));

		setLayout(new BorderLayout());

		ImageIcon iconHome = new ImageIcon("resource\\icon\\images.jpg");
		ImageIcon iconTaiKhoan = new ImageIcon("resource\\icon\\account.png");

		Image scaleIconHome = iconHome.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		JLabel lblIconHome = new JLabel(new ImageIcon(scaleIconHome));

		Image scaleIconTaiKhoan = iconTaiKhoan.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		JLabel lblIconTaiKhoan = new JLabel(new ImageIcon(scaleIconTaiKhoan));

		// Các menu khác
		mnuDatBanTruoc = new JMenu("Đặt Bàn Trước");
		mnuDatBanTrucTiep = new JMenu("Đặt Bàn Trực Tiếp");
		mnuGiaoCa = new JMenu("Giao Ca");
		mnuThongKe = new JMenu("Thống Kê");
		mnuQuanLyKhachHang = new JMenu("Quản Lý Khách Hàng");
		mnuTimKiem = new JMenu("Tìm kiếm");
		mnuTroGiup = new JMenu("Trợ Giúp");

		// Các JMenuItem
		mniDBTT1 = new JMenuItem("Thêm đơn đặt bàn trực tiếp");
		mniDBTT2 = new JMenuItem("Cập nhật đơn đặt bàn trực tiếp");
		mniDBT1 = new JMenuItem("Thêm đơn đặt bàn trước");
		mniDBT2 = new JMenuItem("Cập nhật đơn đặt bàn trước");
		mniMoCa = new JMenuItem("Mở ca");
		mniKetCa = new JMenuItem("Kết ca");
		mniThemKH = new JMenuItem("Thêm khách hàng");
		mniCNKH = new JMenuItem("Cập nhật thông tin khách hàng");
		mniTKDonDatBan = new JMenuItem("Đơn đặt bàn");
		mniTKDonDatMon = new JMenuItem("Đơn đặt món");
		mniTKBan = new JMenuItem("Bàn");
		mniTKMonAn = new JMenuItem("Món ăn");
		mniKhuyenMai = new JMenuItem("Khuyến Mãi");
		mniTKKH = new JMenuItem("Khách hàng");
		mniTKNV = new JMenuItem("Nhân viên");
		mniHDSD = new JMenuItem("Hướng dẫn sử dụng");
		mniChungToi = new JMenuItem("Chúng tôi");

		// Thêm các JMenuItem con vào JMenu
		mnuDatBanTrucTiep.add(mniDBTT1);
		mnuDatBanTrucTiep.add(mniDBTT2);
		mnuDatBanTruoc.add(mniDBT1);
		mnuDatBanTruoc.add(mniDBT2);
		mnuGiaoCa.add(mniMoCa);
		mnuGiaoCa.add(mniKetCa);
		mnuQuanLyKhachHang.add(mniThemKH);
		mnuQuanLyKhachHang.add(mniCNKH);
		mnuTimKiem.add(mniTKDonDatBan);
		mnuTimKiem.add(mniTKDonDatMon);
		mnuTimKiem.add(mniTKBan);
		mnuTimKiem.add(mniTKMonAn);
		mnuTimKiem.add(mniKhuyenMai);
		mnuTimKiem.add(mniTKKH);
		mnuTimKiem.add(mniTKNV);
		mnuTroGiup.add(mniHDSD);
		mnuTroGiup.add(mniChungToi);

		int menuHeight = 50;
		int defaultWidth = 120;

		mnuDatBanTrucTiep.setPreferredSize(new Dimension(defaultWidth, menuHeight));
		mnuDatBanTruoc.setPreferredSize(new Dimension(defaultWidth, menuHeight));
		mnuGiaoCa.setPreferredSize(new Dimension(defaultWidth, menuHeight));
		mnuThongKe.setPreferredSize(new Dimension(defaultWidth, menuHeight));
		mnuQuanLyKhachHang.setPreferredSize(new Dimension(150, menuHeight));
		mnuTimKiem.setPreferredSize(new Dimension(defaultWidth, menuHeight));
		mnuTroGiup.setPreferredSize(new Dimension(defaultWidth, menuHeight));

		// Tạo menuBar
		mnuFunction = new JMenuBar();
		mnuFunction.setLayout(new BoxLayout(mnuFunction, BoxLayout.X_AXIS));
		mnuFunction.setBackground(new Color(30, 129, 191));
		mnuFunction.setOpaque(true);

		mnuFunction.add(lblIconHome);
		mnuFunction.add(Box.createHorizontalStrut(250));
		mnuFunction.add(Box.createHorizontalGlue());

		mnuFunction.add(mnuDatBanTrucTiep);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuDatBanTruoc);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuGiaoCa);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuThongKe);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuQuanLyKhachHang);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuTimKiem);
		mnuFunction.add(Box.createHorizontalStrut(50));
		mnuFunction.add(mnuTroGiup);
		mnuFunction.add(Box.createHorizontalGlue());
		mnuFunction.add(Box.createHorizontalStrut(10));
		mnuFunction.add(lblIconTaiKhoan);

		mnuFunction.add(Box.createHorizontalStrut(200));

		add(mnuFunction, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
