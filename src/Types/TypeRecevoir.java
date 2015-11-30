package Types;

import java.io.Serializable;

public class TypeRecevoir implements Serializable {
	private int idM;
	private String loginU;
	
	public TypeRecevoir(int idM, String loginU) {
		this.setIdM(idM);
		this.setLoginU(loginU);
	}

	public int getIdM() {
		return idM;
	}

	public void setIdM(int idM) {
		this.idM = idM;
	}

	public String getLoginU() {
		return loginU;
	}

	public void setLoginU(String loginU) {
		this.loginU = loginU;
	}
	
	
}
