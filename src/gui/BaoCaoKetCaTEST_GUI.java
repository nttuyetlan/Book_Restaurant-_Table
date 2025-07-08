package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import dao.CaLamViec_DAO;
import dao.ChiTietCaLamViec_DAO;
import entity.CaLamViec;
import entity.ChiTietCaLamViec;
import entity.NhanVien;
import dao.HoaDon_DAO;
import dao.ChiTietHoaDon_DAO;
import entity.HoaDon;
import java.util.ArrayList;
public class BaoCaoKetCaTEST_GUI extends JDialog {
    private static final long serialVersionUID = 1L;

    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private final Color HEADER_COLOR = new Color(25, 79, 115);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    final Color BUTTON_BLUE = new Color(52, 152, 219);
    final Color BUTTON_BLUE_HOVER = new Color(70, 170, 230);
    final Color BUTTON_RED = new Color(231, 76, 60);
    final Color BUTTON_RED_HOVER = new Color(241, 96, 80);
    
    private JLabel lblEmployeeName, lblShiftName, lblTimeOpened, lblTimeClosed;
    private JLabel lblTotalInvoices, lblPaidInvoices, lblUnpaidInvoices;
    private JLabel lblInitialAmount, lblTotalRevenue, lblTransferPayments;
    private JLabel lblTotalCashHandover, lblActualCashHandover, lblDiscrepancy;
    private JTextArea txtNotes;
    private JButton btnCancel, btnCloseShift, btnCloseAndPrint;
    private JSpinner[] denominationSpinners;
    private JLabel[] denominationLabels;
    private int[] denominations = {500000, 200000, 100000, 50000, 20000, 10000, 5000, 2000, 1000};

    private NhanVien currentUser;
    private CaLamViec caLamViec;
    private ChiTietCaLamViec activeShift;
    private ThongKeCaLamViecDau_GUI parentFrame;
    private CaLamViec_DAO caLamViecDAO;
    private  ChiTietCaLamViec_DAO chiTietCaLamViecDAO;

    private double initialAmount = 0;
    
