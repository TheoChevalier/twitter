import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import Types.TypeConnection;
import Types.TypeFollow;
import Types.TypeInscription;
import Types.TypeRecherche;



public class JDBC {
	private static Connection conn;
	
	
	public JDBC(String nomBD) {
		try {
		    Class.forName("org.h2.Driver");
		    
		    conn = DriverManager.getConnection("jdbc:h2:"+nomBD+";IGNORECASE=TRUE", "sa", "");
		    // on cree un objet Statement qui va permettre l'execution des requetes
	        Statement s = conn.createStatement();
	
	        // suppression des tables
	        //suppressionTables();
	        
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
        
        public List<String> rechercherUtilisateur(TypeRecherche r){
        	List<String> liste =  new ArrayList<String>() ;
        	try {
		        Statement s = conn.createStatement();
		        ResultSet rs = s.executeQuery("SELECT loginU FROM UTILISATEURS WHERE loginU LIKE '%" + r.getLogin() + "%'");
		        
		        while (rs.next()){
		        	liste.add(rs.getString("loginU"));
		        }
		        return liste;
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        	return null;
        }
        
        public List<String> listeFollow(TypeRecherche r){
        	List<String> liste =  new ArrayList<String>() ;
        	try {
		        Statement s = conn.createStatement();
		        ResultSet rs = s.executeQuery("SELECT U.loginU FROM EST_ABONNE E, UTILISATEURS USuiveur, UTILISATEURS U WHERE USuiveur.idu = E.iduSuiveur AND E.iduSuivi = U.idu AND USuiveur.loginu = '" + r.getLogin() + "'");
		        
		        while (rs.next()){
		        	liste.add(rs.getString("loginU"));
		        }
		        return liste;
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        	return null;
        }
        
        public List<String> listeFollowers(TypeRecherche r){
        	List<String> liste =  new ArrayList<String>() ;
        	try {
		        Statement s = conn.createStatement();
		        ResultSet rs = s.executeQuery("SELECT U.loginU FROM EST_ABONNE E, UTILISATEURS U, UTILISATEURS USuivi WHERE U.idu = E.iduSuiveur AND E.iduSuivi = USuivi.idu AND USuivi.loginu = '" + r.getLogin() + "'");
		        
		        while (rs.next()){
		        	liste.add(rs.getString("loginU"));
		        }
		        return liste;
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        	return null;
        }
        
        public int countMessages(TypeRecherche r){
        	int nbMessages = 0;
        	try {
		        Statement s = conn.createStatement();
		        ResultSet rs = s.executeQuery("SELECT COUNT(P.*) FROM UTILISATEURS U, POSTER P WHERE U.idu = P.idU AND U.loginu = '" + r.getLogin() + "'");
		        
		        if (rs.next()){
		        	nbMessages = rs.getInt(1);
		        }
		        return nbMessages;
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        	return nbMessages;
        }
        
        public int ajouterEst_Abonne(TypeFollow f) {
        	Statement s;
            int idSuiveur, idSuivi;
            String queryidUSuiveur = "SELECT idU FROM UTILISATEURS WHERE loginU ='" + f.getLoginSuiveur() + "'";
            String queryidUSuivi = "SELECT idU FROM UTILISATEURS WHERE loginU ='" + f.getLoginSuivi() + "'";
            
            // Vérifier si l’entrée existe déjà
            try {
            	s = conn.createStatement();
            	
            	// Récupérer l’id du suiveur
            	ResultSet rs = s.executeQuery(queryidUSuiveur);
            	rs.next();
            	idSuiveur = rs.getInt(1);
            	
            	// Récupérer l’id du suivi
            	rs = s.executeQuery(queryidUSuivi);
            	rs.next();
            	idSuivi = rs.getInt(1);

            	// Vérification d’unicité
            	String queryDupe = "SELECT * FROM Est_Abonne WHERE idUSuiveur ='" + idSuiveur + "' AND idUSuivi='" + idSuivi + "'";
            	rs = s.executeQuery(queryDupe);
            	if (rs.next()) {
            		return 1;
            	} else {
                	// Ajouter le follower
    				try {
    					s = conn.createStatement();
    					s.execute("INSERT INTO Est_Abonne VALUES ('" + idSuiveur + "','" + idSuivi + "' )") ;
    					return 0;
    					
    				} catch (SQLException e2) {
    					e2.printStackTrace();
    					return 1;
    				}
            	}
            } catch (SQLException e) {
				e.printStackTrace();
            }

        	return 2;
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
	