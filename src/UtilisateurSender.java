import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Types.TypeConnection;

public class UtilisateurSender {

	
	public static void main(String[] args) {
		SeConnecter();
	}
	public static String SeConnecter() {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = "QueueReq";
        Destination dest = null;
        Session session = null;
        MessageProducer sender = null;

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
            session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            sender = session.createProducer(dest);

            // start the connection, to enable message sends
            connection.start();
           
        	TypeConnection c = new TypeConnection("toto", "1234");
            ObjectMessage objectMessage = session.createObjectMessage(c);
            objectMessage.setJMSType("Connection");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            sender.send(objectMessage);
            Message rep = tempConsumer.receive();

            if (rep instanceof TextMessage) {
                TextMessage text = (TextMessage) rep;
                System.out.println("Received: " + text.getText());
                return text.getText();
            }

        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return "Not connected";
	}
}
