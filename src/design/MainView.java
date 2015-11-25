package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sources.SenderTopic;
import sources.UtilisateurSender;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
<<<<<<< Upstream, based on branch 'master' of git@github.com:TheoChevalier/twitter.git

import Types.TypeMessage;

=======
>>>>>>> 09b9de6 tabbed-panes UI
import java.awt.ScrollPane;
import javax.swing.AbstractListModel;

public class MainView extends JFrame {

	private JPanel contentPane;
	private UtilisateurSender sender;
	private String login;
	private JTextField tbxFindUser;
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
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 56, 1176, 704);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("General", null, panel, null);
		panel.setLayout(null);
		
		String nb = Integer.toString(sender.nombreMessage(login));
		final JTextPane tbxContenu = new JTextPane();
		tbxContenu.setBounds(370, 377, 381, 80);
		panel.add(tbxContenu);
		
		final JLabel lblResultListFollow = new JLabel("");
		lblResultListFollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollow.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblResultListFollow.setBounds(65, 268, 265, 27);
		panel.add(lblResultListFollow);
		
		JLabel label_8 = new JLabel("Followers:");
		label_8.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_8.setBounds(890, 43, 129, 19);
		panel.add(label_8);
		
		final JLabel lblResultListFollowers = new JLabel("");
		lblResultListFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollowers.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblResultListFollowers.setBounds(890, 268, 258, 27);
		panel.add(lblResultListFollowers);
		
		JLabel label_10 = new JLabel("");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_10.setBounds(45, 679, 381, 27);
		panel.add(label_10);
		
		final JLabel lblResultPostMsg = new JLabel("");
		lblResultPostMsg.setBounds(370, 499, 382, 15);
		panel.add(lblResultPostMsg);
		
		final JLabel lblResultResearchUser = new JLabel("");
		lblResultResearchUser.setBounds(473, 280, 279, 15);
		panel.add(lblResultResearchUser);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Feed", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Twitter feed");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 22));
		label.setBounds(435, 12, 296, 27);
		panel_1.add(label);
		
<<<<<<< Upstream, based on branch 'master' of git@github.com:TheoChevalier/twitter.git
		JLabel label_11 = new JLabel("Welcome to Twitter, "+ login +"!");
		label_11.setBounds(0, 16, 1188, 27);
		contentPane.add(label_11);
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("Dialog", Font.BOLD, 22));
		
		JButton btnUpdateYourProfile = new JButton("Update your profile");
		btnUpdateYourProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Profile frame = new Profile(sender, login);
				frame.setVisible(true);
			}
		});
		btnUpdateYourProfile.setBounds(884, 33, 177, 25);
		contentPane.add(btnUpdateYourProfile);
		btnUpdateYourProfile.setFont(new Font("Dialog", Font.PLAIN, 15));
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		btnLogOut.setBounds(1073, 33, 97, 25);
=======
		JLabel label_11 = new JLabel("Welcome to Twitter, <dynamic>!");
		label_11.setBounds(0, 15, 1188, 27);
		contentPane.add(label_11);
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("Dialog", Font.BOLD, 22));
		
		JButton btnUpdateYourProfile = new JButton("Update your profile");
		btnUpdateYourProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Profile frame = new Profile(sender, login);
				frame.setVisible(true);
			}
		});
		btnUpdateYourProfile.setBounds(902, 19, 177, 25);
		contentPane.add(btnUpdateYourProfile);
		btnUpdateYourProfile.setFont(new Font("Dialog", Font.PLAIN, 15));
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		btnLogOut.setBounds(1091, 19, 97, 25);
>>>>>>> 09b9de6 tabbed-panes UI
		contentPane.add(btnLogOut);
		btnLogOut.setFont(new Font("Dialog", Font.PLAIN, 15));

		final JLabel lblResultUnfollow = new JLabel("");
		lblResultUnfollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultUnfollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultUnfollow.setBounds(222, 236, 302, 27);
		contentPane.add(lblResultUnfollow);


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
		

	    table_2 = new JTable();
	    table_2.setEnabled(false);
	    table_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
