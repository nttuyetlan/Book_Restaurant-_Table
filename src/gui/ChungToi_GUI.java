package gui;

import javax.swing.JFrame;

import entity.NhanVien;

public class ChungToi_GUI extends JFrame {
	private NhanVien currentUser;

	public ChungToi_GUI() {
		// Constructor logic can be added here if needed
		this(null);
	}
	
	public ChungToi_GUI(NhanVien loggedInUser) {
		setTitle("Chúng Tôi");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setLayout(null);
	    setVisible(true);
        this.currentUser = loggedInUser;
        System.out.println("Current user: " + (currentUser != null ? currentUser.getTenNV() : "No user logged in"));
	}
	
	public static void main(String[] args) {
        new ChungToi_GUI();
    }
}
