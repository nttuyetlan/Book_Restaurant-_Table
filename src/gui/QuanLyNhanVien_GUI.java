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
import javax.swing.border.TitledBorder;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import entity.NhanVien;
import entity.TaiKhoan;
import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;

public class QuanLyNhanVien_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
//    private MenuBar_GUI menuBar;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private NhanVien currentUser;
    private JLabel totalEmployeesLabel;
    private JTabbedPane roleTabbedPane;
    private String currentRole = "Tất cả"; // Default filter to show all employees
    private JButton exportButton;
    // Updated colors to match ThongKeCaLamViecDau_GUI
    private final Color PRIMARY_COLOR = new Color(30, 129, 191);
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(46, 175, 80);
    private final Color TEXT_COLOR = new Color(33, 37, 41);
    private final Color BACKGROUND_COLOR = Color.WHITE;
    
    public QuanLyNhanVien_GUI() {
        this(null);
    }
    
    public QuanLyNhanVien_GUI(NhanVien loggedInUser) {
        setTitle("Quản lý nhân viên");
        this.currentUser = loggedInUser;
        
//        setLayout(new BorderLayout());
//        
//        menuBar = new MenuBar_GUI(currentUser);
//        menuBar.addActionListeners(this);
//        add(menuBar.getMenuBar(), BorderLayout.NORTH);
//        
//        initUI();
//        loadEmployees();
//        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setVisible(true);
    }

    public JPanel initUI() {
        // Main container with proper padding
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
        
        // Create status bar with pagination
        JPanel statusPanel = createStatusPanel();
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
//        add(contentPanel, BorderLayout.CENTER);
        loadEmployees();
        
        return contentPanel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
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
        searchField.setText("Tìm kiếm theo tên, số điện thoại, CCCD...");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 35));
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm theo tên, số điện thoại, CCCD...")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Tìm kiếm theo tên, số điện thoại, CCCD...");
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
        
        addButton = createStyledButton("Thêm nhân viên", ACCENT_COLOR);
        editButton = createStyledButton("Sửa", new Color(255, 193, 7));
        deleteButton = createStyledButton("Xóa", new Color(220, 53, 69));
        refreshButton = createStyledButton("Làm mới", new Color(108, 117, 125));
        exportButton = createStyledButton("Xuất Excel", new Color(25, 135, 84)); 
        
        buttonsPanel.add(exportButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);
        
        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonsPanel, BorderLayout.EAST);
        
        return controlPanel;
    }

    public void loadEmployees() {
        tableModel.setRowCount(0);
        
        try {
            // Initialize database connection
            ConnectDB.getInstance().connect();
            
            // Fetch employee data
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            ArrayList<NhanVien> dsNhanVien = nhanVienDAO.getAllNV();
            
            // Format for date display
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Add employee data to table
            for (NhanVien nv : dsNhanVien) {
                String trangThai = nv.getTrangThaiLV() ? "Đang làm việc" : "Nghỉ việc";
                String formattedDate = "";
                if (nv.getNgayBatDauLV() != null) {
                    formattedDate = nv.getNgayBatDauLV().format(dateFormatter);
                }
                
                Object[] rowData = {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getChucVu(),
                    nv.getSoCCCD(),
                    formattedDate,
                    nv.getSdtNV(),
                    nv.getEmailNV(),
                    trangThai
                };
                
                tableModel.addRow(rowData);
            }
            
            // Update the total employees count
            totalEmployeesLabel.setText("Tổng số: " + tableModel.getRowCount() + " nhân viên");
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu nhân viên: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        
        String[] columnNames = {"Mã NV", "Họ tên",  "Chức vụ", "Số CCCD", "Ngày bắt đầu làm việc", "Số điện thoại","Email", "Trạng thái"};
        int[] columnWidths = {80, 180, 120, 150, 120, 150, 120, 100};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
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
        totalEmployeesLabel = new JLabel("Tổng số: 0 nhân viên");
        totalEmployeesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Pagination panel with better spacing
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        paginationPanel.setBackground(BACKGROUND_COLOR);
        
        JButton prevButton = new JButton("« Trước");
        JButton page1 = new JButton("1");
        JButton page2 = new JButton("2");
        JButton page3 = new JButton("3");
        JButton nextButton = new JButton("Sau »");
        
        stylePageButton(prevButton, false);
        stylePageButton(page1, true);
        stylePageButton(page2, false);
        stylePageButton(page3, false);
        stylePageButton(nextButton, false);
        
        paginationPanel.add(prevButton);
        paginationPanel.add(page1);
        paginationPanel.add(page2);
        paginationPanel.add(page3);
        paginationPanel.add(nextButton);
        
        statusPanel.add(totalEmployeesLabel, BorderLayout.WEST);
        statusPanel.add(paginationPanel, BorderLayout.CENTER);
        
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

    private void stylePageButton(JButton button, boolean isActive) {
        if (isActive) {
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(SECONDARY_COLOR);
            button.setForeground(TEXT_COLOR);
        }
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setOpaque(true);

        Color finalBgColor = isActive ? PRIMARY_COLOR : SECONDARY_COLOR;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darken(finalBgColor, 0.1f));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(finalBgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

 // Add this as a class field for search functionality
    private void searchEmployees() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("tìm kiếm theo tên, số điện thoại, cccd...")) {
            return; // Don't search with placeholder text
        }
        
        tableModel.setRowCount(0);
        try {
            ConnectDB.getInstance().connect();
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            ArrayList<NhanVien> dsNhanVien = nhanVienDAO.getAllNV();
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int filteredCount = 0;
            
            for (NhanVien nv : dsNhanVien) {
                // Skip if role filter doesn't match
                if (!currentRole.equals("Tất cả") && !nv.getChucVu().equals(currentRole)) {
                    continue;
                }
                
                // Search in all relevant fields
                if (nv.getMaNV().toLowerCase().contains(searchText) ||
                    nv.getTenNV().toLowerCase().contains(searchText) ||
                    nv.getSoCCCD().toLowerCase().contains(searchText) ||
                    nv.getSdtNV().toLowerCase().contains(searchText) ||
                    nv.getEmailNV().toLowerCase().contains(searchText)) {
                    
                    filteredCount++;
                    String trangThai = nv.getTrangThaiLV() ? "Đang làm việc" : "Nghỉ việc";
                    String formattedDate = "";
                    if (nv.getNgayBatDauLV() != null) {
                        formattedDate = nv.getNgayBatDauLV().format(dateFormatter);
                    }
                    
                    Object[] rowData = {
                        nv.getMaNV(),
                        nv.getTenNV(),
                        nv.getChucVu(),
                        nv.getSoCCCD(),
                        formattedDate,
                        nv.getSdtNV(),
                        nv.getEmailNV(),
                        trangThai
                    };
                    
                    tableModel.addRow(rowData);
                }
            }
            
            totalEmployeesLabel.setText("Tìm thấy: " + filteredCount + " nhân viên");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Implementation for adding an employee
    private void addEmployee() {
        JDialog addDialog = new JDialog(this, "Thêm nhân viên mới", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(700, 550);
        addDialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(11, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Form fields
        JComboBox<String> cboChucVu = new JComboBox<>(new String[]{"Quản lý", "Nhân Viên Quầy"});
        JTextField txtMaNV = new JTextField(20);
        txtMaNV.setEditable(false);
        
        // Generate initial ID based on default selected role
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        String initialRole = (String) cboChucVu.getSelectedItem();
        txtMaNV.setText(nhanVienDAO.generateNextEmployeeId(initialRole));
        
        // Update ID when role changes
        cboChucVu.addActionListener(e -> {
            String selectedRole = (String) cboChucVu.getSelectedItem();
            txtMaNV.setText(nhanVienDAO.generateNextEmployeeId(selectedRole));
        });
        
        JTextField txtTenNV = new JTextField(20);
        JTextField txtCCCD = new JTextField(20);
        JTextField txtSDT = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtDiaChi = new JTextField(20);
        JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        JCheckBox chkTrangThai = new JCheckBox("Đang làm việc");
        chkTrangThai.setSelected(true);
        
        // Date picker for birth date - using simple spinner components
        JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2000, 1950, LocalDate.now().getYear(), 1));
        
        JPanel birthDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        birthDatePanel.add(daySpinner);
        birthDatePanel.add(new JLabel("/"));
        birthDatePanel.add(monthSpinner);
        birthDatePanel.add(new JLabel("/"));
        birthDatePanel.add(yearSpinner);
        
        // Information label for password
        JLabel lblPasswordInfo = new JLabel("Mật khẩu sẽ được tạo tự động từ ngày sinh (dd/mm/yyyy)");
        lblPasswordInfo.setForeground(Color.BLUE);
        JLabel lblPasswordDisplay = new JLabel("");
        
        formPanel.add(new JLabel("Chức vụ:"));
        formPanel.add(cboChucVu);
        formPanel.add(new JLabel("Mã nhân viên (tự động):"));
        formPanel.add(txtMaNV);
        formPanel.add(new JLabel("Họ tên:"));
        formPanel.add(txtTenNV);
        formPanel.add(new JLabel("Số CCCD:"));
        formPanel.add(txtCCCD);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtSDT);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Địa chỉ:"));
        formPanel.add(txtDiaChi);
        formPanel.add(new JLabel("Giới tính:"));
        formPanel.add(cboGioiTinh);
        formPanel.add(new JLabel("Ngày sinh:"));
        formPanel.add(birthDatePanel);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(chkTrangThai);
        formPanel.add(lblPasswordInfo);
        formPanel.add(lblPasswordDisplay);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        saveButton.addActionListener(e -> {
            try {
                // Validate input
                if (txtTenNV.getText().isEmpty() || txtCCCD.getText().isEmpty() || 
                    txtSDT.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ thông tin!");
                    return;
                }
                
                // Create date objects
                int day = (int) daySpinner.getValue();
                int month = (int) monthSpinner.getValue();
                int year = (int) yearSpinner.getValue();
                
                // Create birthdate as LocalDate
                LocalDate ngaySinh = LocalDate.of(year, month, day);
                
                // Format birthdate as password dd/mm/yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String password = ngaySinh.format(formatter);
                
                // Create new employee
                String maNV = txtMaNV.getText();
                String tenNV = txtTenNV.getText();
                String soCCCD = txtCCCD.getText();
                String sdtNV = txtSDT.getText();
                String emailNV = txtEmail.getText();
                String chucVu = (String) cboChucVu.getSelectedItem();
                String diaChi = txtDiaChi.getText();
                String gioiTinh = (String) cboGioiTinh.getSelectedItem();
                boolean trangThaiLV = chkTrangThai.isSelected();
                
                // Current date as start date
                LocalDate ngayBatDauLV = LocalDate.now();
                
                NhanVien nv = new NhanVien(maNV, tenNV, soCCCD, null, sdtNV, emailNV, 
                    chucVu, ngayBatDauLV, trangThaiLV, gioiTinh, diaChi, ngaySinh);
                
                // Transaction: insert employee and create account
                boolean success = false;
                
                // Save to database
                boolean empResult = nhanVienDAO.insertNV(nv);
                
                if (empResult) {
                    // Create account with employee ID as username
                    TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
                    TaiKhoan tk = new TaiKhoan(maNV, password, nv);
                    boolean accResult = tkDAO.insertTK(tk);
                    
                    if (accResult) {
                        success = true;
                    } else {
                        // If account creation fails, attempt to rollback employee creation
                        nhanVienDAO.deleteNV(maNV);
                        JOptionPane.showMessageDialog(addDialog, "Tạo tài khoản thất bại!");
                    }
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhân viên thất bại!");
                }
                
                if (success) {
                    JOptionPane.showMessageDialog(addDialog, 
                        "Thêm nhân viên thành công!\n" +
                        "Tài khoản đã được tạo với:\n" +
                        "Tên đăng nhập: " + maNV + "\n" +
                        "Mật khẩu: " + password);
                    loadEmployees(); // Refresh the table
                    addDialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        cancelButton.addActionListener(e -> addDialog.dispose());
        
        addDialog.setVisible(true);
    }


    // Implementation for editing an employee
    private void editEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }
        
        String maNV = table.getValueAt(selectedRow, 0).toString();
        
        try {
            // Get employee details
            NhanVien_DAO dao = new NhanVien_DAO();
            NhanVien nv = dao.getNhanVienById(maNV);
            
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!");
                return;
            }
            
            // Create edit dialog
            JDialog editDialog = new JDialog(this, "Sửa thông tin nhân viên", true);
            editDialog.setLayout(new BorderLayout());
            editDialog.setSize(700, 500);
            editDialog.setLocationRelativeTo(this);
            
            JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
            formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            // Form fields with current values
            JTextField txtMaNV = new JTextField(nv.getMaNV());
            txtMaNV.setEditable(false); // Cannot edit ID
            JTextField txtTenNV = new JTextField(nv.getTenNV());
            JComboBox<String> cboChucVu = new JComboBox<>(new String[]{"Quản lý", "Nhân Viên Quầy"});
            cboChucVu.setSelectedItem(nv.getChucVu());
            JTextField txtCCCD = new JTextField(nv.getSoCCCD());
            JTextField txtSDT = new JTextField(nv.getSdtNV());
            JTextField txtEmail = new JTextField(nv.getEmailNV());
            JTextField txtDiaChi = new JTextField(nv.getDiaChi());
            JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
            cboGioiTinh.setSelectedItem(nv.getGioiTinh());
            JCheckBox chkTrangThai = new JCheckBox("Đang làm việc");
            chkTrangThai.setSelected(nv.getTrangThaiLV());
            
            formPanel.add(new JLabel("Mã nhân viên:"));
            formPanel.add(txtMaNV);
            formPanel.add(new JLabel("Họ tên:"));
            formPanel.add(txtTenNV);
            formPanel.add(new JLabel("Chức vụ:"));
            formPanel.add(cboChucVu);
            formPanel.add(new JLabel("Số CCCD:"));
            formPanel.add(txtCCCD);
            formPanel.add(new JLabel("Số điện thoại:"));
            formPanel.add(txtSDT);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(txtEmail);
            formPanel.add(new JLabel("Địa chỉ:"));
            formPanel.add(txtDiaChi);
            formPanel.add(new JLabel("Giới tính:"));
            formPanel.add(cboGioiTinh);
            formPanel.add(new JLabel("Trạng thái:"));
            formPanel.add(chkTrangThai);
            
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
                    if (txtTenNV.getText().isEmpty() || txtCCCD.getText().isEmpty() || txtSDT.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin!");
                        return;
                    }
                    
                    // Update employee
                    nv.setTenNV(txtTenNV.getText());
                    nv.setSoCCCD(txtCCCD.getText());
                    nv.setSdtNV(txtSDT.getText());
                    nv.setEmailNV(txtEmail.getText());
                    nv.setChucVu((String) cboChucVu.getSelectedItem());
                    nv.setDiaChi(txtDiaChi.getText());
                    nv.setGioiTinh((String) cboGioiTinh.getSelectedItem());
                    nv.setTrangThaiLV(chkTrangThai.isSelected());
                    
                    // Save to database
                    boolean result = dao.updateNV(nv);
                    
                    if (result) {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thông tin nhân viên thành công!");
                        loadEmployees(); // Refresh the table
                        editDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(editDialog, "Cập nhật thông tin nhân viên thất bại!");
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

    // Implementation for deleting an employee
    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }
        
        String maNV = table.getValueAt(selectedRow, 0).toString();
        String tenNV = table.getValueAt(selectedRow, 1).toString();
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa nhân viên " + tenNV + " (" + maNV + ")?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                NhanVien_DAO dao = new NhanVien_DAO();
                boolean result = dao.deleteNV(maNV);
                
                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                    loadEmployees(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + e.getMessage());
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
                XSSFSheet sheet = workbook.createSheet("Danh sách nhân viên");
                
                // Create header row with styling
                XSSFRow headerRow = sheet.createRow(0);
                XSSFCellStyle headerStyle = workbook.createCellStyle();
                
                // Fix: Use POI's Font instead of AWT Font
                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                
                // Set headers
                String[] columns = {"Mã NV", "Họ tên", "Chức vụ", "Số CCCD", 
                                  "Ngày bắt đầu làm việc", "Số điện thoại", 
                                  "Email", "Trạng thái"};
                
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
            addEmployee();
        } else if (source == editButton) {
            editEmployee();
        } else if (source == deleteButton) {
            deleteEmployee();
        } else if (source == refreshButton) {
            loadEmployees();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.");
        } else if (source == searchButton) {
            searchEmployees();
        } else if (source == exportButton) {
            exportToExcel();
        }
    }
    
//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        SwingUtilities.invokeLater(() -> new QuanLyNhanVien_GUI());
//    }
}
