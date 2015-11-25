package Types;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class TypeMessage implements Serializable{

	private String contenu;
	private String timestamp;
	private String loc;
	private String loginSender;
	
	public TypeMessage (String contenu, String loc, String login){
		this.contenu = contenu;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyHH:mm");
		this.timestamp = sdf.format(date);
		this.loc = loc;
		this.loginSender  = login;
	}
	
	public TypeMessage (String contenu, String timestamp, String loc, String login){
		this.contenu = contenu;
		this.timestamp = timestamp;
		this.loc = loc;
		this.loginSender  = login;
	}

	@Override
	public String toString() {
		return "TypeMessage [contenu=" + contenu + ", timestamp=" + timestamp + ", loc=" + loc + ", loginSender="
				+ loginSender + "]";
	}
	
	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLoginSender() {
		return loginSender;
	}

	public void setLoginSender(String loginSender) {
		this.loginSender = loginSender;
	}
}
