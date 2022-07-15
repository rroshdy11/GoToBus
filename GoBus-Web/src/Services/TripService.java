package Services;


import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EJBs.Station;
import EJBs.Trip;
import EJBs.User;


@RequestScoped
@Path("/trip")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TripService {
	@PersistenceContext(unitName="GoBusWEB")
	 private EntityManager entityManager;
	
	@Resource
	private UserTransaction ut;

	
	@POST
	@Path("/{user_id}")
	public String createTrip(@PathParam("user_id")int user_id,final Trip trip) throws IllegalStateException, SecurityException, SystemException {
		try {
			   ut.begin();
			   User user=entityManager.find(User.class, user_id);
			   if(user.getRole().equals("Admin")) {//to check on the role of user 
				   //search for the station if exists 
				   Station station=entityManager.find(Station.class,trip.getTo_station());
				   trip.setTo_station_fk(station);
				
				   Station station2=entityManager.find(Station.class,trip.getFrom_station());
				   trip.setFrom_station_fk(station2);
				   //persist the trip
				   entityManager.persist(trip);
				   ut.commit();
				   return "Trip Created Successfully";
				  }
			   else {
				ut.commit();
				return "You Are Not Authorized To Use This Service";
			}
			    
		}catch (Exception e) {
			 ut.rollback();
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				      .type(MediaType.TEXT_PLAIN)
				      .entity(e.getMessage())
				      .build());
		}
	}

	
}

