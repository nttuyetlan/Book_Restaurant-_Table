package gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import dao.Ban_DAO;
import dao.DonDatBanTruoc_DAO;
import dao.KhachHang_DAO;
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

public class EmailReminderScheduler {
    
    private static ScheduledExecutorService scheduler;
    private static final long CHECK_INTERVAL_MINUTES = 15; // Check every 15 minutes
    private static final int REMIND_HOURS_BEFORE = 3; // Send reminder 3 hours before reservation
    
    private static final String FROM_EMAIL = "tranminhtu2032004@gmail.com"; 
    private static final String EMAIL_PASSWORD = "gqid xaks fvvp vfwo"; 
    
    private final DonDatBanTruoc_DAO donDatBanTruocDAO;
    private final KhachHang_DAO khachHangDAO;
    private final Ban_DAO banDAO;
    
    private static EmailReminderScheduler instance;
    
    private EmailReminderScheduler() {
        donDatBanTruocDAO = new DonDatBanTruoc_DAO();
        khachHangDAO = new KhachHang_DAO();
        banDAO = new Ban_DAO();
    }
    
    public static EmailReminderScheduler getInstance() {
        if (instance == null) {
            instance = new EmailReminderScheduler();
        }
        return instance;
    }
    
    public void startScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return; // Already running
        }
        
        scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkAndSendReminders();
            } catch (Exception e) {
                System.err.println("Error in reminder scheduler: " + e.getMessage());
                e.printStackTrace();
            }
        }, 1, CHECK_INTERVAL_MINUTES, TimeUnit.MINUTES);
        
        System.out.println("Email reminder scheduler started. Checking every " + CHECK_INTERVAL_MINUTES + " minutes.");
    }
    
    public void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("Email reminder scheduler stopped.");
        }
    }
    
    private void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderWindow = now.plusHours(REMIND_HOURS_BEFORE);
        
        // Calculate time range to check (e.g., reservations in the next 15-20 minutes after the 3-hour mark)
        LocalDateTime windowEnd = reminderWindow.plusMinutes(CHECK_INTERVAL_MINUTES);
        
        ArrayList<DonDatBanTruoc> dsDonDatBanTruoc = donDatBanTruocDAO.getAllDonDatBanTruoc1();
        int remindersSent = 0;
        
        for (DonDatBanTruoc donDat : dsDonDatBanTruoc) {
            LocalDateTime reservationTime = donDat.getThoiGianNhanBan();
            
            // Only consider active (not canceled) reservations
            if (donDat.getTrangThai() == 0 && // STATUS_CHUA_DEN
                reservationTime.isAfter(reminderWindow) && 
                reservationTime.isBefore(windowEnd)) {
                
                KhachHang khachHang = khachHangDAO.getKhachHangById(donDat.getKH().getMaKH());
                Ban ban = banDAO.getBanById(donDat.getBan().getMaBan());
                
                if (khachHang != null && khachHang.getEmailKH() != null && !khachHang.getEmailKH().isEmpty()) {
                    boolean sent = sendReminderEmail(khachHang, donDat, ban);
                    if (sent) {
                        remindersSent++;
                    }
                }
            }
        }
        
        if (remindersSent > 0) {
            System.out.println(now + ": Sent " + remindersSent + " reminder emails");
        }
    }
    
    private boolean sendReminderEmail(KhachHang kh, DonDatBanTruoc donDat, Ban ban) {
        String recipientEmail = kh.getEmailKH();
        String subject = "NHẮC NHỞ: Đơn đặt bàn tại Nhà hàng KAREM";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String messageText = "Kính chào Quý khách " + kh.getTenKH() + ",\n\n"
                + "Nhà hàng Karem xin nhắc nhở quý khách về lịch đặt bàn sắp tới:\n\n"
                + "- Mã đơn đặt bàn: " + donDat.getMaDonDatBanTruoc() + "\n"
                + "- Thời gian nhận bàn: " + donDat.getThoiGianNhanBan().format(formatter) + "\n"
                + "- Bàn số: " + ban.getMaBan() + "\n"
                + "- Số lượng khách: " + donDat.getSoLuongKhach() + "\n\n"
                + "Nhà hàng rất hân hạnh được phục vụ quý khách.\n"
                + "Nếu quý khách cần thay đổi thông tin, vui lòng liên hệ Hotline: 094616747.\n\n"
                + "Trân trọng,\n"
                + "Nhà hàng KAREM\n"
                + "Địa chỉ: 24/4 Trương Quốc Dung, P.8, Q.Phú Nhuận, TP.HCM\n";
        
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
            
            System.out.println("Sent reminder email to: " + recipientEmail + " for reservation: " + donDat.getMaDonDatBanTruoc());
            return true;
        } catch (MessagingException e) {
            System.err.println("Failed to send reminder email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

