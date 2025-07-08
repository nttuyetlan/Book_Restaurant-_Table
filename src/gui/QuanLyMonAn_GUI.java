package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;
import dao.LoaiMon_DAO;
import dao.MonAn_DAO;
import entity.LoaiMon;
import entity.MonAn;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class QuanLyMonAn_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton addFromExcelButton;
    private JButton refreshButton;
    private JLabel totalItemsLabel;
    private LoaiMon_DAO daoLoaiMon;
    private MonAn_DAO daoMonAn;
    private ArrayList<MonAn> dsMonAn;
    private ArrayList<LoaiMon> dsLoaiMon;
    private DateTimeFormatter formatter;
    
    // Màu sắc lấy từ QuanLyNhanVien_GUI
    private final Color PRIMARY_COLOR = new Color(30, 129, 191);
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(46, 175, 80);
    private final Color TEXT_COLOR = new Color(33, 37, 41);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    public QuanLyMonAn_GUI() {
        setTitle("Quản lý món ăn");
        
        // Kết nối cơ sở dữ liệu và khởi tạo DAO
        ConnectDB.getInstance().connect();
        daoLoaiMon = new LoaiMon_DAO();
        daoMonAn = new MonAn_DAO();
        dsMonAn = daoMonAn.getAllMonAn();
        dsLoaiMon = daoLoaiMon.getAllLoaiMon();
        
        // Định dạng ngày giờ
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
  
//        
//        initUI();
//        loadItems();
//        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setVisible(true);
    }

    public JPanel initUI() {
        // Panel chính với padding
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Tiêu đề
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Panel trung tâm chứa panel điều khiển và bảng
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Panel điều khiển (tìm kiếm và các nút)
        JPanel controlPanel = createControlPanel();
        centerPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Panel bảng
        JPanel tableContainer = createTablePanel();
        centerPanel.add(tableContainer, BorderLayout.CENTER);
        
        // Panel trạng thái
        JPanel statusPanel = createStatusPanel();
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        loadItems();
        
//        add(contentPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("QUẢN LÝ MÓN ĂN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(new Color(220, 220, 220));
        headerPanel.add(separator, BorderLayout.SOUTH);
        
        return headerPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(15, 0));
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Phần tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(BACKGROUND_COLOR);
        
        searchField = new JTextField(25);
        searchField.setText("Tìm kiếm theo tên, mã món ăn...");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, 35));
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm theo tên, mã món ăn...")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Tìm kiếm theo tên, mã món ăn...");
                }
            }
        });
        
        searchButton = createStyledButton("Tìm kiếm", new Color(0, 123, 255));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);
        
        // Phần các nút chức năng
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);
        
        addButton = createStyledButton("Thêm món ăn", ACCENT_COLOR);
        addFromExcelButton = createStyledButton("Thêm từ Excel", new Color(25, 135, 84));
        refreshButton = createStyledButton("Làm mới", new Color(108, 117, 125));
        
        buttonsPanel.add(addFromExcelButton);
        buttonsPanel.add(addButton);
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
        
     // In createTablePanel method, update the column names and widths:
        String[] columnNames = {"Mã món ăn", "Tên món ăn", "Mô tả", "Đơn giá", "Ngày cập nhật", "Thuế (%)", "Loại món", "Trạng thái"};
        int[] columnWidths = {100, 200, 250, 100, 150, 80, 120, 100};

        
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
        
        // Đặt chiều rộng cột
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        // Định dạng tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Thêm nút "Xem chi tiết" bên dưới bảng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnViewDetails = createStyledButton("Xem chi tiết", new Color(0, 123, 255));
        btnViewDetails.addActionListener(e -> viewItemDetails());
        bottomPanel.add(btnViewDetails);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);
        
        return tablePanel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout(15, 0));
        statusPanel.setBackground(BACKGROUND_COLOR);
        statusPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        totalItemsLabel = new JLabel("Tổng số: 0 món ăn");
        totalItemsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
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
        
        statusPanel.add(totalItemsLabel, BorderLayout.WEST);
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
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(darken(bgColor, 0.1f));
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
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(darken(finalBgColor, 0.1f));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(finalBgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void loadItems() {
        tableModel.setRowCount(0);
        
        try {
            for (MonAn mon : dsMonAn) {
                Object[] rowData = {
                    mon.getMaMonAn(),
                    mon.getTenMonAn(),
                    mon.getMoTa(),
                    mon.getDonGia(),
                    mon.getNgayCapNhat().format(formatter),
                    mon.getThueMon(),
                    mon.getLoai().getTenLoai(),
                    mon.getTrangThai() == 1 ? "Đang phục vụ" : "Hết món"
                };
                tableModel.addRow(rowData);
            }
            
            totalItemsLabel.setText("Tổng số: " + tableModel.getRowCount() + " món ăn");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu món ăn: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void searchItems() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("tìm kiếm theo tên, mã món ăn...")) {
            return;
        }
        
        tableModel.setRowCount(0);
        try {
            int filteredCount = 0;
            for (MonAn mon : dsMonAn) {
                if (mon.getMaMonAn().toLowerCase().contains(searchText) ||
                    mon.getTenMonAn().toLowerCase().contains(searchText)) {
                    filteredCount++;
                    Object[] rowData = {
                            mon.getMaMonAn(),
                            mon.getTenMonAn(),
                            mon.getMoTa(),
                            mon.getDonGia(),
                            mon.getNgayCapNhat().format(formatter),
                            mon.getThueMon(),
                            mon.getLoai().getTenLoai(),
                            mon.getTrangThai() == 1 ? "Đang phục vụ" : "Hết món"
                        };
                        tableModel.addRow(rowData);
                }
            }
            
            totalItemsLabel.setText("Tìm thấy: " + filteredCount + " món ăn");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addItem() {
        MonAn monMoi = hienDialogThemMonAn(this);
        if (monMoi != null) {
            if (daoMonAn.insertMA(monMoi)) {
                dsMonAn.add(monMoi);
                tableModel.addRow(new Object[]{
                    monMoi.getMaMonAn(),
                    monMoi.getTenMonAn(),
                    monMoi.getMoTa(),
                    monMoi.getDonGia(),
                    monMoi.getNgayCapNhat().format(formatter),
                    monMoi.getThueMon(),
                    monMoi.getLoai().getTenLoai(),
                    monMoi.getTrangThai() == 1 ? "Đang phục vụ" : "Hết món"
                });
                totalItemsLabel.setText("Tổng số: " + tableModel.getRowCount() + " món ăn");
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại!");
            }
        }
    }

    private void themMonAnTuExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File excelFile = fileChooser.getSelectedFile();

            try {
                Workbook workbook = WorkbookFactory.create(excelFile);
                Sheet sheet = workbook.getSheetAt(0);

                List<MonAn> danhSachMonAn = new ArrayList<>();
                List<String> errors = new ArrayList<>();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                // Lấy mã món ăn cuối cùng trước khi import
                String lastMaMonAn = daoMonAn.getLastMaMonAn();
                int lastNumber = 0;
                if (lastMaMonAn != null && lastMaMonAn.matches("MA\\d+")) {
                    lastNumber = Integer.parseInt(lastMaMonAn.substring(2));
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    try {
                        // Tạo mã mới bằng cách tăng số thứ tự
                        lastNumber++;
                        String maMonAn = String.format("MA%03d", lastNumber);

                        String tenMonAn = row.getCell(0).getStringCellValue();
                        String moTa = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
                        double donGia = row.getCell(2).getNumericCellValue();
                        double thueMon = row.getCell(3).getNumericCellValue();
                        String maLoai = row.getCell(4).getStringCellValue();
                        String duongDanHinh = row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null;

                        // Validate dữ liệu
                        if (tenMonAn.isEmpty()) {
                            throw new Exception("Tên món không được để trống");
                        }
                        if (donGia <= 0) {
                            throw new Exception("Đơn giá phải lớn hơn 0");
                        }

                        // Tìm loại món
                        LoaiMon loaiMon = dsLoaiMon.stream()
                                .filter(loai -> loai.getMaLoai().equals(maLoai))
                                .findFirst()
                                .orElseThrow(() -> new Exception("Không tìm thấy loại món với mã: " + maLoai));

                        // Xử lý hình ảnh
                        byte[] hinhAnh = null;
                        if (duongDanHinh != null && !duongDanHinh.isEmpty()) {
                            try {
                                File fileHinh = new File(duongDanHinh);
                                if (fileHinh.exists()) {
                                    hinhAnh = Files.readAllBytes(fileHinh.toPath());
                                } else {
                                    throw new Exception("File hình ảnh không tồn tại: " + duongDanHinh);
                                }
                            } catch (IOException e) {
                                throw new Exception("Lỗi đọc file hình ảnh: " + e.getMessage());
                            }
                        }

                        MonAn monAn = new MonAn(maMonAn, tenMonAn, moTa, donGia, hinhAnh, LocalDateTime.now(), thueMon, loaiMon, 1); // Default trạng thái là 1 (Đang phục vụ)
                        danhSachMonAn.add(monAn);

                    } catch (Exception e) {
                        errors.add("Dòng " + (i + 1) + ": " + e.getMessage());
                    }
                }

                // Thêm vào database
                int countSuccess = 0;
                for (MonAn monAn : danhSachMonAn) {
                    try {
                        if (daoMonAn.insertMA(monAn)) {
                            countSuccess++;
                            dsMonAn.add(monAn);
                            tableModel.addRow(new Object[]{
                                    monAn.getMaMonAn(),
                                    monAn.getTenMonAn(),
                                    monAn.getMoTa(),
                                    monAn.getDonGia(),
                                    monAn.getNgayCapNhat().format(formatter),
                                    monAn.getThueMon(),
                                    monAn.getLoai().getTenLoai(),
                                    monAn.getTrangThai() == 1 ? "Đang phục vụ" : "Hết món"
                            });
                        }
                    } catch (Exception e) {
                        errors.add("Lỗi khi thêm món " + monAn.getTenMonAn() + ": " + e.getMessage());
                    }
                }

                // Hiển thị kết quả
                String message = "Đã thêm thành công " + countSuccess + "/" + danhSachMonAn.size() + " món ăn";
                if (!errors.isEmpty()) {
                    message += "\n\nCác lỗi gặp phải:\n" + String.join("\n", errors);
                }

                JOptionPane.showMessageDialog(this, message, "Kết quả import",
                        errors.isEmpty() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

                totalItemsLabel.setText("Tổng số: " + tableModel.getRowCount() + " món ăn");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc file Excel: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }}
        }
        
private void viewItemDetails() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            MonAn monDuocChon = dsMonAn.get(row);
            hienDialogChiTietMonAnSuaDuoc(this, monDuocChon, dsLoaiMon, () -> {
                tableModel.setValueAt(monDuocChon.getTenMonAn(), row, 1);
                tableModel.setValueAt(monDuocChon.getMoTa(), row, 2);
                tableModel.setValueAt(monDuocChon.getDonGia(), row, 3);
                tableModel.setValueAt(monDuocChon.getNgayCapNhat().format(formatter), row, 4);
                tableModel.setValueAt(monDuocChon.getThueMon(), row, 5);
                tableModel.setValueAt(monDuocChon.getLoai().getTenLoai(), row, 6);
                tableModel.setValueAt(monDuocChon.getTrangThai() == 1 ? "Đang phục vụ" : "Hết món", row, 7);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn để xem chi tiết.");
        }
    }


    // Giữ nguyên các phương thức dialog từ code gốc
    public MonAn hienDialogThemMonAn(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Thêm món ăn mới", true);
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField txtMa = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtMoTa = new JTextField();
        JTextField txtDonGia = new JTextField();
        JTextField txtThue = new JTextField();
        
        JComboBox<LoaiMon> cbLoai = new JComboBox<>(dsLoaiMon.toArray(new LoaiMon[0]));

        String[] trangThaiOptions = {"Hết món", "Đang phục vụ"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setSelectedIndex(1); // Default to available
        
        JLabel lblHinh = new JLabel("Chưa chọn hình");
        lblHinh.setHorizontalAlignment(JLabel.CENTER);
        JButton btnChonHinh = new JButton("Chọn hình");

        final byte[][] hinhDuocChon = new byte[1][];

        btnChonHinh.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png"));
            int result = chooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    hinhDuocChon[0] = java.nio.file.Files.readAllBytes(file.toPath());
                    lblHinh.setText("Đã chọn: " + file.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi đọc file hình ảnh.");
                }
            }
        });

        formPanel.add(new JLabel("Mã món ăn:"));
        formPanel.add(txtMa);
        formPanel.add(new JLabel("Tên món ăn:"));
        formPanel.add(txtTen);
        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(txtMoTa);
        formPanel.add(new JLabel("Đơn giá:"));
        formPanel.add(txtDonGia);
        formPanel.add(new JLabel("Thuế (%):"));
        formPanel.add(txtThue);
        formPanel.add(new JLabel("Loại món:"));
        formPanel.add(cbLoai);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(cbTrangThai);
        formPanel.add(new JLabel("Hình món ăn:"));
        formPanel.add(btnChonHinh);

        JPanel hinhPanel = new JPanel(new BorderLayout());
        hinhPanel.add(lblHinh, BorderLayout.CENTER);

        JButton btnLuu = createStyledButton("Lưu", ACCENT_COLOR);
        JButton btnHuy = createStyledButton("Hủy", new Color(108, 117, 125));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        dialog.add(formPanel, BorderLayout.NORTH);
        dialog.add(hinhPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        final MonAn[] ketQua = new MonAn[1];

        btnLuu.addActionListener(e -> {
            try {
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                String moTa = txtMoTa.getText().trim();
                double donGia = Double.parseDouble(txtDonGia.getText().trim());
                double thue = Double.parseDouble(txtThue.getText().trim());
                LoaiMon loai = (LoaiMon) cbLoai.getSelectedItem();
                int trangThai = cbTrangThai.getSelectedIndex();                if (ma.isEmpty() || ten.isEmpty() || moTa.isEmpty()) {
                    throw new Exception("Vui lòng điền đầy đủ thông tin.");
                }

                MonAn mon = new MonAn(ma, ten, moTa, donGia, hinhDuocChon[0], LocalDateTime.now(), thue, loai, trangThai);
                ketQua[0] = mon;
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
        return ketQua[0];
    }

    public void hienDialogChiTietMonAnSuaDuoc(JFrame parent, MonAn monAn, ArrayList<LoaiMon> danhSachLoaiMon, Runnable callbackCapNhat) {
        JDialog dialog = new JDialog(parent, "Chi tiết món ăn", true);
        dialog.setSize(500, 650);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField txtMa = new JTextField(monAn.getMaMonAn());
        txtMa.setEditable(false);

        JTextField txtTen = new JTextField(monAn.getTenMonAn());
        JTextArea txtMoTa = new JTextArea(monAn.getMoTa());
        JTextField txtDonGia = new JTextField(String.valueOf(monAn.getDonGia()));
        JTextField txtThue = new JTextField(String.valueOf(monAn.getThueMon()));

        JComboBox<LoaiMon> cbLoai = new JComboBox<>(danhSachLoaiMon.toArray(new LoaiMon[0]));
        cbLoai.setSelectedItem(monAn.getLoai());

        String[] trangThaiOptions = {"Hết món", "Đang phục vụ"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setSelectedIndex(monAn.getTrangThai()); // Set the current status

        JLabel lblHinhAnh = new JLabel();
        lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblHinhAnh.setPreferredSize(new Dimension(200, 200));

        if (monAn.getHinhMonAn() != null) {
            lblHinhAnh.setIcon(chuyenByteSangImageIcon(monAn.getHinhMonAn(), 200, 200));
        }

        JButton btnChonHinh = new JButton("Chọn hình mới");
        final byte[][] hinhMoi = new byte[1][];
        hinhMoi[0] = monAn.getHinhMonAn();

        btnChonHinh.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            if (chooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    byte[] imgData = Files.readAllBytes(file.toPath());
                    hinhMoi[0] = imgData;
                    lblHinhAnh.setIcon(chuyenByteSangImageIcon(imgData, 200, 200));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi đọc hình ảnh.");
                }
            }
        });

        formPanel.add(new JLabel("Mã món ăn:"));
        formPanel.add(txtMa);
        formPanel.add(new JLabel("Tên món ăn:"));
        formPanel.add(txtTen);
        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(new JScrollPane(txtMoTa));
        formPanel.add(new JLabel("Đơn giá:"));
        formPanel.add(txtDonGia);
        formPanel.add(new JLabel("Thuế (%):"));
        formPanel.add(txtThue);
        formPanel.add(new JLabel("Loại món:"));
        formPanel.add(cbLoai);
        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(cbTrangThai);

        JPanel hinhPanel = new JPanel(new BorderLayout());
        hinhPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hinhPanel.add(lblHinhAnh, BorderLayout.CENTER);
        hinhPanel.add(btnChonHinh, BorderLayout.SOUTH);

        JButton btnCapNhat = createStyledButton("Cập nhật", ACCENT_COLOR);
        JButton btnHuy = createStyledButton("Hủy", new Color(108, 117, 125));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnHuy);

        dialog.add(formPanel, BorderLayout.NORTH);
        dialog.add(hinhPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        btnCapNhat.addActionListener(e -> {
            try {
                String ten = txtTen.getText().trim();
                String moTa = txtMoTa.getText().trim();
                double donGia = Double.parseDouble(txtDonGia.getText().trim());
                double thue = Double.parseDouble(txtThue.getText().trim());
                LoaiMon loai = (LoaiMon) cbLoai.getSelectedItem();
                int trangThai = cbTrangThai.getSelectedIndex(); 
                if (ten.isEmpty() ) {
                    throw new Exception("Vui lòng nhập đầy đủ thông tin.");
                }

                monAn.setTenMonAn(ten);
                monAn.setMoTa(moTa);
                monAn.setDonGia(donGia);
                monAn.setThueMon(thue);
                monAn.setLoai(loai);
                monAn.setHinhMonAn(hinhMoi[0]);
                monAn.setNgayCapNhat(LocalDateTime.now());
                monAn.setTrangThai(trangThai); // Set the status

                if (daoMonAn.updateMonAn(monAn)) {
                    if (callbackCapNhat != null) callbackCapNhat.run();
                    JOptionPane.showMessageDialog(dialog, "Cập nhật món ăn thành công!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật món ăn thất bại!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private ImageIcon chuyenByteSangImageIcon(byte[] bytes, int width, int height) {
        if (bytes == null || bytes.length == 0) return null;
        ImageIcon icon = new ImageIcon(bytes);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == addButton) {
            addItem();
        } else if (source == searchButton) {
            searchItems();
        } else if (source == refreshButton) {
            dsMonAn = daoMonAn.getAllMonAn();
            loadItems();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.");
        } else if (source == addFromExcelButton) {
        	themMonAnTuExcel();
        } 
    }

//    private void handleMenuBarActions(Object source) {
//        if (source == menuBar.getMniDBTT1()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Thêm đơn đặt bàn trực tiếp sẽ được mở");
//        } else if (source == menuBar.getMniDBTT2()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Cập nhật đơn đặt bàn trực tiếp sẽ được mở");
//        } else if (source == menuBar.getMniDBT1()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Thêm đơn đặt bàn trước sẽ được mở");
//        } else if (source == menuBar.getMniDBT2()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Cập nhật đơn đặt bàn trước sẽ được mở");
//        } else if (source == menuBar.getMniMoCa()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Mở ca sẽ được mở");
//        } else if (source == menuBar.getMniKetCa()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Kết ca sẽ được mở");
//        } else if (source == menuBar.getMniThemKH()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Thêm khách hàng sẽ được mở");
//        } else if (source == menuBar.getMniCNKH()) {
//            JOptionPane.showMessageDialog(this, "Chức năng Cập nhật thông tin khách hàng sẽ được mở");
//        }
//    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new QuanLyMonAn_GUI());
    }
}