package Types;

import java.io.Serializable;

public class TypeConnection implements Serializable {

	private String login;
	private String password;
	
	public TypeConnection (String l, String pwd) {
		login = l;
		password = pwd;
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
}
