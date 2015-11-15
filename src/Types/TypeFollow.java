package Types;

import java.io.Serializable;

public class TypeFollow implements Serializable  {
	private String loginSuiveur;
	private String loginSuivi;
	
	public TypeFollow (String l1, String l2) {
		loginSuiveur = l1;
		loginSuivi = l2;
	}
	
	public String getLoginSuiveur() {
		return loginSuiveur;
	}
	public String getLoginSuivi() {
		return loginSuivi;
	}
}
