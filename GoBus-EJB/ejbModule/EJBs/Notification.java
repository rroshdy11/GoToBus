package EJBs;

import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Stateless
@LocalBean
@Entity
@Table(name="Notification")
public class Notification implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotNull
	@Column(name="message")
	String message;
	
	@NotNull
	@Column(name="notification_datetime")
	String notification_datetime;
	
	@ManyToOne
	@JoinColumn(name="user")
	User user;
	

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotification_datetime() {
		return notification_datetime;
	}

	public void setNotification_datetime(String notification_datetime) {
		this.notification_datetime = notification_datetime;
	}

	
}

