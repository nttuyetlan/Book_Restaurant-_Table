package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.View;

import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.MonAn_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.MonAn;
import entity.NhanVien;

public class DatBanTrucTiep_GUI extends JFrame implements ActionListener{
	
	
	private JMenuBar mnuFunction;
	private JMenu mnuDatBanTruoc;
	private JMenu mnuDatBanTrucTiep;
	private JMenu mnuGiaoCa;
	private JMenu mnuThongKe;
	private JMenu mnuQuanLyKhachHang;
	private JMenu mnuTimKiem;
	private JMenu mnuTroGiup;
	private JMenuItem mniDBTT1;
	private JMenuItem mniDBTT2;
	private JMenuItem mniDBT1;
	private JMenuItem mniDBT2;
	private JMenuItem mniMoCa;
	private JMenuItem mniKetCa;
	private JMenuItem mniThemKH;
	private JMenuItem mniCNKH;
	private JMenuItem mniTKDonDatBan;
	private JMenuItem mniTKDonDatMon;
	private JMenuItem mniTKBan;
	private JMenuItem mniTKMonAn;
	private JMenuItem mniKhuyenMai;
	private JMenuItem mniTKKH;
	private JMenuItem mniTKNV;
	private JMenuItem mniHDSD;
	private JMenuItem mniChungToi;
	private JSplitPane splitPane;
	private JTextField txtTimMon;
	private JLabel lblTimMon;
	private JTextField txtsoKH;
	private JTextField txtTenKH;
	private JTextField txtSDTKH;
	private JLabel lblTimKiemKH;
	private JLabel lblThemKH;
	private JCheckBox chkMangVe;
	private JButton btnChuyenBan;
	private JButton btnTachBan;
	private JButton btnThanhToan;
	private JButton btnTamTinh;
	private JTextField txtKhuVuc;
	private JTextField txtSLGhe;
	private JTextField txtTenKhachHang;
	private JTextField txtSLKhach;
	private JToggleButton btnTatCa;
	private JToggleButton btnDiemTam;
	private JToggleButton btnGoi;
	private JToggleButton btnCanhRau;
	private JToggleButton btnLauCom;
	private JToggleButton btnHaiSan;
	private JToggleButton btnMonThem;
	private JToggleButton btnSoup;
	private JToggleButton btnGaBoHeoEch;
	private JToggleButton btnMiHuTieu;
	private JToggleButton btnNuocEp;
	private JPanel pnlBan;
	private JPanel pnlMonAn;
	private JPanel pnlListMonAn;
	private JPanel pnlHienMonAn;
	private CardLayout cardLayoutButton;
	private JPanel pnlCardButton;
	private JButton btnTimMon;
	private JButton btnTimKiemKH;
	private JTable tblForm;
	private JTextField txtMaBan_Ban;
	private JTextField txtTenKH_Moi;
	private JTextField txtSDTKH_Moi;
	private JTextField txtEmailKH_Moi;
	private JComboBox cmb;
	private JTabbedPane tabPane;
	private JLabel lblLoiTenKH;
	private JLabel lblLoiSDTKH;
	private JLabel lblLoiEmailKH;
	private JButton btnXoaMonAn;
	private double tongTien;
	private JButton btnTru;
	private JButton btnCong;
	private JTextField txtSLKH;
	private JButton btnTimKiemBanTheoSLKH;
	private String labelDangChonKVBan = "Tất cả";
	private ArrayList<Ban> dsBan;
	private Runnable loadBanList;
	private ButtonGroup groupKVBan;
	private JPanel selectedPanel = null;
	private JTextArea txaGhiChuKH;
	private String maHoaDon = "";
	private String maBan = "";
	private int trangThai;
	private JLabel lblMaBanValue;
	private JLabel lblTongTienValue;
	private JLabel lblTrangThai;
	private HoaDon hd;
	private String maHD;
	private ImageIcon iconXoaMon;
	private ImageIcon iconGhiChu;
	private ImageIcon iconCongSL;
	private ImageIcon iconTruSL;
	private JLabel lblHoaDon;
//	private JButton btnHuyDon;
	private JButton btnLamMoi;
	private HoaDon_GUI hoaDonGui;
	private JLabel lblGioNhan;
	private JLabel lblGioDuKien;
	private boolean isChucnangChuyenBan = false;
	private String maBanCu;
	private int sucChuaHienTai;
	private boolean isChucnangTachBan = false;
	private JLabel lblTieuDe;
	private JButton btnTroVe;
	private NhanVien nv;
	
	private static MonAn_DAO MA_dao = new MonAn_DAO();
	private static KhachHang_DAO KH_dao = new KhachHang_DAO();
	private static Ban_DAO Ban_dao = new Ban_DAO();
	private static HoaDon_DAO HD_dao = new HoaDon_DAO();
	private static ChiTietHoaDon_DAO Cthd_dao = new ChiTietHoaDon_DAO();
	
	public DatBanTrucTiep_GUI(NhanVien nv) {
		// TODO Auto-generated constructor stub
		setTitle("Đặt Bàn Trực Tiếp");
		this.nv = nv;
	}
	
	public DatBanTrucTiep_GUI() {
		// TODO Auto-generated constructor stub
		this(null);
	}
	
	public JPanel splitMain() {
		// TODO Auto-generated method stub
		JPanel pnlLeft = new JPanel();
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
		JPanel pnlTieuDe = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTieuDe.setBackground(new Color(252, 233, 205));
		ImageIcon iconMau = new ImageIcon("resource/icon/Mau.png");
	    JLabel lblTMau = new JLabel(iconMau);
		pnlTieuDe.add(lblTMau);
		lblTieuDe = new JLabel("Đơn đặt bàn trực tiếp");
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
		lblTieuDe.setForeground(Color.RED);
		lblTieuDe.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 500));
		pnlTieuDe.add(lblTieuDe);
		pnlTieuDe.setPreferredSize(new Dimension(pnlLeft.getWidth(), 35));
		pnlTieuDe.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		pnlLeft.add(pnlTieuDe);
		
		// Card form  thông tin đặt bàn và đặt món
		CardLayout cardLayout = new CardLayout();
        JPanel pnlCard = new JPanel(cardLayout);
        JPanel pnlTTMonAn = initUI_Left_Menu_MonAn();
        JPanel pnlTTBan = initUI_Left_Menu_Ban();
        
       
        pnlCard.add(pnlTTBan, "Thông tin Bàn");
        pnlCard.add(pnlTTMonAn, "Thông tin Món Ăn");
        
        pnlCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        pnlLeft.add(pnlCard);
        JPanel pnlChucNang = initUI_DS_ChucNang();
        pnlLeft.add(pnlChucNang);
		
        // Tạo card menu bàn và menu món ăn
		tabPane = new JTabbedPane();
		
        pnlBan = new JPanel(new BorderLayout());
        JLabel lblBan = new JLabel("Bàn");
        lblBan.setPreferredSize(new Dimension(355, 30));
        
        pnlMonAn = new JPanel();
        
        pnlMonAn.setLayout(new BorderLayout());
        
        JLabel lblMonAn = new JLabel("Món Ăn");
        lblMonAn.setPreferredSize(new Dimension(355, 30));
        
        tabPane.addTab("Bàn", pnlBan);  
        tabPane.setTabComponentAt(0, lblBan);
        tabPane.addTab("Món Ăn", pnlMonAn);
        tabPane.setTabComponentAt(1, lblMonAn);
        tabPane.setPreferredSize(new Dimension(pnlLeft.getWidth(), 30));
        
        // Set màu mặc định ban đầu
        lblBan.setForeground(Color.WHITE);
        lblMonAn.setForeground(Color.BLACK);
        
        // Lắng nghe khi người dùng đổi tab
        tabPane.addChangeListener(e -> {
        	 int selectedIndex = tabPane.getSelectedIndex();
        	    
        	    if (selectedIndex == 0) {
        	        cardLayout.show(pnlCard, "Thông tin Bàn");
        	        lblBan.setForeground(Color.WHITE);
        	        lblMonAn.setForeground(Color.BLACK);
        	        chkMangVe.setSelected(false);
        	        btnChuyenBan.setEnabled(true);
                    btnTachBan.setEnabled(true);
                    btnTamTinh.setEnabled(true);
                    btnXoaMonAn.setEnabled(false);
                    
        	    } else if (selectedIndex == 1) {
        	        cardLayout.show(pnlCard, "Thông tin Món Ăn");
        	        lblBan.setForeground(Color.BLACK);
        	        lblMonAn.setForeground(Color.WHITE);
        	        lblMaBanValue.setText(txtMaBan_Ban.getText());
        	        btnXoaMonAn.setEnabled(true);
        	        if (!lblMaBanValue.getText().isEmpty()) {
			        	txaGhiChuKH.setEditable(true);
			        }
        	    }
        });
        
        initUI_Menu_MonAn(pnlMonAn);
        pnlBan.add(initUI_Menu_Ban());
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeft, tabPane); 
        splitPane.setResizeWeight(0.5); 
        splitPane.setDividerSize(3);
        splitPane.setEnabled(false);
        
        JPanel tong  = new JPanel(new BorderLayout());
        tong.add(splitPane, BorderLayout.CENTER);
        return tong;
