package Types;

import java.io.Serializable;

public class TypeModificationProfil implements Serializable {
	
	private String ancienLogin;
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String ville;
	
	public TypeModificationProfil (String aLogin, String l, String pwd, String n, String p, String v) {
		setAncienLogin(aLogin);
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

	public String getAncienLogin() {
		return ancienLogin;
	}
	
	public String getVille() {
		return ville;
	}

	public void setAncienLogin(String ancienLogin) {
		this.ancienLogin = ancienLogin;
	}
}
