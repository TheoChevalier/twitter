package sources;

import javax.jms.*;
import javax.naming.*;

import Types.TypeConnection;
import Types.TypeMessage;
import design.MainView;

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
	
	public ReceiverTopic(String topicName, String login, List<String> listeFollow) {
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
		    this.topicConn = connFactory.createTopicConnection();
		    topicConn.setClientID(login);
		                                                                       
		    // create a topic session
		    this.topicSession = topicConn.createTopicSession(false,
		        Session.AUTO_ACKNOWLEDGE);
		    
		    String filter = this.addFilter(listeFollow);
		                                                                       
		    // create a topic subscriber
		    this.topicSubscriber = topicSession.createDurableSubscriber(topic, login, filter, true);
		    
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
		List<String> liste =  new ArrayList<String>() ;
		liste.add("toto");
		
    // set an asynchronous message listener
    ReceiverTopic asyncSubscriber = new ReceiverTopic("TopicMessage", "titi", liste);
    asyncSubscriber.topicSubscriber.setMessageListener(asyncSubscriber);
                                                                      
    // set an asynchronous exception listener on the connection
    asyncSubscriber.topicConn.setExceptionListener(asyncSubscriber);
                                                                       
    // start the connection
    asyncSubscriber.topicConn.start();
                           
    // wait for messages
    System.out.print("waiting for messages...");
                                                                      
    // close the topic connection
    //topicConn.close();
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
    		filter = "JMSType IN ''";
    	}
    	
    	return filter;
    }
}
