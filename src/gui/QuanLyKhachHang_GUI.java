package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.NhanVien;

public class QuanLyKhachHang_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
//    private MenuChinh_GUI menuBar;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton exportButton;
    private NhanVien currentUser;
    private JLabel totalCustomersLabel;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(30, 129, 191);
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(46, 175, 80);
    private final Color TEXT_COLOR = new Color(33, 37, 41);
    private final Color BACKGROUND_COLOR = Color.WHITE;
    
    // DAO
    private KhachHang_DAO khachHangDAO;
    
    public QuanLyKhachHang_GUI() {
        this(null);
    }
    
    public QuanLyKhachHang_GUI(NhanVien loggedInUser) {
        setTitle("Quản lý khách hàng");
        this.currentUser = loggedInUser;
        this.khachHangDAO = new KhachHang_DAO();
        
//        setLayout(new BorderLayout());
//        
//        menuBar = new MenuChinh_GUI();
////        menuBar.addActionListeners(this);
//        add(menuBar.initUI(), BorderLayout.NORTH);
//        
//        initUI();
//        loadKhachHang();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setVisible(true);
    }

    public JPanel initUI() {
        // Main container with proper padding
    	JPanel pnlQLKH = new JPanel(new BorderLayout());
    	
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Create header with better styling
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center content panel that will hold both control panel and table
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Create control panel with search and action buttons
        JPanel controlPanel = createControlPanel();
        centerPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Create table panel with improved sizing
        JPanel tableContainer = createTablePanel();
        centerPanel.add(tableContainer, BorderLayout.CENTER);
        
        // Create status bar with total count
        JPanel statusPanel = createStatusPanel();
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        loadKhachHang();
        pnlQLKH.add(contentPanel);
        return pnlQLKH;
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Add a subtle separator line
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(new Color(220, 220, 220));
        headerPanel.add(separator, BorderLayout.SOUTH);
        
        return headerPanel;
    }

    private JPanel createControlPanel() {
        // Main control panel with card-like appearance
        JPanel controlPanel = new JPanel(new BorderLayout(15, 0));
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Search section
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(BACKGROUND_COLOR);
        
        // Improved search field
        searchField = new JTextField(25);
        searchField.setText("Tìm kiếm theo tên, số điện thoại, email...");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 35));
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm theo tên, số điện thoại, email...")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Tìm kiếm theo tên, số điện thoại, email...");
                }
            }
        });
        
        searchButton = createStyledButton("Tìm kiếm", new Color(0, 123, 255));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);
        
        // Action buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);
        
        addButton = createStyledButton("Thêm khách hàng", ACCENT_COLOR);
        editButton = createStyledButton("Sửa", new Color(255, 193, 7));
        deleteButton = createStyledButton("Xóa", new Color(220, 53, 69));
        refreshButton = createStyledButton("Làm mới", new Color(108, 117, 125));
        exportButton = createStyledButton("Xuất Excel", new Color(25, 135, 84)); 
        
        buttonsPanel.add(exportButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
//        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);
        
        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonsPanel, BorderLayout.EAST);
        
        return controlPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        
        String[] columnNames = {"Mã KH", "Họ tên", "Số điện thoại", "Email", "Ghi chú", "Điểm tích lũy"};
        int[] columnWidths = {80, 200, 150, 200, 200, 100};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(BACKGROUND_COLOR);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(TEXT_COLOR);
        table.setIntercellSpacing(new Dimension(5, 5));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setAutoCreateRowSorter(true);
        
        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        // Style the header
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout(15, 0));
        statusPanel.setBackground(BACKGROUND_COLOR);
        statusPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        // Total records label
        totalCustomersLabel = new JLabel("Tổng số: 0 khách hàng");
        totalCustomersLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        statusPanel.add(totalCustomersLabel, BorderLayout.WEST);
        
        return statusPanel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(darken(bgColor, 0.1f));
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
        
        button.addActionListener(this);
        return button;
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

    public void loadKhachHang() {
        tableModel.setRowCount(0);
        
        try {
            // Initialize database connection
            ConnectDB.getInstance().connect();
            
            // Fetch customer data
            ArrayList<KhachHang> dsKhachHang = khachHangDAO.getAllKhachHang();
            // Add customer data to table
            for (KhachHang kh : dsKhachHang) {
                Object[] rowData = {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getSdtKH(),
                    kh.getEmailKH(),
                    kh.getGhiChuKH(),
                    kh.getDiemTL()
                };
                
                tableModel.addRow(rowData);
            }
            
            // Update the total customers count
            totalCustomersLabel.setText("Tổng số: " + tableModel.getRowCount() + " khách hàng");
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchKhachHang() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("tìm kiếm theo tên, số điện thoại, email...")) {
            return; // Don't search with placeholder text
        }
        
        tableModel.setRowCount(0);
        try {
            ConnectDB.getInstance().connect();
            ArrayList<KhachHang> dsKhachHang = khachHangDAO.getAllKhachHang();
            
            int filteredCount = 0;
            String search = searchText.toLowerCase();

            for (KhachHang kh : dsKhachHang) {
                // Lấy các trường và kiểm tra null an toàn
                String maKH   = kh.getMaKH() != null   ? kh.getMaKH().toLowerCase()   : "";
                String tenKH  = kh.getTenKH() != null  ? kh.getTenKH().toLowerCase()  : "";
                String sdtKH  = kh.getSdtKH() != null  ? kh.getSdtKH().toLowerCase()  : "";
                String email  = kh.getEmailKH() != null ? kh.getEmailKH().toLowerCase() : "";

                if (maKH.contains(search) || tenKH.contains(search) || sdtKH.contains(search) || email.contains(search)) {
                    filteredCount++;

                    Object[] rowData = {
                        kh.getMaKH(),
                        kh.getTenKH(),
                        kh.getSdtKH(),
                        kh.getEmailKH(),
                        kh.getGhiChuKH(),
                        kh.getDiemTL()
                    };

                    tableModel.addRow(rowData);
                }
            }

            totalCustomersLabel.setText("Tìm thấy: " + filteredCount + " khách hàng");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Implementation for editing a customer
    private void editKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        
        String maKH = table.getValueAt(selectedRow, 0).toString();
        
        try {
            // Get customer details
            KhachHang kh = khachHangDAO.getKhachHangById(maKH);
            
            if (kh == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng!");
                return;
            }
            
            // Create edit dialog
            JDialog editDialog = new JDialog(this, "Sửa thông tin khách hàng", true);
            editDialog.setLayout(new BorderLayout());
            editDialog.setSize(600, 400);
            editDialog.setLocationRelativeTo(this);
            
            JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
            formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            // Form fields with current values
            JTextField txtMaKH = new JTextField(kh.getMaKH());
            txtMaKH.setEditable(false); // Cannot edit ID
            JTextField txtTenKH = new JTextField(kh.getTenKH());
            JTextField txtSDT = new JTextField(kh.getSdtKH());
            JTextField txtEmail = new JTextField(kh.getEmailKH());
            JTextField txtGhiChu = new JTextField(kh.getGhiChuKH());
            JSpinner spnDiemTL = new JSpinner(new SpinnerNumberModel(kh.getDiemTL(), 0, 10000, 1));
            
            formPanel.add(new JLabel("Mã khách hàng:"));
            formPanel.add(txtMaKH);
            formPanel.add(new JLabel("Họ tên:"));
            formPanel.add(txtTenKH);
            formPanel.add(new JLabel("Số điện thoại:"));
            formPanel.add(txtSDT);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(txtEmail);
            formPanel.add(new JLabel("Ghi chú:"));
            formPanel.add(txtGhiChu);
            formPanel.add(new JLabel("Điểm tích lũy:"));
            formPanel.add(spnDiemTL);
            
            // Buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
            JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
            
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            
            editDialog.add(formPanel, BorderLayout.CENTER);
            editDialog.add(buttonPanel, BorderLayout.SOUTH);
            
            saveButton.addActionListener(e -> {
                try {
                    // Validate input
                    if (txtTenKH.getText().isEmpty() || txtSDT.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin bắt buộc (Họ tên, Số điện thoại)!");
                        return;
                    }
                    
                    // Validate phone format
                    if (!txtSDT.getText().matches("\\d{10}")) {
                        JOptionPane.showMessageDialog(editDialog, "Số điện thoại phải có 10 chữ số!");
                        return;
                    }
                    
                    // Update customer
                    kh.setTenKH(txtTenKH.getText());
                    kh.setSdtKH(txtSDT.getText());
                    kh.setEmailKH(txtEmail.getText());
                    kh.setGhiChuKH(txtGhiChu.getText());
                    kh.setDiemTL((int) spnDiemTL.getValue());
                    
                    // Save to database
                    boolean result = khachHangDAO.updateKhachHang(kh);
                    
                    if (result) {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thông tin khách hàng thành công!");
                        loadKhachHang(); // Refresh the table
                        editDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thông tin khách hàng thất bại!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editDialog, "Lỗi: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            
            cancelButton.addActionListener(e -> editDialog.dispose());
            
            editDialog.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Implementation for deleting a customer
    private void deleteKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        
        String maKH = table.getValueAt(selectedRow, 0).toString();
        String tenKH = table.getValueAt(selectedRow, 1).toString();
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa khách hàng " + tenKH + " (" + maKH + ")?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                boolean result = khachHangDAO.deleteKhachHang(maKH);
                
                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                    loadKhachHang(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại! Khách hàng có thể đang được sử dụng trong các đơn đặt bàn.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa khách hàng: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void exportToExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                // Create workbook and sheet
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Danh sách khách hàng");
                
                // Create header row with styling
                XSSFRow headerRow = sheet.createRow(0);
                XSSFCellStyle headerStyle = workbook.createCellStyle();
                
                // Use POI's Font
                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                
                // Set headers
                String[] columns = {"Mã KH", "Họ tên", "Số điện thoại", "Email", "Ghi chú", "Điểm tích lũy"};
                
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                    sheet.setColumnWidth(i, 4000); // Set column width
                }
                
                // Add data rows
                int rowNum = 1;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Row row = sheet.createRow(rowNum++);
                    
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        Object value = tableModel.getValueAt(i, j);
                        if (value != null) {
                            row.createCell(j).setCellValue(value.toString());
                        }
                    }
                }
                
                // Write to file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    workbook.close();
                    JOptionPane.showMessageDialog(this, 
                        "Xuất file thành công!\nĐường dẫn: " + filePath,
                        "Thông báo", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất file: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == addButton) {
        	new Dialog_ThemKhachHang(this).setVisible(true);
        } else if (source == editButton) {
            editKhachHang();
        } else if (source == deleteButton) {
            deleteKhachHang();
        } else if (source == refreshButton) {
            loadKhachHang();
            searchField.setText("Tìm kiếm theo tên, số điện thoại, email...");
            searchField.setForeground(Color.GRAY);
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.");
        } else if (source == searchButton) {
            searchKhachHang();
        } else if (source == exportButton) {
            exportToExcel();
            }
//        } else if (menuBar != null) {
//            // Forward to menu bar handler
//            if (menuBar != null) {
//                if (source == menuBar.getMniDBTT1()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Thêm đơn đặt bàn trực tiếp sẽ được mở");
//                } else if (source == menuBar.getMniDBTT2()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Cập nhật đơn đặt bàn trực tiếp sẽ được mở");
//                } else if (source == menuBar.getMniDBT1()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Thêm đơn đặt bàn trước sẽ được mở");
//                } else if (source == menuBar.getMniDBT2()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Cập nhật đơn đặt bàn trước sẽ được mở");
//                } else if (source == menuBar.getMniMoCa()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Mở ca sẽ được mở");
//                } else if (source == menuBar.getMniKetCa()) {
//                    JOptionPane.showMessageDialog(this, "Chức năng Kết ca sẽ được mở");
//                } else if (source == menuBar.getMniQLKH()) {
//                    // We're already in customer management, do nothing
//                } else if (source == menuBar.getMniQLNV()) {
//                    dispose();
//                    new QuanLyNhanVien_GUI(currentUser);
//                }
//            }
//        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();
                new QuanLyKhachHang_GUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
