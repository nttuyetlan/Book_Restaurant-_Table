package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
import dao.DonDatBanTruoc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuVuc_DAO;
import dao.MonAn_DAO;
import dao.NhanVien_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.DonDatBanTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.MonAn;
import entity.NhanVien;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class CuaSoThongTinDDBT_GUI extends JDialog {
	private JTextField txtMaHoaDon, txtMaDonDatBan, txtMaBan, txtThoiGianNhan;
	private JTextField txtSoDienThoai, txtTenKhachHang, txtEmail, txtGhiChuKH;
	private JTextField txtCocBan, txtCocMon, txtTongCoc;
	private Ban_DAO ban_dao = new Ban_DAO();
	private KhuVuc_DAO khuVuc_dao = new KhuVuc_DAO();
	private KhachHang_DAO KH_dao = new KhachHang_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
	private DonDatBanTruoc_DAO DDBT_dao = new DonDatBanTruoc_DAO();
	private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
	private ChiTietHoaDon_DAO ctHD_dao = new ChiTietHoaDon_DAO();
	private MonAn_DAO monAn_dao = new MonAn_DAO();
	private DatBanTruoc_GUI01 ddbtFrame;
	private JTextArea GhiChuKH;

	public CuaSoThongTinDDBT_GUI(DatBanTruoc_GUI01 ddbtFrame, LocalDateTime thoiGianNhanBan, Double tongTienCoc,
			Ban banTamTinh, KhachHang khTamTinh, NhanVien nvien, String maDDBT, int soLuongKH, String GhiChu,
			JTable tblFormMonAn) {
		super((Frame) null, "Thông tin đơn đặt bàn trước", true); 
		setUndecorated(true); 
		setFont(new Font("Arial", Font.BOLD, 20));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize);
		setLocationRelativeTo(null);
		this.ddbtFrame = ddbtFrame;

		String maHD = hoaDon_dao.taoMaHoaDonTuDong();
		String maDon = DDBT_dao.taoMaHoaDonTuDong();

		JPanel panelMain = new JPanel(new GridLayout(1, 2, 20, 20));
		panelMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(panelMain, BorderLayout.CENTER);

		// === BÊN TRÁI: Thông tin đơn + món ===
		JPanel panelLeft = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		panelMain.add(panelLeft);

		Font titleFont = new Font("Arial", Font.BOLD, 18);

		JPanel panelThongTinDon = new JPanel(new GridLayout(5, 2, 15, 15));
		panelThongTinDon
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(30, 129, 191)),
						"Thông tin đơn", TitledBorder.LEFT, TitledBorder.TOP, titleFont, new Color(30, 129, 191)));
		panelThongTinDon.setBorder(new CompoundBorder(panelThongTinDon.getBorder(), new EmptyBorder(15, 15, 15, 15)));

		txtMaHoaDon = new JTextField();
		txtMaHoaDon.setEditable(false);
		txtMaDonDatBan = new JTextField();
		txtMaDonDatBan.setEditable(false);
		txtMaBan = new JTextField();
		txtMaBan.setEditable(false);
		txtThoiGianNhan = new JTextField();
		txtThoiGianNhan.setEditable(false);

		panelThongTinDon.add(new JLabel("Mã hóa đơn:"));
		panelThongTinDon.add(txtMaHoaDon);
		panelThongTinDon.add(new JLabel("Mã đơn đặt bàn trước:"));
		panelThongTinDon.add(txtMaDonDatBan);
		panelThongTinDon.add(new JLabel("Mã bàn:"));
		panelThongTinDon.add(txtMaBan);
		panelThongTinDon.add(new JLabel("Thời gian nhận bàn:"));
		panelThongTinDon.add(txtThoiGianNhan);

		panelThongTinDon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
		panelLeft.add(panelThongTinDon);
		panelLeft.add(Box.createVerticalStrut(20));


		JPanel panelThongTinMon = new JPanel(new BorderLayout());
		panelThongTinMon
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(30, 129, 191)),
						"Thông tin món", TitledBorder.LEFT, TitledBorder.TOP, titleFont, new Color(30, 129, 191)));
		panelThongTinMon.setBorder(new CompoundBorder(panelThongTinMon.getBorder(), new EmptyBorder(15, 15, 15, 15)));
		DefaultTableModel modelThongTinMon = new DefaultTableModel(new Object[][] {},
				new String[] { "STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền", "Ghi chú" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tableMon = new JTable(modelThongTinMon);
		tableMon.setRowHeight(30);
		tableMon.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableCellRenderer multiLineRenderer = new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JTextArea textArea = new JTextArea();
				textArea.setText(value != null ? value.toString() : "");
				textArea.setWrapStyleWord(true);
				textArea.setLineWrap(true);
				textArea.setOpaque(true);
				textArea.setFont(table.getFont());
				textArea.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
				textArea.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
				textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				int columnWidth = table.getColumnModel().getColumn(column).getWidth();
				textArea.setSize(new Dimension(columnWidth, Integer.MAX_VALUE));
				int preferredHeight = textArea.getPreferredSize().height;

				if (table.getRowHeight(row) != preferredHeight) {
					table.setRowHeight(row, preferredHeight);
				}

				return textArea;
			}
		};

		for (int i = 0; i < tableMon.getColumnCount(); i++) {
			if (i == 1 || i == 5) { 

				tableMon.getColumnModel().getColumn(i).setCellRenderer(multiLineRenderer);
			} else {
				tableMon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}

		// Thêm bảng vào scroll
		JScrollPane scrollPane = new JScrollPane(tableMon);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelThongTinMon.add(new JScrollPane(tableMon), BorderLayout.CENTER);
		panelThongTinMon.setPreferredSize(new Dimension(600, 400));
		panelLeft.add(panelThongTinMon);
		loadDuLieuLaiVaoBangThongTinMon(tblFormMonAn, tableMon);

		// === BÊN PHẢI: Thông tin khách hàng + cọc + nút ===
		JPanel panelRight = new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		panelMain.add(panelRight);

		// Thông tin khách hàng
		JPanel panelThongTinKH = new JPanel(new GridLayout(4, 2, 15, 15));
		panelThongTinKH.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(30, 129, 191)), "Thông tin khách hàng", TitledBorder.LEFT,
				TitledBorder.TOP, titleFont, new Color(30, 129, 191)));
		panelThongTinKH.setBorder(new CompoundBorder(panelThongTinKH.getBorder(), new EmptyBorder(15, 15, 15, 15)));

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setEditable(false);
		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setEditable(false);
		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		GhiChuKH = new JTextArea(3, 20); 
		GhiChuKH.setLineWrap(true);
		GhiChuKH.setWrapStyleWord(true);
		GhiChuKH.setEditable(false);
		GhiChuKH.setFocusable(false);
		GhiChuKH.setBackground(UIManager.getColor("TextField.background"));
		GhiChuKH.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollGhiChu = new JScrollPane(GhiChuKH);
		scrollGhiChu.setBorder(null); 

		panelThongTinKH.add(new JLabel("Số điện thoại:"));
		panelThongTinKH.add(txtSoDienThoai);
		panelThongTinKH.add(new JLabel("Tên khách hàng:"));
		panelThongTinKH.add(txtTenKhachHang);
		panelThongTinKH.add(new JLabel("Email:"));
		panelThongTinKH.add(txtEmail);
		panelThongTinKH.add(new JLabel("Ghi chú:"));
		panelThongTinKH.add(scrollGhiChu);

		panelThongTinKH.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
		panelRight.add(panelThongTinKH);
		panelRight.add(Box.createVerticalStrut(20));

		// Thông tin cọc
		JPanel panelThongTinCoc = new JPanel(new GridLayout(3, 2, 15, 15));
		panelThongTinCoc
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(30, 129, 191)),
						"Thông tin cọc", TitledBorder.LEFT, TitledBorder.TOP, titleFont, new Color(30, 129, 191)));
		panelThongTinCoc.setBorder(new CompoundBorder(panelThongTinCoc.getBorder(), new EmptyBorder(15, 15, 15, 15)));

		txtCocBan = new JTextField();
		txtCocBan.setEditable(false);
		txtCocMon = new JTextField();
		txtCocMon.setEditable(false);
		txtTongCoc = new JTextField();
		txtTongCoc.setEditable(false);

		panelThongTinCoc.add(new JLabel("Cọc bàn:"));
		panelThongTinCoc.add(txtCocBan);
		panelThongTinCoc.add(new JLabel("Cọc món:"));
		panelThongTinCoc.add(txtCocMon);
		panelThongTinCoc.add(new JLabel("Tổng cọc:"));
		panelThongTinCoc.add(txtTongCoc);

		panelThongTinCoc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
		panelRight.add(panelThongTinCoc);
		panelRight.add(Box.createVerticalStrut(20));

		// Khối ghi chú
		JPanel panelGhiChu = new JPanel(new BorderLayout());
		panelGhiChu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(30, 129, 191)),
				"Ghi chú", TitledBorder.LEFT, TitledBorder.TOP, titleFont, new Color(30, 129, 191)));
		panelGhiChu.setBorder(new CompoundBorder(panelGhiChu.getBorder(), new EmptyBorder(15, 15, 15, 15)));

		JTextArea GHiChuHD = new JTextArea(5, 20); 
		GHiChuHD.setLineWrap(true);
		GHiChuHD.setWrapStyleWord(true);
		GHiChuHD.setEditable(true);
		GHiChuHD.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		GHiChuHD.setBackground(UIManager.getColor("TextField.background"));

		JScrollPane scrollGhiChuHD = new JScrollPane(GHiChuHD);
		scrollGhiChuHD.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelGhiChu.add(scrollGhiChuHD, BorderLayout.CENTER);
		panelGhiChu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

		panelRight.add(panelGhiChu);
		panelRight.add(Box.createVerticalStrut(20));
		txtMaHoaDon.setCaretColor(new Color(0, 0, 0, 0));
		txtMaDonDatBan.setCaretColor(new Color(0, 0, 0, 0));
		txtMaBan.setCaretColor(new Color(0, 0, 0, 0));
		txtThoiGianNhan.setCaretColor(new Color(0, 0, 0, 0));

		txtSoDienThoai.setCaretColor(new Color(0, 0, 0, 0));
		txtTenKhachHang.setCaretColor(new Color(0, 0, 0, 0));
		txtEmail.setCaretColor(new Color(0, 0, 0, 0));
		txtCocBan.setCaretColor(new Color(0, 0, 0, 0));
		txtCocMon.setCaretColor(new Color(0, 0, 0, 0));
		txtTongCoc.setCaretColor(new Color(0, 0, 0, 0));

		txtMaHoaDon.setText(maHD);
		txtMaDonDatBan.setText(maDon);
		txtMaBan.setText(banTamTinh.getMaBan());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		txtThoiGianNhan.setText(thoiGianNhanBan.format(formatter));
		txtSoDienThoai.setText(khTamTinh.getSdtKH());
		txtTenKhachHang.setText(khTamTinh.getTenKH());
		txtEmail.setText(khTamTinh.getEmailKH());
		String ghiChu = khTamTinh.getGhiChuKH();
		GhiChuKH.setText(ghiChu != null ? ghiChu : "");

		txtTongCoc.setText(String.format("%,.0f VNĐ", tongTienCoc));
		txtCocBan.setText(String.format("%,.0f VNĐ", banTamTinh.getSoLuongGhe() * 500000 * 0.1));
		txtCocMon.setText(String.format("%,.0f VNĐ", tongTienCoc - (banTamTinh.getSoLuongGhe() * 500000 * 0.1)));
		GHiChuHD.setText(GhiChu);
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btn1 = new JButton("Hủy");
		JButton btn2 = new JButton("Cập nhật");
		JButton btn3 = new JButton("Xác nhận");
		panelButtons.add(btn1);
		panelButtons.add(btn2);
		panelButtons.add(btn3);

		btn1.setBackground(new Color(220, 53, 69)); // Đỏ đậm
		btn1.setForeground(Color.WHITE);

		btn2.setBackground(new Color(255, 193, 7)); // Vàng
		btn2.setForeground(Color.BLACK);

		btn3.setBackground(new Color(40, 167, 69)); // Xanh lá đậm
		btn3.setForeground(Color.WHITE);

		btn1.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn làm mới hủy bỏ các thông tin đã điền ?",
					"Xác nhận làm mới", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
				ddbtFrame.getBtnLamMoi().doClick();

			}
		});

		btn2.addActionListener(e -> {
			this.dispose(); 

			if (ddbtFrame != null) {
				dispose();
			}
		});
		btn3.addActionListener(e -> {
			xuLyXacNhan(maDDBT, soLuongKH, thoiGianNhanBan, tongTienCoc, khTamTinh, nvien, banTamTinh,
					GHiChuHD.getText(), tblFormMonAn, maHD, maDon);
		});

		panelRight.add(panelButtons);
	}

	public void capNhatChiTietHD(DefaultTableModel model, HoaDon hd) {
		int rowCount = model.getRowCount(); // Số dòng trong bảng

		for (int i = 0; i < rowCount; i++) {
			String current = model.getValueAt(i, 1).toString();
			String tenMon = "";
			String ghiChu = "";

			if (current.startsWith("<html>")) {
				current = current.substring(6, current.length() - 7);
				int brIndex = current.indexOf("<br>");
				if (brIndex != -1) {
					tenMon = current.substring(0, brIndex).trim();
					int start = current.indexOf("<i");
					int tagClose = current.indexOf(">", start);
					int end = current.indexOf("</i>", tagClose);

					if (start != -1 && tagClose != -1 && end != -1) {
						ghiChu = current.substring(tagClose + 1, end).trim();
					}
				} else {
					tenMon = current.trim(); 
				}
			} else {
				tenMon = current.trim(); 
			}

			int sl = Integer.parseInt(model.getValueAt(i, 3).toString());

			MonAn MonAn = monAn_dao.get1MonAnTheoTenMon(tenMon);

			ChiTietHoaDon CTHD = new ChiTietHoaDon(hd, MonAn, sl, ghiChu);
			Boolean capNhatCTHD = ctHD_dao.insertChiTietHoaDon(CTHD);

		}
	}

	public void xuLyXacNhan(String maDDBT, int soLuongKH, LocalDateTime thoiGianNhanBan, double tongTienCoc,
			KhachHang khTamTinh, NhanVien nvien, Ban banTamTinh, String GhiChu, JTable tblFormMonAn, String maHD,
			String maDon) {

		LocalDateTime thoiGianTao = LocalDateTime.now();


		DonDatBanTruoc DDBT_tao = new DonDatBanTruoc(maDon, thoiGianTao, soLuongKH, thoiGianNhanBan, tongTienCoc,
				khTamTinh, nvien, banTamTinh, 0);

		boolean themDon = DDBT_dao.insertDonDatBanTruoc(DDBT_tao);

		HoaDon hd = new HoaDon(maHD, banTamTinh, DDBT_tao, khTamTinh, nvien, thoiGianTao, 2, soLuongKH, GhiChu);

		boolean themHD = hoaDon_dao.insertHoaDon(hd);

		DefaultTableModel model_luu = (DefaultTableModel) tblFormMonAn.getModel();
		capNhatChiTietHD(model_luu, hd);
		if (themDon && themHD) {

			String htmlContent = generateHtmlEmailContent(khTamTinh.getSdtKH(), maDon, maHD);

			sendEmail(khTamTinh.getEmailKH(), "THÔNG TIN ĐẶT BÀN TẠI NHÀ HÀNG KAREM", htmlContent);
			dispose();
			ddbtFrame.getBtnLamMoi().doClick();
		} else {
			JOptionPane.showMessageDialog(null, "Lưu thất bại. Vui lòng thử lại.");
		}
	}

	private void loadDuLieuLaiVaoBangThongTinMon(JTable tblFormMonAn, JTable tableMon) {
		DefaultTableModel modelSrc = (DefaultTableModel) tblFormMonAn.getModel();
		DefaultTableModel modelDest = (DefaultTableModel) tableMon.getModel();
		modelDest.setRowCount(0);

		for (int i = 0; i < modelSrc.getRowCount(); i++) {
			Object tenMonObj = modelSrc.getValueAt(i, 1); 
			Object soLuong = modelSrc.getValueAt(i, 3);
			Object donGia = modelSrc.getValueAt(i, 5);
			Object thanhTien = modelSrc.getValueAt(i, 6);

			String tenMon = "", ghiChu = "";
			if (tenMonObj != null) {
				String raw = tenMonObj.toString();
				if (raw.startsWith("<html>")) {
					raw = raw.substring(6, raw.length() - 7); 
					int brIndex = raw.indexOf("<br>");
					if (brIndex != -1) {
						tenMon = raw.substring(0, brIndex).trim();

						int start = raw.indexOf("<i");
						int tagClose = raw.indexOf(">", start);
						int end = raw.indexOf("</i>", tagClose);
						if (start != -1 && tagClose != -1 && end != -1) {
							ghiChu = raw.substring(tagClose + 1, end).trim();
						}
					} else {
						tenMon = raw.trim();
					}
				} else {
					tenMon = raw.trim();
				}
			}

			Object[] newRow = { i + 1, tenMon, soLuong, donGia, thanhTien, ghiChu };
			modelDest.addRow(newRow);
		}
	}

	public void sendEmail(String to, String subject, String htmlContent) {
		final String fromEmail = "tranminhtu2032004@gmail.com";
		final String password = "gqid xaks fvvp vfwo";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(htmlContent, "text/html; charset=UTF-8");

			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Lưu thành công!");
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Gửi email thất bại: " + e.getMessage());
		}

	}

	public String generateHtmlEmailContent(String SDTKH, String maDDBT, String maHD)
	{
		StringBuilder html = new StringBuilder();
		KhachHang kh = KH_dao.getKhachHangTheoSDTKhachHang(SDTKH);
		DonDatBanTruoc ddbt = DDBT_dao.getDonDatBanTheoMa(maDDBT);
//		HoaDon hd = hoaDon_dao.timHoaDonTheoMaHoaDon(maHD);

		LocalDateTime thoiGianNhanBan = ddbt.getThoiGianNhanBan(); // hoặc bất kỳ thời điểm nào
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		String thoiGianString = thoiGianNhanBan.format(formatter);
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
		dsCTHD = ctHD_dao.getChiTietHoaDonTheoMaHD(maHD);

		html.append("<html><body style='font-family:Arial,sans-serif;'>").append("<p>Kính gửi Quý khách <strong>")
				.append(kh.getTenKH()).append("</strong>,</p>")
				.append("<p>Chúng tôi trân trọng thông báo rằng Quý khách đã đặt bàn thành công tại nhà hàng <strong>KAREM</strong>.</p>")
				.append("<h3>🔹 Thông tin đơn hàng:</h3>").append("<ul>").append("<li><strong>Mã đơn hàng:</strong> ")
				.append(ddbt.getMaDonDatBanTruoc()).append("</li>").append("<li><strong>Thời gian đặt bàn:</strong> ")
				.append(thoiGianString).append("</li>").append("<li><strong>Mã bàn:</strong> ")
				.append(ddbt.getBan().getMaBan()).append("</li>").append("<li><strong>Tiền cọc:</strong> ")
				.append(String.format("%,.0f VND", ddbt.getTienCoc())).append("</li>").append("</li>").append("</ul>")
				.append("<h3>🔹 Danh sách món ăn đã đặt trước:</h3>")
				.append("<table border='1' cellpadding='8' cellspacing='0' style='border-collapse:collapse;width:100%;text-align:center;'>")
				.append("<thead style='background-color:#f2f2f2;'>").append("<tr>").append("<th>STT</th>")
				.append("<th>Tên món ăn</th>").append("<th>Số lượng</th>").append("<th>Đơn giá (VND)</th>")
				.append("<th>Thành tiền (VND)</th>").append("</tr>").append("</thead>").append("<tbody>");

		double tongTien = 0;
		int stt = 1;
		for (ChiTietHoaDon item : dsCTHD) {
			double thanhTien = item.getSoLuong() * item.getMonAn().getDonGia();
			html.append("<tr>").append("<td>").append(stt++).append("</td>").append("<td>")
					.append(item.getMonAn().getTenMonAn()).append("</td>").append("<td>").append(item.getSoLuong())
					.append("</td>").append("<td>").append(String.format("%,.0f", item.getMonAn().getDonGia()))
					.append("</td>").append("<td>").append(String.format("%,.0f", thanhTien)).append("</td>")
					.append("</tr>");
			tongTien += thanhTien;
		}

		html.append("<tr style='font-weight:bold;background-color:#eaf7ea;'>")
	    .append("<td colspan='4'>Tổng cộng</td>")
	    .append("<td>").append(String.format("%,.0f VND", tongTien)).append("</td>")
	    .append("</tr>")
	    .append("</tbody></table>")

	    .append("<br><h3>🔹 Thông tin thanh toán chuyển khoản:</h3>")
	    .append("<ul>")
	    .append("<li><strong>Ngân hàng:</strong> Vietcombank (VCB)</li>")
	    .append("<li><strong>Số tài khoản:</strong> 0123456789</li>")
	    .append("<li><strong>Chủ tài khoản:</strong>Nhà Hàng KAREM</li>")
	    .append("</ul>")
	    .append("<br><p><strong>Lưu ý:</strong></p>")
	    .append("<ul>")
	    .append("<li>Số tiền cọc sẽ được trừ vào tổng hóa đơn thanh toán.</li>")
	    .append("<li>Vui lòng có mặt đúng giờ để đảm bảo chất lượng phục vụ tốt nhất.</li>")
	    .append("<li>Mọi thay đổi hoặc hủy bàn vui lòng thông báo trước 2 giờ.</li>")
	    .append("</ul>")
	    .append("<p>Trân trọng cảm ơn Quý khách đã tin tưởng và lựa chọn <strong>KAREM</strong>.</p>")
	    .append("<p>Nếu có bất kỳ yêu cầu hỗ trợ nào, xin vui lòng liên hệ với chúng tôi qua email này hoặc hotline <strong>0123 456 789</strong>.</p>")
	    .append("<p>Trân trọng,<br><strong>Nhà hàng KAREM</strong></p>")
	    .append("</body></html>");


		return html.toString();
	}
	
	
	


}
