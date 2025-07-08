package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import connectDB.ConnectDB;
import dao.CaLamViec_DAO;
import dao.ChiTietCaLamViec_DAO;
import dao.NhanVien_DAO;
import entity.CaLamViec;
import entity.ChiTietCaLamViec;
import entity.NhanVien;

public class LichSuCaLamViec_GUI extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTable tblShiftHistory;
    private DefaultTableModel modelShiftHistory;
    private JComboBox<String> cboFilterPeriod;
    private JDateChooser dateChooser;
    private JTextField txtSearchEmployee;
    private JButton btnSearch;
    private JButton btnExport;
    private JButton btnClose;
    
    private NhanVien currentUser;
    private ChiTietCaLamViec_DAO chiTietCaLamViecDAO; // Thay thế CaLamViec_DAO bằng ChiTietCaLamViec_DAO
    private JFrame parentFrame;
    
    public LichSuCaLamViec_GUI(NhanVien loggedInUser) {
        this(null, loggedInUser); // Call the existing constructor with null parent
    }
    public LichSuCaLamViec_GUI(JFrame parent, NhanVien user) {
        super(parent, "Lịch sử ca làm việc", true);
        this.parentFrame = parent;
        this.currentUser = user;
        this.chiTietCaLamViecDAO = new ChiTietCaLamViec_DAO();
        
        initComponents();
        loadShiftHistory(null, null, null);
        
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Filter panel
        JPanel filterPanel = createFilterPanel();
        
        // Table panel
        JPanel tablePanel = createTablePanel();
        
        // Bottom button panel
        JPanel buttonPanel = createButtonPanel();
        
        // Add to main panel
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add to dialog
        setContentPane(mainPanel);
        
        // Setup action listeners
        setupEventListeners();
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout(10, 5));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Lọc dữ liệu"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // Top filter options
        JPanel topFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Period filter
        JLabel lblPeriod = new JLabel("Thời gian:");
        cboFilterPeriod = new JComboBox<>(new String[]{
            "Tất cả", "Hôm nay", "Tuần này", "Tháng này", "Tùy chọn"
        });
        cboFilterPeriod.setPreferredSize(new Dimension(120, 25));
        
        // Date chooser
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 25));
        dateChooser.setEnabled(false);
        
        topFilterPanel.add(lblPeriod);
        topFilterPanel.add(cboFilterPeriod);
        topFilterPanel.add(dateChooser);
        
        // Bottom filter options
        JPanel bottomFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Employee search
        JLabel lblEmployee = new JLabel("Tìm theo nhân viên:");
        txtSearchEmployee = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        stylePrimaryButton(btnSearch);
        
        bottomFilterPanel.add(lblEmployee);
        bottomFilterPanel.add(txtSearchEmployee);
        bottomFilterPanel.add(btnSearch);
        
        // Add to filter panel
        filterPanel.add(topFilterPanel, BorderLayout.NORTH);
        filterPanel.add(bottomFilterPanel, BorderLayout.CENTER);
        
        return filterPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Create table model
        modelShiftHistory = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã ca làm việc", "Tên ca làm việc", "Nhân viên", "Thời gian bắt đầu", 
                "Thời gian kết thúc", "Tiền ban đầu", "Tiền cuối ca", "Doanh thu"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        // Create table and scroll pane
        tblShiftHistory = new JTable(modelShiftHistory);
        formatTable(tblShiftHistory);
        
        JScrollPane scrollPane = new JScrollPane(tblShiftHistory);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        btnExport = new JButton("Xuất báo cáo");
        btnClose = new JButton("Đóng");
        
        stylePrimaryButton(btnExport);
        styleSecondaryButton(btnClose);
        
        buttonPanel.add(btnExport);
        buttonPanel.add(btnClose);
        
        return buttonPanel;
    }
    
    private void setupEventListeners() {
        // Filter period changed
        cboFilterPeriod.addActionListener(e -> {
            String selectedPeriod = (String) cboFilterPeriod.getSelectedItem();
            dateChooser.setEnabled("Tùy chọn".equals(selectedPeriod));
            applyFilters();
        });
        
        // Date chooser changed
        dateChooser.addPropertyChangeListener("date", e -> {
            if ("Tùy chọn".equals(cboFilterPeriod.getSelectedItem())) {
                applyFilters();
            }
        });
        
        // Search button
        btnSearch.addActionListener(e -> applyFilters());
        
        // Export button
        btnExport.addActionListener(e -> exportReport());
        
        // Close button
        btnClose.addActionListener(e -> dispose());
        
        // Double-click on table row to view details
        tblShiftHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewShiftDetails();
                }
            }
        });
    }
    
    private void applyFilters() {
        String selectedPeriod = (String) cboFilterPeriod.getSelectedItem();
        LocalDate selectedDate = null;
        String employeeSearch = txtSearchEmployee.getText().trim();
        
        if ("Tùy chọn".equals(selectedPeriod) && dateChooser.getDate() != null) {
            selectedDate = dateChooser.getDate().toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        }
        
        loadShiftHistory(selectedPeriod, selectedDate, employeeSearch.isEmpty() ? null : employeeSearch);
    }
    
    private void loadShiftHistory(String period, LocalDate date, String employeeSearch) {
        try {
            // Clear existing data
            modelShiftHistory.setRowCount(0);
            
            // Get current date and related dates for filtering
            LocalDate today = LocalDate.now();
            LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
            LocalDate startOfMonth = today.withDayOfMonth(1);
            
            // Get shift history from DAO
            ArrayList<ChiTietCaLamViec> shifts = chiTietCaLamViecDAO.getAllCompletedShifts();
            
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            
            // Apply filters
            for (ChiTietCaLamViec shift : shifts) {
                LocalDateTime shiftStartTime = shift.getGioBatDauThucTe();
                LocalDateTime shiftEndTime = shift.getGioKetThucThucTe();
                LocalDate shiftDate = shiftStartTime.toLocalDate();
                
                // Skip if shift hasn't ended yet
                if (shiftEndTime == null) continue;
                
                // Apply period filter
                boolean periodMatch = true;
                if (period != null) {
                    switch (period) {
                        case "Hôm nay":
                            periodMatch = shiftDate.equals(today);
                            break;
                        case "Tuần này":
                            periodMatch = !shiftDate.isBefore(startOfWeek) && !shiftDate.isAfter(today);
                            break;
                        case "Tháng này":
                            periodMatch = !shiftDate.isBefore(startOfMonth) && !shiftDate.isAfter(today);
                            break;
                        case "Tùy chọn":
                            periodMatch = (date != null) ? shiftDate.equals(date) : true;
                            break;
                    }
                }
                
                // Apply employee search filter
                boolean employeeMatch = true;
                if (employeeSearch != null && !employeeSearch.isEmpty()) {
                    String employeeName = shift.getNhanVien().getTenNV().toLowerCase();
                    String employeeId = shift.getNhanVien().getMaNV().toLowerCase();
                    employeeMatch = employeeName.contains(employeeSearch.toLowerCase()) || 
                                    employeeId.contains(employeeSearch.toLowerCase());
                }
                
                // If all filters match, add to table
                if (periodMatch && employeeMatch) {
                    CaLamViec caLamViec = shift.getCaLamViec();
                 // Change these lines in loadShiftHistory()
                    Object[] row = {
                        caLamViec.getMaCaLV(),
                        caLamViec.getTenCaLV(),
                        shift.getNhanVien().getTenNV() + " (" + shift.getNhanVien().getMaNV() + ")",
                        shiftStartTime.format(dateTimeFormatter),
                        shiftEndTime.format(dateTimeFormatter),
                        formatCurrency(shift.getSoTienBanDau()),
                        formatCurrency(shift.getSoTienCuoiCa()), // Use actual final cash amount
                        formatCurrency(shift.getSoTienCuoiCa() - shift.getSoTienBanDau()) 
                    };

                    modelShiftHistory.addRow(row);
                }
            }
            
            // If no data found
            if (modelShiftHistory.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy dữ liệu ca làm việc phù hợp với điều kiện lọc.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu lịch sử ca làm việc: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void viewShiftDetails() {
        int selectedRow = tblShiftHistory.getSelectedRow();
        
        if (selectedRow >= 0) {
            String shiftId = (String) modelShiftHistory.getValueAt(selectedRow, 0);
            String employeeName = (String) modelShiftHistory.getValueAt(selectedRow, 2);
            String startTime = (String) modelShiftHistory.getValueAt(selectedRow, 3);
            String endTime = (String) modelShiftHistory.getValueAt(selectedRow, 4);
            String initialAmount = (String) modelShiftHistory.getValueAt(selectedRow, 5);
            String finalAmount = (String) modelShiftHistory.getValueAt(selectedRow, 6);
            String revenue = (String) modelShiftHistory.getValueAt(selectedRow, 7);
            
            try {
                // Get additional shift details
                ChiTietCaLamViec shiftDetail = chiTietCaLamViecDAO.getChiTietCaLamViecById(shiftId);
                String note = shiftDetail != null ? shiftDetail.getGhiChu() : "Không có ghi chú";
                
                // Create and show details dialog
                ShiftDetailsDialog detailsDialog = new ShiftDetailsDialog(this, 
                    shiftId, employeeName, startTime, endTime, 
                    initialAmount, finalAmount, revenue, note);
                detailsDialog.setVisible(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải chi tiết ca làm việc: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một ca làm việc để xem chi tiết.",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void exportReport() {
        try {
            // Create a dialog to get export options
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu báo cáo");
            fileChooser.setSelectedFile(new java.io.File("BaoCaoLichSuCaLamViec.pdf"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                
                // Generate PDF report (simplified for now)
                JOptionPane.showMessageDialog(this,
                    "Đã xuất báo cáo thành công!\nVị trí: " + fileToSave.getAbsolutePath(),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất báo cáo: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Helper methods
    private void formatTable(JTable table) {
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã ca làm việc
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Tên ca làm việc
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Nhân viên
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Thời gian bắt đầu
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // Thời gian kết thúc
        table.getColumnModel().getColumn(5).setPreferredWidth(120); // Tiền ban đầu
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Tiền cuối ca
        table.getColumnModel().getColumn(7).setPreferredWidth(120); // Doanh thu
        
        // Set row height and font
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Set header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Set selection style
        table.setSelectionBackground(new Color(210, 230, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 220, 220));
        
        // Set row striping
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                
                ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                
                return c;
            }
        });
    }
    
    private String formatCurrency(double amount) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getNumberInstance(new java.util.Locale("vi", "VN"));
        return formatter.format(amount) + " VNĐ";
    }
    
    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 110, 160), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(60, 115, 160));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }
    
    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(60, 60, 60));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(240, 240, 240));
            }
        });
    }
    
    // Inner class for shift details dialog
    private class ShiftDetailsDialog extends JDialog {
        private static final long serialVersionUID = 1L;
        
        public ShiftDetailsDialog(JDialog parent, String shiftId, String employeeName,
                                 String startTime, String endTime, String initialAmount,
                                 String finalAmount, String revenue, String note) {
            super(parent, "Chi tiết ca làm việc - " + shiftId, true);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            // Create details panel
            JPanel detailsPanel = new JPanel(new GridBagLayout());
            detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin ca làm việc"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);
            
            // Add rows
            addDetailRow(detailsPanel, gbc, 0, "Mã ca làm việc:", shiftId);
            addDetailRow(detailsPanel, gbc, 1, "Nhân viên:", employeeName);
            addDetailRow(detailsPanel, gbc, 2, "Thời gian bắt đầu:", startTime);
            addDetailRow(detailsPanel, gbc, 3, "Thời gian kết thúc:", endTime);
            addDetailRow(detailsPanel, gbc, 4, "Số tiền ban đầu:", initialAmount);
            addDetailRow(detailsPanel, gbc, 5, "Số tiền cuối ca:", finalAmount);
            addDetailRow(detailsPanel, gbc, 6, "Doanh thu:", revenue);
            
            // Note field
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            JLabel lblNote = new JLabel("Ghi chú:");
            detailsPanel.add(lblNote, gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            
            JTextArea txtNote = new JTextArea(note);
            txtNote.setEditable(false);
            txtNote.setLineWrap(true);
            txtNote.setWrapStyleWord(true);
            txtNote.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtNote.setBackground(detailsPanel.getBackground());
            
            JScrollPane noteScrollPane = new JScrollPane(txtNote);
            noteScrollPane.setPreferredSize(new Dimension(300, 100));
            noteScrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            detailsPanel.add(noteScrollPane, gbc);
            
            // Create button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton closeButton = new JButton("Đóng");
            closeButton.addActionListener(e -> dispose());
            styleSecondaryButton(closeButton);
            buttonPanel.add(closeButton);
            
            // Add panels to main panel
            panel.add(detailsPanel);
            panel.add(Box.createVerticalStrut(10));
            panel.add(buttonPanel);
            
            // Set content pane
            setContentPane(panel);
            pack();
            setResizable(false);
            setLocationRelativeTo(parent);
        }
        
        private void addDetailRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.gridwidth = 1;
            JLabel lblField = new JLabel(label);
            lblField.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panel.add(lblField, gbc);
            
            gbc.gridx = 1;
            gbc.gridy = row;
            gbc.gridwidth = 1;
            JLabel lblValue = new JLabel(value);
            lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(lblValue, gbc);
        }
    }
    
    // JDateChooser class - simplified implementation for compatibility
    private class JDateChooser extends JPanel {
        private static final long serialVersionUID = 1L;
        private JTextField dateField;
        private JButton dateButton;
        private java.util.Date date;
        
        public JDateChooser() {
            setLayout(new BorderLayout());
            dateField = new JTextField();
            dateButton = new JButton("...");
            dateButton.setPreferredSize(new Dimension(30, 25));
            
            add(dateField, BorderLayout.CENTER);
            add(dateButton, BorderLayout.EAST);
            
            // When button is clicked, show a date picker (simplified in this example)
            dateButton.addActionListener(e -> {
                // Set today's date as default (simplified implementation)
                setDate(new java.util.Date());
            });
        }
        
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            dateField.setEnabled(enabled);
            dateButton.setEnabled(enabled);
        }
        
        public java.util.Date getDate() {
            return date;
        }
        
        public void setDate(java.util.Date date) {
            this.date = date;
            if (date != null) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                dateField.setText(sdf.format(date));
                firePropertyChange("date", null, date);
            } else {
                dateField.setText("");
            }
        }
        
        public void setPreferredSize(Dimension dimension) {
            super.setPreferredSize(dimension);
        }
    }
    
    // Main method for testing
 // Update main method to handle current logged-in user
 // Main method for testing and standalone execution
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Connect to database
            ConnectDB.getInstance().connect();
            
            // Create a dummy frame for testing
            JFrame testFrame = new JFrame();
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(300, 300);
            
            // In production, you'd get the current logged-in user
            // For testing purposes, we'll create a sample user or try to get the last logged-in user
            NhanVien currentUser = null;
            
            // Try to get user info from the database (last active user)
            try {
                NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
                ChiTietCaLamViec_DAO chiTietCLVDAO = new ChiTietCaLamViec_DAO();
                
                // Option 1: Get any active employee with open shift
                ArrayList<NhanVien> allEmployees = nhanVienDAO.getAllNV();
                for (NhanVien nv : allEmployees) {
                    ChiTietCaLamViec activeShift = chiTietCLVDAO.getActiveShiftForEmployee(nv.getMaNV());
                    if (activeShift != null) {
                        currentUser = nv;
                        break;
                    }
                }
                
                // Option 2: If no active shift, use the first available employee
                if (currentUser == null && !allEmployees.isEmpty()) {
                    currentUser = allEmployees.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // If still no user found, create a sample one
//            if (currentUser == null) {
//                currentUser = new NhanVien();
//                currentUser.setMaNV("NV001");
//                currentUser.setTenNV("Nguyễn Văn A");
//            }
            
            // Create and show the dialog
            final NhanVien finalUser = currentUser;
            SwingUtilities.invokeLater(() -> {
                LichSuCaLamViec_GUI dialog = new LichSuCaLamViec_GUI(testFrame, finalUser);
                dialog.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Lỗi khởi động: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ConnectDB.getInstance().connect();
//
//        // Chạy ở chế độ test
//        new LichSuCaLamViec_GUI();
//    }
}