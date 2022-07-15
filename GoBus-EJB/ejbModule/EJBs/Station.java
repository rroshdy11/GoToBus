package EJBs;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
@Stateless
@LocalBean
@Entity
@Table(name="Station")
public class Station implements Serializable{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="name")
	String name;
	@NotNull
	@Column(name="longitude")
	double longitude;
	@NotNull
	@DecimalMax("90.0")
	@Column(name="latitude")
	double latitude;
	
	@OneToMany(mappedBy = "to_station_fk",fetch = FetchType.EAGER)
	private Set<Trip>to_trips;
	

	@OneToMany(mappedBy = "from_station_fk",fetch = FetchType.EAGER)
	private Set<Trip>from_trips;
	
	
	public Station() {
		to_trips=new HashSet<Trip>();
		from_trips=new HashSet<Trip>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	

}

