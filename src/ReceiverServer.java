import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Types.TypeConnection;
import Types.TypeInscription;

public class ReceiverServer implements MessageListener {

	
    private MessageConsumer mc = null;
    private Session session = null;
    private Queue queueDest = null;
    private String queueName = "QueueReq";
    private Connection connection = null;
    private Context context = null;
    private JDBC base = null;

    public ReceiverServer(JDBC base, Context context, Connection connection) {
    	this.base = base;
        this.connection = connection;
        this.context = context;
        this.init();
    }
    
    
    public void init() {
        try {
            queueDest = (Queue) context.lookup(queueName);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            mc = session.createConsumer(queueDest);
            MessageListener listener = this;
            mc.setMessageListener(listener);

            connection.start();
            System.out.println("Waiting for incoming messages…");

        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            //initialisation de la connection
            ConnectionFactory factory = null;
            Connection connection = null;
            Context context = null;
            //configuration
            String factoryName = "ConnectionFactory";
            // create the JNDI initial context
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            // create the connection
            connection = factory.createConnection();

            JDBC bd = new JDBC ("Twitter");
            new ReceiverServer(bd, context, connection);
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        }
    }
    
    public void onMessage(Message message) {
        try {
            if (message != null) {
            	String reply = "";
				ObjectMessage om = (ObjectMessage) message;

				switch(om.getJMSType()) {
					case "Connection":
				    	TypeConnection c = (TypeConnection) om.getObject();
				    	reply = base.checkConnection(c) ? "Connected." : "Not connected.";
				        break;
					case "Inscription":
						TypeInscription i = (TypeInscription) om.getObject();
				        reply = base.inscrireUtilisateur(i) ? "Signed up." : "Not signed up.";
				        break;
				    default:
				    	reply = "Oh noes! Server caught an unknown message.";
				    	break;
				}

				// Envoyer réponse sur file temporaire
			    MessageProducer replyProducer;
				replyProducer = session.createProducer(message.getJMSReplyTo());
			    TextMessage replyMessage = session.createTextMessage();
			    replyMessage.setText(reply);
			    replyMessage.setJMSCorrelationID(message.getJMSMessageID());
			    replyProducer.send(replyMessage);
			    System.out.println("Envoi dans la file de : " + replyMessage);
            }
  
        } catch (JMSException ex) {
            Logger.getLogger(ReceiverServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
