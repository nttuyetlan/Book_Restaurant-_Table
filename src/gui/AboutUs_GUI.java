package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.NhanVien;

public class AboutUs_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private NhanVien currentUser;
    
    // Updated color scheme for better aesthetics
    private final Color PRIMARY_COLOR = new Color(25, 118, 210);    // Deeper blue
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(56, 142, 60);      // Richer green
    private final Color TEXT_COLOR = new Color(33, 37, 41);
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final Color CARD_BORDER = new Color(224, 224, 224);

    public AboutUs_GUI() {
        this(null);
    }
        
    public AboutUs_GUI(NhanVien loggedInUser) {
//        setTitle("Về chúng tôi - Nhà hàng Karem");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setSize(900, 700);  // Slightly larger for better content display
//        setLocationRelativeTo(null);
        this.currentUser = loggedInUser;
        
        // Set up the content first
//        initUI();
//        
//        // Refresh the layout after adding menu bar
//        revalidate();
//        repaint();
//        
//        setVisible(true);
    }

    public JPanel initUI() {
        
        // Main panel with improved padding
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Header with improved styling
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panels with better spacing and layout
        JPanel contentContainer = new JPanel(new GridLayout(3, 1, 0, 25));
        contentContainer.setBackground(BACKGROUND_COLOR);
        
        JPanel appInfoPanel = createAppInfoPanel();
        JPanel teamPanel = createTeamPanel();
        JPanel businessPanel = createBusinessPanel();
        
        contentContainer.add(appInfoPanel);
        contentContainer.add(teamPanel);
        contentContainer.add(businessPanel);
        
        // Wrap content in a scroll pane for better usability
        JScrollPane scrollPane = new JScrollPane(contentContainer);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
//        add(contentPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
        
        JLabel titleLabel = new JLabel("VỀ CHÚNG TÔI - NHÀ HÀNG KAREM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel subtitleLabel = new JLabel("Hệ thống quản lý đặt bàn và phục vụ nhà hàng");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(new Color(220, 220, 220));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        headerPanel.add(separator, BorderLayout.SOUTH);
        
        return headerPanel;
    }

    private JPanel createAppInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);

        // Reduce bottom padding from 20 to 5
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(20, 20, 5, 20)));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_COLOR);

        ImageIcon infoIcon = new ImageIcon(getClass().getResource(""));
        if (infoIcon.getIconWidth() == -1) {
            JLabel title = new JLabel("Về Đề tài - Ứng dụng Quản lý Đặt bàn nhà hàng Karem");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);
            headerPanel.add(title);
        } else {
            Image scaledImage = infoIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

            JLabel title = new JLabel("Về Ứng dụng Quản lý Đặt bàn Karem");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);

            headerPanel.add(iconLabel);
            headerPanel.add(Box.createHorizontalStrut(10));
            headerPanel.add(title);
        }

        JTextArea description = new JTextArea(
            "Ứng dụng Quản lý Đặt bàn Nhà hàng Karem là một giải pháp phần mềm được Nhóm 13 thiết kế để hỗ trợ nhà hàng Karem quản lý hiệu quả các hoạt động đặt bàn, phục vụ khách hàng và vận hành nội bộ. \n\n" +
            "Mục tiêu của ứng dụng là tối ưu hóa quy trình đặt bàn trước và đặt bàn trực tiếp , quản lý danh sách món ăn, cũng như cung cấp báo cáo chi tiết để hỗ trợ quản lý. \n\n" +
            "Phát triển trong khuôn khổ báo cáo môn học, ứng dụng thể hiện nỗ lực của nhóm trong việc áp dụng công nghệ để nâng cao trải nghiệm khách hàng và hiệu suất làm việc."
        );
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(TEXT_COLOR);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setBorder(new EmptyBorder(10, 0, 0, 0));

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(description, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));


        return panel;
    }


    private JPanel createTeamPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Panel header with icon
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        ImageIcon teamIcon = new ImageIcon(getClass().getResource(""));
        if (teamIcon.getIconWidth() == -1) {
            // Fallback if icon not found
            JLabel title = new JLabel("Đội ngũ Phát triển");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);
            headerPanel.add(title);
        } else {
            Image scaledImage = teamIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            
            JLabel title = new JLabel("Đội ngũ Phát triển");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);
            
            headerPanel.add(iconLabel);
            headerPanel.add(Box.createHorizontalStrut(10));
            headerPanel.add(title);
        }
        
        // Modern team panel with enhanced styling
        JPanel teamContainer = new JPanel();
        teamContainer.setLayout(new BoxLayout(teamContainer, BoxLayout.Y_AXIS));
        teamContainer.setBackground(BACKGROUND_COLOR);
        teamContainer.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        String[][] members = {
            {"Phan Tôn Lộc Nguyên", "Facilitator"},
            {"Nguyễn Thị Tuyết Lan", "Time Keeper"},
            {"Đỗ Minh Thư", "Note taker"},
            {"Trần Minh Tú", "Reporter"}
        };
        
        for (String[] member : members) {
            JPanel memberPanel = new JPanel(new BorderLayout(10, 0));
            memberPanel.setBackground(BACKGROUND_COLOR);
            memberPanel.setBorder(new EmptyBorder(8, 5, 8, 5));
            
            JLabel nameLabel = new JLabel("• " + member[0]);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            nameLabel.setForeground(TEXT_COLOR);
            
            JLabel roleLabel = new JLabel(member[1]);
            roleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            roleLabel.setForeground(new Color(100, 100, 100));
            
            memberPanel.add(nameLabel, BorderLayout.WEST);
            memberPanel.add(roleLabel, BorderLayout.EAST);
            
            teamContainer.add(memberPanel);
            
            // Add separator except for the last item
            if (!member.equals(members[members.length - 1])) {
                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                separator.setForeground(new Color(240, 240, 240));
                teamContainer.add(separator);
            }
        }
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(teamContainer, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createBusinessPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Panel header with icon
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        ImageIcon businessIcon = new ImageIcon(getClass().getResource(""));
        if (businessIcon.getIconWidth() == -1) {
            // Fallback if icon not found
            JLabel title = new JLabel("Sơ lược Các Nghiệp vụ");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);
            headerPanel.add(title);
        } else {
            Image scaledImage = businessIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            
            JLabel title = new JLabel("Sơ lược Các Nghiệp vụ");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(PRIMARY_COLOR);
            
            headerPanel.add(iconLabel);
            headerPanel.add(Box.createHorizontalStrut(10));
            headerPanel.add(title);
        }
        
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBackground(BACKGROUND_COLOR);
        featuresPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        String[][] features = {
            {"Quản lý Đặt bàn", "Hỗ trợ đặt bàn trực tiếp và đặt bàn trước, cho phép khách hàng chọn thời gian và số lượng người."},
            {"Quản lý Món ăn", "Thêm, chỉnh sửa, bật/tắt trạng thái món ăn (Đang phục vụ/Hết món), và quản lý danh mục."},
            {"Quản lý Nhân viên", "Quản lý thông tin nhân viên phục vụ."},
            {"Quản lý Khách hàng", "Quản lý thông tin khách hàng."},
            {"Quản lý Khu vực", "Chức năng hỗ trợ cho nhà hàng về việc thêm khu vực, hoạt tùy chỉnh trạng thái khu vực Hoạt động hoặc bảo trì."},
            {"Báo cáo và Thống kê", "Cung cấp báo cáo doanh thu, số lượng đặt bàn, và tình trạng món ăn."}
        };
        
        for (int i = 0; i < features.length; i++) {
            JPanel featurePanel = new JPanel(new BorderLayout(15, 5));
            featurePanel.setBackground(BACKGROUND_COLOR);
            featurePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
            
            JLabel numberLabel = new JLabel((i+1) + ".");
            numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            numberLabel.setForeground(PRIMARY_COLOR);
            numberLabel.setPreferredSize(new Dimension(25, 20));
            
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBackground(BACKGROUND_COLOR);
            
            JLabel titleLabel = new JLabel(features[i][0]);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titleLabel.setForeground(TEXT_COLOR);
            
            JLabel descLabel = new JLabel(features[i][1]);
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            descLabel.setForeground(new Color(80, 80, 80));
            
            textPanel.add(titleLabel, BorderLayout.NORTH);
            textPanel.add(descLabel, BorderLayout.CENTER);
            
            featurePanel.add(numberLabel, BorderLayout.WEST);
            featurePanel.add(textPanel, BorderLayout.CENTER);
            
            featuresPanel.add(featurePanel);
            
            // Add separator except for the last item
            if (i < features.length - 1) {
                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                separator.setForeground(new Color(240, 240, 240));
                featuresPanel.add(separator);
            }
        }
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(featuresPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(darken(bgColor, 0.1f));
                }
            }

            public void mouseExited(MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor);
                }
            }
        });
        
        return button;
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new AboutUs_GUI());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle menu actions here
        String actionCommand = e.getActionCommand();
        if (actionCommand != null) {
            System.out.println("Menu action triggered: " + actionCommand);
            // Add your menu handling code here
        }
    }
}