<<<<<<< Upstream, based on branch 'master' of git@github.com:TheoChevalier/twitter.git
	    List<TypeMessage> listMessageFollow = sender.getMessageFollow(login);
	    
	    //if (listMessageFollow != null) {
	    	/*Object[][] res = new Object[listMessageFollow.size()][];
    		listMessageFollow.toArray(res);
    		
    		Object[][] array = new Object[listMessageFollow.size()][];
    		for (int i = 0; i < listMessageFollow.size(); i++) {
    		    String[] row = {listMessageFollow.get(i).getContenu(),
    		    		listMessageFollow.get(i).getTimestamp(),
    		    		listMessageFollow.get(i).getLoc(),
    		    		listMessageFollow.get(i).getLoginSender()
    		    };
    		    array[i] = row;
    		}
    		
    		*/
    		table_2.setModel(new DefaultTableModel(
    				new Object[][] {
    		    		{"Toto", "salut ça va bien ?", "22/11/2015"},
    		    		{"Titi", "Test", "20/11/2015"}
    		    	},
    		    	new String[] {
    		    		"Content", "Date", "Loc", "Login" 
    		    	}
    		));
	    //}
	     
	    
	    //Définition des colonnes
	    table_2.getColumnModel().getColumn(0).setPreferredWidth(136);
	    table_2.getColumnModel().getColumn(1).setPreferredWidth(323);
	    table_2.getColumnModel().getColumn(2).setPreferredWidth(110);
	    
	    table_2.setBounds(209, 840, 421, 145);
	    
	    //Permet d'afficher l'en-tête
	    JScrollPane scrollPane = new JScrollPane(table_2);
	    scrollPane.setBounds(12, 64, 1147, 575);
=======
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
>>>>>>> 09b9de6 tabbed-panes UI
	    
	    panel_1.add(scrollPane);

		
		JLabel label_1 = new JLabel("Number of Tweets:");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_1.setBounds(55, 12, 142, 19);
		panel.add(label_1);
		
		JLabel lblNbMsg = new JLabel("0");
		lblNbMsg.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNbMsg.setBounds(217, 13, 56, 16);
		panel.add(lblNbMsg);
		
		JLabel label_3 = new JLabel("Follow:");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_3.setBounds(57, 43, 129, 19);
		panel.add(label_3);
		
		List<String> listFollowArray = sender.listeFollow(login);
		final DefaultListModel listModelFollow = new DefaultListModel();
		for(String f:listFollowArray) {
			listModelFollow.addElement(f);
		}
		
		List<String> listFollowersArray = sender.listeFollowers(login);
		final DefaultListModel listModelFollowers = new DefaultListModel();
		for(String f:listFollowersArray) {
			listModelFollowers.addElement(f);
		}
		
		final JList listFollow = new JList(listModelFollow);
		listFollow.setBounds(57, 75, 216, 174);
		panel.add(listFollow);
		
		final JList listFollowers = new JList(listModelFollowers);
		listFollowers.setBounds(890, 75, 258, 174);
		panel.add(listFollowers);
		
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
		btnUnfollow.setBounds(293, 122, 97, 25);
		panel.add(btnUnfollow);
		
		JLabel label_4 = new JLabel("Search a user to follow:");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_4.setBounds(479, 31, 273, 19);
		panel.add(label_4);
		
		tbxFindUser = new JTextField();
		tbxFindUser.setFont(new Font("Dialog", Font.PLAIN, 15));
		tbxFindUser.setColumns(10);
		tbxFindUser.setBounds(479, 72, 165, 22);
		panel.add(tbxFindUser);
		
		final JList listUserToFollow = new JList();
		listUserToFollow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserToFollow.setBounds(479, 106, 165, 143);
		panel.add(listUserToFollow);
		
		
		JButton btnResearch = new JButton("Search");
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
		btnResearch.setBounds(668, 71, 97, 25);
		panel.add(btnResearch);
		

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
		btnFollow.setBounds(668, 122, 97, 25);
		panel.add(btnFollow);
		

		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! tbxContenu.getText().isEmpty()) {
<<<<<<< Upstream, based on branch 'master' of git@github.com:TheoChevalier/twitter.git
					TypeMessage m = new TypeMessage(tbxContenu.getText(), "", login);
					if (SenderTopic.publishMessage(m)) {
						switch (sender.ajouterMessage(m)) {
						case 0:
							lblResultPostMsg.setText("Your tweet has been send.");
							break;
						default:
							lblResultPostMsg.setText("An error occured while sending your tweet.");
							break;
						};
					}
=======
>>>>>>> 09b9de6 tabbed-panes UI
					
				} else {
					lblResultPostMsg.setText("Please fill in all fields.");
				}
			}
		});
		btnSend.setBounds(763, 432, 97, 25);
		panel.add(btnSend);
		

		
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
