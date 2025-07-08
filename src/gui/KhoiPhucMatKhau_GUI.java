package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import connectDB.ConnectDB;

public class KhoiPhucMatKhau_GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JTextField txtCCCD;
    private JButton btnReset;
    private JButton btnCancel;

    // Constants
    private final Color PANEL_BACKGROUND = new Color(28, 30, 36);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(116, 205, 134);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public KhoiPhucMatKhau_GUI() {
    	try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", 
                "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
        }
        setTitle("Khôi Phục Mật Khẩu");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 22, 26));
        mainPanel.add(createResetPasswordPanel(), BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createResetPasswordPanel() {
        JPanel resetPanel = new JPanel();
        resetPanel.setBackground(PANEL_BACKGROUND);
        resetPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(255, 255, 255, 30)),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("KHÔI PHỤC MẬT KHẨU", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(titleLabel);
        resetPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(LABEL_FONT);
        lblUsername.setForeground(TEXT_COLOR);

        txtUsername = new JTextField();
        setupTextField(txtUsername);
        formPanel.add(lblUsername);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtUsername);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblCCCD = new JLabel("Số CCCD:");
        lblCCCD.setFont(LABEL_FONT);
        lblCCCD.setForeground(TEXT_COLOR);

        txtCCCD = new JTextField();
        setupTextField(txtCCCD);
        formPanel.add(lblCCCD);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtCCCD);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        resetPanel.add(formPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        btnReset = new JButton("KHÔI PHỤC");
        setupButton(btnReset, BUTTON_COLOR);
        btnReset.addActionListener(e -> handlePasswordReset());

        btnCancel = new JButton("HỦY");
        setupButton(btnCancel, new Color(180, 180, 180));
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnReset);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btnCancel);

        resetPanel.add(buttonPanel);
        resetPanel.add(Box.createVerticalGlue());

        JLabel footerLabel = new JLabel("© " + Calendar.getInstance().get(Calendar.YEAR) +
            " Restaurant System Management. All rights reserved | Design by N13", JLabel.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(180, 180, 180));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(footerLabel);

        return resetPanel;
    }

    private void handlePasswordReset() {
        String username = txtUsername.getText().trim();
        String cccd = txtCCCD.getText().trim();

        if (username.isEmpty() || cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            
            // Get the account to find the employee code
            TaiKhoan taiKhoan = taiKhoanDAO.findAccountByUsername(username);
            if (taiKhoan == null) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String maNV = taiKhoan.getNhanVien().getMaNV();
            
            // Get the employee to check CCCD
            NhanVien nhanVien = nhanVienDAO.getNhanVienTheoMa(maNV);
            if (nhanVien == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verify CCCD
            if (!cccd.equals(nhanVien.getSoCCCD())) {
                JOptionPane.showMessageDialog(this, "Số CCCD không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String email = nhanVien.getEmailNV();
            if (email == null || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy email của nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPassword = generateRandomPassword(8);
            
            // Send the new password by email
            sendNewPasswordByEmail(email, newPassword);

            // Reset the password in the database
            if (taiKhoanDAO.resetPassword(username, newPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới đã gửi tới email: " + maskEmail(email), 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to mask email for security (e.g., a***@gmail.com)
    private String maskEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            return email;
        }
        
        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        
        if (name.length() <= 2) {
            return name + "***@" + domain;
        }
        
        return name.substring(0, 1) + "***@" + domain;
    }

    private void sendNewPasswordByEmail(String toEmail, String newPassword) throws MessagingException {
        final String fromEmail = "tranminhtu2032004@gmail.com"; // Email người gửi
        final String password = "gqid xaks fvvp vfwo"; // App Password của Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Khôi phục mật khẩu - Hệ thống nhà hàng");

        String content = "<h3>Chào bạn,</h3>" +
                         "<p>Mật khẩu mới của bạn là: <b>" + newPassword + "</b></p>" +
                         "<p>Vui lòng đăng nhập và đổi mật khẩu ngay khi có thể.</p>";

        message.setContent(content, "text/html; charset=utf-8");
        Transport.send(message);
    }

    private String generateRandomPassword(int length) {
        String digits = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }

    private void setupTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBackground(new Color(40, 44, 52));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 63, 68), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

    private void setupButton(JButton button, Color bgColor) {
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    // Custom border
    class RoundedBorder extends AbstractBorder {
        private static final long serialVersionUID = 1L;
        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            KhoiPhucMatKhau_GUI frame = new KhoiPhucMatKhau_GUI();
            frame.setVisible(true);
        });
    }
}
