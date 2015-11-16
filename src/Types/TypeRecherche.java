package Types;

import java.io.Serializable;

public class TypeRecherche implements Serializable {
	private String login;
	
	public TypeRecherche(String l) {
		this.setLogin(l);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
