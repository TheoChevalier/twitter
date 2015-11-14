package Types;

import java.io.Serializable;

public class TypeInscription implements Serializable {

	private String login;
	private String password;
	private String nom;
	private String prenom;
	
	public TypeInscription (String l, String pwd, String n, String p) {
		login = l;
		password = pwd;
		nom = n;
		prenom = p;
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
}
