package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.channels.Pipe.SourceChannel;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;

import dao.DonDatBanTruoc_DAO;
import dao.HoaDon_DAO;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.NhanVien;

public class ThongKeDoanhThu_GUI extends JFrame implements ActionListener, MouseListener{

	private JMenu mnuQuanLy;
	private JMenu mnuThongKe;
	private JMenu mnuTimKiem;
	private JMenu mnuTroGiup;
	private JMenuBar mnuFunction;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JDateChooser dateFrom;
	private JDateChooser dateTo;
	private JPanel pnlBieuDo;
	private NhanVien nv;
	private JComboBox cmbLoaiTK;
	private JPanel panel2;
	private JPanel pnlBieuDo1;
	private JComboBox cmbNamMacDinh;
	private JComboBox cmbThangMacDinh;
	private JComboBox cmbThang;
	private JComboBox cmbNam;
	private int currentMonth;
	private int currentYear;
	private JButton btnXemBieuDo;
	private JLabel lblThang;
	private JLabel lblNam;
	
	private static HoaDon_DAO hd_dao = new HoaDon_DAO();
	private static DonDatBanTruoc_DAO ddbt_dao = new DonDatBanTruoc_DAO();
	
	public ThongKeDoanhThu_GUI(NhanVien nv) {
    	setTitle("Thống kê");
    	this.nv = nv;
    }
	
	public ThongKeDoanhThu_GUI() {
		this(null); 
	}
    
