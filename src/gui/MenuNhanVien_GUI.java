package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import entity.NhanVien;

public class MenuNhanVien_GUI extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private JMenuBar mnuFunction;
    private JMenu mnuDatBanTrucTiep, mnuTimKiem, mnuTroGiup;
    private JMenuItem mniDBTT1, mniDBTT2, mniDBT1, mniDBT2, mniMoCa, mniKetCa;
    private JMenuItem mniTKDonDatBan, mniTKDonDatMon, mniTKBan, mniTKMonAn, mniKhuyenMai, mniTKKH, mniTKNV, mniHDSD, mniChungToi;
    private NhanVien currentUser;
	private JMenuItem mniTKDonDatBanTruoc;
	private JMenuItem mniTKDonDatBanTrucTiep;
	private JMenuItem mniDangXuat;
	private CardLayout cardLayout;
	private JPanel pnlMain;
	private JMenuItem mniDatBanTrucTiep, mniDatBanTruoc, mniGiaoCa;
	private JMenuItem mniQuanLyKhachHang;
	private DatBanTruoc_GUI01 dbt;
    
    public MenuNhanVien_GUI() {
        this(null);
    }
    
    public MenuNhanVien_GUI(NhanVien user) {
        this.currentUser = user;

        setLayout(new BorderLayout());
        
        // Menu
        JMenuBar mnuFunction = initUI();
        
        // CardPane
        cardLayout = new CardLayout();
        pnlMain = new JPanel(cardLayout);
        
        ThongKeCaLamViecDau_GUI tkclvd = new ThongKeCaLamViecDau_GUI(currentUser);
		JPanel pnlCaLamViecDau = tkclvd.initUI();
		pnlMain.add(pnlCaLamViecDau, "TKCaLamViecDau");
        
        DatBanTrucTiep_GUI dbtt = new DatBanTrucTiep_GUI(currentUser);
		JPanel pnlDBTT = dbtt.splitMain();
		pnlMain.add(pnlDBTT, "DatBanTrucTiep");
		
		dbt = new DatBanTruoc_GUI01(currentUser);
		JPanel pnlDBT = dbt.setupSplitPane();
		pnlMain.add(pnlDBT, "DatBanTruoc");
		
		ThongKeCaLamViecDau_GUI gc = new ThongKeCaLamViecDau_GUI(currentUser);
		JPanel pnlGiaoCa = gc.initUI();
		pnlMain.add(pnlGiaoCa, "GiaoCa");
		
		QuanLyKhachHang_GUI qlKH = new QuanLyKhachHang_GUI(currentUser);
		JPanel pnlQLKH = qlKH.initUI();
		pnlMain.add(pnlQLKH, "QuanLyKH");
		
		DanhSachDatBanTruoc_GUI dsdbt = new DanhSachDatBanTruoc_GUI(currentUser);
		JPanel pnlDSDBT = dsdbt.initUI();
		pnlMain.add(pnlDSDBT, "DSDatBanTruoc");
		
		DanhSachDatBanTrucTiep_GUI dsdbtt = new DanhSachDatBanTrucTiep_GUI(currentUser);
		JPanel pnlDSDBTT = dsdbtt.initUI_Content();
		pnlMain.add(pnlDSDBTT, "DSDatBanTrucTiep");
		
        TaiKhoan_GUI tk = new TaiKhoan_GUI(currentUser, this);
        JPanel pnlTaiKhoan = tk.setupLayout();
        pnlMain.add(pnlTaiKhoan, "TaiKhoan");
        
        add(mnuFunction, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


	private JMenuBar initUI() {
        UIManager.put("Menu.selectionBackground", new Color(204, 204, 204));
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.selectionBackground", new Color(229, 229, 229));
        UIManager.put("Menu.background", new Color(30, 129, 191));
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("MenuItem.background", new Color(255, 255, 255));
        UIManager.put("MenuItem.foreground", new Color(0, 0, 0));
        UIManager.put("MenuItem.font", new Font("Arial", Font.PLAIN, 13));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("MenuItem.selectionBackground", new Color(230, 240, 255));
        UIManager.put("MenuItem.selectionForeground", new Color(0, 100, 200));
        UIManager.put("MenuItem.opaque", true);

        setLayout(new BorderLayout());

        // Main menus
        mniDatBanTrucTiep = new JMenuItem("Đặt Bàn Trực Tiếp");
        mniDatBanTruoc = new JMenuItem("Đặt Bàn Trước");
        mniGiaoCa = new JMenuItem("Giao Ca");
        mniQuanLyKhachHang = new JMenuItem("Quản Lý Khách Hàng");
        mnuTimKiem = new JMenu("Tìm kiếm");
        mnuTroGiup = new JMenu("Trợ Giúp");
        
        
        mniDatBanTrucTiep.setBackground(new Color(30, 129, 191));
        mniDatBanTrucTiep.setForeground(new Color(255, 255, 255));
        mniDatBanTruoc.setBackground(new Color(30, 129, 191));
        mniDatBanTruoc.setForeground(new Color(255, 255, 255));
        mniGiaoCa.setBackground(new Color(30, 129, 191));
        mniGiaoCa.setForeground(new Color(255, 255, 255));
        mniQuanLyKhachHang.setBackground(new Color(30, 129, 191));
        mniQuanLyKhachHang.setForeground(new Color(255, 255, 255));
        
        // Set foreground for all menus
        mniDatBanTruoc.setForeground(Color.WHITE);
        mniQuanLyKhachHang.setForeground(Color.WHITE);
        mnuTimKiem.setForeground(Color.WHITE);
        mnuTroGiup.setForeground(Color.WHITE);
        
        // Set opaque for all menus
        mniDatBanTrucTiep.setOpaque(true);
        mniDatBanTruoc.setOpaque(true);
        mniGiaoCa.setOpaque(true);
        mniQuanLyKhachHang.setOpaque(true);
        mnuTimKiem.setOpaque(true);
        mnuTroGiup.setOpaque(true);

        // Menu items
        mniTKDonDatBanTruoc = new JMenuItem("Đơn đặt bàn trước");
        mniTKDonDatBanTrucTiep = new JMenuItem("Đơn đặt bàn trực tiếp");
        mniHDSD = new JMenuItem("Hướng dẫn sử dụng");
        mniChungToi = new JMenuItem("Chúng tôi");
        mniDangXuat = new JMenuItem("Đăng xuất");
        
        mniDatBanTrucTiep.addActionListener(this);
        mniDatBanTruoc.addActionListener(this);
        mniGiaoCa.addActionListener(this);
        mniQuanLyKhachHang.addActionListener(this);
        mniTKDonDatBanTruoc.addActionListener(this);
        mniTKDonDatBanTrucTiep.addActionListener(this);
        mniHDSD.addActionListener(this);
        mniChungToi.addActionListener(this);
        mniDangXuat.addActionListener(this);

        // Set preferred size for menu items
        Dimension uniformSize = new Dimension(180, 30);
        mniTKDonDatBanTruoc.setPreferredSize(uniformSize);
        mniTKDonDatBanTrucTiep.setPreferredSize(uniformSize);
        mniHDSD.setPreferredSize(uniformSize);
        mniChungToi.setPreferredSize(uniformSize);
        mniDangXuat.setPreferredSize(uniformSize);

        // Add menu items to respective menus
        mnuTimKiem.add(mniTKDonDatBanTruoc);
        mnuTimKiem.add(mniTKDonDatBanTrucTiep);
        mnuTroGiup.add(mniHDSD);
        mnuTroGiup.add(mniChungToi);
        mnuTroGiup.add(mniDangXuat);

        // Set menu dimensions
        int menuHeight = 50;
        int defaultWidth = 120;

        mniDatBanTrucTiep.setPreferredSize(new Dimension(defaultWidth - 150, menuHeight));
        mniDatBanTruoc.setPreferredSize(new Dimension(defaultWidth - 150, menuHeight));
        mniGiaoCa.setPreferredSize(new Dimension(defaultWidth - 150, menuHeight));
        mniQuanLyKhachHang.setPreferredSize(new Dimension(150, menuHeight));
        mnuTimKiem.setPreferredSize(new Dimension(defaultWidth, menuHeight));
        mnuTroGiup.setPreferredSize(new Dimension(defaultWidth, menuHeight));

        mnuFunction = new JMenuBar();
        mnuFunction.setLayout(new BoxLayout(mnuFunction, BoxLayout.X_AXIS));
        mnuFunction.setBackground(new Color(30, 129, 191));
        mnuFunction.setOpaque(true);
        
        // Tạo icon bên trái
        ImageIcon leftIconImage = new ImageIcon("resource/icon/Home.png");
        Image scaledLeftImage = leftIconImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblleftIcon = new JLabel(new ImageIcon(scaledLeftImage));
        lblleftIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        lblleftIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(pnlMain, "TKCaLamViecDau");  
            }

            @Override
            public void mouseEntered(MouseEvent e) { 
            	lblleftIcon.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	lblleftIcon.setOpaque(false);
            	lblleftIcon.setBackground(null);
            }
        });

        // Tạo icon bên phải
        ImageIcon rightIconImage = new ImageIcon("resource/icon/account.png");
        Image scaledRightImage = rightIconImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblrightIcon = new JLabel(new ImageIcon(scaledRightImage));
        lblrightIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        lblrightIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	cardLayout.show(pnlMain, "TaiKhoan");
            }

            @Override
            public void mouseEntered(MouseEvent e) { 
            	lblleftIcon.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	lblrightIcon.setOpaque(false);
            	lblrightIcon.setBackground(null);
            }
        });

        
        
        mnuFunction.add(Box.createHorizontalStrut(30));  
        mnuFunction.add(lblleftIcon);
        mnuFunction.add(Box.createHorizontalGlue());
        mnuFunction.add(mniDatBanTrucTiep);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mniDatBanTruoc);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mniGiaoCa);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mniQuanLyKhachHang);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuTimKiem);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuTroGiup);
        mnuFunction.add(Box.createHorizontalGlue());
        
        mnuFunction.add(lblrightIcon); 
        mnuFunction.add(Box.createHorizontalStrut(30));

        return mnuFunction;
        
    }

