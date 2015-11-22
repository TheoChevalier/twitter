package design;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.AbstractListModel;

public class MainView extends JFrame {

	private JPanel contentPane;
	private UtilisateurSender sender;
	private String login;
	private JTextField tbxFindUser;
	private JTable table;
	private JTable table_1;
	private JTable table_2;

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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1047, 593);
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
		lblWelcomeToTwitter.setBounds(12, 13, 1005, 27);
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
		lblFollowers.setBounds(825, 121, 129, 19);
		contentPane.add(lblFollowers);
		
		List<String> listFollowersArray = sender.listeFollowers(login);
		final DefaultListModel listModelFollowers = new DefaultListModel();
		for(String f:listFollowersArray) {
			listModelFollowers.addElement(f);
		}
		final JList listFollowers = new JList(listModelFollowers);
		
		listFollowers.setBounds(825, 151, 165, 119);
		contentPane.add(listFollowers);
		
		JLabel lblResultListFollowers = new JLabel("");
		lblResultListFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollowers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultListFollowers.setBounds(809, 283, 208, 27);
		contentPane.add(lblResultListFollowers);
		
		final JLabel lblResultResearchUser = new JLabel("");
		lblResultResearchUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultResearchUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultResearchUser.setBounds(403, 293, 296, 27);
		contentPane.add(lblResultResearchUser);
		
		final JList listUserToFollow = new JList();
		listUserToFollow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserToFollow.setBounds(426, 182, 165, 98);
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
		btnResearch.setBounds(602, 146, 97, 25);
		contentPane.add(btnResearch);
		
		tbxFindUser = new JTextField();
		tbxFindUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbxFindUser.setBounds(426, 147, 165, 22);
		contentPane.add(tbxFindUser);
		tbxFindUser.setColumns(10);
		
		JLabel lblResearchAUser = new JLabel("Search a user to follow:");
		lblResearchAUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResearchAUser.setBounds(426, 114, 273, 19);
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
		btnFollow.setBounds(602, 222, 97, 25);
		contentPane.add(btnFollow);
		
		JLabel lblPostAMessage = new JLabel("Post a message:");
		lblPostAMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPostAMessage.setBounds(12, 333, 465, 33);
		contentPane.add(lblPostAMessage);
		
		final JTextPane tbxContenu = new JTextPane();
		tbxContenu.setBounds(12, 369, 322, 127);
		contentPane.add(tbxContenu);
		
		final JLabel lblResultPostMsg = new JLabel("");
		lblResultPostMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultPostMsg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultPostMsg.setBounds(12, 509, 322, 27);
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
		btnSend.setBounds(346, 471, 97, 25);
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

        /*DefaultTableModel model = new DefaultTableModel(new Object[][] {
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
        
		

		//table.setBounds(45, 800, 683, 244);
		//contentPane.add(table);
		
		JLabel label = new JLabel("Twitter feed");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 22));
		label.setBounds(471, 324, 546, 27);
		contentPane.add(label);
		
		JButton btnUpdateYourProfile = new JButton("Update your profile");
		btnUpdateYourProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Profile frame = new Profile(sender, login);
				frame.setVisible(true);
			}
		});
		btnUpdateYourProfile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdateYourProfile.setBounds(731, 64, 177, 25);
		contentPane.add(btnUpdateYourProfile);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogOut.setBounds(920, 64, 97, 25);
		contentPane.add(btnLogOut);
		
	    table_2 = new JTable();
	    table_2.setEnabled(false);
	    table_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    table_2.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{"Toto", "salut ça va bien ?", "22/11/2015"},
	    		{"Titi", "Test", "20/11/2015"}
	    	},
	    	new String[] {
	    		"Login", "Content", "Date"
	    	}
	    ));
	    
	    //Définition des colonnes
	    table_2.getColumnModel().getColumn(0).setPreferredWidth(136);
	    table_2.getColumnModel().getColumn(1).setPreferredWidth(323);
	    table_2.getColumnModel().getColumn(2).setPreferredWidth(110);
	    
	    table_2.setBounds(209, 840, 421, 145);
	    
	    //Permet d'afficher l'en-tête
	    JScrollPane scrollPane = new JScrollPane(table_2);
	    scrollPane.setBounds(471, 364, 546, 172);
	    
	    contentPane.add(scrollPane);
        
		if (listFollowArray.isEmpty()) {
			lblResultListFollow.setText("You’re not following anyone.");
		}
		if (listFollowersArray.isEmpty()) {
			lblResultListFollowers.setText("No one is following you.");
		}
	}
	
	private void close() {
		this.dispose();
	}
	
	public void confirm(){
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, "Do you really want to log out?", "Log Out", dialogButton);
		if(dialogResult == 0) {
			sender.seDeconnecter();
			close();
		}
	}
}
