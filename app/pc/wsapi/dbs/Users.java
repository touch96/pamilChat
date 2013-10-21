package pc.wsapi.dbs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.validation.NotNull;

import play.db.ebean.Model;

@Entity
public class Users extends Model {
	
	public String oskbn;
	public String id;
	public String pw;
	public String createdt;
	
	@Id
	@NotNull
	public String token;
	
	public String getOskbn() {
		return oskbn;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getCreatedt() {
		return (new Date()).toString();
	}
	public String getToken() {
		return token;
	}

	public static Finder<Long,Users> find = new Finder<Long,Users>(
		    Long.class, Users.class
		  ); 
}
