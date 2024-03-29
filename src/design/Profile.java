package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Types.TypeConnection;
import Types.TypeInscription;
import sources.UtilisateurSender;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Profile extends JFrame {

	private JPanel contentPane;
	private JTextField tbxLogin;
	private JPasswordField tbxMdp;
	private JTextField tbxNom;
	private JTextField tbxPrenom;
	private JTextField tbxVille;
	private UtilisateurSender sender;
	private String login;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}

	/**
	 * Create the frame.
	 */
	public Profile(final UtilisateurSender sender, final String login) {
		this.sender = sender;
		this.login = login;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Update");
		lblSignUp.setBounds(12, 13, 408, 27);
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentPane.add(lblSignUp);
		
		JLabel lblAd = new JLabel("Login:");
		lblAd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAd.setBounds(65, 66, 56, 27);
		contentPane.add(lblAd);
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(65, 106, 84, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(65, 139, 73, 19);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("First name:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(65, 166, 84, 27);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("City:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(65, 202, 84, 27);
		contentPane.add(lblNewLabel_3);
		
		tbxLogin = new JTextField();
		tbxLogin.setEnabled(false);
		tbxLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxLogin.setBounds(174, 66, 162, 22);
		contentPane.add(tbxLogin);
		tbxLogin.setColumns(10);
		
		tbxMdp = new JPasswordField();
		tbxMdp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxMdp.setBounds(174, 101, 162, 22);
		contentPane.add(tbxMdp);
		
		tbxNom = new JTextField();
		tbxNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxNom.setBounds(174, 135, 162, 22);
		contentPane.add(tbxNom);
		tbxNom.setColumns(10);
		
		tbxPrenom = new JTextField();
		tbxPrenom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxPrenom.setBounds(174, 166, 162, 22);
		contentPane.add(tbxPrenom);
		tbxPrenom.setColumns(10);
		
		tbxVille = new JTextField();
		tbxVille.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxVille.setBounds(174, 202, 162, 22);
		contentPane.add(tbxVille);
		tbxVille.setColumns(10);
		
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblResult.setBounds(12, 242, 408, 27);
		contentPane.add(lblResult);
		
		JButton btnSignUp = new JButton("Validate");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! tbxLogin.getText().isEmpty() && ! tbxMdp.getText().isEmpty() && ! tbxNom.getText().isEmpty() && ! tbxPrenom.getText().isEmpty() && ! tbxVille.getText().isEmpty()) {
					
					String newVille = tbxVille.getText();
					int update = sender.updateUtilisateur(
							login,
							tbxLogin.getText(),
							tbxMdp.getText(),
							tbxNom.getText(),
							tbxPrenom.getText(),
							newVille.replaceAll("'", "’")
					);
					
					switch(update) {
						case 0:
							lblResult.setText("Your profile has been updated.");
					        break;
						case 1:
							lblResult.setText("This login is already used.");
							break;
						default:
							lblResult.setText("An error occured, please try again.");
							break;
					}
				} else {
					lblResult.setText("Please fill in all fields.");
				}
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignUp.setBounds(93, 287, 97, 25);
		contentPane.add(btnSignUp);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(254, 288, 97, 25);
		contentPane.add(btnCancel);
		
		TypeInscription user = sender.getUtilisateur(login);
		if (user != null) {
			tbxLogin.setText(user.getLogin());
			tbxMdp.setText(user.getPassword());
			tbxNom.setText(user.getNom());
			tbxPrenom.setText(user.getPrenom());
			tbxVille.setText(user.getVille());
		} else {
			lblResult.setText("An error occured while searching your profile.");
		}
	}
}
