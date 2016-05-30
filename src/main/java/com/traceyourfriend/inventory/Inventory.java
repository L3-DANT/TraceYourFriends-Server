package com.traceyourfriend.inventory;

import com.google.gson.Gson;
import com.pusher.rest.Pusher;
import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;
import com.traceyourfriend.utils.Coordinate;
import com.traceyourfriend.utils.HashUser;
import com.traceyourfriend.utils.PusherSingleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
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
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/
	 *
	 * @return - JSON array string
	 * @throws SQLException
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
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/coord
	 *
	 * @param message - emailOrName + coorX + coorY
	 * @return - json array results list from the database
	 * @throws SQLException
	 */
	@POST
	@Path("coord")
	public String Coor(String message) throws SQLException {
		Coordinate coordinate = new Gson().fromJson(message, Coordinate.class);

		HashUser h = HashUser.getInstance();
		User u = h.searchHash(coordinate.getName());
		if (u == null) {
			return new Gson().toJson(null);
		}
		u.setCoorX(coordinate.getCoorX());
		u.setCoorY(coordinate.getCoorY());
		Pusher pusher = PusherSingleton.getInstance().GetPusher();
		pusher.trigger(u.getName(), "coorX/coorY", coordinate.getCoorX() + "/" + coordinate.getCoorY());
		return new Gson().toJson(u.getDemandesAmi());
	}

	/**
	 * This method will permit sign in a specific user
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/inscription
	 *
	 * @param message - email + name + password
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */

	@POST
	@Path("inscription")
	public String Inscription(String message) throws SQLException{
		User u = new Gson().fromJson(message, User.class);
		int inscr = dao.add(u);
		return new Gson().toJson(inscr);

	}

	/**
	 * This method will permit to log in a specific user
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/connexion
	 *
	 * @param message - emailOrName + password
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */

	@POST
	@Path("connexion")
	public String Connexion(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		User u = new Gson().fromJson(message, User.class);
		User user = h.searchHash(u.getMail());
		ArrayList<String> cone;
		if (user != null && u.getPassword().equals(user.getPassword())) {
			//Pusher pusher = PusherSingleton.getInstance().GetPusher();
			//pusher.trigger(user.getName(),"connected",true);
			cone = user.getAmis();
		}else{
			cone = null;
		}
		return new Gson().toJson(cone);

	}


	/**
	 * This method will permit to allow or dismiss a friend request
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/search/contact
	 *
	 * @param recherche - emailOrName of the user we search for
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */
	@POST
	@Path("search/contact")
	public String rechercheContact(String recherche){
		HashUser h = HashUser.getInstance();
		ArrayList<String> listContact = h.searchListContacts(new Gson().fromJson(recherche, String.class));

		return new Gson().toJson(listContact.toString());
	}


	/**
	 * This method will permit to send a request to an other user
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/invite
	 *
	 * @param message - emailOrName + friend emailOrName
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */
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
				userAmi.removeInvitation(name);
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

	/**
	 * This method will permit to allow or dismiss a friend request
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/request
	 *
	 * @param message - emailOrName + friend emailOrName + boolean false or true
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */
	@POST
	@Path("request")
	public String requestAmi(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		User u = new Gson().fromJson("name", User.class);
		User uAmi = new Gson().fromJson("nameAmi", User.class);
		Boolean reponse = new Gson().fromJson("bool", Boolean.class);
		User user = h.searchHash(u.getMail());
		User userAmi = h.searchHash(uAmi.getMail());
		if (user.aDemande(userAmi.getName())){
			dao.deleteRequest(user, userAmi);
			dao.deleteInvitation(userAmi, user);
			user.removeDemandeAmi(userAmi.getName());
			userAmi.removeInvitation(user.getName());
			if (reponse){
				dao.acceptFriend(user, userAmi);
				user.addAmi(userAmi.getName());
				userAmi.addAmi(user.getName());
			}
			return "200";
		}
		return "500";
	}


	/**
	 * This method will delete a specific user thanks to his name
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/delete
	 *
	 * @param message - emailOrName + emailOrName of the user we want to delete
	 * @return - String 500 if crash or 200 if it's ok
	 * @throws SQLException
	 */
	@POST
	@Path("delete")
	public String deleteAmi(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		User u = new Gson().fromJson("name", User.class);
		User uAmi = new Gson().fromJson("nameAmi", User.class);
		User user = h.searchHash(u.getMail());
		User userAmi = h.searchHash(uAmi.getMail());
		if (user.estAmi(userAmi.getName())){
			dao.deleteFriend(user, userAmi);
			user.removeAmi(userAmi.getName());
			userAmi.removeAmi(user.getName());
			return "200";
		}
		return "500";
	}

    @POST
    @Path("listFriend")
    public String listFriend(String message) throws SQLException{
        HashUser h = HashUser.getInstance();
        User u = new Gson().fromJson(message, User.class);
        User user = h.searchHash(u.getMail());
        ArrayList<String> cone;
        if (user != null) {
            cone = user.getAmis();
        }else{
            cone = null;
        }
        return new Gson().toJson(cone);
    }

}