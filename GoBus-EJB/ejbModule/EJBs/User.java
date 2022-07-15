package EJBs;


import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Entity implementation class for Entity: User
 *
 */


@Stateless
@LocalBean
@Entity
@Table(name="USER")
public class User implements Serializable{

	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 7835815458048406149L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	@NotNull
	@Column (name="username")
	String username;
	@NotNull
	@Column (name="password")
	String password;
	
	@Column (name="full_name")
	String full_name;
	
	@Column (name="role")
	String role;
	


	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="UserXTrip",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="trip_id")
			)
	List<Trip>trips;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	List<Notification>notifications;
	


	public List<Notification>UserNotifications(){
		return notifications;
	}

	public void addNotification(Notification notific) {
		notifications.add(notific);
	}
	
	

	public User() {
		super();
		trips=new ArrayList<Trip>();
		notifications=new ArrayList<Notification>();
	}
	
	public List<Trip>UserTrips(){
		return trips;
	}

	public void addtrip(Trip trip) {
		trips.add(trip);
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getusername() {
		return this.username;
	}
	
	public void setusername(String username) {
		this.username = username;
	} 
	 
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String Password) {
		this.password = Password;
	}   
	public String getFull_name() {
		return this.full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	} 
	public String getRole() {
		return this.role;
	}

	public void setRole(String Role) {
		this.role = Role;
	}
   
}

