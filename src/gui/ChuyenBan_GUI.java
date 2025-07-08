package gui;

import javax.swing.*;

import dao.Ban_DAO;
import dao.HoaDon_DAO;
import entity.Ban;
import entity.HoaDon;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChuyenBan_GUI extends JDialog {
	
	private static Ban_DAO ban_dao = new Ban_DAO();
	private static HoaDon_DAO hd_dao = new HoaDon_DAO();
	private AtomicBoolean isChuyenBan;

	// Thay đổi trong constructor
	public ChuyenBan_GUI(JFrame parent, String maBanHienTai, String maBanMoi, int soKhach, AtomicBoolean isChuyenBan) {
		
	    super(parent, "Chuyển Bàn", true);
	    setLayout(new BorderLayout(15, 15));
	    setSize(700, 600);
	    setLocationRelativeTo(parent);
	    
		this.isChuyenBan = isChuyenBan;
		
	    // Header Panel
	    JPanel pnlHeader = new JPanel();
	    pnlHeader.setBackground(new Color(30, 129, 191)); 
	    JLabel lblHeader = new JLabel("Chuyển Bàn");
	    lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
	    lblHeader.setForeground(Color.WHITE);
	    pnlHeader.add(lblHeader);

	    // Panel for old table information
	    JPanel pnlOldTable = new JPanel(new GridBagLayout());
	    pnlOldTable.setBackground(Color.WHITE); 
	    
	    pnlOldTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    // Add old table information
	    Ban banHienTai = ban_dao.getBan(maBanHienTai);
	    
	    addRow(pnlOldTable, gbc, "Số lượng khách hàng:", String.valueOf(soKhach));
	    addRow(pnlOldTable, gbc, "Mã bàn hiện tại:", banHienTai.getMaBan());
	    addRow(pnlOldTable, gbc, "Khu vực hiện tại:", banHienTai.getKV().getMaKV());
	    addRow(pnlOldTable, gbc, "Số lượng ghế hiện tại:", String.valueOf(banHienTai.getSoLuongGhe()));
	    

	    // Panel for new table information
	    JPanel pnlNewTable = new JPanel(new GridBagLayout());
	    pnlNewTable.setBackground(Color.WHITE);
	    pnlNewTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

	    // Add new table information
	    Ban banMoi = ban_dao.getBan(maBanMoi);

	    addRow(pnlNewTable, gbc, "Mã bàn mới:", banMoi.getMaBan());
	    addRow(pnlNewTable, gbc, "Khu vực mới:", banMoi.getKV().getMaKV());
	    addRow(pnlNewTable, gbc, "Số lượng ghế mới:", String.valueOf(banMoi.getSoLuongGhe()));

	 // Wrapper panel to hold both old and new panels
	    JPanel pnlCenter = new JPanel();
	    pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
	    pnlCenter.setBackground(Color.WHITE);

	    // Thêm tiêu đề cho từng panel
	    JPanel pnlOldWrapper = createTitledWrapper("Thông tin bàn hiện tại", pnlOldTable);
	    JPanel pnlNewWrapper = createTitledWrapper("Thông tin bàn mới", pnlNewTable);

	    pnlCenter.add(pnlOldWrapper);
	    pnlCenter.add(Box.createVerticalStrut(20)); // Khoảng cách giữa 2 panel
	    pnlCenter.add(pnlNewWrapper);


	    // Panel for buttons
	    JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    JButton btnSave = createStyledButton("Chuyển bàn", new Color(60, 179, 113));
	    JButton btnCancel = createStyledButton("Hủy", new Color(220, 20, 60));
	    pnlButtons.add(btnSave);
	    pnlButtons.add(btnCancel);

	    // Add action listeners for buttons
	    btnSave.addActionListener(e -> {
	    	HoaDon hd = hd_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
	    	hd.setBan(ban_dao.getBan(maBanMoi));
	    	hd_dao.updateHoaDon(hd);
	    	
	    	Ban banhienTai = ban_dao.getBan(maBanHienTai);
	    	banhienTai.setTrangThai(1);
	    	ban_dao.updateBan(banhienTai);
	    	
	    	Ban banmoi = ban_dao.getBan(maBanMoi);
	    	banmoi.setTrangThai(2);
	    	ban_dao.updateBan(banmoi);
	    	
	        JOptionPane.showMessageDialog(this, "Chuyển bàn thành công!");
	        isChuyenBan.set(true);
	        dispose();
	    });

	    btnCancel.addActionListener(e -> {
	    	isChuyenBan.set(false);
	    	dispose();
	    });

	    // Add panels to the dialog
	    add(pnlHeader, BorderLayout.NORTH);
	    add(pnlCenter, BorderLayout.CENTER);
	    add(pnlButtons, BorderLayout.SOUTH);
	}


	private JPanel createTitledWrapper(String title, JPanel content) {
	    JPanel wrapper = new JPanel(new BorderLayout());
	    wrapper.setBorder(BorderFactory.createTitledBorder(
	        BorderFactory.createLineBorder(new Color(10, 149, 237), 1),
	        title,
	        0,
	        0,
	        new Font("Arial", Font.BOLD, 16),
	        new Color(25, 25, 112)
	    ));
	    wrapper.setBackground(Color.WHITE);
	    wrapper.add(content, BorderLayout.CENTER);
	    return wrapper;
	}


	// Helper method to add a row with JLabel and JTextField
    private void addRow(JPanel panel, GridBagConstraints gbc, String labelText, String value) {
        JLabel label = createStyledLabel(labelText);
        JLabel lblValue = createStyledTextField(value);

        gbc.gridx = 0;
        gbc.weightx = 0.1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        panel.add(lblValue, gbc);
    }

    // Helper method to create styled labels
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    // Helper method to create styled text fields
    private JLabel createStyledTextField(String text) {
        JLabel lblField = new JLabel();
        lblField.setFont(new Font("Arial", Font.PLAIN, 14));
        lblField.setText(text);
        return lblField;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
    	connectDB.ConnectDB.getInstance().connect();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);

            ChuyenBan_GUI dialog = new ChuyenBan_GUI(frame, "B01N03", "B02P06", 5, new AtomicBoolean(false));
            dialog.setVisible(true);
        });
    }
}
