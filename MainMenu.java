import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		// Standard call to end the program when exiting the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// window's (x & y coordinate of top left corner, width, height)
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// WELCOME TEXT: static "Welcome, "
		JLabel welcomeText = new JLabel("Welcome, ");
		welcomeText.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeText.setForeground(new Color(0, 0, 0));
		welcomeText.setBackground(new Color(255, 255, 255));
		welcomeText.setFont(new Font("Tahoma", Font.PLAIN, 22));
		welcomeText.setBounds(464, 85, 115, 39);
		contentPane.add(welcomeText);
		// WELCOME TEXT: link to login's Username?
		JLabel welcomeUSERNAME = new JLabel("");
		welcomeUSERNAME.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeUSERNAME.setForeground(Color.BLACK);
		welcomeUSERNAME.setFont(new Font("Tahoma", Font.PLAIN, 22));
		welcomeUSERNAME.setBackground(Color.WHITE);
		welcomeUSERNAME.setBounds(568, 85, 245, 39);
		contentPane.add(welcomeUSERNAME);
		
		// LOGOUT BUTTON, currently closes window when clicked
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// EDIT PROFILE BUTTON
		JButton editProfileBtn = new JButton("Edit Profile");
		editProfileBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		editProfileBtn.setBounds(965, 23, 115, 39);
		contentPane.add(editProfileBtn);
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		logoutBtn.setBounds(1112, 23, 115, 39);
		contentPane.add(logoutBtn);
		
		// VIEW ACCOUNT BUTTON
		JButton viewAccountBtn = new JButton("View Account");
		viewAccountBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		viewAccountBtn.setBounds(464, 205, 349, 53);
		contentPane.add(viewAccountBtn);
		
		// MANAGE EXPENSES BUTTON
		JButton manageExpensesBtn = new JButton("Manage Expenses");
		manageExpensesBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		manageExpensesBtn.setBounds(464, 385, 349, 53);
		contentPane.add(manageExpensesBtn);
		
		// ANNUAL REPORT BUTTON
		JButton annualReportBtn = new JButton("Annual Report");
		annualReportBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		annualReportBtn.setBounds(464, 295, 349, 53);
		contentPane.add(annualReportBtn);
		
		// Menu Background: filepath may need to be edited on a different computer, pulling from "images" folder
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Danny\\eclipse-workspace\\MainMenu\\images\\backPane.png"));
		lblNewLabel.setBounds(362, 23, 557, 604);
		contentPane.add(lblNewLabel);
		
		// Window Background: filepath may need to be edited on a different computer, pulling from "images" folder
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\Danny\\eclipse-workspace\\MainMenu\\images\\background.jpg"));
		background.setBounds(0, 0, 1264, 692);
		contentPane.add(background);
	}
}