    public BaoCaoKetCaTEST_GUI(ThongKeCaLamViecDau_GUI parent, NhanVien user, CaLamViec caLV) {
        super(parent, "Báo Cáo Kết Ca", true);
        this.parentFrame = parent;
        this.currentUser = user;
        this.caLamViec = caLV;
        this.caLamViecDAO = new CaLamViec_DAO();
        this.chiTietCaLamViecDAO = new ChiTietCaLamViec_DAO();
        try {
            initComponents();
            loadShiftData();
            setSize(700, 650);
            setLocationRelativeTo(parent);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo giao diện: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("BÁO CÁO KẾT CA", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Information Panel (contains employee and shift info)
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBackground(BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        ));

        // Row 1: Employee name and current shift
        lblEmployeeName = createStyledLabel("Nhân viên: " + (currentUser != null ? currentUser.getTenNV() : "Chưa đăng nhập"));
        lblShiftName = createStyledLabel("Ca hiện tại: " + (caLamViec != null ? caLamViec.getTenCaLV() : ""));

        // Row 2: Opening time and current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        lblTimeOpened = createStyledLabel("Giờ mở ca: --:--");
        lblTimeClosed = createStyledLabel("Giờ hiện tại: " + LocalDateTime.now().format(formatter));

        // Row 3: Initial amount
        lblInitialAmount = createStyledLabel("Tiền mặt đầu ca (1): 0 VNĐ");
        JLabel emptyLabel = createStyledLabel(""); // Empty label to maintain grid layout

        // Add components to infoPanel
        infoPanel.add(lblEmployeeName);
        infoPanel.add(lblShiftName);
        infoPanel.add(lblTimeOpened);
        infoPanel.add(lblTimeClosed);
        infoPanel.add(lblInitialAmount);
        infoPanel.add(emptyLabel);

        // Left panel for invoice info
        JPanel invoicePanel = createSectionPanel("Thông Tin Hóa Đơn");
        invoicePanel.setLayout(new GridLayout(5, 1, 0, 5));
        
        lblTotalInvoices = createStyledLabel("Tổng số hóa đơn: 0");
        lblPaidInvoices = createStyledLabel("Đã thanh toán: 0");
        lblUnpaidInvoices = createStyledLabel("Chưa thanh toán: 0");
        lblTotalRevenue = createStyledLabel("Tổng doanh thu (2): 0 VNĐ");
        lblTransferPayments = createStyledLabel("Tổng kết tiền chuyển khoản (3): 0 VNĐ");
        
        invoicePanel.add(lblTotalInvoices);
        invoicePanel.add(lblPaidInvoices);
        invoicePanel.add(lblUnpaidInvoices);
        invoicePanel.add(lblTotalRevenue);
        invoicePanel.add(lblTransferPayments);

        // Denominations panel
        JPanel denominationsPanel = createSectionPanel("Thống Kê Tiền Mặt");
        denominationsPanel.setLayout(new GridBagLayout());
        GridBagConstraints denomGbc = new GridBagConstraints();
        denomGbc.insets = new Insets(3, 5, 3, 5);
        denomGbc.fill = GridBagConstraints.HORIZONTAL;

        denominationLabels = new JLabel[denominations.length];
        denominationSpinners = new JSpinner[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            denomGbc.gridx = 0;
            denomGbc.gridy = i;
            denomGbc.weightx = 0.6;
            denominationLabels[i] = createStyledLabel("Mệnh giá " + denominations[i]/1000 + "k");
            denominationsPanel.add(denominationLabels[i], denomGbc);

            denomGbc.gridx = 1;
            denomGbc.weightx = 0.4;
            denominationSpinners[i] = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            denominationSpinners[i].addChangeListener(e -> updateTotalCashHandover());
            denominationSpinners[i].setPreferredSize(new Dimension(80, 25));
            JComponent editor = denominationSpinners[i].getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                ((JSpinner.DefaultEditor)editor).getTextField().setHorizontalAlignment(JTextField.RIGHT);
            }
            denominationsPanel.add(denominationSpinners[i], denomGbc);
        }

        // Summary panel
        JPanel summaryPanel = createSectionPanel("Tổng Kết");
        summaryPanel.setLayout(new GridLayout(3, 1, 0, 10));
        
        lblTotalCashHandover = createStyledLabel("Tổng tiền cuối ca (4): 0");
        lblActualCashHandover = createStyledLabel("Tiền trong mặt cuối ca (5) = (1)+(2)-(3): 0");
        lblDiscrepancy = createStyledLabel("Chênh lệch (5)-(4): 0");
        
        Font boldFont = new Font(lblTotalCashHandover.getFont().getName(), Font.BOLD, lblTotalCashHandover.getFont().getSize());
        lblTotalCashHandover.setFont(boldFont);
        lblDiscrepancy.setFont(boldFont);
        
        summaryPanel.add(lblTotalCashHandover);
        summaryPanel.add(lblActualCashHandover);
        summaryPanel.add(lblDiscrepancy);

        // Notes panel
        JPanel notesPanel = createSectionPanel("Ghi Chú");
        txtNotes = new JTextArea(4, 20);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setFont(new Font("Arial", Font.PLAIN, 13));
        txtNotes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane notesScrollPane = new JScrollPane(txtNotes);
        notesPanel.setLayout(new BorderLayout());
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        // Layout organization
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.add(infoPanel, BorderLayout.NORTH);
        leftPanel.add(invoicePanel, BorderLayout.CENTER);

        // Create the main center content area with GridBagLayout
        JPanel centerContentPanel = new JPanel(new GridBagLayout());
        centerContentPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.insets = new Insets(0, 0, 5, 5);
        mainGbc.weightx = 0.5;

        // Left side (info + invoice panels)
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.gridheight = 2;
        mainGbc.weighty = 1.0;
        centerContentPanel.add(leftPanel, mainGbc);

        // Right top (denominations panel)
        mainGbc.gridx = 1;
        mainGbc.gridy = 0;
        mainGbc.gridheight = 1;
        mainGbc.weighty = 0.7;
        centerContentPanel.add(denominationsPanel, mainGbc);

        // Bottom right (notes panel)
        mainGbc.gridx = 1;
        mainGbc.gridy = 1;
        mainGbc.weighty = 0.3;
        centerContentPanel.add(notesPanel, mainGbc);

        // Add summary panel below invoice panel in the left column
        JPanel leftBottomPanel = new JPanel(new BorderLayout());
        leftBottomPanel.setBackground(BACKGROUND_COLOR);
        leftBottomPanel.add(summaryPanel, BorderLayout.CENTER);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Create buttons
        btnCancel = new JButton("Hủy bỏ");
        btnCloseShift = new JButton("Kết ca");
        btnCloseAndPrint = new JButton("Kết ca và in báo cáo");

        // Apply styles
        styleModernButton(btnCancel, BUTTON_RED, BUTTON_RED_HOVER);
        styleModernButton(btnCloseShift, BUTTON_BLUE, BUTTON_BLUE_HOVER);
        styleModernButton(btnCloseAndPrint, BUTTON_BLUE, BUTTON_BLUE_HOVER);

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnCloseShift);
        buttonPanel.add(btnCloseAndPrint);

