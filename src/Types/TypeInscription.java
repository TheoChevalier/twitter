package Types;

import java.io.Serializable;

public class TypeInscription implements Serializable {

	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String ville;
	
	public TypeInscription (String l, String pwd, String n, String p, String v) {
		login = l;
		password = pwd;
		nom = n;
		prenom = p;
		ville = v;
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
	public String getVille() {
		return ville;
	}
}
