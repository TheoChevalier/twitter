import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;



public class JDBC {
	private Connection conn;
	
	
	public JDBC(String nomBD) {
		try {
		    Class.forName("org.h2.Driver");
		    
		    conn = DriverManager.getConnection("jdbc:h2:"+nomBD+";IGNORECASE=TRUE", "sa", "");
		    // on cree un objet Statement qui va permettre l'execution des requetes
	        Statement s = conn.createStatement();
	
	        // suppression des tables
	        suppressionTables();
	        
	        // création des tables	        
	        //creationTableVelo(s);
	        
	        // Insertions et tests


	        
	        
		} catch(Exception e) {
			// il y a eu une erreur
			e.printStackTrace();
		}
	}
	
	
	

    public void suppressionTables() {
    	Statement s;
		try {
			s = conn.createStatement();
			s.execute("drop table UTILISATEURS; drop table MESSAGES; drop table EST_ABONNE; drop table POSTER; drop table RECEVOIR;");
			//s.execute("drop table station; drop table utilisateur; drop table velo;") ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    
	/**
	 * Creation des tables après verification de leur non existence
	 * @param s
	 * @throws SQLException
	 */
	
	
	public void createTables(Statement s) throws SQLException {
		 // On regarde si la table existe deja
        String query = "SELECT id FROM UTILISATEURS LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("CREATE TABLE UTILISATEURS  ( " +
        			" id INT NOT NULL PRIMARY KEY, " +
        			" login VARCHAR( 256 ), "+
        			" mdp VARCHAR( 256 ), "+
        			" nom VARCHAR( 256 ), "+
        			" prenom VARCHAR( 256 ), "
        			);
        }
        // On regarde si la table existe deja
        String query = "SELECT idM FROM MESSAGES LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("DROP TABLE MESSAGES");
        	s.execute("create table MESSAGES  ( " +
        			" idM int auto_increment NOT NULL PRIMARY KEY, " +
        			" contenuM VARCHAR( 256 ) , " +
        			" dateM DATE," +
        			" heureM TIME," +
        			" locM VARCHAR (50))"
        			);
        }
        // On regarde si la table existe deja
        String query = "SELECT idUSuiveur FROM EST_ABONNE LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("DROP TABLE EST_ABONNE");
        	s.execute("create table EST_ABONNE  ( " +
        			" idUSuiveur int FOREIGN KEY REFERENCES UTILISATEUR(idU), " +
        			" idUSuivi int FOREIGN KEY REFERENCES UTILISATEUR(idU))" + 
        			"CONSTRAINT pk_est_abonne PRIMARY KEY (idUSuiveur, idUSuivi)"
        			);
        }
        // On regarde si la table existe deja
        String query = "SELECT idM FROM POSTER LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("DROP TABLE POSTER");
        	s.execute("create table POSTER  ( " +
        			" idM int FOREIGN KEY REFERENCES MESSAGES(idM), " +
        			" idU int FOREIGN KEY REFERENCES UTILISATEUR(idU))" + 
        			"CONSTRAINT pk_poster PRIMARY KEY (idM, idU)"
        			);
        }
        // On regarde si la table existe deja
        String query = "SELECT idM FROM RECEVOIR LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("DROP TABLE RECEVOIR");
        	s.execute("create table RECEVOIR  ( " +
        			" idM int FOREIGN KEY REFERENCES MESSAGES(idM), " +
        			" idU int FOREIGN KEY REFERENCES UTILISATEUR(idU))" + 
        			"CONSTRAINT pk_poster PRIMARY KEY (idM, idU)"
        			);
        }
	}
	
        
        /**
         * 
         * Insertion des donnees dans la bd
         * @param parametres de chaque table
         * @throws SQLException
         */
        
        public void ajouterVelo(int num, String etat, int station) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("insert into VELO values (" + num + ", " + " '" + etat + "'," + station + " )") ;
				//incrementerNbVelos(station);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
	
	
        public static void main(String[] args) {
        	
        	JDBC bd = new JDBC("BD");

        	
        	

    	}

	
	
	
}
	
