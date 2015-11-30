package sources;
import java.util.ArrayList;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
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

public class UtilisateurSender{
    private Session session = null;
    private MessageProducer sender = null;
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    
	
	public static void main(String[] args) {
		
		// Instancier la classe actuelle, lancer des connexions et créer différents types de messages
		
		/*UtilisateurSender senderInscription = new UtilisateurSender();
		senderInscription.startJMSConnection();
		senderInscription.inscrireUtilisateur("titi", "1234", "Mr Titi", "Titi");

		*/
		// Création d’un nouvel objet sender pour ne pas partager les sessions et les files temporaires
		/*UtilisateurSender senderSeConnecter = new UtilisateurSender();
		senderSeConnecter.startJMSConnection();
		senderSeConnecter.seConnecter("toto", "1234");
		senderSeConnecter.rehercherUtilisateur("tit");
		senderSeConnecter.listeFollow("toto");
		senderSeConnecter.nombreMessage("toto");
		senderSeConnecter.listeFollowers("toto");
		
		// Création d’un nouvel objet sender pour ne pas partager les sessions et les files temporaires
		UtilisateurSender senderFollow = new UtilisateurSender();
		senderFollow.startJMSConnection();
		senderFollow.follow("toto", "titi");
*/
		System.out.println("Décommenter les lignes dans le main de UtilisateurSender pour créer une première fois l’utilisateur toto.");
	}
	public UtilisateurSender() {
		super();
		this.startJMSConnection();
	}
	
	public void startJMSConnection() {
        this.context = null;
        this.factory = null;
        this.connection = null;
        String factoryName = "ConnectionFactory";
        String destName = "QueueReq";
        Destination dest = null;
        
        try {
            // create the JNDI initial context.
            this.context = new InitialContext();

            // look up the ConnectionFactory
            this.factory = (ConnectionFactory) this.context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            this.connection = this.factory.createConnection();

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
        }
	}

