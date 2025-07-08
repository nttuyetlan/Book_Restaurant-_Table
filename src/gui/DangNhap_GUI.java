package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class DangNhap_GUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkRememberMe;
	private JButton btnLogin;
    private static final String BACKGROUND_PATH = "resource/background/background.jpg";
    private static final Color PANEL_BACKGROUND = new Color(20, 30, 48, 220); // Darker and semi-transparent
    private static final Color BUTTON_COLOR = new Color(0, 102, 204); // Brighter blue
    private static final Color BUTTON_LOGIN_COLOR = new Color(100, 180, 255); // Brighter blue

    private static final Color BUTTON_HOVER_COLOR = new Color(0, 122, 244); // Hover effect color
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font LINK_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    public DangNhap_GUI() {
        setTitle("Đăng nhập hệ thống");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(taoBackgroundPanel());

        JPanel loginPanel = taoLoginPanel();
        
        setLayout(new GridBagLayout());
        add(loginPanel);
     // Gán phím Enter cho nút Đăng nhập
        getRootPane().setDefaultButton(btnLogin);

        // Gán phím Esc để thoát ứng dụng
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
        getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(DangNhap_GUI.this,
                        "Bạn có chắc chắn muốn thoát?", "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        setVisible(true);
    }
    
    private JPanel taoBackgroundPanel() {
        // Tạo panel nền với ảnh background
        final ImageIcon backgroundImage = new ImageIcon(BACKGROUND_PATH);
        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 0, 40, 100),
                    0, getHeight(), new Color(0, 0, 40, 180)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        return backgroundPanel;
    }
    
    private JPanel taoLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(PANEL_BACKGROUND);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, new Color(255, 255, 255, 30)), 
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        loginPanel.setPreferredSize(new Dimension(400, 450));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel mainTitleLabel = new JLabel("HỆ THỐNG QUẢN LÝ", JLabel.CENTER);
        mainTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainTitleLabel.setForeground(TEXT_COLOR);
        mainTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(mainTitleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel subTitleLabel = new JLabel("LOGIN", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subTitleLabel.setForeground(TEXT_COLOR);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(subTitleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(LABEL_FONT);
        lblUsername.setForeground(TEXT_COLOR);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setCaretColor(Color.WHITE);
        txtUsername.setBackground(new Color(40, 44, 52));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 63, 68), 1, true),
            BorderFactory.createEmptyBorder(10, 5, 10, 10)
        ));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.setLayout(new BorderLayout(5, 0));

        JLabel usernameIcon = new JLabel();
        ImageIcon userNameIcon = new ImageIcon("resource/icons/user (1).png"); 
        usernameIcon.setIcon(userNameIcon); // Set the icon to the JLabel
        usernameIcon.setForeground(TEXT_COLOR);
        usernamePanel.add(usernameIcon, BorderLayout.WEST);
        usernamePanel.add(txtUsername, BorderLayout.CENTER);

        formPanel.add(lblUsername);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(usernamePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(LABEL_FONT);
        lblPassword.setForeground(TEXT_COLOR);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBackground(new Color(40, 44, 52));
        txtPassword.setEchoChar('•');
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 63, 68), 1, true),
            BorderFactory.createEmptyBorder(10, 5, 10, 10)
        ));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new BorderLayout(5, 0));

        JLabel passwordIcon = new JLabel();
        ImageIcon passworDIcon = new ImageIcon("resource//icons//user (1).png"); 
        passwordIcon.setIcon(passworDIcon);
        passwordIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordIcon.setForeground(TEXT_COLOR);
        passwordPanel.add(passwordIcon, BorderLayout.WEST);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);

        JCheckBox showPasswordCheckbox = new JCheckBox("Hiện mật khẩu");
        showPasswordCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckbox.setForeground(Color.LIGHT_GRAY);
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.setFocusPainted(false);
        showPasswordCheckbox.addActionListener(e -> {
            txtPassword.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '•');
        });

        formPanel.add(lblPassword);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(showPasswordCheckbox);

        loginPanel.add(formPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(BUTTON_FONT);
        btnLogin.setBackground(BUTTON_LOGIN_COLOR);
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(BUTTON_COLOR);
            }
        });

        btnLogin.addActionListener(e -> xuLyLogin());
        loginPanel.add(btnLogin);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Forgot password
        JPanel forgotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        forgotPanel.setOpaque(false);
        JLabel forgotText = new JLabel("Quên mật khẩu?");
        forgotText.setForeground(Color.WHITE);
        forgotText.setFont(LINK_FONT);

        JLabel restoreLabel = new JLabel("KHÔI PHỤC NGAY");
        restoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        restoreLabel.setForeground(new Color(100, 180, 255));
        restoreLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        restoreLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mở giao diện khôi phục mật khẩu
                SwingUtilities.invokeLater(() -> {
                    KhoiPhucMatKhau_GUI khoiPhucGUI = new KhoiPhucMatKhau_GUI();
                    khoiPhucGUI.setVisible(true);
                });

                // Nếu bạn muốn ẩn cửa sổ đăng nhập hiện tại:
                // ((JFrame) SwingUtilities.getWindowAncestor(restoreLabel)).dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                restoreLabel.setText("<html><u>KHÔI PHỤC NGAY</u></html>");
                forgotText.setForeground(new Color(100, 180, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                restoreLabel.setText("KHÔI PHỤC NGAY");
                forgotText.setForeground(Color.WHITE);
            }
        });

        forgotPanel.add(forgotText);
        forgotPanel.add(restoreLabel);
        loginPanel.add(forgotPanel);
        loginPanel.add(Box.createVerticalGlue());

        // Footer
        JLabel footerLabel = new JLabel("© " + Calendar.getInstance().get(Calendar.YEAR) +
                " Restaurant System Management. All rights reserved | Design by N13", JLabel.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(180, 180, 180));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(footerLabel);

        return loginPanel;
    }
    
    private void xuLyLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập tên đăng nhập và mật khẩu!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (ConnectDB.getConnection() == null) {
                ConnectDB.getInstance().connect();
            }

            TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO(); 
            TaiKhoan authenticatedAccount = taiKhoanDAO.checkLogin(username, password);
            NhanVien loggedInUser = null;

            if (authenticatedAccount != null) {
                // Lấy maNV từ TaiKhoan và lấy thông tin NhanVien đầy đủ
                String maNV = authenticatedAccount.getNhanVien().getMaNV();
                loggedInUser = nhanVienDAO.getNhanVienById(maNV);
                if (loggedInUser == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể lấy thông tin nhân viên. Vui lòng thử lại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this,
                        "Đăng nhập thành công!\nChào mừng quay trở lại " + username + "!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Phân biệt Quản lý (QL) và Nhân viên (NV)
                final NhanVien userToPass = loggedInUser;
                if (maNV.startsWith("QL")) {
                    // Mở giao diện Quản lý (QuanLyMonAn_GUI)
                    SwingUtilities.invokeLater(() -> {
                        try {
                            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                            MenuQuanLy_GUI quanLyForm = new MenuQuanLy_GUI(userToPass);
                            SwingUtilities.updateComponentTreeUI(quanLyForm);
                            quanLyForm.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this,
                                    "Lỗi khi mở giao diện quản lý: " + ex.getMessage(),
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                } else if (maNV.startsWith("NV")) {
                    // Mở giao diện Nhân viên (ThongKeCaLamViecDau_GUI)
                    SwingUtilities.invokeLater(() -> {
                        try {
                            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                            MenuNhanVien_GUI datBanForm = new MenuNhanVien_GUI(userToPass);
                            SwingUtilities.updateComponentTreeUI(datBanForm);
                            datBanForm.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this,
                                    "Lỗi khi mở giao diện nhân viên: " + ex.getMessage(),
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Mã nhân viên không hợp lệ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.dispose();
            } else {
                ArrayList<TaiKhoan> dsTaiKhoan = taiKhoanDAO.getAllTK();
                boolean isAuthenticated = false;
                TaiKhoan account = null;

                for (TaiKhoan tk : dsTaiKhoan) {
                    if (tk.getTenTaiKhoan().equals(username)) {
                        System.out.println("Tên TK: " + tk.getTenTaiKhoan());
                        System.out.println("Mật khẩu lưu: " + tk.getMatKhau());
                        System.out.println("Mật khẩu hash từ input: " + taiKhoanDAO.hashPasswordSHA512(password));
                        
                        if (taiKhoanDAO.checkPassword(password, tk.getMatKhau())) {
                            isAuthenticated = true;
                            account = tk;
                            break;
                        }
                    }
                }

                if (isAuthenticated) {
                    String maNV = account.getNhanVien().getMaNV();
                    loggedInUser = nhanVienDAO.getNhanVienById(maNV);
                    if (loggedInUser == null) {
                        JOptionPane.showMessageDialog(this,
                                "Không thể lấy thông tin nhân viên. Vui lòng thử lại!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(this,
                            "Đăng nhập thành công!\nChào mừng quay trở lại " + username + "!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Phân biệt Quản lý (QL) và Nhân viên (NV)
                    final NhanVien userToPass = loggedInUser;
                    if (maNV.startsWith("QL")) {
                        // Mở giao diện Quản lý (QuanLyMonAn_GUI)
                        SwingUtilities.invokeLater(() -> {
                            try {
                                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                                MenuQuanLy_GUI quanLyForm = new MenuQuanLy_GUI(userToPass);
                                SwingUtilities.updateComponentTreeUI(quanLyForm);
                                quanLyForm.setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this,
                                        "Lỗi khi mở giao diện quản lý: " + ex.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    } else if (maNV.startsWith("NV")) {
                        // Mở giao diện Nhân viên (ThongKeCaLamViecDau_GUI)
                        SwingUtilities.invokeLater(() -> {
                            try {
                                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                                MenuNhanVien_GUI datBanForm = new MenuNhanVien_GUI(userToPass);
                                SwingUtilities.updateComponentTreeUI(datBanForm);
                                datBanForm.setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this,
                                        "Lỗi khi mở giao diện nhân viên: " + ex.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Mã nhân viên không hợp lệ!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Sai tài khoản hoặc mật khẩu!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi đăng nhập: " + e.getMessage(),
                    "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    
    public static class TextPrompt extends JLabel implements FocusListener {
        private JTextComponent component;
        private boolean showPromptOnce;
        private int focusLost;

        public TextPrompt(String text, JTextComponent component) {
            this(text, component, JLabel.LEADING);
        }

        public TextPrompt(String text, JTextComponent component, int horizontalAlignment) {
            this.component = component;
            setOpaque(false);
            setText(text);
            setFont(component.getFont());
            setForeground(component.getForeground());
            setHorizontalAlignment(horizontalAlignment);

            component.addFocusListener(this);
            component.add(this);
            component.setLayout(new BorderLayout());
        }

        public void changeAlpha(float alpha) {
            setForeground(new Color(getForeground().getRed(),
                                    getForeground().getGreen(),
                                    getForeground().getBlue(),
                                    (int)(alpha * 255)));
        }

        public void changeStyle(int style) {
            setFont(getFont().deriveFont(style));
        }

        public void focusGained(FocusEvent e) {
            checkForPrompt();
        }

        public void focusLost(FocusEvent e) {
            focusLost++;
            if (showPromptOnce && focusLost > 1) return;
            checkForPrompt();
        }

        private void checkForPrompt() {
            if (component.getText().length() > 0) {
                setVisible(false);
                return;
            }
            setVisible(true);
        }
    }

    static class SplashScreen extends JWindow {
        private JProgressBar progressBar;
        private JLabel statusLabel;
        private ImageIcon backgroundImage;
        private final String[] loadingSteps = {
            "Khởi tạo hệ thống...",
            "Đang kết nối cơ sở dữ liệu...",
            "Đang tải dữ liệu...",
            "Đang cài đặt giao diện...",
            "Đang kiểm tra bản cập nhật...",
            "Hoàn tất, sẵn sàng khởi động..."
        };

        public SplashScreen() {
            backgroundImage = new ImageIcon(BACKGROUND_PATH);
            Image img = backgroundImage.getImage();
            // Điều chỉnh kích thước ảnh để lớn và sắc nét hơn
            img = img.getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            backgroundImage = new ImageIcon(img);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            
            JLabel splashLabel = new JLabel(backgroundImage);
            splashLabel.setLayout(new BorderLayout());
            
            // Thêm tên ứng dụng vào splash
            JLabel appNameLabel = new JLabel("", JLabel.CENTER);
            appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            appNameLabel.setForeground(Color.ORANGE);
            appNameLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
            splashLabel.add(appNameLabel, BorderLayout.NORTH);
            
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setBackground(new Color(0, 0, 0, 150));
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            
            // Thanh trạng thái
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
            progressBar.setForeground(BUTTON_COLOR);
            progressBar.setBackground(Color.WHITE);
            progressBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            
            // Label hiển thị trạng thái tải
            statusLabel = new JLabel("Đang khởi động hệ thống...");
            statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            statusLabel.setForeground(Color.WHITE);
            
            bottomPanel.add(statusLabel, BorderLayout.NORTH);
            bottomPanel.add(progressBar, BorderLayout.CENTER);
            
            // Thêm phiên bản
            JLabel versionLabel = new JLabel("Phiên bản 1.0.0", JLabel.RIGHT);
            versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            versionLabel.setForeground(Color.WHITE);
            bottomPanel.add(versionLabel, BorderLayout.SOUTH);
            
            splashLabel.add(bottomPanel, BorderLayout.SOUTH);
            mainPanel.add(splashLabel, BorderLayout.CENTER);
            
            getContentPane().add(mainPanel);
            setSize(800, 500);
            setLocationRelativeTo(null);
        }

        public void showSplash() {
            setVisible(true);
            
            int stepSize = 100 / loadingSteps.length;
            int delay = 800; 
            
            // Mô phỏng quá trình tải thực tế
            new Thread(() -> {
                try {
                    for (int step = 0; step < loadingSteps.length; step++) {
                        final int currentStep = step;
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText(loadingSteps[currentStep]);
                        });
                        
                        int startProgress = step * stepSize;
                        int endProgress = (step + 1) * stepSize;
                        
                        for (int i = startProgress; i < endProgress; i++) {
                            final int progress = i;
                            SwingUtilities.invokeLater(() -> {
                                progressBar.setValue(progress);
                                progressBar.setString(progress + "%");
                            });
                            Thread.sleep(30);
                        }
                        if (step < loadingSteps.length - 1) {
                            Thread.sleep(delay);
                        }
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(100);
                        progressBar.setString("100%");
                    });
                    
                    Thread.sleep(1000);
                    dispose(); // Tắt splash
                    SwingUtilities.invokeLater(() -> new DangNhap_GUI());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

 
    
//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        SwingUtilities.invokeLater(() -> {
//            SplashScreen splash = new SplashScreen();
//            splash.showSplash();
//        });
//    }
}