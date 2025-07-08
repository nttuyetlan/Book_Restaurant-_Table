package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

import dao.ChiTietCaLamViec_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietCaLamViec;
import entity.NhanVien;

public class TaiKhoan_GUI extends JFrame {
    private NhanVien currentUser;
    private JLabel avatarLabel;
    private JTextField idField, nameField, positionField, emailField, phoneField, hireDateField;
    private JTextArea shiftArea;
    private JLabel salaryLabel;
    private JButton updateButton, clearButton;
    
    private static NhanVien_DAO nv_dao = new NhanVien_DAO();
    private static ChiTietCaLamViec_DAO ctclv_dao = new ChiTietCaLamViec_DAO();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private Color bgColor = new Color(250, 250, 250);
    private Color primaryColor = new Color(52, 152, 219);
    private Color secondaryColor = new Color(41, 128, 185);
    private Color accentColor = new Color(231, 76, 60);
    private Color textColor = new Color(44, 62, 80);
	private NhanVien nv;
	private JFrame menu;

    public TaiKhoan_GUI() {
        this(null, null);
    }

    public TaiKhoan_GUI(NhanVien loggedInUser, JFrame parent) {
    	 setTitle("Thông Tin Cá Nhân");
         this.currentUser = loggedInUser;
         this.menu = parent;
    }