//        add(splitPane, BorderLayout.CENTER);
	}

	

	private JPanel initUI_Left_Menu_Ban() {
		// TODO Auto-generated method stub
		JPanel pnlCardBan = new JPanel(new BorderLayout());
		pnlCardBan.setPreferredSize(new Dimension(0,0));
		
		// Thông tin bàn
		JPanel pnlTTBan = new JPanel();
		pnlTTBan.setLayout(new BoxLayout(pnlTTBan, BoxLayout.Y_AXIS));
		pnlTTBan.setBorder(new EmptyBorder(0, 0, 150, 0)); 
		
		JLabel lblTieuDe = new JLabel("Thông tin bàn");
		lblTieuDe.setOpaque(true);
		lblTieuDe.setBackground(new Color(0, 123, 193));
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 14));
		lblTieuDe.setBorder(new EmptyBorder(8, 10, 8, 660));
		pnlTTBan.add(lblTieuDe);
        
        txtMaBan_Ban = new JTextField();
        txtKhuVuc = new JTextField();
        txtSLGhe = new JTextField();
        pnlTTBan.add(createRow("Mã bàn:", txtMaBan_Ban));
        pnlTTBan.add(createRow("Khu vực:", txtKhuVuc));
        pnlTTBan.add(createRow("Số lượng ghế:", txtSLGhe));

        
		pnlCardBan.add(pnlTTBan, BorderLayout.CENTER);
		return pnlCardBan;
	}


	
	private static JPanel createRow(String label, JTextField txtField) {
	    JPanel row = new JPanel();
	    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
	    row.setAlignmentX(Component.LEFT_ALIGNMENT); 
	    row.setBorder(new EmptyBorder(8, 10, 8, 200));

	    JLabel lbl = new JLabel(label);
	    lbl.setPreferredSize(new Dimension(150, 25)); 
	    lbl.setMaximumSize(new Dimension(150, 25));

	    txtField.setPreferredSize(new Dimension(200, 25));
	    txtField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
	    
	    row.add(lbl);
	    row.add(Box.createHorizontalStrut(10)); 
	    row.add(txtField);

	    return row;
	}

	
	private JPanel initUI_Left_Menu_MonAn() {
		// TODO Auto-generated method stub
		JPanel pnlCardMonAn = new JPanel();
		pnlCardMonAn.setLayout(new BoxLayout(pnlCardMonAn, BoxLayout.Y_AXIS));
		
		// Form thông tin món đặt
		String[] columnNames = {"", "Tên món ăn", "", "Số lượng", "", "Đơn giá", "Thành tiền", ""};
		
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 3;
		    }
		};
		tblForm = new JTable(model);
		tblForm.setFocusable(false);
		tblForm.setDefaultEditor(Object.class, null); 
		

		
		// Điều chỉnh kích thước các cột
		TableColumnModel columnModel = tblForm.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);   
		columnModel.getColumn(1).setPreferredWidth(200); 
		columnModel.getColumn(2).setPreferredWidth(10);
		columnModel.getColumn(3).setPreferredWidth(150); 
		columnModel.getColumn(4).setPreferredWidth(10);
		columnModel.getColumn(5).setPreferredWidth(100); 
		columnModel.getColumn(6).setPreferredWidth(100); 
		columnModel.getColumn(7).setPreferredWidth(10);
		
		// Tuỳ chỉnh giao diện cột "Số lượng"
		tblForm.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {

		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		        if (isSelected) {
		            c.setBackground(new Color(184, 207, 229)); // màu khi được chọn
		        } else {
		            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(204, 229, 255)); // tô dòng chẵn/lẻ
		        }

		        return c;
		    }
		});


		
		tblForm = new JTable(model) {
		    @Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        Component c = super.prepareRenderer(renderer, row, column);
		        
		        // Tô màu cả dòng chẵn/lẻ
		        if (!isRowSelected(row)) {
		            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(204, 229, 255));
		        } else {
		            c.setBackground(new Color(30, 129, 191)); 
		        }

		        return c;
		    }
		};



		// Tăng kích thước bảng 
		tblForm.setFont(new Font("Arial", Font.PLAIN, 12));
		tblForm.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
		tblForm.setIntercellSpacing(new Dimension(5, 5));
		
		// Bỏ viền bảng
		tblForm.setShowGrid(false);
		tblForm.setIntercellSpacing(new Dimension());
		tblForm.setBorder(null);
		

		// Bỏ nền và viền header
		JTableHeader tbhForm = tblForm.getTableHeader();
		tbhForm.setOpaque(false);
		tbhForm.setBackground(new Color(255, 255, 255));
		tbhForm.setForeground(Color.BLUE);
		tbhForm.setFont(new Font("Arial", Font.BOLD, 13));
		tbhForm.setBorder(null);

		// Bỏ viền header toàn cục
		UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
		
		// Canh tên thuộc tính và giá trị thẳng hàng
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tblForm.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);  
		tblForm.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblForm.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
		tblForm.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); 
		tblForm.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tblForm.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		
		tblForm.setPreferredScrollableViewportSize(new Dimension(500, 150));

		tblForm.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		            boolean hasFocus, int row, int column) {
		        
		        JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        lbl.setText(value != null ? value.toString() : "");
		        lbl.setVerticalAlignment(SwingConstants.TOP);
		        lbl.setOpaque(true);

		        // Tự tính chiều cao dòng dựa trên nội dung HTML
		        int prefHeight = getPreferredHeight(lbl, table.getColumnModel().getColumn(column).getWidth());
		        if (table.getRowHeight(row) != prefHeight) {
		            table.setRowHeight(row, prefHeight);
		        }

		        return lbl;
		    }

		    private int getPreferredHeight(JLabel label, int width) {
		        View view = BasicHTML.createHTMLView(label, label.getText());
		        view.setSize(width, 0);
		        float prefHeight = view.getPreferredSpan(View.Y_AXIS);
		        return (int) prefHeight + 10; // padding 10
		    }
		});

		 // Sự kiện cho icon Xóa, icon Tăng, icon giảm,  icon Ghi Chú trong bảng
	    tblForm.addMouseListener(new MouseAdapter() {

			@Override
	        public void mouseClicked(MouseEvent e) {
	            int row = tblForm.rowAtPoint(e.getPoint());
	            int col = tblForm.columnAtPoint(e.getPoint());

	            // Giả sử icon xóa nằm ở cột 0
	            if (col == 0 && row != -1) {
	                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá dòng này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
	                if (confirm == JOptionPane.YES_OPTION) {
	                    DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
	                    model.removeRow(row);
	                }
	            }
	            tongTien = tinhTongTien(tblForm);
	            lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
	            
	            if (row != -1 && (col == 2 || col == 4)) {
	                DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
	                int sl = Integer.parseInt(model.getValueAt(row, 3).toString());
	                double price = Double.parseDouble(model.getValueAt(row, 5).toString().replaceAll("[^\\d]", ""));

	                if (col == 2 && sl > 1) sl--; // Giảm số lượng
	                if (col == 4) sl++;          // Tăng số lượng

	                model.setValueAt(String.valueOf(sl), row, 3); // Cập nhật số lượng
	                model.setValueAt(String.format("%,.0f VNĐ", price * sl), row, 6); // Cập nhật thành tiền

	                tongTien = tinhTongTien(tblForm);
	                lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
	            }
	            
	            if (col == 7 && row != -1) {
	                Object cellValue = tblForm.getValueAt(row, 1);
	                String current = cellValue != null ? cellValue.toString() : "";
	                String tenMon = "";
	                String ghiChuCu = "";

	                // Nếu có <html> thì tách lấy tên món và ghi chú
	                if (current.startsWith("<html>")) {
	                    // Loại bỏ thẻ <html> đầu và </html> cuối
	                    current = current.substring(6, current.length() - 7);

	                    // Tách phần trước <br> là tên món
	                    int brIndex = current.indexOf("<br>");
	                    if (brIndex != -1) {
	                        tenMon = current.substring(0, brIndex).trim();

	                        // Lấy phần ghi chú nằm trong <i style>...</i>
	                        int start = current.indexOf("<i");
	                        int tagClose = current.indexOf(">", start);
	                        int end = current.indexOf("</i>", tagClose);

	                        if (start != -1 && tagClose != -1 && end != -1) {
	                            ghiChuCu = current.substring(tagClose + 1, end).trim();
	                        }
	                    } else {
	                        tenMon = current.trim(); // Trường hợp chỉ có tên món, chưa có ghi chú
	                    }
	                } else {
	                    tenMon = current.trim(); // Không có html thì là chuỗi đơn thuần
	                }
	                

	                // Tạo dialog nhập ghi chú
	                JTextArea txaNote = new JTextArea(5, 30);
	                txaNote.setLineWrap(true);
	                txaNote.setWrapStyleWord(true);
	                txaNote.setText(ghiChuCu);
	                
	                String[] goiY = {
	                	    "Ít cay", "Không hành", "Thêm đá", "Ít ngọt", "Mang về", "Cho thêm tương"
	                	};

	                	JPanel pnlCheckBox = new JPanel(new GridLayout(0, 2));

	                	for (String goiYItem : goiY) {
	                	    JCheckBox chk = new JCheckBox(goiYItem);

	                	    // Nếu ghi chú cũ đã có → đánh dấu sẵn
	                	    if (ghiChuCu.contains(goiYItem)) {
	                	    	chk.setSelected(true);
	                	    }

	                	    // Xử lý khi người dùng tick hoặc bỏ tick
	                	    chk.addItemListener(ae -> {
	                	        String currentText = txaNote.getText();

	                	        if (chk.isSelected()) {
	                	            // Nếu tick và chưa có → thêm vào
	                	            if (!currentText.contains(goiYItem)) {
	                	                if (!currentText.isEmpty() && !currentText.endsWith(",") && !currentText.endsWith(" ")) {
	                	                    currentText += ", ";
	                	                }
	                	                currentText += goiYItem;
	                	            }
	                	        } else {
	                	            // Nếu bỏ tick → xóa chính xác cụm text đó
	                	        	currentText = currentText
	                	        		    .replaceAll("(?i)(?<=^|,\\s?)" + Pattern.quote(goiYItem) + "(?=,|$)", "") // xóa từ chính xác giữa các dấu phẩy
	                	        		    .replaceAll(",\\s*,", ",")  // tránh dấu phẩy kép
	                	        		    .replaceAll("(^,\\s*|,\\s*$)", "") // xóa dấu phẩy đầu/cuối
	                	        		    .replaceAll("\\s{2,}", " ") // xóa khoảng trắng thừa
	                	        		    .replaceAll("^\\s+|\\s+$", "") // trim khoảng trắng đầu/cuối
	                	        		    .trim();
	                	        }

	                	        txaNote.setText(currentText);
	                	    });

	                	    pnlCheckBox.add(chk);
	                	}


	                	JPanel pnl = new JPanel(new BorderLayout(5, 5));
	                	pnl.add(new JLabel("Ghi chú:"), BorderLayout.NORTH);
	                	pnl.add(new JScrollPane(txaNote), BorderLayout.CENTER);
	                	pnl.add(pnlCheckBox, BorderLayout.SOUTH);

	                	int result = JOptionPane.showConfirmDialog(
	                	    null, pnl, "Ghi chú cho món ăn", JOptionPane.OK_CANCEL_OPTION);

	                if (result == JOptionPane.OK_OPTION) {
	                    String note = txaNote.getText().trim();
	                    if (!note.isEmpty()) {
	                        tblForm.setValueAt("<html>" + tenMon + "<br><i style='color:rgb(129,14,202)'>" + note + "</i></html>", row, 1);
	                    } else {
	                        tblForm.setValueAt(tenMon, row, 1); 
	                    }
	                }
	            }

	        }
	    });

	    model.addTableModelListener(e -> {
	        int row = e.getFirstRow();
	        int column = e.getColumn();

	        // Nếu người dùng sửa ô số lượng
	        if (column == 3) {
	            try {
	                int sl = Integer.parseInt(model.getValueAt(row, 3).toString());

	                // Lấy giá tiền từ cột 5 (cột đơn giá)
	                double price = Double.parseDouble(model.getValueAt(row, 5).toString().replaceAll("[^\\d]", ""));

	                // Cập nhật lại cột thành tiền (cột 6)
	                model.setValueAt(String.format("%,.0f VNĐ", price * sl), row, 6);

	                // Cập nhật tổng tiền
	                tongTien = tinhTongTien(tblForm);
	                lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));

	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
	                model.setValueAt("1", row, 3); 
	            }
	        }
	    });

	    
		// Thêm bảng vào giao diện
		JScrollPane scrFrom = new JScrollPane();
		scrFrom.setViewportView(tblForm);
		tblForm.setRowHeight(40);
		tblForm.setAutoCreateRowSorter(true);
		tblForm.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrFrom.setBorder(null);
		scrFrom.setPreferredSize(new Dimension(pnlCardMonAn.getWidth(), 250));
		pnlCardMonAn.add(scrFrom);
		
		
		// Form thông tin bàn
		JPanel pnlTTD = new JPanel();
		pnlTTD.setLayout(new BoxLayout(pnlTTD, BoxLayout.Y_AXIS));

		JLabel lblTTD, lblMaBan, lblSoKH, lblTenKH, lblTongTien;
		Dimension lblSize = new Dimension(100, 30); // Kích thước label chung
		
		Box box1 = Box.createHorizontalBox();
		JPanel pnlLabel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		pnlLabel.setBackground(new Color(30, 129, 191));
		lblTTD = new JLabel("Thông tin đơn");
		lblTTD.setForeground(Color.WHITE);
		lblTTD.setFont(new Font("Arial", Font.BOLD, 14));
		lblTTD.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 500));
		lblHoaDon = new JLabel();
		lblHoaDon.setFont(new Font("Arial", Font.BOLD, 14));
		lblHoaDon.setForeground(Color.WHITE);
		pnlLabel.add(lblTTD);
		pnlLabel.add(lblHoaDon);
		box1.add(pnlLabel);

		// ==== BOX 2 ====
		JPanel pnlTam1 = new JPanel();
		pnlTam1.setLayout(new BoxLayout(pnlTam1, BoxLayout.X_AXIS));
		
		JPanel pnlTam2 = new JPanel();
		pnlTam2.setLayout(new BoxLayout(pnlTam2, BoxLayout.Y_AXIS));
		
		Box box2 = Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(10));

		lblMaBan = new JLabel("Mã bàn");
		lblMaBan.setPreferredSize(lblSize);
		lblMaBan.setForeground(new Color(255, 165, 0));
		box2.add(lblMaBan);
		box2.add(Box.createHorizontalStrut(10));

		lblMaBanValue = new JLabel();
		lblMaBanValue.setPreferredSize(new Dimension(250, 30));
		lblMaBanValue.setMaximumSize(lblMaBanValue.getPreferredSize());
		lblMaBanValue.setAlignmentX(Component.LEFT_ALIGNMENT); 
		lblMaBanValue.setEnabled(false);
		box2.add(lblMaBanValue);
		box2.add(Box.createHorizontalStrut(50));
		pnlTam2.add(box2);	

		// ==== BOX 3 ====
		Box box3 = Box.createHorizontalBox();
		box3.add(Box.createHorizontalStrut(10));

		lblTongTien = new JLabel("Tổng tiền");
		lblTongTien.setPreferredSize(lblSize);
		lblTongTien.setForeground(new Color(255, 165, 0));
		box3.add(lblTongTien);
		box3.add(Box.createHorizontalStrut(10));

		lblTongTienValue = new JLabel("0 VNĐ");
		lblTongTienValue.setPreferredSize(new Dimension(260, 30));
		lblTongTienValue.setMaximumSize(lblTongTienValue.getPreferredSize()); 
		lblTongTienValue.setAlignmentX(Component.LEFT_ALIGNMENT); 
		lblTongTienValue.setEnabled(false);
		box3.add(lblTongTienValue);
		box3.add(Box.createHorizontalStrut(50));
		pnlTam2.add(box3);
		pnlTam1.add(pnlTam2);
		
		// === BOX 4 ==== 
		JPanel pnlTam3 = new JPanel();
		pnlTam3.setLayout(new BoxLayout(pnlTam3, BoxLayout.Y_AXIS));
		
		// Button xóa hết món ăn trong hóa đơn
	    lblTrangThai = new JLabel();
	    lblTrangThai.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	    lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
	    lblTrangThai.setAlignmentX(Component.CENTER_ALIGNMENT); 
	    pnlTam3.add(lblTrangThai);
	    
	    pnlTam3.add(Box.createVerticalStrut(20));
	    
	    Box box4 = Box.createHorizontalBox();
		box4.add(Box.createHorizontalStrut(10));

		JLabel lblGhiChu = new JLabel("Ghi chú");
		lblGhiChu.setPreferredSize(lblSize);
		lblGhiChu.setForeground(new Color(255, 165, 0));
		box4.add(lblGhiChu);
		box4.add(Box.createHorizontalStrut(10));

		txaGhiChuKH = new JTextArea(5, 30);
		txaGhiChuKH.setPreferredSize(new Dimension(250, 35));
		txaGhiChuKH.setMaximumSize(txaGhiChuKH.getPreferredSize()); 
		txaGhiChuKH.setAlignmentX(Component.LEFT_ALIGNMENT); 
		txaGhiChuKH.setLineWrap(true);      
		txaGhiChuKH.setWrapStyleWord(true);
		txaGhiChuKH.setDisabledTextColor(Color.BLACK);
		txaGhiChuKH.setEditable(false);

		box4.add(txaGhiChuKH);
		pnlTam3.add(box4);
		pnlTam1.add(pnlTam3);
		pnlTam1.add(Box.createHorizontalStrut(20));

		// ==== Thêm vào panel ====
		pnlTTD.add(Box.createVerticalStrut(10));
		pnlTTD.add(box1);
		pnlTTD.add(Box.createVerticalStrut(15));
		pnlTTD.add(pnlTam1);

		pnlTTD.setPreferredSize(new Dimension(0, 100));
		pnlCardMonAn.add(pnlTTD);
		
		return pnlCardMonAn;
	}
	
	

	private JPanel initUI_DS_ChucNang() {
		// TODO Auto-generated method stub
		JPanel pnlChucNang = new JPanel();
		pnlChucNang.setLayout(new BoxLayout(pnlChucNang, BoxLayout.Y_AXIS));
		pnlChucNang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
	    
	    // Chức năng các nút JButton
		ImageIcon iconHuyDon = new ImageIcon("resource/icon/HuyDon.png");
		Image scaleIconHuyDon = iconHuyDon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblHuyDon = new JLabel(new ImageIcon(scaleIconHuyDon));

	    ImageIcon iconXoaHetMon = new ImageIcon("resource/icon/XoaHetMon.png");
		Image scaleIconXoaHetMon = iconXoaHetMon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	    JLabel lblXoaHetMon = new JLabel(new ImageIcon(scaleIconXoaHetMon));
	    
	    ImageIcon iconChuyenBan = new ImageIcon("resource/icon/ChuyenBan.png");
		Image scaleIconChuyenBan = iconChuyenBan.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblChuyenBan = new JLabel(new ImageIcon(scaleIconChuyenBan));
	    
	    ImageIcon iconTachBan = new ImageIcon("resource/icon/TachBan.png");
		Image scaleIconTachBan = iconTachBan.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblTachBan = new JLabel(new ImageIcon(scaleIconTachBan));
	    
	    ImageIcon iconGhiChu = new ImageIcon("resource/icon/GhiChu.png");
		Image scaleIconGhiChu = iconGhiChu.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblGhiChu = new JLabel(new ImageIcon(scaleIconGhiChu));
	    
	    ImageIcon iconTamTinh = new ImageIcon("resource/icon/TamTinh.png");
		Image scaleIconTamTinh = iconTamTinh.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblTamTinh = new JLabel(new ImageIcon(scaleIconTamTinh));
	    
	    ImageIcon iconThanhToan = new ImageIcon("resource/icon/ThanhToan.png");
		Image scaleIconThanhToan = iconThanhToan.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    JLabel lblThanhToan = new JLabel(new ImageIcon(scaleIconThanhToan));
	    
	    JPanel pnlZero = new JPanel(new GridLayout(1, 2));
	    btnTroVe = taoButton("Trở về", lblHuyDon, Color.LIGHT_GRAY);
        btnXoaMonAn = taoButton("Xóa hết món", lblXoaHetMon, Color.LIGHT_GRAY);
        btnXoaMonAn.setEnabled(false);
        pnlZero.add(btnTroVe);
        pnlZero.add(btnXoaMonAn);
        
	    
        JPanel pnlTop = new JPanel(new GridLayout(1, 2));
        JPanel pnlMangVe = new JPanel(new GridBagLayout());
        pnlMangVe.setBackground(Color.WHITE);
        chkMangVe = new JCheckBox("Mua mang về");
        chkMangVe.setFont(new Font("Arial", Font.BOLD, 16));
        chkMangVe.setBorder(BorderFactory.createEmptyBorder());
        chkMangVe.setOpaque(false);
        pnlMangVe.add(chkMangVe);
        pnlTop.add(pnlMangVe);
        
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2));
        btnChuyenBan = taoButton("Chuyển bàn", lblChuyenBan, Color.LIGHT_GRAY);
        btnTachBan = taoButton("Tách bàn", lblTachBan, Color.LIGHT_GRAY);
        pnlCenter.add(btnChuyenBan);
        pnlCenter.add(btnTachBan);

        JPanel pnlBottom = new  JPanel(new GridLayout(1, 2));
        btnThanhToan = taoButton("Thanh Toán", lblThanhToan, new Color(220, 53, 69));
        btnTamTinh = taoButton("Tạm tính", lblTamTinh, new Color(40, 167, 69));
        pnlBottom.add(btnThanhToan);
        pnlBottom.add(btnTamTinh);
        
        btnTroVe.addActionListener(this);
        btnXoaMonAn.addActionListener(this);
        btnTachBan.addActionListener(this);
        btnChuyenBan.addActionListener(this);
        btnTamTinh.addActionListener(this);
        btnThanhToan.addActionListener(this);
        
        pnlZero.setPreferredSize(new Dimension(0, 0));
        pnlTop.setPreferredSize(new Dimension(0, 0));
        pnlCenter.setPreferredSize(new Dimension(0, 0));
        pnlBottom.setPreferredSize(new Dimension(0, 0));
        
        pnlChucNang.add(pnlTop);
        pnlChucNang.add(pnlZero);
        pnlChucNang.add(pnlCenter);
        pnlChucNang.add(pnlBottom);
        
        chkMangVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isChecked = chkMangVe.isSelected();

                // Nếu là mang về thì vô hiệu hóa các nút
                btnChuyenBan.setEnabled(!isChecked);
                btnTachBan.setEnabled(!isChecked);
                btnTamTinh.setEnabled(!isChecked);
                
                if (isChecked) {
                    tabPane.setSelectedIndex(1);
                	lblMaBanValue.setText(""); 
                	txaGhiChuKH.setEditable(true);
                }else {
                	tabPane.setSelectedIndex(0);
                	txaGhiChuKH.setText("");
                	txaGhiChuKH.setEditable(false);
                }
            }
        });
        
        return pnlChucNang;
	}
	
	private JButton taoButton(String text, JLabel iconLabel, Color bgColor) {
	    JButton btn = new JButton(text);
	    btn.setFont(new Font("Arial", Font.BOLD, 16));
	    btn.setForeground(Color.WHITE);
	    btn.setFocusPainted(false);
	    btn.setBackground(bgColor);
	    btn.setOpaque(true);
	    btn.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
	    btn.setPreferredSize(new Dimension(60, 30));

	    if (iconLabel != null && iconLabel.getIcon() != null) {
	    	btn.setIcon(iconLabel.getIcon());
	        btn.setHorizontalTextPosition(SwingConstants.RIGHT); 
	        btn.setIconTextGap(10);
	    }

	    return btn;
	}


	public JPanel initUI_Menu_Ban() {
		// TODO Auto-generated method stub
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnlCardBan = new JPanel(new BorderLayout());
		dsBan = new ArrayList<Ban>();

		JPanel pnlNorthCardBan = new JPanel();
		pnlNorthCardBan.setLayout(new BoxLayout(pnlNorthCardBan, BoxLayout.Y_AXIS));
		pnlNorthCardBan.setPreferredSize(new Dimension(pnl.getWidth(), 35));
// ----- Tìm kiếm 
		JPanel pnlSLKhachHang = new JPanel();
		pnlSLKhachHang.setPreferredSize(new Dimension(pnl.getWidth(), 35));
		JLabel lbSLKhachHang = new JLabel("Số lượng khách hàng:");
		pnlSLKhachHang.add(lbSLKhachHang);
//		lbSLKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pnlSLKhachHang.add(txtSLKH = new JTextField(40));
		// Tạo button tìm kiếm
		btnTimKiemBanTheoSLKH = new JButton("Tìm");
		btnTimKiemBanTheoSLKH.setForeground(Color.BLACK);
		btnTimKiemBanTheoSLKH.addActionListener(e -> {
		    String input = txtSLKH.getText().trim();
		    

		    if (input.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng khách.");
		        return;
		    }

		    try {
		        int soKhach = Integer.parseInt(input);
		        int[] loaiBan = {2, 4, 8, 12};

		        List<Integer> banPhuHop = new ArrayList<>();
		        for (int ghe : loaiBan) {
		            if (ghe >= soKhach) {
		                banPhuHop.add(ghe);
		            }
		        }

		        if (banPhuHop.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Không có loại bàn nào phù hợp.");
		        } else {
		            ArrayList<Ban> cacBanPhuHop = Ban_dao.getBanPhuHop(banPhuHop,labelDangChonKVBan);
		            if (cacBanPhuHop.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Không có bàn nào trống phù hợp.");
		            } else {
		            	dsBan = cacBanPhuHop;
						loadBanList.run();
		            }
		        }

		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.");
		    }
//		    txtSLKH.setText("");
		});

		pnlSLKhachHang.add(btnTimKiemBanTheoSLKH);
		pnlSLKhachHang.setBackground(new Color(194, 232, 255));
		
		pnlNorthCardBan.add(pnlSLKhachHang);
		pnl.add(pnlNorthCardBan, BorderLayout.NORTH);
// ---- Các btn 
//		
		JPanel pnlCenterCardBan = new JPanel(new BorderLayout());
		pnlCenterCardBan.setBackground(Color.WHITE);

		// ==== PANEL CHỨA CÁC NÚT LOC ====
		JPanel pnlscrBtnCardBan = new JPanel();
		pnlscrBtnCardBan.setPreferredSize(new Dimension(pnlCenterCardBan.getWidth(), 40));
		pnlscrBtnCardBan.setBackground(Color.white);

		JPanel pnlBtnCardBan = new JPanel();
		pnlBtnCardBan.setBackground(Color.white);
		String[] buttonLabelsBan = { "Tất cả", "Ngoài trời", "Tầng 1", "Tầng 2", "Sân thượng" };
		groupKVBan = new ButtonGroup();
		JToggleButton btnTatCa = null;

		// ==== PANEL CHỨA CÁC BÀN ====
		JPanel pnlCacBan = new JPanel(new BorderLayout());
		pnlCacBan.setPreferredSize(new Dimension(0, 900));
		pnlCacBan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 900));

		JPanel pnlScrBan = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnlScrBan.setBackground(Color.WHITE);

		pnlScrBan.removeAll();
		pnlScrBan.revalidate();
		pnlScrBan.repaint();

		// Load DAO
		dsBan = Ban_dao.getBanTheoTrangThai(1);

		int itemWidth = 180;
		int itemHeight = 250;
		int hGap = 10;
		int vGap = 10;
		int columns = 4;
		int totalWidth = (itemWidth * columns) + (hGap * (columns + 1));

		 selectedPanel = null; 
		
		// Load danh sách mặc định (tất cả bàn) 
		 loadBanList = () -> {
				pnlScrBan.removeAll();
				for (Ban ban : dsBan) {
					int trang_thai = ban.getTrangThai();
					if (trang_thai == 4 )
							continue;
					int sl = ban.getSoLuongGhe();

					Color color = getColorTheoTrangThai(trang_thai);
					String iconPath = getIconTheoSoLuongGhe(sl);

					pnlScrBan.add(create_1_BanPanel(ban.getMaBan(), color, iconPath));
				}

				int totalItems = pnlScrBan.getComponentCount();
				int rows = (int) Math.ceil((double) totalItems / columns);
				int totalHeight = (itemHeight * rows) + (vGap * (rows + 1));
				pnlScrBan.setPreferredSize(new Dimension(totalWidth, totalHeight));

				pnlScrBan.revalidate();
				pnlScrBan.repaint();
			};

		// Gắn sự kiện cho từng nút lọc
		for (String labelChonKVBan : buttonLabelsBan) {
			JToggleButton btnChonKVBan = createToggleButton(labelChonKVBan);
			groupKVBan.add(btnChonKVBan);
			pnlBtnCardBan.add(btnChonKVBan);

			if (labelChonKVBan.equals("Tất cả"))
				btnTatCa = btnChonKVBan;

			btnChonKVBan.addActionListener(e -> {
				switch (labelChonKVBan) {
				case "Tất cả" -> {
					dsBan = Ban_dao.getAllBan();
					labelDangChonKVBan = "Tất cả";
				}
				case "Ngoài trời" -> {
					dsBan = Ban_dao.getBanNgoaiTroi();
					labelDangChonKVBan = "KV01";
				}
				case "Tầng 1" -> {
					dsBan = Ban_dao.getBanTang1();
					labelDangChonKVBan = "KV02";
				}
				case "Tầng 2" -> {
					dsBan = Ban_dao.getBanTang2();
					labelDangChonKVBan = "KV03";
				}
				case "Sân thượng" -> {
					dsBan = Ban_dao.getBanSanThuong();
					labelDangChonKVBan = "KV04";
				}
				}
				loadBanList.run();
			});
		}
		if (btnTatCa != null)
			btnTatCa.setSelected(true);

		JButton btntest = new JButton("test");

		JScrollPane scrButton = new JScrollPane(pnlBtnCardBan);
		scrButton.setBorder(null);
		scrButton.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrButton.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pnlscrBtnCardBan.add(scrButton);

		pnlCenterCardBan.add(pnlscrBtnCardBan, BorderLayout.CENTER);
		pnlCacBan.add(pnlCenterCardBan, BorderLayout.NORTH);

		// Scroll chính chứa bàn
		JScrollPane scrPane = new JScrollPane(pnlScrBan);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pnlCacBan.add(scrPane, BorderLayout.CENTER);
		pnl.add(pnlCacBan, BorderLayout.CENTER);

		// Load danh sách bàn lần đầu
		loadBanList.run();

