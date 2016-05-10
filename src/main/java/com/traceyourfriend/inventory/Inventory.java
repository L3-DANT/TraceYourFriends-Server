package com.traceyourfriend.inventory;

import com.google.gson.Gson;
import com.pusher.rest.Pusher;
import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;
import com.traceyourfriend.utils.HashUser;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
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
	 * This method will return the specific email of user is looking for.
	 * It is very similar to the method findAll except this method uses the
	 * PathParam to bring in the data.
	 * <p/>
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/searchMail/aniss.cherkaoui@gmail.com
	 *
	 * @param email - product email user
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@Path("searchMail/{email}")
	@GET
	public String findWithEmail(@PathParam("email") String email) throws SQLException {
		if (StringUtils.isEmpty(email)) {
			throw new NotFoundException();
		}
		User user = dao.findWithEmail(email);
		if (user == null) {
			throw new NotFoundException();
		}
		return new Gson().toJson(user);
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

	@GET
	@Path("Coor/{name}/{coorX}/{coorY}")
	public Response Coor(@PathParam("name") final String name, @PathParam("coorX") final String coorX, @PathParam("coorY") final String coorY) throws SQLException {
		User u = dao.findWithName(name);
		if (u == null) {
			throw new NotFoundException();
		}
		u.setCoorX(coorX);
		u.setCoorY(coorY);

		String url = "http://" + "272ee489a902c2f6a96f" + ":" + "efb0b30a6239f96d1e95" + "@api.pusherapp.com:80/apps/" + "195526";
		Pusher pusher = new Pusher(url);
		pusher.trigger(u.getName(), "coor", Collections.singletonMap(u.getName(),u.getCoor()));
		// Never returns nothing because your server will have to response something very basic
		// You have a lot of warnings on some server for this kind of error.
		return Response.noContent().build();
	}

	@POST
	@Path("inscription")
	public String Inscription(String message) throws SQLException{
		User u = new Gson().fromJson(message, User.class);
		// This is better to send the user instead of a code.
		// The code that you want to send is the HTTP status code
		// So either you use a 200 with a content / 403 / 404
		return new Gson().toJson(u);

	}

	@POST
	@Path("connexion")
	public String Connexion(String message) throws SQLException{
		HashUser h = HashUser.getInstance();
		User u = new Gson().fromJson(message, User.class);
		u = h.searchHash(u.getMail());
		if (u == null) {
			// The user doesn't exist, we can't allow that
			throw new ForbiddenException();
		}
		// This is better to send the user instead of a code.
		// The code that you want to send is the HTTP status code
		// So either you use a 200 with a content / 403 / 404
		return new Gson().toJson(u);

	}
}