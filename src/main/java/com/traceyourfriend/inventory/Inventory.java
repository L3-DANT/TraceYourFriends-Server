package com.traceyourfriend.inventory;

import com.google.gson.Gson;
import com.pusher.rest.Pusher;
import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;
import com.traceyourfriend.utils.Coordinate;
import com.traceyourfriend.utils.HashUser;
import com.traceyourfriend.utils.PusherSingleton;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


/**
 * This class is used to manage users inventory.
 *
 * @author Aniss
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class Inventory {


	private final UsersDAO dao = new UsersDAO();

	/**
	 * This method will return all users that are listed
	 * in users table.
	 * <p/>
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/
	 *
	 * @return - JSON array string
	 * @throws Exception
	 */
	@GET
	public String findAll() throws SQLException {
		List<User> users = dao.findAll();
		return new Gson().toJson(users);
	}


	/**
	 * This method will return the specific name of user is looking for.
	 * It is very similar to the method findAll except this method uses the
	 * PathParam to bring in the data.
	 * <p/>
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/searchName/aniss
	 *
	 * @param name - name user
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@Path("searchName/{name}")
	@GET
	public String findWithName(@PathParam("name") String name) throws SQLException {
		if (StringUtils.isEmpty(name)) {
			throw new NotFoundException();
		}
		User user = dao.findWithName(name);
		if (user == null) {
			throw new NotFoundException();
		}
		return new Gson().toJson(user);
	}

	@POST
	@Path("coord")
	public String Coor(String message) throws SQLException {
		Coordinate coordinate = new Gson().fromJson(message, Coordinate.class);

		HashUser h = HashUser.getInstance();
		User u = h.searchHash(coordinate.getName());
		if (u == null) {
			return new Gson().toJson(500);
		}
		u.setCoorX(coordinate.getCoorX());
		u.setCoorY(coordinate.getCoorY());
		Pusher pusher = PusherSingleton.getInstance().GetPusher();
		pusher.trigger(u.getName(), "coorX", coordinate.getCoorX());
		pusher.trigger(u.getName(), "coorY", coordinate.getCoorY());

		// Never returns nothing because your server will have to response something very basic
		// You have a lot of warnings on some server for this kind of error.
		return new Gson().toJson(u.getDemandesAmi());
	}

	@POST
	@Path("inscription")
	public String Inscription(String message) throws SQLException{
		User u = new Gson().fromJson(message, User.class);
		int inscr = dao.add(u);
		return new Gson().toJson(inscr);

	}

	@POST
	@Path("connexion")
	public String Connexion(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		User u = new Gson().fromJson(message, User.class);
		User user = h.searchHash(u.getMail());
		ArrayList<String> cone= new ArrayList<String>();
		if (user != null && u.getPassword().equals(user.getPassword())) {
			Pusher pusher = PusherSingleton.getInstance().GetPusher();
			pusher.trigger(user.getName(),"connected",true);
			cone = user.getAmis();
		}else{
			cone = null;
		}
		return new Gson().toJson(cone);

	}

	@POST
	@Path("search/contact")
	public String rechercheContact(String recherche){
		HashUser h = HashUser.getInstance();
		ArrayList<String> listContact = h.searchListContacts(new Gson().fromJson(recherche, String.class));

		return new Gson().toJson(listContact.toString());
	}

/*Demande d'ami*/
	@POST
	@Path("invite")
	public String inviteAmi(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		String name = new Gson().fromJson("name", String.class);
		String nameAmi = new Gson().fromJson("nameAmi", String.class);
		User user = h.searchHash(name);
		User userAmi = h.searchHash(nameAmi);
		if (!user.estAmi(userAmi.getName()) && !userAmi.aDemande(user.getName())){
			/*Si un utilisateur invite un autre utilisateur qui de son côté a aussi fait la demande auparavant, alors ils deviennent amis sans demande de confirmation*/
			if(user.aDemande(nameAmi)){
				user.removeDemandeAmi(nameAmi);
				user.addAmi(nameAmi);
				userAmi.addAmi(name);
			} else{
				userAmi.addDemandeAmi(name);
				user.addInvitation(nameAmi);
			}
			return "200";
		}
		return "500";
	}

	/*Accepter ou refuser une invitation*/
	@POST
	@Path("request")
	public String requestAmi(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		String name = new Gson().fromJson("name", String.class);
		String nameAmi = new Gson().fromJson("nameAmi", String.class);
		Boolean reponse = new Gson().fromJson("reponse", Boolean.class);
		User user = h.searchHash(name);
		User userAmi = h.searchHash(nameAmi);
		if (user.aDemande(nameAmi)){
			user.removeDemandeAmi(nameAmi);
			if (reponse){
				user.addAmi(nameAmi);
				userAmi.addAmi(name);
			}
			return "200";
		}
		return "500";
	}

	@POST
	@Path("delete")
	public String deleteAmi(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		String name = new Gson().fromJson("name", String.class);
		String nameAmi = new Gson().fromJson("nameAmi", String.class);
		User user = h.searchHash(name);
		User userAmi = h.searchHash(nameAmi);
		if (user.estAmi(nameAmi)){
			user.removeAmi(nameAmi);
			userAmi.removeAmi(name);
			return "200";
		}
		return "500";
	}

}