        // Add action listeners
        btnCancel.addActionListener(e -> dispose());
        btnCloseShift.addActionListener(e -> handleCloseShift(false));
        btnCloseAndPrint.addActionListener(e -> handleCloseShift(true));

        // Add the main content and button panel to the dialog
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerContentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void loadShiftData() {
        try {
            // Get active shift for this employee
            if (currentUser != null && caLamViec != null) {
                activeShift = chiTietCaLamViecDAO.getActiveShiftForEmployee(currentUser.getMaNV());
            	
                
                if (activeShift != null) {
                    // Format time information
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
                    
                    // Set employee and shift information
                    lblEmployeeName.setText("Nhân viên: " + currentUser.getTenNV() + " - " + currentUser.getMaNV());
                    lblShiftName.setText("Ca hiện tại: " + caLamViec.getTenCaLV());
                    lblTimeOpened.setText("Giờ mở ca: " + activeShift.getGioBatDauThucTe().format(formatter));
                    lblTimeClosed.setText("Giờ hiện tại: " + LocalDateTime.now().format(formatter));
                    
                    // Set financial information
                    initialAmount = activeShift.getSoTienBanDau();
                    lblInitialAmount.setText("Tiền mặt đầu ca (1): " + formatCurrency(initialAmount));
                    
                    // IMPORTANT: Call loadInvoiceData to get invoice information
                    loadInvoiceData(activeShift);
                    
                    // Update the total cash handover display
                    updateTotalCashHandover();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu ca làm việc: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadInvoiceData(ChiTietCaLamViec activeShift) {
        try {
            System.out.println("Beginning invoice data loading...");
            
            // Create DAO objects for accessing invoice data
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            
            // Get the start time of the shift
            LocalDateTime shiftStartTime = activeShift.getGioBatDauThucTe();
            LocalDateTime currentTime = LocalDateTime.now();
            
            System.out.println("Shift start time: " + shiftStartTime);
            System.out.println("Current time: " + currentTime);
            
            // Extract the shift code from the active shift
            String caCode = activeShift.getCaLamViec().getMaCaLV(); // Should be "CA01", "CA02", or "CA03"
            System.out.println("Current shift code: " + caCode);
            
            // Get all invoices from the database
            ArrayList<HoaDon> allInvoices = hoaDonDAO.getAllHoaDon();
            System.out.println("Total invoices in database: " + allInvoices.size());
            
            // Filter invoices for this shift based on invoice ID pattern and payment time
            ArrayList<HoaDon> shiftInvoices = new ArrayList<>();
            int paidInvoices = 0;
            double totalRevenue = 0;
            double transferPayments = 0;
            
            for (HoaDon hd : allInvoices) {
                String maHD = hd.getMaHD();
                System.out.println("Checking invoice: " + maHD + 
                                  ", Payment time: " + (hd.getThoiGianThanhToan() != null ? hd.getThoiGianThanhToan() : "null") +
                                  ", Payment method: " + hd.getHinhThucThanhToan());
                
                // Check if the invoice ID contains the current shift code
                if (maHD != null && maHD.contains(caCode) && hd.getThoiGianThanhToan() != null) {
                    shiftInvoices.add(hd);
                    System.out.println("✓ Added invoice: " + maHD + ", Amount: " + hd.getTongThanhToan());
                    
                    paidInvoices++;
                    totalRevenue += hd.getTongThanhToan();
                    
                    // Check if payment method is transfer
                    if (hd.getHinhThucThanhToan() != null && 
                        (hd.getHinhThucThanhToan().equalsIgnoreCase("Chuyển khoản") || 
                         hd.getHinhThucThanhToan().equalsIgnoreCase("Transfer"))) {
                        transferPayments += hd.getTongThanhToan();
                    }
                } else {
                    System.out.println("✗ Skipped invoice: " + maHD);
                }
            }
            
            // Update the UI with invoice information
            int totalInvoices = shiftInvoices.size();
            lblTotalInvoices.setText("Tổng số hóa đơn: " + totalInvoices);
            lblPaidInvoices.setText("Đã thanh toán: " + paidInvoices);
            lblUnpaidInvoices.setText("Chưa thanh toán: " + (totalInvoices - paidInvoices));
            lblTotalRevenue.setText("Tổng doanh thu (2): " + formatCurrency(totalRevenue));
            lblTransferPayments.setText("Tổng kết tiền chuyển khoản (3): " + formatCurrency(transferPayments));

            // Calculate the expected cash in drawer at the end of shift
            double expectedCash = initialAmount + totalRevenue - transferPayments;
            lblActualCashHandover.setText("Tiền trong két cuối ca (5) = (1)+(2)-(3): " + formatCurrency(expectedCash));
            // Update denomination counts based on the expected cash
            suggestDenominationCounts(expectedCash);

            // Add default note with shift summary
            String note = "Kết thúc ca làm việc " + caLamViec.getTenCaLV() + 
                          "\nNhân viên: " + currentUser.getTenNV() + " (" + currentUser.getMaNV() + ")" +
                          "\nSố lượng hóa đơn: " + totalInvoices + 
                          "\nTổng doanh thu: " + formatCurrency(totalRevenue) + 
                          "\nTiền mặt ban đầu: " + formatCurrency(initialAmount) +
                          "\nTiền chuyển khoản: " + formatCurrency(transferPayments) +
                          "\nTiền mặt dự kiến: " + formatCurrency(expectedCash);
            
            txtNotes.setText(note);
            
            // Update the display for discrepancy
            updateTotalCashHandover();
            
            System.out.println("Invoice data loaded successfully");
            System.out.println("Total invoices: " + totalInvoices);
            System.out.println("Total revenue: " + totalRevenue);
            System.out.println("Transfer payments: " + transferPayments);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading invoice data: " + e.getMessage());
        }
    }

	private void suggestDenominationCounts(double expectedCash) {
	    // This is an algorithm to suggest denominations that add up to the expected cash amount
	    long remainingAmount = (long)expectedCash;
	    
	    // Reset all spinners to zero first
	    for (JSpinner spinner : denominationSpinners) {
	        spinner.setValue(0);
	    }
	    
	    // Fill from largest to smallest denomination
	    for (int i = 0; i < denominations.length; i++) {
	        // Determine how many of this denomination can fit
	        int count = (int)(remainingAmount / denominations[i]);
	        
	        // Limit count to a reasonable number (no more than 20 of any denomination)
	        count = Math.min(count, 20);
	        
	        // Set the spinner value
	        denominationSpinners[i].setValue(count);
	        
	        // Reduce the remaining amount
	        remainingAmount -= count * denominations[i];
	    }
	}

	private void updateTotalCashHandover() {
	    long total = 0;
	    for (int i = 0; i < denominations.length; i++) {
	        int count = (int) denominationSpinners[i].getValue();
	        total += (long) count * denominations[i];
	    }
	    lblTotalCashHandover.setText("Tổng tiền cuối ca (4): " + formatCurrency(total));
	    updateDiscrepancy(total);
	}


	private void updateDiscrepancy(long totalCashHandover) {
	    // Extract the expected cash amount from the label text
	    String expectedCashText = lblActualCashHandover.getText()
	            .replace("Tiền trong két cuối ca (5) = (1)+(2)-(3): ", "")
	            .replace(" VNĐ", "");
	    
	    // Parse both values consistently
	    long expectedCash = parseCurrency(expectedCashText);
	    
	    // Now calculate discrepancy
	    long discrepancy = expectedCash - totalCashHandover;
	    
	    lblDiscrepancy.setText("Chênh lệch (5)-(4): " + formatCurrency(discrepancy));
	    
	    if (discrepancy != 0) {
	        lblDiscrepancy.setForeground(Color.RED);
	    } else {
	        lblDiscrepancy.setForeground(new Color(0, 128, 0)); // Dark green
	    }
	}

    


    private void handleCloseShift(boolean exportReport) {
        try {
            String notes = txtNotes.getText().trim();
            if (notes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Vui lòng nhập ghi chú.", 
                        "Thông tin thiếu", 
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Xác nhận kết ca?\nSau khi kết ca, bạn sẽ không thể cập nhật lại thông tin.", 
                    "Xác nhận", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            if (activeShift != null && caLamViec != null) {
                // Calculate total cash handover
                long totalCash = 0;
                for (int i = 0; i < denominations.length; i++) {
                    int count = (int) denominationSpinners[i].getValue();
                    totalCash += (long) count * denominations[i];
                }
                
                // Update the shift end time in database with actual cash amount
                LocalDateTime endTime = LocalDateTime.now();
                String additionalNote = txtNotes.getText();
                
                boolean success = chiTietCaLamViecDAO.updateChiTietCaLamViecEndShift(
                        caLamViec.getMaCaLV(), 
                        currentUser.getMaNV(), 
                        endTime, 
                        totalCash,
                        additionalNote);
                
                if (success) {
                    // Update shift status to closed
                    caLamViec.setTrangThai(false);
                    success = caLamViecDAO.updateCaLamViec(caLamViec);
                    
                    if (success) {
                        if (exportReport) {
                            // Export the shift report
                            exportReport();
                        }
                        
                        JOptionPane.showMessageDialog(this, 
                                "Kết ca thành công!", 
                                "Thông báo", 
                                JOptionPane.INFORMATION_MESSAGE);
                        
                        // Update parent UI to reflect closed shift
                        if (parentFrame != null) {
                            parentFrame.refreshData();
                        }
                        
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                                "Không thể cập nhật trạng thái ca làm việc!", 
                                "Lỗi", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Không thể cập nhật thời gian kết thúc ca làm việc!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Không tìm thấy thông tin ca làm việc!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    "Lỗi khi kết ca: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void exportReport() {
        try {
            // Create a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu báo cáo");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            String defaultFileName = "BaoCaoKetCa_" + 
                                    caLamViec.getMaCaLV() + "_" + 
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm")) + 
                                    ".xlsx";
            fileChooser.setSelectedFile(new java.io.File(defaultFileName));
            int userSelection = fileChooser.showSaveDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                // Parse invoice data
                String shiftCode = activeShift.getCaLamViec().getMaCaLV();
                String employeeName = currentUser.getTenNV();
                String employeeId = currentUser.getMaNV();
                
                // Extract data from labels
                String totalInvoicesText = lblTotalInvoices.getText().replace("Tổng số hóa đơn: ", "");
                String paidInvoicesText = lblPaidInvoices.getText().replace("Đã thanh toán: ", "");
                String totalRevenueText = lblTotalRevenue.getText().replace("Tổng doanh thu (2): ", "").replace(" VNĐ", "");
                String transferPaymentsText = lblTransferPayments.getText().replace("Tổng kết tiền chuyển khoản (3): ", "").replace(" VNĐ", "");
                String initialAmountText = lblInitialAmount.getText().replace("Tiền mặt đầu ca (1): ", "").replace(" VNĐ", "");
                String totalCashHandoverText = lblTotalCashHandover.getText().replace("Tổng tiền cuối ca (4): ", "").replace(" VNĐ", "");

                String expectedCashText = lblActualCashHandover.getText().replace("Tiền trong két cuối ca (5) = (1)+(2)-(3): ", "").replace(" VNĐ", "");
                String discrepancyText = lblDiscrepancy.getText().replace("Chênh lệch (5)-(4): ", "").replace(" VNĐ", "");
                
                // Format times
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String startTime = activeShift.getGioBatDauThucTe().format(formatter);
                String endTime = LocalDateTime.now().format(formatter);
                
                // Create workbook and sheet
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Báo Cáo Kết Ca");
                
                // Create cell styles
                XSSFCellStyle headerStyle = workbook.createCellStyle();
                XSSFFont headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 12);
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                XSSFFont titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 14);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                
                XSSFCellStyle subtitleStyle = workbook.createCellStyle();
                XSSFFont subtitleFont = workbook.createFont();
                subtitleFont.setBold(true);
                subtitleFont.setFontHeightInPoints((short) 11);
                subtitleStyle.setFont(subtitleFont);
                
                XSSFCellStyle normalStyle = workbook.createCellStyle();
                normalStyle.setAlignment(HorizontalAlignment.LEFT);
                
                // Set column widths
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 6000);
                sheet.setColumnWidth(2, 6000);
                
                int rowIndex = 0;
                
                // Title
                Row titleRow = sheet.createRow(rowIndex++);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("BÁO CÁO KẾT CA");
                titleCell.setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
                
                rowIndex++; // Empty row for spacing
                
                // Shift information
                Row shiftInfoTitle = sheet.createRow(rowIndex++);
                Cell shiftInfoCell = shiftInfoTitle.createCell(0);
                shiftInfoCell.setCellValue("THÔNG TIN CA LÀM VIỆC");
                shiftInfoCell.setCellStyle(subtitleStyle);
                
                Row shiftCodeRow = sheet.createRow(rowIndex++);
                shiftCodeRow.createCell(0).setCellValue("Mã ca làm việc:");
                shiftCodeRow.createCell(1).setCellValue(shiftCode);
                
                Row shiftNameRow = sheet.createRow(rowIndex++);
                shiftNameRow.createCell(0).setCellValue("Tên ca làm việc:");
                shiftNameRow.createCell(1).setCellValue(caLamViec.getTenCaLV());
                
                Row employeeRow = sheet.createRow(rowIndex++);
                employeeRow.createCell(0).setCellValue("Nhân viên:");
                employeeRow.createCell(1).setCellValue(employeeName + " (" + employeeId + ")");
                
                Row startTimeRow = sheet.createRow(rowIndex++);
                startTimeRow.createCell(0).setCellValue("Thời gian bắt đầu:");
                startTimeRow.createCell(1).setCellValue(startTime);
                
                Row endTimeRow = sheet.createRow(rowIndex++);
                endTimeRow.createCell(0).setCellValue("Thời gian kết thúc:");
                endTimeRow.createCell(1).setCellValue(endTime);
                
                rowIndex++; // Empty row
                
                // Invoice information
                Row invoiceTitle = sheet.createRow(rowIndex++);
                Cell invoiceInfoCell = invoiceTitle.createCell(0);
                invoiceInfoCell.setCellValue("THÔNG TIN HÓA ĐƠN");
                invoiceInfoCell.setCellStyle(subtitleStyle);
                
                Row totalInvRow = sheet.createRow(rowIndex++);
                totalInvRow.createCell(0).setCellValue("Tổng số hóa đơn:");
                totalInvRow.createCell(1).setCellValue(Integer.parseInt(totalInvoicesText));
                
                Row paidInvRow = sheet.createRow(rowIndex++);
                paidInvRow.createCell(0).setCellValue("Đã thanh toán:");
                paidInvRow.createCell(1).setCellValue(Integer.parseInt(paidInvoicesText));
                
                Row unpaidInvRow = sheet.createRow(rowIndex++);
                unpaidInvRow.createCell(0).setCellValue("Chưa thanh toán:");
                unpaidInvRow.createCell(1).setCellValue(Integer.parseInt(totalInvoicesText) - Integer.parseInt(paidInvoicesText));
                
                rowIndex++; // Empty row
                
                // Financial summary
                Row financeTitle = sheet.createRow(rowIndex++);
                Cell financeCell = financeTitle.createCell(0);
                financeCell.setCellValue("TỔNG KẾT TÀI CHÍNH");
                financeCell.setCellStyle(subtitleStyle);
                
                Row initialAmountRow = sheet.createRow(rowIndex++);
                initialAmountRow.createCell(0).setCellValue("Tiền mặt đầu ca:");
                initialAmountRow.createCell(1).setCellValue(parseCurrency(initialAmountText));
                
                Row revenueRow = sheet.createRow(rowIndex++);
                revenueRow.createCell(0).setCellValue("Tổng doanh thu:");
                revenueRow.createCell(1).setCellValue(parseCurrency(totalRevenueText));
                
                Row transferRow = sheet.createRow(rowIndex++);
                transferRow.createCell(0).setCellValue("Tiền chuyển khoản:");
                transferRow.createCell(1).setCellValue(parseCurrency(transferPaymentsText));
                
                Row expectedCashRow = sheet.createRow(rowIndex++);
                expectedCashRow.createCell(0).setCellValue("Tiền mặt cuối ca (hệ thống):");
                expectedCashRow.createCell(1).setCellValue(parseCurrency(expectedCashText));
                
                Row actualCashRow = sheet.createRow(rowIndex++);
                actualCashRow.createCell(0).setCellValue("Tổng tiền cuối ca:");
                actualCashRow.createCell(1).setCellValue(parseCurrency(totalCashHandoverText));

                
                Row discrepancyRow = sheet.createRow(rowIndex++);
                discrepancyRow.createCell(0).setCellValue("Chênh lệch:");
                discrepancyRow.createCell(1).setCellValue(parseCurrency(discrepancyText));
                
                rowIndex++; // Empty row
                
                // Denomination details
                Row denomTitle = sheet.createRow(rowIndex++);
                Cell denomCell = denomTitle.createCell(0);
                denomCell.setCellValue("CHI TIẾT MỆNH GIÁ");
                denomCell.setCellStyle(subtitleStyle);
                
                // Create denomination header
                Row denomHeaderRow = sheet.createRow(rowIndex++);
                denomHeaderRow.createCell(0).setCellValue("Mệnh giá");
                denomHeaderRow.createCell(1).setCellValue("Số lượng");
                denomHeaderRow.createCell(2).setCellValue("Thành tiền");
                
                for (int i = 0; i < 3; i++) {
                    denomHeaderRow.getCell(i).setCellStyle(headerStyle);
                }
                
                // Add denominations data
                for (int i = 0; i < denominations.length; i++) {
                    Row denomRow = sheet.createRow(rowIndex++);
                    int count = (int) denominationSpinners[i].getValue();
                    long value = count * denominations[i];
                    
                    denomRow.createCell(0).setCellValue(denominations[i]);
                    denomRow.createCell(1).setCellValue(count);
                    denomRow.createCell(2).setCellValue(value);
                }
                
                rowIndex += 2; // Empty rows for spacing
                
                // Notes
                Row notesTitle = sheet.createRow(rowIndex++);
                Cell notesCell = notesTitle.createCell(0);
                notesCell.setCellValue("GHI CHÚ");
                notesCell.setCellStyle(subtitleStyle);
                
                Row notesRow = sheet.createRow(rowIndex++);
                Cell notesContentCell = notesRow.createCell(0);
                notesContentCell.setCellValue(txtNotes.getText());
                sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex+2, 0, 2)); // Merge cells for notes
                
                // Write to file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    workbook.close();
                    JOptionPane.showMessageDialog(this, 
                        "Xuất báo cáo thành công!\nĐường dẫn: " + filePath,
                        "Thông báo", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất báo cáo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper methods
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR), 
                        title,
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 13),
                        PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
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
        if (button.getText().length() > 10) {
            buttonSize = new Dimension(180, 32);
        }
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

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VNĐ";
    }

    private long parseCurrency(String text) {
        try {
            // Remove all non-numeric characters except for digits
            String numericString = text.replaceAll("[^0-9]", "");
            return Long.parseLong(numericString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}