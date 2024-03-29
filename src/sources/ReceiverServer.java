package sources;
import java.io.Serializable;
import java.util.ArrayList;
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
import Types.TypeMessage;
import Types.TypeModificationProfil;
import Types.TypeRecevoir;
import Types.TypeRecherche;
import design.MainView;

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
        init();
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
    	//initialisation de la connection
        ConnectionFactory factory = null;
        Connection connection = null;
        Context context = null;
        
        try {
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
    	TypeFollow f;
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
						f = (TypeFollow) om.getObject();
				        reply(message, base.ajouterEst_Abonne(f));
				        break;
					case "Unfollow":
						f = (TypeFollow) om.getObject();
				        reply(message, base.seDesabonner(f));
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
					case "GetUtilisateur":
						r = (TypeRecherche) om.getObject();
				        reply(message, base.getUtilisateur(r));
				        break;
					case "Modification":
						TypeModificationProfil tmp = (TypeModificationProfil) om.getObject();
				        reply(message, base.majUtilisateur(tmp));
				        break;
					case "AjouterMessage":
						TypeMessage tm = (TypeMessage) om.getObject();
				        reply(message, base.ajouterMessage(tm));
				        break;
					case "AjouterRecevoir":
						TypeRecevoir tr = (TypeRecevoir) om.getObject();
				        reply(message, base.ajouterRecevoir(tr));
				        break;
					case "getMessageFollow":
						r = (TypeRecherche) om.getObject();
						replyMessage(message, base.getMessageFollow(r));
				        break;
					case "getMessages":
						r = (TypeRecherche) om.getObject();
						replyMessage(message, base.getMessagePosted(r));
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
    
    // Répondre un Object sur la file temporaire
    private void reply (Message message, TypeInscription user) {
	    try {
			// Envoyer réponse sur file temporaire
		    MessageProducer replyProducer;
			replyProducer = session.createProducer(message.getJMSReplyTo());
		    ObjectMessage replyMessage = session.createObjectMessage();
		    replyMessage.setObject(user);
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
    
    private void replyMessage(Message message, ArrayList<TypeMessage> messageFollow) {
		// TODO Auto-generated method stub
    	try {
			// Envoyer réponse sur file temporaire
		    MessageProducer replyProducer;
			replyProducer = session.createProducer(message.getJMSReplyTo());
		    ObjectMessage replyMessage = session.createObjectMessage();
		    if(! messageFollow.isEmpty()) {
		    	replyMessage.setObject(messageFollow);
		    	replyMessage.setIntProperty("Size", messageFollow.size());
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
