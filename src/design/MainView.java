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
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class MainView extends JFrame {

	private JPanel contentPane;
	private UtilisateurSender sender;
	private String login;
	private JTextField tbxFindUser;

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
		setBounds(100, 100, 567, 603);
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
		lblWelcomeToTwitter.setBounds(12, 13, 523, 27);
		contentPane.add(lblWelcomeToTwitter);
		
		List<String> listFollowArray = sender.listeFollow(login);
		JList listFollow = new JList(listFollowArray.toArray());
		listFollow.setBounds(45, 152, 165, 119);
		contentPane.add(listFollow);
		
		JLabel lblResultListFollow = new JLabel("");
		lblResultListFollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultListFollow.setBounds(22, 284, 208, 27);
		contentPane.add(lblResultListFollow);
		
		JLabel lblFollow = new JLabel("Follow:");
		lblFollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFollow.setBounds(45, 120, 129, 19);
		contentPane.add(lblFollow);
		
		JLabel lblFollowers = new JLabel("Followers:");
		lblFollowers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFollowers.setBounds(343, 122, 129, 19);
		contentPane.add(lblFollowers);
		
		List<String> listFollowersArray = sender.listeFollowers(login);
		JList listFollowers = new JList(listFollowersArray.toArray());
		listFollowers.setBounds(343, 152, 165, 119);
		contentPane.add(listFollowers);
		
		JLabel lblResultListFollowers = new JLabel("");
		lblResultListFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollowers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultListFollowers.setBounds(327, 284, 208, 27);
		contentPane.add(lblResultListFollowers);
		
		JLabel lblResultResearchUser = new JLabel("");
		lblResultResearchUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultResearchUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultResearchUser.setBounds(22, 503, 296, 27);
		contentPane.add(lblResultResearchUser);
		
		JList listUserToFollow = new JList();
		listUserToFollow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserToFollow.setBounds(45, 392, 165, 98);
		contentPane.add(listUserToFollow);
		
		JButton btnResearch = new JButton("Research");
		btnResearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! tbxFindUser.getText().isEmpty()) {
					List<String> listUserToFollowArray = sender.rehercherUtilisateur(tbxFindUser.getText());
					listUserToFollow.setListData(listUserToFollowArray.toArray());
					if (listUserToFollowArray.isEmpty()) {
						lblResultResearchUser.setText("No result.");
					}
				} else {
					lblResultResearchUser.setText("Please, enter a login to do the reasearch.");
				}
			}
		});
		btnResearch.setBounds(221, 356, 97, 25);
		contentPane.add(btnResearch);
		
		tbxFindUser = new JTextField();
		tbxFindUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxFindUser.setBounds(45, 357, 165, 22);
		contentPane.add(tbxFindUser);
		tbxFindUser.setColumns(10);
		
		JLabel lblResearchAUser = new JLabel("Research a user to follow:");
		lblResearchAUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResearchAUser.setBounds(45, 324, 273, 19);
		contentPane.add(lblResearchAUser);
		
		JButton btnFollow = new JButton("Follow");
		btnFollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! listUserToFollow.isSelectionEmpty()) {
					sender.follow(login, listUserToFollow.getSelectedValue().toString());
				} else {
					lblResultResearchUser.setText("Select a user to follow him.");
				}
			}
		});
		btnFollow.setBounds(221, 432, 97, 25);
		contentPane.add(btnFollow);
		
		if (listFollowArray.isEmpty()) {
			lblResultListFollow.setText("You don't follow anyone.");
		}
		if (listFollowersArray.isEmpty()) {
			lblResultListFollowers.setText("No one is following you.");
		}
	}
}
