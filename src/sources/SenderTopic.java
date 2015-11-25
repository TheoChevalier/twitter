package sources;

import javax.jms.*;
import javax.naming.*;


public class SenderTopic {
	private InitialContext ctx;
	private Topic topic;
	private TopicConnectionFactory connFactory;
	private TopicConnection topicConn;
	private TopicSession topicSession;
	private TopicPublisher topicPublisher;
	private String topicName;
	
	public SenderTopic(String topicName){
		this.topicName = topicName;
		
		try {
			//get the initial context
			this.ctx = new InitialContext();
			
			// lookup the topic object
			this.topic = (Topic) ctx.lookup(topicName);
			                                                                    
			// lookup the topic connection factory
			this.connFactory = (TopicConnectionFactory) ctx.
			    lookup("ConnectionFactory");
			                                                                   
			// create a topic connection
			this.topicConn = connFactory.createTopicConnection();
			                                                                   
			// create a topic session
			this.topicSession = topicConn.createTopicSession(false, 
			   Session.AUTO_ACKNOWLEDGE);
			                                                                  
			// create a topic publisher
			this.topicPublisher = topicSession.createPublisher(topic);

			this.topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		                                                                      
		
	}
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		publishMessage("titi", "Premier tweet", "");
		// close the topic connection
		//topicConn.close();
	}
	
	public static void publishMessage(String login, String mes, String loc) {
		SenderTopic st;
		if (loc.isEmpty()){
			st = new SenderTopic("TopicMessage");
		} else {
			st = new SenderTopic("TopicMessageLoc");
		}
		
		try {
			TextMessage message = st.topicSession.createTextMessage();
			message.setText(mes);
			message.setJMSType(login);
			// publish the messages
			st.topicPublisher.publish(message);
			
			// print what we did
			System.out.println("published: " + message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
