package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import connectDB.ConnectDB;
import dao.Ban_DAO;
import dao.CaLamViec_DAO;
import dao.ChiTietCaLamViec_DAO;
import dao.DonDatBanTruoc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.CaLamViec;
import entity.ChiTietCaLamViec;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
//import gui.DanhSachDatBanTrucTiep_GUI.StatusColumnRenderer;

public class ThongKeCaLamViecDau_GUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
	
    private JLabel lblTimeOpened, lblInitialAmount;
    private JTextArea txtGhiChu;
    private JPanel statusIndicator;
	private JButton btnMoCa;
	private JButton btnKetCa;
	private JButton btnLichSu;
	private JPanel statisticsPanel;
    private Timer closeShiftReminderTimer;
	private ArrayList<HoaDon> dsDonDatBanTrucTiep;

    private DefaultTableModel modelDonDatBan;
    private JTable tblReservations;
    private ArrayList<DonDatBanTruoc> dsDonDatBanTruoc;
    private DonDatBanTruoc_DAO donDatBanTruocDAO;
    private KhachHang_DAO khachHangDAO;
    private NhanVien_DAO nhanVienDAO;
    private CaLamViec_DAO caLamViecDAO;
    private  ChiTietCaLamViec_DAO chiTietCaLamViecDAO;
    private NhanVien currentUser; 
    private Ban_DAO banDAO;
    private DefaultTableModel modelUnpaidInvoices;
    private JTable tblUnpaidInvoices;
    private ArrayList<HoaDon> dsUnpaidInvoices;
    private HoaDon_DAO hoaDonDAO;
	private static HoaDon_DAO hd_dao = new HoaDon_DAO();
	private static final int STATUS_CHUA_THANH_TOAN = 0;    
	private static final int STATUS_DA_HUY = 3; 
	public ThongKeCaLamViecDau_GUI() {
	    this(null); // Gọi constructor có tham số với giá trị null
	}
	
    public ThongKeCaLamViecDau_GUI(NhanVien loggedInUser) {
        setTitle("Thống kê ca làm việc đầu");
        this.currentUser = loggedInUser;
        donDatBanTruocDAO = new DonDatBanTruoc_DAO();
      khachHangDAO = new KhachHang_DAO();
      nhanVienDAO = new NhanVien_DAO();
      banDAO = new Ban_DAO();
      dsDonDatBanTruoc = new ArrayList<>();
      caLamViecDAO = new CaLamViec_DAO();
      chiTietCaLamViecDAO = new ChiTietCaLamViec_DAO(); 
      hoaDonDAO = new HoaDon_DAO();
      dsUnpaidInvoices = new ArrayList<>();
    }

    public JPanel initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top statistics panel
        statisticsPanel = createStatisticsPanel(); 

        // Center split pane
        JSplitPane centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerSplitPane.setResizeWeight(0.65);
        centerSplitPane.setDividerSize(1);
        centerSplitPane.setEnabled(false);

        // Left panel with tabbed pane for tables
        JTabbedPane tableTabbedPane = new JTabbedPane();
        tableTabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Reservations table panel
        JPanel reservationTablePanel = createReservationTablePanel();
        tableTabbedPane.addTab("Đơn Đặt Trước", reservationTablePanel);
        
        // Unpaid invoices table panel
        JPanel unpaidInvoicesTablePanel = createUnpaidInvoicesTablePanel();
        tableTabbedPane.addTab("Hóa Đơn Chưa Thanh Toán", unpaidInvoicesTablePanel);

        // Right panel split vertically 50/50
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setResizeWeight(0.5);
        rightSplitPane.setDividerSize(1);
        rightSplitPane.setEnabled(false);

        JPanel shiftInfoPanel = caLamViec();
        JPanel backgroundImagePanel = backgroundImagePanel();

        rightSplitPane.setTopComponent(shiftInfoPanel);
        rightSplitPane.setBottomComponent(backgroundImagePanel);

        centerSplitPane.setLeftComponent(tableTabbedPane);
        centerSplitPane.setRightComponent(rightSplitPane);

        mainPanel.add(statisticsPanel, BorderLayout.NORTH);
        mainPanel.add(centerSplitPane, BorderLayout.CENTER);
        
        addTableSelectionListener();
        loadDonDatBanTruoc();
        loadUnpaidInvoices();
        setupEventHandlers();
        loadActiveShiftInfo();
        nhacNhoDongCa();
        addHoverToButton(btnMoCa, new Color(0, 204, 0), new Color(0, 230, 0));
        addHoverToButton(btnKetCa, Color.GRAY, new Color(130, 130, 130));
        addHoverToButton(btnLichSu, Color.GRAY, new Color(130, 130, 130));
        setupReservationTable();
        loadAndUpdateStatistics();
        return mainPanel;
    }

    private JPanel caLamViec() {
        JPanel caLamViecPanel = new JPanel(new BorderLayout());
        caLamViecPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        caLamViecPanel.setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(30, 129, 191),
                    0, getHeight(), new Color(25, 105, 170));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 35));
        
        JLabel headerLabel = new JLabel("THÔNG TIN CA LÀM VIỆC", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Main content with card-like design
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20),
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15))));
        contentPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 12);

        JPanel employeeInfoPanel = new JPanel();
        employeeInfoPanel.setLayout(new BoxLayout(employeeInfoPanel, BoxLayout.Y_AXIS));
        employeeInfoPanel.setBackground(Color.WHITE);
        employeeInfoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 0, 8, 0)
        ));

        String employeeName = "Chưa đăng nhập";
        if (currentUser != null) {
            employeeName = currentUser.getTenNV();
        }
        JPanel namePanel = createFieldPanel("Tên nhân viên:", employeeName, labelFont, valueFont);
        employeeInfoPanel.add(namePanel);
     // Shift information section
        JPanel shiftDetailsPanel = new JPanel();
        shiftDetailsPanel.setLayout(new BoxLayout(shiftDetailsPanel, BoxLayout.Y_AXIS));
        shiftDetailsPanel.setBackground(Color.WHITE);
        shiftDetailsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0),
                BorderFactory.createEmptyBorder(0, 0, 8, 0)));

        String shiftTimeDisplay = "Chưa mở ca";
        Color statusColor = Color.GRAY;
        String initialAmount = "0 VNĐ";
        String shiftNote = "Nhập ghi chú tại đây...";
        
        try {
            if (currentUser != null) {
                // Logic to check for active shift remains the same
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel timePanel = new JPanel(new BorderLayout(8, 0));
        timePanel.setBackground(Color.WHITE);
        timePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel timeLabel = new JLabel("Thời gian mở ca:");
        timeLabel.setFont(labelFont);
        timeLabel.setPreferredSize(new Dimension(120, 20));

        lblTimeOpened = new JLabel("Chưa mở ca"); // Gán tham chiếu
        lblTimeOpened.setFont(valueFont);

        statusIndicator = new JPanel(); // Gán tham chiếu
        statusIndicator.setBackground(Color.GRAY);
        statusIndicator.setPreferredSize(new Dimension(10, 10));
        statusIndicator.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JPanel timeInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        timeInfoPanel.setBackground(Color.WHITE);
        timeInfoPanel.add(lblTimeOpened);
        timeInfoPanel.add(statusIndicator);

        timePanel.add(timeLabel, BorderLayout.WEST);
        timePanel.add(timeInfoPanel, BorderLayout.CENTER);

        JPanel soTienPanel = createFieldPanel("Số tiền ban đầu:", "0 VNĐ", labelFont, valueFont);
        lblInitialAmount = (JLabel) soTienPanel.getComponent(1); // Gán tham chiếu (giả định value là thành phần thứ hai)

        shiftDetailsPanel.add(timePanel);
        shiftDetailsPanel.add(Box.createVerticalStrut(6));
        shiftDetailsPanel.add(soTienPanel);

        // Notes section
        JPanel notesSection = new JPanel();
        notesSection.setLayout(new BorderLayout());
        notesSection.setBackground(Color.WHITE);
        notesSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)));

        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setFont(labelFont);
        lblGhiChu.setPreferredSize(new Dimension(120, 15));

        txtGhiChu = new JTextArea("Nhập ghi chú tại đây..."); // Gán tham chiếu
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtGhiChu.setRows(1);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setBackground(new Color(248, 248, 248));
        txtGhiChu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        txtGhiChu.setForeground(Color.GRAY);
        txtGhiChu.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtGhiChu.getText().equals("Nhập ghi chú tại đây...")) {
                    txtGhiChu.setText("");
                    txtGhiChu.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtGhiChu.getText().isEmpty()) {
                    txtGhiChu.setText("Nhập ghi chú tại đây...");
                    txtGhiChu.setForeground(Color.GRAY);
                }
            }
        });

        notesSection.add(lblGhiChu, BorderLayout.WEST);
        notesSection.add(new JScrollPane(txtGhiChu), BorderLayout.CENTER);

        JPanel revenuePanel = new JPanel();
        revenuePanel.setLayout(new BorderLayout());
        revenuePanel.setBackground(new Color(240, 247, 255));
        revenuePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        
        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu ca hiện tại:", SwingConstants.LEFT);
        lblTongDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        JLabel lblDoanhThuValue = new JLabel("0 VNĐ", SwingConstants.RIGHT);
        lblDoanhThuValue.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDoanhThuValue.setForeground(new Color(0, 100, 0));
        
        revenuePanel.add(lblTongDoanhThu, BorderLayout.WEST);
        revenuePanel.add(lblDoanhThuValue, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        btnMoCa = new JButton("Mở ca");
        btnKetCa = new JButton("Kết ca");
        btnLichSu = new JButton("Lịch sử");

        styleModernButton(btnMoCa, new Color(46, 175, 80), new Color(38, 160, 70));
        styleModernButton(btnKetCa, new Color(100, 110, 120), new Color(80, 90, 100));
        styleModernButton(btnLichSu, new Color(70, 130, 180), new Color(60, 115, 160));
        
        btnKetCa.setEnabled(false);
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(btnMoCa);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(btnKetCa);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(btnLichSu);
        buttonPanel.add(Box.createHorizontalGlue());

        contentPanel.add(employeeInfoPanel);
        contentPanel.add(shiftDetailsPanel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(notesSection);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(revenuePanel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(buttonPanel);

        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setBackground(new Color(245, 245, 245));
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        paddingPanel.add(contentPanel, BorderLayout.CENTER);
        
        caLamViecPanel.add(headerPanel, BorderLayout.NORTH);
        caLamViecPanel.add(paddingPanel, BorderLayout.CENTER);

        return caLamViecPanel;
    }


    private void styleModernButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        Dimension buttonSize = new Dimension(90, 32);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(hoverColor);
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor);
                    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }


    private JPanel backgroundImagePanel() {
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setPreferredSize(new Dimension(0, 300)); 

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boolean imageLoaded = false;
                try {
                    URL resourceUrl = getClass().getResource("/resource/background/background.jpg");
                    if (resourceUrl != null) {
                        ImageIcon icon = new ImageIcon(resourceUrl);
                        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                            imageLoaded = true;
                        }
                    }
                } catch (Exception e) {}

                if (!imageLoaded) {
                    try {
                        File file = new File("resource/background/background.jpg");
                        if (file.exists()) {
                            Image img = new ImageIcon(file.getAbsolutePath()).getImage();
                            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                            imageLoaded = true;
                        }
                    } catch (Exception e) {}
                }

                if (!imageLoaded) {
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(
                        0, 0, new Color(235, 245, 251),
                        getWidth(), getHeight(), new Color(220, 230, 240));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        backgroundPanel.add(imagePanel, BorderLayout.CENTER);
        return backgroundPanel;
    }

    private JPanel createFieldPanel(String labelText, String valueText, Font labelFont, Font valueFont) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(245, 245, 245));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(120, 25));

        JLabel value = new JLabel(valueText);
        value.setFont(valueFont);

        panel.add(label, BorderLayout.WEST);
        panel.add(value, BorderLayout.CENTER);

        return panel;
    }

    private void styleButton(JButton button, Color bgColor, Color borderColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setOpaque(true);
        button.setFocusPainted(false);
        Dimension buttonSize = new Dimension(85, 30);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    private JPanel createStatisticsPanel() {
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new GridLayout(1, 4, 10, 0));
        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statisticsPanel.setPreferredSize(new Dimension(0, 100));

        // Create statistics cards
        statisticsPanel.add(createStatCard("Hóa đơn trong ngày", "0", new Color(135, 206, 250)));
        statisticsPanel.add(createStatCard("Doanh thu trong ngày", "0 VNĐ", new Color(144, 238, 144)));
        statisticsPanel.add(createStatCard("Số lượng bàn đặt", "0", new Color(255, 182, 193)));
        statisticsPanel.add(createStatCard("Số lượng khách", "0", new Color(255, 218, 185)));

        return statisticsPanel;
    }

    private JPanel createStatCard(String title, String value, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // bảng đơn đặt trước
    private void initTableModel() {
        modelDonDatBan = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã phiếu đặt", "Mã bàn", "Tên khách hàng", "Số điện thoại", "Giờ đến", "Số lượng khách"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        tblReservations.setModel(modelDonDatBan);
    }

    // Create the reservation table panel
    private JPanel createReservationTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        
        // Create header panel with gradient background
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(30, 129, 191),
                    0, getHeight(), new Color(25, 105, 170));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 35));
        
        JLabel headerLabel = new JLabel("DANH SÁCH ĐƠN ĐẶT TRƯỚC", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Create table
        tblReservations = new JTable();
        tblReservations.setFont(new Font("Segoe UI", 0, 14));
        tblReservations.setRowHeight(30);
        tblReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblReservations.setGridColor(new Color(240, 240, 240));
        
        // Style table header
        JTableHeader header = tblReservations.getTableHeader();
        header.setBackground(new Color(120, 220, 120));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        
        // Initialize table model
        initTableModel();
        
        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(tblReservations);
        
        // Add components to table panel
        tablePanel.add(headerPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }

    private void initUnpaidInvoicesTableModel() {
        modelUnpaidInvoices = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã hóa đơn", "Thời gian tạo", "Mã bàn", "Nhân viên", "Trạng thái"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUnpaidInvoices.setModel(modelUnpaidInvoices);
    }


    private JPanel createUnpaidInvoicesTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(30, 129, 191),
                    0, getHeight(), new Color(25, 105, 170));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 35));
        
        JLabel headerLabel = new JLabel("DANH SÁCH HÓA ĐƠN CHƯA THANH TOÁN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        tblUnpaidInvoices = new JTable();
        tblUnpaidInvoices.setFont(new Font("Segoe UI", 0, 14));
        tblUnpaidInvoices.setRowHeight(30);
        tblUnpaidInvoices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUnpaidInvoices.setGridColor(new Color(240, 240, 240));
        
        JTableHeader header = tblUnpaidInvoices.getTableHeader();
        header.setBackground(new Color(120, 220, 120));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        
        initUnpaidInvoicesTableModel();
        
        JScrollPane scrollPane = new JScrollPane(tblUnpaidInvoices);
        
        tablePanel.add(headerPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }

    // Method to load data from database to table
    private void loadDonDatBanTruoc() {
        modelDonDatBan.setRowCount(0);
        dsDonDatBanTruoc.clear();
        
        try {
            dsDonDatBanTruoc = donDatBanTruocDAO.getAllDonDatBanTruoc();
            LocalDate today = LocalDate.now(); // Get the current date
            
            for (DonDatBanTruoc ddbt : dsDonDatBanTruoc) {
                // Filter reservations by today's date
                if (ddbt.getThoiGianNhanBan().toLocalDate().isEqual(today)) {
                    // Get customer information - handle potential nulls
                    KhachHang khachHang = ddbt.getKH();
                    if (khachHang == null && ddbt.getKH() != null && ddbt.getKH().getMaKH() != null) {
                        khachHang = khachHangDAO.getKhachHangById(ddbt.getKH().getMaKH());
                    }
                    
                    // Format time
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    String gioNhan = ddbt.getThoiGianNhanBan().format(timeFormatter);
                    
                    // Add row to table
                    Object[] row = {
                        ddbt.getMaDonDatBanTruoc(),
                        ddbt.getBan() != null ? ddbt.getBan().getMaBan() : "",
                        khachHang != null ? khachHang.getTenKH() : "N/A",  
                        khachHang != null ? khachHang.getSdtKH() : "N/A", 
                        gioNhan,
                        ddbt.getSoLuongKhach()
                    };
                    
                    modelDonDatBan.addRow(row);
                }
            }
            
            // If there are rows, select the first one
            if (tblReservations.getRowCount() > 0) {
                tblReservations.setRowSelectionInterval(0, 0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Lỗi khi tải dữ liệu đơn đặt bàn: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
    
    private void loadUnpaidInvoices() {
        modelUnpaidInvoices.setRowCount(0);  // Xóa dữ liệu cũ

        dsDonDatBanTrucTiep = hd_dao.timHoaDonTheoTrangThai(0); // Lấy hóa đơn chưa thanh toán
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (HoaDon hd : dsDonDatBanTrucTiep) {
            String trangThai = getStatusText(hd.getTrangThai());

            Object[] row = {
                hd.getMaHD(),
                hd.getThoiGianTaoDon().format(formatter),
                hd.getBan().getMaBan(),
                hd.getNV().getTenNV(),
                trangThai
            };
            modelUnpaidInvoices.addRow(row);
        }

        // Gán renderer cho cột trạng thái
//        tblUnpaidInvoices.getColumnModel().getColumn(4).setCellRenderer(new StatusColumnRenderer());
//
//        if (tblUnpaidInvoices.getRowCount() > 0) {
//            tblUnpaidInvoices.setRowSelectionInterval(0, 0);
//            trangThaiButton(0);
//        }
    }


    
    // Method to refresh the table data
    public void refreshData() {
    	loadDonDatBanTruoc();
        loadUnpaidInvoices();
        loadAndUpdateStatistics();
    }

    // Add selection listener to handle row selection
    private void addTableSelectionListener() {
        tblReservations.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblReservations.getSelectedRow();
                if (selectedRow >= 0) {
                    // Handle row selection (e.g., show details)
                    // You can implement this later if needed
                }
            }
        });
    }

    private void setupReservationTable() {
        donDatBanTruocDAO = new DonDatBanTruoc_DAO();
        khachHangDAO = new KhachHang_DAO();
        nhanVienDAO = new NhanVien_DAO();
        dsDonDatBanTruoc = new ArrayList<>();
        JPanel tablePanel = createReservationTablePanel();
        addTableSelectionListener();
        loadDonDatBanTruoc();
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

    private void setupEventHandlers() {
        // Button event handlers
        btnMoCa.addActionListener(this);
        btnKetCa.addActionListener(this);
        btnLichSu.addActionListener(this);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Handle button events
        if (source == btnMoCa) {
            handleMoCa();
        } 
        else if (source == btnKetCa) {
            handleKetCa();
        } 
        else if (source == btnLichSu) {
            handleLichSu();
        }
    }
    
    @Override
    public void dispose() {
        // Stop the timer when disposing the frame
        if (closeShiftReminderTimer != null) {
            closeShiftReminderTimer.stop();
        }
        super.dispose();
    }
    private void nhacNhoDongCa() {
        closeShiftReminderTimer = new Timer(60000, e -> {
            if (currentUser == null) return;
            try {
                ChiTietCaLamViec activeShift = chiTietCaLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());
                if (activeShift != null) {
                    LocalDateTime now = LocalDateTime.now();
                    CaLamViec caLamViec = activeShift.getCaLamViec();
                    LocalDateTime reminderTime = caLamViec.getThoiGianKetThuc().minusMinutes(15);
                    LocalDateTime autoCloseTime = caLamViec.getThoiGianKetThuc().plusMinutes(30);

                    if ("CA03".equals(caLamViec.getMaCaLV())) {
                        if (now.isAfter(reminderTime)) {
                            // Only show reminder for CA03, don't auto-close
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                            JOptionPane.showMessageDialog(this,
                                    "Ca tối " + caLamViec.getTenCaLV() + " sắp kết thúc. Vui lòng đóng ca khi hết khách.",
                                    "Nhắc nhở", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (now.isAfter(reminderTime) && now.isBefore(autoCloseTime)) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                            JOptionPane.showMessageDialog(this,
                                    "Ca làm việc " + caLamViec.getTenCaLV() + " sắp kết thúc. Vui lòng đóng ca trước " +
                                            caLamViec.getThoiGianKetThuc().format(formatter) + ".",
                                    "Nhắc nhở", JOptionPane.INFORMATION_MESSAGE);
                        } else if (now.isAfter(autoCloseTime)) {
                            autoCloseShift(activeShift, caLamViec);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ThongKeCaLamViecDau_GUI.class.getName()).log(Level.SEVERE, "Lỗi trong timer nhắc nhở đóng ca", ex);
            }
        });
        closeShiftReminderTimer.start();
    }


    private void autoCloseShift(ChiTietCaLamViec activeShift, CaLamViec caLamViec) {
        try {
            // Skip auto-closing for night shift (CA03)
            if ("CA03".equals(caLamViec.getMaCaLV())) {
                JOptionPane.showMessageDialog(this,
                        "Ca tối đã quá giờ quy định. Vui lòng đợi hết khách và đóng ca thủ công.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            LocalDateTime now = LocalDateTime.now();
            String note = "Tự động đóng ca do quá thời gian quy định.\n" +
                    "Hóa đơn chưa thanh toán sẽ được chuyển sang ca tiếp theo.";
            double totalCash = chiTietCaLamViecDAO.getPreviousShiftFinalAmount(currentUser.getMaNV(), caLamViec.getMaCaLV());

            ArrayList<HoaDon> unpaidInvoices = getUnpaidInvoicesForShift(caLamViec.getMaCaLV());
            if (!unpaidInvoices.isEmpty()) {
                String nextShiftCode = getNextShiftCode(caLamViec.getMaCaLV());
                for (HoaDon hd : unpaidInvoices) {
                    hoaDonDAO.updateShiftCode(hd.getMaHD(), nextShiftCode);
                    note += "\nHóa đơn chưa thanh toán " + hd.getMaHD() + " đã được chuyển sang ca " + nextShiftCode;
                }
            }

            boolean success = chiTietCaLamViecDAO.updateChiTietCaLamViecEndShift(
                    caLamViec.getMaCaLV(),
                    currentUser.getMaNV(),
                    now,
                    totalCash,
                    note);

            if (success) {
                caLamViec.setTrangThai(false);
                success = caLamViecDAO.updateCaLamViec(caLamViec);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Ca làm việc đã được đóng tự động do quá thời gian quy định.",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                    updateUIForClosedShift();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tự động đóng ca: " + ex.getMessage() + "\nVui lòng liên hệ quản trị viên.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ThongKeCaLamViecDau_GUI.class.getName()).log(Level.SEVERE, "Lỗi tự động đóng ca", ex);
        }
    }


    private String getNextShiftCode(String currentShiftCode) {
        switch (currentShiftCode) {
            case "CA01":
                return "CA02";
            case "CA02":
                return "CA03";
            case "CA03":
                return "CA01";
            default:
                return "CA01";
        }
    }

    private ArrayList<HoaDon> getUnpaidInvoicesForShift(String maCaLV) {
        ArrayList<HoaDon> unpaidInvoices = new ArrayList<>();
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        try {
            ArrayList<HoaDon> allInvoices = hoaDonDAO.getAllHoaDon();
            for (HoaDon hd : allInvoices) {
                if (hd.getMaHD().contains(maCaLV) && hd.getThoiGianThanhToan() == null) {
                    unpaidInvoices.add(hd);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ThongKeCaLamViecDau_GUI.class.getName()).log(Level.SEVERE, "Lỗi lấy hóa đơn chưa thanh toán", e);
        }
        return unpaidInvoices;
    }

    private void handleMoCa() {
    	try {
    		if (currentUser == null) {
                JOptionPane.showMessageDialog(this,
                        "Không thể lấy thông tin nhân viên. Vui lòng đăng nhập lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            ChiTietCaLamViec activeShift = chiTietCaLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());
            if (activeShift != null) {
                JOptionPane.showMessageDialog(this,
                        "Bạn đang có ca làm việc mở. Vui lòng kết ca trước khi mở ca mới.",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDateTime today = now.toLocalDate().atStartOfDay();
            LocalDateTime caSangStart = today.withHour(3).withMinute(0).withSecond(0);
            LocalDateTime caSangOpenWindow = caSangStart.minusMinutes(30); // 7:30
            LocalDateTime caSangEnd = today.withHour(6).withMinute(0).withSecond(0);
            LocalDateTime caChieuStart = today.withHour(6).withMinute(0).withSecond(0);
            LocalDateTime caChieuOpenWindow = caChieuStart.minusMinutes(30); // 13:30
            LocalDateTime caChieuEnd = today.withHour(18).withMinute(0).withSecond(0);
            LocalDateTime caToiStart = today.withHour(18).withMinute(0).withSecond(0);
            LocalDateTime caToiOpenWindow = caToiStart.minusMinutes(30); // 17:30
            LocalDateTime caToiEnd = today.withHour(22).withMinute(0).withSecond(0);

            String maCaLV;
            String tenCaLV;
            LocalDateTime thoiGianBatDau;
            LocalDateTime thoiGianKetThuc;

            // Kiểm tra và chọn ca phù hợp
            if (now.isAfter(caSangOpenWindow) && now.isBefore(caSangEnd)) {
                maCaLV = "CA01";
                tenCaLV = "Ca sáng";
                thoiGianBatDau = caSangStart;
                thoiGianKetThuc = caSangEnd;
                if (now.isAfter(caSangStart)) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn đang mở ca sáng muộn. Giờ mở ca khuyến nghị: trước 08:00. Tiếp tục mở ca?",
                            "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) return;
                }
            } else if (now.isAfter(caChieuOpenWindow) && now.isBefore(caChieuEnd)) {
                maCaLV = "CA02";
                tenCaLV = "Ca chiều";
                thoiGianBatDau = caChieuStart;
                thoiGianKetThuc = caChieuEnd;
                if (now.isAfter(caChieuStart)) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn đang mở ca chiều muộn. Giờ mở ca khuyến nghị: trước 14:00. Tiếp tục mở ca?",
                            "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) return;
                }
            } else if (now.isAfter(caToiOpenWindow) && now.isBefore(caToiEnd)) {
                maCaLV = "CA03";
                tenCaLV = "Ca tối";
                thoiGianBatDau = caToiStart;
                thoiGianKetThuc = caToiEnd;
                if (now.isAfter(caToiStart)) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn đang mở ca tối muộn. Giờ mở ca khuyến nghị: trước 18:00. Tiếp tục mở ca?",
                            "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) return;
                }
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                JOptionPane.showMessageDialog(this,
                        "Không thể mở ca vào thời điểm này. Khung giờ mở ca:\n" +
                                "Ca sáng: " + caSangOpenWindow.format(formatter) + " - 14:00\n" +
                                "Ca chiều: " + caChieuOpenWindow.format(formatter) + " - 18:00\n" +
                                "Ca tối: " + caToiOpenWindow.format(formatter) + " - 22:00",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra ca trước chỉ cho ngày hiện tại
            if (!"CA01".equals(maCaLV)) {
                String previousMaCaLV = "CA02".equals(maCaLV) ? "CA01" : "CA02";
                ChiTietCaLamViec previousShift = chiTietCaLamViecDAO.getShiftByMaCaLVAndDate(previousMaCaLV, now.toLocalDate());
                if (previousShift != null && previousShift.getGioKetThucThucTe() == null) {
                    JOptionPane.showMessageDialog(this,
                            "Ca trước (" + ("CA01".equals(previousMaCaLV) ? "Ca sáng" : "Ca chiều") + ") của ngày hôm nay chưa được đóng. Vui lòng đóng ca trước.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Kiểm tra ca đã được mở bởi nhân viên khác trong ngày hiện tại
            CaLamViec existingShift = caLamViecDAO.getCaLamViecByMaCaLV(maCaLV);
            if (existingShift != null && existingShift.getTrangThai()) {
                ChiTietCaLamViec otherActiveShift = chiTietCaLamViecDAO.getActiveShiftByMaCaLV(maCaLV);
                if (otherActiveShift != null && otherActiveShift.getGioBatDauThucTe().toLocalDate().isEqual(now.toLocalDate())) {
                    String employeeName = otherActiveShift.getNhanVien().getTenNV();
                    JOptionPane.showMessageDialog(this,
                            "Ca " + tenCaLV + " của ngày hôm nay đã được mở bởi nhân viên " + employeeName + ". Vui lòng liên hệ quản lý để xử lý.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Nhập số tiền ban đầu và ghi chú
            double initialAmount;
            String note = "Bắt đầu ca làm việc";
            double previousFinalAmount = chiTietCaLamViecDAO.getPreviousShiftFinalAmount(currentUser.getMaNV(), maCaLV);

            if ("CA01".equals(maCaLV) || previousFinalAmount <= 0) {
                initialAmount = 1000000;
            } else {
                initialAmount = previousFinalAmount;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String formattedDisplayTime = now.format(formatter);

            JTextField noteField = new JTextField(note);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Số tiền ban đầu (VNĐ):"));
            panel.add(new JLabel(formatCurrency(initialAmount)));
            panel.add(new JLabel("Ghi chú:"));
            panel.add(noteField);

            if ("CA01".equals(maCaLV)) {
                JLabel infoLabel = new JLabel("* Ca sáng luôn bắt đầu với số tiền cố định");
                infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                infoLabel.setForeground(new Color(0, 102, 204));
                panel.add(infoLabel);
            } else {
                JLabel infoLabel = new JLabel("* Số tiền được lấy từ kết quả ca trước của cùng ngày");
                infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                infoLabel.setForeground(new Color(0, 102, 204));
                panel.add(infoLabel);
            }

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Mở Ca Làm Việc", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                note = noteField.getText();
            } else {
                return;
            }

            // Lưu ca làm việc
            CaLamViec caLamViec = new CaLamViec(maCaLV, tenCaLV, thoiGianBatDau, thoiGianKetThuc, true);
            boolean success = caLamViecDAO.updateCaLamViec(caLamViec);

            if (!success) {
                success = caLamViecDAO.insertCaLamViec(caLamViec);
            }

            if (!success) {
                JOptionPane.showMessageDialog(this,
                        "Không thể lưu thông tin ca làm việc vào cơ sở dữ liệu. Vui lòng liên hệ quản trị viên.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime gioBatDauThucTe = now;
            ChiTietCaLamViec chiTiet = new ChiTietCaLamViec(caLamViec, currentUser, gioBatDauThucTe, initialAmount, note);
            success = chiTietCaLamViecDAO.insertChiTietCaLamViec(chiTiet);

            if (!success) {
                JOptionPane.showMessageDialog(this,
                        "Không thể lưu chi tiết ca làm việc vào cơ sở dữ liệu. Vui lòng liên hệ quản trị viên.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Cập nhật giao diện
            updateUIForOpenShift(caLamViec, chiTiet, formattedDisplayTime, initialAmount, note);
            revalidate();
            repaint();

            btnMoCa.setBackground(Color.GRAY);
            btnMoCa.setEnabled(false);
            btnKetCa.setBackground(new Color(0, 204, 0));
            btnKetCa.setEnabled(true);

            JOptionPane.showMessageDialog(this,
                    "Đã mở ca làm việc thành công!\nThời gian: " + formattedDisplayTime + "\nCa làm việc: " + tenCaLV,
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi hệ thống khi mở ca. Vui lòng thử lại hoặc liên hệ quản trị viên.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ThongKeCaLamViecDau_GUI.class.getName()).log(Level.SEVERE, "Lỗi khi mở ca", e);
        }
    }

    // Update this method to include the note parameter
    private void updateUIForOpenShift(CaLamViec caLamViec, ChiTietCaLamViec chiTietCa, String timeOpened,
            double initialAmount, String note) {
        lblTimeOpened.setText(timeOpened);
        lblInitialAmount.setText(formatCurrency(initialAmount));
        txtGhiChu.setText(note);
        txtGhiChu.setForeground(Color.BLACK);
        statusIndicator.setBackground(new Color(46, 175, 80));
        btnMoCa.setEnabled(false);
        btnKetCa.setEnabled(true);
        revalidate();
        repaint();
    }

    // New helper method to find all relevant shift info components
    private Component[] findShiftInfoComponents() {
        ArrayList<Component> components = new ArrayList<>();
        findComponentsRecursively(getContentPane(), components);
        return components.toArray(new Component[0]);
    }

    // Recursive helper method to find all components
    private void findComponentsRecursively(Container container, ArrayList<Component> components) {
        for (Component comp : container.getComponents()) {
            // Check if this component is one we're looking for
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText() != null && 
                    (label.getText().contains("Thời gian mở ca") || 
                     label.getText().contains("Số tiền ban đầu"))) {
                    components.add(label);
                }
            } else if (comp instanceof JPanel && comp.getPreferredSize().width == 10 
                    && comp.getPreferredSize().height == 10) {
                components.add(comp);
            } else if (comp instanceof JTextArea) {
                components.add(comp);
            }
            
            // Continue searching in nested containers
            if (comp instanceof Container) {
                findComponentsRecursively((Container) comp, components);
            }
        }
    }


    private void updateUIComponents(Container container, String timeOpened, double initialAmount, String note) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                // Update time field
                if ("Thời gian mở ca:".equals(label.getText())) {
                    Container parent = label.getParent();
                    for (Component sibling : parent.getComponents()) {
                        if (sibling instanceof JPanel) {
                            for (Component innerComp : ((JPanel) sibling).getComponents()) {
                                if (innerComp instanceof JLabel) {
                                    ((JLabel) innerComp).setText(timeOpened);
                                    break;
                                }
                            }
                        }
                    }
                }
                
                // Update amount field
                if ("Số tiền ban đầu:".equals(label.getText())) {
                    Container parent = label.getParent();
                    for (Component sibling : parent.getComponents()) {
                        if (sibling instanceof JLabel && sibling != label) {
                            ((JLabel) sibling).setText(formatCurrency(initialAmount));
                        }
                    }
                }
            }
            
            // Update status indicator
            if (comp instanceof JPanel && ((JPanel) comp).getPreferredSize().width == 10 
                    && ((JPanel) comp).getPreferredSize().height == 10) {
                ((JPanel) comp).setBackground(new Color(46, 175, 80));
            }
            
            // Continue recursion for all containers
            if (comp instanceof Container) {
                updateUIComponents((Container) comp, timeOpened, initialAmount, note);
            }
        }
    }

 // Add this method to ThongKeCaLamViecDau_GUI
    private void loadAndUpdateStatistics() {
        try {
            // Get today's date
            LocalDate today = LocalDate.now();
            
            // Initialize DAOs
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            DonDatBanTruoc_DAO donDatBanTruocDAO = new DonDatBanTruoc_DAO();
            
            // Get all invoices
            ArrayList<HoaDon> allInvoices = hoaDonDAO.getAllHoaDon();
            
            // Initialize counters
            int invoiceCount = 0;
            double totalRevenue = 0;
            int customerCount = 0;
            
            // Count invoices and calculate revenue for today
            for (HoaDon hd : allInvoices) {
                if (hd.getThoiGianThanhToan() != null && 
                    hd.getThoiGianThanhToan().toLocalDate().equals(today)) {
                    invoiceCount++;
                    totalRevenue += hd.getTongThanhToan();
                    
                    // Add customer count if available
                    if (hd.getSoKhach() > 0) {
                        customerCount += hd.getSoKhach();
                    }
                }
            }
            
            // Count table reservations for today
            ArrayList<DonDatBanTruoc> dsDonDatBan = donDatBanTruocDAO.getAllDonDatBanTruoc();
            int tableReservationCount = 0;
            
            for (DonDatBanTruoc ddbt : dsDonDatBan) {
                if (ddbt.getThoiGianNhanBan() != null && 
                    ddbt.getThoiGianNhanBan().toLocalDate().equals(today)) {
                    tableReservationCount++;
                    
                    // If we don't have customer count from invoices, get it from reservations
                    if (customerCount == 0 && ddbt.getSoLuongKhach() > 0) {
                        customerCount += ddbt.getSoLuongKhach();
                    }
                }
            }
            
            // Format the revenue
            @SuppressWarnings("deprecation")
			NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            String formattedRevenue = formatter.format(totalRevenue) + " VNĐ";
            
            // Update the statistics cards
            updateStatCard(0, String.valueOf(invoiceCount));
            updateStatCard(1, formattedRevenue);
            updateStatCard(2, String.valueOf(tableReservationCount));
            updateStatCard(3, String.valueOf(customerCount));
            
            System.out.println("Statistics updated: " + invoiceCount + " invoices, " + 
                              formattedRevenue + " revenue, " +
                              tableReservationCount + " tables, " +
                              customerCount + " customers");
                              
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading statistics: " + e.getMessage());
        }
    }

    
    // Helper method to update individual statistic cards
    private void updateStatCard(int cardIndex, String value) {
        if (statisticsPanel.getComponentCount() > cardIndex) {
            JPanel card = (JPanel) statisticsPanel.getComponent(cardIndex);
            for (Component comp : card.getComponents()) {
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    if (label.getFont().getSize() == 24) {
                        label.setText(value);
                        break;
                    }
                }
            }
        }
    }

    
    private void loadActiveShiftInfo() {
        if (currentUser == null) return;

        try {
        	ChiTietCaLamViec activeShift = chiTietCaLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());

//            ChiTietCaLamViec activeShift = caLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());
            if (activeShift != null) {
                CaLamViec caLamViec = activeShift.getCaLamViec();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                String formattedTime = activeShift.getGioBatDauThucTe().format(formatter);
                String formattedAmount = formatCurrency(activeShift.getSoTienBanDau());
                String note = activeShift.getGhiChu();

                updateUIForOpenShift(caLamViec, activeShift, formattedTime, activeShift.getSoTienBanDau(), note);
                revalidate();
                repaint();

                btnMoCa.setEnabled(false);
                btnKetCa.setEnabled(true);
//                menuBar.getMniMoCa().setEnabled(false);
//                menuBar.getMniKetCa().setEnabled(true);
            } else {
                // Nếu không có ca hoạt động, đảm bảo giao diện hiển thị trạng thái mặc định
                updateUIForClosedShift();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải thông tin ca làm việc: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to update note field
    private void updateNoteField(JPanel panel, String note) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                updateNoteField((JPanel) comp, note);
            } else if (comp instanceof JTextArea) {
                JTextArea txtArea = (JTextArea) comp;
                txtArea.setText(note);
                txtArea.setForeground(Color.BLACK);
            } else if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                Component view = scrollPane.getViewport().getView();
                if (view instanceof JTextArea) {
                    JTextArea txtArea = (JTextArea) view;
                    txtArea.setText(note);
                    txtArea.setForeground(Color.BLACK);
                }
            }
        }
    }


    // Add this new method to more precisely target the labels that need updating
    private void updateSpecificLabels(Container container, String timeOpened, double initialAmount, String note) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                
                // Check if this panel contains our labels by recursively searching
                if (panel.getLayout() instanceof BorderLayout) {
                    for (Component c : panel.getComponents()) {
                        if (c instanceof JPanel) {
                            JPanel subPanel = (JPanel) c;
                            for (Component inner : subPanel.getComponents()) {
                                // Update time label
                                if (inner instanceof JLabel) {
                                    JLabel label = (JLabel) inner;
                                    if ("Thời gian mở ca:".equals(label.getText())) {
                                        // Find and update the value label which should be a sibling
                                        for (Component sibling : subPanel.getComponents()) {
                                            if (sibling instanceof JPanel) {
                                                JPanel valuePanel = (JPanel) sibling;
                                                for (Component valueComp : valuePanel.getComponents()) {
                                                    if (valueComp instanceof JLabel) {
                                                        ((JLabel) valueComp).setText(timeOpened);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    // Update amount label
                                    else if ("Số tiền ban đầu:".equals(label.getText())) {
                                        for (Component sibling : subPanel.getComponents()) {
                                            if (sibling instanceof JLabel && sibling != label) {
                                                ((JLabel) sibling).setText(formatCurrency(initialAmount));
                                            }
                                        }
                                    }
                                }
                            }
                            
                            // Update note field if we find the JTextArea
                            for (Component inner : subPanel.getComponents()) {
                                if (inner instanceof JScrollPane) {
                                    JScrollPane scrollPane = (JScrollPane) inner;
                                    Component view = scrollPane.getViewport().getView();
                                    if (view instanceof JTextArea) {
                                        JTextArea textArea = (JTextArea) view;
                                        textArea.setText(note);
                                        textArea.setForeground(Color.BLACK);
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Continue searching in nested containers
                updateSpecificLabels(panel, timeOpened, initialAmount, note);
            }
        }
    }


    // Method to handle closing a shift
 // Method to handle closing a shift
    private void handleKetCa() {
        try {
            // Check if the user is logged in
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, 
                    "Không thể lấy thông tin nhân viên. Vui lòng đăng nhập lại.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ChiTietCaLamViec activeShift = chiTietCaLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());

            if (activeShift == null) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy ca làm việc đang mở. Vui lòng mở ca trước khi kết ca.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CaLamViec caLamViec = activeShift.getCaLamViec();
            
            BaoCaoKetCaTEST_GUI baoCaoKetCa = new BaoCaoKetCaTEST_GUI(this, currentUser, caLamViec);
            baoCaoKetCa.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi kết ca: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void handleLichSu() {
        try {
            // Check if the user is logged in
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, 
                    "Không thể lấy thông tin nhân viên. Vui lòng đăng nhập lại.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Since this is a history view, we don't need to check for active shift
            // Just open the history GUI with the current user information
            
            // Create and show the shift history GUI
            LichSuCaLamViec_GUI lichSuGUI = new LichSuCaLamViec_GUI(currentUser);
            lichSuGUI.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi mở giao diện lịch sử ca làm việc: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }



    private void updateUIForClosedShift() {
        // Reset the time label
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                findAndUpdateLabels((JPanel) comp, "Thời gian mở ca:", "Chưa mở ca");
                findAndUpdateLabels((JPanel) comp, "Số tiền ban đầu:", "0 VNĐ");
            }
        }
        
        // Update status indicator color
        updateStatusIndicator(Color.GRAY);
        
        // Enable/disable buttons appropriately
        btnMoCa.setEnabled(true);
        btnKetCa.setEnabled(false);
//        menuBar.getMniMoCa().setEnabled(false);
//        menuBar.getMniKetCa().setEnabled(true);
    }

    // Helper method to find and update labels in panels
    private void findAndUpdateLabels(JPanel panel, String labelText, String newValue) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                findAndUpdateLabels((JPanel) comp, labelText, newValue);
            } else if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (labelText.equals(label.getText())) {
                    // Find the container that has the label pair
                    Container parent = label.getParent();
                    if (parent.getLayout() instanceof BorderLayout) {
                        // For panels with BorderLayout, the value is likely in CENTER
                        for (Component sibling : parent.getComponents()) {
                            if (sibling instanceof JLabel && sibling != label) {
                                ((JLabel) sibling).setText(newValue);
                                return;
                            }
                        }
                    } else {
                        // For other layouts, check all siblings
                        for (Component sibling : parent.getComponents()) {
                            if (sibling instanceof JLabel && sibling != label) {
                                ((JLabel) sibling).setText(newValue);
                                return;
                            }
                        }
                    }
                    
                    // If we haven't found the value label yet, check the parent's parent
                    Container grandParent = parent.getParent();
                    if (grandParent != null) {
                        for (Component uncle : grandParent.getComponents()) {
                            if (uncle instanceof JLabel && uncle != label) {
                                ((JLabel) uncle).setText(newValue);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


    // Helper method to update the status indicator color
    private void updateStatusIndicator(Color color) {
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                findAndUpdateStatusIndicator((JPanel) comp, color);
            }
        }
    }

    // Helper method to find and update status indicator
    private void findAndUpdateStatusIndicator(JPanel panel, Color color) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                findAndUpdateStatusIndicator((JPanel) comp, color);
            } else if (comp instanceof JPanel && ((JPanel) comp).getBackground() == Color.GRAY) {
                // This is a simplified check - in a real app, you might need a more specific identifier
                JPanel indicator = (JPanel) comp;
                if (indicator.getPreferredSize().width == 10 && indicator.getPreferredSize().height == 10) {
                    indicator.setBackground(color);
                    return;
                }
            }
        }
    }

    // Helper method to format currency values
    private String formatCurrency(double amount) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getNumberInstance(new java.util.Locale("vi", "VN"));
        return formatter.format(amount) + " VNĐ";
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectDB.getInstance().connect();

        // Chạy ở chế độ test
        new ThongKeCaLamViecDau_GUI();
    }
}