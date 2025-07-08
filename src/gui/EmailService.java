package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.KhachHang_DAO;
import dao.Ban_DAO;
import entity.Ban;
import entity.DonDatBanTruoc;
import entity.KhachHang;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
    // UI constants
    private static final Color CONTENT_PANEL = Color.WHITE;
    private static final Color BUTTON_GREEN = new Color(46, 175, 80);
    private static final Color BUTTON_GREEN_HOVER = new Color(38, 160, 70);
    private static final Color BUTTON_GRAY = new Color(100, 110, 120);
    private static final Color BUTTON_GRAY_HOVER = new Color(80, 90, 100);
    
    // Email credentials - you could make these configurable
    private static final String FROM_EMAIL = "tranminhtu2032004@gmail.com";
    private static final String EMAIL_PASSWORD = "gqid xaks fvvp vfwo";
    
    /**
     * Prepares email for a customer based on their reservation
     * @param parentFrame The parent frame for dialogs
     * @param selectedDonDat The selected reservation
     * @param khachHangDAO Data access object for customer information
     * @param banDAO Data access object for table information
     */
    public static void emailThongBao(JFrame parentFrame, DonDatBanTruoc selectedDonDat, 
            KhachHang_DAO khachHangDAO, Ban_DAO banDAO) {
        
        if (selectedDonDat == null) {
            JOptionPane.showMessageDialog(parentFrame, "Vui lòng chọn một đơn đặt bàn!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        KhachHang khachHang = khachHangDAO.getKhachHangById(selectedDonDat.getKH().getMaKH());
        Ban ban = banDAO.getBanById(selectedDonDat.getBan().getMaBan());

        if (khachHang != null && khachHang.getEmailKH() != null && !khachHang.getEmailKH().isEmpty()) {
            showEmailPreviewDialog(parentFrame, khachHang, selectedDonDat, ban);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Không tìm thấy địa chỉ email của khách hàng.", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Shows the email preview dialog
     * @param parentFrame The parent frame for this dialog
     * @param kh Customer information
     * @param donDat Reservation information
     * @param ban Table information
     */
    private static void showEmailPreviewDialog(JFrame parentFrame, KhachHang kh, 
            DonDatBanTruoc donDat, Ban ban) {
        
        JDialog emailDialog = new JDialog(parentFrame, "Gửi Email cho Khách Hàng", true);
        emailDialog.setSize(500, 450);
        emailDialog.setLocationRelativeTo(parentFrame);
        emailDialog.setLayout(new BorderLayout());

        JPanel pnlEmailContent = new JPanel(new BorderLayout(10, 10));
        pnlEmailContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlEmailContent.setBackground(CONTENT_PANEL);

        JPanel pnlEmailHeader = new JPanel(new GridLayout(3, 2, 5, 10));
        pnlEmailHeader.setBackground(CONTENT_PANEL);

        JLabel lblToEmail = new JLabel("Đến:");
        JTextField txtToEmail = new JTextField(kh.getEmailKH());
        txtToEmail.setEditable(false);

        JLabel lblSubject = new JLabel("Tiêu đề:");
        JTextField txtSubject = new JTextField("THÔNG TIN ĐƠN ĐẶT BÀN TẠI NHÀ HÀNG KAREM");

        JLabel lblCustomerName = new JLabel("Khách hàng:");
        JTextField txtCustomerName = new JTextField(kh.getTenKH());
        txtCustomerName.setEditable(false);

        pnlEmailHeader.add(lblToEmail);
        pnlEmailHeader.add(txtToEmail);
        pnlEmailHeader.add(lblSubject);
        pnlEmailHeader.add(txtSubject);
        pnlEmailHeader.add(lblCustomerName);
        pnlEmailHeader.add(txtCustomerName);

        JLabel lblContent = new JLabel("Nội dung:");
        JTextArea txtContent = new JTextArea(12, 40);
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);

        String emailContent = "Kính chào Qúy khách " + kh.getTenKH() + ",\n\n"
                + "Nhà hàng Karem trân trọng cảm ơn quý khách đã đặt bàn tại nhà hàng của chúng tôi.\n\n"
                + "Mã đơn đặt bàn của quý khách là " + donDat.getMaDonDatBanTruoc() + ",\n" 
                + "Thời gian nhận: " + donDat.getThoiGianNhanBan().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ",\n"
                + "Bàn: " + ban.getMaBan() + ",\n" 
                + "Số lượng khách: " + donDat.getSoLuongKhach() + ",\n\n"
                + "Nếu có thắc mắc hoặc thay đổi thông tin, vui lòng phản hồi lại Email này hoặc số Hotline: 094616747.\n"
                + "Trân trọng,\nNhà hàng KAREM \n\n" 
                + "Địa chỉ: 24/4 Trương Quốc Dung, P.8, Q.Phú Nhuận, TP.HCM\n";
        txtContent.setText(emailContent);

        JScrollPane scrollContent = new JScrollPane(txtContent);

        JPanel pnlEmailBody = new JPanel(new BorderLayout(5, 10));
        pnlEmailBody.setBackground(CONTENT_PANEL);
        pnlEmailBody.add(lblContent, BorderLayout.NORTH);
        pnlEmailBody.add(scrollContent, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(CONTENT_PANEL);

        JButton btnSend = new JButton("Gửi Email");
        styleModernButton(btnSend, BUTTON_GREEN, BUTTON_GREEN_HOVER);

        JButton btnCancel = new JButton("Hủy");
        styleModernButton(btnCancel, BUTTON_GRAY, BUTTON_GRAY_HOVER);

        pnlButtons.add(btnSend);
        pnlButtons.add(btnCancel);

        pnlEmailContent.add(pnlEmailHeader, BorderLayout.NORTH);
        pnlEmailContent.add(pnlEmailBody, BorderLayout.CENTER);
        pnlEmailContent.add(pnlButtons, BorderLayout.SOUTH);

        emailDialog.add(pnlEmailContent, BorderLayout.CENTER);

        btnCancel.addActionListener(e -> emailDialog.dispose());
        btnSend.addActionListener(e -> {
            sendEmail(txtToEmail.getText(), txtSubject.getText(), txtContent.getText());
            emailDialog.dispose();
        });

        emailDialog.setVisible(true);
    }

    /**
     * Sends an email with the provided information
     * @param recipientEmail Email address to send to
     * @param subject Email subject
     * @param messageText Email body text
     * @return true if email was sent successfully, false otherwise
     */
    public static boolean sendEmail(String recipientEmail, String subject, String messageText) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email đã được gửi thành công!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Gửi email thất bại: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Styles a button with modern look and hover effects
     */
    private static void styleModernButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        Dimension buttonSize = new Dimension(120, 32);
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
}