    private void initComponents() {
        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(160, 160));
        avatarLabel.setBorder(new LineBorder(Color.GRAY, 2, true));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);

        idField = createField(false);
        nameField = createField(true);
        positionField = createField(false);
        emailField = createField(true);
        phoneField = createField(true);
        hireDateField = createField(false);

        shiftArea = new JTextArea(5, 20);
        shiftArea.setLineWrap(true);
        shiftArea.setWrapStyleWord(true);
        shiftArea.setBorder(new LineBorder(secondaryColor, 1));

        salaryLabel = new JLabel("0 VND");
        salaryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        salaryLabel.setForeground(primaryColor);

        updateButton = new JButton("Cập nhật");
        updateButton.setBackground(primaryColor);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        updateButton.setPreferredSize(new Dimension(160, 35));

        clearButton = new JButton("Đăng xuất");
        clearButton.setBackground(accentColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        clearButton.setPreferredSize(new Dimension(160, 35));
    }

    private JTextField createField(boolean editable) {
        JTextField field = new JTextField();
        field.setEditable(editable);
//        field.setEnabled(editable);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        return field;
    }

    public JPanel setupLayout() {
    	JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);

        initComponents();
        setupListeners();

        nv = nv_dao.getNhanVienById(currentUser.getMaNV());

        if (nv != null) {
            setEmployeeAvatar(new ImageIcon(nv.getHinhTheNV()));
            LocalDate localDate = nv.getNgayBatDauLV();
            Date sqlDate = Date.valueOf(localDate);
            setEmployeeInfo(nv.getMaNV(), nv.getTenNV(), nv.getChucVu(), nv.getEmailNV(), nv.getSdtNV(), sqlDate);

//            setShiftInfo(
//                "Ca sáng",
//                LocalDateTime.of(2025, 5, 25, 7, 30),
//                LocalDateTime.of(2025, 5, 25, 15, 0),
//                500_000,
//                1_250_000,
//                "Giao ca đúng giờ, hỗ trợ khách hàng tốt.\nKhông có vấn đề phát sinh."
//            );
        }
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(bgColor);
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 10));
        leftPanel.add(avatarLabel, BorderLayout.NORTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(bgColor);
        rightPanel.setBorder(new EmptyBorder(20, 10, 20, 20));

        rightPanel.add(createSection("Thông Tin Nhân Viên", new JComponent[]{
            createLabelField("Mã nhân viên:", idField),
            createLabelField("Họ tên:", nameField),
            createLabelField("Chức vụ:", positionField),
            createLabelField("Email:", emailField),
            createLabelField("Số điện thoại:", phoneField),
            createLabelField("Ngày vào làm:", hireDateField)
        }));

        rightPanel.add(Box.createVerticalStrut(15));
//        rightPanel.add(createSection("Lịch làm việc", new JComponent[]{
//            new JScrollPane(shiftArea)
//        }));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(bgColor);

        TitledBorder statusBorder = BorderFactory.createTitledBorder("Trạng thái làm việc");
        statusBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
        statusPanel.setBorder(statusBorder);

        JLabel statusLabel = new JLabel("Đang làm việc");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(primaryColor);
        statusPanel.add(statusLabel);
        
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(statusPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(bgColor);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(buttonPanel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createSection(String title, JComponent[] components) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(components.length, 1, 10, 10));
        panel.setBackground(bgColor);
        TitledBorder sectionBorder = BorderFactory.createTitledBorder(title);
        sectionBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
        panel.setBorder(sectionBorder);
        for (JComponent comp : components) panel.add(comp);
        return panel;
    }

    private JPanel createLabelField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(bgColor);
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        jLabel.setForeground(textColor);
        jLabel.setPreferredSize(new Dimension(120, 30));
        panel.add(jLabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void setupListeners() {
        updateButton.addActionListener(e -> updateProfile());
        clearButton.addActionListener(e -> RemoveProfile());
    }

    private boolean validateInputs() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Họ tên không được để trống"); return false;
        }
        if (!EMAIL_PATTERN.matcher(emailField.getText().trim()).matches()) {
            showError("Email không hợp lệ"); return false;
        }
        if (!phoneField.getText().trim().matches("\\d{10}")) {
            showError("Số điện thoại phải gồm 10 chữ số"); return false;
        }
        return true;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void updateProfile() {
        if (validateInputs()) {
            
            nv.setTenNV(nameField.getText().trim());
            nv.setEmailNV(emailField.getText());
            nv.setSdtNV(phoneField.getText());
            if (nv_dao.updateNV(nv)) {
				JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "Cập nhật không thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
        }
    }

    private void RemoveProfile() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            menu.dispose();
            
            // Open login screen
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    DangNhap_GUI dangNhapGUI = new DangNhap_GUI(); 
                    dangNhapGUI.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }


    public void setEmployeeAvatar(ImageIcon icon) {
    	if (icon != null) {
            SwingUtilities.invokeLater(() -> {
                int width = avatarLabel.getWidth();
                int height = avatarLabel.getHeight();
                if (width > 0 && height > 0) {
                    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    avatarLabel.setIcon(new ImageIcon(image));
                }
            });
        }
    }

    public void setEmployeeInfo(String id, String name, String position, String email, String phone, Date hireDate) {
        idField.setText(id);
        nameField.setText(name);
        positionField.setText(position);
        emailField.setText(email);
        phoneField.setText(phone);
        hireDateField.setText(hireDate != null ? DATE_FORMAT.format(hireDate) : "");
    }

//    public void setShiftInfo(String tenCa, LocalDateTime gioBD, LocalDateTime gioKT, double tienDauCa, double tienCuoiCa, String ghiChu) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
//
//	    StringBuilder sb = new StringBuilder();
//	    sb.append("--- Chi tiết ca làm việc ---\n\n");
//	    sb.append(String.format("Ca làm việc:         %s\n", tenCa != null ? tenCa : "N/A"));
//	    sb.append(String.format("Bắt đầu (thực tế):   %s\n", gioBD != null ? dtf.format(gioBD) : "N/A"));
//	    sb.append(String.format("Kết thúc (thực tế):  %s\n", gioKT != null ? dtf.format(gioKT) : "N/A"));
//	    sb.append("\n");
//	    sb.append(String.format("Số tiền ban đầu:     %,d VND\n", (long)tienDauCa));
//	    sb.append(String.format("Số tiền cuối ca:     %,d VND\n", (long)tienCuoiCa));
//	    sb.append("\n");
//	    sb.append("Ghi chú:\n");
//	    sb.append(ghiChu != null && !ghiChu.isEmpty()
//	        ? "- " + ghiChu.trim().replace("\n", "\n- ")
//	        : "- Không có ghi chú.");
//	
//	    shiftArea.setText(sb.toString());
//    }

    public void setSalary(long salary) {
        salaryLabel.setText(String.format("%,d VND", salary));
    }

    public static void main(String[] args) {
        new TaiKhoan_GUI();
    }
}