	public boolean seConnecter(String login, String password) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeConnection c = new TypeConnection(login, password);
            ObjectMessage objectMessage = session.createObjectMessage(c);
            objectMessage.setJMSType("Connection");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof TextMessage) {
                TextMessage text = (TextMessage) rep;
                String resp = text.getText();
                System.out.println("Received: " + resp);
                if (resp.equals("Connected.")) {
                	return true;
                } else {
                	return false;
                }
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return false;
	}
	
	public void seDeconnecter() {
		// ferme le contexte
		if (this.context != null) {
		try {
			this.context.close();
			} catch (NamingException exception) {
			exception.printStackTrace();
			}
		}
		// ferme la connexion
		if (connection != null) {
			try {
				connection.close();
				System.out.println("You are now logged out.");
			} catch (JMSException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public boolean inscrireUtilisateur(String login, String password, String nom, String prenom, String ville) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeInscription i = new TypeInscription(login, password, nom, prenom, ville);
            ObjectMessage objectMessage = session.createObjectMessage(i);
            objectMessage.setJMSType("Inscription");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof TextMessage) {
            	TextMessage text = (TextMessage) rep;
                String resp = text.getText();
                System.out.println("Received: " + resp);
                if (resp.equals("Signed up.")) {
                	return true;
                } else {
                	return false;
                }
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return false;
	}
	
	public int updateUtilisateur(String ancienLogin, String login, String password, String nom, String prenom, String ville) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeModificationProfil mp = new TypeModificationProfil(ancienLogin, login, password, nom, prenom, ville);
            ObjectMessage objectMessage = session.createObjectMessage(mp);
            objectMessage.setJMSType("Modification");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
         // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage stream = (StreamMessage) rep;
            	return stream.readInt();
            }
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return 2;
	}
	
	public int ajouterMessage(TypeMessage m) {
		Session session = this.session;
        try {
            ObjectMessage objectMessage = session.createObjectMessage(m);
            objectMessage.setJMSType("AjouterMessage");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
         // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage stream = (StreamMessage) rep;
            	return stream.readInt();
            }
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return 2;
	}
	
	public int follow (String login1, String login2) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeFollow f = new TypeFollow(login1, login2);
            ObjectMessage objectMessage = session.createObjectMessage(f);
            objectMessage.setJMSType("Follow");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage stream = (StreamMessage) rep;
            	return stream.readInt();
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return 2;
	}
	
	public TypeInscription getUtilisateur (String login) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("GetUtilisateur");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof ObjectMessage) {
            	ObjectMessage om = (ObjectMessage) rep;
            	// récupération de l'objet transporté
            	TypeInscription o = (TypeInscription) om.getObject();
            	return o;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;
	}
	
	public int unfollow (String login1, String login2) {
		Session session = this.session;
        try {
        	// Création et envoi
        	TypeFollow f = new TypeFollow(login1, login2);
            ObjectMessage objectMessage = session.createObjectMessage(f);
            objectMessage.setJMSType("Unfollow");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage stream = (StreamMessage) rep;
            	return stream.readInt();
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return 2;
	}
	
	public List<TypeMessage> getMessageFollow(String login) {
		Session session = this.session;
		int i=0;
		ArrayList<TypeMessage> liste;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("getMessageFollow");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof ObjectMessage) {
            	ObjectMessage text = (ObjectMessage) rep;
            	liste = (ArrayList<TypeMessage>) text.getObject();
            	return liste;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;		
	}
	
	public List<TypeMessage> getMessages(String login) {
		Session session = this.session;
		int i=0;
		ArrayList<TypeMessage> liste;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("getMessages");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof ObjectMessage) {
            	ObjectMessage text = (ObjectMessage) rep;
            	liste = (ArrayList<TypeMessage>) text.getObject();
            	return liste;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;		
	}
	
	public List<String> rechercherUtilisateur(String login, String loginAppelant) {
		Session session = this.session;
		int i=0;
		List<String> liste =  new ArrayList<String>() ;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("Recherche");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage text = (StreamMessage) rep;
            	if (text.getIntProperty("Size") == 0) {
        			System.out.println("No result.");        			
        		} else {
        			while (i < text.getIntProperty("Size")) {
                		liste.add(text.readString());
                		i++;
                	}
        			System.out.println("User list: " + liste.toString());
        		}
            	System.out.println("Appelant: " + loginAppelant);
                for (int j = 0;  j < liste.size(); j++) {
                    String tempName = liste.get(j);
                    if (tempName.equals(loginAppelant)) {
                    	liste.remove(j);
                    }
                }

            	return liste;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;
	}
	
	public List<String> listeFollow(String login) {
		Session session = this.session;
		int i=0;
		List<String> liste =  new ArrayList<String>() ;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("ListeFollow");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage text = (StreamMessage) rep;
            	if (text.getIntProperty("Size") == 0) {
        			System.out.println("You don't follow anyone.");        			
        		} else {
        			while (i < text.getIntProperty("Size")) {
                		liste.add(text.readString());
                		i++;
                	}
        			System.out.println("All users you follow: " + liste.toString());
        		}
            	return liste;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;
	}
	
	public List<String> listeFollowers(String login) {
		Session session = this.session;
		int i=0;
		List<String> liste =  new ArrayList<String>() ;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("listeFollowers");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage text = (StreamMessage) rep;
            	if (text.getIntProperty("Size") == 0) {
        			System.out.println("No one is following you.");        			
        		} else {
        			while (i < text.getIntProperty("Size")) {
                		liste.add(text.readString());
                		i++;
                	}
        			System.out.println("All your followers: " + liste.toString());
        		}
            	return liste;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return null;
	}
	
	public int nombreMessage(String login) {
		Session session = this.session;
		int nbMessages;
        try {
        	// Création et envoi
        	TypeRecherche r = new TypeRecherche(login);
            ObjectMessage objectMessage = session.createObjectMessage(r);
            objectMessage.setJMSType("NombreMessages");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
            // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage text = (StreamMessage) rep;
            	nbMessages = text.readInt();
            	if (nbMessages != 0){
            		System.out.println("Number of all your tweets: " + nbMessages);
            	} else {
            		System.out.println("You don't have any tweet.");
            	}
            	return nbMessages;
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return -1;
	}
	public int ajouterRecevoir(TypeRecevoir tr) {
		// TODO Auto-generated method stub
		Session session = this.session;
        try {
            ObjectMessage objectMessage = session.createObjectMessage(tr);
            objectMessage.setJMSType("AjouterRecevoir");
            Destination temp = session.createTemporaryQueue();
            MessageConsumer tempConsumer = session.createConsumer(temp);
            objectMessage.setJMSReplyTo(temp);
            this.sender.send(objectMessage);
            
         // Réponse sur file temporaire
            Message rep = tempConsumer.receive();

            if (rep instanceof StreamMessage) {
            	StreamMessage stream = (StreamMessage) rep;
            	return stream.readInt();
            }
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
        return 2;
		
	}

}
