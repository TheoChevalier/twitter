package sources;

import javax.jms.*;
import javax.naming.*;


public class SenderTopic {

	Session session = null;
    MessageProducer sender = null;
    MessageProducer senderGeo = null;
	

    public SenderTopic()
    {
    	
    }
    
	public void startJMSConnection() {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        
        String topicDest = "TopicMess";
        String topicDestGeo = "TopicMessGeo";
        
        Destination dest = null;
        Destination destGeo = null;
        
        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(topicDest);
            destGeo = (Destination) context.lookup(topicDestGeo);
            
            // create the connection
            connection = factory.createConnection();

            // create the session
            this.session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender : deux producteurs : géolocalisé et non géolocalisé
            this.sender = this.session.createProducer(dest);
            this.senderGeo = this.session.createProducer(destGeo);
            
            
            // start the connection, to enable message sends
            connection.start();
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context

        }
	}

	
	public void send(String message, String login, String loc)
	{
		try {
			
			ObjectMessage objectMessage = session.createObjectMessage(message);
			objectMessage.setStringProperty("login", login);
			objectMessage.setStringProperty("loc", loc);
			
			
			if (loc == "")
			{
				senderGeo.send(objectMessage);
				System.out.println("Message envoyé sur TopicMess");
			}
			else
			{
				sender.send(objectMessage);
				System.out.println("Message envoyé sur TopicMessGeo");
			}
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
}
