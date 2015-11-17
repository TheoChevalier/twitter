import java.util.List;
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
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Types.TypeConnection;
import Types.TypeFollow;
import Types.TypeInscription;
import Types.TypeRecherche;

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
    	TypeRecherche r;
        try {
            if (message != null) {
            	String reply = "";
				ObjectMessage om = (ObjectMessage) message;

				switch(om.getJMSType()) {
					case "Connection":
				    	TypeConnection c = (TypeConnection) om.getObject();
				    	reply = base.checkConnection(c) ? "Connected." : "Not connected.";
				    	reply(message, reply);
				        break;
					case "Inscription":
						TypeInscription i = (TypeInscription) om.getObject();
				        reply = base.inscrireUtilisateur(i) ? "Signed up." : "Not signed up.";
				        reply(message, reply);
				        break;
					case "Follow":
						TypeFollow f = (TypeFollow) om.getObject();
				        reply(message, base.ajouterEst_Abonne(f));
				        break;
					case "Recherche":
						r = (TypeRecherche) om.getObject();
				        reply(message, base.rechercherUtilisateur(r));
				        break;
					case "ListeFollow":
						r = (TypeRecherche) om.getObject();
				        reply(message, base.listeFollow(r));
				        break;
					case "NombreMessages":
						r = (TypeRecherche) om.getObject();
				        reply(message, base.countMessages(r));
				        break;
					case "listeFollowers":
						r = (TypeRecherche) om.getObject();
				        reply(message, base.listeFollowers(r));
				        break;    
				    default:
				    	reply = "Oh noes! Server caught an unknown message.";
				    	break;
				}
            }
  
        } catch (JMSException ex) {
            Logger.getLogger(ReceiverServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Répondre un String sur la file temporaire
    private void reply (Message message, String s) {
	    try {
			// Envoyer réponse sur file temporaire
		    MessageProducer replyProducer;
			replyProducer = session.createProducer(message.getJMSReplyTo());
		    TextMessage replyMessage = session.createTextMessage();
		    replyMessage.setText(s);
		    replyMessage.setJMSCorrelationID(message.getJMSMessageID());
		    replyProducer.send(replyMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Répondre un int sur la file temporaire
    private void reply (Message message, int i) {
	    try {
			// Envoyer réponse sur file temporaire
		    MessageProducer replyProducer;
			replyProducer = session.createProducer(message.getJMSReplyTo());
		    StreamMessage replyMessage = session.createStreamMessage();
		    replyMessage.writeInt(i);
		    replyMessage.setJMSCorrelationID(message.getJMSMessageID());
		    replyProducer.send(replyMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 // Répondre une liste sur la file temporaire
    private void reply (Message message, List<String> liste) {
	    try {
			// Envoyer réponse sur file temporaire
		    MessageProducer replyProducer;
			replyProducer = session.createProducer(message.getJMSReplyTo());
		    StreamMessage replyMessage = session.createStreamMessage();
		    if(! liste.isEmpty()) {
		    	for (String login : liste) {
			    	replyMessage.writeString(login);
				}
		    	replyMessage.setIntProperty("Size", liste.size());
		    } else {
		    	replyMessage.setIntProperty("Size", 0);
		    }
		    replyMessage.setJMSCorrelationID(message.getJMSMessageID());
		    replyProducer.send(replyMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
