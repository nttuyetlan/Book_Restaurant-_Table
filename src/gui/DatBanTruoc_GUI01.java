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
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.View;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;

import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JCheckBox;

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
import java.awt.Cursor;

import connectDB.ConnectDB;
import entity.*;
import dao.*;
import java.util.regex.Pattern;

public class DatBanTruoc_GUI01 extends JFrame implements ActionListener {

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
	private JTextField txtsoKH;
	private JTextField txtTenKHMon;
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
	private JTextField txtTTDTongTienMon, txtTTDSoLuongMon, txtTTDSoMon;
	private JTextField txtTTDTongTienCoc;
	private JSplitPane splitPane;
	private JPanel pnlThongTin;
	private Component pnlMenuBanMonAn;
	private JPanel pnlCardBan;
	private JPanel pnlCardMonAn;
	private ArrayList<Ban> dsBan;
	private ButtonGroup groupKVBan;
	private String labelDangChonKVBan = "Tất cả";
	private Ban_DAO ban_dao = new Ban_DAO();
	private KhuVuc_DAO khuVuc_dao = new KhuVuc_DAO();
	private KhachHang_DAO KH_dao = new KhachHang_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
	private DonDatBanTruoc_DAO DDBT_dao = new DonDatBanTruoc_DAO();
	private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
	private ChiTietHoaDon_DAO ctHD_dao = new ChiTietHoaDon_DAO();
	private Runnable loadBanList;
	private JPanel selectedPanel;
	private JButton btnTimKiemBanTheoSLKH;
	private JTextField txtSLKH;
	private JToggleButton btnTatCaMon;// btnTatCa
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
	private CardLayout cardLayoutButton;
	private JPanel pnlCardButton;
	private MonAn_DAO monAn_dao = new MonAn_DAO();
	private JTable tblFormMonAn;
	private JButton btnXoaMonAn;
	private JTextField txtMaBanMon;
	private JTextField txtMaBanBan;
	private JButton btnTimKiemSDTKH;
	private KhachHang_DAO khachHang_dao = new KhachHang_DAO();;
	private JLabel txtTienMonAnCoc;
	private JDateChooser dateChooser;
	private JSpinner spinnerGio;
	private CardLayout cardLayoutBanMonAn;
	private JPanel pnlCardBanMonAn;
	private JButton btnBan;
	private JButton btnMon;
	private JButton btnCapNhat;
	private JButton btnLuuDon;
	private JTextArea textAreaGhiChu;
	private JButton btnDaDen;
	private NhanVien nv;
	private JButton btnLamMoi;
	private Color mauBanChon;

	public DatBanTruoc_GUI01(NhanVien nv) {
		dsBan = new ArrayList<Ban>();
		setupSplitPane();
		this.nv = nv;
	}

	public JPanel setupSplitPane() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource\\icon\\logo01.png"));
		JPanel pnlDSDBT = new JPanel(new BorderLayout());

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

		pnlDSDBT.add(splitPane);
		return pnlDSDBT;
	}

	public JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource\\icon\\logo01.png"));
		setFont(new Font("Arial", Font.BOLD, 20));
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Đặt bàn trước");

		splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setResizeWeight(0.5);

		widthSplitPane = splitPane.getPreferredSize().width;
		heightSplitPane = splitPane.getPreferredSize().height;
		pnlThongTin = createLeftPanelThongTin();
		pnlThongTin.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlMenuBanMonAn = createRightPanelMenuMonAn();
		splitPane.setLeftComponent(pnlThongTin);
		splitPane.setRightComponent(pnlMenuBanMonAn);
		mainPanel.add(splitPane, BorderLayout.CENTER);

		return mainPanel;
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
		JPanel pnlCardTTMonAn = initUI_Left_Menu_MonAn();
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

		txtMaBanBan = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Mã bàn:", txtMaBanBan, false, 90, 30, false));

		txtKhuVuc = new JTextField();
		pnlCenterTTBan.add(createLabelTextFieldRow("Khu vực:", txtKhuVuc, false, 80, 30, false));

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
		pnlCenterTTKH.add(createLabelTextFieldRow("Tên khách hàng:", txtTTTenKH, false, 40, 30, false));

		txtTTSDTKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("SĐT khách hàng:", txtTTSDTKH, false, 40, 30, false));

		txtTTEmailKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("Email khách hàng:", txtTTEmailKH, false, 30, 30, false));

		txtTTSLKH = new JTextField();
		pnlCenterTTKH.add(createLabelTextFieldRow("Số lượng khách hàng:", txtTTSLKH, true, 5, 30, true));

		pnlThongTinKhachHang.add(pnlCenterTTKH, BorderLayout.CENTER);

		pnlCardTTBan.add(pnlThongTinBan);
		pnlCardTTBan.add(pnlThongTinKhachHang);

		return pnlCardTTBan;
	}

