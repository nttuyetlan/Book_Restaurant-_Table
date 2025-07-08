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
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import entity.ChiTietHoaDon;
import entity.DonDatBanTruoc;
import entity.KhachHang;
import entity.MonAn;
import entity.NhanVien;
import entity.HoaDon;

public class DanhSachDatBanTrucTiep_GUI extends JFrame{

	private NhanVien currentUser;
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
	private JLabel lblTitle;
	private JPanel pnlSearch;
	private JComboBox cmbSearchType;
	private JTextField txtSearchInfo;
	private JButton btnSearch;
	private JDateChooser dateCreationTime;
	private JDateChooser dateReceiveTime;
	private JComboBox cmbStatus;
	private JPanel pnlTable;
	private DefaultTableModel modelDonDatBan;
	private JTable tblReservations;
	private JPanel pnlButtons;
	private JButton btnDetail;
	private JButton btnCancel;
	private ArrayList<HoaDon> dsDonDatBanTrucTiep;
	private JButton btnLamMoi;
	
	private static final int STATUS_CHUA_THANH_TOAN = 0;    
	private static final int STATUS_DA_HUY = 3; 
	
	private static HoaDon_DAO hd_dao = new HoaDon_DAO();
	private static KhachHang_DAO kh_dao = new KhachHang_DAO();
	private static ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();

	public DanhSachDatBanTrucTiep_GUI() {
		this(null);
	}
	
	public DanhSachDatBanTrucTiep_GUI(NhanVien loggedInUser) {
		setTitle("Danh Sách Đặt Bàn Trực Tiếp");
		this.currentUser = loggedInUser;
	}
	
	public JPanel initUI_Content() {
		JPanel pnlDachDatBanTrucTiep = new JPanel(new BorderLayout());
		
		JPanel pnlContent = new JPanel();
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		pnlContent.setBackground(BACKGROUND_PANEL);
		pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Header
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
		lblTitle = new JLabel("DANH SÁCH ĐƠN ĐẶT BÀN TRỰC TIẾP", SwingConstants.CENTER);
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

		cmbSearchType = new JComboBox<>(new String[] {"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã bàn"});
		JPanel pnlSearchType = createFieldPanel("Loại tìm kiếm:", cmbSearchType);

		txtSearchInfo = new JTextField();
		btnSearch = new JButton("Tìm");
		styleModernButton(btnSearch, BUTTON_GRAY, BUTTON_GRAY_HOVER);
		btnSearch.setPreferredSize(new Dimension(60, 25));
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
		JPanel pnlReceiveTime = createFieldPanel("Thời gian dự kiến:", dateReceiveTime);
		

		pnlSearchContent.add(pnlSearchType);
		pnlSearchContent.add(pnlCreationTime);
		pnlSearchContent.add(pnlSearchInfo);
		pnlSearchContent.add(pnlReceiveTime);

		pnlSearch.add(pnlSearchContent, BorderLayout.CENTER);
		pnlContent.add(pnlSearch);
		pnlContent.add(Box.createVerticalStrut(10));

		// Table Panel
		pnlTable = new JPanel(new BorderLayout());
		pnlTable.setBackground(CONTENT_PANEL);
		pnlTable.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

		String[] columnNames = { "Mã đơn", "Thời gian tạo", "Thời gian dự kiến", "Bàn", "Nhân viên", "Trạng thái" };
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

		tblReservations.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					String status = value.toString();
					c.setForeground(status.equals("Ăn xong") ? Color.GREEN : Color.YELLOW);
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
		
		btnLamMoi = new JButton("Làm mới");
		btnDetail = new JButton("Chi tiết đơn");
		btnCancel = new JButton("Hủy đơn");
		
		styleModernButton(btnLamMoi, BUTTON_BLUE, BUTTON_BLUE_HOVER);
		styleModernButton(btnDetail, BUTTON_BLUE, BUTTON_BLUE_HOVER);
		styleModernButton(btnCancel, BUTTON_RED, BUTTON_RED_HOVER);
		
		pnlButtons.add(Box.createHorizontalGlue());
		pnlButtons.add(btnLamMoi);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnDetail);
		pnlButtons.add(Box.createHorizontalStrut(15));
		pnlButtons.add(btnCancel);
		pnlButtons.add(Box.createHorizontalGlue());

