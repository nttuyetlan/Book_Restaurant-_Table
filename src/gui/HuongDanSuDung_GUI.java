package gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

public class HuongDanSuDung_GUI {

	public static void showGuide() {
		int choice = JOptionPane.showConfirmDialog(
			null,
			"Bạn có muốn mở trang hướng dẫn sử dụng không?",
			"Hướng dẫn sử dụng",
			JOptionPane.YES_NO_OPTION
		);

		if (choice == JOptionPane.YES_OPTION) {
			openWebpage("https://ptlocnguyen.github.io/HDSD_QuanLyDatBanNhaHangKarem/");
		}
	}

	public static void openWebpage(String url) {
		try {
			URI uri = new URI(url);

			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();

				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(uri);
				} else {
					JOptionPane.showMessageDialog(null, "Trình duyệt không được hỗ trợ trên hệ thống này.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Desktop không được hỗ trợ trên hệ thống này.");
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi mở trang web: " + e.getMessage());
		}
	}
}