//------ Card Thông tin món ăn

	private JPanel initUI_Left_Menu_MonAn() {
		// TODO Auto-generated method stub
		JPanel pnlCardMonAnfunc = new JPanel();// pnlCardMonAn
		pnlCardMonAnfunc.setLayout(new BorderLayout());

		// Form thông tin món đặt
		String[] columnNames = { "", "Tên món ăn", "", "Số lượng", "", "Đơn giá", "Thành tiền", "" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3;
			}
		};
		tblFormMonAn = new JTable(model);
		tblFormMonAn.setFocusable(false);
		tblFormMonAn.setDefaultEditor(Object.class, null);

		// Điều chỉnh kích thước các cột
		TableColumnModel columnModel = tblFormMonAn.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(10);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(4).setPreferredWidth(10);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(100);
		columnModel.getColumn(7).setPreferredWidth(10);

		// Tuỳ chỉnh giao diện cột "Số lượng"
		tblFormMonAn.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

		tblFormMonAn = new JTable(model) {
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
		tblFormMonAn.setFont(new Font("Arial", Font.PLAIN, 12));
		tblFormMonAn.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
		tblFormMonAn.setIntercellSpacing(new Dimension(5, 5));

		// Bỏ viền bảng
		tblFormMonAn.setShowGrid(false);
		tblFormMonAn.setIntercellSpacing(new Dimension());
		tblFormMonAn.setBorder(null);

		// Bỏ nền và viền header
		JTableHeader tbhForm = tblFormMonAn.getTableHeader();
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
		tblFormMonAn.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblFormMonAn.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblFormMonAn.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblFormMonAn.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblFormMonAn.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
		tblFormMonAn.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tblFormMonAn.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tblFormMonAn.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

		tblFormMonAn.setPreferredScrollableViewportSize(new Dimension(500, 150));

		tblFormMonAn.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				lbl.setText(value != null ? value.toString() : "");
				lbl.setVerticalAlignment(SwingConstants.TOP);
				lbl.setOpaque(true);

				// Tự tính chiều cao dòng dựa trên nội dung HTML
				int prefHeight = getPreferredHeight(lbl, table.getColumnModel().getColumn(column).getWidth());
				if (table.getRowHeight(row) != prefHeight) {
					table.setRowHeight(row, prefHeight);
				}

				return lbl;
			}

			private int getPreferredHeight(JLabel label, int width) {
				View view = BasicHTML.createHTMLView(label, label.getText());
				view.setSize(width, 0);
				float prefHeight = view.getPreferredSpan(View.Y_AXIS);
				return (int) prefHeight + 10; // padding 10
			}
		});

		// Sự kiện cho icon Xóa, icon Tăng, icon giảm, icon Ghi Chú trong bảng
		tblFormMonAn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblFormMonAn.rowAtPoint(e.getPoint());
				int col = tblFormMonAn.columnAtPoint(e.getPoint());

				// Giả sử icon xóa nằm ở cột 0
				if (col == 0 && row != -1) {
					int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá dòng này?", "Xác nhận xoá",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
						model.removeRow(row);
						tinhTongTien(tblFormMonAn);
					}
				}
				if (row != -1 && (col == 2 || col == 4)) {
					DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
					int sl = Integer.parseInt(model.getValueAt(row, 3).toString());
					double price = Double.parseDouble(model.getValueAt(row, 5).toString().replaceAll("[^\\d]", ""));

					if (col == 2 && sl > 1)
						sl--;
					if (col == 4)
						sl++;

					model.setValueAt(String.valueOf(sl), row, 3);
					model.setValueAt(String.format("%,.0f VNĐ", price * sl), row, 6);
					tinhTongTien(tblFormMonAn);
				}

				if (col == 7 && row != -1) {
					Object cellValue = tblFormMonAn.getValueAt(row, 1);
					String current = cellValue != null ? cellValue.toString() : "";
					String tenMon = "";
					String ghiChuCu = "";
					if (current.startsWith("<html>")) {
						current = current.substring(6, current.length() - 7);

						int brIndex = current.indexOf("<br>");
						if (brIndex != -1) {
							tenMon = current.substring(0, brIndex).trim();

							int start = current.indexOf("<i");
							int tagClose = current.indexOf(">", start);
							int end = current.indexOf("</i>", tagClose);

							if (start != -1 && tagClose != -1 && end != -1) {
								ghiChuCu = current.substring(tagClose + 1, end).trim();
							}
						} else {
							tenMon = current.trim();
						}
					} else {
						tenMon = current.trim();
					}

					JTextArea txaNote = new JTextArea(5, 30);
					txaNote.setLineWrap(true);
					txaNote.setWrapStyleWord(true);
					txaNote.setText(ghiChuCu);

					String[] goiY = { "Ít cay", "Thêm cay", "Ít ngọt", "Nhiều sốt", "Không hành", "Không rau",
							"Cho thêm tương", "Ít dầu mỡ" };

					JPanel pnlCheckBox = new JPanel(new GridLayout(0, 2));

					for (String goiYItem : goiY) {
						JCheckBox chk = new JCheckBox(goiYItem);
						if (ghiChuCu.contains(goiYItem)) {
							chk.setSelected(true);
						}
						chk.addItemListener(ae -> {
							String currentText = txaNote.getText();

							if (chk.isSelected()) {
								if (!currentText.contains(goiYItem)) {
									if (!currentText.isEmpty() && !currentText.endsWith(",")
											&& !currentText.endsWith(" ")) {
										currentText += ", ";
									}
									currentText += goiYItem;
								}
							} else {
								currentText = currentText
										.replaceAll("(?i)(?<=^|,\\s?)" + Pattern.quote(goiYItem) + "(?=,|$)", "")
										.replaceAll(",\\s*,", ",").replaceAll("(^,\\s*|,\\s*$)", "")
										.replaceAll("\\s{2,}", " ").replaceAll("^\\s+|\\s+$", "").trim();
							}

							txaNote.setText(currentText);
						});

						pnlCheckBox.add(chk);
					}

					JPanel pnl = new JPanel(new BorderLayout(5, 5));
					pnl.add(new JLabel("Ghi chú:"), BorderLayout.NORTH);
					pnl.add(new JScrollPane(txaNote), BorderLayout.CENTER);
					pnl.add(pnlCheckBox, BorderLayout.SOUTH);

					int result = JOptionPane.showConfirmDialog(null, pnl, "Ghi chú cho món ăn",
							JOptionPane.OK_CANCEL_OPTION);

					if (result == JOptionPane.OK_OPTION) {
						String note = txaNote.getText().trim();
						if (!note.isEmpty()) {
							tblFormMonAn.setValueAt(
									"<html>" + tenMon + "<br><i style='color:rgb(129,14,202)'>" + note + "</i></html>",
									row, 1);
						} else {
							tblFormMonAn.setValueAt(tenMon, row, 1);
						}

					}
				}

			}
		});

		model.addTableModelListener(e -> {
			int row = e.getFirstRow();
			int column = e.getColumn();

			if (column == 3) {
				try {
					int sl = Integer.parseInt(model.getValueAt(row, 3).toString());

					double price = Double.parseDouble(model.getValueAt(row, 5).toString().replaceAll("[^\\d]", ""));

					model.setValueAt(String.format("%,.0f VNĐ", price * sl), row, 6);
					tinhTongTien(tblFormMonAn);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
					model.setValueAt("1", row, 3);
				}
			}
		});

		JScrollPane scrFrom = new JScrollPane();
		scrFrom.setViewportView(tblFormMonAn);
		tblFormMonAn.setRowHeight(35); // thay đổi
		tblFormMonAn.setAutoCreateRowSorter(true);
		tblFormMonAn.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrFrom.setBorder(null);
		scrFrom.setPreferredSize(new Dimension(pnlCardMonAnfunc.getWidth(), 300));// thay đổi
		pnlCardMonAnfunc.add(scrFrom);

		JPanel pnlTTDonMonAn = new JPanel(new BorderLayout());

		JPanel pnlTTMonTien = new JPanel(new BorderLayout());

		JPanel pnlTTSLMon = new JPanel(new FlowLayout(FlowLayout.LEFT));

		txtTTDSoMon = new JTextField();
		pnlTTSLMon.add(createLabelTextFieldRow("Số món ăn: ", txtTTDSoMon, false, 0, 15, false));
		txtTTDSoMon.setText("0");

		txtTTDSoLuongMon = new JTextField();
		pnlTTSLMon.add(createLabelTextFieldRow("Số lượng món ăn: ", txtTTDSoLuongMon, false, 0, 10, false));
		txtTTDSoLuongMon.setText("0");

		btnXoaMonAn = new JButton("Xóa hết món");
		btnXoaMonAn.setFocusPainted(false);
		btnXoaMonAn.addActionListener(this);
		pnlTTSLMon.add(btnXoaMonAn);

		JPanel pnlTTDonTien = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTTDonTien.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		txtTTDTongTienMon = new JTextField();
		pnlTTDonTien.add(createLabelTextFieldRow("Tổng tiền món: ", txtTTDTongTienMon, false, 5, 13, false));
		txtTTDTongTienMon.setText(String.format("%,.0f VNĐ", 0.0));

		txtTTDTongTienCoc = new JTextField();
		pnlTTDonTien.add(createLabelTextFieldRow("Tổng tiền cọc món: ", txtTTDTongTienCoc, false, 0, 14, false));
		txtTTDTongTienCoc.setText(String.format("%,.0f VNĐ", 0.0));

		pnlTTMonTien.add(pnlTTSLMon, BorderLayout.NORTH);
		pnlTTMonTien.add(pnlTTDonTien, BorderLayout.SOUTH);

		pnlTTDonMonAn.add(scrFrom, BorderLayout.CENTER);
		pnlTTDonMonAn.add(pnlTTMonTien, BorderLayout.SOUTH);

		pnlCardMonAnfunc.add(pnlTTDonMonAn, BorderLayout.CENTER);

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

		pnlCardMonAnfunc.add(pnlTTDon, BorderLayout.SOUTH);

		return pnlCardMonAnfunc;
	}

	private JPanel createPanelTTButton() {
		JPanel pnlTTButton = new JPanel();
		pnlTTButton.setLayout(new BoxLayout(pnlTTButton, BoxLayout.Y_AXIS));
		JPanel pnlTimKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnlTimKhachHang.setPreferredSize(new Dimension(pnlTTButton.getWidth(), 40));
		JLabel lblSDTKH;
		pnlTimKhachHang.add(lblSDTKH = new JLabel("SĐT khách hàng:"));
		pnlTimKhachHang.add(txtSĐTKH = new JTextField(20));
		// Tạo icon tìm kiếm
		btnTimKiemSDTKH = new JButton("Tìm");
		btnTimKiemSDTKH.addActionListener(e -> {

			String text = txtSĐTKH.getText().trim();
			KhachHang kh = KH_dao.getKhachHangTheoSDTKhachHang(text);
			if (kh != null) {
				txtTTDTenKH.setText(kh.getTenKH());
				txtTTTenKH.setText(kh.getTenKH());
				txtTTEmailKH.setText(kh.getEmailKH());
				txtTTSDTKH.setText(kh.getSdtKH());
				txtSĐTKH.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
				txtTTDTenKH.setText("");
				txtTTTenKH.setText("");
				txtTTEmailKH.setText("");
				txtTTSDTKH.setText("");
				txtSĐTKH.setText("");
			}
		});

		pnlTimKhachHang.add(btnTimKiemSDTKH);
		ImageIcon iconThemKH = new ImageIcon("resource\\icon\\ThemKH.png");
		Image scaleIconThemKH = iconThemKH.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		lblThemKH = new JLabel(new ImageIcon(scaleIconThemKH));
		pnlTimKhachHang.setBackground(new Color(194, 232, 255));

		lblThemKH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dialog_ThemKhachHang(DatBanTruoc_GUI01.this).setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblThemKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

		pnlTimKhachHang.add(lblThemKH);

		pnlTTButton.add(pnlTimKhachHang);

// Luư ghi chú
		JPanel pnlTamTinhLuu = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		pnlTamTinhLuu.setPreferredSize(new Dimension(0, 100));
		pnlTamTinhLuu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		pnlTamTinhLuu.setMinimumSize(new Dimension(0, 100));

		JPanel pnlGhiChu = new JPanel();
		pnlGhiChu.setLayout(new BoxLayout(pnlGhiChu, BoxLayout.Y_AXIS));

		JLabel lblGhiChu = new JLabel("Ghi chú đơn đặt bàn:");
		pnlGhiChu.add(lblGhiChu);
		pnlGhiChu.add(Box.createVerticalStrut(5));

		textAreaGhiChu = new JTextArea(3, 5);
		textAreaGhiChu.setLineWrap(true);
		textAreaGhiChu.setWrapStyleWord(true);
		JScrollPane scrollPaneGhiChu = new JScrollPane(textAreaGhiChu);
		scrollPaneGhiChu.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

		scrollPaneGhiChu.setPreferredSize(new Dimension(950, 70));
		scrollPaneGhiChu.setMaximumSize(new Dimension(950, 70));

		pnlGhiChu.add(scrollPaneGhiChu);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.95;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		pnlTamTinhLuu.add(pnlGhiChu, gbc);

		JPanel pnlBtnGhiChuLuuDon = new JPanel(new BorderLayout());

		btnLamMoi = taoButtonIcon("Làm mới", "", new Color(160, 160, 160), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnLamMoi.setBorder(BorderFactory.createLineBorder(Color.white, 1));

		btnLamMoi.setPreferredSize(new Dimension(btnLamMoi.getPreferredSize().width, 30));
		btnLamMoi.addActionListener(e -> {

			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn làm mới hủy bỏ các thông tin đã điền ?",
					"Xác nhận làm mới", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
				model.setRowCount(0);
				tinhTongTien(tblFormMonAn);

				cardLayoutBanMonAn.show(pnlCardBanMonAn, "CardBan");
				((CardLayout) ((JPanel) pnlThongTin.getComponent(1)).getLayout())
						.show((JPanel) pnlThongTin.getComponent(1), "pnlCardTTBan");

				// Cập nhật màu
				btnBan.setBackground(new Color(0xFFA500));
				btnMon.setBackground(new Color(0xFCE9CD));

				locDanhSachBanTheoThoiGianLoadDau();
				txtMaBanBan.setText("");
				txtTTDMaBan.setText("");
				txtKhuVuc.setText("");
				txtSLGhe.setText("");
				txtTienCoc.setText("");

				txtTTDTenKH.setText("");
				txtTTTenKH.setText("");
				txtTTEmailKH.setText("");
				txtTTSDTKH.setText("");
				txtSĐTKH.setText("");
				textAreaGhiChu.setText("");
				txtMaDon.setText("");
				txtTTSLKH.setText("");
				txtTTDSKH.setText("");

				Calendar calendar = Calendar.getInstance();
				dateChooser.setDate(calendar.getTime());
				spinnerGio.setValue(calendar.getTime());
				btnCapNhat.setEnabled(false);
				btnDaDen.setEnabled(false);
				btnLuuDon.setEnabled(false);
			}

		});
		pnlBtnGhiChuLuuDon.add(btnLamMoi, BorderLayout.NORTH);

		// Tạo nút "Cập nhật"
		btnCapNhat = taoButtonIcon("Cập nhật", "", new Color(30, 129, 191), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnCapNhat.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		btnCapNhat.setPreferredSize(new Dimension(100, 50));
		btnCapNhat.addActionListener(e -> {

			Date ngayDate = dateChooser.getDate();
			Date gioDate = (Date) spinnerGio.getValue();
			LocalDate localDate = ngayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalTime localTime = gioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);
			LocalDateTime thoiGianTao = LocalDateTime.now();
			Double tongTienCoc = Double.parseDouble(txtTTDTienCoc.getText().replace("VNĐ", "").replace(",", ""));
			Ban banTamTinh = ban_dao.getBan(txtTTDMaBan.getText());
			KhachHang khTamTinh = KH_dao.getKhachHangTheoSDTKhachHang(txtTTSDTKH.getText());
			String maDDBT = txtMaDon.getText().trim();

			int soLuongKH = 1;
			try {
				String input = txtTTSLKH.getText().trim();
				if (!input.isEmpty()) {
					soLuongKH = Integer.parseInt(input);
				}
			} catch (NumberFormatException ex) {
				soLuongKH = banTamTinh.getSoLuongGhe();
			}
			CuaSoCapNhatDDBT_GUI dialog = new CuaSoCapNhatDDBT_GUI(this, thoiGianNhanBan, tongTienCoc, banTamTinh,
					khTamTinh, nv, maDDBT, soLuongKH, textAreaGhiChu.getText(), tblFormMonAn);
			dialog.setVisible(true);
			this.setVisible(false);

		});

		btnDaDen = taoButtonIcon("Đã đến", "", new Color(34, 153, 84), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnDaDen.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		btnDaDen.setPreferredSize(new Dimension(90, 50));
		btnDaDen.addActionListener(e -> {

			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn đánh dấu đơn này là đã đến ?",
					"Xác nhận đã đến", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {

				String maDDBDen = txtMaDon.getText().trim();
				HoaDon hoaDon = hoaDon_dao.getHoaDonTheoMaDonDatBanTruoc(maDDBDen);
				boolean ddbtden = DDBT_dao.updateTrangThai(maDDBDen, 1);
				LocalDateTime now = LocalDateTime.now();
				
				Ban b = hoaDon.getBan();
				boolean banDaDen = ban_dao.capNhatTrangThaiBan(b.getMaBan(), 2);
				ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
				dsCTHD = ctHD_dao.getChiTietHoaDonTheoMaHD(hoaDon.getMaHD());
				int totalQuantity = 0;
				for (ChiTietHoaDon ct : dsCTHD) {
				    totalQuantity += ct.getSoLuong();
				LocalDateTime thoiGianDuKien = modelDuDoanThoiGianAn.duDoanThoiGian(hoaDon.getSoKhach(), totalQuantity, LocalDateTime.now());
				hoaDon.setThoiGianThanhToan(thoiGianDuKien);
				boolean hddden = hoaDon_dao.updateTrangThaiVaThoiGianTaoDon(hoaDon.getMaHD(), 0, now,thoiGianDuKien);
				}
				
				

			}
			btnLamMoi.doClick();

		});
		JPanel pnlCapNhatDaDen = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlCapNhatDaDen.setOpaque(false);
		pnlCapNhatDaDen.add(btnCapNhat);
		pnlCapNhatDaDen.add(btnDaDen);
		pnlBtnGhiChuLuuDon.add(pnlCapNhatDaDen, BorderLayout.CENTER);

		btnCapNhat.setEnabled(false);
		btnDaDen.setEnabled(false);

		btnLuuDon = taoButtonIcon("Lưu đơn đặt bàn", "", new Color(255, 65, 65), Color.WHITE,
				new Font("Segoe UI", Font.BOLD, 14));
		btnLuuDon.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		btnLuuDon.setEnabled(false);
		btnLuuDon.setPreferredSize(new Dimension(btnLuuDon.getPreferredSize().width, 30));
		btnLuuDon.addActionListener(e -> {

			StringBuilder loi = new StringBuilder();

			Date gioDatecheck = (Date) spinnerGio.getValue();
			LocalTime localTimeCHeck = gioDatecheck.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalTime minTime = LocalTime.of(8, 0);
			LocalTime maxTime = LocalTime.of(22, 0);
			if (localTimeCHeck.isBefore(minTime) || localTimeCHeck.isAfter(maxTime)) {
				loi.append("- Chọn giờ từ 08:00 đến 22:00.\n");
			}

			if (txtTTDMaBan.getText().trim().isEmpty())
				loi.append("- Chọn bàn\n");
			if (txtTTSDTKH.getText().trim().isEmpty())
				loi.append("- Thêm thông tin khách hàng\n");
			else if (txtTTEmailKH.getText().trim().isEmpty())
				loi.append("- Thêm Email khách hàng\n");

			if (txtTTSLKH.getText().trim().isEmpty())
				loi.append("- Số lượng khách\n");
			if (loi.length() > 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng cập nhật đầy đủ:\n" + loi);
				return;
			} else {
				Date ngayDate = dateChooser.getDate();
				Date gioDate = (Date) spinnerGio.getValue();

				LocalDate localDate = ngayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalTime localTime = gioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
				LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);
				LocalDateTime thoiGianTao = LocalDateTime.now();
				Double tongTienCoc = Double.parseDouble(txtTTDTienCoc.getText().replace("VNĐ", "").replace(",", ""));
				Ban banTamTinh = ban_dao.getBan(txtTTDMaBan.getText());
				KhachHang khTamTinh = KH_dao.getKhachHangTheoSDTKhachHang(txtTTSDTKH.getText());

				String maDDBT = txtMaDon.getText().trim();
				int soLuongKH = 1;
				try {
					String input = txtTTSLKH.getText().trim();
					if (!input.isEmpty()) {
						soLuongKH = Integer.parseInt(input);
					}
				} catch (NumberFormatException ex) {
					soLuongKH = banTamTinh.getSoLuongGhe();
				}
				CuaSoThongTinDDBT_GUI dialog = new CuaSoThongTinDDBT_GUI(this, thoiGianNhanBan, tongTienCoc, banTamTinh,
						khTamTinh, nv, maDDBT, soLuongKH, textAreaGhiChu.getText(), tblFormMonAn);
				dialog.setVisible(true);
				this.setVisible(false);
			}

		});

		pnlBtnGhiChuLuuDon.add(btnLuuDon, BorderLayout.SOUTH);

		gbc.gridx = 1;
		gbc.weightx = 0.15;
		pnlTamTinhLuu.add(pnlBtnGhiChuLuuDon, gbc);

		pnlTTButton.add(pnlTamTinhLuu);

		return pnlTTButton;
	}

