package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sources.UtilisateurSender;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainView extends JFrame {

	private JPanel contentPane;
	private UtilisateurSender sender;
	private String login;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView(sender);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public MainView(UtilisateurSender sender, String login) {
		this.sender = sender;
		this.login = login;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTweets = new JLabel("Number of Tweets:");
		lblTweets.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTweets.setBounds(12, 84, 129, 19);
		contentPane.add(lblTweets);
		
		JLabel lblNbMsg = new JLabel("New label");
		lblNbMsg.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNbMsg.setBounds(154, 86, 56, 16);
		contentPane.add(lblNbMsg);
		
		String nb = Integer.toString(sender.nombreMessage(login));
		lblNbMsg.setText(nb);
		
		JLabel lblWelcomeToTwitter = new JLabel("Welcome to Twitter " + login);
		lblWelcomeToTwitter.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToTwitter.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblWelcomeToTwitter.setBounds(12, 13, 574, 27);
		contentPane.add(lblWelcomeToTwitter);
	}

}
