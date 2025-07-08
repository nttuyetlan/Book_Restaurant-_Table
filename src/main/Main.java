
package main;

import connectDB.ConnectDB;
import gui.DangNhap_GUI;

public class Main {
	public static void main(String[] args) {
		ConnectDB.getInstance().connect();
		new DangNhap_GUI().setVisible(true);
	}
}
