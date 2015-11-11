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
	//Test
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
	
	
	public void createTableUtilisateur(Statement s) throws SQLException {
		 // On regarde si la table existe deja
        String query = "SELECT id FROM Utilisateur LIMIT 1";
        try {
        	s.executeQuery(query);
        } catch(Exception e) {
        	// sinon on la cree
        	s.execute("CREATE TABLE Velo  ( " +
        			" id INT NOT NULL PRIMARY KEY, " +
        			" login VARCHAR( 256 ), "+
        			" mdp VARCHAR( 256 ), "+
        			" nom VARCHAR( 256 ), "+
        			" prenom VARCHAR( 256 ), "
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
	
