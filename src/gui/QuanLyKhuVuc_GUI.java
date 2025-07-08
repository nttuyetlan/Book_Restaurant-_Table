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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import connectDB.ConnectDB;
import dao.Ban_DAO;
import dao.KhuVuc_DAO;
import entity.Ban;
import entity.KhuVuc;
import entity.NhanVien;

public class QuanLyKhuVuc_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    private JTable khuVucTable, banTable;
    private DefaultTableModel khuVucTableModel, banTableModel;
    private JButton addKhuVucButton, editKhuVucButton, deleteKhuVucButton, toggleKhuVucButton, refreshKhuVucButton;
    private JButton addBanButton, editBanButton, deleteBanButton, refreshBanButton;
    private JLabel khuVucCountLabel, banCountLabel;
    private final Color PRIMARY_COLOR = new Color(30, 129, 191);
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(46, 175, 80);
    private final Color TEXT_COLOR = new Color(33, 37, 41);
    private final Color BACKGROUND_COLOR = Color.WHITE;
//    private MenuChinh_GUI menuBar;
    private NhanVien currentUser;

    private List<KhuVuc> khuVucList = new ArrayList<>();
    private List<Ban> banList = new ArrayList<>();

    public QuanLyKhuVuc_GUI() {
        this(null);
    }
    
    public QuanLyKhuVuc_GUI(NhanVien loggedInUser) {
        setTitle("Quản lý khu vực");
        setLayout(new BorderLayout());
        this.currentUser = loggedInUser;
//        s = new MenuChinh_GUI(currentUser);
//        menuBar.addActionListeners(this);
//        add(menuBar.getMenuBar(), BorderLayout.NORTH);
//        initUI();
//        loadKhuVucTable();
//        loadBanTable();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setVisible(true);
    }

    public JPanel initUI() {
        // Main container
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JLabel titleLabel = new JLabel("QUẢN LÝ NHÀ HÀNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabbed pane for modules
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.addTab("Khu vực", createKhuVucPanel());
        tabbedPane.addTab("Bàn", createBanPanel());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        loadKhuVucTable();
        loadBanTable();
        
//        add(contentPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel createKhuVucPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BACKGROUND_COLOR);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setBackground(BACKGROUND_COLOR);
        addKhuVucButton = createStyledButton("Thêm khu vực", ACCENT_COLOR);
        editKhuVucButton = createStyledButton("Sửa", new Color(255, 193, 7));
        deleteKhuVucButton = createStyledButton("Xóa", new Color(220, 53, 69));
        toggleKhuVucButton = createStyledButton("Bật/Tắt trạng thái", new Color(108, 117, 125));
        refreshKhuVucButton = createStyledButton("Làm mới", new Color(108, 117, 125));
        controlPanel.add(addKhuVucButton);
        controlPanel.add(editKhuVucButton);
        controlPanel.add(deleteKhuVucButton);
        controlPanel.add(toggleKhuVucButton);
        controlPanel.add(refreshKhuVucButton);
        panel.add(controlPanel, BorderLayout.NORTH);

        // Table panel
        String[] columnNames = {"Mã khu vực", "Tên khu vực", "Trạng thái"};
        khuVucTableModel = new DefaultTableModel(columnNames, 0);
        khuVucTable = new JTable(khuVucTableModel);
        styleTable(khuVucTable);
        JScrollPane scrollPane = new JScrollPane(khuVucTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Status panel
        khuVucCountLabel = new JLabel("Tổng số: 0 khu vực");
        khuVucCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(BACKGROUND_COLOR);
        statusPanel.add(khuVucCountLabel);
        panel.add(statusPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBanPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BACKGROUND_COLOR);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setBackground(BACKGROUND_COLOR);
        addBanButton = createStyledButton("Thêm bàn", ACCENT_COLOR);
        editBanButton = createStyledButton("Sửa", new Color(255, 193, 7));
        deleteBanButton = createStyledButton("Xóa", new Color(220, 53, 69));
        refreshBanButton = createStyledButton("Làm mới", new Color(108, 117, 125));
        controlPanel.add(addBanButton);
        controlPanel.add(editBanButton);
//        controlPanel.add(deleteBanButton);
        controlPanel.add(refreshBanButton);
        panel.add(controlPanel, BorderLayout.NORTH);

        // Table panel
        String[] columnNames = {"Mã bàn", "Số ghế", "Trạng thái", "Khu vực"};
        banTableModel = new DefaultTableModel(columnNames, 0);
        banTable = new JTable(banTableModel);
        styleTable(banTable);
        JScrollPane scrollPane = new JScrollPane(banTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Status panel
        banCountLabel = new JLabel("Tổng số: 0 bàn");
        banCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(BACKGROUND_COLOR);
        statusPanel.add(banCountLabel);
        panel.add(statusPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(BACKGROUND_COLOR);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(TEXT_COLOR);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
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

    private void loadKhuVucTable() {
        try {
            ConnectDB.getInstance().connect();

            KhuVuc_DAO khuVucDAO = new KhuVuc_DAO();
            khuVucList = khuVucDAO.getAllKhuVuc();
            
            // Clear table
            khuVucTableModel.setRowCount(0);
            
            // Populate table
            for (KhuVuc kv : khuVucList) {
                khuVucTableModel.addRow(new Object[]{
                    kv.getMaKV(),
                    kv.getTenKV(),
                    kv.getTrangThai() ? "Hoạt động" : "Không hoạt động"
                });
            }
            
            khuVucCountLabel.setText("Tổng số: " + khuVucList.size() + " khu vực");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu khu vực: " + e.getMessage());
        }
    }

    private void loadBanTable() {
        try {
            ConnectDB.getInstance().connect();

            Ban_DAO banDAO = new Ban_DAO();
            banList = banDAO.getAllBan();
            
            // Clear table
            banTableModel.setRowCount(0);
            
            // Populate table
            for (Ban b : banList) {
                String trangThaiText = switch(b.getTrangThai()) {
                    case 1 -> "Trống";
                    case 2 -> "Có khách";
                    case 3 -> "Đã đặt";
                    case 4 -> "Bảo trì";
                    default -> "Không xác định";
                };
                
                // Get KhuVuc name
                String tenKV = "";
                for (KhuVuc kv : khuVucList) {
                    if (kv.getMaKV().equals(b.getKV().getMaKV())) {
                        tenKV = kv.getTenKV();
                        break;
                    }
                }
                
                banTableModel.addRow(new Object[]{
                    b.getMaBan(),
                    b.getSoLuongGhe(),
                    trangThaiText,
                    tenKV + " (" + b.getKV().getMaKV() + ")"
                });
            }
            
            banCountLabel.setText("Tổng số: " + banList.size() + " bàn");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu bàn: " + e.getMessage());
        }
    }

    private void addKhuVuc() {
        JDialog dialog = new JDialog(this, "Thêm khu vực mới", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        
        // Panel chính sử dụng BoxLayout theo chiều dọc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 hàng, 2 cột
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        String maKV = "KV" + String.format("%02d", khuVucList.size() + 1);
        JTextField txtMaKV = new JTextField(maKV);
        txtMaKV.setEditable(false);
        txtMaKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JTextField txtTenKV = new JTextField();
        txtTenKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JCheckBox chkTrangThai = new JCheckBox("Hoạt động", true);
        chkTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTrangThai.setBackground(BACKGROUND_COLOR);

        JLabel lblMaKV = new JLabel("Mã khu vực:");
        lblMaKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTenKV = new JLabel("Tên khu vực:");
        lblTenKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        inputPanel.add(lblMaKV);
        inputPanel.add(txtMaKV);
        inputPanel.add(lblTenKV);
        inputPanel.add(txtTenKV);
        inputPanel.add(lblTrangThai);
        inputPanel.add(chkTrangThai);

        // Panel nút điều khiển
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        saveButton.setPreferredSize(new Dimension(80, 35));
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        cancelButton.setPreferredSize(new Dimension(80, 35));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống dãn cách
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);

        saveButton.addActionListener(e -> {
            if (txtTenKV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tên khu vực không được rỗng!");
                return;
            }
            KhuVuc_DAO khuVucDAO = new KhuVuc_DAO();
            KhuVuc newKhuVuc = new KhuVuc(txtMaKV.getText(), txtTenKV.getText().trim(), chkTrangThai.isSelected());
            if (khuVucDAO.insertKhuVuc(newKhuVuc)) {
                JOptionPane.showMessageDialog(dialog, "Thêm khu vực thành công!");
                loadKhuVucTable();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Thêm khu vực thất bại!");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void editKhuVuc() {
        int selectedRow = khuVucTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực cần sửa!");
            return;
        }
        
        String maKV = (String) khuVucTableModel.getValueAt(selectedRow, 0);
        KhuVuc selectedKV = null;
        
        for (KhuVuc kv : khuVucList) {
            if (kv.getMaKV().equals(maKV)) {
                selectedKV = kv;
                break;
            }
        }
        
        if (selectedKV == null) return;
        
        JDialog dialog = new JDialog(this, "Sửa khu vực", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        
        // Panel chính sử dụng BoxLayout theo chiều dọc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 hàng, 2 cột
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JTextField txtMaKV = new JTextField(selectedKV.getMaKV());
        txtMaKV.setEditable(false);
        txtMaKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JTextField txtTenKV = new JTextField(selectedKV.getTenKV());
        txtTenKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JCheckBox chkTrangThai = new JCheckBox("Hoạt động", selectedKV.getTrangThai());
        chkTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTrangThai.setBackground(BACKGROUND_COLOR);

        JLabel lblMaKV = new JLabel("Mã khu vực:");
        lblMaKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTenKV = new JLabel("Tên khu vực:");
        lblTenKV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        inputPanel.add(lblMaKV);
        inputPanel.add(txtMaKV);
        inputPanel.add(lblTenKV);
        inputPanel.add(txtTenKV);
        inputPanel.add(lblTrangThai);
        inputPanel.add(chkTrangThai);

        // Panel nút điều khiển
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        saveButton.setPreferredSize(new Dimension(80, 35));
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        cancelButton.setPreferredSize(new Dimension(80, 35));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống dãn cách
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);

        saveButton.addActionListener(e -> {
            if (txtTenKV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tên khu vực không được rỗng!");
                return;
            }
            
            KhuVuc updatedKhuVuc = new KhuVuc(maKV, txtTenKV.getText().trim(), chkTrangThai.isSelected());
            KhuVuc_DAO khuVucDAO = new KhuVuc_DAO();
            
            if (khuVucDAO.updateKhuVuc(updatedKhuVuc)) {
                JOptionPane.showMessageDialog(dialog, "Cập nhật khu vực thành công!");
                loadKhuVucTable();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật khu vực thất bại!");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }


    private void deleteKhuVuc() {
        int selectedRow = khuVucTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực cần xóa!");
            return;
        }
        
        String maKV = (String) khuVucTableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa khu vực này?\nLưu ý: Xóa khu vực sẽ làm mất các bàn thuộc khu vực!", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            KhuVuc_DAO khuVucDAO = new KhuVuc_DAO();
            if (khuVucDAO.deleteKhuVuc(maKV)) {
                JOptionPane.showMessageDialog(this, "Xóa khu vực thành công!");
                loadKhuVucTable();
                loadBanTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khu vực thất bại! Có thể khu vực đang được sử dụng.");
            }
        }
    }

    private void toggleKhuVuc() {
        int selectedRow = khuVucTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực cần thay đổi trạng thái!");
            return;
        }
        
        String maKV = (String) khuVucTableModel.getValueAt(selectedRow, 0);
        KhuVuc selectedKV = null;
        
        for (KhuVuc kv : khuVucList) {
            if (kv.getMaKV().equals(maKV)) {
                selectedKV = kv;
                break;
            }
        }
        
        if (selectedKV == null) return;
        
        // Toggle status
        KhuVuc updatedKhuVuc = new KhuVuc(maKV, selectedKV.getTenKV(), !selectedKV.getTrangThai());
        KhuVuc_DAO khuVucDAO = new KhuVuc_DAO();
        
        if (khuVucDAO.updateKhuVuc(updatedKhuVuc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái khu vực thành công!");
            loadKhuVucTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái khu vực thất bại!");
        }
    }


    private void addBan() {
        if (khuVucList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm khu vực trước khi thêm bàn!");
            return;
        }

        JDialog dialog = new JDialog(this, "Thêm bàn mới", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        // Panel chính sử dụng BoxLayout theo chiều dọc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 hàng, 2 cột
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); 

        JLabel lblMaBan = new JLabel("Mã bàn:");
        lblMaBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField txtMaBan = new JTextField();
        txtMaBan.setEditable(false);
        txtMaBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblSoLuongGhe = new JLabel("Số lượng ghế:");
        lblSoLuongGhe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField txtSoLuongGhe = new JTextField();
        txtSoLuongGhe.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        String[] trangThaiOptions = {"Mở bàn", "Đóng bàn"};
        JComboBox<String> cboTrangThai = new JComboBox<>(trangThaiOptions);
        cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblKhuVuc = new JLabel("Khu vực:");
        lblKhuVuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JComboBox<KhuVuc> cboKhuVuc = new JComboBox<>();
        for (KhuVuc kv : khuVucList) {
            cboKhuVuc.addItem(kv);
        }
        if (!khuVucList.isEmpty()) {
            cboKhuVuc.setSelectedIndex(0);
        }
        cboKhuVuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Sinh mã bàn ban đầu
        Ban_DAO banDAO = new Ban_DAO();
        KhuVuc selectedKV = (KhuVuc) cboKhuVuc.getSelectedItem();
        if (selectedKV != null) {
            String maBan = banDAO.generateMaBan(selectedKV.getMaKV(), banList.size() + 1);
            txtMaBan.setText(maBan);
        }

        // Cập nhật mã bàn khi thay đổi khu vực
        cboKhuVuc.addActionListener(e -> {
            KhuVuc newSelectedKV = (KhuVuc) cboKhuVuc.getSelectedItem();
            if (newSelectedKV != null) {
                String maBan = banDAO.generateMaBan(newSelectedKV.getMaKV(), banList.size() + 1);
                txtMaBan.setText(maBan);
            }
        });

        // Thêm các thành phần vào inputPanel
        inputPanel.add(lblMaBan);
        inputPanel.add(txtMaBan);
        inputPanel.add(lblSoLuongGhe);
        inputPanel.add(txtSoLuongGhe);
        inputPanel.add(lblTrangThai);
        inputPanel.add(cboTrangThai);
        inputPanel.add(lblKhuVuc);
        inputPanel.add(cboKhuVuc);

        // Panel nút điều khiển
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        saveButton.setPreferredSize(new Dimension(80, 35));
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        cancelButton.setPreferredSize(new Dimension(80, 35));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống dãn cách
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);

        saveButton.addActionListener(e -> {
            try {
                int soLuongGhe = Integer.parseInt(txtSoLuongGhe.getText().trim());
                if (soLuongGhe <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Số lượng ghế phải lớn hơn 0!");
                    return;
                }

                KhuVuc finalSelectedKV = (KhuVuc) cboKhuVuc.getSelectedItem();
                if (finalSelectedKV == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn khu vực!");
                    return;
                }

                int trangThai = cboTrangThai.getSelectedIndex() == 0 ? 1 : 0;
                Ban newBan = new Ban(txtMaBan.getText(), soLuongGhe, trangThai, finalSelectedKV);

                if (banDAO.insertBan(newBan)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm bàn thành công!");
                    loadBanTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm bàn thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Số lượng ghế phải là số nguyên!");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void editBan() {
        int selectedRow = banTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần sửa!");
            return;
        }
        
        String maBan = (String) banTableModel.getValueAt(selectedRow, 0);
        Ban selectedBan = null;
        
        for (Ban b : banList) {
            if (b.getMaBan().equals(maBan)) {
                selectedBan = b;
                break;
            }
        }
        
        if (selectedBan == null) return;
        
        JDialog dialog = new JDialog(this, "Sửa bàn", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        // Panel chính sử dụng BoxLayout theo chiều dọc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 hàng, 2 cột
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); 
        
        JLabel lblMaBan = new JLabel("Mã bàn:");
        lblMaBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField txtMaBan = new JTextField(selectedBan.getMaBan());
        txtMaBan.setEditable(false);
        txtMaBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblSoLuongGhe = new JLabel("Số lượng ghế:");
        lblSoLuongGhe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField txtSoLuongGhe = new JTextField(String.valueOf(selectedBan.getSoLuongGhe()));
        txtSoLuongGhe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        String[] trangThaiOptions = {"Trống", "Có khách", "Đã đặt", "Bảo trì"};
        JComboBox<String> cboTrangThai = new JComboBox<>(trangThaiOptions);
        cboTrangThai.setSelectedIndex(selectedBan.getTrangThai() - 1); // -1 because indexing starts from 0
        cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblKhuVuc = new JLabel("Khu vực:");
        lblKhuVuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JComboBox<KhuVuc> cboKhuVuc = new JComboBox<>();
        for (KhuVuc kv : khuVucList) {
            cboKhuVuc.addItem(kv);
            if (kv.getMaKV().equals(selectedBan.getKV().getMaKV())) {
                cboKhuVuc.setSelectedItem(kv);
            }
        }
        cboKhuVuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Thêm các thành phần vào inputPanel
        inputPanel.add(lblMaBan);
        inputPanel.add(txtMaBan);
        inputPanel.add(lblSoLuongGhe);
        inputPanel.add(txtSoLuongGhe);
        inputPanel.add(lblTrangThai);
        inputPanel.add(cboTrangThai);
        inputPanel.add(lblKhuVuc);
        inputPanel.add(cboKhuVuc);

        // Panel nút điều khiển
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton saveButton = createStyledButton("Lưu", ACCENT_COLOR);
        saveButton.setPreferredSize(new Dimension(80, 35));
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125));
        cancelButton.setPreferredSize(new Dimension(80, 35));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalGlue()); // Thêm khoảng trống dãn cách
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel);

        saveButton.addActionListener(e -> {
            try {
                int soLuongGhe = Integer.parseInt(txtSoLuongGhe.getText().trim());
                if (soLuongGhe <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Số lượng ghế phải lớn hơn 0!");
                    return;
                }
                
                KhuVuc selectedKV = (KhuVuc) cboKhuVuc.getSelectedItem();
                int trangThai = cboTrangThai.getSelectedIndex() + 1; // +1 because indexing starts from 0
                
                // Use the existing phong to maintain compatibility
                Ban updatedBan = new Ban(maBan, soLuongGhe, trangThai, selectedKV);
                
                Ban_DAO banDAO = new Ban_DAO();
                if (banDAO.updateBan(updatedBan)) {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật bàn thành công!");
                    loadBanTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật bàn thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Số lượng ghế phải là số nguyên!");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }


//    private void deleteBan() {
//        int selectedRow = banTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần xóa!");
//            return;
//        }
//        
//        String maBan = (String) banTableModel.getValueAt(selectedRow, 0);
//        
//        int confirm = JOptionPane.showConfirmDialog(this, 
//                "Bạn có chắc muốn xóa bàn này?", 
//                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//        
//        if (confirm == JOptionPane.YES_OPTION) {
//            Ban_DAO banDAO = new Ban_DAO();
//            try {
//                if (banDAO.deleteBan(maBan)) {
//                    JOptionPane.showMessageDialog(this, "Xóa bàn thành công!");
//                    loadBanTable();
//                } else {
//                    JOptionPane.showMessageDialog(this, "Xóa bàn thất bại!");
//                }
//            } catch (Exception ex) {
//                // Check if it's a foreign key constraint error
//                if (ex.getMessage() != null && ex.getMessage().contains("REFERENCE constraint")) {
//                    JOptionPane.showMessageDialog(this, 
//                        "Không thể xóa bàn này vì nó đang được sử dụng trong hoá đơn hoặc các bảng liên quan khác.",
//                        "Lỗi ràng buộc dữ liệu",
//                        JOptionPane.ERROR_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(this, "Lỗi xóa bàn: " + ex.getMessage());
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addKhuVucButton) {
            addKhuVuc();
        } else if (source == editKhuVucButton) {
            editKhuVuc();
        } else if (source == deleteKhuVucButton) {
            deleteKhuVuc();
        } else if (source == toggleKhuVucButton) {
            toggleKhuVuc();
        } else if (source == refreshKhuVucButton) {
            loadKhuVucTable();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu khu vực.");
        } else if (source == addBanButton) {
            addBan();
        } else if (source == editBanButton) {
            editBan();
//        } else if (source == deleteBanButton) {
//            deleteBan();
        } else if (source == refreshBanButton) {
            loadBanTable();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu bàn.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new QuanLyKhuVuc_GUI());
    }
}
