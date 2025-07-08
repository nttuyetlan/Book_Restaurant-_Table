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

public class MenuQuanLy_GUI extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
	private NhanVien currentUser;
	private CardLayout cardLayout;
	private JPanel pnlMain;
	private JMenu mnuQuanLy;
	private JMenu mnuThongKe;
	private JMenu mnuTimKiem;
	private JMenu mnuTroGiup;
	private JMenuItem mniQLKH;
	private JMenuItem mniQLKM;
	private JMenuItem mniQLNV;
	private JMenuItem mniTKDoanhThu;
	private JMenuItem mniTKMonAn;
	private JMenuItem mniQLMonAn;
	private JMenuItem mniQLKV_Ban;
	private JMenuItem mniChungToi;
	private JMenuItem mniHDSD;
	private JMenuItem mniDangXuat;
	private JMenuBar mnuFunction;
	private JMenu mnuDatBan;
	private JMenu mnuQuanLyDonDatBan;
	private JMenuItem mniDatBanTruoc;
	private JMenuItem mniDatBanTrucTiep;
	private JMenuItem mniDSDonDatBanTruoc;
	private JMenuItem mniDSDonDatBanTrucTiep;
	private DanhSachDatBanTruoc_GUI dsdbt;
	private DatBanTruoc_GUI01 dbt;
    
    public MenuQuanLy_GUI() {
        this(null);
    }
    
    public MenuQuanLy_GUI(NhanVien user) {
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
        
        QuanLyKhuyenMai_GUI qlKM = new QuanLyKhuyenMai_GUI(currentUser);
		JPanel pnlQLKM = qlKM.initUI();
		pnlMain.add(pnlQLKM, "QLKhuyenMai");
		
		QuanLyMonAn_GUI qlMA = new QuanLyMonAn_GUI();
		JPanel pnlQLMA = qlMA.initUI();
		pnlMain.add(pnlQLMA, "QLMonAn");
		
		QuanLyKhuVuc_GUI qlKV = new QuanLyKhuVuc_GUI(currentUser);
		JPanel pnlQLKV = qlKV.initUI();
		pnlMain.add(pnlQLKV, "QLKhuVuc_Ban");
		
		QuanLyKhachHang_GUI qlKH = new QuanLyKhachHang_GUI(currentUser);
		JPanel pnlQLKH = qlKH.initUI();
		pnlMain.add(pnlQLKH, "QLKhachHang");
		
		QuanLyNhanVien_GUI qlNV = new QuanLyNhanVien_GUI();
		JPanel pnlQLNV = qlNV.initUI();
		pnlMain.add(pnlQLNV, "QLNhanVien");
		
		ThongKeDoanhThu_GUI tkDT = new ThongKeDoanhThu_GUI(currentUser);
		JPanel pnlTKDoanhThu = tkDT.initUI_MenuThongKe();
		pnlMain.add(pnlTKDoanhThu, "TKDoanhThu");
		
		ThongKeMonAn_GUI tkMA = new ThongKeMonAn_GUI();
		pnlMain.add(tkMA, "TKMonAn");
		
		DanhSachDatBanTruoc_GUI dsdbt = new DanhSachDatBanTruoc_GUI(currentUser, this);
		JPanel pnlDSDBT = dsdbt.initUI();
		pnlMain.add(pnlDSDBT, "DSDatBanTruoc");
		
		DanhSachDatBanTrucTiep_GUI dsdbtt = new DanhSachDatBanTrucTiep_GUI(currentUser);
		JPanel pnlDSDBTT = dsdbtt.initUI_Content();
		pnlMain.add(pnlDSDBTT, "DSDatBanTrucTiep");
		
		AboutUs_GUI about = new AboutUs_GUI(currentUser);
		JPanel pnlAbout = about.initUI();
		pnlMain.add(pnlAbout, "AboutUs");
		
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
        mnuDatBan = new JMenu("Đặt Bàn");
        mnuQuanLy= new JMenu("Quản Lý");
        mnuThongKe = new JMenu("Thống Kê");
        mnuQuanLyDonDatBan = new JMenu("Quản Lý Đơn Đặt Bàn");
        mnuTroGiup = new JMenu("Trợ Giúp");
        
        // Set foreground for all menus
        mnuDatBan.setForeground(Color.WHITE);
        mnuQuanLy.setForeground(Color.WHITE);
        mnuThongKe.setForeground(Color.WHITE);
        mnuQuanLyDonDatBan.setForeground(Color.WHITE);
        mnuTroGiup.setForeground(Color.WHITE);
        
        // Set opaque for all menus
        mnuDatBan.setOpaque(true);
        mnuQuanLy.setOpaque(true);
        mnuThongKe.setOpaque(true);
        mnuQuanLyDonDatBan.setOpaque(true);
        mnuTroGiup.setOpaque(true);

        // Menu items
        mniDatBanTruoc = new JMenuItem("Đặt Bàn Trước");
        mniDatBanTrucTiep = new JMenuItem("Đặt Bàn Trực Tiếp");
        mniQLKM = new JMenuItem("Quản lý Khuyến Mãi");
        mniQLMonAn = new JMenuItem("Quản lý Món Ăn");
        mniQLKV_Ban = new JMenuItem("Quản lý Khu Vực");
        mniQLKH = new JMenuItem("Quản lý Khách Hàng");
        mniQLNV = new JMenuItem("Quản lý Nhân Viên");
        mniTKDoanhThu = new JMenuItem("Thống kê Doanh Thu");
        mniTKMonAn = new JMenuItem("Thống kê Món Ăn");
        mniDSDonDatBanTruoc = new JMenuItem("Danh Sách Đơn Đặt Bàn Trước");
        mniDSDonDatBanTrucTiep = new JMenuItem("Danh Sách Đơn Đặt Bàn Trực Tiếp");
        mniHDSD = new JMenuItem("Hướng dẫn sử dụng");
        mniChungToi = new JMenuItem("Chúng tôi");
        
        mniDatBanTruoc.addActionListener(this);
        mniDatBanTrucTiep.addActionListener(this);
        mniQLKM.addActionListener(this);
        mniQLMonAn.addActionListener(this);
        mniQLKV_Ban.addActionListener(this);
        mniQLKH.addActionListener(this);
        mniQLNV.addActionListener(this);
        mniTKDoanhThu.addActionListener(this);
        mniTKMonAn.addActionListener(this);
        mniDSDonDatBanTruoc.addActionListener(this);
        mniDSDonDatBanTrucTiep.addActionListener(this);
        mniHDSD.addActionListener(this);
        mniChungToi.addActionListener(this);

        // Set preferred size for menu items
        Dimension uniformSize = new Dimension(180, 30);
        mniDatBanTruoc.setPreferredSize(uniformSize);
        mniDatBanTrucTiep.setPreferredSize(uniformSize);
        mniQLKM.setPreferredSize(uniformSize);
        mniQLMonAn.setPreferredSize(uniformSize);
        mniQLKV_Ban.setPreferredSize(uniformSize);
        mniQLKH.setPreferredSize(uniformSize);
        mniQLNV.setPreferredSize(uniformSize);
        mniTKDoanhThu.setPreferredSize(uniformSize);
        mniTKMonAn.setPreferredSize(uniformSize);
        mniDSDonDatBanTruoc.setPreferredSize(new Dimension(240, 30));
        mniDSDonDatBanTrucTiep.setPreferredSize(new Dimension(240, 30));
        mniHDSD.setPreferredSize(uniformSize);
        mniChungToi.setPreferredSize(uniformSize);

        // Add menu items to respective menus
        mnuDatBan.add(mniDatBanTruoc);
        mnuDatBan.add(mniDatBanTrucTiep);
        mnuQuanLy.add(mniQLKM);
        mnuQuanLy.add(mniQLMonAn);
        mnuQuanLy.add(mniQLKV_Ban);
        mnuQuanLy.add(mniQLKH);
        mnuQuanLy.add(mniQLNV);
        mnuThongKe.add(mniTKDoanhThu);
        mnuThongKe.add(mniTKMonAn);
        mnuQuanLyDonDatBan.add(mniDSDonDatBanTruoc);
        mnuQuanLyDonDatBan.add(mniDSDonDatBanTrucTiep);
        mnuTroGiup.add(mniHDSD);
        mnuTroGiup.add(mniChungToi);

        // Set menu dimensions
        int menuHeight = 50;
        int defaultWidth = 120;
        
        mnuDatBan.setPreferredSize(new Dimension(defaultWidth, menuHeight));
        mnuQuanLy.setPreferredSize(new Dimension(defaultWidth, menuHeight));
        mnuThongKe.setPreferredSize(new Dimension(defaultWidth, menuHeight));
        mnuQuanLyDonDatBan.setPreferredSize(new Dimension(180, menuHeight));
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
        mnuFunction.add(mnuDatBan);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuQuanLy);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuThongKe);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuQuanLyDonDatBan);
        mnuFunction.add(Box.createHorizontalStrut(50));
        mnuFunction.add(mnuTroGiup);
        mnuFunction.add(Box.createHorizontalGlue());
        
        mnuFunction.add(lblrightIcon); 
        mnuFunction.add(Box.createHorizontalStrut(30));

        return mnuFunction;
        
	}
	
	public void showDatBanTruocAndLoad(String maDonDatBanTruoc) {
	    cardLayout.show(pnlMain, "DatBanTruoc");
	    if (dbt != null) {
	    	dbt.loadThongTinDonDatBanTruoc(maDonDatBanTruoc);
	       }
	   
	}
    
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
		if(source.equals(mniDatBanTruoc)) {
			cardLayout.show(pnlMain, "DatBanTruoc");
		} else if(source.equals(mniDatBanTrucTiep)) {
			cardLayout.show(pnlMain, "DatBanTrucTiep");
		} else if (source.equals(mniQLKM) ) {
			cardLayout.show(pnlMain, "QLKhuyenMai");
		} else if (source.equals(mniQLMonAn)) {
			cardLayout.show(pnlMain, "QLMonAn");
		} else if (source.equals(mniQLKV_Ban)) {
			cardLayout.show(pnlMain, "QLKhuVuc_Ban");
		} else if (source.equals(mniQLKH)) {
			cardLayout.show(pnlMain, "QLKhachHang");
		}else if (source.equals(mniQLNV)) {
			cardLayout.show(pnlMain, "QLNhanVien");
		}else if (source.equals(mniTKDoanhThu)) {
			cardLayout.show(pnlMain, "TKDoanhThu");
		}else if (source.equals(mniTKMonAn)) {
			cardLayout.show(pnlMain, "TKMonAn");
		}else if(source.equals(mniDSDonDatBanTruoc)) {
			cardLayout.show(pnlMain, "DSDatBanTruoc");
		}else if(source.equals(mniDSDonDatBanTrucTiep)) {
			cardLayout.show(pnlMain, "DSDatBanTrucTiep");
		}else if (source.equals(mniChungToi)) {
			cardLayout.show(pnlMain, "AboutUs");
		}else if(source.equals(mniHDSD)) {
			HuongDanSuDung_GUI hdsd = new HuongDanSuDung_GUI();
			hdsd.showGuide();
		}
	}
     
}
