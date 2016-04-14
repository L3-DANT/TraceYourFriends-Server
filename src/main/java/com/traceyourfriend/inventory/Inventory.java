package com.traceyourfriend.inventory;

import com.google.gson.Gson;
import com.pusher.rest.Pusher;
import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
	public void Coor(@PathParam("login") final String name, @PathParam("coorX") final String coorX, @PathParam("coorY") final String coorY) throws SQLException {
		User u = null;

		u = dao.findWithName(name);
		u.setCoorX(coorX);
		u.setCoorY(coorY);

		String url = "http://" + "272ee489a902c2f6a96f" + ":" + "efb0b30a6239f96d1e95" + "@api.pusherapp.com:80/apps/" + "195526";
		Pusher pusher = new Pusher(url);
		pusher.trigger(u.getName(), "coor", Collections.singletonMap(u.getName(),u.getCoor()));
	}

	@POST
	@Path("Inscription/")
	@Consumes("application/json")
	public String Inscription(String message) throws SQLException{
		User u = new Gson().fromJson(message, User.class);

		Boolean inscr = dao.CreateUser(u);

		return new Gson().toJson(inscr.toString());
	}
}