//	public void showDatBanTruocAndLoad(String maDonDatBanTruoc) {
//	    cardLayout.show(pnlMain, "DatBanTruoc");
//	    if (dbt != null) {
//	    	dbt.loadThongTinDonDatBanTruoc(maDonDatBanTruoc);
//	       }
//	   
//	}
    
    // Getters for menu items so they can be accessed from outside
//    public JMenuItem getMniTKDonDatBan() { return mniTKDonDatBanTruoc; }
//    public JMenuItem getMniTKDonDatMon() { return mniTKDonDatBanTrucTiep; }
//    public JMenuItem getMniHDSD() { return mniHDSD; }
//    public JMenuItem getMniChungToi() { return mniChungToi; }
    
    // Get the menu bar component
//    public JMenuBar getMenuBar() {
//        return mnuFunction;
//    }
    
    // Method to update logged-in user information
    public void updateUser(NhanVien user) {
        this.currentUser = user;
    }
    
    // Method to enable/disable menu items based on user roles (if needed)
    public void updateMenuItemsByRole(boolean isAdmin) {
        // Example: Only show certain menus based on user role
        // mnuGiaoCa.setVisible(isAdmin);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if (source.equals(mniDatBanTrucTiep) ) {
			cardLayout.show(pnlMain, "DatBanTrucTiep");
		} else if (source.equals(mniDatBanTruoc)) {
			cardLayout.show(pnlMain, "DatBanTruoc");
		} else if (source.equals(mniGiaoCa)) {
			cardLayout.show(pnlMain, "GiaoCa");
		} else if (source.equals(mniQuanLyKhachHang)) {
			cardLayout.show(pnlMain, "QuanLyKH");
		}else if (source.equals(mniTKDonDatBanTruoc)) {
			cardLayout.show(pnlMain, "DSDatBanTruoc");
		}else if (source.equals(mniTKDonDatBanTrucTiep)) {
			cardLayout.show(pnlMain, "DSDatBanTrucTiep");
		}
	}
     
}
