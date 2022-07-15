package EJBs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Stateless
@LocalBean
@Entity
@Table(name="Trip")
public class Trip implements Serializable{
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3284220924369083342L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="from_station_fk")
	Station from_station_fk;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="to_station_fk")
	Station to_station_fk;
	
	@NotNull
	@Column(name="departure_time")
	
	String departure_time;
	
	@NotNull
	@Column(name="arrival_time")
	String arrival_time;
	
	@NotNull
	@Min(0)
	@Column(name="available_seats")
	int available_seats;
	
	
	String from_station;
	
	String to_station;
	


	@Transient
	String from_date;
	@Transient
	String to_date;


	
	@ManyToMany(mappedBy = "trips",fetch = FetchType.EAGER)
	List<User>users;
	
	public 	Trip() {
		users=new ArrayList<User>();
	}
	
	
	public boolean adduser(User user) {
		if(available_seats>0) {
			users.add(user);
			available_seats--;
			return true;
		}
		else {
			return false;
		}
	}





	/*
	 *
	 */




	public void setFrom_station_fk(Station from_station_fk) {
		this.from_station_fk = from_station_fk;
	}



	public void setTo_station_fk(Station to_station_fk) {
		this.to_station_fk = to_station_fk;
	}



	public String getFrom_date() {
		return from_date;
	}



	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}



	public String getTo_date() {
		return to_date;
	}



	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}




	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	public String getFrom_station() {
		return from_station;
	}



	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}



	public String getTo_station() {
		return to_station;
	}



	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}



	public String getDeparture_time() {
		return departure_time;
	}



	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}



	public String getArrival_time() {
		return arrival_time;
	}



	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}



	public int getAvailable_seats() {
		return available_seats;
	}



	public void setAvailable_seats(int available_seats) {
		this.available_seats = available_seats;
	}
	

}