// Giao diện bên phải  bàn món ăn 
	private JPanel createRightPanelMenuMonAn() {
		JPanel pnlnMenuBanMonAnfn = new JPanel(new BorderLayout());
		pnlnMenuBanMonAnfn.setPreferredSize(new Dimension(widthSplitPane / 2 + 40, heightSplitPane));
		splitPane.setRightComponent(pnlnMenuBanMonAnfn);

		// Card Layout bàn món ăn
		pnlCardBanMonAn = new JPanel();
		cardLayoutBanMonAn = new CardLayout(0, 0);
		pnlCardBanMonAn.setLayout(cardLayoutBanMonAn);
		pnlnMenuBanMonAnfn.add(pnlCardBanMonAn, BorderLayout.CENTER);

		pnlCardBan = new JPanel(new BorderLayout());
		createpnlCardBan();
		pnlCardBanMonAn.add(pnlCardBan, "CardBan");

		pnlCardMonAn = new JPanel();

		pnlCardMonAn.setLayout(new BorderLayout());
		initUI_Menu_MonAn(pnlCardMonAn);
		pnlCardBanMonAn.add(pnlCardMonAn, "CardMonAn");
		JPanel pnlBtnBanMonAn = new JPanel(new GridLayout(1, 2));
		pnlnMenuBanMonAnfn.add(pnlBtnBanMonAn, BorderLayout.NORTH);

		btnBan = new JButton("Bàn ");
		btnMon = new JButton("Món ăn");
		Color selectedColor = new Color(0xFFA500);
		Color unselectedColor = new Color(0xFCE9CD);
		btnBan.setPreferredSize(new Dimension(0, 36));

		btnBan.setBackground(selectedColor);
		btnMon.setBackground(unselectedColor);

		btnBan.addActionListener(e -> {
			cardLayoutBanMonAn.show(pnlCardBanMonAn, "CardBan");
			((CardLayout) ((JPanel) pnlThongTin.getComponent(1)).getLayout()).show((JPanel) pnlThongTin.getComponent(1),
					"pnlCardTTBan");
			btnLuuDon.setEnabled(false);
			btnBan.setBackground(selectedColor);
			btnMon.setBackground(unselectedColor);
		});
		pnlBtnBanMonAn.add(btnBan);
		btnMon.addActionListener(e -> {
			if (txtTTDMaBan.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Chọn bàn trước");

				return;
			}

			cardLayoutBanMonAn.show(pnlCardBanMonAn, "CardMonAn");
			((CardLayout) ((JPanel) pnlThongTin.getComponent(1)).getLayout()).show((JPanel) pnlThongTin.getComponent(1),
					"CardTTMonAn");

			if (!txtMaDon.getText().trim().isEmpty()) {
				btnLuuDon.setEnabled(false);
			} else {
				if (mauBanChon.equals(new Color(0xACACAC))) {
					btnLuuDon.setEnabled(false);
				} else {
					btnLuuDon.setEnabled(true);
				}
			}

			txtTTDSKH.setText(txtTTSLKH.getText());
			txtTTDTienCoc.setText(txtTienCoc.getText());

			Double cocban = Double.parseDouble(txtTienCoc.getText().replace(" VNĐ", "").replace(",", ""));
			Double cocMon = Double.parseDouble(txtTTDTongTienCoc.getText().replace(" VNĐ", "").replace(",", ""));
			txtTTDTienCoc.setText(String.format("%,.0f VNĐ", cocban + cocMon));
			txtTTDSKH.setText(txtTTSLKH.getText());
			btnMon.setBackground(selectedColor);
			btnBan.setBackground(unselectedColor);
		});

		pnlBtnBanMonAn.add(btnMon);

		return pnlnMenuBanMonAnfn;
	}

	private void createpnlCardBan() {
		JPanel pnlNorthCardBan = new JPanel();
		pnlNorthCardBan.setLayout(new BoxLayout(pnlNorthCardBan, BoxLayout.Y_AXIS));
		pnlNorthCardBan.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 70));
		JPanel pnlThoiGian = new JPanel(new GridLayout(1, 3));
		pnlThoiGian.setPreferredSize(new Dimension(pnlNorthCardBan.getWidth(), 35));

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setMinSelectableDate(new Date());

		Calendar calendar = Calendar.getInstance();
		dateChooser.setDate(calendar.getTime());
		JTextField editorNgay = (JTextField) dateChooser.getDateEditor().getUiComponent();
		editorNgay.setForeground(Color.WHITE);
		editorNgay.setBackground(new Color(30, 129, 191));
		editorNgay.setCaretColor(Color.WHITE);
		editorNgay.setHorizontalAlignment(SwingConstants.CENTER);
		dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
			editorNgay.setForeground(Color.WHITE);
			editorNgay.setBackground(new Color(30, 129, 191));

			ArrayList<Ban> dsBanloc = locDanhSachBanTheoThoiGian(labelDangChonKVBan, 1);
			dsBan = dsBanloc;
			loadBanList.run();
		});

		pnlThoiGian.add(dateChooser);

		JPanel pnlGio = new JPanel(new BorderLayout());
		pnlGio.setBackground(new Color(30, 129, 191));
		pnlGio.setPreferredSize(new Dimension(100, 30));
		Date ngayGioThem15Phut = calendar.getTime();

		SpinnerDateModel model = new SpinnerDateModel(ngayGioThem15Phut, null, null, Calendar.MINUTE);
		spinnerGio = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerGio, "HH:mm");
		spinnerGio.setEditor(editor);

		spinnerGio.addChangeListener(e -> {
			ArrayList<Ban> dsBanloc = locDanhSachBanTheoThoiGian(labelDangChonKVBan, 1);
			dsBan = dsBanloc;
			loadBanList.run();
		});

		JTextField txtGio = editor.getTextField();
		txtGio.setBackground(new Color(30, 129, 191));
		txtGio.setForeground(Color.WHITE);
		txtGio.setCaretColor(Color.WHITE);
		txtGio.setBorder(null);
		txtGio.setHorizontalAlignment(SwingConstants.CENTER);
		txtGio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						spinnerGio.commitEdit();
						ArrayList<Ban> dsBanloc = locDanhSachBanTheoThoiGian(labelDangChonKVBan, 1);
						dsBan = dsBanloc;
						loadBanList.run();
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		// Icon đồng hồ
		ImageIcon rawIcon = new ImageIcon("resource\\icon\\clock.png");
		Image img = rawIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		JLabel lblIcon = new JLabel(new ImageIcon(img));
		lblIcon.setPreferredSize(new Dimension(44, 24));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		pnlGio.add(spinnerGio, BorderLayout.CENTER);
		pnlGio.add(lblIcon, BorderLayout.EAST);
		pnlThoiGian.add(pnlGio);
		pnlNorthCardBan.add(pnlThoiGian);

		JPanel pnlSLKhachHang = new JPanel();
		pnlSLKhachHang.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 35));
		JLabel lbSLKhachHang = new JLabel("Số lượng khách hàng:");
		pnlSLKhachHang.add(lbSLKhachHang);
		pnlSLKhachHang.add(txtSLKH = new JTextField(40));
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
				txtTTSLKH.setText(input);
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
		JPanel pnlCenterCardBan = new JPanel(new BorderLayout());
		pnlCenterCardBan.setBackground(Color.WHITE);

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
		JPanel pnlScrBan = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnlScrBan.setBackground(Color.WHITE);

		pnlScrBan.removeAll();
		pnlScrBan.revalidate();
		pnlScrBan.repaint();

		// Load DAO
		ban_dao = new Ban_DAO();
		dsBan = locDanhSachBanTheoThoiGianLoadDau();

		int itemWidth = 180;
		int itemHeight = 250;
		int hGap = 10;
		int vGap = 10;
		int columns = 4;
		int totalWidth = (itemWidth * columns) + (hGap * (columns + 1));

		selectedPanel = null;
		loadBanList = () -> {
			pnlScrBan.removeAll();
			for (Ban ban : dsBan) {
				int trang_thai = ban.getTrangThai();
				if (trang_thai == 4)
					continue;
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

		for (String labelChonKVBan : buttonLabelsBan) {
			JToggleButton btnChonKVBan = createToggleButton(labelChonKVBan);
			groupKVBan.add(btnChonKVBan);
			pnlBtnCardBan.add(btnChonKVBan);

			if (labelChonKVBan.equals("Tất cả"))
				btnTatCaBan = btnChonKVBan;

			btnChonKVBan.addActionListener(e -> {
				switch (labelChonKVBan) {
				case "Tất cả" -> {
					labelDangChonKVBan = "Tất cả";
				}
				case "Ngoài trời" -> {
					labelDangChonKVBan = "KV01";
				}
				case "Tầng 1" -> {
					labelDangChonKVBan = "KV02";
				}
				case "Tầng 2" -> {
					labelDangChonKVBan = "KV03";
				}
				case "Sân thượng" -> {
					labelDangChonKVBan = "KV04";
				}
				}
				ArrayList<Ban> dsBanloc = locDanhSachBanTheoThoiGian(labelDangChonKVBan, 1);
				dsBan = dsBanloc;
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
		loadBanList.run();

		JPanel pnlSouthCardBan = new JPanel(new BorderLayout());
		pnlSouthCardBan.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 100));
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
		JButton btnBanTatCa = new JButton();

		JPanel pnlbtnBanTrong = createPanelChuThich(btnBanTrong, new Color(0x8EF49A), "Bàn trống", 1);
		JPanel pnlbtnBanCoKhach = createPanelChuThich(btnBanCoKhach, new Color(0xFF8A8A), "Bàn có khách", 2);
		JPanel pnlbtnBanDaDat = createPanelChuThich(btnBanDaDat, new Color(0xACACAC), "Bàn đã đặt", 3);
		JPanel pnlbtnBanTatCa = createPanelChuThich(btnBanTatCa, new Color(0xFFFFFF), "Tất cả", 4);

		pnlbtnChuThich.add(pnlbtnBanTrong);
		pnlbtnChuThich.add(pnlbtnBanCoKhach);
		pnlbtnChuThich.add(pnlbtnBanDaDat);
		pnlbtnChuThich.add(pnlbtnBanTatCa);

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

	// btn trạng thái

	private JPanel createPanelChuThich(JButton btn, Color color, String labelText, int trangThai) {
		btn.setPreferredSize(new Dimension(20, 20));
		btn.setBackground(color);
		btn.setFocusable(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK)));

		btn.addActionListener(e -> {
			dsBan.clear();

			ArrayList<Ban> dsBanloc = locDanhSachBanTheoThoiGian(labelDangChonKVBan, trangThai);
			dsBan = dsBanloc;

			loadBanList.run();

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

		if (bgColor.equals(new Color(0xACACAC))) {
			Date ngayDate = dateChooser.getDate();
			Date gioDate = (Date) spinnerGio.getValue();
			LocalDate localDate = ngayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalTime localTime = gioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);

			String maTrung = DDBT_dao.getMaDonDatBanTruocTrungThoiGian(tenBan, thoiGianNhanBan);
			DonDatBanTruoc dontruoc = DDBT_dao.getDonDatBanTheoMa(maTrung);
			LocalDateTime thoiGianDon = dontruoc.getThoiGianNhanBan();

			int sl = dontruoc.getBan().getSoLuongGhe();
			int thoiGianDuKien = 50 + (sl - 1) * 30;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm ");
			String thoiGianStr = thoiGianDon.format(formatter);
			String thoiGianDuKienDat = thoiGianDon.minusMinutes(thoiGianDuKien).format(formatter);
			String thoiGianDuKienDat1 = thoiGianDon.plusMinutes(thoiGianDuKien).format(formatter);

			JLabel lblThoiGian = new JLabel(thoiGianStr, JLabel.CENTER);
			lblThoiGian.setForeground(Color.RED);

			JLabel lblThoiGianDuKien = new JLabel("Gợi ý: " + thoiGianDuKienDat + " - " + thoiGianDuKienDat1,
					JLabel.CENTER);
			lblThoiGianDuKien.setForeground(Color.RED);

			JPanel pnlThoiGian = new JPanel();
			pnlThoiGian.setLayout(new BoxLayout(pnlThoiGian, BoxLayout.Y_AXIS));
			pnlThoiGian.setOpaque(false);

			pnlThoiGian.add(lblThoiGian);
			pnlThoiGian.add(lblThoiGianDuKien);

			pnlCenterBan.add(pnlThoiGian, BorderLayout.NORTH);

		}
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

		btnChiTiet.setEnabled(false);
		if (bgColor.equals(new Color(0xACACAC))) {
			btnChiTiet.setEnabled(true);
		}
		btnChiTiet.addActionListener(e -> {

			pnlbtnBan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			btnCapNhat.setEnabled(true);
			btnDaDen.setEnabled(true);

			btnLuuDon.setEnabled(false);

			Date ngayDate = dateChooser.getDate();
			Date gioDate = (Date) spinnerGio.getValue();
			LocalDate localDate = ngayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalTime localTime = gioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);
			String maTrung = DDBT_dao.getMaDonDatBanTruocTrungThoiGian(tenBan, thoiGianNhanBan);
			if (maTrung == null) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy đơn đặt bàn trước phù hợp!");
				return;
			}
			DonDatBanTruoc ddbt = DDBT_dao.getDonDatBanTheoMa(maTrung);
			HoaDon hoaDon = hoaDon_dao.getHoaDonTheoMaDonDatBanTruoc(maTrung);
			Ban ban = ban_dao.getBanById(ddbt.getBan().getMaBan());
			KhachHang kh = khachHang_dao.getKhachHangById(ddbt.getKH().getMaKH());
			txtMaBanBan.setText(ban.getMaBan());
			KhuVuc kv = khuVuc_dao.getKhuVucTheoMa(ban.getKV().getMaKV());

			txtKhuVuc.setText(kv.getTenKV());

			txtSLGhe.setText(String.valueOf(ban.getSoLuongGhe()));
			txtTienCoc.setText(String.format("%,.0f VNĐ", ban.getSoLuongGhe() * 500000 * 0.1));

			txtTTDMaBan.setText(ban.getMaBan());
			txtTTDSKH.setText(String.valueOf(ddbt.getSoLuongKhach()));
			txtTTDTenKH.setText(kh.getTenKH());

			txtTTTenKH.setText(kh.getTenKH());
			txtTTEmailKH.setText(kh.getEmailKH());
			txtTTSDTKH.setText(kh.getSdtKH());

			textAreaGhiChu.setText(hoaDon.getChuThich());

			txtTTSLKH.setText(String.valueOf(ddbt.getSoLuongKhach()));
			txtMaDon.setText(maTrung);

			DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
			model.setRowCount(0);
			ArrayList<ChiTietHoaDon> dsMonAn = ctHD_dao.layDanhSachChiTietTheoMaHD(hoaDon.getMaHD());

			ImageIcon iconXoaMon = new ImageIcon("resource/icon/Xoa.png");
			ImageIcon iconGhiChu = new ImageIcon("resource/icon/GhiChu.png");
			ImageIcon iconCongSL = new ImageIcon("resource/icon/Tang.png");
			ImageIcon iconTruSL = new ImageIcon("resource/icon/Giam.png");
			for (ChiTietHoaDon cthd : dsMonAn) {
				MonAn ma = monAn_dao.timTenMonAnVaGiaMonAnTheoMa(cthd.getMonAn().getMaMonAn());
				String ghiChu = cthd.getGhiChu();

				String cellTenMon;
				if (ghiChu != null && !ghiChu.trim().isEmpty()) {
					cellTenMon = "<html>" + ma.getTenMonAn() + "<br><i style='color:rgb(129,14,202)'>" + ghiChu
							+ "</i></html>";
				} else {
					cellTenMon = ma.getTenMonAn();
				}

				Object[] rowData = { iconXoaMon, cellTenMon, iconTruSL, cthd.getSoLuong(), iconCongSL,
						String.format("%,.0f VNĐ", ma.getDonGia()),
						String.format("%,.0f VNĐ", ma.getDonGia() * cthd.getSoLuong()), iconGhiChu };

				model.addRow(rowData);
			}

			tinhTongTien(tblFormMonAn);
			txtTTDTongTienCoc.setText(txtTTDTienCoc.getText());

		});
		pnlbtnBan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mauBanChon = bgColor;
				System.out.println(mauBanChon + "");
				Ban banChon = ban_dao.getBan(tenBan);
				KhuVuc kvChon = khuVuc_dao.getKhuVucTheoMa(banChon.getKV().getMaKV());

				txtMaBanBan.setText(banChon.getMaBan());
				txtTTDMaBan.setText(banChon.getMaBan());
				txtKhuVuc.setText(kvChon.getTenKV());
				txtSLGhe.setText(Integer.toString(banChon.getSoLuongGhe()));
				double tienCoc = 50000 * banChon.getSoLuongGhe();

				txtTienCoc.setText(String.format("%,.0f VNĐ", tienCoc));

				if (selectedPanel != null) {
					selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
				pnlbtnBan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
				selectedPanel = pnlbtnBan;

			}

			private String String(String maKV) {
				return null;
			}

		});

		pnlbtnBan.add(pnlTenBan, BorderLayout.NORTH);
		pnlbtnBan.add(pnlCenterBan, BorderLayout.CENTER);
		pnlbtnBan.add(pnlChiTiet, BorderLayout.SOUTH);

		return pnlbtnBan;
	}

	private void initUI_Menu_MonAn(JPanel pnl) {
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));

		JPanel pnlTimMon = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTimMon.setBackground(new Color(194, 232, 255));
		JLabel lblTenMon;
		pnlTimMon.add(lblTenMon = new JLabel("Tên món ăn:"));
		lblTenMon.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 30));

		txtTimMon = new JTextField(40);
		pnlTimMon.add(txtTimMon);

		btnTimMon = new JButton("Tìm");
		btnTimMon.setFocusPainted(false);
		pnlTimMon.add(btnTimMon);
		pnl.add(pnlTimMon);

		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
		pnlButton.setPreferredSize(new Dimension(0, 100));
		ButtonGroup group = new ButtonGroup();
		btnTatCaMon = createToggleButton("TẤT CẢ");
		btnDiemTam = createToggleButton("ĐIỂM TÂM");
		btnGoi = createToggleButton("GỎI");
		btnLauCom = createToggleButton("LẨU - CƠM");
		btnCanhRau = createToggleButton("CANH - RAU");
		btnHaiSan = createToggleButton("HẢI SẢN");
		btnMonThem = createToggleButton("MÓN THÊM");
		btnSoup = createToggleButton("SOUP - TIỀM - CHÁO");
		btnGaBoHeoEch = createToggleButton("GÀ - BÒ - HEO - ẾCH - LƯƠN");
		btnMiHuTieu = createToggleButton("MÌ - MIẾN - HỦ TIẾU");
		btnNuocEp = createToggleButton("SINH TỐ - NƯỚC ÉP");

		group.add(btnTatCaMon);
		group.add(btnDiemTam);
		group.add(btnGoi);
		group.add(btnLauCom);
		group.add(btnCanhRau);
		group.add(btnHaiSan);
		group.add(btnMonThem);
		group.add(btnSoup);
		group.add(btnGaBoHeoEch);
		group.add(btnMiHuTieu);
		group.add(btnNuocEp);

		pnlButton.add(btnTatCaMon);
		pnlButton.add(btnDiemTam);
		pnlButton.add(btnGoi);
		pnlButton.add(btnLauCom);
		pnlButton.add(btnCanhRau);
		pnlButton.add(btnHaiSan);
		pnlButton.add(btnMonThem);
		pnlButton.add(btnSoup);
		pnlButton.add(btnGaBoHeoEch);
		pnlButton.add(btnMiHuTieu);
		pnlButton.add(btnNuocEp);

		btnTatCaMon.addActionListener(this);
		btnDiemTam.addActionListener(this);
		btnGoi.addActionListener(this);
		btnLauCom.addActionListener(this);
		btnCanhRau.addActionListener(this);
		btnHaiSan.addActionListener(this);
		btnMonThem.addActionListener(this);
		btnSoup.addActionListener(this);
		btnGaBoHeoEch.addActionListener(this);
		btnMiHuTieu.addActionListener(this);
		btnNuocEp.addActionListener(this);
		btnTimMon.addActionListener(this);

		btnTatCaMon.setSelected(true);
		pnl.add(pnlButton);

		cardLayoutButton = new CardLayout();
		pnlCardButton = new JPanel(cardLayoutButton);
		pnl.add(pnlCardButton);
		btnTatCaMon.doClick();

	}

	private JPanel foodMenuUI(List<MonAn> dsMonAn) {

		JPanel pnlListMonAn = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnlListMonAn.setBackground(Color.WHITE);

		for (MonAn mon : dsMonAn) {
			JPanel pnlMon = taoUIPanelMonAn(mon);
			pnlListMonAn.add(pnlMon);
		}
		int itemWidth = 230;
		int itemHeight = 150;
		int hGap = 10;
		int vGap = 10;
		int columns = 3;

		int totalItems = pnlListMonAn.getComponentCount();
		int rows = (int) Math.ceil((double) totalItems / columns);

		int totalWidth = (itemWidth * columns) + (hGap * (columns + 1));
		int totalHeight = (itemHeight * rows) + (vGap * (rows + 1));

		pnlListMonAn.setPreferredSize(new Dimension(totalWidth, totalHeight));

		JScrollPane scrPane = new JScrollPane(pnlListMonAn);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setPreferredSize(new Dimension(0, 450));
		wrapper.add(scrPane, BorderLayout.CENTER);

		return wrapper;
	}

	private JPanel taoUIPanelMonAn(MonAn mon) {
		String ten = mon.getTenMonAn();
		Double gia = mon.getDonGia();
		int trangThaiMon = mon.getTrangThai();

		ImageIcon icon = null;
		if (mon.getHinhMonAn() != null) {
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(mon.getHinhMonAn());
				BufferedImage bimg = ImageIO.read(bais);
				if (bimg != null) {
					Image scaled = bimg.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
					icon = new ImageIcon(scaled);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return createFoodItem(ten, gia, icon, trangThaiMon);
	}

	private JPanel createFoodItem(String name, Double price, ImageIcon icon, int trangThaiMon) {
		JPanel pnlItem = new JPanel(null);
		pnlItem.setPreferredSize(new Dimension(230, 150));
		pnlItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pnlItem.setBackground(Color.WHITE);

		// Checkbox ở góc trên trái => Thông báo Hết Món
		JCheckBox chkHetMon = new JCheckBox();
		chkHetMon.setBounds(5, 5, 20, 20);

		if (trangThaiMon == 1) {
			chkHetMon.setSelected(false);
		} else if (trangThaiMon == 0) {
			chkHetMon.setSelected(true);
			hienThiHetMon(pnlItem);
		}

		pnlItem.add(chkHetMon);
		final boolean[] allowClick = { true }; // mặc định cho click
		chkHetMon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isChecked = chkHetMon.isSelected();
				allowClick[0] = !isChecked;

				if (chkHetMon.isSelected()) {
					hienThiHetMon(pnlItem);

					pnlItem.addMouseListener(new MouseAdapter() {
					});
					pnlItem.addMouseMotionListener(new MouseMotionAdapter() {
					});

					if (monAn_dao.capNhatTrangThaiMonAn(name, 0)) {
						JOptionPane.showMessageDialog(null, "Cập nhật trạng thái hết món ăn thành công!");
					} else {
						JOptionPane.showMessageDialog(null, "Cập nhật trạng thái hết món ăn thất bại!");
					}
				} else {
					// Khôi phục lại nền panel món ăn
					for (Component comp : pnlItem.getComponents()) {
						if ("overlayHetMon".equals(comp.getName()) || "glassPane".equals(comp.getName())) {
							pnlItem.remove(comp);
						}
					}

					pnlItem.setOpaque(true);
					pnlItem.setBackground(Color.WHITE);
					pnlItem.revalidate();
					pnlItem.repaint();

					if (monAn_dao.capNhatTrangThaiMonAn(name, 1)) {
						JOptionPane.showMessageDialog(null, "Cập nhật trạng thái món ăn thành công!");
					} else {
						JOptionPane.showMessageDialog(null, "Cập nhật trạng thái món ăn thất bại!");
					}
				}

			}
		});

		JLabel lblAnh = new JLabel();
		lblAnh.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnh.setBounds(26, 10, 180, 60);
		if (icon != null) {
			lblAnh.setIcon(icon);
		}
		pnlItem.add(lblAnh);

		// Tên món ăn
		JLabel lblTen = new JLabel(name, SwingConstants.CENTER);
		lblTen.setBounds(20, 75, 180, 20);
		pnlItem.add(lblTen);

		NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String gia = vndFormat.format(price);
		JLabel lblGia = new JLabel("Đơn giá: " + gia, SwingConstants.CENTER);
		lblGia.setForeground(Color.RED);
		lblGia.setBounds(20, 95, 200, 20);
		pnlItem.add(lblGia);
		ImageIcon iconXoaMon = new ImageIcon("resource/icon/Xoa.png");
		ImageIcon iconGhiChu = new ImageIcon("resource/icon/GhiChu.png");
		ImageIcon iconCongSL = new ImageIcon("resource/icon/Tang.png");
		ImageIcon iconTruSL = new ImageIcon("resource/icon/Giam.png");

		// Renderer để hiển thị ImageIcon Xóa và Ghi Chú trong JTable
		DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel lbl = new JLabel();
				if (column == 2) {
					lbl.setHorizontalAlignment(SwingConstants.RIGHT);
				} else if (column == 4) {
					lbl.setHorizontalAlignment(SwingConstants.LEFT);
				} else {
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
				}

				if (value instanceof Icon) {
					lbl.setIcon((Icon) value);
				}

				if (isSelected) {
					lbl.setBackground(new Color(30, 129, 191));
					lbl.setOpaque(true);
				} else {
					Color bg = (row % 2 == 0) ? Color.WHITE : new Color(204, 229, 255);
					lbl.setBackground(bg);
					lbl.setOpaque(true);
				}

				return lbl;
			}
		};
		tblFormMonAn.getColumnModel().getColumn(0).setCellRenderer(iconRenderer);
		tblFormMonAn.getColumnModel().getColumn(2).setCellRenderer(iconRenderer);
		tblFormMonAn.getColumnModel().getColumn(4).setCellRenderer(iconRenderer);
		tblFormMonAn.getColumnModel().getColumn(7).setCellRenderer(iconRenderer);

		pnlItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

