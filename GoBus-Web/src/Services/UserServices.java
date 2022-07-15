package Services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EJBs.Notification;
import EJBs.Trip;
import EJBs.User;
import EJBs.UserXTrip;

@RequestScoped
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserServices {
	
	@PersistenceContext(unitName="GoBusWEB")
	 private EntityManager entityManager;

	@Resource
	private UserTransaction ut;
	
	@POST
	@Path("/register")
	public String  register(User client) throws IllegalStateException, SecurityException, SystemException {
		try {
		  ut.begin();
		  entityManager.persist(client);
		  ut.commit();
		  return "Client Added Successfuly--> You User Name: "+client.getusername()+" -- Your ID :"+client.getId() ;
		}
		catch (Exception e) {
			// TODO: handle exception
			ut.rollback();
			return e.getMessage();
		}
	}
	
	@POST
	@Path("/login")
	public String  login(User client) throws IllegalStateException, SecurityException, SystemException {
  
		try {
			ut.begin();
		  String select = "SELECT u FROM User u WHERE u.username=:userName and u.password=:passWord";
		  Query query = entityManager.createQuery(select);
		  query.setParameter("userName", client.getusername());
		  query.setParameter("passWord", client.getPassword());
		  query.getSingleResult();
		  ut.commit();
		  return  "SuccessFully Logged In ";
		}
		catch (Exception e) {
			// rollback the transaction
			ut.rollback();
			//this throw to return 400 bad request as required if credential are not exist 
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
				      .type(MediaType.TEXT_PLAIN)
				      .entity("Sorry Username or Password are not right")
				      .build());
		}
	}

	@POST
	@Path("/searchtrips")
	public List<Trip> searchTrips(Trip trip) throws IllegalStateException, SecurityException, SystemException
	{
		
		try {
			ut.begin();

			TypedQuery<Trip>query = entityManager.createQuery("SELECT t FROM Trip t where t.departure_time >=?1 and t.arrival_time <=?2 and t.from_station LIKE ?3 and t.to_station LIKE ?4",Trip.class);
			query.setParameter(1, trip.getFrom_date());
			query.setParameter(2, trip.getTo_date());
			query.setParameter(3,trip.getFrom_station());
			query.setParameter(4,  trip.getTo_station());
			List<Trip> trips=query.getResultList();
			ut.commit();
			return trips;
		} catch (Exception e) {
			ut.rollback();
			// to return 500 Internal Server Error Response as required
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				      .type(MediaType.TEXT_PLAIN)
				      .entity(e.getMessage())
				      .build());
		}
	}
	
	
	
	@POST
	@Path("/booktrip")
	public String bookTrip(UserXTrip usertrip) throws IllegalStateException, SecurityException, SystemException {
		try {
			Calendar calendar = Calendar.getInstance();//to get the current time and date
			
			ut.begin();
			User user= entityManager.find(User.class, usertrip.getUser_id());//searching for user by id
			Trip trip=entityManager.find(Trip.class, usertrip.getTrip_id());//searching fot trip by id
			
			Notification notific=new Notification();//notification to be added to the user
			notific.setUser(user);//seting the user for notification
			if(trip.adduser(user)) {//if the number of seats is more than zero will add user and return true 
				
				user.addtrip(trip);
				
				notific.setMessage("You have booked trip from "+trip.getFrom_station()+" to "+trip.getTo_station()+" successfully" );
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				notific.setNotification_datetime(formatter.format(calendar.getTime()));
				entityManager.persist(notific);//persisting the notitification in db
				
				entityManager.merge(trip);//updating the trip in db
				entityManager.merge(user);//updating the user in db
				
				ut.commit();
			}
			else {//if there is no seat available 
				notific.setMessage("Sorry, Trip  "+trip.getFrom_station()+" to "+trip.getTo_station()+" have no available seats" );
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				notific.setNotification_datetime(formatter.format(calendar.getTime()));
				entityManager.persist(notific);
				
				entityManager.merge(user);
				ut.commit();
			}
			return "trip booking In Progress check your Notififcations";
		} catch (Exception e) {
			ut.rollback();
			//to return the 500 Internal Server error if error happens or trip or user not found
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				      .type("User Or Trip Not Found")
				      .entity(e.getMessage())
				      .build());
		}
		
	}
	
	@GET
	@Path("/viewtrips/{user_id}")
	public List<Trip> viewTrip(@PathParam("user_id") int user_id) throws IllegalStateException, SecurityException, SystemException {
		try {
			ut.begin();
			User user= entityManager.find(User.class,user_id );//get the specified user by id
			List<Trip>trips= user.UserTrips();//getting the user trips to return it
			ut.commit();
			return trips;
			
		} catch (Exception e) {
			ut.rollback();
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				      .type(MediaType.TEXT_PLAIN)
				      .entity(e.getMessage())
				      .build());
		}
	
		
	}
	
	@GET
	@Path("/notifications/{user_id}")
	public List<Notification>showUserNotifications(@PathParam("user_id")int user_id) throws IllegalStateException, SecurityException, SystemException{
		try {
			ut.begin();
			User user= entityManager.find(User.class,user_id );//get the specified user by id
			List<Notification>notifications= user.UserNotifications();//getting the user trips to return it
			ut.commit();
			return notifications;
			
		} catch (Exception e) {
			ut.rollback();
			throw new WebApplicationException(Response
				      .status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
				      .type(MediaType.TEXT_PLAIN)
				      .entity(e.getMessage())
				      .build());
		}
	}
}

