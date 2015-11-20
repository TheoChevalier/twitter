package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Types.TypeConnection;
import sources.UtilisateurSender;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Connexion extends JFrame {

	private JPanel contentPane;
	private JTextField tbxLogin;
	private JPasswordField tbxMdp;
	private UtilisateurSender senderSeConnecter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
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
	public Connexion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogin.setBounds(41, 95, 56, 16);
		contentPane.add(lblLogin);
		
		JLabel lblMdp = new JLabel("Password:");
		lblMdp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMdp.setBounds(41, 138, 99, 16);
		contentPane.add(lblMdp);
		
		JLabel lblConnexion = new JLabel("Sign in");
		lblConnexion.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnexion.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblConnexion.setBounds(12, 13, 408, 27);
		contentPane.add(lblConnexion);
		
		tbxLogin = new JTextField();
		tbxLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxLogin.setBounds(164, 88, 168, 22);
		contentPane.add(tbxLogin);
		tbxLogin.setColumns(10);
		
		tbxMdp = new JPasswordField();
		tbxMdp.setBounds(164, 132, 168, 22);
		contentPane.add(tbxMdp);
		
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblResult.setBounds(12, 182, 408, 27);
		contentPane.add(lblResult);
		
		JButton btnConnexion = new JButton("Sign in");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! tbxLogin.getText().isEmpty() && ! tbxMdp.getText().isEmpty()) {
					senderSeConnecter = new UtilisateurSender();
					if (senderSeConnecter.seConnecter(tbxLogin.getText(), tbxMdp.getText())) {
						lblResult.setText("Connected.");
						MainView frame = new MainView(senderSeConnecter, tbxLogin.getText());
						frame.setVisible(true);
					} else {
						lblResult.setText("Not connected.");
					}
				} else {
					lblResult.setText("Please fill in all fields.");
				}
			}
		});
		btnConnexion.setBounds(168, 215, 97, 25);
		contentPane.add(btnConnexion);
	}
}
