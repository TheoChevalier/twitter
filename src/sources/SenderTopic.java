package sources;

import javax.jms.*;
import javax.naming.*;

import Types.TypeMessage;


public class SenderTopic {
	private InitialContext ctx;
	private Topic topic;
	private TopicConnectionFactory connFactory;
	private TopicConnection topicConn;
	private TopicSession topicSession;
	private TopicPublisher topicPublisher;
	private String topicName;
	public static SenderTopic st = new SenderTopic("TopicMessage");
	public static SenderTopic stLoc = new SenderTopic("TopicMessageLoc");
	
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
		//TypeMessage m = new TypeMessage("coucou", "", "titi");
		
		//publishMessage(m);
		// close the topic connection
		//topicConn.close();
	}
	
	public static boolean publishMessage(TypeMessage m, int idMessage) {
		String login = m.getLoginSender();
		String loc = m.getLoc();
		
		ObjectMessage objectMessage;
		try {
			if (loc.isEmpty()) {
				objectMessage = st.topicSession.createObjectMessage(m);
			}
			else {
				objectMessage = stLoc.topicSession.createObjectMessage(m);
			}
			objectMessage.setJMSType(login);
			objectMessage.setIntProperty("ID", idMessage);
			
			
			// publish the messages
			if (loc.isEmpty()) {
				st.topicPublisher.publish(objectMessage);
			} else {
				stLoc.topicPublisher.publish(objectMessage);
			}
			
			// print what we did
			System.out.println("published: " + objectMessage.getObject().toString());
			return true;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
