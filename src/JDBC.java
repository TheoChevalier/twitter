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

import Types.TypeConnection;
import Types.TypeInscription;



public class JDBC {
	private static Connection conn;
	
	
	public JDBC(String nomBD) {
		try {
		    Class.forName("org.h2.Driver");
		    
		    conn = DriverManager.getConnection("jdbc:h2:"+nomBD+";IGNORECASE=TRUE", "sa", "");
		    // on cree un objet Statement qui va permettre l'execution des requetes
	        Statement s = conn.createStatement();
	
	        // suppression des tables
	        suppressionTables();
	        
	        // création des tables	        
	        createTables(s);
	        
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
	 * Création des tables après vérification de leur non existence
	 * @param s
	 * @throws SQLException
	 */
	
	
	public void createTables(Statement s) throws SQLException {
		 // On regarde si la table existe deja
        String query = "SELECT idU FROM UTILISATEURS LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("CREATE TABLE UTILISATEURS  ( " +
        			" idU int auto_increment NOT NULL PRIMARY KEY, " +
        			" loginU VARCHAR( 256 ), "+
        			" mdpU VARCHAR( 256 ), "+
        			" nomU VARCHAR( 256 ), "+
        			" prenomU VARCHAR( 256 )) "
        			);
        }
        // On regarde si la table existe deja
        query = "SELECT idM FROM MESSAGES LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("create table MESSAGES  ( " +
        			" idM int auto_increment NOT NULL PRIMARY KEY, " +
        			" contenuM VARCHAR( 256 ) , " +
        			" dateM DATE," +
        			" heureM TIME," +
        			" locM VARCHAR (50))"
        			);
        }
        // On regarde si la table existe deja
        query = "SELECT idUSuiveur FROM EST_ABONNE LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("create table EST_ABONNE  ( " +
        			" idUSuiveur int, " +
        			" idUSuivi int," +
        			" FOREIGN KEY (idUSuiveur) REFERENCES UTILISATEURS(idU)," + 
        			" FOREIGN KEY (idUSuivi) REFERENCES UTILISATEURS(idU)," + 
        			"PRIMARY KEY (idUSuiveur, idUSuivi))"
        			);
        }
        // On regarde si la table existe deja
        query = "SELECT idM FROM POSTER LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("create table POSTER  ( " +
        			" idM int, " +
        			" idU int, " + 
        			" FOREIGN KEY (idM) REFERENCES MESSAGES(idM)," +
        			" FOREIGN KEY (idU) REFERENCES UTILISATEURS(idU)," +
        			"PRIMARY KEY (idM, idU))"
        			);
        }
        // On regarde si la table existe deja
        query = "SELECT idM FROM RECEVOIR LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("create table RECEVOIR  ( " +
        			" idM int, " +
        			" idU int, " + 
        			"FOREIGN KEY (idM) REFERENCES MESSAGES(idM), " +
        			"FOREIGN KEY (idU) REFERENCES UTILISATEURS(idU)," + 
        			"PRIMARY KEY (idM, idU))"
        			);
        }
	}
	
        
        /**
         * 
         * Insertion des donnees dans la bd
         * @param parametres de chaque table
         * @throws SQLException
         */
        
        public boolean ajouterUtilisateur(String loginU, String mdpU, String nomU, String prenomU) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("INSERT INTO Utilisateurs (loginU, mdpU, nomU, prenomU) VALUES ('" +loginU + "'," + "'" + mdpU + "'," + "'" + nomU + "'," + "'" + prenomU + "'" + " )");
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }
	
        public boolean ajouterMessage(String contenuM, String dateM, String heureM, String locM) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("INSERT INTO Messages (contenuM, dateM, heureM, locM) VALUES ('" +contenuM + "'," + "'" + dateM + "'," + "'" + heureM + "'," + "'" + locM + "'" + " )") ;
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }
        
        public boolean ajouterEst_Abonne(int idUSuiveur, int idUSuivi) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("INSERT INTO Est_Abonne(" + idUSuiveur + "," + idUSuivi + " )") ;
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }
        
        public boolean ajouterPoster(int idM, int idU) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("INSERT INTO Poster(" + idM + "," + idU + " )") ;
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }      
        
        public boolean ajouterRecevoir(int idM, int idU) {
        	Statement s;
			try {
				s = conn.createStatement();
				s.execute("INSERT INTO Recevoir(" + idM + "," + idU + " )") ;
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }
        
        
        public boolean checkConnection(TypeConnection c) {
        	try {
		        Statement s = conn.createStatement();
		        ResultSet rs = s.executeQuery("SELECT idU, loginU, mdpU FROM UTILISATEURS WHERE loginU = '" + c.getLogin() + "' AND mdpU = '" + c.getPassword() + "'");
		        
		        return rs.next();
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        	return false;
        }
        
        public boolean inscrireUtilisateur(TypeInscription i) {
        	return ajouterUtilisateur(i.getLogin(), i.getPassword(), i.getNom(), i.getPrenom());
        }
        
        public static void main(String[] args) {
        	
        	JDBC bd = new JDBC("Twitter");

        	System.out.println("Création de la base réussie");
    	}

	
	
	
}
	
