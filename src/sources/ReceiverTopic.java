package sources;

import javax.jms.*;
import javax.naming.*;
import javax.swing.SwingUtilities;

import Types.TypeConnection;
import Types.TypeMessage;
import design.MainView;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverTopic implements MessageListener, ExceptionListener {
	
	private InitialContext ctx;
	private Topic topic;
	private TopicConnectionFactory connFactory;
	private TopicConnection topicConn;
	private TopicSession topicSession;
	private TopicSubscriber topicSubscriber;
	private String topicName;
	private String login;
	private MainView view;
	
	public ReceiverTopic(String topicName, String login, List<String> listeFollow, MainView view) {
		this.view = view;
		this.topicName = topicName;
		this.login = login;
		 // get the initial context
	    try {
			this.ctx = new InitialContext();
			// lookup the topic object
		    this.topic = (Topic) ctx.lookup(topicName);
		                                                                       
		    // lookup the topic connection factory
		    this.connFactory = (TopicConnectionFactory) ctx.
		       lookup("ConnectionFactory");
		                                                                      
		    // create a topic connection
		    this.setTopicConn(connFactory.createTopicConnection());
		    if(topicName.equals("TopicMessage")) {
		    	getTopicConn().setClientID(login);
		    } else {
		    	getTopicConn().setClientID(login + "Loc");
		    }
		                                                                       
		    // create a topic session
		    this.setTopicSession(getTopicConn().createTopicSession(false,
		        Session.AUTO_ACKNOWLEDGE));
		    
		    String filter = this.addFilter(listeFollow);
		    
		    if(topicName.equals("TopicMessage")) {
		    	// create a topic subscriber
			    topicSubscriber = topicSession.createDurableSubscriber(topic, login, filter, true);
		    } else {
		    	// create a topic subscriber
			    topicSubscriber = topicSession.createDurableSubscriber(topic, login+"Loc", filter, true);
		    }
		    
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception
    {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UtilisateurSender senderSeConnecter = new UtilisateurSender();
						
						
						
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("test");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UtilisateurSender senderSeConnecter = new UtilisateurSender();
					MainView frame = new MainView(senderSeConnecter, "toto");
					frame.setVisible(true);
					UtilisateurSender senderSeConnecterTiti = new UtilisateurSender();
						
					MainView frameTiti = new MainView(senderSeConnecterTiti, "titi");
					frameTiti.setVisible(true);
					
					//SenderTopic.publishMessage(new TypeMessage("couou", "", "titi"));
					List<String> listFollowersArray = new ArrayList<String>();
					listFollowersArray.add("titi");
					// set an asynchronous message listener
				    ReceiverTopic asyncSubscriber = new ReceiverTopic("TopicMessage", "toto", listFollowersArray, frame);
				    
					asyncSubscriber.getTopicSubscriber().setMessageListener(asyncSubscriber);
					// set an asynchronous exception listener on the connection
				    asyncSubscriber.getTopicConn().setExceptionListener(asyncSubscriber);
				                                                                       
				    // start the connection
				    asyncSubscriber.getTopicConn().start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		
	    SenderTopic.publishMessage(new TypeMessage("couou", "", "titi"));
    }
                                                                           
    /**
       This method is called asynchronously by JMS when a message arrives
       at the topic. Client applications must not throw any exceptions in
       the onMessage method.
       @param message A JMS message.
     */
    public void onMessage(Message message)
    {
    	ObjectMessage om = (ObjectMessage) message;
		TypeMessage mess;
		try {
			mess = (TypeMessage) om.getObject();
			//MainView.updateListTweet(mess);
			System.out.println("received: " + mess.toString());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("fenetre: " + this.view.getName());
		System.out.println("for: "+this.login);
		
		
    }
                                                                           
    /**
       This method is called asynchronously by JMS when some error occurs.
       When using an asynchronous message listener it is recommended to use
       an exception listener also since JMS have no way to report errors
       otherwise.
       @param exception A JMS exception.
     */
    public void onException(JMSException exception)
    {
       System.err.println("something bad happended: " + exception);
    }
    
    public String addFilter(List<String> listeFollow){
    	String filter;
    	
    	if (! listeFollow.isEmpty()) {
    		filter = "JMSType IN (";
    		for (String login : listeFollow) {
        		//Si c'est le dernier élément
    			if (listeFollow.get(listeFollow.size()-1).equals(login)){
    				filter = filter + "'" + login + "')";
    			} else {
    				filter = filter + "'" + login + "',";
    			}
    		}
    	} else {
    		filter = "JMSType IN ('')";
    	}
    	
    	return filter;
    }
	public TopicSubscriber getTopicSubscriber() {
		return topicSubscriber;
	}
	public void setTopicSubscriber(TopicSubscriber topicSubscriber) {
		this.topicSubscriber = topicSubscriber;
	}
	public TopicConnection getTopicConn() {
		return topicConn;
	}
	public void setTopicConn(TopicConnection topicConn) {
		this.topicConn = topicConn;
	}
	public void seDeconnecter(){
   	 // close the topic connection
       try {
		topicConn.close();
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
	public TopicSession getTopicSession() {
		return topicSession;
	}
	public void setTopicSession(TopicSession topicSession) {
		this.topicSession = topicSession;
	}
}
