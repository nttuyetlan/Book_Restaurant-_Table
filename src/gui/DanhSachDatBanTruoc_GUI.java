package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
//import dao.ChiTietDatMonTruoc_DAO;
import dao.DonDatBanTruoc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
//import entity.ChiTietDatMonTruoc;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.MonAn;
import entity.NhanVien;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class DanhSachDatBanTruoc_GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	private JPanel pnlContent, pnlSearch, pnlTable, pnlButtons;
	private JTable tblReservations;
	private DefaultTableModel modelDonDatBan;
	private JLabel lblTitle;
	private JTextField txtSearchInfo;
	private JComboBox<String> cmbSearchType, cmbStatus;
	private JButton btnSearch, btnUpdate, btnCancel, btnContactCustomer;
	private JDateChooser dateCreationTime, dateReceiveTime;

	private DonDatBanTruoc_DAO donDatBanTruocDAO;
	private KhachHang_DAO khachHangDAO;
	private NhanVien_DAO nhanVienDAO;
	private Ban_DAO banDAO;
	private ArrayList<DonDatBanTruoc> dsDonDatBanTruoc;

	private final Color PRIMARY_HEADER_START = new Color(30, 129, 191);
	private final Color PRIMARY_HEADER_END = new Color(25, 105, 170);
	private final Color BACKGROUND_PANEL = new Color(245, 245, 245);
	private final Color CONTENT_PANEL = Color.WHITE;
	private final Color BORDER_COLOR = new Color(220, 220, 220);
	private final Color TABLE_HEADER = new Color(120, 220, 120);
	private final Color BUTTON_GREEN = new Color(46, 175, 80);
	private final Color BUTTON_BLUE = new Color(30, 129, 191);
	private final Color BUTTON_BLUE_HOVER = new Color(50, 150, 220);

	private final Color BUTTON_GREEN_HOVER = new Color(38, 160, 70);
	private final Color BUTTON_RED = new Color(239, 83, 80);
	private final Color BUTTON_RED_HOVER = new Color(255, 99, 95);
	private final Color BUTTON_GRAY = new Color(100, 110, 120);
	private final Color BUTTON_GRAY_HOVER = new Color(80, 90, 100);
	private JButton btnDetail;
	private NhanVien nv;

	private static final int STATUS_CHUA_DEN = 0;  
	private static final int STATUS_DA_DEN = 1;   
	private static final int STATUS_DA_HUY = 2;  
	private MenuQuanLy_GUI parentMenu;


	private JButton btnReFresh;

	public DanhSachDatBanTruoc_GUI(NhanVien nv, MenuQuanLy_GUI parentMenu) {
	    this.nv = nv;
	    this.parentMenu = parentMenu;
	    // Initialize DAOs and list
	    donDatBanTruocDAO = new DonDatBanTruoc_DAO();
	    khachHangDAO = new KhachHang_DAO();
	    nhanVienDAO = new NhanVien_DAO();
	    banDAO = new Ban_DAO();
	    dsDonDatBanTruoc = new ArrayList<>();
	    EmailReminderScheduler.getInstance().startScheduler();
	}
	
	public DanhSachDatBanTruoc_GUI(NhanVien nv) {
		setTitle("Hệ Thống Quản Lý Đặt Bàn Nhà Hàng");
		this.nv = nv;
		donDatBanTruocDAO = new DonDatBanTruoc_DAO();
		khachHangDAO = new KhachHang_DAO();
		nhanVienDAO = new NhanVien_DAO();
		banDAO = new Ban_DAO();
		dsDonDatBanTruoc = new ArrayList<>();
		EmailReminderScheduler.getInstance().startScheduler();
		
	}

	public JPanel initUI() {
		JPanel pnlDSDBBT = new JPanel(new BorderLayout());
		initUI_Content();
		pnlDSDBBT.add(pnlContent, BorderLayout.CENTER);
		loadDonDatBanTruocData();
		return pnlDSDBBT;
	}

	private void initUI_Content() {
		pnlContent = new JPanel();
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		pnlContent.setBackground(BACKGROUND_PANEL);
		pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel headerPanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.setPaint(new GradientPaint(0, 0, PRIMARY_HEADER_START, 0, getHeight(), PRIMARY_HEADER_END));
				g2d.fillRect(0, 0, getWidth(), getHeight());

				g2d.setColor(new Color(255, 255, 255, 60));
				for (int i = 0; i < 7; i++) {
					int size = 8 + (i * 3);
					g2d.fillOval(20 + (i * 25), getHeight() / 2 - size / 2, size, size);
				}

				for (int i = 0; i < 7; i++) {
					int size = 8 + (i * 3);
					g2d.fillOval(getWidth() - 40 - (i * 25), getHeight() / 2 - size / 2, size, size);
				}

				g2d.setColor(new Color(255, 255, 255, 30));
				g2d.setStroke(new BasicStroke(1.5f));
				g2d.drawLine(150, 5, getWidth() - 150, 5);
				g2d.drawLine(150, getHeight() - 5, getWidth() - 150, getHeight() - 5);

				g2d.setColor(new Color(255, 255, 255, 40));
				g2d.setStroke(new BasicStroke(2f));
				g2d.drawArc(5, 5, 30, 30, 180, 90);
				g2d.drawArc(getWidth() - 35, 5, 30, 30, 270, 90);
				g2d.drawArc(5, getHeight() - 35, 30, 30, 90, 90);
				g2d.drawArc(getWidth() - 35, getHeight() - 35, 30, 30, 0, 90);
			}
		};

		// Reduce the header panel height if needed - adjust based on preference
		headerPanel.setPreferredSize(new Dimension(0, 35));
		lblTitle = new JLabel("DANH SÁCH ĐƠN ĐẶT TRƯỚC", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTitle.setForeground(Color.WHITE);
		headerPanel.add(lblTitle, BorderLayout.CENTER);
		pnlContent.add(headerPanel);
		pnlContent.add(Box.createVerticalStrut(10));

		// Search Panel
		pnlSearch = new JPanel(new BorderLayout());
		pnlSearch.setBackground(CONTENT_PANEL);
		pnlSearch.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER_COLOR),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		pnlSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

		JPanel pnlSearchContent = new JPanel(new GridLayout(2, 3, 10, 10));
		pnlSearchContent.setBackground(CONTENT_PANEL);

		cmbSearchType = new JComboBox<>(new String[] { "Số điện thoại khách hàng", "Mã đơn đặt bàn trước",
				"Mã khách hàng", "Mã nhân viên", "Mã bàn", "Trạng thái" });
		JPanel pnlSearchType = createFieldPanel("Loại tìm kiếm:", cmbSearchType);

		txtSearchInfo = new JTextField();
		
		btnSearch = new JButton("Tìm");
		styleModernButton(btnSearch, BUTTON_GRAY, BUTTON_GRAY_HOVER);
		btnSearch.setPreferredSize(new Dimension(60, 40));
		
		JPanel pnlSearchField = new JPanel(new BorderLayout(5, 0));
		pnlSearchField.setBackground(CONTENT_PANEL);
		pnlSearchField.add(txtSearchInfo, BorderLayout.CENTER);
		pnlSearchField.add(btnSearch, BorderLayout.EAST);
		JPanel pnlSearchInfo = createFieldPanel("Nhập thông tin:", pnlSearchField);

		dateCreationTime = new JDateChooser();
		dateCreationTime.setDateFormatString("dd/MM/yyyy");
		JPanel pnlCreationTime = createFieldPanel("Thời gian tạo:", dateCreationTime);
		
		dateReceiveTime = new JDateChooser();
		dateReceiveTime.setDateFormatString("dd/MM/yyyy");
		dateReceiveTime.setDate(new Date());
		JPanel pnlReceiveTime = createFieldPanel("Thời gian nhận:", dateReceiveTime);
		

		cmbStatus = new JComboBox<>(new String[] { "Tất cả", "Chưa đến", "Đã đến", "Đã hủy" });
		JPanel pnlStatus = createFieldPanel("Trạng thái:", cmbStatus);

		pnlSearchContent.add(pnlSearchType);
		pnlSearchContent.add(pnlReceiveTime);
		pnlSearchContent.add(pnlCreationTime);
		pnlSearchContent.add(pnlSearchInfo);
		pnlSearchContent.add(pnlStatus);

		pnlSearch.add(pnlSearchContent, BorderLayout.CENTER);
		pnlContent.add(pnlSearch);
		pnlContent.add(Box.createVerticalStrut(10));

		// Table Panel
		pnlTable = new JPanel(new BorderLayout());
		pnlTable.setBackground(CONTENT_PANEL);
		pnlTable.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

		String[] columnNames = { "Mã đơn đặt bàn", "Thời gian tạo", "Thời gian nhận", "Bàn", "Nhân viên", "Khách hàng",
				"Mã KH", "Trạng thái" };
		modelDonDatBan = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblReservations = new JTable(modelDonDatBan);
		tblReservations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tblReservations.setRowHeight(30);
		tblReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblReservations.setGridColor(new Color(240, 240, 240));
		JTableHeader header = tblReservations.getTableHeader();
		header.setBackground(TABLE_HEADER);
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Segoe UI", Font.BOLD, 12));
		header.setReorderingAllowed(false);

		tblReservations.getColumnModel().getColumn(6).setMinWidth(0);
		tblReservations.getColumnModel().getColumn(6).setMaxWidth(0);
		tblReservations.getColumnModel().getColumn(6).setPreferredWidth(0);
		tblReservations.getColumnModel().getColumn(6).setResizable(false);

		tblReservations.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					String status = value.toString();
					c.setForeground(status.equals("Đã đến") ? Color.GREEN : Color.YELLOW);
				}
				return c;
			}
		});

		JScrollPane scrollPane = new JScrollPane(tblReservations);
		pnlTable.add(scrollPane, BorderLayout.CENTER);
		pnlContent.add(pnlTable);
		pnlContent.add(Box.createVerticalStrut(10));

		// Button Panel
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		pnlButtons.setBackground(BACKGROUND_PANEL);

		btnReFresh = new JButton("Làm mới");
		
		btnDetail = new JButton("Chi tiết đơn");
		btnUpdate = new JButton("Cập nhật");
		btnCancel = new JButton("Hủy đơn");
		btnContactCustomer = new JButton("Liên hệ khách");

		styleModernButton(btnReFresh, BUTTON_GREEN, BUTTON_GREEN_HOVER);
		styleModernButton(btnDetail, BUTTON_BLUE, BUTTON_BLUE_HOVER);
		styleModernButton(btnUpdate, BUTTON_BLUE, BUTTON_BLUE_HOVER);
		styleModernButton(btnCancel, BUTTON_RED, BUTTON_RED_HOVER);
		styleModernButton(btnContactCustomer, BUTTON_BLUE, BUTTON_BLUE_HOVER);

		pnlButtons.add(Box.createHorizontalGlue());
		pnlButtons.add(btnReFresh);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnDetail);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnUpdate);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnContactCustomer);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnCancel);
		pnlButtons.add(Box.createHorizontalGlue());

		pnlContent.add(pnlButtons);

		// Event Listeners
		btnReFresh.addActionListener(this);
		btnSearch.addActionListener(e -> timKiemDonDatBanTruoc());
		btnCancel.addActionListener(e -> huyDon());
		btnContactCustomer.addActionListener(e -> emailThongBao());
		btnDetail.addActionListener(e -> chiTietDonDatBanTruoc());
		btnUpdate.addActionListener(e -> {
		 int selectedRow = tblReservations.getSelectedRow();
	    if (selectedRow >= 0) {
	    	
	         String maDonDatBanTruoc = tblReservations.getValueAt(selectedRow, 0).toString();
	    	parentMenu.showDatBanTruocAndLoad(maDonDatBanTruoc);
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn đặt bàn để cập nhật!");
	    }
		}
		);
		
		tblReservations.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tblReservations.getSelectedRow();
				if (selectedRow != -1) {
					trangThaiButton(selectedRow);
				}
			}
		});
		cmbSearchType.addActionListener(e -> {
			String selectedSearchType = (String) cmbSearchType.getSelectedItem();
			if (selectedSearchType.equals("Trạng thái")) {
				txtSearchInfo.setVisible(false);
				cmbStatus.setVisible(true);
			} else {
				txtSearchInfo.setVisible(true);
				cmbStatus.setVisible(true);
			}
		});
	}

	private JPanel createFieldPanel(String labelText, JComponent component) {
		JPanel panel = new JPanel(new BorderLayout(5, 0));
		panel.setBackground(CONTENT_PANEL);

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		label.setPreferredSize(new Dimension(100, 25));

		panel.add(label, BorderLayout.WEST);
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}

	private void styleModernButton(JButton button, Color bgColor, Color hoverColor) {
		button.setBackground(bgColor);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.BOLD, 13));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setOpaque(true);

		Dimension buttonSize = new Dimension(120, 32);
		button.setPreferredSize(buttonSize);
		button.setMinimumSize(buttonSize);
		button.setMaximumSize(buttonSize);

		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				if (button.isEnabled()) {
					button.setBackground(hoverColor);
					button.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			public void mouseExited(MouseEvent evt) {
				if (button.isEnabled()) {
					button.setBackground(bgColor);
					button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}

	private String getStatusText(int statusCode) {
	    switch (statusCode) {
	    case STATUS_CHUA_DEN:
	        return "Chưa đến";
	    case STATUS_DA_DEN:
	        return "Đã đến";
	    case STATUS_DA_HUY:
	        return "Đã hủy";
	    default:
	        return "Không xác định";
	    }
	}

	public void loadDonDatBanTruocData() {
		modelDonDatBan.setRowCount(0);
		dsDonDatBanTruoc.clear();
		dsDonDatBanTruoc = donDatBanTruocDAO.getAllDonDatBanTruoc();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		for (DonDatBanTruoc ddbt : dsDonDatBanTruoc) {
			// Use the status from database instead of calculating based on time
	        String trangThai = getStatusText(ddbt.getTrangThai());

			Object[] row = { ddbt.getMaDonDatBanTruoc(), ddbt.getThoiGianTao().format(formatter),
					ddbt.getThoiGianNhanBan().format(formatter), ddbt.getBan().getMaBan(), ddbt.getNV().getTenNV(),
					ddbt.getKH().getTenKH(), ddbt.getKH().getMaKH(), trangThai };
			modelDonDatBan.addRow(row);
		}
	    tblReservations.getColumnModel().getColumn(7).setCellRenderer(new StatusColumnRenderer());

		if (tblReservations.getRowCount() > 0) {
			tblReservations.setRowSelectionInterval(0, 0);
			trangThaiButton(0);
		}
		
		for (DonDatBanTruoc ddbt : dsDonDatBanTruoc) {
			System.out.println("Mã đơn đặt bàn: " + ddbt.getMaDonDatBanTruoc() + ", Trạng thái: " + getStatusText(ddbt.getTrangThai()));
		}
	}
	private class StatusColumnRenderer extends DefaultTableCellRenderer {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, 
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        
	        JLabel label = (JLabel) super.getTableCellRendererComponent(
	                table, value, isSelected, hasFocus, row, column);
	        
	        label.setHorizontalAlignment(SwingConstants.CENTER);
	        label.setOpaque(true);
	        
	        if (isSelected) {
	            label.setForeground(Color.WHITE);
	            return label;
	        }
	        
	        String status = value.toString();
	        
	        switch (status) {
	            case "Đã đến":
	                label.setBackground(new Color(220, 255, 220)); // Light green
	                label.setForeground(new Color(0, 120, 0));     // Dark green
	                label.setBorder(BorderFactory.createCompoundBorder(
	                    BorderFactory.createLineBorder(new Color(180, 230, 180)), 
	                    BorderFactory.createEmptyBorder(4, 6, 4, 6)));
	                break;
	                
	            case "Chưa đến":
	                label.setBackground(new Color(255, 255, 220)); // Light yellow
	                label.setForeground(new Color(150, 100, 0));   // Brown
	                label.setBorder(BorderFactory.createCompoundBorder(
	                    BorderFactory.createLineBorder(new Color(230, 220, 150)), 
	                    BorderFactory.createEmptyBorder(4, 6, 4, 6)));
	                break;
	                
	            case "Đã hủy":
	                label.setBackground(new Color(255, 220, 220)); // Light red
	                label.setForeground(new Color(180, 0, 0));     // Dark red
	                label.setBorder(BorderFactory.createCompoundBorder(
	                    BorderFactory.createLineBorder(new Color(230, 180, 180)), 
	                    BorderFactory.createEmptyBorder(4, 6, 4, 6)));
	                break;
	                
	            default:
	                label.setBackground(Color.WHITE);
	                label.setForeground(Color.BLACK);
	                label.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
	        }
	        
	        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
	        return label;
	    }
	}
	private void huyDon() {
		int row = tblReservations.getSelectedRow();
		if (row >= 0) {
			String reservationId = tblReservations.getValueAt(row, 0).toString();
			int result = JOptionPane.showConfirmDialog(this,
					"Bạn có chắc chắn muốn hủy đơn đặt bàn " + reservationId + "?", "Xác nhận hủy",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				DonDatBanTruoc donDat = dsDonDatBanTruoc.get(row);
				donDat.setTrangThai(STATUS_DA_HUY);
				boolean updated = donDatBanTruocDAO.updateTrangThai(donDat.getMaDonDatBanTruoc(), STATUS_DA_HUY);

				if (updated) {
					// Update UI
					tblReservations.setValueAt(getStatusText(STATUS_DA_HUY), row, 7);
					trangThaiButton(row);
					JOptionPane.showMessageDialog(this, "Đã hủy đơn đặt bàn thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Không thể hủy đơn đặt bàn. Vui lòng thử lại.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn đặt bàn để hủy!");
		}
	}

	private void trangThaiButton(int selectedRow) {
		if (selectedRow >= 0 && selectedRow < dsDonDatBanTruoc.size()) {
			DonDatBanTruoc donDat = dsDonDatBanTruoc.get(selectedRow);
			int statusCode = donDat.getTrangThai();
			KhachHang khachHang = khachHangDAO.getKhachHangById(donDat.getKH().getMaKH());
			boolean canUpdate = statusCode != STATUS_DA_HUY && statusCode != STATUS_DA_DEN;
			boolean canCancel = statusCode != STATUS_DA_HUY && statusCode != STATUS_DA_DEN;
			btnUpdate.setEnabled(canUpdate);
			btnCancel.setEnabled(canCancel);
			if (!canUpdate) {
				btnUpdate.setBackground(new Color(200, 200, 200));
				btnUpdate.setForeground(new Color(150, 150, 150));
			} else {
				btnUpdate.setBackground(BUTTON_BLUE);
				btnUpdate.setForeground(Color.WHITE);
				btnUpdate.setText("Cập nhật");
			}

			if (!canCancel) {
				btnCancel.setBackground(new Color(200, 200, 200));
				btnCancel.setForeground(new Color(150, 150, 150));
			} else {
				btnCancel.setBackground(BUTTON_RED);
				btnCancel.setForeground(Color.WHITE);
				btnCancel.setText("Hủy đơn");
			}

			btnContactCustomer
					.setEnabled(khachHang != null && khachHang.getSdtKH() != null && !khachHang.getSdtKH().isEmpty());
		}
	}

	private void chiTietDonDatBanTruoc() {
		int selectedRow = tblReservations.getSelectedRow();
		if (selectedRow >= 0) {
			DonDatBanTruoc donDat = dsDonDatBanTruoc.get(selectedRow);
			JDialog detailDialog = new JDialog(this, "Chi Tiết Đơn Đặt Bàn", true);
			detailDialog.setSize(650, 500);
			detailDialog.setLocationRelativeTo(this);
			detailDialog.setLayout(new BorderLayout());

			JTabbedPane tabbedPane = new JTabbedPane();
			tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

			JPanel pnlReservationDetails = dialogChiTietDon(donDat);
			tabbedPane.addTab("Thông Tin Đơn Đặt Bàn", pnlReservationDetails);
			
			JPanel pnlFoodItems = dialogMonAn(donDat.getMaDonDatBanTruoc());
			tabbedPane.addTab("Danh Sách Món Ăn Đặt Trước", pnlFoodItems);

			JPanel pnlDialogButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pnlDialogButtons.setBackground(CONTENT_PANEL);
			JButton btnClose = new JButton("Đóng");
			styleModernButton(btnClose, BUTTON_GRAY, BUTTON_GRAY_HOVER);
			pnlDialogButtons.add(btnClose);

			detailDialog.add(tabbedPane, BorderLayout.CENTER);
			detailDialog.add(pnlDialogButtons, BorderLayout.SOUTH);

			btnClose.addActionListener(e -> detailDialog.dispose());
			detailDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn đặt bàn để xem chi tiết!");
		}
	}

	private JPanel dialogChiTietDon(DonDatBanTruoc donDat) {
		JPanel pnlDetails = new JPanel();
		pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
		pnlDetails.setBackground(CONTENT_PANEL);
		pnlDetails.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		JPanel pnlInfo = new JPanel(new GridLayout(0, 2, 10, 10));
		pnlInfo.setBackground(CONTENT_PANEL);

		addInfoField(pnlInfo, "Mã đơn đặt bàn:", donDat.getMaDonDatBanTruoc());
		addInfoField(pnlInfo, "Thời gian tạo:", donDat.getThoiGianTao().format(formatter));
		addInfoField(pnlInfo, "Thời gian nhận bàn:", donDat.getThoiGianNhanBan().format(formatter));
		addInfoField(pnlInfo, "Nhân viên:", donDat.getNV().getTenNV());
		addInfoField(pnlInfo, "Khách hàng:", donDat.getKH().getTenKH());

		KhachHang kh = khachHangDAO.getKhachHangById(donDat.getKH().getMaKH());
		String contact = kh != null ? kh.getSdtKH() : "Không có";
		addInfoField(pnlInfo, "Liên hệ:", contact);

		addInfoField(pnlInfo, "Bàn:", donDat.getBan().getMaBan());
		addInfoField(pnlInfo, "Trạng thái:", getStatusText(donDat.getTrangThai()));
		addInfoField(pnlInfo, "Số lượng khách:", String.valueOf(donDat.getSoLuongKhach()));
		addInfoField(pnlInfo, "Tiền cọc:", String.format("%,.0f VNĐ", donDat.getTienCoc()));
		JScrollPane scrollPane = new JScrollPane(pnlInfo);
		scrollPane.setBorder(null);
		pnlDetails.add(scrollPane);

		return pnlDetails;
	}

	private void addInfoField(JPanel panel, String label, String value) {
		JLabel lblField = new JLabel(label);
		lblField.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblField.setForeground(new Color(50, 50, 50));

		JLabel lblValue = new JLabel(value);
		lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		panel.add(lblField);
		panel.add(lblValue);
	}
	private JPanel dialogMonAn(String maDonDatBanTruoc) {
	    JPanel pnlFoodItems = new JPanel(new BorderLayout());
	    pnlFoodItems.setBackground(CONTENT_PANEL);
	    pnlFoodItems.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    String[] columns = { "STT", "Tên món ăn", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú" };
	    DefaultTableModel modelFoodItems = new DefaultTableModel(columns, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    JTable tblFoodItems = new JTable(modelFoodItems);
	    tblFoodItems.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    tblFoodItems.setRowHeight(30);
	    tblFoodItems.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    tblFoodItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    tblFoodItems.setGridColor(new Color(240, 240, 240));

	    JTableHeader header = tblFoodItems.getTableHeader();
	    header.setBackground(TABLE_HEADER);
	    header.setForeground(Color.BLACK);
	    header.setFont(new Font("Segoe UI", Font.BOLD, 12));
	    tblFoodItems.getColumnModel().getColumn(0).setPreferredWidth(40);  // STT
	    tblFoodItems.getColumnModel().getColumn(0).setMinWidth(35);
	    tblFoodItems.getColumnModel().getColumn(0).setMaxWidth(50);

	    tblFoodItems.getColumnModel().getColumn(3).setPreferredWidth(60);  // Số lượng
	    tblFoodItems.getColumnModel().getColumn(3).setMinWidth(50);
	    tblFoodItems.getColumnModel().getColumn(3).setMaxWidth(70);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    TableCellRenderer multiLineRenderer = new TableCellRenderer() {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                       boolean hasFocus, int row, int column) {
	            JTextArea textArea = new JTextArea();
	            textArea.setText(value != null ? value.toString() : "");
	            textArea.setWrapStyleWord(true);
	            textArea.setLineWrap(true);
	            textArea.setOpaque(true);
	            textArea.setFont(table.getFont());
	            textArea.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
	            textArea.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
	            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	            int columnWidth = table.getColumnModel().getColumn(column).getWidth();
	            textArea.setSize(new Dimension(columnWidth, Integer.MAX_VALUE));
	            int preferredHeight = textArea.getPreferredSize().height;

	            if (table.getRowHeight(row) != preferredHeight) {
	                table.setRowHeight(row, preferredHeight);
	            }

	            return textArea;
	        }
	    };

	    for (int i = 0; i < tblFoodItems.getColumnCount(); i++) {
	        if (i == 1 || i == 5) { // "Tên món ăn" and "Ghi chú"
	            tblFoodItems.getColumnModel().getColumn(i).setCellRenderer(multiLineRenderer);
	        } else {
	            tblFoodItems.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	    }

	    HoaDon_DAO hdon_dao = new HoaDon_DAO();
	    HoaDon hdon = hdon_dao.getHoaDonTheoMaDonDatBanTruoc(maDonDatBanTruoc);
	    ChiTietHoaDon_DAO chiTietDAO = new ChiTietHoaDon_DAO();
	    ArrayList<ChiTietHoaDon> dsChiTiet = chiTietDAO.getChiTietHoaDonTheoMaHD(hdon.getMaHD());

	    int stt = 1;
	    for (ChiTietHoaDon chiTiet : dsChiTiet) {
	        MonAn monAn = chiTiet.getMonAn();
	        double donGia = monAn.getDonGia();
	        int soLuong = chiTiet.getSoLuong();
	        double thanhTien = donGia * soLuong;
	        String ghiChu = chiTiet.getGhiChu() != null ? chiTiet.getGhiChu() : "";

	        Object[] row = {
	            stt++,
	            monAn.getTenMonAn(),
	            String.format("%,.0f VNĐ", donGia),
	            soLuong,
	            String.format("%,.0f VNĐ", thanhTien),
	            ghiChu
	        };
	        modelFoodItems.addRow(row);
	    }

	    JScrollPane scrollPane = new JScrollPane(tblFoodItems);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

	    pnlFoodItems.add(scrollPane, BorderLayout.CENTER);
	    return pnlFoodItems;
	}

	private void timKiemDonDatBanTruoc() {
		String searchType = (String) cmbSearchType.getSelectedItem();
		String searchValue = txtSearchInfo.getText().trim();
		String statusFilter = (String) cmbStatus.getSelectedItem();
		Date creationDate = dateCreationTime.getDate();
		Date receiveDate = dateReceiveTime.getDate();

		LocalDateTime creationDateTime = creationDate != null
				? creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				: null;
		LocalDateTime receiveDateTime = receiveDate != null
				? receiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				: null;

		modelDonDatBan.setRowCount(0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		for (DonDatBanTruoc ddbt : dsDonDatBanTruoc) {
			boolean matchesSearch = true;

			if (!searchValue.isEmpty()) {
				switch (searchType) {
				case "Số điện thoại khách hàng":
					KhachHang khachHang = khachHangDAO.getKhachHangById(ddbt.getKH().getMaKH());
					matchesSearch = khachHang != null && khachHang.getSdtKH() != null
							&& khachHang.getSdtKH().contains(searchValue);
					break;
				case "Mã đơn đặt bàn trước":
					matchesSearch = ddbt.getMaDonDatBanTruoc().contains(searchValue);
					break;
				case "Mã khách hàng":
					matchesSearch = ddbt.getKH() != null && ddbt.getKH().getMaKH().contains(searchValue);
					break;
				case "Mã nhân viên":
					matchesSearch = ddbt.getNV() != null && ddbt.getNV().getMaNV().contains(searchValue);
					break;
				case "Mã bàn":
					matchesSearch = ddbt.getBan() != null && ddbt.getBan().getMaBan().contains(searchValue);
					break;
				case "Trạng thái":
					String rowStatus = getStatusText(ddbt.getTrangThai());
					matchesSearch = rowStatus.contains(searchValue);
					break;
				}
			}

			if (!statusFilter.equals("Tất cả")) {
		        String rowStatus = getStatusText(ddbt.getTrangThai());
		        matchesSearch = matchesSearch && rowStatus.equals(statusFilter);
		    }

			if (creationDateTime != null) {
				LocalDateTime creationDateOnly = LocalDateTime.of(creationDateTime.getYear(),
						creationDateTime.getMonth(), creationDateTime.getDayOfMonth(), 0, 0, 0);
				LocalDateTime nextDay = creationDateOnly.plusDays(1);
				matchesSearch = matchesSearch
						&& (ddbt.getThoiGianTao().isAfter(creationDateOnly)
								|| ddbt.getThoiGianTao().isEqual(creationDateOnly))
						&& ddbt.getThoiGianTao().isBefore(nextDay);
			}

			if (receiveDateTime != null) {
				LocalDateTime receiveDateOnly = LocalDateTime.of(receiveDateTime.getYear(), receiveDateTime.getMonth(),
						receiveDateTime.getDayOfMonth(), 0, 0, 0);
				LocalDateTime nextDay = receiveDateOnly.plusDays(1);
				matchesSearch = matchesSearch
						&& (ddbt.getThoiGianNhanBan().isAfter(receiveDateOnly)
								|| ddbt.getThoiGianNhanBan().isEqual(receiveDateOnly))
						&& ddbt.getThoiGianNhanBan().isBefore(nextDay);
			}

			 if (matchesSearch) {
			        // Use the status directly from the database, not calculated
			        String trangThai = getStatusText(ddbt.getTrangThai());
			        Object[] row = { ddbt.getMaDonDatBanTruoc(), ddbt.getThoiGianTao().format(formatter),
			                ddbt.getThoiGianNhanBan().format(formatter), ddbt.getBan().getMaBan(), ddbt.getNV().getTenNV(),
			                ddbt.getKH().getTenKH(), ddbt.getKH().getMaKH(), trangThai };
			        modelDonDatBan.addRow(row);
			    }
		}

		if (tblReservations.getRowCount() > 0) {
			tblReservations.setRowSelectionInterval(0, 0);
			trangThaiButton(0);
		}
	}

	private void emailThongBao() {
		int selectedRow = tblReservations.getSelectedRow();
		if (selectedRow != -1) {
			String maKH = tblReservations.getValueAt(selectedRow, 6).toString();
			DonDatBanTruoc donDat = dsDonDatBanTruoc.get(selectedRow);
			KhachHang khachHang = khachHangDAO.getKhachHangById(maKH);
			Ban ban = banDAO.getBanById(donDat.getBan().getMaBan());

			if (khachHang != null && khachHang.getEmailKH() != null && !khachHang.getEmailKH().isEmpty()) {
				showEmailPreviewDialog(khachHang, donDat, ban);
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy địa chỉ email của khách hàng.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void showEmailPreviewDialog(KhachHang kh, DonDatBanTruoc donDat, Ban ban) {
		JDialog emailDialog = new JDialog(this, "Gửi Email cho Khách Hàng", true);
		emailDialog.setSize(500, 450);
		emailDialog.setLocationRelativeTo(this);
		emailDialog.setLayout(new BorderLayout());

		JPanel pnlEmailContent = new JPanel(new BorderLayout(10, 10));
		pnlEmailContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		pnlEmailContent.setBackground(CONTENT_PANEL);

		JPanel pnlEmailHeader = new JPanel(new GridLayout(3, 2, 5, 10));
		pnlEmailHeader.setBackground(CONTENT_PANEL);

		JLabel lblToEmail = new JLabel("Đến:");
		JTextField txtToEmail = new JTextField(kh.getEmailKH());
		txtToEmail.setEditable(false);

		JLabel lblSubject = new JLabel("Tiêu đề:");
		JTextField txtSubject = new JTextField("THÔNG TIN ĐƠN ĐẶT BÀN TẠI NHÀ HÀNG KAREM");

		JLabel lblCustomerName = new JLabel("Khách hàng:");
		JTextField txtCustomerName = new JTextField(kh.getTenKH());
		txtCustomerName.setEditable(false);

		pnlEmailHeader.add(lblToEmail);
		pnlEmailHeader.add(txtToEmail);
		pnlEmailHeader.add(lblSubject);
		pnlEmailHeader.add(txtSubject);
		pnlEmailHeader.add(lblCustomerName);
		pnlEmailHeader.add(txtCustomerName);

		JLabel lblContent = new JLabel("Nội dung:");
		JTextArea txtContent = new JTextArea(12, 40);
		txtContent.setLineWrap(true);
		txtContent.setWrapStyleWord(true);

		String emailContent = "Kính chào Qúy khách " + kh.getTenKH() + ",\n\n"
				+ "Nhà hàng Karem trân trọng cảm ơn quý khách đã đặt bàn tại nhà hàng của chúng tôi.\n\n"
				+ "Mã đơn đặt bàn của quý khách là " + donDat.getMaDonDatBanTruoc() + ",\n" + "Thời gian nhận: "
				+ donDat.getThoiGianNhanBan().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ",\n"
				+ "Bàn: " + ban.getMaBan() + ",\n" + "Số lượng khách: " + donDat.getSoLuongKhach() + ",\n\n"
				+ "Nếu có thắc mắc hoặc thay đổi thông tin, vui lòng phản hồi lại Email này hoặc số Hotline: 0908070000.\n"
				+ "Trân trọng,\nNhà hàng KAREM \n\n" + "Địa chỉ: 24/4 Trương Quốc Dung, P.8, Q.Phú Nhuận, TP.HCM\n";
		txtContent.setText(emailContent);

		JScrollPane scrollContent = new JScrollPane(txtContent);

		JPanel pnlEmailBody = new JPanel(new BorderLayout(5, 10));
		pnlEmailBody.setBackground(CONTENT_PANEL);
		pnlEmailBody.add(lblContent, BorderLayout.NORTH);
		pnlEmailBody.add(scrollContent, BorderLayout.CENTER);

		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnlButtons.setBackground(CONTENT_PANEL);

		JButton btnSend = new JButton("Gửi Email");
		styleModernButton(btnSend, BUTTON_GREEN, BUTTON_GREEN_HOVER);

		JButton btnCancel = new JButton("Hủy");
		styleModernButton(btnCancel, BUTTON_GRAY, BUTTON_GRAY_HOVER);

		pnlButtons.add(btnSend);
		pnlButtons.add(btnCancel);

		pnlEmailContent.add(pnlEmailHeader, BorderLayout.NORTH);
		pnlEmailContent.add(pnlEmailBody, BorderLayout.CENTER);
		pnlEmailContent.add(pnlButtons, BorderLayout.SOUTH);

		emailDialog.add(pnlEmailContent, BorderLayout.CENTER);

		btnCancel.addActionListener(e -> emailDialog.dispose());
		btnSend.addActionListener(e -> {
			sendEmail(txtToEmail.getText(), txtSubject.getText(), txtContent.getText());
			emailDialog.dispose();
		});

		emailDialog.setVisible(true);
	}

	public void sendEmail(String recipientEmail, String subject, String messageText) {
		final String fromEmail = "tranminhtu2032004@gmail.com";
		final String password = "gqid xaks fvvp vfwo";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject(subject);
			message.setText(messageText);
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Email đã được gửi thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Gửi email thất bại: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addHoverToButton(final JButton button, final Color originalBg, final Color hoverBg) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(hoverBg);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(originalBg);
				button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (btnReFresh.equals(source)) {
			loadDonDatBanTruocData();
		}
	}
}