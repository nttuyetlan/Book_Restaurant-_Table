package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.MonAn_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.MonAn;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TachBan_GUI extends JDialog {
    private DefaultTableModel model;
	private JTable tblModel;
	private AtomicBoolean isTachBan;
	private static Ban_DAO ban_dao = new Ban_DAO();
	private static ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private static MonAn_DAO ma_dao = new MonAn_DAO();
	private static HoaDon_DAO hd_dao = new HoaDon_DAO();

    public TachBan_GUI(Frame parent, String maBanHienTai, String maBanMoi, AtomicBoolean isTachBan) {
    	super(parent, "Tách bàn", true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
		
        this.isTachBan = isTachBan;
        
     // ===== Panel bọc ngoài cùng (đặt vào BorderLayout.NORTH) =====
        JPanel panelWrapper = new JPanel();
        panelWrapper.setLayout(new BorderLayout());
        panelWrapper.setBackground(new Color(30, 129, 191));

        // ===== Panel tiêu đề riêng =====
        JLabel lblTitle = new JLabel("TÁCH BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 129, 191));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelWrapper.add(lblTitle, BorderLayout.NORTH);

        // ===== Panel thông tin số khách riêng =====
        JPanel guestInfoPanel = new JPanel();
        guestInfoPanel.setLayout(new BoxLayout(guestInfoPanel, BoxLayout.X_AXIS));
        guestInfoPanel.setBackground(Color.WHITE);
        guestInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

     // Label hiển thị số khách hiện có
        JLabel lblCurrentGuests = new JLabel("Số khách hiện có: ");
        lblCurrentGuests.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCurrentGuests.setForeground(Color.BLACK);

        JLabel lblCurrentGuestsValue = new JLabel();
        lblCurrentGuestsValue.setFont(new Font("Arial", Font.BOLD, 14));
        lblCurrentGuestsValue.setForeground(Color.RED);

        // Label + TextField cho bàn cũ
        JLabel lblOldGuests = new JLabel("Số khách bàn cũ: ");
        lblOldGuests.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOldGuests.setForeground(Color.BLACK);

        JTextField txtOldGuests = new JTextField();
        txtOldGuests.setFont(new Font("Arial", Font.PLAIN, 14));
        txtOldGuests.setPreferredSize(new Dimension(50, 25));
        txtOldGuests.setMaximumSize(new Dimension(60, 25));

        // Label + TextField cho bàn mới
        JLabel lblNewGuests = new JLabel("Số khách bàn mới: ");
        lblNewGuests.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNewGuests.setForeground(Color.BLACK);

        JTextField txtNewGuests = new JTextField();
        txtNewGuests.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNewGuests.setPreferredSize(new Dimension(50, 25));
        txtNewGuests.setMaximumSize(new Dimension(60, 25));

        // Thêm các thành phần vào panel thông tin khách
        guestInfoPanel.add(lblCurrentGuests);
        guestInfoPanel.add(lblCurrentGuestsValue);
        guestInfoPanel.add(Box.createHorizontalStrut(80));
        guestInfoPanel.add(lblOldGuests);
        guestInfoPanel.add(txtOldGuests);
        guestInfoPanel.add(Box.createHorizontalGlue());
        guestInfoPanel.add(lblNewGuests);
        guestInfoPanel.add(txtNewGuests);

        panelWrapper.add(guestInfoPanel, BorderLayout.CENTER);
        
        add(panelWrapper, BorderLayout.NORTH);

        String[] columnNames = {"Tên món gọi", "Bàn cũ", "Bàn mới"};

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Cột "Số lượng" có thể chỉnh sửa
            }
        };
        
        model.setRowCount(0); 
        
        // Thêm dữ liệu mẫu vào bảng
        HoaDon hd = hd_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
        
        lblCurrentGuestsValue.setText(String.valueOf(hd.getSoKhach()));
        
        ArrayList<ChiTietHoaDon> listCTHD = cthd_dao.getChiTietHoaDonTheoMaHD(hd.getMaHD());
	    for (ChiTietHoaDon cthd : listCTHD) {
	        MonAn ma = ma_dao.timTenMonAnVaGiaMonAnTheoMa(cthd.getMonAn().getMaMonAn());

	        Object[] rowData = {
	            ma.getTenMonAn(),
	            cthd.getSoLuong(),               
	            0
	        };
	        model.addRow(rowData);
	    }

        tblModel = new JTable(model);
        styleTable(tblModel);

        // Đặt renderer và editor cho cột "Số lượng"
        tblModel.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        tblModel.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tblModel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton exitButton = createStyledButton("Thoát", new Color(255, 99, 71));
        JButton proceedButton = createStyledButton("Xác nhận tách", new Color(60, 179, 113));

        buttonPanel.add(exitButton);
        buttonPanel.add(proceedButton);
        add(buttonPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(e -> {
			isTachBan.set(false);
			dispose();
		});

        proceedButton.addActionListener(e -> {
        	if(txtOldGuests.getText().isEmpty() || txtNewGuests.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số khách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(Integer.parseInt(txtOldGuests.getText()) <= 0 || Integer.parseInt(txtNewGuests.getText()) <= 0 ) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số khách bàn cũ và mới lớn hơn 0.");
				return;
			}else if(Integer.parseInt(txtOldGuests.getText()) >= Integer.parseInt(lblCurrentGuestsValue.getText()) ||  Integer.parseInt(txtNewGuests.getText()) >= Integer.parseInt(lblCurrentGuestsValue.getText())) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số khách của mỗi bàn không quá số khách hiện tại");
				return;
			}
        	
        	// Cập nhật hóa đơn cho bàn cũ
        	HoaDon hdCu = hd_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
        	hdCu.setSoKhach(Integer.parseInt(txtOldGuests.getText()));
        	int[] tongMon = demSoLuongMon();
        	int soLuongMonBanCu = tongMon[0];
        	int soLuongMonBanMoi = tongMon[1];
			LocalDateTime thoiGianDuKien = modelDuDoanThoiGianAn.duDoanThoiGian(hdCu.getSoKhach(), soLuongMonBanCu, hdCu.getThoiGianTaoDon());
			hdCu.setThoiGianThanhToan(thoiGianDuKien);
			hd_dao.updateHoaDon(hdCu);
			
			// Lấy ghi chú của các chi tiết hóa đơn
			Map<String, String> ghiChuMap = new HashMap<>();
        	ArrayList<ChiTietHoaDon> dsCTHDCu = cthd_dao.getChiTietHoaDonTheoMaHD(hdCu.getMaHD());
        	for (ChiTietHoaDon cthd : dsCTHDCu) {
        	    ghiChuMap.put(cthd.getMonAn().getMaMonAn(), cthd.getGhiChu());
        	}
			
			// Cập nhật chi tiết hóa đơn cho bàn cũ
        	for (int i = 0; i < model.getRowCount(); i++) {
        	    String tenMon = (String) model.getValueAt(i, 0);
        	    int soLuongBanCu = (int) model.getValueAt(i, 1);
        	    
        	    String maMonAn = ma_dao.timMaMonAnTheoTen(tenMon);
        	    ChiTietHoaDon cthdCu = cthd_dao.getChiTietTheoMaHDVaMaMon(hdCu.getMaHD(), maMonAn);
        	    
        	    if (cthdCu != null) {
        	    	if (soLuongBanCu == 0) {
        	            cthd_dao.deleteChiTietTheoMaHDVaMaMon(hdCu.getMaHD(), maMonAn);
        	        } else {
        	            cthdCu.setSoLuong(soLuongBanCu);
        	            cthd_dao.updateChiTietHoaDon(cthdCu);
        	        }
        	    }
        	}

        	// Cập nhật hóa đơn cho bàn mới
        	HoaDon hdMoi = hd_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
        	hdMoi.setMaHD(hd_dao.taoMaHoaDonTuDong());
        	hdMoi.setBan(ban_dao.getBan(maBanMoi));
        	hdMoi.setSoKhach(Integer.parseInt(txtNewGuests.getText()));
        	LocalDateTime thoiGianDuKien1 = modelDuDoanThoiGianAn.duDoanThoiGian(hdMoi.getSoKhach(), soLuongMonBanMoi, hdMoi.getThoiGianTaoDon());
        	hdMoi.setThoiGianThanhToan(thoiGianDuKien1);
        	hd_dao.insertHoaDon(hdMoi);
        	
        	// Cập nhật chi tiết hóa đơn cho bàn mới
        	for (int i = 0; i < model.getRowCount(); i++) {
			    String tenMon = (String) model.getValueAt(i, 0);
			    int soLuongBanMoi = (int) model.getValueAt(i, 2);
			    
			    if (soLuongBanMoi > 0) {
		            String maMonAn = ma_dao.timMaMonAnTheoTen(tenMon);
		            MonAn monAn = ma_dao.timTenMonAnVaGiaMonAnTheoMa(maMonAn);
		            String ghiChu = ghiChuMap.getOrDefault(maMonAn, "");

		            ChiTietHoaDon cthdMoi = new ChiTietHoaDon(hdMoi, monAn, soLuongBanMoi, ghiChu);
		            cthd_dao.insertChiTietHoaDon(cthdMoi);
		        }
			}


        	
        	Ban banmoi = ban_dao.getBan(maBanMoi);
        	banmoi.setTrangThai(2);
        	ban_dao.updateBan(banmoi);
        	JOptionPane.showMessageDialog(this, "Đã tách bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        	isTachBan.set(true);
        	System.out.println("Tách bàn thành công, isTachBan = true");

        	dispose();
        });
    }
    
    private int[] demSoLuongMon() {
        int tongMonBanCu = 0;
        int tongMonBanMoi = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            tongMonBanCu += (int) model.getValueAt(i, 1); 
            tongMonBanMoi += (int) model.getValueAt(i, 2); 
        }

        return new int[] {tongMonBanCu, tongMonBanMoi};
    }


    private void styleTable(JTable tbl) {
    	tbl.setFont(new Font("Arial", Font.PLAIN, 14));
    	tbl.setRowHeight(45);
    	tbl.setShowGrid(false); // Tắt đường kẻ giữa các ô
    	tbl.setIntercellSpacing(new Dimension(0, 5)); // Tạo khoảng cách giữa các dòng
    	tbl.setSelectionBackground(new Color(255, 255, 255)); // Không đổi màu nền khi chọn dòng
    	tbl.setSelectionForeground(Color.BLACK); // Dòng không có màu nền
    	tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    	tbl.getTableHeader().setBackground(new Color(70, 130, 180));
        tbl.getTableHeader().setForeground(Color.WHITE);
        tbl.getTableHeader().setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền header

        tbl.setCellSelectionEnabled(true);
        tbl.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền cho bảng

        tbl.getColumnModel().getColumn(0).setPreferredWidth(400);  // Cột "Tên món gọi" rộng ra
        tbl.getColumnModel().getColumn(1).setPreferredWidth(40);  // Cột "Số lượng" nhỏ lại
        tbl.getColumnModel().getColumn(2).setPreferredWidth(40);  // Cột "Số lượng bàn mới" nhỏ lại

        // Căn giữa giá trị của cột có index = 2
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }


    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Renderer cho cột 1 (Số lượng) để có nút cộng trừ và căn giữa giá trị
    class ButtonRenderer implements TableCellRenderer {
        private JButton minusButton;
        private JButton plusButton;
        private JLabel quantityLabel;

        public ButtonRenderer() {
            // Layout để căn giữa
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
            
            ImageIcon  iconCongSL = new ImageIcon("resource/icon/Tang.png");
            ImageIcon  iconTruSL = new ImageIcon("resource/icon/Giam.png");
            
            minusButton = new JButton(iconTruSL);
            plusButton = new JButton(iconCongSL);
            quantityLabel = new JLabel("0", SwingConstants.CENTER);

            styleButton(minusButton);
            styleButton(plusButton);
            quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            panel.add(minusButton);
            panel.add(quantityLabel);
            panel.add(plusButton);
        }

        private void styleButton(JButton button) {
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(25, 25));
//            button.setFocusPainted(false);
            button.setBorderPainted(false); 
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Trả về panel chứa các nút cộng/trừ và giá trị
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

            quantityLabel.setText(value.toString());
            panel.add(minusButton);
            panel.add(quantityLabel);
            panel.add(plusButton);

            // Nếu dòng được chọn, thay đổi màu nền
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }

            return panel;
        }
    }

    // Editor cho cột 1 (Số lượng) để xử lý nút cộng và trừ
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton minusButton;
        private JButton plusButton;
        private JLabel quantityLabel;
        private int value;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            
            ImageIcon  iconCongSL = new ImageIcon("resource/icon/Tang.png");
            ImageIcon  iconTruSL = new ImageIcon("resource/icon/Giam.png");
            
            minusButton = new JButton(iconTruSL);
            plusButton = new JButton(iconCongSL);
            quantityLabel = new JLabel("0", SwingConstants.CENTER);

            styleButton(minusButton);
            styleButton(plusButton);
            quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            panel.add(minusButton);
            panel.add(quantityLabel);
            panel.add(plusButton);
            

            minusButton.addActionListener(e -> {
                int row = tblModel.getEditingRow();
                int currentOld = (int) model.getValueAt(row, 1); // Value in "Bàn cũ"
                int currentNew = (int) model.getValueAt(row, 2); // Value in "Bàn mới"

                if (currentOld > 0) {
                    value--; // Decrease the editor's value
                    quantityLabel.setText(String.valueOf(value));
                    model.setValueAt(currentOld - 1, row, 1); // Decrease "Bàn cũ"
                    model.setValueAt(currentNew + 1, row, 2); // Increase "Bàn mới"
                }
            });

            plusButton.addActionListener(e -> {
                int row = tblModel.getEditingRow();
                int currentOld = (int) model.getValueAt(row, 1); // Value in "Bàn cũ"
                int currentNew = (int) model.getValueAt(row, 2); // Value in "Bàn mới"

                if (currentNew > 0) {
                    value++; // Increase the editor's value
                    quantityLabel.setText(String.valueOf(value));
                    model.setValueAt(currentOld + 1, row, 1); // Increase "Bàn cũ"
                    model.setValueAt(currentNew - 1, row, 2); // Decrease "Bàn mới"
                }
            });

        }

        private void styleButton(JButton button) {
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(25, 25));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.value = (int) value;
            quantityLabel.setText(String.valueOf(value));
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return value;
        }
    }

    public static void main(String[] args) {
    	connectDB.ConnectDB.getInstance().connect();
        SwingUtilities.invokeLater(() -> new TachBan_GUI(null, "B01N01", "B02N05", new AtomicBoolean(false)).setVisible(true));
    }
}
