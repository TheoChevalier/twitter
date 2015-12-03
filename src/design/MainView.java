package design;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sources.ReceiverTopic;
import sources.SenderTopic;
import sources.UtilisateurSender;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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

import Types.TypeInscription;
import Types.TypeMessage;
import Types.TypeRecevoir;

import java.awt.ScrollPane;

import javax.jms.JMSException;
import javax.swing.AbstractListModel;
import java.awt.Component;

public class MainView extends JFrame implements Runnable {
	private static DefaultTableModel listTweetFeed;
	private static DefaultTableModel listTweetPosted;
	private ReceiverTopic asyncSubscriber;
	private ReceiverTopic asyncSubscriber1;
	private JPanel contentPane;
	private static UtilisateurSender sender;
	private String login;
	private JTextField tbxFindUser;
	private JTable table_1;
	private JTable table_2;
	private JTable table;
	private JTextField tbxLoc;
	private JLabel lblNbMsg;
	private final DefaultListModel listModelFollowers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UtilisateurSender senderSeConnecter = new UtilisateurSender();
						
						MainView frame = new MainView(senderSeConnecter, "toto");
						frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	/**
	 * Create the frame.
	 */
	public MainView(final UtilisateurSender sender, final String login) {
		
		this.sender = sender;
		this.login = login;
	    
	    List<String> listFollowArray = sender.listeFollow(login);
	    
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1165, 642);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 56, 1128, 532);
		contentPane.add(tabbedPane);
		
		String nb = Integer.toString(sender.nombreMessage(login));
		
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
		btnUpdateYourProfile.setBounds(849, 33, 177, 25);
		contentPane.add(btnUpdateYourProfile);
		btnUpdateYourProfile.setFont(new Font("Dialog", Font.PLAIN, 15));
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		btnLogOut.setBounds(1038, 33, 97, 25);
		contentPane.add(btnLogOut);
		btnLogOut.setFont(new Font("Dialog", Font.PLAIN, 15));

		final JLabel lblResultUnfollow = new JLabel("");
		lblResultUnfollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultUnfollow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultUnfollow.setBounds(222, 236, 302, 27);
		contentPane.add(lblResultUnfollow);
	    List<TypeMessage> listMessageFollow = sender.getMessageFollow(login);
	    
	    int i = 0;
	    Object[][] res;
	    if (listMessageFollow != null) {
	    	res = new Object[listMessageFollow.size()][5];
		    for (TypeMessage typeMessage : listMessageFollow) {
		    	res[i][0] = typeMessage.getContenu();
		    	String date = typeMessage.getTimestamp();
		    	res[i][1] = date.substring(0, 10);
		    	res[i][2] = date.substring(10);
		    	res[i][3] = typeMessage.getLoc();
		    	res[i][4] = typeMessage.getLoginSender();
		    	i++;
			}
	    } else {
	    	res = new Object[0][5];
	    }
	    
	    
	 
	    	listTweetFeed = new DefaultTableModel(
				res,
		    	new String[] {
		    		"Content", "Date", "Heure", "Location", "From" 
		    	});
	    	
	    	
		String nbM = Integer.toString(sender.nombreMessage(login));
		
		final DefaultListModel listModelFollow = new DefaultListModel();
		for(String f:listFollowArray) {
			listModelFollow.addElement(f);
		}
		
		List<String> listFollowersArray = sender.listeFollowers(login);

		listModelFollowers = new DefaultListModel();
		for(String f:listFollowersArray) {
			listModelFollowers.addElement(f);
		}
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("General", null, panel, null);
		panel.setLayout(null);
		final JTextPane tbxContenu = new JTextPane();
		tbxContenu.setBounds(12, 346, 381, 80);
		panel.add(tbxContenu);
		
		final JLabel lblResultListFollow = new JLabel("");
		lblResultListFollow.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollow.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblResultListFollow.setBounds(12, 269, 335, 27);
		panel.add(lblResultListFollow);
		
		JLabel label_8 = new JLabel("Followers:");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_8.setBounds(847, 44, 129, 19);
		panel.add(label_8);
		
		final JLabel lblResultListFollowers = new JLabel("");
		lblResultListFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultListFollowers.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblResultListFollowers.setBounds(847, 269, 258, 27);
		panel.add(lblResultListFollowers);
		
		JLabel label_10 = new JLabel("");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_10.setBounds(45, 679, 381, 27);
		panel.add(label_10);
		
		final JLabel lblResultPostMsg = new JLabel("");
		lblResultPostMsg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultPostMsg.setBounds(12, 468, 382, 27);
		panel.add(lblResultPostMsg);
		
		final JLabel lblResultResearchUser = new JLabel("");
		lblResultResearchUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblResultResearchUser.setBounds(430, 269, 292, 27);
		panel.add(lblResultResearchUser);
		
				
				JLabel label_1 = new JLabel("Number of Tweets:");
				label_1.setFont(new Font("Dialog", Font.PLAIN, 15));
				label_1.setBounds(12, 13, 142, 19);
				panel.add(label_1);
				lblNbMsg = new JLabel(nbM);
				lblNbMsg.setFont(new Font("Dialog", Font.BOLD, 15));
				lblNbMsg.setBounds(174, 14, 56, 16);
				panel.add(lblNbMsg);
				
				JLabel label_3 = new JLabel("Follow:");
				label_3.setFont(new Font("Tahoma", Font.BOLD, 15));
				label_3.setBounds(14, 44, 129, 19);
				panel.add(label_3);
				
				final JList listFollow = new JList(listModelFollow);
				listFollow.setBounds(14, 76, 216, 174);
				panel.add(listFollow);
				
				final JList listFollowers = new JList(listModelFollowers);
				listFollowers.setBounds(847, 76, 258, 174);
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
	            		asyncSubscriber.seDeconnecter();
	        			asyncSubscriber1.seDeconnecter();
	        			
	        			majFiltre();
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
				btnUnfollow.setBounds(250, 123, 97, 25);
				panel.add(btnUnfollow);
				
				JLabel label_4 = new JLabel("Search a user to follow:");
				label_4.setFont(new Font("Tahoma", Font.BOLD, 15));
				label_4.setBounds(436, 32, 273, 19);
				panel.add(label_4);
				
				tbxFindUser = new JTextField();
				tbxFindUser.setFont(new Font("Dialog", Font.PLAIN, 15));
				tbxFindUser.setColumns(10);
				tbxFindUser.setBounds(436, 73, 165, 22);
				panel.add(tbxFindUser);
				
				final JList listUserToFollow = new JList();
				listUserToFollow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				listUserToFollow.setBounds(436, 107, 165, 143);
				panel.add(listUserToFollow);
				
				
				JButton btnResearch = new JButton("Search");
				btnResearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (! tbxFindUser.getText().isEmpty()) {
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
				btnResearch.setBounds(625, 72, 97, 25);
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
	            		lblResultListFollow.setText("");
	            		asyncSubscriber.seDeconnecter();
	        			asyncSubscriber1.seDeconnecter();
	        			
	        			majFiltre();
	        			
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
				btnFollow.setBounds(625, 123, 97, 25);
				panel.add(btnFollow);
				

				
				JButton btnSend = new JButton("Send");
				btnSend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(! tbxContenu.getText().isEmpty()) {
							String newLoc = tbxLoc.getText();
							newLoc = newLoc.replace("'", "’");
							System.out.println("New ville: "+newLoc);
							TypeMessage m = new TypeMessage(tbxContenu.getText(), newLoc, login);
								int idMessage = sender.ajouterMessage(m);
								if (idMessage != -1) {
									if (SenderTopic.publishMessage(m, idMessage)) {
										updateListTweet(m, login);
										lblResultPostMsg.setText("Your tweet has been sent.");
									}
								} else {
									lblResultPostMsg.setText("An error occured while sending your tweet.");
								}
						} else {
							lblResultPostMsg.setText("Please fill in all fields.");
						}
					}
				});
				btnSend.setBounds(405, 401, 97, 25);
				panel.add(btnSend);
				
				JLabel lblGeolocation = new JLabel("Geolocation");
				lblGeolocation.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblGeolocation.setBounds(12, 439, 108, 22);
				panel.add(lblGeolocation);
				
				JLabel lblNewLabel = new JLabel("Post a message");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
				lblNewLabel.setBounds(12, 309, 381, 27);
				panel.add(lblNewLabel);
				
				setTbxLoc(new JTextField());
				getTbxLoc().setFont(new Font("Tahoma", Font.PLAIN, 15));
				getTbxLoc().setBounds(136, 440, 151, 22);
				panel.add(getTbxLoc());
				getTbxLoc().setColumns(10);
				
				JButton btnUpdate = new JButton("Update");
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						updateListFollowers();
					}
				});
				btnUpdate.setBounds(1008, 312, 97, 25);
				panel.add(btnUpdate);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Feed", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Twitter feed");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 22));
		label.setBounds(435, 12, 296, 27);
		panel_1.add(label);
		
			    table_2 = new JTable();
			    table_2.setEnabled(false);
			    table_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
			    table_2.setModel(listTweetFeed);
			    
			     
			    
			    //Définition des colonnes
			    table_2.getColumnModel().getColumn(0).setPreferredWidth(500);
			    table_2.getColumnModel().getColumn(1).setPreferredWidth(50);
			    table_2.getColumnModel().getColumn(2).setPreferredWidth(50);
			    
			    table_2.setBounds(209, 840, 421, 145);
			    
			    //Permet d'afficher l'en-tête
			    JScrollPane scrollPane = new JScrollPane(table_2);
			    scrollPane.setBounds(12, 64, 1099, 425);
			    
			    panel_1.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Tweet", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblTweets = new JLabel("Tweet");
		lblTweets.setBounds(524, 5, 123, 29);
		lblTweets.setHorizontalAlignment(SwingConstants.CENTER);
		lblTweets.setFont(new Font("Dialog", Font.BOLD, 22));
		panel_2.add(lblTweets);
		
		table = new JTable();
		table.setEnabled(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    List<TypeMessage> listMessages = sender.getMessages(login);
	    
	    i = 0;
	    Object[][] resTabTweet;
	    if (listMessages != null) {
	    	resTabTweet = new Object[listMessages.size()][4];
		    for (TypeMessage typeMessage : listMessages) {
		    	resTabTweet[i][0] = typeMessage.getContenu();
		    	String date = typeMessage.getTimestamp();
		    	resTabTweet[i][1] = date.substring(0, 10);
		    	resTabTweet[i][2] = date.substring(10);
		    	resTabTweet[i][3] = typeMessage.getLoc();
		    	i++;
			}
	    } else {
	    	resTabTweet = new Object[0][4];
	    }
	    
	    	listTweetPosted = new DefaultTableModel(
	    		resTabTweet,
		    	new String[] {
		    		"Content", "Date", "Heure", "Location"
		    	});
    		table.setModel(listTweetPosted);
    	
    		//Définition des colonnes
    	    table.getColumnModel().getColumn(0).setPreferredWidth(500);
    	    table.getColumnModel().getColumn(1).setPreferredWidth(50);
    	    table.getColumnModel().getColumn(2).setPreferredWidth(50);
    	    
    	    table.setBounds(209, 840, 421, 145);
    	    
    	    //Permet d'afficher l'en-tête
    	    JScrollPane scrollPane1 = new JScrollPane(table);
    	    scrollPane1.setBounds(12, 64, 1099, 421);
    	    
    	    panel_2.add(scrollPane1);

		if (listFollowArray.isEmpty()) {
			lblResultListFollow.setText("You’re not following anyone.");
		}
		if (listFollowersArray.isEmpty()) {
			lblResultListFollowers.setText("No one is following you.");
		}
		
		TypeInscription user = sender.getUtilisateur(login);
		
		// set an asynchronous message listener
		this.asyncSubscriber = new ReceiverTopic("TopicMessage", login, user.getVille(), listFollowArray, this);
				    
		                       
		// set an asynchronous message listener
		this.asyncSubscriber1 = new ReceiverTopic("TopicMessageLoc", login, user.getVille(), listFollowArray, this);
	}

	private void close() {
		this.dispose();
	}
	
	
	public void confirm(){
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, "Do you really want to log out?", "Log Out", dialogButton);
		if(dialogResult == 0) {
			sender.seDeconnecter();
			
			asyncSubscriber.seDeconnecter();
			asyncSubscriber1.seDeconnecter();
			close();
		}
	}
	public void updateListTweet(TypeMessage m, String login){
		String date = m.getTimestamp();
		String row[] = {m.getContenu(), date.substring(0, 10), date.substring(10), m.getLoc(), m.getLoginSender()};
		listTweetPosted.addRow(row);
		String nbM = Integer.toString(sender.nombreMessage(login));
		this.lblNbMsg.setText(nbM);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public JTextField getTbxLoc() {
		return tbxLoc;
	}

	public void setTbxLoc(JTextField tbxLoc) {
		this.tbxLoc = tbxLoc;
	}
	
	public void majFiltre(){
		List<String> listFollowArray = sender.listeFollow(login);
		TypeInscription user = sender.getUtilisateur(login);
		 
	    // set an asynchronous message listener
		asyncSubscriber = new ReceiverTopic("TopicMessage", login, user.getVille(), listFollowArray, this);
				    
		                       
		// set an asynchronous message listener
		asyncSubscriber1 = new ReceiverTopic("TopicMessageLoc", login, user.getVille(), listFollowArray, this);
	}
	
	public void updateUI(final TypeMessage m, final int idMessage) {
		  SwingUtilities.invokeLater(
		    new Runnable(){
		      @Override
		      public void run() {
		    	  updatelistTweetFeed(m, idMessage);
		      }
		    }
		  );
		}
	
	public void updatelistTweetFeed(TypeMessage m, int idMessage) {
    	String date = m.getTimestamp();
		Object[] row = {m.getContenu(), date.substring(0, 10), date.substring(10), m.getLoc(),m.getLoginSender()};
		listTweetFeed.addRow(row);
		TypeRecevoir tr = new TypeRecevoir(idMessage, login);
		switch (sender.ajouterRecevoir(tr)) {
			case 0:
				System.out.println("You received the message: "+m.toString());
				break;
			default:
				System.out.println("An error occured while receving the message.");
				break;
		}
	}
	
	public void updateListFollowers() {
		List<String> listFollowersArray = sender.listeFollowers(login);
		listModelFollowers.clear();
		for(String f:listFollowersArray) {
			listModelFollowers.addElement(f);
		}
	}
}
