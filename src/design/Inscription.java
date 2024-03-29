package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sources.UtilisateurSender;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inscription extends JFrame {

	private JPanel contentPane;
	private JTextField tbxLogin;
	private JPasswordField tbxMdp;
	private JTextField tbxNom;
	private JTextField tbxPrenom;
	private JTextField tbxVille;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscription frame = new Inscription();
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
	public Inscription() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Sign up");
		lblSignUp.setBounds(12, 13, 408, 27);
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentPane.add(lblSignUp);
		
		JLabel lblAd = new JLabel("Login:");
		lblAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAd.setBounds(65, 66, 116, 27);
		contentPane.add(lblAd);
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(65, 106, 116, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Last name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(65, 139, 116, 19);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("First name:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(65, 166, 116, 27);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("City:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(65, 196, 116, 27);
		contentPane.add(lblNewLabel_3);
		
		tbxLogin = new JTextField();
		tbxLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxLogin.setBounds(193, 66, 162, 22);
		contentPane.add(tbxLogin);
		tbxLogin.setColumns(10);
		
		tbxMdp = new JPasswordField();
		tbxMdp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxMdp.setBounds(193, 101, 162, 22);
		contentPane.add(tbxMdp);
		
		tbxNom = new JTextField();
		tbxNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxNom.setBounds(193, 135, 162, 22);
		contentPane.add(tbxNom);
		tbxNom.setColumns(10);
		
		tbxPrenom = new JTextField();
		tbxPrenom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxPrenom.setBounds(193, 166, 162, 22);
		contentPane.add(tbxPrenom);
		tbxPrenom.setColumns(10);
		
		tbxVille = new JTextField();
		tbxVille.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxVille.setBounds(193, 196, 162, 22);
		contentPane.add(tbxVille);
		tbxVille.setColumns(10);
		
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblResult.setBounds(12, 231, 408, 27);
		contentPane.add(lblResult);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! tbxLogin.getText().isEmpty() && ! tbxMdp.getText().isEmpty() && ! tbxNom.getText().isEmpty() && ! tbxPrenom.getText().isEmpty()) {
					if (tbxMdp.getText().length() >= 4) {
						UtilisateurSender senderInscription = new UtilisateurSender();
						senderInscription.startJMSConnection();
						boolean signUp = senderInscription.inscrireUtilisateur(
								tbxLogin.getText(),
								tbxMdp.getText(),
								tbxNom.getText(),
								tbxPrenom.getText(),
								tbxVille.getText()
						);
						if (signUp) {
							lblResult.setText("Your registration is now complete.");
						} else {
							lblResult.setText("An error occured. Please try again later.");
						}
						senderInscription.seDeconnecter();
					} else {
						lblResult.setText("Your password must be at least 4 characters long.");
					}
				} else {
					lblResult.setText("Please fill in all fields.");
				}
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignUp.setBounds(174, 272, 97, 25);
		contentPane.add(btnSignUp);
		
		this.getRootPane().setDefaultButton(btnSignUp);
	}
}
