package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sources.UtilisateurSender;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JTable;

public class MainView extends JFrame {

	private JPanel contentPane;
	private UtilisateurSender sender;
	private String login;
	private JTextField tbxFindUser;
	private JTable table;

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
	public MainView(final UtilisateurSender sender, final String login) {
		this.sender = sender;
		this.login = login;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 758, 1200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTweets = new JLabel("Number of Tweets:");
		lblTweets.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTweets.setBounds(12, 84, 142, 19);
		contentPane.add(lblTweets);
		
		JLabel lblNbMsg = new JLabel("New label");
		lblNbMsg.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNbMsg.setBounds(174, 85, 56, 16);
		contentPane.add(lblNbMsg);
		
		String nb = Integer.toString(sender.nombreMessage(login));
		lblNbMsg.setText(nb);
		
		JLabel lblWelcomeToTwitter = new JLabel("Welcome to Twitter, " + login + "!");
		lblWelcomeToTwitter.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToTwitter.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblWelcomeToTwitter.setBounds(12, 13, 716, 27);
		contentPane.add(lblWelcomeToTwitter);

		List<String> listFollowArray = sender.listeFollow(login);
		final DefaultListModel listModelFollow = new DefaultListModel();
		for(String f:listFollowArray) {
			listModelFollow.addElement(f);
		}
		final JList listFollow = new JList(listModelFollow);
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
		lblFollowers.setBounds(536, 120, 129, 19);
		contentPane.add(lblFollowers);
		
		List<String> listFollowersArray = sender.listeFollowers(login);
		final DefaultListModel listModelFollowers = new DefaultListModel();
		for(String f:listFollowersArray) {
			listModelFollowers.addElement(f);
		}
		final JList listFollowers = new JList(listModelFollowers);
		
		listFollowers.setBounds(536, 150, 165, 119);
		contentPane.add(listFollowers);
		
		JLabel lblResultListFollowers = new JLabel("");
		lblResultListFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollowers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultListFollowers.setBounds(520, 282, 208, 27);
		contentPane.add(lblResultListFollowers);
		
		final JLabel lblResultResearchUser = new JLabel("");
		lblResultResearchUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultResearchUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultResearchUser.setBounds(22, 503, 296, 27);
		contentPane.add(lblResultResearchUser);
		
		final JList listUserToFollow = new JList();
		listUserToFollow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserToFollow.setBounds(45, 392, 165, 98);
		contentPane.add(listUserToFollow);
		
		JButton btnResearch = new JButton("Search");
		btnResearch.putClientProperty("login", login );
		btnResearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! tbxFindUser.getText().isEmpty()) {
					String login = (String)((JButton)arg0.getSource()).getClientProperty("login");
					List<String> listUserToFollowArray = sender.rechercherUtilisateur(tbxFindUser.getText(), login);
					listUserToFollow.setListData(listUserToFollowArray.toArray());
					if (listUserToFollowArray.isEmpty()) {
						lblResultResearchUser.setText("No result.");
					}
				} else {
					lblResultResearchUser.setText("Please type a login to search.");
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
		
		JLabel lblResearchAUser = new JLabel("Search a user to follow:");
		lblResearchAUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResearchAUser.setBounds(45, 324, 273, 19);
		contentPane.add(lblResearchAUser);
		
		JButton btnFollow = new JButton("Follow");
		btnFollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! listUserToFollow.isSelectionEmpty()) {
	            	switch(sender.follow(login, listUserToFollow.getSelectedValue().toString())) {
	            	case 1:
	            		lblResultResearchUser.setText("You’re already following this user.");
	            		break;
	            	case 0:
	            		lblResultResearchUser.setText("You’re now following this user.");
	            		listModelFollow.addElement(listUserToFollow.getSelectedValue().toString());
	            		break;
	            	default:
	            		lblResultResearchUser.setText("Oops, an error occured while trying to follow this user. Try again later.");
	            		break;
            	}
				} else {
					lblResultResearchUser.setText("Select a user to follow him.");
				}
			}
		});
		btnFollow.setBounds(221, 432, 97, 25);
		contentPane.add(btnFollow);
		
		JLabel lblPostAMessage = new JLabel("Post a message:");
		lblPostAMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPostAMessage.setBounds(45, 543, 199, 33);
		contentPane.add(lblPostAMessage);
		
		final JTextPane tbxContenu = new JTextPane();
		tbxContenu.setBounds(45, 579, 381, 80);
		contentPane.add(tbxContenu);
		
		final JLabel lblResultPostMsg = new JLabel("");
		lblResultPostMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultPostMsg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultPostMsg.setBounds(45, 673, 381, 27);
		contentPane.add(lblResultPostMsg);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! tbxContenu.getText().isEmpty()) {
					
				} else {
					lblResultPostMsg.setText("Please fill in all fields.");
				}
			}
		});
		btnSend.setBounds(438, 634, 97, 25);
		contentPane.add(btnSend);
		
		final JLabel lblResultUnfollow = new JLabel("");
		lblResultUnfollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultUnfollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultUnfollow.setBounds(222, 236, 302, 27);
		contentPane.add(lblResultUnfollow);
		
		JButton btnUnfollow = new JButton("Unfollow");
		btnUnfollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (! listFollow.isSelectionEmpty()) {
	            	switch(sender.unfollow(login, listFollow.getSelectedValue().toString())) {
	            	case 1:
	            		lblResultUnfollow.setText("You’re not following this user yet.");
	            		break;
	            	case 0:
	            		lblResultUnfollow.setText("You’re not following this user anymore.");
	            		int selectedIndex = listFollow.getSelectedIndex();
	            		listModelFollow.remove(selectedIndex);
	            		break;
	            	default:
	            		lblResultUnfollow.setText("Oops, an error occured while trying to unfollow this user. Try again later.");
	            		break;
            	}
				} else {
					lblResultUnfollow.setText("Select a user to unfollow him.");
				}
			}
		});
		btnUnfollow.setBounds(221, 198, 97, 25);
		contentPane.add(btnUnfollow);

        DefaultTableModel model = new DefaultTableModel(new Object[][] {
            { "some", "text" } },
            new Object[] { "Column 1", "Column 2" });

		
		table = new JTable(model);
		
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		/*
        DefaultTableModel dtm = new DefaultTableModel();

        Object [] rowData = new Object[4];
        for (int i = 0; i < rowData.length; ++i)
        {
            rowData[i] = "MAGGLE";
        }
        dtm.addRow(rowData);

        table.setModel(dtm);
        dtm.fireTableDataChanged();
        //////////////////////////*/
        


		table.setBounds(45, 800, 683, 244);
		contentPane.add(table);
		
		JLabel label = new JLabel("Twitter feed");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 22));
		label.setBounds(45, 761, 683, 27);
		contentPane.add(label);
		
		JButton btnUpdateYourProfile = new JButton("Update your profile");
		btnUpdateYourProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Profile frame = new Profile(sender, login);
				frame.setVisible(true);
			}
		});
		btnUpdateYourProfile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdateYourProfile.setBounds(536, 60, 177, 25);
		contentPane.add(btnUpdateYourProfile);
		
		if (listFollowArray.isEmpty()) {
			lblResultListFollow.setText("You’re not following anyone.");
		}
		if (listFollowersArray.isEmpty()) {
			lblResultListFollowers.setText("No one is following you.");
		}
	}
}
