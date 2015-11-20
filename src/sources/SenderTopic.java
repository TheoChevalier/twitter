package sources;

import javax.jms.*;
import javax.naming.*;


public class SenderTopic {

	Session session = null;
    MessageProducer sender = null;
	
	

	public void startJMSConnection() {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        
        String destName = "TopicMess";
        Destination dest = null;
        
        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            this.session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            this.sender = this.session.createProducer(dest);

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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
