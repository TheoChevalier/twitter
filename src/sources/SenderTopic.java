package sources;

import javax.jms.*;
import javax.naming.*;


public class SenderTopic {

	Session session = null;
    MessageProducer sender = null;
    MessageProducer senderGeo = null;
	
    
    public SenderTopic()
    {
    	super();
    	startJMSConnection();
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
	
	

	public static void main(String[] args) {
		
		// Instancier la classe actuelle, lancer des connexions et créer différents types de messages
		
		System.out.println("Début du test");
		
		/*
		UtilisateurSender senderInscription = new UtilisateurSender();
		senderInscription.startJMSConnection();
		senderInscription.inscrireUtilisateur("titi", "1234", "Mr Titi", "Titi");
		
		
		System.out.println("Connection");	
		
		// Création d’un nouvel objet sender pour ne pas partager les sessions et les files temporaires
		UtilisateurSender senderSeConnecter = new UtilisateurSender();
		senderSeConnecter.startJMSConnection();
		senderSeConnecter.seConnecter("toto", "1234");
		*/

		System.out.println("Préparation de l'envoi");	
		
		SenderTopic topic = new SenderTopic();

		
		topic.send("Coucou c'est toto", "toto", "");
		
		
		
		
		System.out.println("Décommenter les lignes dans le main de UtilisateurSender pour créer une première fois l’utilisateur toto.");
	}
	
	
	
}
