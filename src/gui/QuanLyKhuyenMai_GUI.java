package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

import com.toedter.calendar.JDateChooser;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;

import connectDB.ConnectDB;
import dao.KhuyenMai_DAO;
import entity.KhuyenMai;
import entity.NhanVien;

public class QuanLyKhuyenMai_GUI extends JFrame implements ActionListener {
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
    
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // DAO
    private KhuyenMai_DAO khuyenMaiDAO;
    
    public QuanLyKhuyenMai_GUI() {
        this(null);
    }
    
    public QuanLyKhuyenMai_GUI(NhanVien loggedInUser) {
        setTitle("Quản lý khuyến mãi");
        this.currentUser = loggedInUser;
        this.khuyenMaiDAO = new KhuyenMai_DAO();
        
//        setLayout(new BorderLayout());
//        
//        menuBar = new MenuChinh_GUI();
////        menuBar.addActionListeners(this);
//        add(menuBar.initUI(), BorderLayout.NORTH);
//        
//        initUI();
//        loadKhuyenMai();
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
        
        // Create status bar with total count
        JPanel statusPanel = createStatusPanel();
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
//        add(contentPanel, BorderLayout.CENTER);
        loadKhuyenMai();
        return contentPanel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("QUẢN LÝ KHUYẾN MÃI");
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
        searchField.setText("Tìm theo mã hoặc tên khuyến mãi");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 35));
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm theo mã hoặc tên khuyến mãi")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Tìm theo mã hoặc tên khuyến mãi");
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
        
        addButton = createStyledButton("Thêm khuyến mãi", ACCENT_COLOR);
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

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        
        String[] columnNames = {"Mã KM", "Tên KM", "Nội dung", "Giá trị", "Số lượng", "Bắt đầu", "Kết thúc"};
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
        totalCustomersLabel = new JLabel("Tổng số: 0 khuyến mãi");
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

    //Tải dữ liệu khuyến mãi ra bảng
    private void loadKhuyenMai() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        try {
            ArrayList<KhuyenMai> dsKM = new ArrayList();
            dsKM.clear();
            dsKM = khuyenMaiDAO.getAllKhuyenMai();
            for (KhuyenMai km : dsKM) {
                Object[] rowData = {
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getNoiDungKM(),
                    km.getGiaTriKM(),
                    km.getSoLuong(),
                    km.getNgayBatDauKM().toString(),
                    km.getNgayKetThucKM().toString()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Tìm kiếm khuyến mãi
    private void searchKhuyenMai() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("Tìm theo mã hoặc tên khuyến mãi")) {
            return; // Don't search with placeholder text
        }
        
        tableModel.setRowCount(0);
        try {
            ConnectDB.getInstance().connect();
            ArrayList<KhuyenMai> dsKhuyenMai = khuyenMaiDAO.getAllKhuyenMai();
            
            int filteredCount = 0;
            
            for (KhuyenMai km : dsKhuyenMai) {
                // Search in all relevant fields
                if (km.getMaKM().toLowerCase().contains(searchText) ||
                    km.getTenKM().toLowerCase().contains(searchText)) {
                    
                    filteredCount++;
                    
                    Object[] rowData = {
                    	km.getMaKM(),
                        km.getTenKM(),
                        km.getNoiDungKM(),
                        km.getGiaTriKM(),
                        km.getSoLuong(),
                        km.getNgayBatDauKM().format(dtf),
                        km.getNgayKetThucKM().format(dtf)
                    };
                    
                    tableModel.addRow(rowData);
                }
            }
            
            totalCustomersLabel.setText("Tìm thấy: " + filteredCount + " khuyến mãi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //Thêm khuyến mãi mới
    private void addKhuyenMai() {
        JDialog addDialog = new JDialog(this, "Thêm khuyến mãi mới", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(600, 400);
        addDialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField txtMaKM = new JTextField(20);
        txtMaKM.setEditable(false);
        txtMaKM.setText(khuyenMaiDAO.taoMaKhuyenMaiTuDong());

        JTextField txtTenKM = new JTextField(20);
        JTextField txtNoiDung = new JTextField(20);
        JTextField txtGiaTri = new JTextField(20);
        JTextField txtSoLuong = new JTextField(20);

        // Chọn ngày bằng JCalendar
        JDateChooser dateChooserBatDau = new JDateChooser();
        JDateChooser dateChooserKetThuc = new JDateChooser();
        dateChooserBatDau.setDateFormatString("yyyy-MM-dd");
        dateChooserKetThuc.setDateFormatString("yyyy-MM-dd");

        formPanel.add(new JLabel("Mã khuyến mãi (tự động):")); formPanel.add(txtMaKM);
        formPanel.add(new JLabel("Tên khuyến mãi:")); formPanel.add(txtTenKM);
        formPanel.add(new JLabel("Nội dung khuyến mãi:")); formPanel.add(txtNoiDung);
        formPanel.add(new JLabel("Giá trị khuyến mãi (%):")); formPanel.add(txtGiaTri);
        formPanel.add(new JLabel("Số lượng:")); formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Ngày bắt đầu:")); formPanel.add(dateChooserBatDau);
        formPanel.add(new JLabel("Ngày kết thúc:")); formPanel.add(dateChooserKetThuc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                if (txtTenKM.getText().isEmpty() || txtGiaTri.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng nhập Tên và Giá trị khuyến mãi!");
                    return;
                }

                Date ngayBD = dateChooserBatDau.getDate();
                Date ngayKT = dateChooserKetThuc.getDate();

                if (ngayBD == null || ngayKT == null) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng chọn ngày bắt đầu và kết thúc!");
                    return;
                }

                LocalDate ngayBatDau = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate ngayKetThuc = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Kiểm tra logic ngày
                if (ngayBatDau.isAfter(ngayKetThuc)) {
                    JOptionPane.showMessageDialog(addDialog, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                    return;
                }

                if (!ngayBatDau.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(addDialog, "Ngày bắt đầu phải lớn hơn ngày hiện tại!");
                    return;
                }

                // Parse input
                String maKM = txtMaKM.getText();
                String tenKM = txtTenKM.getText();
                String noiDung = txtNoiDung.getText();
                double giaTri = Double.parseDouble(txtGiaTri.getText());
                int soLuong = Integer.parseInt(txtSoLuong.getText());

                KhuyenMai km = new KhuyenMai(maKM, tenKM, noiDung, giaTri, soLuong, ngayBatDau, ngayKetThuc);

                boolean result = khuyenMaiDAO.insertKhuyenMai(km);

                if (result) {
                    JOptionPane.showMessageDialog(addDialog, "Thêm khuyến mãi thành công!");
                    loadKhuyenMai();
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm khuyến mãi thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> addDialog.dispose());
        addDialog.setVisible(true);
    }

    private void editKhuyenMai() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa!");
            return;
        }

        String maKM = table.getValueAt(selectedRow, 0).toString();
        KhuyenMai km = khuyenMaiDAO.timKhuyenMai(maKM);

        if (km == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khuyến mãi!");
            return;
        }

        JDialog editDialog = new JDialog(this, "Sửa khuyến mãi", true);
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(600, 400);
        editDialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField txtMaKM = new JTextField(km.getMaKM());
        txtMaKM.setEditable(false);
        JTextField txtTenKM = new JTextField(km.getTenKM());
        JTextField txtNoiDung = new JTextField(km.getNoiDungKM());
        JTextField txtGiaTri = new JTextField(String.valueOf(km.getGiaTriKM()));
        JTextField txtSoLuong = new JTextField(String.valueOf(km.getSoLuong()));

        JDateChooser dateChooserBatDau = new JDateChooser();
        dateChooserBatDau.setDate(java.sql.Date.valueOf(km.getNgayBatDauKM()));
        dateChooserBatDau.setDateFormatString("yyyy-MM-dd");

        JDateChooser dateChooserKetThuc = new JDateChooser();
        dateChooserKetThuc.setDate(java.sql.Date.valueOf(km.getNgayKetThucKM()));
        dateChooserKetThuc.setDateFormatString("yyyy-MM-dd");

        formPanel.add(new JLabel("Mã khuyến mãi:")); formPanel.add(txtMaKM);
        formPanel.add(new JLabel("Tên khuyến mãi:")); formPanel.add(txtTenKM);
        formPanel.add(new JLabel("Nội dung khuyến mãi:")); formPanel.add(txtNoiDung);
        formPanel.add(new JLabel("Giá trị khuyến mãi (%):")); formPanel.add(txtGiaTri);
        formPanel.add(new JLabel("Số lượng:")); formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Ngày bắt đầu:")); formPanel.add(dateChooserBatDau);
        formPanel.add(new JLabel("Ngày kết thúc:")); formPanel.add(dateChooserKetThuc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                String tenKM = txtTenKM.getText();
                String noiDung = txtNoiDung.getText();
                double giaTri = Double.parseDouble(txtGiaTri.getText());
                int soLuong = Integer.parseInt(txtSoLuong.getText());

                Date ngayBD = dateChooserBatDau.getDate();
                Date ngayKT = dateChooserKetThuc.getDate();

                if (tenKM.isEmpty() || ngayBD == null || ngayKT == null) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đủ thông tin!");
                    return;
                }

                LocalDate ngayBatDau = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate ngayKetThuc = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Kiểm tra logic ngày
                if (!ngayBatDau.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(editDialog, "Ngày bắt đầu phải lớn hơn ngày hiện tại!");
                    return;
                }

                if (ngayKetThuc.isBefore(ngayBatDau)) {
                    JOptionPane.showMessageDialog(editDialog, "Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
                    return;
                }

                // Cập nhật lại đối tượng
                km.setTenKM(tenKM);
                km.setNoiDungKM(noiDung);
                km.setGiaTriKM(giaTri);
                km.setSoLuong(soLuong);
                km.setNgayBatDauKM(ngayBatDau);
                km.setNgayKetThucKM(ngayKetThuc);

                boolean result = khuyenMaiDAO.updateKhuyenMai(km);
                if (result) {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                    loadKhuyenMai();
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> editDialog.dispose());
        editDialog.setVisible(true);
    }

    
    
    // Xóa khuyến mãi
    private void deleteKhuyenMai() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!");
            return;
        }

        String maKM = table.getValueAt(selectedRow, 0).toString();
        String tenKM = table.getValueAt(selectedRow, 1).toString();

        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khuyến mãi " + tenKM + " (" + maKM + ")?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                boolean result = khuyenMaiDAO.deleteKhuyenMai(maKM);

                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
                    loadKhuyenMai(); // Là hàm refresh lại bảng sau khi xóa
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại! Có thể khuyến mãi đang được sử dụng.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa khuyến mãi: " + e.getMessage());
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
                XSSFSheet sheet = workbook.createSheet("Danh sách khuyến mãi");
                
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
                String[] columns = {"Mã KM", "Tên KM", "Nội dung", "Giá trị", "Số lượng", "Bắt đầu", "Kết thúc"};
                
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
            addKhuyenMai();
        } else if (source == editButton) {
            editKhuyenMai();
        } else if (source == deleteButton) {
            deleteKhuyenMai();
        } else if (source == refreshButton) {
            loadKhuyenMai();
            searchField.setText("Tìm theo mã hoặc tên khuyến mãi");
            searchField.setForeground(Color.GRAY);
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.");
        } else if (source == searchButton) {
            searchKhuyenMai();
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
//        SwingUtilities.invokeLater(() -> {
//            try {
//                ConnectDB.getInstance().connect();
//                new QuanLyKhuyenMai_GUI();
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }
}