		pnlContent.add(pnlButtons);

		// Event Listeners
		btnSearch.addActionListener(e -> timKiemDonDatBanTrucTiep());
		btnLamMoi.addActionListener(e -> loadDonDatBanTrucTiepData());
		btnCancel.addActionListener(e -> huyDon());
		btnDetail.addActionListener(e -> chiTietDonDatBanTrucTiep());
		tblReservations.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tblReservations.getSelectedRow();
				if (selectedRow != -1) {
					trangThaiButton(selectedRow);
				}
			}
		});
		loadDonDatBanTrucTiepData();
		
		pnlDachDatBanTrucTiep.add(pnlContent, BorderLayout.CENTER);
		btnLamMoi.doClick();
		return pnlDachDatBanTrucTiep;
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
	
	private void timKiemDonDatBanTrucTiep() {
		String searchType = (String) cmbSearchType.getSelectedItem();
		String searchValue = txtSearchInfo.getText().trim();
		String statusFilter = "Tất cả";
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
		
		ArrayList<HoaDon> dsDonDatBanTrucTiep = hd_dao.timHoaDonTheoTrangThai(0);
		
		for (HoaDon hd : dsDonDatBanTrucTiep) {
			boolean matchesSearch = true;

			if (!searchValue.isEmpty()) {
				switch (searchType) {
				case "Tất cả":
					loadDonDatBanTrucTiepData();
					return;
				case "Mã hóa đơn":
					matchesSearch = hd.getMaHD().contains(searchValue);
					break;
				case "Mã nhân viên":
					matchesSearch = hd.getNV() != null && hd.getNV().getMaNV().contains(searchValue);
					break;
				case "Mã bàn":
					matchesSearch = hd.getBan() != null && hd.getBan().getMaBan().contains(searchValue);
					break;
				}
			}

			if (!statusFilter.equals("Tất cả")) {
		        String rowStatus = getStatusText(hd.getTrangThai());
		        matchesSearch = matchesSearch && rowStatus.equals(statusFilter);
		    }

			if (creationDateTime != null) {
				LocalDateTime creationDateOnly = LocalDateTime.of(creationDateTime.getYear(),
						creationDateTime.getMonth(), creationDateTime.getDayOfMonth(), 0, 0, 0);
				LocalDateTime nextDay = creationDateOnly.plusDays(1);
				matchesSearch = matchesSearch
						&& (hd.getThoiGianTaoDon().isAfter(creationDateOnly)
								|| hd.getThoiGianTaoDon().isEqual(creationDateOnly))
						&& hd.getThoiGianTaoDon().isBefore(nextDay);
			}

			if (receiveDateTime != null) {
				LocalDateTime receiveDateOnly = LocalDateTime.of(receiveDateTime.getYear(), receiveDateTime.getMonth(),
						receiveDateTime.getDayOfMonth(), 0, 0, 0);
				LocalDateTime nextDay = receiveDateOnly.plusDays(1);
				matchesSearch = matchesSearch
						&& (hd.getThoiGianThanhToan().isAfter(receiveDateOnly)
								|| hd.getThoiGianThanhToan().isEqual(receiveDateOnly))
						&& hd.getThoiGianThanhToan().isBefore(nextDay);
			}
			 if (matchesSearch) {
			        // Use the status directly from the database, not calculated
			        String trangThai = getStatusText(hd.getTrangThai());
			        Object[] row = { hd.getMaHD(), hd.getThoiGianTaoDon().format(formatter),
			                hd.getThoiGianThanhToan().format(formatter), hd.getBan().getMaBan(), hd.getNV().getTenNV(),
			                trangThai };
			        modelDonDatBan.addRow(row);
			    }
		}

		if (tblReservations.getRowCount() > 0) {
			tblReservations.setRowSelectionInterval(0, 0);
			trangThaiButton(0);
		}
	}
	
	private String getStatusText(int statusCode) {
	    switch (statusCode) {
	    case STATUS_CHUA_THANH_TOAN:
	        return "Chưa thanh toán";
	    case STATUS_DA_HUY:
	        return "Đã hủy";
	    default:
	        return "Không xác định";
	    }
	}
	
	private void loadDonDatBanTrucTiepData() {
		modelDonDatBan.setRowCount(0);
		dsDonDatBanTrucTiep = hd_dao.timHoaDonTheoTrangThai(0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		for (HoaDon hd : dsDonDatBanTrucTiep) {
			// Use the status from database instead of calculating based on time
	        String trangThai = getStatusText(hd.getTrangThai());

			Object[] row = { hd.getMaHD(), hd.getThoiGianTaoDon().format(formatter),
					hd.getThoiGianThanhToan().format(formatter), hd.getBan().getMaBan(), hd.getNV().getTenNV(),
					trangThai };
			modelDonDatBan.addRow(row);
		}
	    tblReservations.getColumnModel().getColumn(5).setCellRenderer(new StatusColumnRenderer());

		if (tblReservations.getRowCount() > 0) {
			tblReservations.setRowSelectionInterval(0, 0);
			trangThaiButton(0);
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
	            case "Chưa thanh toán":
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
	        
	        // Add a slight font enhancement
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
				// Get the reservation object
				HoaDon donDat = dsDonDatBanTrucTiep.get(row);

				// Update status in database
				donDat.setTrangThai(STATUS_DA_HUY);
				boolean updated = hd_dao.updateHoaDon(donDat);

				if (updated) {
					// Update UI
					tblReservations.setValueAt(getStatusText(STATUS_DA_HUY), row, 5);
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
		if (selectedRow >= 0 && selectedRow < dsDonDatBanTrucTiep.size()) {
			HoaDon donDat = dsDonDatBanTrucTiep.get(selectedRow);
			int statusCode = donDat.getTrangThai();

			// Can update if not canceled and not arrived
			boolean canUpdate = statusCode != STATUS_DA_HUY;
			// Can cancel if not canceled and not arrived
			boolean canCancel = statusCode != STATUS_DA_HUY;

			// Enable/disable buttons based on status
			btnCancel.setEnabled(canCancel);

			if (!canCancel) {
				btnCancel.setBackground(new Color(200, 200, 200));
				btnCancel.setForeground(new Color(150, 150, 150));
			} else {
				btnCancel.setBackground(BUTTON_RED);
				btnCancel.setForeground(Color.WHITE);
				btnCancel.setText("Hủy đơn");
			}

		}
	}
	
	private void chiTietDonDatBanTrucTiep() {
		int selectedRow = tblReservations.getSelectedRow();
		if (selectedRow >= 0) {
			HoaDon donDat = dsDonDatBanTrucTiep.get(selectedRow);
			JDialog detailDialog = new JDialog(this, "Chi Tiết Đơn Đặt Bàn", true);
			detailDialog.setSize(650, 500);
			detailDialog.setLocationRelativeTo(this);
			detailDialog.setLayout(new BorderLayout());

			// Create tabbed pane
			JTabbedPane tabbedPane = new JTabbedPane();
			tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

			// Tab 1: Reservation Details
			JPanel pnlReservationDetails = dialogChiTietDon(donDat);
			tabbedPane.addTab("Thông Tin Đơn Đặt Bàn", pnlReservationDetails);

			// Tab 2: Pre-ordered Food Items
			JPanel pnlFoodItems = dialogMonAn(donDat.getMaHD());
			tabbedPane.addTab("Danh Sách Món Ăn Đang Dùng", pnlFoodItems);

			// Button Panel
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
	
	private JPanel dialogChiTietDon(HoaDon donDat) {
		JPanel pnlDetails = new JPanel();
		pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
		pnlDetails.setBackground(CONTENT_PANEL);
		pnlDetails.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Format information with consistent width
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		JPanel pnlInfo = new JPanel(new GridLayout(0, 2, 10, 10));
		pnlInfo.setBackground(CONTENT_PANEL);

		addInfoField(pnlInfo, "Mã đơn đặt bàn:", donDat.getMaHD());
		addInfoField(pnlInfo, "Thời gian tạo:", donDat.getThoiGianTaoDon().format(formatter));
		addInfoField(pnlInfo, "Thời gian dự kiến:", donDat.getThoiGianThanhToan().format(formatter));
		addInfoField(pnlInfo, "Nhân viên:", donDat.getNV().getTenNV());

		addInfoField(pnlInfo, "Bàn:", donDat.getBan().getMaBan());
		// Update this line to use trangThai field instead of time comparison
		addInfoField(pnlInfo, "Trạng thái:", getStatusText(donDat.getTrangThai()));
		addInfoField(pnlInfo, "Số lượng khách:", String.valueOf(donDat.getSoKhach()));
		addInfoField(pnlInfo, "Tổng tiền:", String.format("%,.0f VNĐ", donDat.tinhTongTien()));

		// Add the grid to a scroll pane in case there are many fields
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
	
	private JPanel dialogMonAn(String maHD) {
		JPanel pnlFoodItems = new JPanel(new BorderLayout());
		pnlFoodItems.setBackground(CONTENT_PANEL);
		pnlFoodItems.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Create table model for food items
		String[] columns = { "Tên món ăn", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú" };
		DefaultTableModel modelFoodItems = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tblFoodItems = new JTable(modelFoodItems);
		tblFoodItems.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tblFoodItems.setRowHeight(30);
		tblFoodItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblFoodItems.setGridColor(new Color(240, 240, 240));
		JTableHeader header = tblFoodItems.getTableHeader();
		header.setBackground(TABLE_HEADER);
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		// Get food items for this reservation
		ArrayList<ChiTietHoaDon> dsChiTiet = cthd_dao.getChiTietHoaDonTheoMaHD(maHD);

		// Populate table with food items
		double tongTien = 0;
		for (ChiTietHoaDon chiTiet : dsChiTiet) {
			MonAn monAn = chiTiet.getMonAn();
			tongTien += chiTiet.tinhThanhTien();

			Object[] row = { monAn.getTenMonAn(), String.format("%,.0f VNĐ", monAn.getDonGia()), chiTiet.getSoLuong(),
					String.format("%,.0f VNĐ", chiTiet.tinhThanhTien()), chiTiet.getGhiChu() != null ? chiTiet.getGhiChu() : "" };
			modelFoodItems.addRow(row);
		}

		JScrollPane scrollPane = new JScrollPane(tblFoodItems);
		pnlFoodItems.add(scrollPane, BorderLayout.CENTER);
		
		// Total panel at the bottom
		JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlTotal.setBackground(CONTENT_PANEL);
		JLabel lblTotal = new JLabel("Tổng tiền: " + String.format("%,.0f VNĐ", tongTien));
		lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTotal.setForeground(new Color(220, 20, 60));
		pnlTotal.add(lblTotal);

		// Add placeholder message if no items
		if (dsChiTiet.isEmpty()) {
			JPanel pnlNoItems = new JPanel(new BorderLayout());
			pnlNoItems.setBackground(CONTENT_PANEL);
			JLabel lblNoItems = new JLabel("Không có món ăn đặt trước", JLabel.CENTER);
			lblNoItems.setFont(new Font("Segoe UI", Font.ITALIC, 14));
			lblNoItems.setForeground(new Color(150, 150, 150));
			pnlNoItems.add(lblNoItems, BorderLayout.CENTER);
			pnlFoodItems.add(pnlNoItems, BorderLayout.CENTER);
		} else {
			pnlFoodItems.add(pnlTotal, BorderLayout.SOUTH);
		}

		return pnlFoodItems;
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

	
	public static void main(String[] args) {
		ConnectDB.getInstance().connect();
        new DanhSachDatBanTrucTiep_GUI();
    }
}