	public JPanel initUI_MenuThongKe() {
	    JPanel pnlMain = new JPanel();
	    pnlMain.setLayout(new BorderLayout(0, 15));
	    pnlMain.setBackground(new Color(228, 228, 228));
	    
	    JPanel pnlMenu = new JPanel();
	    pnlMenu.setBackground(Color.WHITE);

	    JLabel lblThongKe = new JLabel("THỐNG KÊ DOANH THU", SwingConstants.LEFT);
	    lblThongKe.setFont(new Font("Arial", Font.BOLD, 20));
	    lblThongKe.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 300));
	    lblThongKe.setForeground(new Color(0x11C90D));

	    // Panel chứa các tab
	    JPanel pnlTab = new JPanel();
	    pnlTab.setLayout(new BoxLayout(pnlTab, BoxLayout.X_AXIS)); 

	    pnlTab.add(Box.createRigidArea(new Dimension(80, 0)));
	    pnlTab.setBorder(BorderFactory.createEmptyBorder(10, 300, 10, 30));
	    pnlTab.setBackground(Color.WHITE);

	    // Các panel con tương ứng
	    JPanel pnlDoanhThu = initUI_DoanhThu();

	    // Thêm vào panel chính
	    pnlMenu.add(lblThongKe);
	    pnlMenu.add(pnlTab);
	    pnlMain.add(pnlMenu, BorderLayout.NORTH);
	    pnlMain.add(pnlDoanhThu, BorderLayout.CENTER);

	    return pnlMain;
	}



	private JPanel initUI_DoanhThu() {
	    // Panel cha được bo góc
		RoundedPanel pnl = new RoundedPanel(30, Color.WHITE); 

		pnl.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 15));
		pnl.setPreferredSize(new Dimension(850, 150)); 
		
		// Icon 
		ImageIcon iconDoanhThu = new ImageIcon("resource/icon/DoanhThu.png");
		ImageIcon iconHoaDon = new ImageIcon("resource/icon/HoaDon.png");
		ImageIcon iconDatBan = new ImageIcon("resource/icon/DatBan.png");
		ImageIcon iconDonHuy = new ImageIcon("resource/icon/DonHuy.png");
		ImageIcon iconKhachHang = new ImageIcon("resource/icon/soKhach.png");
	    
	     
	    // Các box thống kê (panel con)
		ArrayList<HoaDon> dsHoaDon = hd_dao.getAllHoaDon();
		double doanhThu = 0;
		int soLuongKhach = 0;
		for (HoaDon hd : dsHoaDon) {
			doanhThu += hd.tinhTienThanhToan();
			soLuongKhach += hd.getSoKhach();
		}
		DecimalFormat df = new DecimalFormat("#,###.##");
		String formattedDoanhThu = df.format(doanhThu);

		int tongHoaDon = hd_dao.demTongSoHoaDon();
		int soDonDatBan = hd_dao.demHoaDonCoMaBan();
		
		int soDonHuy = 0;
		ArrayList<HoaDon> dsHD = hd_dao.timHoaDonTheoTrangThai(3);
		soDonHuy = dsHD.size();
		ArrayList<DonDatBanTruoc> dsDDBT = ddbt_dao.timDonDatBanTruocTheoTrangThai(2);
		soDonHuy += dsDDBT.size();
		
		
		pnl.add(createSingleBox(formattedDoanhThu, "Doanh thu", new Color(0, 123, 255), iconDoanhThu));
		pnl.add(Box.createRigidArea(new Dimension(100, 0)));
	    pnl.add(createSingleBox(String.valueOf(tongHoaDon), "Tổng hóa đơn", new Color(0, 174, 255), iconHoaDon));
	    pnl.add(Box.createRigidArea(new Dimension(100, 0)));
	    pnl.add(createSingleBox(String.valueOf(soDonDatBan), "Số đơn đặt bàn", new Color(255, 102, 102), iconDatBan));
	    pnl.add(createSingleBox(String.valueOf(soDonHuy), "Hóa đơn bị hủy", new Color(255, 153, 51), iconDonHuy));
	    pnl.add(Box.createRigidArea(new Dimension(100, 0)));
	    pnl.add(createSingleBox(String.valueOf(soLuongKhach), "Tổng số lượng khách", new Color(0, 204, 102), iconKhachHang));

	    // Panel cha để căn giữa và thêm khoảng trắng hai bên
	    JPanel pnlwrapper = new JPanel(new BorderLayout());
	    pnlwrapper.setBackground(new Color(228, 228, 228));
	    pnlwrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
	    pnlwrapper.add(pnl, BorderLayout.NORTH);
	    
	    pnlwrapper.add(createTimeFilterPanel(), BorderLayout.CENTER);
	    
	    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
	    panel.setBackground(new Color(228, 228, 228));
	    
	    pnlBieuDo = createBarChart();
	    panel.add(pnlBieuDo);
	    panel.add(Box.createRigidArea(new Dimension(50, 0)));
	    pnlBieuDo1 = new JPanel(new BorderLayout());
	    panel.add(pnlBieuDo1);
	    pnlwrapper.add(panel, BorderLayout.SOUTH);
	    
	    return pnlwrapper;
	}

	private JPanel createSingleBox(String value, String title, Color bgColor, Icon icon) {
	    RoundedPanel pnlBox = new RoundedPanel(20, bgColor);
	    pnlBox.setPreferredSize(new Dimension(320, 50));
	    pnlBox.setBackground(bgColor);
	    pnlBox.setLayout(new BoxLayout(pnlBox, BoxLayout.X_AXIS));
	    pnlBox.setOpaque(false);

	    // Icon (nằm bên trái)
	    JLabel lblIcon = new JLabel(icon);
	    lblIcon.setPreferredSize(new Dimension(40, 40)); 
	    lblIcon.setAlignmentY(Component.CENTER_ALIGNMENT);
	    lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

	    // Text panel (gồm giá trị và tiêu đề)
	    JLabel lblValue = new JLabel(value);
	    lblValue.setFont(new Font("Arial", Font.BOLD, 18));
	    lblValue.setForeground(Color.WHITE);
	    lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);

	    JLabel lblTitle = new JLabel(title);
	    lblTitle.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblTitle.setForeground(Color.WHITE);
	    lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

	    JPanel pnlText = new JPanel();
	    pnlText.setOpaque(false);
	    pnlText.setLayout(new BoxLayout(pnlText, BoxLayout.Y_AXIS));
	    pnlText.add(lblValue);
	    pnlText.add(lblTitle);
	    pnlText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

	    pnlBox.add(Box.createRigidArea(new Dimension(80, 0)));
	    pnlBox.add(lblIcon);
	    pnlBox.add(pnlText);
	    pnlBox.add(Box.createHorizontalGlue()); 

	    return pnlBox;
	}

	private JPanel createTimeFilterPanel() {
	    JPanel pnlLocTheoTime = new JPanel(new GridLayout(1, 2, 20, 0));
	    pnlLocTheoTime.setBackground(new Color(228, 228, 228));
	    pnlLocTheoTime.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 30));
	    
	 // ========== Panel 1: Theo khoảng thời gian ==========
	    JPanel pnlKhungTron1 = new RoundedPanel(20, Color.WHITE);
	    pnlKhungTron1.setLayout(new BoxLayout(pnlKhungTron1, BoxLayout.Y_AXIS));
	    pnlKhungTron1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    JLabel lblKhoangTime = new JLabel("Thống kê chi tiết theo khoảng thời gian");
	    lblKhoangTime.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
	    lblKhoangTime.setForeground(new Color(0, 102, 204));
	    lblKhoangTime.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    pnlBieuDo = new JPanel(new BorderLayout());

	    JLabel lblFrom = new JLabel("Ngày bắt đầu");
	    JLabel lblTo = new JLabel("Đến");

	    dateFrom = new JDateChooser();
	    dateFrom.setPreferredSize(new Dimension(220, 25));
	    Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        dateFrom.setDate(firstDayOfMonth);
        
	    dateTo = new JDateChooser();
	    dateTo.setPreferredSize(new Dimension(220, 25));
	    Calendar calendar1 = Calendar.getInstance(); 
        Date currentDate = calendar1.getTime();
        dateTo.setDate(currentDate);
        
        addDateChangeListener();
        reloadBieuDoDoanhThu();

	    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
	    panel1.setOpaque(false);
	    panel1.add(lblFrom);
	    panel1.add(dateFrom);
	    panel1.add(Box.createRigidArea(new Dimension(20, 0)));
	    panel1.add(lblTo);
	    panel1.add(dateTo);

	    pnlKhungTron1.add(lblKhoangTime);
	    pnlKhungTron1.add(panel1);

	    // ========== Panel 2: Theo ngày/tháng/năm ==========	    
	    JPanel pnlNgayThangNam = new RoundedPanel(20, Color.WHITE);
	    pnlNgayThangNam.setLayout(new BoxLayout(pnlNgayThangNam, BoxLayout.Y_AXIS));
	    pnlNgayThangNam.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

	    JLabel lblNgayThangNam = new JLabel("Thống kê chi tiết theo ngày tháng năm");
	    lblNgayThangNam.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
	    lblNgayThangNam.setForeground(new Color(0, 102, 204));
	    lblNgayThangNam.setAlignmentX(Component.CENTER_ALIGNMENT);
	  
	    JPanel pnlLoaiTK = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
	    pnlLoaiTK.setBackground(Color.WHITE);
	    JLabel lblLoaiTK = new JLabel("Loại thống kê");
	    lblLoaiTK.setFont(new Font("Arial", Font.BOLD, 14));
	    String[] loaiTK = {"Tuần", "Tháng", "Năm"};
	    cmbLoaiTK = new JComboBox<>(loaiTK);
	    cmbLoaiTK.setPreferredSize(new Dimension(150, 25));
	    cmbLoaiTK.addActionListener(this);
	    pnlLoaiTK.add(lblLoaiTK);
	    pnlLoaiTK.add(cmbLoaiTK);
	    
	    panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
	    panel2.setOpaque(false);
	    
	    lblThang = new JLabel("Tháng");
	    cmbThang = new JComboBox<>();
	    for (int i = 1; i <= 12; i++) cmbThang.addItem(String.format("%02d", i));
	    cmbThang.setPreferredSize(new Dimension(100, 25));
	    
	    lblNam = new JLabel("Năm");
	    cmbNam = new JComboBox<>();
	    for (int i = 2020; i <= 2025; i++) cmbNam.addItem(String.valueOf(i));
	    cmbNam.setPreferredSize(new Dimension(100, 25));

	    // ==== Chọn mặc định theo thời gian hệ thống ====
	    Calendar now = Calendar.getInstance();
	    int currentMonth = now.get(Calendar.MONTH) + 1;
	    int currentYear = now.get(Calendar.YEAR);

	    cmbThang.setSelectedItem(String.format("%02d", currentMonth));
	    cmbNam.setSelectedItem(String.valueOf(currentYear));
	    panel2.setOpaque(false);
	    panel2.add(lblThang);
	    panel2.add(Box.createRigidArea(new Dimension(10, 0)));
	    panel2.add(cmbThang);
	    panel2.add(Box.createRigidArea(new Dimension(20, 0)));
	    panel2.add(lblNam);
	    panel2.add(Box.createRigidArea(new Dimension(10, 0)));
	    panel2.add(cmbNam);
	    
	    btnXemBieuDo = new JButton("Xem biểu đồ");
	    btnXemBieuDo.addActionListener(this);
	    btnXemBieuDo.setFocusPainted(false);
	    panel2.add(btnXemBieuDo);
	    SwingUtilities.invokeLater(() -> btnXemBieuDo.doClick());

	    pnlNgayThangNam.add(lblNgayThangNam);
	    pnlNgayThangNam.add(pnlLoaiTK);
	    pnlNgayThangNam.add(panel2);

	    pnlLocTheoTime.add(pnlKhungTron1);
	    pnlLocTheoTime.add(pnlNgayThangNam);
	    
	    
	    return pnlLocTheoTime;
	}

	
	private void addDateChangeListener() {
	    PropertyChangeListener listener = new PropertyChangeListener() {
	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	            if ("date".equals(evt.getPropertyName())) {
	                reloadBieuDoDoanhThu();
	            }
	        }
	    };

	    dateFrom.getDateEditor().addPropertyChangeListener(listener);
	    dateTo.getDateEditor().addPropertyChangeListener(listener);
	}

	private void reloadBieuDoDoanhThu() {
		JPanel newChart = createBarChart();
		
		if (pnlBieuDo != null) {
	        pnlBieuDo.removeAll();
	    } 
		
	    pnlBieuDo.add(newChart, BorderLayout.CENTER);
	    pnlBieuDo.revalidate();
	    pnlBieuDo.repaint();
	}

	private JPanel createBarChart() {
	    Date utilFrom = dateFrom.getDate();
	    Date utilTo = dateTo.getDate();

	    LocalDate tuNgay = utilFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate denNgay = utilTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	    DefaultCategoryDataset dataset = hd_dao.getDoanhThuTheoNgay(tuNgay, denNgay);

	    JFreeChart chart = ChartFactory.createBarChart(
	            "Doanh thu theo Ngày",  
	            "Ngày",                
	            "Doanh thu (VNĐ)",  
	            dataset,           
	            PlotOrientation.VERTICAL, 
	            false,               
	            true,              
	            false         
	    );

	    CategoryPlot plot = chart.getCategoryPlot();
	    plot.setBackgroundPaint(Color.WHITE);  
	    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);  

	    // Tùy chỉnh màu sắc cột
	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setSeriesPaint(0, new Color(79, 129, 189)); 

	    // Tùy chỉnh tiêu đề
	    chart.getTitle().setFont(new Font("Tahoma", Font.BOLD, 16));

	    // B5: Tạo ChartPanel để hiển thị biểu đồ
	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new Dimension(600, 400));  

	    // Trả về JPanel chứa biểu đồ
	    JPanel panelBieuDo = new JPanel(new BorderLayout());
	    panelBieuDo.add(chartPanel, BorderLayout.CENTER);
	    return panelBieuDo;
	}

	private void veBieuDoTheoTuan(int thang, int nam) {
	    DefaultCategoryDataset dataset = hd_dao.getDoanhThuTheoTuanTrongThang(thang, nam);

	    JFreeChart chart = ChartFactory.createBarChart(
	        "Doanh thu theo tuần - Tháng " + thang + "/" + nam,
	        "Tuần",
	        "Doanh thu (VNĐ)",
	        dataset,
	        PlotOrientation.VERTICAL,
	        false, true, false
	    );

	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new Dimension(600, 400));
	    
	    pnlBieuDo1.removeAll();
	    pnlBieuDo1.add(chartPanel, BorderLayout.CENTER);
	    pnlBieuDo1.revalidate();
	    pnlBieuDo1.repaint();
	}
	
	public void hienThiBieuDoThangTheoNam(int nam) {
	    DefaultCategoryDataset dataset = hd_dao.getDoanhThuTheoThangTrongNam(nam);

	    JFreeChart barChart = ChartFactory.createBarChart(
	        "Thống kê doanh thu theo tháng - Năm " + nam,
	        "Tháng",
	        "Doanh thu (VNĐ)",
	        dataset,
	        PlotOrientation.VERTICAL,
	        true, true, false
	    );

	    ChartPanel chartPanel = new ChartPanel(barChart);
	    chartPanel.setPreferredSize(new Dimension(600, 400));

	    pnlBieuDo1.removeAll();
	    pnlBieuDo1.setLayout(new BorderLayout());
	    pnlBieuDo1.add(chartPanel, BorderLayout.CENTER);
	    pnlBieuDo1.revalidate();
	    pnlBieuDo1.repaint();
	}
	
	public void hienThiBieuDoTheoNam(int namTu, int namDen) {
	    DefaultCategoryDataset dataset = hd_dao.getDoanhThuTheoNam(namTu, namDen);

	    JFreeChart barChart = ChartFactory.createBarChart(
	        "Thống kê doanh thu từ " + namTu + " đến " + namDen,
	        "Năm",
	        "Doanh thu (VNĐ)",
	        dataset,
	        PlotOrientation.VERTICAL,
	        true, true, false
	    );

	    ChartPanel chartPanel = new ChartPanel(barChart);
	    chartPanel.setPreferredSize(new Dimension(600, 400));

	    pnlBieuDo1.removeAll();
	    pnlBieuDo1.setLayout(new BorderLayout());
	    pnlBieuDo1.add(chartPanel, BorderLayout.CENTER);
	    pnlBieuDo1.revalidate(); 
	    pnlBieuDo1.repaint();
	}



    public static void main(String[] args) {
    	connectDB.ConnectDB.getInstance().connect();
        SwingUtilities.invokeLater(() -> new ThongKeDoanhThu_GUI());
    }



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == cmbLoaiTK) {
            String selected = (String) cmbLoaiTK.getSelectedItem();
            System.out.println("Loại thống kê được chọn: " + selected);

            switch (selected) {
                case "Tuần":
                    // Hiện comboBox tháng/năm
                	lblThang.setVisible(true);
                	lblNam.setVisible(true);
                	cmbThang.setVisible(true);
                	cmbNam.setVisible(true);
                    break;
                case "Tháng":
                    // Ẩn panel tháng/năm nếu không cần
                	lblThang.setVisible(false);
                	cmbThang.setVisible(false);
                	lblNam.setVisible(true);
                	cmbNam.setVisible(true);
                    break;
                case "Năm":
                	lblThang.setVisible(false);
                	cmbThang.setVisible(false);
                	lblNam.setVisible(false);
                	cmbNam.setVisible(false);
                    break;
            }
        }
		if (source.equals(btnXemBieuDo)) {
			switch (cmbLoaiTK.getSelectedIndex()) {
				case 0: // Thống kê theo tuần
					int thang = Integer.parseInt((String) cmbThang.getSelectedItem());
					int nam = Integer.parseInt((String) cmbNam.getSelectedItem());
					veBieuDoTheoTuan(thang, nam);
					break;
				case 1: // Thống kê theo tháng
					int namThang = Integer.parseInt((String) cmbNam.getSelectedItem());
					hienThiBieuDoThangTheoNam(namThang);
					break;
				case 2: // Thống kê theo năm
					String namTuStr = (String) cmbNam.getItemAt(0);
					String namDenStr = (String) cmbNam.getItemAt(cmbNam.getItemCount() - 1); 

					int namTu = Integer.parseInt(namTuStr);
					int namDen = Integer.parseInt(namDenStr);
					hienThiBieuDoTheoNam(namTu, namDen);
					
					break;
			}
		}
	}
}