//				
				if (chkHetMon.isSelected()) {
					JOptionPane.showMessageDialog(null, "Món này đã hết!");
					return;
				}

				if (!allowClick[0])
					return;

				DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
				boolean daTonTai = false;
				for (int i = 0; i < model.getRowCount(); i++) {
					String tenTrongBang = model.getValueAt(i, 1).toString();
					if (tenTrongBang.equals(name)) {
						int sl = Integer.parseInt(model.getValueAt(i, 3).toString());
						sl++;
						model.setValueAt(String.valueOf(sl), i, 3);
						model.setValueAt(String.format("%,.0f VNĐ", sl * price), i, 6);
						tinhTongTien(tblFormMonAn);
						daTonTai = true;
						break;
					}

				}

				if (selectedPanel != null) {
					selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
				pnlItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
				selectedPanel = pnlItem;

				if (!daTonTai) {
					Object[] rowData = { iconXoaMon, name, iconTruSL, "1", iconCongSL,
							String.format("%,.0f VNĐ", price), String.format("%,.0f VNĐ", price), iconGhiChu // cột 7
					};

					model.addRow(rowData);
				}

				tinhTongTien(tblFormMonAn);

			}
		});

		return pnlItem;

	}

	private void hienThiHetMon(JPanel pnlItem) {
		pnlItem.setOpaque(false);
		pnlItem.setBackground(new Color(255, 255, 255, 10));

		JPanel pnlOverlay = new JPanel(new BorderLayout());
		pnlOverlay.setBackground(new Color(255, 0, 0, 180));
		pnlOverlay.setBounds(0, 30, 230, 50);
		pnlOverlay.setName("overlayHetMon");

		JLabel lblOut = new JLabel("Hết món", SwingConstants.CENTER);
		lblOut.setForeground(Color.WHITE);
		lblOut.setFont(new Font("Arial", Font.BOLD, 16));
		pnlOverlay.add(lblOut, BorderLayout.CENTER);

		pnlOverlay.setOpaque(true);
		pnlOverlay.setVisible(true);

		pnlItem.setLayout(null);
		pnlItem.add(pnlOverlay);
		pnlItem.setComponentZOrder(pnlOverlay, 0);

		pnlItem.revalidate();
		pnlItem.repaint();
	}

	public void tinhTongTien(JTable tblForm) {
		double tong = 0;
		int sl = 0;
		DefaultTableModel model = (DefaultTableModel) tblForm.getModel();

		for (int i = 0; i < model.getRowCount(); i++) {
			String thanhTienStr = model.getValueAt(i, 6).toString();
			thanhTienStr = thanhTienStr.replace("VNĐ", "").replace(",", "").trim();

			try {
				double thanhTien = Double.parseDouble(thanhTienStr);
				tong += thanhTien;
				sl += Integer.parseInt(model.getValueAt(i, 3).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		txtTTDTongTienMon.setText(String.format("%,.0f VNĐ", tong));
		txtTTDTongTienCoc.setText(String.format("%,.0f VNĐ", tong * 0.1));
		String textCoc = txtTienCoc.getText();

		Double cocban = 0.0;
		if (textCoc != null && !textCoc.trim().isEmpty()) {
			try {
				cocban = Double.parseDouble(textCoc.replace(" VNĐ", "").replace(",", "").trim());
			} catch (NumberFormatException e) {
				cocban = 0.0;
			}
		}

		txtTTDTienCoc.setText(String.format("%,.0f VNĐ", cocban + tong * 0.1));
		txtTTDSoLuongMon.setText(sl + "");
		txtTTDSoMon.setText(model.getRowCount() + "");

	}

	private JPanel createLabelTextFieldRow(String labelText, JTextField textField, boolean editable, int labelSpacing,
			int textFieldColumns, boolean hasBorder) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createHorizontalStrut(10));

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(label);

		panel.add(Box.createHorizontalStrut(labelSpacing));

		textField.setColumns(textFieldColumns);
		textField.setFont(label.getFont());
		textField.setEditable(editable);
		if (!hasBorder) {
			textField.setBorder(null);
			textField.setCaretColor(null);
			textField.setCaretColor(textField.getBackground());
		}
		panel.add(textField);

		return panel;
	}

	public JButton taoButtonIcon(String text, String iconPath, Color background, Color foreground, Font font) {
		ImageIcon icon = new ImageIcon(iconPath);
		JButton button = new JButton(" " + text, icon);
		button.setBackground(background);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		button.setFont(font);
		button.setBorderPainted(true);
		button.setHorizontalAlignment(SwingConstants.CENTER);

		return button;
	}

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

	private JButton createCustomButton(String text) {
		JButton btn = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isEnabled()) {
					createCustomButtonBackground(g, this, true, false, false);
				} else {
					createCustomButtonBackground(g, this, false, getModel().isRollover(), getModel().isPressed());
				}
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

	private void createCustomButtonBackground(Graphics g, JButton btn, boolean isDisabled, boolean isHover,
			boolean isPressed) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color backgroundColor;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnTatCaMon) {
			ArrayList<MonAn> dsMon = monAn_dao.getAllMonAn();
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.removeAll();
			pnlCardButton.add(pnlMon, "TatCa");
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			cardLayoutButton.show(pnlCardButton, "TatCa");
		} else if (o == btnDiemTam) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Điểm tâm");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "DiemTam");
			cardLayoutButton.show(pnlCardButton, "DiemTam");
		} else if (o == btnGoi) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Gỏi");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Goi");
			cardLayoutButton.show(pnlCardButton, "Goi");
		} else if (o == btnLauCom) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Lẩu-cơm");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Goi");
			cardLayoutButton.show(pnlCardButton, "Goi");
		} else if (o == btnCanhRau) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Canh-Rau");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "CanhRau");
			cardLayoutButton.show(pnlCardButton, "CanhRau");
		} else if (o == btnHaiSan) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Hải sản");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "HaiSan");
			cardLayoutButton.show(pnlCardButton, "HaiSan");
		} else if (o == btnMonThem) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Món Thêm");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "MonThem");
			cardLayoutButton.show(pnlCardButton, "MonThem");
		} else if (o == btnSoup) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Soup-Tiềm-Cháo");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Soup");
			cardLayoutButton.show(pnlCardButton, "Soup");
		} else if (o == btnGaBoHeoEch) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Gà-Bò-Heo-Ếch-Lươn");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Ga");
			cardLayoutButton.show(pnlCardButton, "Ga");
		} else if (o == btnMiHuTieu) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Mì-Miến-Hủ tiếu xào");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Mi");
			cardLayoutButton.show(pnlCardButton, "Mi");
		} else if (o == btnNuocEp) {
			ArrayList<MonAn> dsMon = monAn_dao.locMonAnTheoTenLoai_SP("Sinh tố-Nước ép");
			JPanel pnlMon = foodMenuUI(dsMon);
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			pnlCardButton.add(pnlMon, "Sinh-to");
			cardLayoutButton.show(pnlCardButton, "Sinh-to");
		} else if (o == btnTimMon) {
			String text = txtTimMon.getText().trim();
			ArrayList<MonAn> dsMon = monAn_dao.getMonAnTheoTenMon(text);
			JPanel pnlMon = foodMenuUI(dsMon);

			pnlCardButton.removeAll();

			pnlCardButton.add(pnlMon, "TimMon");
			pnlCardButton.revalidate();
			pnlCardButton.repaint();
			cardLayoutButton.show(pnlCardButton, "TimMon");
		} else if (o == btnXoaMonAn) {
			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá hết món này?", "Xác nhận xoá",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
				model.setRowCount(0);
				tinhTongTien(tblFormMonAn);
			}
		}
	}

	public ArrayList<Ban> locDanhSachBanTheoThoiGian(String labelDangChonKVBanfunc, int trangThaifunc) {
		ArrayList<Ban> dsBanLoc = new ArrayList<>();
		ArrayList<Ban> dsBanTheoKV = new ArrayList<Ban>();
		Date ngayDate = dateChooser.getDate();
		Date gioDate = (Date) spinnerGio.getValue();
		LocalDate localDate = ngayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime localTime = gioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);
		ArrayList<Ban> dsBiTrung = ban_dao.getBanDaBiTrungThoiGian(thoiGianNhanBan);
		if (labelDangChonKVBanfunc.equals("Tất cả")) {
			dsBanTheoKV = ban_dao.getAllBan();
		} else {
			dsBanTheoKV = ban_dao.getBanTheoKhuVuc(labelDangChonKVBanfunc);
		}

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowMinus5m = now.minusMinutes(5);
		LocalDateTime nowPlus3h = now.plusHours(3);

		boolean trongKhoangThoiGian = !thoiGianNhanBan.isBefore(nowMinus5m) && !thoiGianNhanBan.isAfter(nowPlus3h)
				&& thoiGianNhanBan.toLocalDate().equals(LocalDate.now());
		if (trongKhoangThoiGian) {

			if (dsBiTrung.isEmpty()) {
				dsBanLoc = dsBanTheoKV;
			} else {
				for (Ban ban : dsBanTheoKV) {
					boolean biTrung = false;

					for (Ban biTrungBan : dsBiTrung) {
						if (ban.getMaBan().equals(biTrungBan.getMaBan())) {
							ban.setTrangThai(3);
							dsBanLoc.add(ban);

							biTrung = true;
							break;
						}
					}

					if (!biTrung) {
						dsBanLoc.add(ban); // không bị trùng thời gian
					}
				}
			}

		} else {
			if (dsBiTrung.isEmpty()) {
				for (Ban ban : dsBanTheoKV) {
					ban.setTrangThai(1);
					dsBanLoc.add(ban);
				}

			} else {
				for (Ban ban : dsBanTheoKV) {
					boolean biTrung = false;

					for (Ban biTrungBan : dsBiTrung) {
						if (ban.getMaBan().equals(biTrungBan.getMaBan())) {
							ban.setTrangThai(3);
							dsBanLoc.add(ban);
							biTrung = true;
							break;
						}
					}

					if (!biTrung) {
						ban.setTrangThai(1);
						dsBanLoc.add(ban);
					}
				}

			}

		}
		if (trangThaifunc != 4)
			dsBanLoc = ban_dao.locBanTheoTrangThai(dsBanLoc, trangThaifunc);

		return dsBanLoc;
	}

	public ArrayList<Ban> locDanhSachBanTheoThoiGianLoadDau() {
		ArrayList<Ban> dsBanLoc = new ArrayList<>();
		ArrayList<Ban> dsBanTheoKV = new ArrayList<Ban>();
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now().plusMinutes(15);
		LocalDateTime thoiGianNhanBan = LocalDateTime.of(localDate, localTime);
		ArrayList<Ban> dsBiTrung = ban_dao.getBanDaBiTrungThoiGian(thoiGianNhanBan);

		dsBanTheoKV = ban_dao.getAllBan();

		if (dsBiTrung.isEmpty()) {
			dsBanLoc = dsBanTheoKV;
		} else {

			// Xử lý trạng thái bàn
			for (Ban ban : dsBanTheoKV) {
				boolean biTrung = false;

				for (Ban biTrungBan : dsBiTrung) {
					if (ban.getMaBan().equals(biTrungBan.getMaBan())) {
						ban.setTrangThai(3);
						dsBanLoc.add(ban);
						biTrung = true;
						break;
					}
				}

				if (!biTrung) {
					dsBanLoc.add(ban);
				}
			}

		}

		dsBanLoc = ban_dao.locBanTheoTrangThai(dsBanLoc, 1);
		return dsBanLoc;
	}

	public void loadThongTinDonDatBanTruoc(String maTrung) {
		DonDatBanTruoc ddbt = DDBT_dao.getDonDatBanTheoMa(maTrung);
		if (ddbt == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy đơn đặt bàn trước với mã: " + maTrung, "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		HoaDon hoaDon = hoaDon_dao.getHoaDonTheoMaDonDatBanTruoc(maTrung);
		if (hoaDon == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn tương ứng với đơn đặt bàn: " + maTrung, "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Ban ban = ban_dao.getBanById(ddbt.getBan().getMaBan());
		KhachHang kh = khachHang_dao.getKhachHangById(ddbt.getKH().getMaKH());
		KhuVuc kv = khuVuc_dao.getKhuVucTheoMa(ban.getKV().getMaKV());
		LocalDateTime thoiGianDatNhanBan = ddbt.getThoiGianNhanBan();
		Date date = Date.from(thoiGianDatNhanBan.atZone(ZoneId.systemDefault()).toInstant());

		dateChooser.setDate(date);
		spinnerGio.setValue(date);
		txtMaBanBan.setText(ban.getMaBan());
		txtKhuVuc.setText(kv.getTenKV());
		txtSLGhe.setText(String.valueOf(ban.getSoLuongGhe()));
		txtTienCoc.setText(String.format("%,.0f VNĐ", ban.getSoLuongGhe() * 500_000 * 0.1));

		txtTTDMaBan.setText(ban.getMaBan());
		txtTTDSKH.setText(String.valueOf(ddbt.getSoLuongKhach()));
		txtTTDTenKH.setText(kh.getTenKH());

		txtTTTenKH.setText(kh.getTenKH());
		txtTTEmailKH.setText(kh.getEmailKH());
		txtTTSDTKH.setText(kh.getSdtKH());

		textAreaGhiChu.setText(hoaDon.getChuThich());
		txtTTSLKH.setText(String.valueOf(ddbt.getSoLuongKhach()));
		txtMaDon.setText(maTrung);

		// 5. Load danh sách món ăn đã đặt
		DefaultTableModel model = (DefaultTableModel) tblFormMonAn.getModel();
		model.setRowCount(0); // Clear table

		ArrayList<ChiTietHoaDon> dsMonAn = ctHD_dao.layDanhSachChiTietTheoMaHD(hoaDon.getMaHD());

		ImageIcon iconXoaMon = new ImageIcon("resource/icon/Xoa.png");
		ImageIcon iconGhiChu = new ImageIcon("resource/icon/GhiChu.png");
		ImageIcon iconCongSL = new ImageIcon("resource/icon/Tang.png");
		ImageIcon iconTruSL = new ImageIcon("resource/icon/Giam.png");

		for (ChiTietHoaDon cthd : dsMonAn) {
			MonAn ma = monAn_dao.timTenMonAnVaGiaMonAnTheoMa(cthd.getMonAn().getMaMonAn());
			String ghiChu = cthd.getGhiChu();

			String cellTenMon;
			if (ghiChu != null && !ghiChu.trim().isEmpty()) {
				cellTenMon = "<html>" + ma.getTenMonAn() + "<br><i style='color:rgb(129,14,202)'>" + ghiChu
						+ "</i></html>";
			} else {
				cellTenMon = ma.getTenMonAn();
			}

			Object[] rowData = { iconXoaMon, cellTenMon, iconTruSL, cthd.getSoLuong(), iconCongSL,
					String.format("%,.0f VNĐ", ma.getDonGia()),
					String.format("%,.0f VNĐ", ma.getDonGia() * cthd.getSoLuong()), iconGhiChu };

			model.addRow(rowData);
		}

		btnCapNhat.setEnabled(true);
		btnDaDen.setEnabled(true);

		btnLuuDon.setEnabled(false);
		tinhTongTien(tblFormMonAn);
		txtTTDTongTienCoc.setText(txtTTDTienCoc.getText());
	}

	public JButton getBtnLamMoi() {
		return btnLamMoi;
	}

}
