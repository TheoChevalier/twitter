package sources;

import javax.jms.*;
import javax.naming.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverTopic implements MessageListener {

	
    private MessageConsumer mc = null;
    private Session session = null;
    private Topic topicDest = null;
    private String topicName = null;
    private Connection connection = null;
    private Context context = null;
    private JDBC base = null;
    
    
    public ReceiverTopic( Context context, Connection connection) {
        super();

        this.connection = connection;
        this.context = context;
        init();
    }
	

    public void init() {
        try {
        	topicDest = (Topic) context.lookup(topicName);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            mc = session.createConsumer(topicDest);
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


	public void onMessage(Message m) {

		
		ObjectMessage om = (ObjectMessage) m;
		try {
			
			Destination dest = om.getJMSDestination();
			String login = om.getStringProperty("login");
			
			TextMessage text = (TextMessage) m;
            String message = text.getText();
            
            
			if (dest.toString() == "TopicMess")
			{
				base.ajouterMessage(message,"");
				//base.ajouterMessage(m.toString(),"");
			}
			else if (dest.toString() == "TopicMessGeo")
			{
				base.ajouterMessage(message,om.getStringProperty("loc"));
				//base.ajouterMessage(m.toString(),"");
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
    
	
}
