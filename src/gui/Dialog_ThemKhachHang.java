// Demo dùng 

//
//lblThemKH.addMouseListener(new MouseAdapter() {
//		    @Override
//		    public void mouseClicked(MouseEvent e) {
//		        new Dialog_ThemKhachHang(DatBanTruoc_GUI01.this).setVisible(true);
//		    }
//
//		    @Override
//		    public void mouseEntered(MouseEvent e) {
//		        lblThemKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
//		    }
//		});



package gui;

import javax.swing.*;

import dao.KhachHang_DAO;
import entity.KhachHang;

import java.awt.*;
import java.awt.event.*;

public class Dialog_ThemKhachHang extends JDialog {
    private JTextField txtTenKH_Moi, txtSDTKH_Moi, txtEmailKH_Moi;
    private JTextArea txaGhiChuKH_Moi;
    private JLabel lblLoiTenKH, lblLoiSDTKH, lblLoiEmailKH;
    private KhachHang_DAO KH_dao;

    public Dialog_ThemKhachHang(JFrame parent) {
        super(parent, "Thêm Khách Hàng Mới", true);
        KH_dao = new KhachHang_DAO(); 
        
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addComponents();
    }

    private void addComponents() {
    	add(Box.createVerticalStrut(10));
    	txtTenKH_Moi = new JTextField();
		add(createLabelTextFieldRow("Tên khách hàng:", txtTenKH_Moi, true, 20, 15, true));
		
		JPanel pnlLoiTenKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblLoiTenKH = new JLabel(" ");
        lblLoiTenKH.setForeground(Color.RED);
        lblLoiTenKH.setFont(new Font("Arial", Font.PLAIN, 11));
        pnlLoiTenKH.add(lblLoiTenKH);
        add(pnlLoiTenKH);
//        add(Box.createVerticalStrut(10));
        
        txtSDTKH_Moi = new JTextField();
		add(createLabelTextFieldRow("Số điện thoại:", txtSDTKH_Moi, true, 40, 15, true));
		
		JPanel pnlLoiSDTKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblLoiSDTKH = new JLabel(" ");
        lblLoiSDTKH.setForeground(Color.RED);
        lblLoiSDTKH.setFont(new Font("Arial", Font.PLAIN, 11));
        pnlLoiSDTKH.add(lblLoiSDTKH);
        add(pnlLoiSDTKH);
//        add(Box.createVerticalStrut(20));
        
        txtEmailKH_Moi = new JTextField();
		add(createLabelTextFieldRow("Email khách hàng:", txtEmailKH_Moi, true, 10, 15, true));

        JPanel pnlLoiEmailKH = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblLoiEmailKH = new JLabel(" ");
        lblLoiEmailKH.setForeground(Color.RED);
        lblLoiEmailKH.setFont(new Font("Arial", Font.PLAIN, 11));
        pnlLoiEmailKH.add(lblLoiEmailKH);
        add(pnlLoiEmailKH);
        
        JLabel lblGCKH_Moi;
        JPanel pnlGCKH_Moi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlGCKH_Moi.add(Box.createHorizontalStrut(20));
        pnlGCKH_Moi.add(lblGCKH_Moi = new JLabel("Ghi chú:"));
        pnlGCKH_Moi.add(Box.createHorizontalStrut(90));
        txaGhiChuKH_Moi = new JTextArea(5, 10);
        txaGhiChuKH_Moi.setLineWrap(true);
        txaGhiChuKH_Moi.setWrapStyleWord(true);
        JScrollPane scrGhiChuKH_Moi = new JScrollPane(txaGhiChuKH_Moi);
        scrGhiChuKH_Moi.setPreferredSize(new Dimension(210, 100));
        pnlGCKH_Moi.add(scrGhiChuKH_Moi);

        JPanel pnlButton = new JPanel();
        JButton btnLuuKH_Moi = new JButton("Lưu thông tin");
        JButton btnHuyKH_Moi = new JButton("Hủy bỏ");
        btnLuuKH_Moi.setFocusPainted(false);
        btnHuyKH_Moi.setFocusPainted(false);
        btnLuuKH_Moi.setBackground(new Color(13, 184, 50));
        btnHuyKH_Moi.setBackground(new Color(206, 8, 8));
        btnLuuKH_Moi.setForeground(Color.WHITE);
        btnHuyKH_Moi.setForeground(Color.WHITE);
        pnlButton.add(btnLuuKH_Moi);
        pnlButton.add(btnHuyKH_Moi);
        add(Box.createVerticalStrut(20));

        btnHuyKH_Moi.addActionListener(e -> dispose());

        btnLuuKH_Moi.addActionListener(e -> {
            if (kiemTraHopLeThongTinKH()) {
                String maKH = KH_dao.taoMaKhachHangTuDong();
                String tenKH = txtTenKH_Moi.getText().trim();
                String sdtKH = txtSDTKH_Moi.getText().trim();
                String emailKH = txtEmailKH_Moi.getText().trim();
                String ghiChuKH = txaGhiChuKH_Moi.getText().trim();
                int diemTL = 0;
                KhachHang kh = new KhachHang(maKH, tenKH, sdtKH, emailKH, ghiChuKH, diemTL);
                if (KH_dao.insertKhachHang(kh)) {
                    JOptionPane.showMessageDialog(this, "Thêm Khách Hàng thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm Khách Hàng thất bại!");
                }
                dispose();
            }
        });

        add(pnlGCKH_Moi);
        add(Box.createVerticalStrut(20));
        add(pnlButton);
    }

    private boolean kiemTraHopLeThongTinKH() {
        boolean hopLe = true;

        if (txtTenKH_Moi.getText().trim().isEmpty()) {
            lblLoiTenKH.setText("Tên khách hàng không được để trống");
            hopLe = false;
        } else {
            lblLoiTenKH.setText(" ");
        }

        if (!txtSDTKH_Moi.getText().trim().matches("\\d{10}")) {
            lblLoiSDTKH.setText("Số điện thoại phải gồm 10 chữ số");
            hopLe = false;
        } else {
            lblLoiSDTKH.setText(" ");
        }

        if (!txtEmailKH_Moi.getText().trim().matches("^\\S+@\\S+\\.\\S+$")) {
            lblLoiEmailKH.setText("Email không hợp lệ");
            hopLe = false;
        } else {
            lblLoiEmailKH.setText(" ");
        }

        return hopLe;
    }
    private JPanel createLabelTextFieldRow(String labelText, JTextField textField, boolean editable, int labelSpacing,
			int textFieldColumns, boolean hasBorder) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createHorizontalStrut(20)); // Cách trái

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(label);

		panel.add(Box.createHorizontalStrut(labelSpacing)); // Khoảng cách giữa label và textField

		textField.setColumns(textFieldColumns);
		textField.setFont(label.getFont());
		textField.setEditable(editable);
		if (!hasBorder) {
			textField.setBorder(null); // Xoá viền nếu cần
			textField.setCaretColor(null);
			textField.setCaretColor(textField.getBackground());
		}
		panel.add(textField);

		return panel;
	}
    
}