//		// Thêm vào vùng hiển thị chính
//		pnlCardBan.add(pnlCenterCardBan, BorderLayout.CENTER);
//		reatePanelLoadBan();

		// Panel dưới
		JPanel pnlSouthCardBan = new JPanel(new BorderLayout());
		pnlSouthCardBan.setPreferredSize(new Dimension(pnlCardBan.getWidth(), 120));
//		pnlCardBan.add(pnlSouthCardBan, BorderLayout.SOUTH);

		// Chú thích
		JLabel lblChuThich = new JLabel("Chú thích");
		lblChuThich.setFont(new Font("Arial", Font.BOLD, 16));
		lblChuThich.setForeground(Color.WHITE);
		JPanel pnlChuThich = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlChuThich.setBackground(new Color(30, 129, 191));
		pnlChuThich.setPreferredSize(new Dimension(0, 35));
		lblChuThich.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		pnlChuThich.add(lblChuThich);

		JPanel pnlbtnChuThich = new JPanel(new GridLayout(2, 2));

		JButton btnBanTrong = new JButton();
		JButton btnBanCoKhach = new JButton();
		JButton btnBanDaDat = new JButton();

		JPanel pnlbtnBanTrong = createPanelChuThich(btnBanTrong, new Color(0x8EF49A), "Bàn trống", 1);
		JPanel pnlbtnBanCoKhach = createPanelChuThich(btnBanCoKhach, new Color(0xFF8A8A), "Bàn có khách", 2);
		JPanel pnlbtnBanDaDat = createPanelChuThich(btnBanDaDat, new Color(0xACACAC), "Bàn đã đặt", 3);
		
		pnlbtnChuThich.add(pnlbtnBanTrong);
		pnlbtnChuThich.add(pnlbtnBanCoKhach);
		pnlbtnChuThich.add(pnlbtnBanDaDat);

		pnlSouthCardBan.add(pnlChuThich, BorderLayout.NORTH);
		pnlSouthCardBan.add(pnlbtnChuThich, BorderLayout.CENTER);
		pnl.add(pnlSouthCardBan, BorderLayout.SOUTH);
		
		return pnl;
	}
	
	private JPanel create_1_BanPanel(String tenBan, Color bgColor, String pathIcon) {
		JPanel pnlbtnBan = new JPanel(new BorderLayout());
		pnlbtnBan.setPreferredSize(new Dimension(180, 250));

		JLabel lblTenBan = new JLabel(tenBan, JLabel.CENTER);
		lblTenBan.setFont(new Font("Arial", Font.BOLD, 14));
		lblTenBan.setForeground(Color.WHITE);
		JPanel pnlTenBan = new JPanel();
		pnlTenBan.setBackground(new Color(30, 129, 191));
		pnlTenBan.setPreferredSize(new Dimension(180, 35));
		pnlTenBan.add(lblTenBan);
		

		JPanel pnlCenterBan = new JPanel(new BorderLayout());
		pnlCenterBan.setPreferredSize(new Dimension(180, 175));
		pnlCenterBan.setBackground(bgColor);
		
		HoaDon hdtam = HD_dao.timHoaDonTheoBanVaTrangThai(tenBan, 0);
		if(hdtam == null) {
			lblGioNhan = new JLabel("");
		}else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String hienThiThoiGianNhan = hdtam.getThoiGianTaoDon().format(formatter);

			lblGioNhan = new JLabel(hienThiThoiGianNhan);
		}
		lblGioNhan.setHorizontalAlignment(JLabel.CENTER);
	    lblGioNhan.setFont(new Font("Arial", Font.PLAIN, 12));
	    lblGioNhan.setForeground(Color.BLACK);
	    lblGioNhan.setPreferredSize(new Dimension(180, 20));
	    pnlCenterBan.add(lblGioNhan, BorderLayout.NORTH);

		ImageIcon iconBan = new ImageIcon(pathIcon);
		Image imageBan = iconBan.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
		JLabel lblImageBan = new JLabel(new ImageIcon(imageBan));
		lblImageBan.setHorizontalAlignment(JLabel.CENTER);
		pnlCenterBan.add(lblImageBan, BorderLayout.CENTER);
		
		if(hdtam == null || hdtam.getThoiGianThanhToan() == null) {
			lblGioDuKien = new JLabel();
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String hienThiThoiGianDuKien = hdtam.getThoiGianThanhToan().format(formatter);
			lblGioDuKien = new JLabel(hienThiThoiGianDuKien);
		}

		lblGioDuKien.setHorizontalAlignment(JLabel.CENTER);
	    lblGioDuKien.setFont(new Font("Arial", Font.PLAIN, 12));
	    lblGioDuKien.setForeground(Color.BLACK);
	    lblGioDuKien.setPreferredSize(new Dimension(180, 20));
	    pnlCenterBan.add(lblGioDuKien, BorderLayout.SOUTH);

		JPanel pnlChiTiet = new JPanel();
		pnlChiTiet.setPreferredSize(new Dimension(180, 40));
		pnlChiTiet.setBackground(bgColor);
		JButton btnChiTiet = createCustomButton("Chi tiết");
		pnlChiTiet.add(btnChiTiet);
		
		if (bgColor.equals(new Color(0x8EF49A))) {
		    btnChiTiet.setEnabled(false); 
		}
		
		// Sự kiện khi nhấn nút Chi tiết
		trangThai = 0;
		HoaDon hd = HD_dao.timHoaDonTheoBanVaTrangThai(tenBan, trangThai);
		
		btnChiTiet.addActionListener(e -> {
			
			Ban banChon = Ban_dao.getBan(tenBan);
			Ban ban = Ban_dao.sp_TimTenKhuVucTuMaBan(tenBan);
			
			txtMaBan_Ban.setText(banChon.getMaBan());
			txtKhuVuc.setText(ban.getKV().getTenKV());
			txtSLGhe.setText(Integer.toString(ban.getSoLuongGhe()));
			
		    tabPane.setSelectedIndex(1);
		    lblMaBanValue.setText(tenBan);
		    if (trangThai == 0) {
		        lblTrangThai.setText("Trạng thái: Chưa thanh toán");
		        lblTrangThai.setForeground(new Color(220, 53, 69));
		    } else {
		        lblTrangThai.setText("Trạng thái: Đã thanh toán");
		        lblTrangThai.setForeground(new Color(40, 167, 69));
		    }
		    if (hd.getChuThich() == null) {
		    	txaGhiChuKH.setText("");
		    }else {
		    	txaGhiChuKH.setText(hd.getChuThich());
		    }
		    lblHoaDon.setText(hd.getMaHD());

		    DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
		    model.setRowCount(0); 

		    ArrayList<ChiTietHoaDon> listCTHD = Cthd_dao.getChiTietHoaDonTheoMaHD(hd.getMaHD());
		    for (ChiTietHoaDon cthd : listCTHD) {
		        MonAn ma = MA_dao.timTenMonAnVaGiaMonAnTheoMa(cthd.getMonAn().getMaMonAn());
		        String ghiChu = cthd.getGhiChu();

		        String cellTenMon;
		        if (ghiChu != null && !ghiChu.trim().isEmpty()) {
		            cellTenMon = "<html>" + ma.getTenMonAn() + "<br><i style='color:rgb(129,14,202)'>" + ghiChu + "</i></html>";
		        } else {
		            cellTenMon = ma.getTenMonAn();
		        }

		        Object[] rowData = {
		            iconXoaMon,
		            cellTenMon,               
		            iconTruSL,
		            cthd.getSoLuong(),
		            iconCongSL,
		            String.format("%,.0f VNĐ", ma.getDonGia()),
		            String.format("%,.0f VNĐ", ma.getDonGia() * cthd.getSoLuong()),
		            iconGhiChu                
		        };

		        model.addRow(rowData);
		    }

		    tongTien = tinhTongTien(tblForm);
		    lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
		});


	
		// Sự kiện khi nhấp đúp vào toàn bộ panel
		pnlbtnBan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					Ban banChon = Ban_dao.getBan(tenBan);
					Ban ban = Ban_dao.sp_TimTenKhuVucTuMaBan(tenBan);
					
					txtMaBan_Ban.setText(banChon.getMaBan());
					txtKhuVuc.setText(ban.getKV().getTenKV());
					txtSLGhe.setText(Integer.toString(ban.getSoLuongGhe()));
					
					if (selectedPanel != null) {
	                    selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Viền mặc định
	                }

	                // Đặt viền đen cho panel được chọn
					pnlbtnBan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));  // Viền đỏ với độ dày 3px

	                // Cập nhật panel được chọn
	                selectedPanel = pnlbtnBan;
	                
	                // Thực khi chuyển bàn
	                if (isChucnangChuyenBan) {
	                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pnlbtnBan);
	                    AtomicBoolean isChuyenBan = new AtomicBoolean(false);

	                    ChuyenBan_GUI dialog = new ChuyenBan_GUI(frame, maBanCu, tenBan, sucChuaHienTai, isChuyenBan);
	                    dialog.setVisible(true);

	                    if (isChuyenBan.get()) {
	                        // Thực hiện cập nhật danh sách bàn nếu chuyển thành công
	                        switch (labelDangChonKVBan) {
	                            case "Tất cả" -> dsBan = Ban_dao.getAllBan();
	                            case "KV01" -> dsBan = Ban_dao.getBanNgoaiTroi();
	                            case "KV02" -> dsBan = Ban_dao.getBanTang1();
	                            case "KV03" -> dsBan = Ban_dao.getBanTang2();
	                            case "KV04" -> dsBan = Ban_dao.getBanSanThuong();
	                        }
	                        loadBanList.run();
	                        
	                        isChucnangChuyenBan = false; 
	                        lblTieuDe.setText("Đặt bàn trực tiếp");
	                        txtMaBan_Ban.setText("");
	                        txtKhuVuc.setText("");
	                        txtSLGhe.setText("");
	                        
	                    } else {
	                        // Nếu không chuyển, giữ nguyên trạng thái để chọn lại bàn khác
	                        System.out.println("Hủy chuyển bàn, giữ nguyên trạng thái chuyển.");
	                    }
	                }
	                
	                // Thực hiện tách bàn
	                if(isChucnangTachBan) {
	      		  		Frame frame = (JFrame) SwingUtilities.getWindowAncestor(pnlbtnBan);
	                    AtomicBoolean isTachBan = new AtomicBoolean(false);

	                    TachBan_GUI dialog = new TachBan_GUI((JFrame) SwingUtilities.getWindowAncestor(pnlbtnBan), maBanCu, tenBan, isTachBan);
	                    dialog.setVisible(true);

	                    if (isTachBan.get()) {
	                        // Thực hiện cập nhật danh sách bàn nếu chuyển thành công
	                        switch (labelDangChonKVBan) {
	                            case "Tất cả" -> dsBan = Ban_dao.getAllBan();
	                            case "KV01" -> dsBan = Ban_dao.getBanNgoaiTroi();
	                            case "KV02" -> dsBan = Ban_dao.getBanTang1();
	                            case "KV03" -> dsBan = Ban_dao.getBanTang2();
	                            case "KV04" -> dsBan = Ban_dao.getBanSanThuong();
	                        }
	                        loadBanList.run();
	                        
	                        isChucnangTachBan = false; 
	                        lblTieuDe.setText("Đặt bàn trực tiếp");
	                        txtMaBan_Ban.setText("");
	                        txtKhuVuc.setText("");
	                        txtSLGhe.setText("");
	                    } else {
	                        // Nếu không chuyển, giữ nguyên trạng thái để chọn lại bàn khác
	                        System.out.println("Hủy tách bàn, giữ nguyên trạng thái tách.");
	                    }
	                }
				}
		});



		pnlbtnBan.add(pnlTenBan, BorderLayout.NORTH);
		pnlbtnBan.add(pnlCenterBan, BorderLayout.CENTER);
		pnlbtnBan.add(pnlChiTiet, BorderLayout.SOUTH);

		return pnlbtnBan;
	}
	// btn ghi chú
	private JPanel createPanelChuThich(JButton btn, Color color, String labelText, int trangThai) {
		btn.setPreferredSize(new Dimension(20, 20));
		btn.setBackground(color);
		btn.setFocusable(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK)));

		btn.addActionListener(e -> {
			if (labelDangChonKVBan == "Tất cả") {
				dsBan = Ban_dao.getBanTheoTrangThai(trangThai);
				loadBanList.run();
			} else {
				dsBan = Ban_dao.getBanTheoKhuVucVaTrangThai(labelDangChonKVBan, trangThai);
				loadBanList.run();
			}
			
			if (dsBan==null) {
				JOptionPane.showMessageDialog(null, "Không có bàn hợp lệ!!!");
			}

		});

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createHorizontalStrut(30)); // cách trái
		panel.add(btn);
		panel.add(new JLabel(labelText));
		return panel;
	}
	
	// nút chi tiết 
	private JButton createCustomButton(String text) {
	    JButton btn = new JButton(text) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            if (!isEnabled()) {
	                // Vẽ màu nền mờ (disabled)
	            	createCustomButtonBackground(g, this, true, false, false); 
	            } else {
	            	createCustomButtonBackground(g, this, false, getModel().isRollover(), getModel().isPressed());
	            }
	            super.paintComponent(g);  
	        }
	    };

	    // Thiết lập các thuộc tính cho nút
	    btn.setFocusPainted(false);
	    btn.setFont(new Font("Arial", Font.BOLD, 12));
	    btn.setForeground(Color.WHITE); 
	    btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
	    btn.setContentAreaFilled(false);  
	    btn.setOpaque(false);  

	    return btn;
	}
	
	// set bo góc + màu nút chi tiết 
		private void createCustomButtonBackground(Graphics g, JButton btn, boolean isDisabled, boolean isHover, boolean isPressed) {
		    Graphics2D g2 = (Graphics2D) g.create();
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		    Color backgroundColor;

		    // Xử lý màu nền tùy thuộc vào trạng thái của nút
		    if (isDisabled) {
		        backgroundColor = new Color(30, 129, 191,50);  
		    } else if (isPressed) {
		        backgroundColor = new Color(30, 129, 191,20);  
		    }  else {
		        backgroundColor = new Color(0x1E81BF); 
		    }

		    g2.setColor(backgroundColor);
		    g2.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 20, 20); 

		    g2.dispose();
		}

	private String getIconTheoSoLuongGhe(int soLuong) {
		// TODO Auto-generated method stub
		return switch (soLuong) {
		case 2 -> "resource\\icon\\ban2.png";
		case 4 -> "resource\\icon\\ban4.png";
		case 8 -> "resource\\icon\\ban8.png";
		case 12 -> "resource\\icon\\ban12.png";
		default -> "resource\\icon\\ban_default.png";
		};
	}

	// Hàm trả về màu theo trạng thái
	private Color getColorTheoTrangThai(int trangThai) {
		// TODO Auto-generated method stub
		return switch (trangThai) {
			case 1 -> new Color(0x8EF49A);
			case 2 -> new Color(0xFF8A8A);
			case 3 -> new Color(0xACACAC);
			default -> Color.black;
		};
	}
	

	private void  initUI_Menu_MonAn(JPanel pnl) {
		// TODO Auto-generated method stub
		// Tìm kiếm món ăn
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		
		
		JPanel pnlTimMon = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTimMon.setBackground(new Color(194, 232, 255));
		JLabel lblTenMon;
		pnlTimMon.add(lblTenMon = new JLabel("Tên món ăn:"));
		lblTenMon.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 30));
		
		txtTimMon = new JTextField(40);
		pnlTimMon.add(txtTimMon);
		
		btnTimMon = new JButton("Tìm");
		btnTimMon.setFocusPainted(false);
		pnlTimMon.add(btnTimMon);
		pnl.add(pnlTimMon);
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
		pnlButton.setPreferredSize(new Dimension(0, 100));
		
		// Các button lọc món
		ButtonGroup group = new ButtonGroup();
		btnTatCa = createToggleButton("TẤT CẢ");
	    btnDiemTam = createToggleButton("ĐIỂM TÂM");
	    btnGoi = createToggleButton("GỎI");
	    btnLauCom = createToggleButton("LẨU - CƠM");
	    btnCanhRau = createToggleButton("CANH - RAU");
	    btnHaiSan = createToggleButton("HẢI SẢN");
	    btnMonThem = createToggleButton("MÓN THÊM");
	    btnSoup = createToggleButton("SOUP - TIỀM - CHÁO");
	    btnGaBoHeoEch = createToggleButton("GÀ - BÒ - HEO - ẾCH - LƯƠN");
	    btnMiHuTieu = createToggleButton("MÌ - MIẾN - HỦ TIẾU");
	    btnNuocEp = createToggleButton("SINH TỐ - NƯỚC ÉP");

	    group.add(btnTatCa);
	    group.add(btnDiemTam);
	    group.add(btnGoi);
	    group.add(btnLauCom);
	    group.add(btnCanhRau);
	    group.add(btnHaiSan);
	    group.add(btnMonThem);
	    group.add(btnSoup);
	    group.add(btnGaBoHeoEch);
	    group.add(btnMiHuTieu);
	    group.add(btnNuocEp);

	    pnlButton.add(btnTatCa);
	    pnlButton.add(btnDiemTam);
	    pnlButton.add(btnGoi);
	    pnlButton.add(btnLauCom);
	    pnlButton.add(btnCanhRau);
	    pnlButton.add(btnHaiSan);
	    pnlButton.add(btnMonThem);
	    pnlButton.add(btnSoup);
	    pnlButton.add(btnGaBoHeoEch);
	    pnlButton.add(btnMiHuTieu);
	    pnlButton.add(btnNuocEp);
	    
	    btnTatCa.addActionListener(this);
	    btnDiemTam.addActionListener(this);
	    btnGoi.addActionListener(this);
	    btnLauCom.addActionListener(this);
	    btnCanhRau.addActionListener(this);
	    btnHaiSan.addActionListener(this);
	    btnMonThem.addActionListener(this);
	    btnSoup.addActionListener(this);
	    btnGaBoHeoEch.addActionListener(this);
	    btnMiHuTieu.addActionListener(this);
	    btnNuocEp.addActionListener(this);
	    btnTimMon.addActionListener(this);

	    btnTatCa.setSelected(true);
	    pnl.add(pnlButton);
	   
	    cardLayoutButton = new CardLayout();
        pnlCardButton = new JPanel(cardLayoutButton);
        pnl.add(pnlCardButton);
        btnTatCa.doClick();
	}


	private JPanel foodMenuUI(List<MonAn> dsMonAn) {
	    
	    JPanel pnlListMonAn = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    pnlListMonAn.setBackground(Color.WHITE);

	    for (MonAn mon : dsMonAn) {
	        JPanel pnlMon = taoUIPanelMonAn(mon);
	        pnlListMonAn.add(pnlMon);
	    }
	    
	 // Tính toán chiều rộng mong muốn (3 cột, mỗi item 230px + spacing 10px)
	    int itemWidth = 230;
	    int itemHeight = 150;
	    int hGap = 10;
	    int vGap = 10;
	    int columns = 3;

	    int totalItems = pnlListMonAn.getComponentCount();
	    int rows = (int) Math.ceil((double) totalItems / columns);

	    int totalWidth = (itemWidth * columns) + (hGap * (columns + 1));
	    int totalHeight = (itemHeight * rows) + (vGap * (rows + 1));

	    pnlListMonAn.setPreferredSize(new Dimension(totalWidth, totalHeight));

	    JScrollPane scrPane = new JScrollPane(pnlListMonAn);
	    scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

	    JPanel wrapper = new JPanel(new BorderLayout());
	    wrapper.setPreferredSize(new Dimension(0, 450));
	    wrapper.add(scrPane, BorderLayout.CENTER);

	    return wrapper;
	}



	private JPanel taoUIPanelMonAn(MonAn mon) {
		// TODO Auto-generated method stub
		String ten = mon.getTenMonAn();
        Double gia =  mon.getDonGia();
        int trangThai = mon.getTrangThai();

        ImageIcon icon = null;
        if (mon.getHinhMonAn() != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(mon.getHinhMonAn());
                BufferedImage bimg = ImageIO.read(bais);
                if (bimg != null) {
                    Image scaled = bimg.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(scaled);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return createFoodItem(ten, gia, icon, trangThai);
	}

	private JPanel createFoodItem(String name, Double price, ImageIcon icon, int trangThai) {
	    JPanel pnlItem = new JPanel(null);
	    pnlItem.setPreferredSize(new Dimension(230, 150));
	    pnlItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    pnlItem.setBackground(Color.WHITE);
	    
	 // Checkbox ở góc trên trái => Thông báo Hết Món
	    JCheckBox chkHetMon = new JCheckBox();
	    chkHetMon.setBounds(5, 5, 20, 20);
	    if(trangThai == 1) {
	        chkHetMon.setSelected(false);
	    } else if (trangThai == 0) {
	    	chkHetMon.setSelected(true);
	        hienThiHetMon(pnlItem);
	    }
	    pnlItem.add(chkHetMon);
		final boolean[] allowClick = { true };
	    chkHetMon.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	boolean isChecked = chkHetMon.isSelected();
	            allowClick[0] = !isChecked;
	            	 
	            if (chkHetMon.isSelected()) { 
	            	 hienThiHetMon(pnlItem);
	            	 
	            	 pnlItem.addMouseListener(new MouseAdapter() {});
	            	 pnlItem.addMouseMotionListener(new MouseMotionAdapter() {});

	            	 if (MA_dao.capNhatTrangThaiMonAn(name, 0)) {
	            		 JOptionPane.showMessageDialog(null, "Cập nhật trạng thái hết món ăn thành công!");
	            	 } else {
	            		 JOptionPane.showMessageDialog(null, "Cập nhật trạng thái hết món ăn thất bại!");
	            	 }
	            } else {
	            	// Khôi phục lại nền panel món ăn
	            	for (Component comp : pnlItem.getComponents()) {
	                    if ("overlayHetMon".equals(comp.getName()) || "glassPane".equals(comp.getName())) {
	                        pnlItem.remove(comp);
	                    }
	                }

	                pnlItem.setOpaque(true);
	                pnlItem.setBackground(Color.WHITE);
	                pnlItem.revalidate();
	                pnlItem.repaint();
	                
	                if (MA_dao.capNhatTrangThaiMonAn(name, 1)) {
	                    JOptionPane.showMessageDialog(null, "Cập nhật trạng thái món ăn thành công!");
	                } else {
	                    JOptionPane.showMessageDialog(null, "Cập nhật trạng thái món ăn thất bại!");
	                }
	            }
	        }
	    });


	    // Ảnh món ăn
	    JLabel lblAnh = new JLabel();
	    lblAnh.setHorizontalAlignment(SwingConstants.CENTER);
	    lblAnh.setBounds(26, 10, 180, 60);
	    if (icon != null) {
	        lblAnh.setIcon(icon);
	    }
	    pnlItem.add(lblAnh);

	    // Tên món ăn
	    JLabel lblTen = new JLabel(name, SwingConstants.CENTER);
	    lblTen.setBounds(20, 75, 180, 20);
	    pnlItem.add(lblTen);

	    // Giá
	    NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String gia = vndFormat.format(price);
	    JLabel lblGia = new JLabel("Đơn giá: " + gia, SwingConstants.CENTER);
	    lblGia.setForeground(Color.RED);
	    lblGia.setBounds(20, 95, 200, 20);
	    pnlItem.add(lblGia);
	    
	    // Sự kiện thêm món ăn vào bảng
	    iconXoaMon = new ImageIcon("resource/icon/Xoa.png");
	    iconGhiChu = new ImageIcon("resource/icon/GhiChu.png");
	    iconCongSL = new ImageIcon("resource/icon/Tang.png");
	    iconTruSL = new ImageIcon("resource/icon/Giam.png");
	    
	    
		// Renderer để hiển thị ImageIcon Xóa, tăng, giảm và Ghi Chú trong JTable
		DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {

		        JLabel lbl = new JLabel();
		        if (column == 2) {
		            lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		        } else if (column == 4){
		            lbl.setHorizontalAlignment(SwingConstants.LEFT);
		        }else {
		        	lbl.setHorizontalAlignment(SwingConstants.CENTER);
		        }

		        if (value instanceof Icon) {
		        	lbl.setIcon((Icon) value);
		        }

		        // Tô màu giống như các cột văn bản
		        if (isSelected) {
		        	lbl.setBackground(new Color(30, 129, 191));
		        	lbl.setOpaque(true);
		        } else {
		            Color bg = (row % 2 == 0) ? Color.WHITE : new Color(204, 229, 255);
		            lbl.setBackground(bg);
		            lbl.setOpaque(true);
		        }

		        return lbl;
		    }
		};
		tblForm.getColumnModel().getColumn(0).setCellRenderer(iconRenderer);	
		tblForm.getColumnModel().getColumn(2).setCellRenderer(iconRenderer);
		tblForm.getColumnModel().getColumn(4).setCellRenderer(iconRenderer);
		tblForm.getColumnModel().getColumn(7).setCellRenderer(iconRenderer);
		
		// Khi click vào panel, thêm món vào bảng		
		pnlItem.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	if (lblMaBanValue.getText().trim().isEmpty() && !chkMangVe.isSelected()) {
		    	    JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn hoặc mang về trước khi gọi món!");
		    	    return;
		    	}
		    	
		    	if (chkHetMon.isSelected()) {
		            JOptionPane.showMessageDialog(null, "Món này đã hết!");
		            return;
		        }
		    	
		        if (!allowClick[0]) return;

		        DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
		        boolean daTonTai = false;

		        for (int i = 0; i < model.getRowCount(); i++) {
		            String tenTrongBang = model.getValueAt(i, 1).toString();
		            if (tenTrongBang.equals(name)) {
		                int sl = Integer.parseInt(model.getValueAt(i, 3).toString());
		                sl++;
		                model.setValueAt(String.valueOf(sl), i, 3); 
		                model.setValueAt(String.format("%,.0f VNĐ", sl * price), i, 6); 
		                tongTien = tinhTongTien(tblForm);
		                lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
		                daTonTai = true;
		                break;
		            }
		        }

		        if (selectedPanel != null) {
		            selectedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		        }
		        pnlItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		        selectedPanel = pnlItem;

		        if (!daTonTai) {
		            Object[] rowData = {
		                iconXoaMon,              // cột 0
		                name,                   // cột 1
		                iconTruSL,              // cột 2: giảm
		                "1",                    // cột 3: số lượng
		                iconCongSL,            // cột 4: tăng
		                String.format("%,.0f VNĐ", price),        // cột 5: đơn giá
		                String.format("%,.0f VNĐ", price),        // cột 6: thành tiền
		                iconGhiChu              // cột 7
		            };

		            model.addRow(rowData);
		            tongTien = tinhTongTien(tblForm);
		            lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
		        }
		    }
		});

		
	    return pnlItem;
	}
	
	private void hienThiHetMon(JPanel pnlItem) {
	    pnlItem.setOpaque(false);
	    pnlItem.setBackground(new Color(255, 255, 255, 10));

	    JPanel pnlOverlay = new JPanel(new BorderLayout());
	    pnlOverlay.setBackground(new Color(255, 0, 0, 180));
	    pnlOverlay.setBounds(0, 30, 230, 50); 
	    pnlOverlay.setName("overlayHetMon");

	    JLabel lblOut = new JLabel("Hết món", SwingConstants.CENTER);
	    lblOut.setForeground(Color.WHITE);
	    lblOut.setFont(new Font("Arial", Font.BOLD, 16));
	    pnlOverlay.add(lblOut, BorderLayout.CENTER);

	    pnlOverlay.setOpaque(true);
	    pnlOverlay.setVisible(true);

	    pnlItem.setLayout(null);
	    pnlItem.add(pnlOverlay);
	    pnlItem.setComponentZOrder(pnlOverlay, 0);

	    pnlItem.revalidate();
	    pnlItem.repaint();
	}

	

	public double tinhTongTien(JTable tblForm) {
	    double tong = 0;
	    DefaultTableModel model = (DefaultTableModel) tblForm.getModel();

	    for (int i = 0; i < model.getRowCount(); i++) {
	        String thanhTienStr = model.getValueAt(i, 6).toString();
	        thanhTienStr = thanhTienStr.replace("VNĐ", "").replace(",", "").trim();

	        try {
	            double thanhTien = Double.parseDouble(thanhTienStr);
	            tong += thanhTien;
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }

	    return tong;
	}


	private JToggleButton createToggleButton(String text) {
        JToggleButton btn = new JToggleButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(new Color(33, 150, 243));

        // Đổi màu khi được chọn hoặc bỏ chọn
        btn.addItemListener(e -> {
            if (btn.isSelected()) {
                btn.setBackground(Color.RED);
                
            } else {
                btn.setBackground(new Color(33, 150, 243));
            }
        });

        return btn;
    }
	
	// Main
	public static void main(String[] args) {
		connectDB.ConnectDB.getInstance().connect();
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		new DatBanTrucTiep_GUI();
	}
	
	public DefaultTableModel getTableModel() {
	    return (DefaultTableModel) tblForm.getModel();
	}
	
	public static void open() {
	    DatBanTrucTiep_GUI frame = new DatBanTrucTiep_GUI();
	    frame.setVisible(true);
	}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 Object o = e.getSource();
		 if (o == btnTatCa) {
			ArrayList<MonAn> dsMon = MA_dao.getAllMonAn();
	        JPanel pnlMon = foodMenuUI(dsMon);
	        pnlCardButton.removeAll(); 
	        pnlCardButton.add(pnlMon, "TatCa");
	        pnlCardButton.revalidate();
	        pnlCardButton.repaint();
	        cardLayoutButton.show(pnlCardButton, "TatCa");
	     } else if (o == btnDiemTam) {
	        ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Điểm tâm");
	        JPanel pnlMon = foodMenuUI(dsMon);
	        pnlCardButton.revalidate();
	        pnlCardButton.repaint();
	        pnlCardButton.add(pnlMon, "DiemTam");
	        cardLayoutButton.show(pnlCardButton, "DiemTam");
	     } else if (o == btnGoi) {
	        ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Gỏi");
	        JPanel pnlMon = foodMenuUI(dsMon);
	        pnlCardButton.revalidate();
	        pnlCardButton.repaint();
	        pnlCardButton.add(pnlMon, "Goi");
	        cardLayoutButton.show(pnlCardButton, "Goi");
	     }else if (o == btnLauCom) {
	        ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Lẩu-cơm");
	        JPanel pnlMon = foodMenuUI(dsMon);
	        pnlCardButton.revalidate();
	        pnlCardButton.repaint();
	        pnlCardButton.add(pnlMon, "Goi");
	        cardLayoutButton.show(pnlCardButton, "Goi");
	     }else if (o == btnCanhRau) {
	        ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Canh-Rau");
	        JPanel pnlMon = foodMenuUI(dsMon);
	        pnlCardButton.revalidate();
	        pnlCardButton.repaint();
	        pnlCardButton.add(pnlMon, "CanhRau");
	        cardLayoutButton.show(pnlCardButton, "CanhRau");
	     }else if (o == btnHaiSan) {
		     ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Hải sản");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "HaiSan");
		     cardLayoutButton.show(pnlCardButton, "HaiSan");
	     }else if (o == btnMonThem) {
		     ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Món Thêm");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "MonThem");
		     cardLayoutButton.show(pnlCardButton, "MonThem");
		 }else if(o == btnSoup) {
			 ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Soup-Tiềm-Cháo");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "Soup");
		     cardLayoutButton.show(pnlCardButton, "Soup");
		 }else if(o == btnGaBoHeoEch) {
			 ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Gà-Bò-Heo-Ếch-Lươn");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "Ga");
		     cardLayoutButton.show(pnlCardButton, "Ga");
		 }else if(o == btnMiHuTieu) {
			 ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Mì-Miến-Hủ tiếu xào");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "Mi");
		     cardLayoutButton.show(pnlCardButton, "Mi");
		 }else if(o == btnNuocEp) {
			 ArrayList<MonAn> dsMon = MA_dao.locMonAnTheoTenLoai_SP("Sinh tố-Nước ép");
		     JPanel pnlMon = foodMenuUI(dsMon);
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     pnlCardButton.add(pnlMon, "Sinh-to");
		     cardLayoutButton.show(pnlCardButton, "Sinh-to");
		 }else if(o == btnTimMon) {
			 String text = txtTimMon.getText().trim();
			 if (text.isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa món ăn để tìm kiếm.");
				 return;
			 }
			 
			 ArrayList<MonAn> dsMon = MA_dao.getMonAnTheoTenMon(text);
			 JPanel pnlMon = foodMenuUI(dsMon);
		     
		     pnlCardButton.removeAll(); 
		     
		     pnlCardButton.add(pnlMon, "TimMon");
		     pnlCardButton.revalidate();
		     pnlCardButton.repaint();
		     cardLayoutButton.show(pnlCardButton, "TimMon");
		 }else if(o == btnXoaMonAn) {
			 int selectedRow = tblForm.getRowCount();
			 if (selectedRow == 0) {
				 JOptionPane.showMessageDialog(null, "Không có món nào để xóa");
				 return;
			 }
			 
			 int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá hết món này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
                 model.setRowCount(0);
                 tongTien = tinhTongTien(tblForm);
                 lblTongTienValue.setText(String.format("%,.0f VNĐ", tongTien));
                 txaGhiChuKH.setText("");
             }
		 }else if(o == btnTroVe) {
			 isChucnangChuyenBan = false;
			 isChucnangTachBan = false;
			 
			 DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
			 model.setRowCount(0);
			 txtMaBan_Ban.setText("");
			 txtKhuVuc.setText("");
			 txtSLGhe.setText("");
			 lblMaBanValue.setText("");
			 lblTongTienValue.setText(String.format("0 VNĐ"));
			 txaGhiChuKH.setText("");
			 txaGhiChuKH.setEditable(false);
			 lblTrangThai.setText("");
			 lblHoaDon.setText("");
			 lblTieuDe.setText("Đặt bàn trực tiếp");
			 
			 tabPane.setSelectedIndex(0);
			 
			 switch (labelDangChonKVBan) {
				case "Tất cả" -> {
					dsBan = Ban_dao.getAllBan();
				}
				case "KV01" -> {
					dsBan = Ban_dao.getBanNgoaiTroi();
				}
				case "KV02" -> {
					dsBan = Ban_dao.getBanTang1();
				}
				case "KV03" -> {
					dsBan = Ban_dao.getBanTang2();
				}
				case "KV04" -> {
					dsBan = Ban_dao.getBanSanThuong();
				}
			 }
			 loadBanList.run();
			 
		 }else if(o == btnChuyenBan) {		
			 lblTieuDe.setText("Chuyển bàn");
			 
			// Lấy sức chứa của bàn hiện tại
			 String maBanHienTai = txtMaBan_Ban.getText();
			 if (maBanHienTai.isEmpty()) {
			    JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn có khách muốn chuyển.");
			    return;
			 }

			 HoaDon hoaDonHienTai = HD_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
			 if (hoaDonHienTai == null) {
			    JOptionPane.showMessageDialog(null, "Phải chọn bàn đang phục vụ khách.");
			    return;
			 }
			 
			 sucChuaHienTai = hoaDonHienTai.getSoKhach();

			 // Lọc danh sách bàn trống phù hợp
			 ArrayList<Ban> danhSachBanPhuHop = Ban_dao.getBanPhuHopChoChuyenBan(sucChuaHienTai);

			 if (danhSachBanPhuHop.isEmpty()) {
			    JOptionPane.showMessageDialog(null, "Không có bàn trống phù hợp để chuyển.");
			    return;
			 }

			 // Cập nhật danh sách bàn hiển thị
			 switch (labelDangChonKVBan) {
			    case "Tất cả" -> {
			        dsBan = new ArrayList<>(danhSachBanPhuHop); 
			    }
			    case "KV01" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV01".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV02" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV02".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV03" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV03".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV04" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV04".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			}
			loadBanList.run();
				
			 tabPane.setSelectedIndex(0);
			 isChucnangChuyenBan = true; 
			 maBanCu = txtMaBan_Ban.getText();
			 
		 }else if(o == btnTachBan) {
			 lblTieuDe.setText("Tách bàn");
			 String maBanHienTai = txtMaBan_Ban.getText();
			 if (maBanHienTai.isEmpty()) {
			    JOptionPane.showMessageDialog(null, "Vui lòng chọn bàn có khách muốn tách.");
			    return;
			 }
			 
			 HoaDon hoaDon = HD_dao.timHoaDonTheoBanVaTrangThai(maBanHienTai, 0);
			 if (hoaDon == null) {
			    JOptionPane.showMessageDialog(null, "Phải chọn bàn đang phục vụ khách.");
			    return;
			 }
			 
			 // Lọc danh sách bàn trống phù hợp
			 ArrayList<Ban> danhSachBanPhuHop = Ban_dao.getBanTheoTrangThai(1);

			 if (danhSachBanPhuHop.isEmpty()) {
			    JOptionPane.showMessageDialog(null, "Không có bàn trống phù hợp để chuyển.");
			    return;
			 }

			 // Cập nhật danh sách bàn hiển thị
			 switch (labelDangChonKVBan) {
			    case "Tất cả" -> {
			        dsBan = new ArrayList<>(danhSachBanPhuHop); 
			    }
			    case "KV01" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV01".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV02" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV02".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV03" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV03".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			    case "KV04" -> {
			        dsBan = danhSachBanPhuHop.stream()
			            .filter(b -> "KV04".equals(b.getKV().getMaKV()))
			            .collect(Collectors.toCollection(ArrayList::new));
			    }
			}
			loadBanList.run();
				
			 tabPane.setSelectedIndex(0); 
			 maBanCu = txtMaBan_Ban.getText();
			 isChucnangTachBan = true;
	 
		 }else if(o == btnThanhToan) {
			 if (tblForm.getRowCount() == 0) {
				 JOptionPane.showMessageDialog(null, "Không đơn nào để thanh toán");
				 return;
			 }
 
			 if(chkMangVe.isSelected() == true) {
				 hoaDonGui = new HoaDon_GUI(this, HD_dao.taoMaHoaDonTuDong(), lblMaBanValue.getText(), 0, nv.getMaNV(), txaGhiChuKH.getText(), loadBanList, labelDangChonKVBan, dsBan);
			 }else {
				 hoaDonGui = new HoaDon_GUI(this, lblHoaDon.getText(), lblMaBanValue.getText(), 0, nv.getMaNV(), txaGhiChuKH.getText(), loadBanList, labelDangChonKVBan, dsBan);
			 }
			 
			 this.setVisible(true);
			 hoaDonGui.setVisible(true);
			 

		 }
		 else if(o == btnTamTinh) {	 
			 if (tblForm.getRowCount() == 0) {
				 JOptionPane.showMessageDialog(null, "Không đơn nào để tạm tính");
				 return;
			 }
			 
			 JPanel pnlTamTinh = new JPanel(new BorderLayout(10, 10));
			 pnlTamTinh.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
			 
			 int soKhach = 0;
			 if (!txtSLKH.getText().trim().isEmpty()) {
				 soKhach = Integer.parseInt(txtSLKH.getText());
			 }else {
				 soKhach = Integer.parseInt(txtSLGhe.getText());
			 }
			 
			 LocalDateTime now = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			 String formattedDateTime = now.format(formatter);
			 
			 if (!lblHoaDon.getText().trim().isEmpty()) {
				 HoaDon h = HD_dao.timHoaDonTheoBanVaTrangThai(lblMaBanValue.getText(), 0);
				 hd =  new HoaDon(lblHoaDon.getText(), h.getThoiGianThanhToan(), 0, 0, null, new Ban(lblMaBanValue.getText()), null, null, h.getSoKhach(), nv, null, h.getThoiGianTaoDon(), 0, txaGhiChuKH.getText(), 0, 0, null);
			 }else {
				hd = new HoaDon(HD_dao.taoMaHoaDonTuDong(), null, 0, 0, null, new Ban(lblMaBanValue.getText()), null, null, soKhach, nv, null, now, 0, txaGhiChuKH.getText(), 0, 0, null);
			 }
			 
			// Tiêu đề
			 JLabel lblTamTinh = new JLabel("HÓA ĐƠN TẠM TÍNH", SwingConstants.CENTER);
			 lblTamTinh.setForeground(new Color(40, 167, 69));
			 lblTamTinh.setFont(new Font("Segoe UI", Font.BOLD, 18));
			 pnlTamTinh.add(lblTamTinh, BorderLayout.NORTH);
			 
			// Panel nội dung (chi tiết hóa đơn)
			 JPanel pnlContent = new JPanel(new GridBagLayout());
			 GridBagConstraints gbc = new GridBagConstraints();
			 gbc.insets = new Insets(5, 10, 5, 10);
			 gbc.anchor = GridBagConstraints.WEST;
			 gbc.fill = GridBagConstraints.HORIZONTAL;
			 gbc.gridx = 0;
			 gbc.gridy = 0;
			 
			 Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
			 
			// Dòng 1: Mã hóa đơn
			 pnlContent.add(new JLabel("Mã hóa đơn:", JLabel.RIGHT), gbc);
			 gbc.gridx = 1;
			 pnlContent.add(new JLabel(hd.getMaHD()), gbc);

			 // Dòng 2: Mã nhân viên
			 gbc.gridx = 0; gbc.gridy++;
			 pnlContent.add(new JLabel("Mã nhân viên:", JLabel.RIGHT), gbc);
			 gbc.gridx = 1;
			 pnlContent.add(new JLabel(hd.getNV().getMaNV()), gbc);

			 // Dòng 3: Mã bàn
			 gbc.gridx = 0; gbc.gridy++;
			 pnlContent.add(new JLabel("Mã bàn:", JLabel.RIGHT), gbc);
			 gbc.gridx = 1;
			 pnlContent.add(new JLabel(hd.getBan().getMaBan()), gbc);

			 // Dòng 4: Ngày giờ
			 gbc.gridx = 0; gbc.gridy++;
			 pnlContent.add(new JLabel("Ngày giờ:", JLabel.RIGHT), gbc);
			 gbc.gridx = 1;
			 pnlContent.add(new JLabel(formattedDateTime), gbc);

			 // Dòng 5: Tổng tiền
			 gbc.gridx = 0; gbc.gridy++;
			 pnlContent.add(new JLabel("Tổng tiền:", JLabel.RIGHT), gbc);
			 gbc.gridx = 1;
			 JLabel lblTien = new JLabel(lblTongTienValue.getText());
			 lblTien.setFont(new Font("Segoe UI", Font.BOLD, 14));
			 lblTien.setForeground(new Color(0, 102, 204));
			 pnlContent.add(lblTien, gbc);

			 // Thêm nội dung vào giao diện chính
			 pnlTamTinh.add(pnlContent, BorderLayout.CENTER);
			 
			// Hiển thị dialog
			int result = JOptionPane.showConfirmDialog(
			    null,
			    pnlTamTinh,
			    "Thông tin tạm tính",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.PLAIN_MESSAGE
			);

			if (result == JOptionPane.OK_OPTION) {
				
				if (!lblHoaDon.getText().trim().isEmpty()) {	
					Cthd_dao.deleteChiTietHoaDonTheoMaHD(hd.getMaHD());
					HD_dao.updateHoaDon(hd);
					
				}else {
					HD_dao.insertHoaDon(hd);
				}
			    
			    
			    maHoaDon = hd.getMaHD();
				
				// Thêm món ăn vào chi tiết hóa đơn
				DefaultTableModel model = (DefaultTableModel) tblForm.getModel();
				int soMon = 0;
				ArrayList<ChiTietHoaDon> dsChiTietHoaDons = new ArrayList<ChiTietHoaDon>();
				for (int i = 0; i < model.getRowCount(); i++) {
				    String tenMon = "";
				    String ghiChu = "";

				    Object cellValue = model.getValueAt(i, 1);
				    String current = cellValue != null ? cellValue.toString() : "";

				    // Tách tên món và ghi chú từ HTML
				    if (current.startsWith("<html>")) {
				        current = current.substring(6, current.length() - 7); // bỏ <html> và </html>
				        String[] parts = current.split("<br>");
				        if (parts.length >= 1) tenMon = parts[0].trim();

				        if (parts.length >= 2) {
				            String ghiChuPart = parts[1]; // ví dụ: <i style='...'>ghi chú</i>
				            int tagOpen = ghiChuPart.indexOf(">");
				            int tagClose = ghiChuPart.indexOf("</i>");
				            if (tagOpen != -1 && tagClose != -1) {
				                ghiChu = ghiChuPart.substring(tagOpen + 1, tagClose).trim();
				            }
				        }
				    } else {
				        tenMon = current.trim();
				    }

				    // Lấy số lượng
				    int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());
				    if (tenMon.equals("Khăn Lạnh") || 
				    	    tenMon.equals("Nước Suối") || 
				    	    tenMon.equals("Sting") || 
				    	    tenMon.equals("Bia")) {
				    	    soMon += 0; 
				    	} else {
				    	    soMon += soLuong;
				    	}

				    // Lấy đơn giá
				    String donGiaStr = model.getValueAt(i, 5).toString().replace("VNĐ", "").replace(",", "").trim();
				    double donGia = Double.parseDouble(donGiaStr);

				    // Tạo chi tiết hóa đơn
				    ChiTietHoaDon cthd = new ChiTietHoaDon(
					    new HoaDon(hd.getMaHD()),
					    new MonAn(MA_dao.timMaMonAnTheoTen(tenMon)),
					    soLuong,
					    ghiChu
					);
				    Cthd_dao.insertChiTietHoaDon(cthd);
				    dsChiTietHoaDons.add(cthd); 
				}    
				
				if(lblHoaDon.getText().trim().isEmpty() && !txtSLKH.getText().trim().isEmpty()) {
					int soKhach1 = Integer.parseInt(txtSLKH.getText());
					LocalDateTime thoiGianDuKien = modelDuDoanThoiGianAn.duDoanThoiGian(soKhach1, soMon, now);
					hd.setThoiGianThanhToan(thoiGianDuKien);
				}else if(lblHoaDon.getText().trim().isEmpty() && txtSLKH.getText().trim().isEmpty()) {
					int soKhach1 = Integer.parseInt(txtSLGhe.getText());
					LocalDateTime thoiGianDuKien = modelDuDoanThoiGianAn.duDoanThoiGian(soKhach1, soMon, now);
					hd.setThoiGianThanhToan(thoiGianDuKien);	
				}else if(!lblHoaDon.getText().trim().isEmpty()) {
					int soKhach1 = hd.getSoKhach();
					LocalDateTime thoiGianDuKien = modelDuDoanThoiGianAn.duDoanThoiGian(soKhach1, soMon, now);
					hd.setThoiGianThanhToan(thoiGianDuKien);
				}
				
				hd.setDanhSachChiTiet(dsChiTietHoaDons);
				HD_dao.updateHoaDon(hd);
				
				 // Thay đổi trạng thái bàn
				Ban_dao.capNhatTrangThaiBan(hd.getBan().getMaBan(), 2);
				switch (labelDangChonKVBan) {
				case "Tất cả" -> {
					dsBan = Ban_dao.getAllBan();
				}
				case "KV01" -> {
					dsBan = Ban_dao.getBanNgoaiTroi();
				}
				case "KV02" -> {
					dsBan = Ban_dao.getBanTang1();
				}
				case "KV03" -> {
					dsBan = Ban_dao.getBanTang2();
				}
				case "KV04" -> {
					dsBan = Ban_dao.getBanSanThuong();
				}
				}
				loadBanList.run();
				
				 model.setRowCount(0);
				 txtMaBan_Ban.setText("");
				 txtKhuVuc.setText("");
				 txtSLGhe.setText("");
				 lblMaBanValue.setText("");
				 lblTongTienValue.setText(String.format("0 VNĐ"));
				 txaGhiChuKH.setText("");
				 lblTrangThai.setText("");
				 lblHoaDon.setText("");
				 txtSLKH.setText("");
			}
		 
		 }
		 
	 }
}
